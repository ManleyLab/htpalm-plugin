/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.fovfilter;

import ch.epfl.leb.htpalm.fovfilter.regions.BreadthFirstLabelingFilterable;
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.ParticleAnalyzer;
import imagescience.feature.Laplacian;
import imagescience.image.Aspects;
import imagescience.image.FloatImage;
import imagescience.image.Image;
import imagescience.segment.ZeroCrosser;
import imagescience.utility.Progressor;
import mmcorej.CMMCore;
import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.AcquisitionEngine;
import org.micromanager.utils.ReportingUtils;
import fiji.threshold.Auto_Local_Threshold;
import java.util.List;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class FovFilter  {
   //NB to self - this is written for ImageJ 1.46e on 121022

   private static final String BACKGROUND="Default";

   ImagePlus im,labelIm;
   private ImagePlus imSeg;
   private ImageProcessor labelIp;
   private int nParticle=0;
   BreadthFirstLabelingFilterable labeling;
	String origTitle;
   private Rectangle roiRect;
   private int ROI_x_=0;
   private int ROI_y_=0;
   private int ROI_w_=Integer.MAX_VALUE;
   private int ROI_h_=Integer.MAX_VALUE;
   private FovFilterConfig fovFilterConf_;
   private boolean fovOk = false;

   public FovFilter(ImagePlus im0, FovFilterConfig fovFilterConf_, Rectangle roiRect){
      im = im0.duplicate();
      this.fovFilterConf_ = fovFilterConf_;
      this.roiRect = roiRect;
      ROI_x_ = (int) roiRect.getX();
      ROI_y_ = (int) roiRect.getY();
      ROI_w_ = (int) roiRect.getWidth();
      ROI_h_ = (int) roiRect.getHeight();
      doAll();
   }

   public FovFilter(ImagePlus im0, double rollingBallRad_, int segAlg_,Object AlgParam){
      im = im0.duplicate();
      fovFilterConf_ = new FovFilterConfig();
      fovFilterConf_.setBgSub_ballRad_(rollingBallRad_);

      updateSegAlgParam(segAlg_, AlgParam);
      doAll();
      
   }

   private void doAll(){
      doBGSubtract();
      doSeg();
      doLabeling();
      doFilter();

   }

   private void updateSegAlgParam(int segAlg_, Object AlgParam){
      fovFilterConf_.setSegAlg_(segAlg_);
      if (segAlg_ == FovFilterConfig.LOG && AlgParam instanceof LoGAlgParam){
         fovFilterConf_.setLogAlgParam_((LoGAlgParam) AlgParam);
      } else if (segAlg_ == FovFilterConfig.LOCALTHRESH && AlgParam instanceof LocalAlgParam){
         fovFilterConf_.setLocalAlgParam_((LocalAlgParam) AlgParam);
      } else {
         throw new RuntimeException("Unrecognised segmentation algorithm or algorithm parameters");
      }
         
   }

   public void updateBGSubtract(double rollingBallRad_){
      fovFilterConf_.setBgSub_ballRad_(rollingBallRad_);
      doBGSubtract();
      doSeg();
      doLabeling();
      doFilter();
   }
   public void updateSeg(int segAlg_, Object AlgParam){
      updateSegAlgParam(segAlg_, AlgParam);
      doSeg();
      doLabeling();
      doFilter();
   }
   
   public void updateFilter(int minPix, int maxPix,int ROI_x_, int ROI_y_,int  ROI_w_, int ROI_h_){
      fovFilterConf_.setFilter_bactSize_min_(minPix);
      fovFilterConf_.setFilter_bactSize_max_(maxPix);
      this.ROI_x_ = ROI_x_;
      this.ROI_y_ = ROI_y_;
      this.ROI_w_ = ROI_w_;
      this.ROI_h_ = ROI_h_;
      doFilter();
   }

   private void doSeg(){
      int segAlg_ = fovFilterConf_.getSegAlg_();
      if (segAlg_ == FovFilterConfig.LOG){
         doLoGSeg();
      } else if (segAlg_ == FovFilterConfig.LOCALTHRESH){
         doLocalSeg();
      } else if (segAlg_ == FovFilterConfig.SIMPLESEG){
         doSimpleSeg();
      }
   }

   private void doSimpleSeg(){
      /**todo - possibly multi level (3 level) otsu to account for 
       * phase haloing
       */
      
      LoGAlgParam logAlgParam = fovFilterConf_.getLogAlgParam_();
      imSeg = makeOtsuBw(im,logAlgParam.smoothRadius_);
      ImageConverter iconv = new ImageConverter(imSeg);
      iconv.convertToGray8(); 
      IJ.run(imSeg,"Make Binary",null);
      /*make binary just applies an unverting lut - doesnt change 
       * the image values but for some reason this inverts teh effect of 
       * erode and dilate in imagej!@
       * MUST run make binary to get sensible results.
       */
      
   }

  private void doLoGSeg(){
      ImagePlus imEdge, imThresh;
      LoGAlgParam logAlgParam = fovFilterConf_.getLogAlgParam_();
      //get edges image
      imEdge = FovFilter.getEdge(im, logAlgParam.smoothRadius_);
      //get thresholded image
      imThresh = makeOtsuBw(im,logAlgParam.smoothRadius_);
      
      //final calculation combines the two
      ImageCalculator icalc = new ImageCalculator();
      imSeg= icalc.run("AND create", imEdge, imThresh);
      boolean doScaling = false;
      imEdge.getProcessor().convertToByte(doScaling);
      ImageConverter iconv = new ImageConverter(imSeg);
      iconv.convertToGray8(); 
      IJ.run(imSeg,"Make Binary",null);
      /*make binary just applies an unverting lut - doesnt change 
       * the image values but for some reason this inverts teh effect of 
       * erode and dilate in imagej!@
       * MUST run make binary to get sensible results.
       */
      //some morphology stuff to improve segmentation
      imSeg.getProcessor().erode();
      IJ.run(imSeg,"Open",null);
      
      //imEdge.show();
      //imThresh.show();

      //do i have to run close on all the image plus's ive made but not shown??
   }

   private void doLabeling(){
      ImageProcessor ip = imSeg.getProcessor();
		labeling = new BreadthFirstLabelingFilterable(ip);

		origTitle = im.getTitle();
      
      nParticle = labeling.getRegions().size();
   }

   private void doFilter(){
      filterRegions();
      nParticle = labeling.getRegions().size();
      int minCell = fovFilterConf_.getFilter_nCell_min_();
      int maxCell = fovFilterConf_.getFilter_nCell_max_();
      if (nParticle >= minCell && nParticle <= maxCell){
         fovOk = true;
      } else {
         fovOk=false;
      }
   }

   private void filterRegions(){
      int minPix, maxPix;
      minPix = fovFilterConf_.getFilter_bactSize_min_();
      maxPix = fovFilterConf_.getFilter_bactSize_max_();
      labeling.filterParticles(minPix, maxPix,ROI_x_,ROI_y_,ROI_w_,ROI_h_);
      
   }

   public ImageProcessor getLabeledImage(){
      if (labeling==null){
         throw new RuntimeException("Labelled image not yet calculated");
      } else {
         if (labelIp ==null){
            labelIp = labeling.makeRandomColorImage().duplicate();
            }
         return labelIp;
      }
   }

   //DEBUGGING
   private void setToBlack(ImageProcessor ip){
      //DEBUG
      int w,h;
      w=ip.getWidth();
      h=ip.getHeight();
      
      for (int v = 0; v < h; v++) {
         for (int u = 0; u < w; u++) {
            int val = 0;
            ip.putPixel(u, v, val);
         }
      } 
   }

   
   private void subtractBackground(ImagePlus im, double rollingBallRad){
      BackgroundSubtracter ib = new BackgroundSubtracter();
      boolean createBackground=false;
      boolean lightBackground=true;
      boolean useParaboloid=false;
      boolean doPresmooth=true;
      boolean correctCorners=true;
      ib.rollingBallBackground(im.getProcessor(),rollingBallRad, createBackground, 
              lightBackground, useParaboloid, doPresmooth, correctCorners ); 
   }

   private static ImagePlus makeOtsuBw(ImagePlus im, double gaussRadius){
     ImagePlus im2 = im.duplicate();
     IJ.run(im2,"Gaussian Blur...", "sigma="+Double.toString(gaussRadius));
     IJ.setAutoThreshold(im2, BACKGROUND);
     IJ.run(im2, "Convert to Mask",null);
     return im2;
   }
   
   private static ImagePlus getEdge( ImagePlus im, double gaussRadius)
   {
      ImagePlus imInv, imLog, imEdge;
      imInv = im.duplicate();
      //1. Invert
      ImageProcessor impInv = imInv.getProcessor();
      impInv.invert();

      //2. Gaussian blur (optional)
      
      //3. Compute LoG, make binary from >0
      imLog = doLoG(imInv,gaussRadius);
      imEdge = LoG2Bw(imLog);// Log above zero

      imEdge.getProcessor().invert();

      return imEdge;
      
   }
   
   private static ImagePlus LoG2Bw(ImagePlus imLog){
      
      int w = imLog.getWidth(),h= imLog.getHeight();
      
      ImageProcessor impLog = imLog.getProcessor();

      ImagePlus imBw = NewImage.createByteImage("Image Edges", w, h,1,NewImage.FILL_BLACK);
      ImageProcessor impBw = imBw.getProcessor();
      impBw.convertToByte(false);//==clip values
      

      for (int ii=0; ii<h; ii++) {
         for (int jj=0; jj<w; jj++) { 
            float v = impLog.getPixelValue(jj,ii);
            int vBw;
            if (v>0){
               vBw = 255;
            }
            else{
               vBw = 0;
            }
            impBw.putPixel(jj,ii,vBw);
         }
      }
      return imBw;
   }
   
   
   private static ImagePlus doLoG(//adapted from FeatureJ - remember to cite
		final ImagePlus imp,
		final double scale)
   {
      final Image img = Image.wrap(imp);
      Image newimg = new FloatImage(img);
      
      double[] pls = {0, 1}; int pl = 0;
      pls = new double[] {0, 0.95, 1};
      
      final Aspects aspects = newimg.aspects();
      //if (!FJ_Options.isotropic) newimg.aspects(new Aspects());
      newimg.aspects(new Aspects()); // never want isotrop
      final Laplacian laplace = new Laplacian();
      newimg = laplace.run(newimg,scale);
      newimg.aspects(aspects);
      return newimg.imageplus();
   }

   /**
    * @return the imSeg
    */
   public ImagePlus getImSeg() {
      return imSeg;
   }

   /**
    * @return the nParticle
    */
   public int getNParticle() {
      return nParticle;
   }

   private void doBGSubtract() {
      // background subtract the image
      subtractBackground(im, fovFilterConf_.getBgSub_ballRad_());
      IJ.run(im,"Enhance Contrast", "saturated=0 normalize");
   }

   private void doLocalSeg() {
      LocalAlgParam localAlgParam = fovFilterConf_.getLocalAlgParam_();
      Auto_Local_Threshold autoSeg = new Auto_Local_Threshold();
      Object[] exec = autoSeg.exec(im, localAlgParam.getMethod(), localAlgParam.getRadius(), localAlgParam.getPar1(), localAlgParam.getPar2(), localAlgParam.isDoIwhite());
      imSeg = (ImagePlus) exec[0];
      IJ.run(imSeg, "Make Binary",null);
      IJ.run(imSeg, "Open",null);
   }

   /**
    * @return the fovOk
    */
   public boolean isFovOk() {
      return fovOk;
   }


}
