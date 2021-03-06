
package ch.epfl.leb.htpalm;

import ch.epfl.leb.autolase.AutoLase;
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
import ch.epfl.leb.htpalm.fovfilter.FovFilter;
import ch.epfl.leb.htpalm.fovfilter.FovFilterConfig;
import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.micromanager.utils.ImageUtils;
import org.micromanager.utils.MMException;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HardwareControl implements ImageListener{

   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   /* note configHW_ will be a CLONE of config_ used in the other parts of 
    * the plugin to make sure that it cannot be modified during acquisition
    */
   ConfigurationOptions configHW_ = new ConfigurationOptions();
   FovList fovList_;
   HtpalmMetadata metadata_;
   int currentFovNum_;
   double currentFovPosX_,currentFovPosY_;
   boolean skipCurrentFOV_=false;
   Thread currentAcqThread_=null;
   boolean isInitialized_ = false, isRunning_=false;

   private Boolean showLabelIm_ = true;
   private ImagePlus filterIm_ = null, labelIm_=null;
   private ImageProcessor filterIp_ = null, labelIp_=null;
   FovFilter fovFilter_;
   
   public HardwareControl(MMStudioMainFrame gui_){
      this.gui_ = gui_;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
      ImagePlus.addImageListener(this); 
      AutoLase.INSTANCE.setup(gui_);
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
      fovList_ = new Spiral(configHW_.getMosaicStartPosX_(), configHW_.getMosaicStartPosY_(), configHW_.getMosaicStepSizeX_(), configHW_.getMosaicStepSizeY_());
      //set up all the correct file names and metadata  - how is skip fov going to work for saving? - save after every acquisition.
      metadata_ = new HtpalmMetadata(configHW_);
      gotoFOV(0);
   }
   
   private void checkIfPreviousAcqExists(){//and if it does, make a new folder and change the acquisition name
      //if the configHW_.fileAcqFolder_ does not exist, 
      String configPath;
      String acqName = configHW_.getFileAcqFolder_();
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

      configHW_.setFileAcqFolder_(acqName);
      metadata_.setAcqFolder_(acqName);
      //setup the config files and were good to go
      //save a copy of the config in the acquisition folder
      configPath = acqName +File.separator+ metadata_.getConfigFileName();
      configHW_.saveConfig(configPath);//save the config in the acquisition folder
      //save an empty metadata file to signal that the folder is in use
      metadata_.saveMetadata();
   }
   
   public void gotoFOV(int fovNum){
      currentFovNum_ = fovNum;
      fovList_.gotoFov(fovNum);
      currentFovPosX_ =fovList_.getX();
      currentFovPosY_ =fovList_.getY();
         
      try {
         gui_.setXYStagePosition(currentFovPosX_,currentFovPosY_);
         if (DEBUG){
            Point2D.Double posXY = gui_.getXYStagePosition();
            System.out.println("Stage pos x: "+ posXY.x+ " y: "+ posXY.y);
            
         }
      } catch (MMScriptException ex) {
         ReportingUtils.logError(ex, "Could not move stage to FOV "+ Integer.toString(fovNum));
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
      } else {
         throw new RuntimeException("Error: FOV number < 0 requested");
      }
   }

   public void gotoNextFov(){
      gotoFOV(currentFovNum_+1);
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

   private void initializeAcqEngine() {
      //DO NOTHING
      //TODO: clear all the Acq Engine settings
   }

   private void closeAllAcqWindows(){
      try {
        String[] acqNames = gui_.getAcquisitionNames();
         for(int ii = 0;ii < acqNames.length;ii++){
            gui_.getAcquisition(acqNames[ii]).closeImageWindow();
         }
      } catch (MMScriptException ex) {
         throw new RuntimeException(ex);
      }
   }
   
   @Override
   public void imageOpened(ImagePlus imp) {
      //DO NOTHING
   }

   @Override
   public void imageClosed(ImagePlus imp) {
       if (imp == this.labelIm_){
         labelIm_ = null;
      } else if (imp == this.filterIm_){
         filterIm_ = null; 
      }
   }

   @Override
   public void imageUpdated(ImagePlus imp) {
      //DO NOTHING
   }

   private void acquireAllFirstSet() {
      // TODO : add a maxFOV as a hard limit
      int nFov = configHW_.getMosaicNFov_();
      int ii=0;
      gotoFOV(0);
      while (ii < nFov){
         
         closeAllAcqWindows();

         boolean phPreAcquire=true,phPostAcquire=true;// for now this is always the case
         int[] flCh={0};// for now, this is always the case
         if (configHW_.isExcludeBadFov_()){
            filterCurrentFov();
         }

         if (skipCurrentFOV_==false){
            ii ++;
            metadata_.addNewAcquisition(currentFovNum_, phPreAcquire, phPostAcquire, flCh);
            metadata_.saveMetadata();
            
            //get last array acqMetadata
            AcqMetadata acqMetadata =metadata_.getAcqMetadataList_().get(metadata_.getAcqMetadataList_().size()-1);
            //acquire
            acquire1FovAuto(acqMetadata);
         }
         gotoNextFov();

      }
   }

   private void acquireAllRepeats() {
      //get a list of the fov #s previously imaged
      ArrayList<FovMetadata> fovMetadataList = metadata_.getFovMetadataList_();
      int nFov = fovMetadataList.size();

      //NB this ii = 1 start is intentional - not a zero indexing bug! 
      // we have already acquired the 0th (first) FOV in acquireAllFirstSet
      for (int ii = 1; ii<configHW_.getMosaicNRepeats_() ;ii++ ){
         for (int jj =0;jj< nFov;jj++){
            int curFov = fovMetadataList.get(jj).getFovNum_();
            
            closeAllAcqWindows();
            gotoFOV(curFov);
            
            boolean phPreAcquire=true,phPostAcquire=true;// for now this is always the case
            int[] flCh={0};// for now, this is always the case
            
            metadata_.addNewAcquisition(curFov, phPreAcquire, phPostAcquire, flCh);
            metadata_.saveMetadata();

            //get last array acqMetadata
            AcqMetadata acqMetadata =metadata_.getAcqMetadataList_().get(metadata_.getAcqMetadataList_().size()-1);
            //acquire
            acquire1FovAuto(acqMetadata);
         }
      }
      
      
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
            initializeAcqEngine();
            closeAllAcqWindows();

            //sort out  the lasers
            if (configHW_.isLaserControlIsAutomatic_()){
               //TODO - Autolase
            }
            else {
               //Make sure the laser powers etc are right
               core_.setProperty(configHW_.getLaserExDacName_(0),configHW_.getLaserExDacName_(1), configHW_.getLaserManualExPower_());
               core_.setProperty(configHW_.getLaserExTtlName_(0),configHW_.getLaserExTtlName_(1),1);
               core_.setProperty(configHW_.getLaserActDacName_(0),configHW_.getLaserActDacName_(1), configHW_.getLaserManualActPower_());
               core_.setProperty(configHW_.getLaserActTtlName_(0),configHW_.getLaserActTtlName_(1),1);
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
            AcqMetadata acqMetadata =control_.metadata_.getAcqMetadataList_().get(control_.metadata_.getAcqMetadataList_().size()-1);

      
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
            initializeAcqEngine();
            
            //sort out  the lasers
            core_.setProperty(configHW_.getLaserExDacName_(0),configHW_.getLaserExDacName_(1), configHW_.getLaserManualExPower_());
            core_.setProperty(configHW_.getLaserExTtlName_(0),configHW_.getLaserExTtlName_(1),1);
            if (!configHW_.isLaserControlIsAutomatic_()){
               //Make sure the laser powers etc are right
               core_.setProperty(configHW_.getLaserActDacName_(0),configHW_.getLaserActDacName_(1), configHW_.getLaserManualActPower_());
               core_.setProperty(configHW_.getLaserActTtlName_(0),configHW_.getLaserActTtlName_(1),1);
            } //otherwise AutoLase handles the activation laser 
            core_.waitForSystem();

            //turn autoshutter on (and findout the original state so we can reset it after acquisition)
            boolean autoShutterOnBefore = core_.getAutoShutter();
            if (!autoShutterOnBefore){
               core_.setAutoShutter(true);
               core_.waitForSystem();
            }

            //aquire all the Fovs
            control_.acquireAllFirstSet();
            if (configHW_.getMosaicNRepeats_()>1){
               control_.acquireAllRepeats();
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
      try{
         // allow time for autofocus to stabilise before acquisition
         if (configHW_.getCamAutoFocusDelayTimeMs_()> 1){//ie if its not been set to 0 
            Thread.sleep(Math.round(configHW_.getCamAutoFocusDelayTimeMs_()));
         }
         
         if (acqMetadata.isPhPreAcquire_()){
            //acquire ph pre
            acquire1Phase(acqMetadata.getAcqNamePhPre_());
         }

         for (int ii: acqMetadata.getFlCh_()){
            //TODO - multichannel logic to go here
            acquire1Fl(acqMetadata.getAcqNameFl()[ii]);
         }
      
         if (acqMetadata.isPhPostAcquire_()){
            //acquire ph post 
            acquire1Phase(acqMetadata.getAcqNamePhPost_());
         }
      }
      catch (InterruptedException ex){
         throw new RuntimeException(ex);
      }
   }

   private void acquire1Phase(String acqName){
      try {


         //SETUP THE ACQ
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(configHW_.getLaserShutterTtlName_(0),configHW_.getLaserShutterTtlName_(1),0);//Lasers off
         core_.setProperty(configHW_.getPhLampTtlName_(0),configHW_.getPhLampTtlName_(1),1);//Ph lamp on
         core_.waitForSystem();
   
         //run the acquisition
         String camName = configHW_.getCamPhName_();
         String rootDirName = configHW_.getFileAcqFolder_();
         //TODO add an acquisition name!
         int numFrames = 1;
         double intervalMs = 0;
         double exposureTime = configHW_.getCamPhExposureMs_();
         if (configHW_.isCamConvertPhExposureToSec_()){//correct for stupid bug in the camera driver
            exposureTime /= 1000;
         }
         double delayTime = configHW_.getCamPhDelayMs_();
         boolean closeOnExit = true;
         //acquire1Movie(camName, acqName, rootDirName, numFrames, intervalMs, exposureTime,delayTime, closeOnExit);
         
         // RUN THE ACQ
         core_.setProperty("Core", "Camera", camName);
         core_.setProperty(camName,"Exposure",exposureTime);
         
         acq_.setRootName(rootDirName);
         acq_.setDirName(acqName);
         acq_.setFrames(numFrames,intervalMs);
         acq_.setUpdateLiveWindow(true);
         core_.waitForSystem();
         //String[] oldAcqNames=null, newAcqNames= null;
         //String currentAcqName=null;
         //oldAcqNames = gui_.getAcquisitionNames();
         acq_.acquire();
         
         while(acq_.isAcquisitionRunning() ||!acq_.isFinished() ){
            //if (newAcqNames== null){ 
            //   newAcqNames =gui_.getAcquisitionNames(); 
            //   currentAcqName = getCurrentAcqName(newAcqNames,oldAcqNames);
            //}
            Thread.sleep(200);
         }  
         
         core_.waitForSystem();
         if (delayTime> 1){//ie if its not been set to 0 
            Thread.sleep(Math.round(delayTime));//give the cam time to sort itself out
         }
         
         if (closeOnExit==true){
            acq_.stop(true);//Workaround for weird race condition that sometimes fails to close acquisitions
            //gui_.closeAcquisitionWindow(currentAcqName);
         }
         gui_.refreshGUI();
        


         
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }
   
   private void acquire1Fl(String acqName){
      try {
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(configHW_.getLaserShutterTtlName_(0),configHW_.getLaserShutterTtlName_(1),1);//Lasers ON
         core_.setProperty(configHW_.getPhLampTtlName_(0),configHW_.getPhLampTtlName_(1),0);//Ph lamp OFF
         core_.waitForSystem();
         
         //run the acquisition
         String camName = configHW_.getCamEmccdName_();
         String rootDirName = configHW_.getFileAcqFolder_();
         int numFrames = configHW_.getCamEmccdNumFrames_();
         double intervalMs = 0;
         double exposureTime = configHW_.getCamEmccdExposureMs_();
         double delayTime = configHW_.getCamPhDelayMs_();
         boolean closeOnExit = true;
         //acquire1Movie(camName, acqName, rootDirName, numFrames, intervalMs, exposureTime,delayTime, closeOnExit);

         //RUN THE ACQ
         if (configHW_.isLaserControlIsAutomatic_()){
            AutoLase.INSTANCE.setLaserToMinPower();
         }
         
         core_.setProperty("Core", "Camera", camName);
         core_.setProperty(camName,"Exposure",exposureTime);

         
         acq_.setRootName(rootDirName);
         acq_.setDirName(acqName);
         acq_.setFrames(numFrames,intervalMs);
         acq_.setUpdateLiveWindow(true);
         core_.waitForSystem();
         //String[] oldAcqNames=null, newAcqNames= null;
         //String currentAcqName=null;
         //oldAcqNames = gui_.getAcquisitionNames();
         acq_.acquire();

         //we dont want to run autolase before start of acquisition
         while(!acq_.isAcquisitionRunning() ){
            Thread.sleep(50);
         }
         
         if (configHW_.isLaserControlIsAutomatic_()){
            AutoLase.INSTANCE.startLaserControl();
            AutoLase.INSTANCE.startDensityMonitor();
         }
         
         while(acq_.isAcquisitionRunning() ||!acq_.isFinished() ){
            //if (newAcqNames== null){ 
            //   newAcqNames =gui_.getAcquisitionNames(); 
            //   currentAcqName = getCurrentAcqName(newAcqNames,oldAcqNames);
            //}
            Thread.sleep(200);
         }  
         
         if (configHW_.isLaserControlIsAutomatic_()){
            AutoLase.INSTANCE.pauseLaserControl();
            AutoLase.INSTANCE.pauseDensityMonitor();
            AutoLase.INSTANCE.setLaserToMinPower();
         }
         
         core_.waitForSystem();
         if (delayTime> 1){//ie if its not been set to 0 
            Thread.sleep(Math.round(delayTime));//give the cam time to sort itself out
         }
         
         if (closeOnExit==true){
            acq_.stop(true);//Workaround for weird race condition that sometimes fails to close acquisitions
            //gui_.closeAcquisitionWindow(currentAcqName);
         }
         gui_.refreshGUI();
         
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   //private void acquire1Movie(String camName, String acqName, String rootDirName, int numFrames, double intervalMs, double exposureTime,double delayTime, boolean closeOnExit){
   //   try{
   //      core_.setProperty("Core", "Camera", camName);
   //      core_.setProperty(camName,"Exposure",exposureTime);
   //      
   //      acq_.setRootName(rootDirName);
   //      acq_.setDirName(acqName);
   //      acq_.setFrames(numFrames,intervalMs);
   //      acq_.setUpdateLiveWindow(true);
   //      core_.waitForSystem();
   //      String[] oldAcqNames=null, newAcqNames= null;
   //      String currentAcqName=null;
   //      oldAcqNames = gui_.getAcquisitionNames();
   //      acq_.acquire();
   //      
   //      while(acq_.isAcquisitionRunning() ||!acq_.isFinished() ){
   //         if (newAcqNames== null){ 
   //            newAcqNames =gui_.getAcquisitionNames(); 
   //            currentAcqName = getCurrentAcqName(newAcqNames,oldAcqNames);
   //         }
   //         Thread.sleep(200);
   //      }  
   //      
   //      core_.waitForSystem();
   //      if (delayTime> 1){//ie if its not been set to 0 
   //         Thread.sleep(Math.round(delayTime));//give the cam time to sort itself out
   //      }
   //      
   //      if (closeOnExit==true){
   //         acq_.stop(true);//Workaround for weird race condition that sometimes fails to close acquisitions
   //         gui_.closeAcquisitionWindow(currentAcqName);
   //      }
   //      gui_.refreshGUI();
   //      
   //   }
   //   catch(InterruptedException e){
   //      throw new RuntimeException(e);
   //   }
   //   catch (MMException mex) {
   //      throw new RuntimeException(mex);
   //   }
   //   catch (Exception ex) {
   //      throw new RuntimeException(ex);
   //   }

   //}

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

   /*
    * Check if current Fov is ok using FovFilter analysis
    */
   void filterCurrentFov(){
      //snap 1 phase contrast image using current settings, do not save
      filterIp_ = snapFilterImage();
      updateFilterIm();
      //TESTING
      //ImagePlus testIm = new ImagePlus("test",filterIp);
      //testIm.show();
      //filterIm_ = IJ.getImage();
      
      //test if current fov is ok, or to be skipped
      processFilterImage(filterIm_);
      
      //update the label image
      if (showLabelIm_){
         updateLabelIm();
      }
   }

   private ImageProcessor snapFilterImage(){
      try {
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(configHW_.getLaserShutterTtlName_(0),configHW_.getLaserShutterTtlName_(1),0);//Lasers off
         core_.setProperty(configHW_.getPhLampTtlName_(0),configHW_.getPhLampTtlName_(1),1);//Ph lamp on
         String camName =configHW_.getCamPhName_() ;
         double exposureTime = configHW_.getCamPhExposureMs_();
         if (configHW_.isCamConvertPhExposureToSec_()){//correct for stupid bug in the camera driver
            exposureTime /= 1000;
         }
         core_.setProperty("Core", "Camera", camName);
         core_.setProperty(camName,"Exposure",exposureTime);
         core_.waitForSystem(); 
         return acquireImage();
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
      
   } 
   /*
    * Acquire 1 image without showing it, from org.micromanager.slideexplorer.Hardware
    */
   private ImageProcessor acquireImage(){
        try {
            core_.snapImage();
            Object img = core_.getImage();
            return ImageUtils.makeProcessor(core_, img);
        } catch (Exception e) {
            ReportingUtils.logError(e);
            return null;
        }
   }

   private void processFilterImage(ImagePlus im) {
      if (im != null){// ie exclude case where grabCurrentImage failed to find an image
         FovFilterConfig fovFilterConf = configHW_.getFilterConf_();
         Rectangle roiRect= configHW_.getCamConf_()[0].getRectangle();
         fovFilter_ = new FovFilter(im, fovFilterConf,roiRect);
         skipCurrentFOV_ = !fovFilter_.isFovOk();
         System.out.println("N particle: "+ fovFilter_.getNParticle());
         System.out.println("Skip FOV : "+ skipCurrentFOV_);
      } 
   }
 
    
   private void  updateFilterIm(){
      if (filterIp_!=null){
         if (filterIm_ == null){
            filterIm_ = new ImagePlus();
            filterIm_.setProcessor(filterIp_);
            filterIm_.setTitle("Current aquisition: filter image");
            filterIm_.show();
         } else {
            filterIm_.setProcessor(filterIp_);
            filterIm_.updateAndDraw();
         } 
         IJ.run(filterIm_,"Enhance Contrast", "saturated=0.25");
      }
   }
   
   private void updateLabelIm(){
      labelIp_= fovFilter_.getLabeledImage();

      if (labelIm_ == null){
         labelIm_ = new ImagePlus();
         labelIm_.setProcessor(labelIp_);
         labelIm_.setTitle("current aquisition: labelled image");
         labelIm_.show();
      } else {
         labelIm_.setProcessor(labelIp_);
         labelIm_.updateAndDraw();
      }
   }
   
}

