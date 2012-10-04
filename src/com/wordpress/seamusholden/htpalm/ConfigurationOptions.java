
package com.wordpress.seamusholden.htpalm;

// use the simple framework to allow easy generation of POJO xml config file
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static com.wordpress.seamusholden.htpalm.Debug.DEBUG;

/**
 * All the configuration options for required to run HTPALM. 
 * @author seamus.holden@epfl.ch
 */
@Default
public class ConfigurationOptions {
   
   static final String configPathDefault_ = "HTPALM_Config.xml";

   
   //Mosaic config
   double mosaicStartPosX_, mosaicStartPosY_, mosaicStepSizeX_, mosaicStepSizeY_;
   int mosaicNFov_;
   //FOV analysis/segmenation config        
   boolean fovAnalysis_excludeBadFov_;
   //Camera config
   String camEmccdName_, camPhName_;
   double camEmccdExposureMs_,camPhExposureMs_,camPhDelayMs_;
   int camEmccdNumFrames_;
   private boolean camConvertPhExposureToSec_;
   //Laser config
   double laserManualExPower_, laserManualActPower_;
   boolean laserControlIsAutomatic_;
   String[] laserExDacName_;
   String[] laserExTtlName_;
   String[] laserActDacName_;
   String[] laserActTtlName_;
   String[] laserShutterTtlName_;
   String[] phLampTtlName_;
   //File config
   String fileAcqFolder_, fileBaseName_;
   
   public ConfigurationOptions(){
   }

   
   public void initialize(){
      this.loadDefaultConfig();
   }
   

   public void loadDefaultConfig(){
      File f = new File(configPathDefault_);
      //try to load the default settings
      if (f.exists()){
         loadConfig(configPathDefault_);
      }
      else{
      //if they dont exist, use these default settings, and save them:
         assignDefaultConfig();
         saveConfig(configPathDefault_);
      }
      
   }

   public void loadConfig(String fpath){
      File f = new File(fpath);
      Serializer serializer = new Persister();
      ConfigurationOptions config;
      try {
         config = serializer.read(ConfigurationOptions.class,f);
         BeanUtils.copyProperties(this,config);//copy the properties of config into the this
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      } catch (InvocationTargetException ex) {
         throw new RuntimeException(ex);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   public void saveConfig(String fpath){
      File f = new File(fpath);
      Serializer serializer = new Persister();
      try {
         serializer.write(this,f);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   public void saveConfig(){// if no path is supplied use default path
      saveConfig(configPathDefault_);
   }
   
   private void assignDefaultConfig(){
      mosaicStartPosX_=0.0;
      mosaicStartPosY_=0.0;
      mosaicStepSizeX_=17.0;
      mosaicStepSizeY_=17.0;
      mosaicNFov_ = 100;
      //Laser config
      laserManualExPower_ = 3.0;
      laserManualActPower_ = 0.3;
      laserControlIsAutomatic_ = false;
      setLaserExDacName(new String[]{"AOTF-DAC1","Volts"});
      setLaserExTtlName(new String[]{"AOTF-Switch","TTL1"});
      setLaserActDacName(new String[]{"AOTF-DAC3","Volts"});
      setLaserActTtlName(new String[]{"AOTF-Switch","TTL3"});
      setLaserShutterTtlName(new String[]{"TTL-Switch","TTL2"});
      setPhLampTtlName(new String[]{"TTL-Switch","TTL3"});
      //FOV analysis/segmenation config        
      fovAnalysis_excludeBadFov_ = false;
      //Camera config
      camEmccdName_= "Evolve128";
      camPhName_ = "TIS_DCAM";
      camEmccdExposureMs_=30;
      camPhExposureMs_=200;
      camPhDelayMs_=500;
      camEmccdNumFrames_ = 10000;
      camConvertPhExposureToSec_=true;
      //File config
      fileAcqFolder_="."; 
      fileBaseName_="HTPALM_Acq";
   }

   //Just the getters and setters below here
   
   /**
    * @return the configPathDefault_
    */
   public static String getDefaultConfigPath() {
      return configPathDefault_;
   }
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
   public String getLaserExDacName(int idx) {
      return getLaserExDacName()[idx];
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName(String laserExDacName_, int idx) {
      this.getLaserExDacName()[idx] = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String getLaserExTtlName(int idx) {
      return getLaserExTtlName()[idx];
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName(String laserExTtlName_, int idx) {
      this.getLaserExTtlName()[idx] = laserExTtlName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String getLaserActTtlName(int idx) {
      return getLaserActTtlName()[idx];
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName(String laserActTtlName_, int idx) {
      this.getLaserActTtlName()[idx] = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String getLaserShutterTtlName(int idx) {
      return getLaserShutterTtlName()[idx];
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName(String laserShutterTtlName_, int idx) {
      this.getLaserShutterTtlName()[idx] = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String getPhLampTtlName(int idx) {
      return getPhLampTtlName()[idx];
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName(String phLampTtlName_, int idx) {
      this.getPhLampTtlName()[idx] = phLampTtlName_;
   }

   /**
    * @return the laserActDacName_
    */
   public String getLaserActDacName(int idx) {
      return getLaserActDacName()[idx];
   }

   /**
    * @param laserActDacName_ the laserActDacName_ to set
    */
   public void setLaserActDacName(String laserActDacName_, int idx) {
      this.getLaserActDacName()[idx] = laserActDacName_;
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
    * @return the mosaicNFov_
    */
   public int getMosaicNFov() {
      return mosaicNFov_;
   }

   /**
    * @param mosaicNFov_ the mosaicNFov_ to set
    */
   public void setMosaicNFov(int mosaicNFov_) {
      this.mosaicNFov_ = mosaicNFov_;
   }

   /**
    * @return the laserExDacName_
    */
   public String[] getLaserExDacName() {
      return laserExDacName_;
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName(String[] laserExDacName_) {
      this.laserExDacName_ = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String[] getLaserExTtlName() {
      return laserExTtlName_;
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName(String[] laserExTtlName_) {
      this.laserExTtlName_ = laserExTtlName_;
   }

   /**
    * @return the laserActDacName_
    */
   public String[] getLaserActDacName() {
      return laserActDacName_;
   }

   /**
    * @param laserActDacName_ the laserActDacName_ to set
    */
   public void setLaserActDacName(String[] laserActDacName_) {
      this.laserActDacName_ = laserActDacName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String[] getLaserActTtlName() {
      return laserActTtlName_;
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName(String[] laserActTtlName_) {
      this.laserActTtlName_ = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String[] getLaserShutterTtlName() {
      return laserShutterTtlName_;
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName(String[] laserShutterTtlName_) {
      this.laserShutterTtlName_ = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String[] getPhLampTtlName() {
      return phLampTtlName_;
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName(String[] phLampTtlName_) {
      this.phLampTtlName_ = phLampTtlName_;
   }

   /**
    * @return the camEmccdNumFrames_
    */
   public int getCamEmccdNumFrames() {
      return camEmccdNumFrames_;
   }

   /**
    * @param camEmccdNumFrames_ the camEmccdNumFrames_ to set
    */
   public void setCamEmccdNumFrames(int camEmccdNumFrames_) {
      this.camEmccdNumFrames_ = camEmccdNumFrames_;
   }

   /**
    * @return the camConvertPhExposureToSec_
    */
   public boolean isCamConvertPhExposureToSec() {
      return camConvertPhExposureToSec_;
   }

}
