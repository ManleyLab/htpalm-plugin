
package com.wordpress.seamusholden.htpalm;

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
import static com.wordpress.seamusholden.htpalm.Debug.DEBUG;
import java.io.File;
import org.micromanager.utils.MMException;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HardwareControl {

   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   ConfigurationOptions config_ = new ConfigurationOptions();
   SpiralMosaic mosaic_;
   HtpalmMetadata metadata_;
   int currentFovNum_;
   double currentFovPosX_,currentFovPosY_;
   boolean skipCurrentFOV_;
   Thread currentAcqThread_=null;
   boolean isInitialized_ = false;
   
   public HardwareControl(MMStudioMainFrame gui_){
      this.gui_ = gui_;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }
   
   public void initializeAcquisition(){
      config_.loadDefaultConfig();
      initializeHardwareControl();
   }
   public void initializeAcquisition(String fpath){
      config_.loadConfig(fpath);
      initializeHardwareControl();
   }
   public void initializeAcquisition(ConfigurationOptions newConfig){
      try {
         BeanUtils.copyProperties(config_,newConfig);//copy the properties of newConfig into config_, ready to run acquisition
         initializeHardwareControl();
         isInitialized_ = true;
      } catch (IllegalAccessException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      } catch (InvocationTargetException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      }
   }

   private void initializeHardwareControl(){
      mosaic_ = new SpiralMosaic(config_.mosaicStartPosX_,config_.mosaicStartPosY_,config_.mosaicStepSizeX_,config_.mosaicStepSizeY_, config_.getMosaicNFov());
      //set up all the correct file names and metadata  - how is skip fov going to work for saving? - save after every acquisition.
      metadata_ = new HtpalmMetadata(config_,mosaic_);
      //save a copy of the config in the acquisition folder
      String configPath =metadata_.acqFolder_+File.separator+metadata_.getConfigFileName();
      config_.saveConfig(configPath);//save the config in the acquisition folder
      gotoFOV(0);
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
      if (config_.fovAnalysis_excludeBadFov_){
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
      if (currentFovNum_<config_.getMosaicNFov()){
         gotoFOV(currentFovNum_+1);
      }
   }

   public void acquire1Fov(){
      //TODO  - check we are not about to overwrite an existing acquisition
      //TODO - set the laser powers, ttls
      if (!isInitialized_){
            ReportingUtils.showError("Error: Htpalm acquisition must be initialized before starting acquisition!");
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
      //TODO  - check we are not about to overwrite an existing acquisition
      //TODO - set the laser powers, ttls

      if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
         ReportingUtils.showError("Error: Cannot start new acquisition while a previous acquisition is running!");
      } else {
         currentAcqThread_ = new Thread(new AcquireAllFov(this));
         currentAcqThread_.start();
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
            //note - in manual mode we ignore skipCurrentFOV_

            //sort out  the lasers
            if (config_.laserControlIsAutomatic_){
               //TODO - Autolase
            }
            else {
               //Make sure the laser powers etc are right
               core_.setProperty(config_.getLaserExDacName(0),config_.getLaserExDacName(1),config_.laserManualExPower_);
               core_.setProperty(config_.getLaserExTtlName(0),config_.getLaserExTtlName(1),1);
               core_.setProperty(config_.getLaserActDacName(0),config_.getLaserActDacName(1),config_.laserManualActPower_);
               core_.setProperty(config_.getLaserActTtlName(0),config_.getLaserActTtlName(1),1);
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

         } catch (Exception ex) {
            throw new RuntimeException(ex);
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
            //sort out  the lasers
            if (config_.laserControlIsAutomatic_){
                     //TODO - Autolase
            }
            else {
               //Make sure the laser powers etc are right
               core_.setProperty(config_.getLaserExDacName(0),config_.getLaserExDacName(1),config_.laserManualExPower_);
               core_.setProperty(config_.getLaserExTtlName(0),config_.getLaserExTtlName(1),1);
               core_.setProperty(config_.getLaserActDacName(0),config_.getLaserActDacName(1),config_.laserManualActPower_);
               core_.setProperty(config_.getLaserActTtlName(0),config_.getLaserActTtlName(1),1);
               core_.waitForSystem();
            } 

            //turn autoshutter on (and findout the original state so we can reset it after acquisition)
            boolean autoShutterOnBefore = core_.getAutoShutter();
            if (!autoShutterOnBefore){
               core_.setAutoShutter(true);
               core_.waitForSystem();
            }

            //aquire all the Fovs
            for (int ii=0;ii< control_.config_.mosaicNFov_;ii++){
               boolean phPreAcquire=true,phPostAcquire=true;// for now this is always the case
               int[] flCh={0};// for now, this is always the case
               control_.metadata_.addNewAcquisition(control_.currentFovNum_, phPreAcquire, phPostAcquire, flCh);
               control_.metadata_.saveMetadata();

               control_.gotoFOV(ii);
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
         core_.setProperty(config_.getLaserShutterTtlName(0),config_.getLaserShutterTtlName(1),0);//Lasers off
         core_.setProperty(config_.getPhLampTtlName(0),config_.getPhLampTtlName(1),1);//Ph lamp on
         core_.waitForSystem();
   
         //run the acquisition
         String camName = config_.camPhName_;
         String rootDirName = config_.fileAcqFolder_;
         //TODO add an acquisition name!
         int numFrames = 1;
         double intervalMs = 0;
         double exposureTime = config_.camPhExposureMs_;
         double delayTime = config_.camPhDelayMs_;
         boolean closeOnExit = true;
         acquire1Movie(camName, acqName, rootDirName, numFrames, intervalMs, exposureTime,delayTime, closeOnExit);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }
   
   private void acquire1Fl(String acqName){
      try {
         //set the ttls - note this assumes the auto shutter is on and working happily
         core_.setProperty(config_.getLaserShutterTtlName(0),config_.getLaserShutterTtlName(1),1);//Lasers ON
         core_.setProperty(config_.getPhLampTtlName(0),config_.getPhLampTtlName(1),0);//Ph lamp OFF
         core_.waitForSystem();
         
         //run the acquisition
         String camName = config_.camEmccdName_;
         String rootDirName = config_.fileAcqFolder_;
         int numFrames = config_.getCamEmccdNumFrames();
         double intervalMs = 0;
         double exposureTime = config_.camEmccdExposureMs_;
         double delayTime = config_.camPhDelayMs_;
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
   private String getCurrentAcqName(String[] newAcqNames, String[] oldAcqNames) throws Exception{
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
         throw new Exception("Finding current acquisition name failed - no new names ");// throw runtime exception
		}      
		else if (nCurrent > 1){
         throw new Exception("Finding current acquisition name failed - multiple new names ");// throw runtime exception
		}
      else {
          currentAcqName = newAcqNames[iCurrent];
 		}           
      return currentAcqName;
   } 
}
