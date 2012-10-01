
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
   private int currentFovNum_;
   private double currentFovPosX_,currentFovPosY_;
   private boolean skipCurrentFOV_;
   
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
      config_.saveConfig(metadata_.getConfigFileName());//save the config in the acquisition folder
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
         //TODO 
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
      //note - in manual mode we ignore skipCurrentFOV_

      //update the metadata
      boolean phPreAcquired=true,phPostAcquired=true;// for now this is always the case
      int[] flCh={0};// for now this is always the case
      metadata_.addNewAcquisition(currentFovNum_, phPreAcquired, phPostAcquired, flCh);
      //TODO - run the acquisition
      //save the metadata
   }
   public void acquireAll(){
      //for (int ii;ii< config_.mosaicNFov_){

      //TODO - run the acquisition
      //}
   }
   public void abortAll(){
      //TODO - shut down running acquisition
      //TODO - if an acquisition was interupted, delete the metadata for that acquisition
   }

   
}
