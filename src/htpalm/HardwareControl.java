
package htpalm;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.apache.commons.beanutils.BeanUtils;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.AcquisitionEngine;
import org.micromanager.utils.ReportingUtils;

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
      mosaic_ = new SpiralMosaic(config_.mosaicStartPosX_,config_.mosaicStartPosY_,config_.mosaicStepSizeX_,config_.mosaicStepSizeY_,config_.mosaicNFov);
      //set up all the correct file names and metadata  - how is skip fov going to work for saving? - save after every acquisition.
      metadata_ = new HtpalmMetadata(config_,mosaic_);
      //save a copy of the config in the acquisition folder
   }
   
   public void setCurrentPosAsOrigin(){
      //TODO
   }
   
   public Integer getCurrentFovNum(){
      //TODO
      return 3;//TMP
   }
   public void gotoPrevFov(){
      //TODO
   }
   public void gotoNextFov(){
      //TODO
   }
   public void acquire1Fov(){
      //TODO
   }
   public void acquireAll(){
      //TODO
   }
   public void abortAll(){
      //TODO
   }

   
}
