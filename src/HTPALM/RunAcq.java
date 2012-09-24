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
import org.micromanager.MMStudioMainFrame;
//import org.micromanager.api.ScriptInterface;// may need to change this if its not run from beanshell
import org.micromanager.api.AcquisitionEngine;
import mmcorej.CMMCore;
import org.micromanager.utils.MMException;
import org.micromanager.utils.ReportingUtils;
import org.micromanager.acquisition.MMAcquisition;
import java.util.regex.*;

/**
 *
 * @author seamus.holden@epfl.ch
 */
 public class RunAcq {

   public static void run(MMStudioMainFrame app, String camName, String acqName, String rootDirName, int numFrames, double intervalMs, double exposureTime, boolean closeOnExit){ 
      
      MMStudioMainFrame gui_ ;
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
         acq_.setUpdateLiveWindow(true);
         core_.waitForSystem();
         String[] oldAcqNames=null, newAcqNames= null;
         String currentAcqName=null;
         oldAcqNames = gui_.getAcquisitionNames();
         acq_.acquire();
         
         //while(!acq_.isAcquisitionRunning())//wait until acq_ is started
         //{	Thread.sleep(50);}
         while(!acq_.isFinished()){
            if (newAcqNames== null){ 
               newAcqNames =gui_.getAcquisitionNames(); 
               currentAcqName = RunAcq.getCurrentAcqName(newAcqNames,oldAcqNames);
            }
            Thread.sleep(200);
         }  
         
         
         
         core_.waitForSystem();
         gui_.message("Acq finished");
         //RunAcq.sleep(1000);//give the PH cam time to sort itself out
         if (closeOnExit==true){
            gui_.closeAcquisitionImage5D(currentAcqName);
         }
         gui_.refreshGUI();
         
      }
      catch(InterruptedException e){}
      catch (MMException mex) {
         Logger.getLogger(RunAcq.class.getName()).log(Level.SEVERE, null, mex);
      }
      catch (Exception ex) {
         Logger.getLogger(RunAcq.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

   //  find the acquisition name matching Acq_NN where NN is the largest number
   private static String getCurrentAcqName(String[] newAcqNames, String[] oldAcqNames) throws Exception{
      String currentAcqName =null;
      int nOld,nNew,nCurrent,iCurrent;
      nOld = oldAcqNames.length;
      nNew = newAcqNames.length;
      nCurrent=0;//number of new names
      iCurrent = 0;
      
      //for each acq name in newAcqNames
      for (int ii=0;ii<nNew;ii++){
         //compare to all memebers of old acqNames, check if it existed before
         int nMatches=0;
         for (int jj=0;jj<nOld;jj++){
            if (newAcqNames[ii].matches(oldAcqNames[jj])){
               nMatches++;
            }
         }
         if (nMatches==0){//if its new
            nCurrent++;
            iCurrent= ii;
         }
      }
      
      if (nCurrent ==0 ){
         throw new Exception("Finding current acquisition name failed - no new names ");// throw runtime exception
		}      
		else if (nCurrent > 1){
         throw new Exception("Finding current acquisition name failed - multiple new names ");// throw runtime exception
		}
      else {
          currentAcqName = newAcqNames[iCurrent];
 		}           
      return currentAcqName;
   } 
}
