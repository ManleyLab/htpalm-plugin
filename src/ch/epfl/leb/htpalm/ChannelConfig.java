
package ch.epfl.leb.htpalm;

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
 *
 * @author Holden
 */
@Root
class ChannelConfig{

   // base channel is 0th element 
   @Element
   private String chShortName_;
   @Element
   private String chFullName_;
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

   /**
    * @return the chShortName_
    */
   public String getChShortName_() {
      return chShortName_;
   }

   /**
    * @param chShortName_ the chShortName_ to set
    */
   public void setChShortName_(String chShortName_) {
      this.chShortName_ = chShortName_;
   }

   /**
    * @return the chFullName_
    */
   public String getChFullName_() {
      return chFullName_;
   }

   /**
    * @param chFullName_ the chFullName_ to set
    */
   public void setChFullName_(String chFullName_) {
      this.chFullName_ = chFullName_;
   }
   
}
