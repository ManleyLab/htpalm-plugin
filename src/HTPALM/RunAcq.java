/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.gui.ImageCanvas;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.ScriptInterface;// may need to change this if its not run from beanshell
import org.micromanager.api.AcquisitionEngine;
import mmcorej.CMMCore;
import org.micromanager.utils.MMException;
import org.micromanager.utils.ReportingUtils;
/**
 *
 * @author seamus.holden@epfl.ch
 */
 public class RunAcq{

   public static void run(ScriptInterface app, String camName, String acqName, String rootDirName, int numFrames, double intervalMs, double exposureTime){
      ScriptInterface gui_ ;
      CMMCore core_ ;
      AcquisitionEngine acq_ ;

      gui_ = app;
      core_ = gui_.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
  
      //***************************
      //acquire

      try{
         core_.setProperty("Core", "Camera", camName);
         core_.setProperty(camName,"Exposure",exposureTime);
         acq_.setRootName(rootDirName);
         acq_.setFrames(numFrames,intervalMs);
         core_.waitForSystem();
         acq_.acquire();
         
         while(!acq_.isAcquisitionRunning())//wait until acq_ is started
         {	Thread.sleep(50);}
         while(!acq_.isFinished())//wait until acq_ is finished
         {	Thread.sleep(50);}    
         
         core_.waitForSystem();
         Thread.sleep(1000);//give it time to sort itself out
         ImageWindow win = IJ.getImage().getWindow();
         win.close();//this closes the acquisition gui_ window
      }
      catch(InterruptedException e){}
      catch (MMException mex) {
         Logger.getLogger(RunAcq.class.getName()).log(Level.SEVERE, null, mex);
      }
      catch (Exception ex) {
         Logger.getLogger(RunAcq.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
  
}
