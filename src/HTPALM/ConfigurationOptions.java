/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

// use the simple framework to allow easy generation of POJO xml config file
import org.simpleframework.xml.*;

/**
 * All the configuration options for required to run HTPALM. 
 * @author seamus.holden@epfl.ch
 */
@Default
public class ConfigurationOptions {
   
   //Mosaic config
   private double mosaicStartPosX_, mosaicStartPosY_, mosaicStepSizeX_, mosaicStepSizeY_;
   //Laser config
   private double laserManualExPower_, laserManualActPower_;
   private boolean laserControlIsAutomatic_;
   private String laserExDacName_, laserExTtlName_,laserActTtlName_, laserShutterTtlName_,phLampTtlName_;
   //FOV analysis/segmenation config        
   private boolean excludeBadFov_;
   //Camera config
   private String camEmccdName_, camPhName_;
   private double camEmccdExposureMs_,camPhExposureMs_,camPhDelayMs;
   private boolean camConvertPhExposureToSec_;
   //File config
   private String fileAcqFolder_, fileBaseName;
   
   ConfigurationOptions(){}

   /**
    * @return the mosaicStartPosX_
    */
   public double getMosaicStartPosX_() {
      return mosaicStartPosX_;
   }

   /**
    * @param mosaicStartPosX_ the mosaicStartPosX_ to set
    */
   public void setMosaicStartPosX_(double mosaicStartPosX_) {
      this.mosaicStartPosX_ = mosaicStartPosX_;
   }

   /**
    * @return the mosaicStartPosY_
    */
   public double getMosaicStartPosY_() {
      return mosaicStartPosY_;
   }

   /**
    * @param mosaicStartPosY_ the mosaicStartPosY_ to set
    */
   public void setMosaicStartPosY_(double mosaicStartPosY_) {
      this.mosaicStartPosY_ = mosaicStartPosY_;
   }

   /**
    * @return the mosaicStepSizeX_
    */
   public double getMosaicStepSizeX_() {
      return mosaicStepSizeX_;
   }

   /**
    * @param mosaicStepSizeX_ the mosaicStepSizeX_ to set
    */
   public void setMosaicStepSizeX_(double mosaicStepSizeX_) {
      this.mosaicStepSizeX_ = mosaicStepSizeX_;
   }

   /**
    * @return the mosaicStepSizeY_
    */
   public double getMosaicStepSizeY_() {
      return mosaicStepSizeY_;
   }

   /**
    * @param mosaicStepSizeY_ the mosaicStepSizeY_ to set
    */
   public void setMosaicStepSizeY_(double mosaicStepSizeY_) {
      this.mosaicStepSizeY_ = mosaicStepSizeY_;
   }

   /**
    * @return the laserManualExPower_
    */
   public double getLaserManualExPower_() {
      return laserManualExPower_;
   }

   /**
    * @param laserManualExPower_ the laserManualExPower_ to set
    */
   public void setLaserManualExPower_(double laserManualExPower_) {
      this.laserManualExPower_ = laserManualExPower_;
   }

   /**
    * @return the laserManualActPower_
    */
   public double getLaserManualActPower_() {
      return laserManualActPower_;
   }

   /**
    * @param laserManualActPower_ the laserManualActPower_ to set
    */
   public void setLaserManualActPower_(double laserManualActPower_) {
      this.laserManualActPower_ = laserManualActPower_;
   }

   /**
    * @return the laserControlIsAutomatic_
    */
   public boolean isLaserControlIsAutomatic_() {
      return laserControlIsAutomatic_;
   }

   /**
    * @param laserControlIsAutomatic_ the laserControlIsAutomatic_ to set
    */
   public void setLaserControlIsAutomatic_(boolean laserControlIsAutomatic_) {
      this.laserControlIsAutomatic_ = laserControlIsAutomatic_;
   }

