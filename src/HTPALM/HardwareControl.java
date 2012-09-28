
package HTPALM;

import mmcorej.CMMCore;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.AcquisitionEngine;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HardwareControl {

   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   
   public HardwareControl(MMStudioMainFrame gui_){
      this.gui_ = gui_;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }
   
   public void setCurrentPosAsOrigin(){}
   public Double getCurrentX(){
      return 42.;
   }
   public Double getCurrentY(){
      return 43.;
   }
   public Integer getCurrentFovNum(){
      return 3;
   }
   public void gotoPrevFov(){}
   public void gotoNextFov(){}
   public void acquire1Fov(){}
   public void initializeAcquisition(){}
   public void acquireAll(){}
   public void abortAll(){}
   
   
}
