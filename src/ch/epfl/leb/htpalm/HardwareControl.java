
package ch.epfl.leb.htpalm;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.apache.commons.beanutils.BeanUtils;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.AcquisitionEngine;
import org.micromanager.utils.MMScriptException;
import org.micromanager.utils.ReportingUtils;
import static ch.epfl.leb.htpalm.Debug.DEBUG;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.micromanager.utils.MMException;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HardwareControl {

   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   /* note configHW_ will be a CLONE of config_ used in the other parts of 
    * the plugin to make sure that it cannot be modified during acquisition
    */
   ConfigurationOptions configHW_ = new ConfigurationOptions();
   SpiralMosaic mosaic_;
   HtpalmMetadata metadata_;
   int currentFovNum_;
   double currentFovPosX_,currentFovPosY_;
   boolean skipCurrentFOV_;
   Thread currentAcqThread_=null;
   boolean isInitialized_ = false, isRunning_=false;
   
   public HardwareControl(MMStudioMainFrame gui_){
      this.gui_ = gui_;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }
   
   public void initializeAcquisition(){
      configHW_.loadDefaultConfig();
      initializeAcquisitionMain();
   }
   
   public void initializeAcquisition(String fpath){
      configHW_.loadConfig(fpath);
      initializeAcquisitionMain();
   }
   
   public void initializeAcquisition(ConfigurationOptions newConfig){
      try {
         BeanUtils.copyProperties(configHW_,newConfig);//copy the properties of newConfig into configHW_, ready to run acquisition
         initializeAcquisitionMain();
      } 
      catch (IllegalAccessException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      }
      catch (InvocationTargetException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      }
   }


   private void initializeAcquisitionMain(){
      initializeHardwareControl();
      //check that Acquisition folder does not aleady contain data
      checkIfPreviousAcqExists();
      isInitialized_ = true;
   }

   private void initializeHardwareControl(){
      mosaic_ = new SpiralMosaic(configHW_.mosaicStartPosX_,configHW_.mosaicStartPosY_,configHW_.mosaicStepSizeX_,configHW_.mosaicStepSizeY_, configHW_.getMosaicNFov());
      //set up all the correct file names and metadata  - how is skip fov going to work for saving? - save after every acquisition.
      metadata_ = new HtpalmMetadata(configHW_,mosaic_);
      gotoFOV(0);
   }
   
   private void checkIfPreviousAcqExists(){//and if it does, make a new folder and change the acquisition name
      //if the configHW_.fileAcqFolder_ does not exist, 
      String configPath;
      String acqName = configHW_.fileAcqFolder_;
      File acqFolder = new File(acqName);
      //String fname = configHW_.fileAcqFolder_+File.separator+metadata_.metaDataFileName_;
      //File metadataFile = new File(fname);

      //have to do this in a loop in case the replacement filename matches as well
      boolean acqNameIsOk = false;
      while (acqNameIsOk ==false){
         //if the folder already exists and contains files
         // parse the name of fileAcqFolder_ , think up a new one
         //find anything that looks like _(numbers) at the end of the file
         // if you dont find any, add _1 to the end
         // otherwise, increment the number

         if (!acqFolder.exists()){
            acqFolder.mkdir();
            acqNameIsOk = true;
         }
         else if ( (acqFolder.list()).length ==0){//if the folder is empty
            acqNameIsOk = true;
         }
         else{
            acqName = makeNewFolderName(acqName);
            acqFolder = new File(acqName);
            acqNameIsOk = false;//go round again til we get an empty folder
         }
         
         if (DEBUG){
            System.out.println("Folder name: " + acqName);
         }
      }

      configHW_.fileAcqFolder_ = acqName;
      metadata_.acqFolder_ = acqName;
      //setup the config files and were good to go
      //save a copy of the config in the acquisition folder
      configPath = acqName +File.separator+ metadata_.getConfigFileName();
      configHW_.saveConfig(configPath);//save the config in the acquisition folder
      //save an empty metadata file to signal that the folder is in use
      metadata_.saveMetadata();
   }
   
   public void gotoFOV(int fovNum){
      currentFovNum_ = fovNum;
      currentFovPosX_ =mosaic_.getX(fovNum);
      currentFovPosY_ =mosaic_.getY(fovNum);
         
      try {
         gui_.setXYStagePosition(currentFovPosX_,currentFovPosY_);
         if (DEBUG){
            Point2D.Double posXY = gui_.getXYStagePosition();
            System.out.println("Stage pos x: "+ posXY.x+ " y: "+ posXY.y);
            
         }
      } catch (MMScriptException ex) {
         ReportingUtils.logError(ex, "Could not move stage to FOV "+ Integer.toString(fovNum));
      }

      //Check whether we will skip this FOV
      if (configHW_.fovAnalysis_excludeBadFov_){
         //TODO - detect the bacteria
         skipCurrentFOV_=false;
      } else {
         skipCurrentFOV_ = false;
      }
   }

   public void setCurrentPosAsOrigin(){
      try {
         Point2D.Double posXY;
         posXY = gui_.getXYStagePosition();
         gui_.setXYOrigin(posXY.x,posXY.y);
      } catch (MMScriptException ex) {
         ReportingUtils.logError(ex, "Could not set origin");
      }
   }
   
   public Integer getCurrentFovNum(){
      return currentFovNum_;
   }

   public void gotoPrevFov(){
      if (currentFovNum_>0){
         gotoFOV(currentFovNum_-1);
      }
   }

   public void gotoNextFov(){
      if (currentFovNum_<configHW_.getMosaicNFov()){
         gotoFOV(currentFovNum_+1);
      }
   }

   public void acquire1Fov(){
      //TODO  - check we are not about to overwrite an existing acquisition
      //TODO - set the laser powers, ttls
      if (!isInitialized_){
            throw new RuntimeException("Error: Htpalm acquisition must be initialized before starting acquisition!");
      } 
      else { 
         if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
            ReportingUtils.showError("Error: Cannot start new acquisition while a previous acquisition is running!");
         } 
         else {
            currentAcqThread_ = new Thread(new Acquire1FovManual(this));
            currentAcqThread_.start();
         }
      }
   }
   
   public void acquireAll(){

      if (!isInitialized_){
         throw new RuntimeException("Error: Htpalm acquisition must be initialized before starting acquisition!");
      } 
      else{
         if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
            ReportingUtils.showError("Error: Cannot start new acquisition while a previous acquisition is running!");
         } else {
            currentAcqThread_ = new Thread(new AcquireAllFov(this));
            currentAcqThread_.start();
         }
      }
   }
   
   public void abortAll(){
      if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
         currentAcqThread_.interrupt();
         acq_.abortRequest();//Shut down current acquisition
      }
      //TODO - if an acquisition was interupted, delete the metadata for that acquisition
   }


   
   class Acquire1FovManual implements Runnable{
      //TODO setup proper interrupt to shut down acquisition
      HardwareControl control_;
      Acquire1FovManual(HardwareControl control) {
         this.control_= control;
      }

      public void run() {
         try {
            isRunning_ = true;
            //note - in manual mode we ignore skipCurrentFOV_

            //sort out  the lasers
            if (configHW_.laserControlIsAutomatic_){
               //TODO - Autolase
            }
            else {
               //Make sure the laser powers etc are right
               core_.setProperty(configHW_.getLaserExDacName(0),configHW_.getLaserExDacName(1),configHW_.laserManualExPower_);
               core_.setProperty(configHW_.getLaserExTtlName(0),configHW_.getLaserExTtlName(1),1);
               core_.setProperty(configHW_.getLaserActDacName(0),configHW_.getLaserActDacName(1),configHW_.laserManualActPower_);
               core_.setProperty(configHW_.getLaserActTtlName(0),configHW_.getLaserActTtlName(1),1);
               core_.waitForSystem();
            }         

            //turn autoshutter on (and findout the original state so we can reset it after acquisition)
            boolean autoShutterOnBefore = core_.getAutoShutter();
            if (!autoShutterOnBefore){
               core_.setAutoShutter(true);
               core_.waitForSystem();
            }
            
            //update the metadata
            boolean phPreAcquire=true,phPostAcquire=true;// for now this is always the case
            int[] flCh={0};// for now this is always the case
            control_.metadata_.addNewAcquisition(control_.currentFovNum_, phPreAcquire, phPostAcquire, flCh);
            control_.metadata_.saveMetadata();

            // goto FOV - should be there already but just in case
            control_.gotoFOV(control_.currentFovNum_);
            
            if (DEBUG){
               System.out.println("Acquiring FOV "+control_.currentFovNum_);
            }
            //get last array acqMetadata
            AcqMetadata acqMetadata =control_.metadata_.acqMetadataList_.get(control_.metadata_.acqMetadataList_.size()-1);

      
            //Acquire
            acquire1FovAuto(acqMetadata);

            //reset the autoshutter to its initial state
            if (!autoShutterOnBefore){
               core_.setAutoShutter(autoShutterOnBefore);
               core_.waitForSystem();
            }

         } 
         catch (Exception ex) {
            throw new RuntimeException(ex);
         }
         finally{
            isRunning_ = false;
         }
      }
      
   }

   class AcquireAllFov implements Runnable{

      
      //TODO setup proper interrupt to shut down acquisition
      HardwareControl control_;
      AcquireAllFov(HardwareControl control) {
         this.control_= control;
      }

      
      public void run() {
         try{
            isRunning_=true;
            //sort out  the lasers
            if (configHW_.laserControlIsAutomatic_){
                     //TODO - Autolase
            }
            else {
               //Make sure the laser powers etc are right
               core_.setProperty(configHW_.getLaserExDacName(0),configHW_.getLaserExDacName(1),configHW_.laserManualExPower_);
               core_.setProperty(configHW_.getLaserExTtlName(0),configHW_.getLaserExTtlName(1),1);
               core_.setProperty(configHW_.getLaserActDacName(0),configHW_.getLaserActDacName(1),configHW_.laserManualActPower_);
               core_.setProperty(configHW_.getLaserActTtlName(0),configHW_.getLaserActTtlName(1),1);
               core_.waitForSystem();
            } 

            //turn autoshutter on (and findout the original state so we can reset it after acquisition)
            boolean autoShutterOnBefore = core_.getAutoShutter();
            if (!autoShutterOnBefore){
               core_.setAutoShutter(true);
               core_.waitForSystem();
            }

            //aquire all the Fovs
            for (int ii=0;ii< control_.configHW_.mosaicNFov_;ii++){
               boolean phPreAcquire=true,phPostAcquire=true;// for now this is always the case
               int[] flCh={0};// for now, this is always the case
               control_.gotoFOV(ii);
               control_.metadata_.addNewAcquisition(control_.currentFovNum_, phPreAcquire, phPostAcquire, flCh);
               control_.metadata_.saveMetadata();

               if (control_.skipCurrentFOV_==false){
                  
                  if (DEBUG){
                     System.out.println("Acquiring FOV "+control_.currentFovNum_);
                  }

                  //get last array acqMetadata
                  AcqMetadata acqMetadata =control_.metadata_.acqMetadataList_.get(control_.metadata_.acqMetadataList_.size()-1);
                  //acquire
                  acquire1FovAuto(acqMetadata);
               }
            }

            //reset the autoshutter to its initial state
            if (!autoShutterOnBefore){
               core_.setAutoShutter(autoShutterOnBefore);
               core_.waitForSystem();
            }
         } 
         catch (Exception ex) {
            throw new RuntimeException(ex);
         }
         finally{
            isRunning_=false;
            isInitialized_=false; //this makes sure the acquisition will not get overwritten
         }
      }

   }


   
   private void acquire1FovAuto(AcqMetadata acqMetadata){
      
      String fname;

      if (acqMetadata.phPreAcquire_=true){
         //acquire ph pre
         acquire1Phase(acqMetadata.acqNamePhPre_);
      }

      for (int ii: acqMetadata.flCh_){
         //TODO - multichannel logic to go here
         acquire1Fl(acqMetadata.acqNameFl[ii]);
      }
   
      if (acqMetadata.phPostAcquire_=true){
         //acquire ph post 
         acquire1Phase(acqMetadata.acqNamePhPost_);
      }
   }

   private void acquire1Phase(String acqName){
      try {
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(configHW_.getLaserShutterTtlName(0),configHW_.getLaserShutterTtlName(1),0);//Lasers off
         core_.setProperty(configHW_.getPhLampTtlName(0),configHW_.getPhLampTtlName(1),1);//Ph lamp on
         core_.waitForSystem();
   
         //run the acquisition
         String camName = configHW_.camPhName_;
         String rootDirName = configHW_.fileAcqFolder_;
         //TODO add an acquisition name!
         int numFrames = 1;
         double intervalMs = 0;
         double exposureTime = configHW_.camPhExposureMs_;
         if (configHW_.isCamConvertPhExposureToSec()){//correct for stupid bug in the camera driver
            exposureTime /= 1000;
         }
         double delayTime = configHW_.camPhDelayMs_;
         boolean closeOnExit = true;
         acquire1Movie(camName, acqName, rootDirName, numFrames, intervalMs, exposureTime,delayTime, closeOnExit);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }
   
   private void acquire1Fl(String acqName){
      try {
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(configHW_.getLaserShutterTtlName(0),configHW_.getLaserShutterTtlName(1),1);//Lasers ON
         core_.setProperty(configHW_.getPhLampTtlName(0),configHW_.getPhLampTtlName(1),0);//Ph lamp OFF
         core_.waitForSystem();
         
         //run the acquisition
         String camName = configHW_.camEmccdName_;
         String rootDirName = configHW_.fileAcqFolder_;
         int numFrames = configHW_.getCamEmccdNumFrames();
         double intervalMs = 0;
         double exposureTime = configHW_.camEmccdExposureMs_;
         double delayTime = configHW_.camPhDelayMs_;
         boolean closeOnExit = true;
         acquire1Movie(camName, acqName, rootDirName, numFrames, intervalMs, exposureTime,delayTime, closeOnExit);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   private void acquire1Movie(String camName, String acqName, String rootDirName, int numFrames, double intervalMs, double exposureTime,double delayTime, boolean closeOnExit){
      try{
         core_.setProperty("Core", "Camera", camName);
         core_.setProperty(camName,"Exposure",exposureTime);
         
         acq_.setRootName(rootDirName);
         acq_.setDirName(acqName);
         acq_.setFrames(numFrames,intervalMs);
         acq_.setUpdateLiveWindow(true);
         core_.waitForSystem();
         String[] oldAcqNames=null, newAcqNames= null;
         String currentAcqName=null;
         oldAcqNames = gui_.getAcquisitionNames();
         acq_.acquire();
         
         //while(!acq_.isAcquisitionRunning())//wait until acq_ is started
         //{	Thread.sleep(50);}
         while(!acq_.isFinished()){
            if (newAcqNames== null){ 
               newAcqNames =gui_.getAcquisitionNames(); 
               currentAcqName = getCurrentAcqName(newAcqNames,oldAcqNames);
            }
            Thread.sleep(200);
         }  
         
         core_.waitForSystem();
         gui_.message("Acq finished");
         if (delayTime> 1){//ie if its not been set to 0 as per fl acquisition
            Thread.sleep(Math.round(delayTime));//give the PH cam time to sort itself out
         }
         
         if (closeOnExit==true){
            gui_.closeAcquisitionImage5D(currentAcqName);
         }
         gui_.refreshGUI();
         
      }
      catch(InterruptedException e){
         throw new RuntimeException(e);
      }
      catch (MMException mex) {
         throw new RuntimeException(mex);
      }
      catch (Exception ex) {
         throw new RuntimeException(ex);
      }

   }

   /*
    *   find the acquisition name matching Acq_NN where NN is the largest number
    */
   private String getCurrentAcqName(String[] newAcqNames, String[] oldAcqNames) {
      String currentAcqName =null;
      int nOld,nNew,nCurrent,iCurrent;
      nOld = oldAcqNames.length;
      nNew = newAcqNames.length;
      nCurrent=0;//number of new names
      iCurrent = 0;
      
      //for each acq name in newAcqNames
      for (int ii=0;ii<nNew;ii++){
         //compare to all memebers of old acqNames, check if it existed before
         int nMatches=0;
         for (int jj=0;jj<nOld;jj++){
            if (newAcqNames[ii].matches(oldAcqNames[jj])){
               nMatches++;
            }
         }
         if (nMatches==0){//if its new
            nCurrent++;
            iCurrent= ii;
         }
      }
      
      if (nCurrent ==0 ){
         throw new RuntimeException("Finding current acquisition name failed - no new names ");// throw runtime exception
		}      
		else if (nCurrent > 1){
         throw new RuntimeException("Finding current acquisition name failed - multiple new names ");// throw runtime exception
		}
      else {
          currentAcqName = newAcqNames[iCurrent];
 		}           
      return currentAcqName;
   } 
   
   public String makeNewFolderName(String fname){
      String newFname;
      
      Pattern p = Pattern.compile("_\\d+$");// ie "_[some numbers][end of string]"
      Matcher m = p.matcher(fname);
      if (m.find()){
         String sMatch = m.group();
         //skip the _,convert it to a number, and increment it by 1
         int fNum = Integer.parseInt(sMatch.substring(1)) + 1;
         //get fname without the _ stuff
         fname = fname.substring(0,m.start());
         // increment the number by 1
         newFname = fname + "_" + fNum;
      }
      else{
         newFname = fname + "_" + 1;
      }

      return newFname;
   }
}