   /**
    * @return the laserExDacName_
    */
   public String getLaserExDacName_() {
      return laserExDacName_;
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName_(String laserExDacName_) {
      this.laserExDacName_ = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String getLaserExTtlName_() {
      return laserExTtlName_;
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName_(String laserExTtlName_) {
      this.laserExTtlName_ = laserExTtlName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String getLaserActTtlName_() {
      return laserActTtlName_;
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName_(String laserActTtlName_) {
      this.laserActTtlName_ = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String getLaserShutterTtlName_() {
      return laserShutterTtlName_;
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName_(String laserShutterTtlName_) {
      this.laserShutterTtlName_ = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String getPhLampTtlName_() {
      return phLampTtlName_;
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName_(String phLampTtlName_) {
      this.phLampTtlName_ = phLampTtlName_;
   }

   /**
    * @return the excludeBadFov_
    */
   public boolean isExcludeBadFov_() {
      return excludeBadFov_;
   }

   /**
    * @param excludeBadFov_ the excludeBadFov_ to set
    */
   public void setExcludeBadFov_(boolean excludeBadFov_) {
      this.excludeBadFov_ = excludeBadFov_;
   }

   /**
    * @return the camEmccdName_
    */
   public String getCamEmccdName_() {
      return camEmccdName_;
   }

   /**
    * @param camEmccdName_ the camEmccdName_ to set
    */
   public void setCamEmccdName_(String camEmccdName_) {
      this.camEmccdName_ = camEmccdName_;
   }

   /**
    * @return the camPhName_
    */
   public String getCamPhName_() {
      return camPhName_;
   }

   /**
    * @param camPhName_ the camPhName_ to set
    */
   public void setCamPhName_(String camPhName_) {
      this.camPhName_ = camPhName_;
   }

   /**
    * @return the camEmccdExposureMs_
    */
   public double getCamEmccdExposureMs_() {
      return camEmccdExposureMs_;
   }

   /**
    * @param camEmccdExposureMs_ the camEmccdExposureMs_ to set
    */
   public void setCamEmccdExposureMs_(double camEmccdExposureMs_) {
      this.camEmccdExposureMs_ = camEmccdExposureMs_;
   }

   /**
    * @return the camPhExposureMs_
    */
   public double getCamPhExposureMs_() {
      return camPhExposureMs_;
   }

   /**
    * @param camPhExposureMs_ the camPhExposureMs_ to set
    */
   public void setCamPhExposureMs_(double camPhExposureMs_) {
      this.camPhExposureMs_ = camPhExposureMs_;
   }

   /**
    * @return the camPhDelayMs
    */
   public double getCamPhDelayMs() {
      return camPhDelayMs;
   }

   /**
    * @param camPhDelayMs the camPhDelayMs to set
    */
   public void setCamPhDelayMs(double camPhDelayMs) {
      this.camPhDelayMs = camPhDelayMs;
   }

   /**
    * @return the camConvertPhExposureToSec_
    */
   public boolean isConvertPhExposureToSec_() {
      return camConvertPhExposureToSec_;
   }

   /**
    * @param camConvertPhExposureToSec_ the camConvertPhExposureToSec_ to set
    */
   public void setCamConvertPhExposureToSec_(boolean camConvertPhExposureToSec_) {
      this.camConvertPhExposureToSec_ = camConvertPhExposureToSec_;
   }

   /**
    * @return the fileAcqFolder_
    */
   public String getFileAcqFolder_() {
      return fileAcqFolder_;
   }

   /**
    * @param fileAcqFolder_ the fileAcqFolder_ to set
    */
   public void setFileAcqFolder_(String fileAcqFolder_) {
      this.fileAcqFolder_ = fileAcqFolder_;
   }

   /**
    * @return the fileBaseName
    */
   public String getFileBaseName() {
      return fileBaseName;
   }

   /**
    * @param fileBaseName the fileBaseName to set
    */
   public void setFileBaseName(String fileBaseName) {
      this.fileBaseName = fileBaseName;
   }

}
