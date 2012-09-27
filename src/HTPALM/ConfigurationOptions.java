/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

// use the simple framework to allow easy generation of POJO xml config file
import java.io.File;
import org.simpleframework.xml.*;

/**
 * All the configuration options for required to run HTPALM. 
 * @author seamus.holden@epfl.ch
 */
@Default
public class ConfigurationOptions {
   
   //Mosaic config
   double mosaicStartPosX_, mosaicStartPosY_, mosaicStepSizeX_, mosaicStepSizeY_;
   //Laser config
   double laserManualExPower_, laserManualActPower_;
   boolean laserControlIsAutomatic_;
   String laserExDacName_, laserExTtlName_;
   private String laserActDacName_;
   String  laserActTtlName_, laserShutterTtlName_,phLampTtlName_;
   //FOV analysis/segmenation config        
   boolean fovAnalysis_excludeBadFov_;
   //Camera config
   String camEmccdName_, camPhName_;
   double camEmccdExposureMs_,camPhExposureMs_,camPhDelayMs_;
   boolean camConvertPhExposureToSec_;
   //File config
   String fileAcqFolder_, fileBaseName_;
   
   ConfigurationOptions(){
      this.loadDefaultConfig();
   }
   

   private void loadDefaultConfig(){
      String configPath = "HTPALM_Config.xml";
      File f = new File(configPath);
      //try to load the default settings
      if (f.exists()){
         
      }
      else{
      //if they dont exist, use these default settings, and save them:
         ConfigurationOptions config = new ConfigurationOptions();
         config.assignDefaultConfig();
         config.saveConfig(configPath);
      }
      
   }

   private void loadConfig(String fpath){
      
   }

   private void saveConfig(String fpath){
      
   }

   private void assignDefaultConfig(){
      mosaicStartPosX_=0.0;
      mosaicStartPosY_=0.0;
      mosaicStepSizeX_=0.0;
      mosaicStepSizeY_=0.0;
      //Laser config
      laserManualExPower_ = 3.0;
      laserManualActPower_ = 0.3;
      laserControlIsAutomatic_ = false;
      laserExDacName_= "\"AOTF-DAC1\",\"Volts\"";
      laserExTtlName_= "\"AOTF-Switch\",\"TTL1\"";
      laserActDacName_= "\"AOTF-DAC3\",\"Volts\"";
      laserActTtlName_= "\"AOTF-Switch\",\"TTL3\"";
      laserShutterTtlName_ = "\"TTL-Switch\",\"TTL2\"";
      phLampTtlName_= "\"TTL-Switch\",\"TTL3\"";
      //FOV analysis/segmenation config        
      fovAnalysis_excludeBadFov_ = false;
      //Camera config
      camEmccdName_= "Evolve128";
      camPhName_ = "TIS_DCAM";
      camEmccdExposureMs_=30;
      camPhExposureMs_=200;
      camPhDelayMs_=500;
      camConvertPhExposureToSec_=true;
      //File config
      fileAcqFolder_="."; 
      fileBaseName_="HTPALM_Acq";
   }

   //Just the getters and setters below here

   /**
    * @return the mosaicStartPosX_
    */
   public double getMosaicStartPosX() {
      return mosaicStartPosX_;
   }

   /**
    * @param mosaicStartPosX_ the mosaicStartPosX_ to set
    */
   public void setMosaicStartPosX(double mosaicStartPosX_) {
      this.mosaicStartPosX_ = mosaicStartPosX_;
   }

   /**
    * @return the mosaicStartPosY_
    */
   public double getMosaicStartPosY() {
      return mosaicStartPosY_;
   }

   /**
    * @param mosaicStartPosY_ the mosaicStartPosY_ to set
    */
   public void setMosaicStartPosY(double mosaicStartPosY_) {
      this.mosaicStartPosY_ = mosaicStartPosY_;
   }

   /**
    * @return the mosaicStepSizeX_
    */
   public double getMosaicStepSizeX() {
      return mosaicStepSizeX_;
   }

   /**
    * @param mosaicStepSizeX_ the mosaicStepSizeX_ to set
    */
   public void setMosaicStepSizeX(double mosaicStepSizeX_) {
      this.mosaicStepSizeX_ = mosaicStepSizeX_;
   }

   /**
    * @return the mosaicStepSizeY_
    */
   public double getMosaicStepSizeY() {
      return mosaicStepSizeY_;
   }

   /**
    * @param mosaicStepSizeY_ the mosaicStepSizeY_ to set
    */
   public void setMosaicStepSizeY(double mosaicStepSizeY_) {
      this.mosaicStepSizeY_ = mosaicStepSizeY_;
   }

   /**
    * @return the laserManualExPower_
    */
   public double getLaserManualExPower() {
      return laserManualExPower_;
   }

   /**
    * @param laserManualExPower_ the laserManualExPower_ to set
    */
   public void setLaserManualExPower(double laserManualExPower_) {
      this.laserManualExPower_ = laserManualExPower_;
   }

   /**
    * @return the laserManualActPower_
    */
   public double getLaserManualActPower() {
      return laserManualActPower_;
   }

