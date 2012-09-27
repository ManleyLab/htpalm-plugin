/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.ScriptInterface;
import org.micromanager.api.AcquisitionEngine;
import org.micromanager.utils.MMScriptException;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HTPALM_MainRunnable implements Runnable{
   
   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   
   HTPALM_MainRunnable(CMMCore core_, MMStudioMainFrame gui_, AcquisitionEngine acq_){
      this.core_ = core_;
      this.gui_=gui_;
      this.acq_ =acq_;
   }
   
   public void run(){
        //param
      String camName =  "DCam";
      
      // file locations
      String acqName = "test-acq";
      String rootDirName = "F:/seamus/demoAcqFolder";
      
      int numFrames = 50;
      double intervalMs = 0;
      double exposure=100;
      
      gui_.closeAllAcquisitions();
      try {
         gui_.clearMessageWindow();
      } catch (MMScriptException ex) {
         Logger.getLogger(HTPALM_MMPlugin.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      // Running this on Beanshell works ok
      boolean closeOnExit=true;
      RunAcq.run(gui_,camName,acqName,rootDirName,numFrames,intervalMs,exposure,closeOnExit); 
   }
}
