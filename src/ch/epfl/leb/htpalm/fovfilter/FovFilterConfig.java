/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.fovfilter;

import org.simpleframework.xml.*;

/**
 *
 * @author Holden
 */
@Root
public class FovFilterConfig {
   public static final int LOG=0, LOCALTHRESH=1;
   public static final String[] segAlgNames_ = {"LoG", "Local thresholding"};
   
   @Element
   private LoGAlgParam logAlgParam_;
   @Element
   private LocalAlgParam localAlgParam_;
   @Element
   private double bgSub_ballRad_;
   @Element
   private int segAlg_;
   @Element
   private int filter_bactSize_min_;
   @Element
   private int filter_bactSize_max_;
   @Element
   private int filter_nCell_min_;
   @Element
   private int filter_nCell_max_;

   // Just the getters and setters below this

   /**
    * @return the logAlgParam_
    */
   public LoGAlgParam getLogAlgParam_() {
      return logAlgParam_;
   }

   /**
    * @param logAlgParam_ the logAlgParam_ to set
    */
   public void setLogAlgParam_(LoGAlgParam logAlgParam_) {
      this.logAlgParam_ = logAlgParam_;
   }

   /**
    * @return the localAlgParam_
    */
   public LocalAlgParam getLocalAlgParam_() {
      return localAlgParam_;
   }

   /**
    * @param localAlgParam_ the localAlgParam_ to set
    */
   public void setLocalAlgParam_(LocalAlgParam localAlgParam_) {
      this.localAlgParam_ = localAlgParam_;
   }

   /**
    * @return the bgSub_ballRad_
    */
   public double getBgSub_ballRad_() {
      return bgSub_ballRad_;
   }

   /**
    * @param bgSub_ballRad_ the bgSub_ballRad_ to set
    */
   public void setBgSub_ballRad_(double bgSub_ballRad_) {
      this.bgSub_ballRad_ = bgSub_ballRad_;
   }

   /**
    * @return the segAlg_
    */
   public int getSegAlg_() {
      return segAlg_;
   }

   /**
    * @param segAlg_ the segAlg_ to set
    */
   public void setSegAlg_(int segAlg_) {
      this.segAlg_ = segAlg_;
   }

   /**
    * @return the filter_nCell_min_
    */
   public int getFilter_nCell_min_() {
      return filter_nCell_min_;
   }

   /**
    * @param filter_nCell_min_ the filter_nCell_min_ to set
    */
   public void setFilter_nCell_min_(int filter_nCell_min_) {
      this.filter_nCell_min_ = filter_nCell_min_;
   }

   /**
    * @return the filter_nCell_max_
    */
   public int getFilter_nCell_max_() {
      return filter_nCell_max_;
   }

   /**
    * @param filter_nCell_max_ the filter_nCell_max_ to set
    */
   public void setFilter_nCell_max_(int filter_nCell_max_) {
      this.filter_nCell_max_ = filter_nCell_max_;
   }

   /**
    * @return the filter_bactSize_min_
    */
   public int getFilter_bactSize_min_() {
      return filter_bactSize_min_;
   }

   /**
    * @param filter_bactSize_min_ the filter_bactSize_min_ to set
    */
   public void setFilter_bactSize_min_(int filter_bactSize_min_) {
      this.filter_bactSize_min_ = filter_bactSize_min_;
   }

   /**
    * @return the filter_bactSize_max_
    */
   public int getFilter_bactSize_max_() {
      return filter_bactSize_max_;
   }

   /**
    * @param filter_bactSize_max_ the filter_bactSize_max_ to set
    */
   public void setFilter_bactSize_max_(int filter_bactSize_max_) {
      this.filter_bactSize_max_ = filter_bactSize_max_;
   }
}
