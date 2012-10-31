/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.bactcounting;

import org.micromanager.api.ScriptInterface;
import mmcorej.CMMCore;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.AcquisitionEngine;
import org.micromanager.api.ScriptInterface;
import org.micromanager.utils.ReportingUtils;

/**
 * @author seamus.holden@epfl.ch
 * Simple hello world plugin for Micromanager
 * Note: the build.xml file is modified so that every time you do 
 *    "Run"> "Clean and build main project",
 * the JAR of your plugin is copied to the MM plugins folder.
 * Then, when you run MM using
 *    "Run">"Run main project"
 * you should see "Hello world plugin" listed in the MM plugins menu
 */
public class TestSegMMPlugin implements org.micromanager.api.MMPlugin{
   public static String menuName = "Test bact segmentation";
   public static String tooltipDescription = "test";
   private CMMCore core_;
   private MMStudioMainFrame gui_;
   private AcquisitionEngine acq_;
   TestSegJDialog dlg_ = null;
            
   @Override
   public void dispose() {
      /*
       * you can put things that need to be run on shutdown here
       * note: if you launch a JDialog from the plugin using show(), shutdown of the dialog will not automatically call dispose()
       * You will need to add a call to dispose() from the formWindowClosing() method of your JDialog.
       */
   }

   @Override
   public void setApp(ScriptInterface app) {
      gui_ = (MMStudioMainFrame) app;
      core_ = app.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
   }

   @Override
   public void show() {//this is run when the menu listing for the plugin is clicked

      if (dlg_==null) {
          dlg_ = new TestSegJDialog(gui_,false, this);
      }
      dlg_.setVisible(true);
   }

   @Override
   public void configurationChanged() {
      // TODO Auto-generated method stub
   }

   @Override
   public String getDescription() {
      return "";
   }

   @Override
   public String getInfo() {
      return "Info:";
   }

   @Override
   public String getVersion() {
      return "1.0";
   }

   @Override
   public String getCopyright() {
      return "(C) 2012 S Holden";
   }
   

}
