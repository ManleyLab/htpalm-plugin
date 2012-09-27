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
   //this stores all the HTPALM config options
   ConfigurationOptions config_=null;
   
   HTPALMDialog dlg;
   
   /****
    * Temporary test code for runAcq
    */
   public void testRunAcq(){
      Thread t = new Thread(new HTPALM_MainRunnable(core_,gui_,acq_));
      t.start();
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
      if (config_==null){
         config_ = new ConfigurationOptions();
      }
         
      if (dlg==null) {
          dlg = new HTPALMDialog(gui_,config_, false, this);
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
