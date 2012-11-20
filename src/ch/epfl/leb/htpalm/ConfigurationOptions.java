
package ch.epfl.leb.htpalm;

// use the simple framework to allow easy generation of POJO xml config file
import ch.epfl.leb.htpalm.fovfilter.FovFilterConfig;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ch.epfl.leb.htpalm.Debug.DEBUG;
import ch.epfl.leb.htpalm.fovfilter.*;
import java.awt.Rectangle;
import org.simpleframework.xml.*;

/**
 * All the configuration options for required to run HTPALM. 
 * @author seamus.holden@epfl.ch
 */
@Root
public class ConfigurationOptions {
   
   @Element
   private static final String configPathDefault_ = "HTPALM_Config.xml";

   /**
    * @return the configPathDefault_
    */
   public static String getConfigPathDefault_() {
      return configPathDefault_;
   }

   
   //Mosaic config
   @Element
   private double mosaicStartPosX_;
   @Element
   private double mosaicStartPosY_;
   @Element
   private double mosaicStepSizeX_;
   @Element
   private double mosaicStepSizeY_;
   @Element
   private int mosaicNFov_;
   //FOV analysis/segmenation config        
   @Element
   private boolean fovAnalysis_excludeBadFov_;
   //Camera config
   @Element
   private String camEmccdName_;
   @Element
   private String camPhName_;
   @Element
   private double camEmccdExposureMs_;
   @Element
   private double camPhExposureMs_;
   @Element
   private double camPhDelayMs_;
   @Element
   private int camEmccdNumFrames_;
   @Element
   private boolean camConvertPhExposureToSec_;
   @Element
   private double camAutoFocusDelayTimeMs_;
   
   //Laser config
   @Element
   private double laserManualExPower_;
   @Element
   private double laserManualActPower_;
   @Element
   private boolean laserControlIsAutomatic_;
   @ElementArray
   private String[] laserExDacName_;
   @ElementArray
   private String[] laserExTtlName_;
   @ElementArray
   private String[] laserActDacName_;
   @ElementArray
   private String[] laserActTtlName_;
   @ElementArray
   private String[] laserShutterTtlName_;
   @ElementArray
   private String[] phLampTtlName_;
   //File config
   @Element
   private String fileAcqFolder_;
   @Element
   private String fileBaseName_;

   @Element
   private int camConf_nCam_ = 2;
   @ElementArray
   private ChannelConfig[] camConf_;
   
   @Element
   private FovFilterConfig filterConf_;

   
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
      setLaserExDacName_(new String[]{"AOTF-DAC1","Volts"});
      setLaserExTtlName_(new String[]{"AOTF-Switch","TTL1"});
      setLaserActDacName_(new String[]{"AOTF-DAC3","Volts"});
      setLaserActTtlName_(new String[]{"AOTF-Switch","TTL3"});
      setLaserShutterTtlName_(new String[]{"TTL-Switch","TTL2"});
      setPhLampTtlName_(new String[]{"TTL-Switch","TTL3"});
      //FOV analysis/segmenation config        
      fovAnalysis_excludeBadFov_ = false;
      //Camera config
      camEmccdName_= "Evolve128";
      camPhName_ = "TIS_DCAM";
      camEmccdExposureMs_=30;
      camPhExposureMs_=200;
      camPhDelayMs_=500;
      camEmccdNumFrames_ = 10000;
      camConvertPhExposureToSec_=false;
      camAutoFocusDelayTimeMs_ = 500.;
      //File config
      fileAcqFolder_="."; 
      fileBaseName_="HTPALM_Acq";

