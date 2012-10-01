
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
      if (!isInitialized_){
            ReportingUtils.showError("Error: Htpalm acquisition must be initialized before starting acquisition!");
      } 
      else { 
         if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
            ReportingUtils.showError("Error: Cannot start new acquisition while a previous acquisition is running!");
         } 
         else {
            currentAcqThread_ = new Thread(new Acquire1Fov(this));
            currentAcqThread_.start();
         }
      }
   }
   
   public void acquireAll(){
      if (!isInitialized_){
            ReportingUtils.showError("Error: Htpalm acquisition must be initialized before starting acquisition!");
      } 
      else { 
         if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
            ReportingUtils.showError("Error: Cannot start new acquisition while a previous acquisition is running!");
         }
         else {
            currentAcqThread_ = new Thread(new AcquireAllFov(this));
            currentAcqThread_.start();
         }
      }
   }
   
   public void abortAll(){
      if ( !(currentAcqThread_==null) && currentAcqThread_.isAlive()){
         currentAcqThread_.interrupt();
      }
      //TODO - if an acquisition was interupted, delete the metadata for that acquisition
   }
}

class Acquire1Fov implements Runnable{
   //TODO setup proper interrupt to shut down acquisition
   HardwareControl control_;
   Acquire1Fov(HardwareControl control) {
      this.control_= control;
   }
   public void run() {
      //note - in manual mode we ignore skipCurrentFOV_

      //update the metadata
      boolean phPreAcquired=true,phPostAcquired=true;// for now this is always the case
      int[] flCh={0};// for now this is always the case
      control_.metadata_.addNewAcquisition(control_.currentFovNum_, phPreAcquired, phPostAcquired, flCh);
      control_.metadata_.saveMetadata();

      // goto FOV - should be there already but just in case
      control_.gotoFOV(control_.currentFovNum_);
      //TODO - run the acquisition
      if (DEBUG){
         System.out.println("Acquiring FOV "+control_.currentFovNum_);
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
      for (int ii=0;ii< control_.config_.mosaicNFov_;ii++){
         boolean phPreAcquired=true,phPostAcquired=true;// for now this is always the case
         int[] flCh={0};// for now this is always the case
         control_.metadata_.addNewAcquisition(control_.currentFovNum_, phPreAcquired, phPostAcquired, flCh);
         control_.metadata_.saveMetadata();

         control_.gotoFOV(ii);
         if (control_.skipCurrentFOV_==false){
            //TODO - run the acquisition
            if (DEBUG){
               System.out.println("Acquiring FOV "+control_.currentFovNum_);
            }
         }
      }
   }

}