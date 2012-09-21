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
public class HTPALM implements org.micromanager.api.MMPlugin {

   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   
   HTPALMDialog dlg;
   
   /****
    * Temporary test code for runAcq
    */
   public void testRunAcq(){
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
         Logger.getLogger(HTPALM.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      // Running this on Beanshell works ok
      RunAcq.run(gui_,camName,acqName,rootDirName,numFrames,intervalMs,exposure); 
   }

   
   @Override
   public void dispose() {
   }

   @Override
   public void setApp(ScriptInterface app) {
      gui_ = (MMStudioMainFrame) app;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }

   @Override
   public void show() {
      if (dlg==null) {
          dlg = new HTPALMDialog(gui_, false, this);
      }
      dlg.setVisible(true);
   }

   @Override
   public void configurationChanged() {
      // TODO Check if configuration is ok
   }

   @Override
   public String getDescription() {
      return "Automated PALM for microbiology";
   }

   @Override
   public String getInfo() {
      return "Automated PALM for microbiology";
   }

   @Override
   public String getVersion() {
      return "0.1";
   }

   @Override
   public String getCopyright() {
      return "(C) 2012 Seamus Holden, Thomas Pengo, EPFL, Switzerland";
   }
   
}