      assignDefaultChannelConf();
      assignDefaultFilterConf();
   }


   private void assignDefaultChannelConf(){
      camConf_ = new ChannelConfig[camConf_nCam_];
      for (int ii=0; ii<camConf_.length;ii++){
         camConf_[ii]= new ChannelConfig();
      }
      
      //channel 0 - phase contrast
      camConf_[0].setChShortName_("Ph");
      camConf_[0].setChFullName_("Phase contrast");
      camConf_[0].setPixSizeNm_x_(45.);
      camConf_[0].setPixSizeNm_y_(45.);
      camConf_[0].setnPix_x_(1024);
      camConf_[0].setnPix_y_(768);
      camConf_[0].setROI_xPix_(0);
      camConf_[0].setROI_yPix_(0);
      camConf_[0].setROI_wPix_(1024);
      camConf_[0].setROI_hPix_(768);

      //channel 1 - emccd 
      camConf_[1].setChShortName_("Fl0");
      camConf_[1].setChFullName_("Fluorescence");
      camConf_[1].setPixSizeNm_x_(120);
      camConf_[1].setPixSizeNm_y_(120);
      camConf_[1].setnPix_x_(128);
      camConf_[1].setnPix_y_(128);
      camConf_[1].setROI_xPix_(0);
      camConf_[1].setROI_yPix_(0);
      camConf_[1].setROI_wPix_(128);
      camConf_[1].setROI_hPix_(128);
   }

   private void assignDefaultFilterConf(){
      filterConf_ = new FovFilterConfig();
      
      filterConf_.setLogAlgParam_(new LoGAlgParam());
      filterConf_.getLogAlgParam_().setSmoothRadius_(2.0);
      
      filterConf_.setLocalAlgParam_(new LocalAlgParam());
      filterConf_.getLocalAlgParam_().setMethod(LocalAlgParam.SAUVOLA);
      filterConf_.getLocalAlgParam_().setRadius(15);
      filterConf_.getLocalAlgParam_().setPar1(0.);
      filterConf_.getLocalAlgParam_().setPar2(0.);

      filterConf_.setBgSub_ballRad_(50.);
      filterConf_.setSegAlg_(FovFilterConfig.LOG);
      filterConf_.setFilter_nCell_min_(0);
      filterConf_.setFilter_nCell_max_(Integer.MAX_VALUE);
      filterConf_.setFilter_bactSize_min_(0);
      filterConf_.setFilter_bactSize_max_(Integer.MAX_VALUE);

   }

   public Rectangle getRoiRectangle_(int channel){
      return camConf_[channel].getRectangle();
   }

   //Just the getters and setters below here
   
   /**
    * @return the configPathDefault_
    */
   public static String getDefaultConfigPath_() {
      return configPathDefault_;
   }

   /**
    * @return the laserExDacName_
    */
   public String getLaserExDacName_(int idx) {
      return laserExDacName_[idx];
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName_(String laserExDacName_, int idx) {
      this.laserExDacName_[idx] = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String getLaserExTtlName_(int idx) {
      return laserExTtlName_[idx];
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName_(String laserExTtlName_, int idx) {
      this.laserExTtlName_[idx] = laserExTtlName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String getLaserActTtlName_(int idx) {
      return laserActTtlName_[idx];
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName_(String laserActTtlName_, int idx) {
      this.laserActTtlName_[idx] = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String getLaserShutterTtlName_(int idx) {
      return laserShutterTtlName_[idx];
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName_(String laserShutterTtlName_, int idx) {
      this.laserShutterTtlName_[idx] = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String getPhLampTtlName_(int idx) {
      return phLampTtlName_[idx];
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName_(String phLampTtlName_, int idx) {
      this.phLampTtlName_[idx] = phLampTtlName_;
   }

   /**
    * @return the laserActDacName_
    */
   public String getLaserActDacName_(int idx) {
      return laserActDacName_[idx];
   }

   /**
    * @param laserActDacName_ the laserActDacName_ to set
    */
   public void setLaserActDacName_(String laserActDacName_, int idx) {
      this.laserActDacName_[idx] = laserActDacName_;
   }
   /**
    * @return the fovAnalysis_excludeBadFov_
    */
   public boolean isExcludeBadFov_() {
      return fovAnalysis_excludeBadFov_;
   }

   /**
    * @param fovAnalysis_excludeBadFov_ the fovAnalysis_excludeBadFov_ to set
    */
   public void setExcludeBadFov_(boolean excludeBadFov_) {
      this.fovAnalysis_excludeBadFov_ = excludeBadFov_;
   }

   /**
    * @param camConvertPhExposureToSec_ the camConvertPhExposureToSec_ to set
    */
   public void setCamConvertPhExposureToSec_(boolean camConvertPhExposureToSec_) {
      this.camConvertPhExposureToSec_ = camConvertPhExposureToSec_;
   }


   /**
    * @return the mosaicNFov_
    */
   public int getMosaicNFov_() {
      return mosaicNFov_;
   }

   /**
    * @param mosaicNFov_ the mosaicNFov_ to set
    */
   public void setMosaicNFov_(int mosaicNFov_) {
      this.mosaicNFov_ = mosaicNFov_;
   }

   /**
    * @return the laserExDacName_
    */
   public String[] getLaserExDacName_() {
      return laserExDacName_;
   }

   /**
    * @param laserExDacName_ the laserExDacName_ to set
    */
   public void setLaserExDacName_(String[] laserExDacName_) {
      this.laserExDacName_ = laserExDacName_;
   }

   /**
    * @return the laserExTtlName_
    */
   public String[] getLaserExTtlName_() {
      return laserExTtlName_;
   }

   /**
    * @param laserExTtlName_ the laserExTtlName_ to set
    */
   public void setLaserExTtlName_(String[] laserExTtlName_) {
      this.laserExTtlName_ = laserExTtlName_;
   }

   /**
    * @param laserActDacName_ the laserActDacName_ to set
    */
   public void setLaserActDacName_(String[] laserActDacName_) {
      this.laserActDacName_ = laserActDacName_;
   }

   /**
    * @param laserActTtlName_ the laserActTtlName_ to set
    */
   public void setLaserActTtlName_(String[] laserActTtlName_) {
      this.laserActTtlName_ = laserActTtlName_;
   }

   /**
    * @return the laserShutterTtlName_
    */
   public String[] getLaserShutterTtlName_() {
      return laserShutterTtlName_;
   }

   /**
    * @param laserShutterTtlName_ the laserShutterTtlName_ to set
    */
   public void setLaserShutterTtlName_(String[] laserShutterTtlName_) {
      this.laserShutterTtlName_ = laserShutterTtlName_;
   }

   /**
    * @return the phLampTtlName_
    */
   public String[] getPhLampTtlName_() {
      return phLampTtlName_;
   }

   /**
    * @param phLampTtlName_ the phLampTtlName_ to set
    */
   public void setPhLampTtlName_(String[] phLampTtlName_) {
      this.phLampTtlName_ = phLampTtlName_;
   }

   /**
    * @return the camConvertPhExposureToSec_
    */
   public boolean isCamConvertPhExposureToSec_() {
      return camConvertPhExposureToSec_;
   }

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
    * @return the fovAnalysis_excludeBadFov_
    */
   public boolean isFovAnalysis_excludeBadFov_() {
      return fovAnalysis_excludeBadFov_;
   }

   /**
    * @param fovAnalysis_excludeBadFov_ the fovAnalysis_excludeBadFov_ to set
    */
   public void setFovAnalysis_excludeBadFov_(boolean fovAnalysis_excludeBadFov_) {
      this.fovAnalysis_excludeBadFov_ = fovAnalysis_excludeBadFov_;
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
    * @return the camPhDelayMs_
    */
   public double getCamPhDelayMs_() {
      return camPhDelayMs_;
   }

   /**
    * @param camPhDelayMs_ the camPhDelayMs_ to set
    */
   public void setCamPhDelayMs_(double camPhDelayMs_) {
      this.camPhDelayMs_ = camPhDelayMs_;
   }

   /**
    * @return the camEmccdNumFrames_
    */
   public int getCamEmccdNumFrames_() {
      return camEmccdNumFrames_;
   }

   /**
    * @param camEmccdNumFrames_ the camEmccdNumFrames_ to set
    */
   public void setCamEmccdNumFrames_(int camEmccdNumFrames_) {
      this.camEmccdNumFrames_ = camEmccdNumFrames_;
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
    * @return the laserActDacName_
    */
   public String[] getLaserActDacName_() {
      return laserActDacName_;
   }

   /**
    * @return the laserActTtlName_
    */
   public String[] getLaserActTtlName_() {
      return laserActTtlName_;
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
    * @return the fileBaseName_
    */
   public String getFileBaseName_() {
      return fileBaseName_;
   }

   /**
    * @param fileBaseName_ the fileBaseName_ to set
    */
   public void setFileBaseName_(String fileBaseName_) {
      this.fileBaseName_ = fileBaseName_;
   }

   /**
    * @return the camConf_nCam_
    */
   public int getCamConf_nCam_() {
      return camConf_nCam_;
   }

   /**
    * @param camConf_nCam_ the camConf_nCam_ to set
    */
   public void setCamConf_nCam_(int camConf_nCam_) {
      this.camConf_nCam_ = camConf_nCam_;
   }

   /**
    * @return the camConf_
    */
   public ChannelConfig[] getCamConf_() {
      return camConf_;
   }

   /**
    * @param camConf_ the camConf_ to set
    */
   public void setCamConf_(ChannelConfig[] camConf_) {
      this.camConf_ = camConf_;
   }

   /**
    * @return the filterConf_
    */
   public FovFilterConfig getFilterConf_() {
      return filterConf_;
   }

   /**
    * @param filterConf_ the filterConf_ to set
    */
   public void setFilterConf_(FovFilterConfig filterConf_) {
      this.filterConf_ = filterConf_;
   }

   /**
    * @return the camAutoFocusDelayTimeMs_
    */
   public double getCamAutoFocusDelayTimeMs_() {
      return camAutoFocusDelayTimeMs_;
   }

   /**
    * @param camAutoFocusDelayTimeMs_ the camAutoFocusDelayTimeMs_ to set
    */
   public void setCamAutoFocusDelayTimeMs_(double camAutoFocusDelayTimeMs_) {
      this.camAutoFocusDelayTimeMs_ = camAutoFocusDelayTimeMs_;
   }

}