   /**
    * @param laserManualActPower_ the laserManualActPower_ to set
    */
   public void setLaserManualActPower(double laserManualActPower_) {
      this.laserManualActPower_ = laserManualActPower_;
   }

   /**
    * @return the laserControlIsAutomatic_
    */
   public boolean isLaserControlIsAutomatic() {
      return laserControlIsAutomatic_;
   }

   /**
    * @param laserControlIsAutomatic_ the laserControlIsAutomatic_ to set
    */
   public void setLaserControlIsAutomatic(boolean laserControlIsAutomatic_) {
      this.laserControlIsAutomatic_ = laserControlIsAutomatic_;
   }

   /**
    * @return the laserExDacName_
    */
   public String getLaserExDacName() {
      return laserExDacName_;
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName(String laserExDacName_) {
      this.laserExDacName_ = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String getLaserExTtlName() {
      return laserExTtlName_;
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName(String laserExTtlName_) {
      this.laserExTtlName_ = laserExTtlName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String getLaserActTtlName() {
      return laserActTtlName_;
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName(String laserActTtlName_) {
      this.laserActTtlName_ = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String getLaserShutterTtlName() {
      return laserShutterTtlName_;
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName(String laserShutterTtlName_) {
      this.laserShutterTtlName_ = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String getPhLampTtlName() {
      return phLampTtlName_;
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName(String phLampTtlName_) {
      this.phLampTtlName_ = phLampTtlName_;
   }

   /**
    * @return the fovAnalysis_excludeBadFov_
    */
   public boolean isExcludeBadFov() {
      return fovAnalysis_excludeBadFov_;
   }

   /**
    * @param fovAnalysis_excludeBadFov_ the fovAnalysis_excludeBadFov_ to set
    */
   public void setExcludeBadFov(boolean excludeBadFov_) {
      this.fovAnalysis_excludeBadFov_ = excludeBadFov_;
   }

   /**
    * @return the camEmccdName_
    */
   public String getCamEmccdName() {
      return camEmccdName_;
   }

   /**
    * @param camEmccdName_ the camEmccdName_ to set
    */
   public void setCamEmccdName(String camEmccdName_) {
      this.camEmccdName_ = camEmccdName_;
   }

   /**
    * @return the camPhName_
    */
   public String getCamPhName() {
      return camPhName_;
   }

   /**
    * @param camPhName_ the camPhName_ to set
    */
   public void setCamPhName(String camPhName_) {
      this.camPhName_ = camPhName_;
   }

   /**
    * @return the camEmccdExposureMs_
    */
   public double getCamEmccdExposureMs() {
      return camEmccdExposureMs_;
   }

   /**
    * @param camEmccdExposureMs_ the camEmccdExposureMs_ to set
    */
   public void setCamEmccdExposureMs(double camEmccdExposureMs_) {
      this.camEmccdExposureMs_ = camEmccdExposureMs_;
   }

   /**
    * @return the camPhExposureMs_
    */
   public double getCamPhExposureMs() {
      return camPhExposureMs_;
   }

   /**
    * @param camPhExposureMs_ the camPhExposureMs_ to set
    */
   public void setCamPhExposureMs(double camPhExposureMs_) {
      this.camPhExposureMs_ = camPhExposureMs_;
   }

   /**
    * @return the camPhDelayMs_
    */
   public double getCamPhDelayMs() {
      return camPhDelayMs_;
   }

   /**
    * @param camPhDelayMs_ the camPhDelayMs_ to set
    */
   public void setCamPhDelayMs(double camPhDelayMs) {
      this.camPhDelayMs_ = camPhDelayMs;
   }

   /**
    * @return the camConvertPhExposureToSec_
    */
   public boolean isConvertPhExposureToSec() {
      return camConvertPhExposureToSec_;
   }

   /**
    * @param camConvertPhExposureToSec_ the camConvertPhExposureToSec_ to set
    */
   public void setCamConvertPhExposureToSec(boolean camConvertPhExposureToSec_) {
      this.camConvertPhExposureToSec_ = camConvertPhExposureToSec_;
   }

   /**
    * @return the fileAcqFolder_
    */
   public String getFileAcqFolder() {
      return fileAcqFolder_;
   }

   /**
    * @param fileAcqFolder_ the fileAcqFolder_ to set
    */
   public void setFileAcqFolder(String fileAcqFolder_) {
      this.fileAcqFolder_ = fileAcqFolder_;
   }

   /**
    * @return the fileBaseName_
    */
   public String getFileBaseName() {
      return fileBaseName_;
   }

   /**
    * @param fileBaseName_ the fileBaseName_ to set
    */
   public void setFileBaseName(String fileBaseName) {
      this.fileBaseName_ = fileBaseName;
   }

   /**
    * @return the laserActDacName_
    */
   public String getLaserActDacName() {
      return laserActDacName_;
   }

   /**
    * @param laserActDacName_ the laserActDacName_ to set
    */
   public void setLaserActDacName(String laserActDacName_) {
      this.laserActDacName_ = laserActDacName_;
   }

}
