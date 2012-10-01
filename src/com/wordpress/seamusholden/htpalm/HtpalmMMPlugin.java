/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.seamusholden.htpalm;

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
public class HtpalmMMPlugin implements org.micromanager.api.MMPlugin {

   public static String menuName = "HTPALM";
   public static String tooltipDescription= "Automatic PALM acquisition plugin";
           
   CMMCore core_;
   MMStudioMainFrame gui_;
   AcquisitionEngine acq_ ;
   //this stores all the HtpalmMMPlugin config options
   ConfigurationOptions config_=null;
   HtpalmDialog dlg_=null;
   HardwareControl control_=null;
   
   ///****
   // * Temporary test code for runAcq
   // */
   //public void testRunAcq(){
   //   Thread t = new Thread(new HTPALM_MainRunnable(core_,gui_,acq_));
   //   t.start();
   //}

   
   @Override
   public void dispose() {
      config_=null;
      control_=null;
      dlg_=null;
      //TODO ensure current acquisition is shut down
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
         config_.initialize();
      }

      if (control_==null){
         control_= new HardwareControl(gui_);
      }
         
      if (dlg_==null) {
          dlg_ = new HtpalmDialog(gui_,false,config_,control_,  this);
      }
      dlg_.setVisible(true);
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
