
package HTPALM;

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
   
   public HardwareControl(MMStudioMainFrame gui_){
      this.gui_ = gui_;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }
   
   public void initializeAcquisition(){
      config_.loadDefaultConfig();
   }
   public void initializeAcquisition(String fpath){
      config_.loadConfig(fpath);
   }
   public void initializeAcquisition(ConfigurationOptions newConfig){
      try {
         BeanUtils.copyProperties(config_,newConfig);//copy the properties of newConfig into config_, ready to run acquisition
      } catch (IllegalAccessException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      } catch (InvocationTargetException ex) {
         ReportingUtils.logError(ex, "Failed to copy config object");
      }
   }
   
   public void setCurrentPosAsOrigin(){
      //TODO
   }
   
   public Integer getCurrentFovNum(){
      //TODO
      return 3;
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
