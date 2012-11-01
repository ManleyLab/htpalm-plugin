
package ch.epfl.leb.htpalm.fovfilter;

// use the simple framework to allow easy generation of POJO xml config file
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;


/**
 * All the configuration options for required to run HTPALM. 
 * @author seamus.holden@epfl.ch
 */
@Root
public class MultiChannelAlignConfig {
   
   @Element
   private static final String configPathDefault_ = "HTPALM_Alignment_Config.xml";
   @ElementArray
   private ChannelConfig[] channelConfig_;
   
   public MultiChannelAlignConfig(){
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
      MultiChannelAlignConfig config;
      try {
         config = serializer.read(MultiChannelAlignConfig.class,f);
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
      channelConfig_ = new ChannelConfig[2];
      for (int ii=0; ii<channelConfig_.length;ii++){
         channelConfig_[ii]= new ChannelConfig();
      }
      
      //channel 0 - phase contrast
      channelConfig_[0].setChName_("Ph");
      channelConfig_[0].setPixSizeNm_x_(45.);
      channelConfig_[0].setPixSizeNm_y_(45.);
      channelConfig_[0].setnPix_x_(1024);
      channelConfig_[0].setnPix_y_(768);
      channelConfig_[0].setROI_xPix_(0);
      channelConfig_[0].setROI_yPix_(0);
      channelConfig_[0].setROI_wPix_(1024);
      channelConfig_[0].setROI_hPix_(768);

      //channel 1 - emccd 
      channelConfig_[1].setChName_("Fl0");
      channelConfig_[1].setPixSizeNm_x_(120);
      channelConfig_[1].setPixSizeNm_y_(120);
      channelConfig_[1].setnPix_x_(1024);
      channelConfig_[1].setnPix_y_(768);
      channelConfig_[1].setROI_xPix_(0);
      channelConfig_[1].setROI_yPix_(0);
      channelConfig_[1].setROI_wPix_(128);
      channelConfig_[1].setROI_hPix_(128);
   }

   //Just the getters and setters below here

   /**
    * @return the channelConfig_
    */
   public ChannelConfig[] getChannelConfig_() {
      return channelConfig_;
   }

   /**
    * @return the Ith channelConfig_
    */
   public ChannelConfig getChannelConfig_(int ii) {
      return channelConfig_[ii];
   }
   
   /**
    * @param channelConfig_ the channelConfig_ to set
    */
   public void setChannelConfig_(ChannelConfig[] channelConfig_) {
      this.channelConfig_ = channelConfig_;
   }
   
   /**
    * @return the configPathDefault_
    */
   public static String getConfigPathDefault_() {
      return configPathDefault_;
   }

}

/**
 *
 * @author Holden
 */
@Root
class ChannelConfig{

   // base channel is 0th element 
   @Element
   private String chName_;
   @Element
   private double pixSizeNm_x_;
   @Element
   private double pixSizeNm_y_;
   @Element
   private int nPix_x_;
   @Element
   private int nPix_y_;
   @Element
   private int ROI_xPix_;
   @Element
   private int ROI_yPix_;
   @Element
   private int ROI_wPix_;
   @Element
   private int ROI_hPix_;

   /**
    * @return the chName_
    */
   public String getChName_() {
      return chName_;
   }

   /**
    * @param chName_ the chName_ to set
    */
   public void setChName_(String chName_) {
      this.chName_ = chName_;
   }

   /**
    * @return the pixSizeNm_x_
    */
   public double getPixSizeNm_x_() {
      return pixSizeNm_x_;
   }

   /**
    * @param pixSizeNm_x_ the pixSizeNm_x_ to set
    */
   public void setPixSizeNm_x_(double pixSizeNm_x_) {
      this.pixSizeNm_x_ = pixSizeNm_x_;
   }

   /**
    * @return the pixSizeNm_y_
    */
   public double getPixSizeNm_y_() {
      return pixSizeNm_y_;
   }

   /**
    * @param pixSizeNm_y_ the pixSizeNm_y_ to set
    */
   public void setPixSizeNm_y_(double pixSizeNm_y_) {
      this.pixSizeNm_y_ = pixSizeNm_y_;
   }

   /**
    * @return the nPix_x_
    */
   public int getnPix_x_() {
      return nPix_x_;
   }

   /**
    * @param nPix_x_ the nPix_x_ to set
    */
   public void setnPix_x_(int nPix_x_) {
      this.nPix_x_ = nPix_x_;
   }

   /**
    * @return the nPix_y_
    */
   public int getnPix_y_() {
      return nPix_y_;
   }

   /**
    * @param nPix_y_ the nPix_y_ to set
    */
   public void setnPix_y_(int nPix_y_) {
      this.nPix_y_ = nPix_y_;
   }

   /**
    * @return the ROI_xPix_
    */
   public int getROI_xPix_() {
      return ROI_xPix_;
   }

   /**
    * @param ROI_xPix_ the ROI_xPix_ to set
    */
   public void setROI_xPix_(int ROI_xPix_) {
      this.ROI_xPix_ = ROI_xPix_;
   }

   /**
    * @return the ROI_yPix_
    */
   public int getROI_yPix_() {
      return ROI_yPix_;
   }

   /**
    * @param ROI_yPix_ the ROI_yPix_ to set
    */
   public void setROI_yPix_(int ROI_yPix_) {
      this.ROI_yPix_ = ROI_yPix_;
   }

   /**
    * @return the ROI_wPix_
    */
   public int getROI_wPix_() {
      return ROI_wPix_;
   }

   /**
    * @param ROI_wPix_ the ROI_wPix_ to set
    */
   public void setROI_wPix_(int ROI_wPix_) {
      this.ROI_wPix_ = ROI_wPix_;
   }

   /**
    * @return the ROI_hPix_
    */
   public int getROI_hPix_() {
      return ROI_hPix_;
   }

   /**
    * @param ROI_hPix_ the ROI_hPix_ to set
    */
   public void setROI_hPix_(int ROI_hPix_) {
      this.ROI_hPix_ = ROI_hPix_;
   }
   
}
