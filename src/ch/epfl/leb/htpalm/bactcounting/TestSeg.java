/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.bactcounting;

import ch.epfl.leb.htpalm.bactcounting.regions.BreadthFirstLabelingFilterable;
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
public class TestSeg  {
   //NB to self - this is written for ImageJ 1.46e on 121022

   private static final String BACKGROUND="Default";

   ImagePlus im,labelIm;
   private ImagePlus imSeg;
   private ImageProcessor labelIp;
   double rollingBallRad_;
   private int nParticle=0;
   BreadthFirstLabelingFilterable labeling;
	String origTitle;
   static final int PIXEL_MIN = 0, PIXEL_MAX = Integer.MAX_VALUE;
   int minPix_=PIXEL_MIN, maxPix_=PIXEL_MAX;
   private int ROI_x_=0;
   private int ROI_y_=0;
   private int ROI_w_=Integer.MAX_VALUE;
   private int ROI_h_=Integer.MAX_VALUE;
   private SegAlgorithm segAlg_=SegAlgorithm.LOG;
   private LoGAlgParam logAlgParam_;
   private LocalAlgParam localAlgParam_;

   public TestSeg(ImagePlus im0, double rollingBallRad_, SegAlgorithm segAlg_,Object AlgParam){
      this.logAlgParam_ = new LoGAlgParam();
      this.localAlgParam_ = new LocalAlgParam();
      this.rollingBallRad_=rollingBallRad_;
      im = im0.duplicate();

      updateSegAlgParam(segAlg_, AlgParam);
      
      doBGSubtract();
      doSeg();
      doLabeling();
      doFilter();
   }

   private void updateSegAlgParam(SegAlgorithm segAlg_, Object AlgParam){
      this.segAlg_ = segAlg_;
      if (segAlg_ == SegAlgorithm.LOG && AlgParam instanceof LoGAlgParam){
         logAlgParam_ = (LoGAlgParam) AlgParam;
      } else if (segAlg_ == SegAlgorithm.LOCALTHRESH && AlgParam instanceof LocalAlgParam){
         localAlgParam_ = (LocalAlgParam) AlgParam;
      } else {
         throw new RuntimeException("Unrecognised segmentation algorithm or algorithm parameters");
      }
         
   }

   public void updateBGSubtract(double rollingBallRad_){
      this.rollingBallRad_=rollingBallRad_;
      doBGSubtract();
      doSeg();
      doLabeling();
      doFilter();
   }
   public void updateSeg(SegAlgorithm segAlg_, Object AlgParam){
      updateSegAlgParam(segAlg_, AlgParam);
      doSeg();
      doLabeling();
      doFilter();
   }
   
   public void updateFilter(int minPix, int maxPix,int ROI_x_, int ROI_y_,int  ROI_w_, int ROI_h_){
      this.minPix_ = minPix;
      this.maxPix_=maxPix;
      this.ROI_x_ = ROI_x_;
      this.ROI_y_ = ROI_y_;
      this.ROI_w_ = ROI_w_;
      this.ROI_h_ = ROI_h_;
      doFilter();
   }

   private void doSeg(){
      if (segAlg_ == SegAlgorithm.LOG){
         doLoGSeg();
      } else if (segAlg_ == SegAlgorithm.LOCALTHRESH){
         doLocalSeg();
      }
   }

  private void doLoGSeg(){
      ImagePlus imEdge, imThresh;
      
      //get edges image
      imEdge = TestSeg.getEdge(im, logAlgParam_.smoothRadius_);
      //get thresholded image
      imThresh = makeOtsuBw(im,logAlgParam_.smoothRadius_);
      
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
      labeling.filterParticles(minPix_, maxPix_,ROI_x_,ROI_y_,ROI_w_,ROI_h_);
      nParticle = labeling.getRegions().size();
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
      subtractBackground(im, rollingBallRad_);
      IJ.run(im,"Enhance Contrast", "saturated=0 normalize");
   }

   private void doLocalSeg() {
      Auto_Local_Threshold autoSeg = new Auto_Local_Threshold();
      Object[] exec = autoSeg.exec(im, localAlgParam_.method, localAlgParam_.radius,  localAlgParam_.par1, localAlgParam_.par2, localAlgParam_.doIwhite );
      imSeg = (ImagePlus) exec[0];
      IJ.run(imSeg, "Make Binary",null);
      IJ.run(imSeg, "Open",null);
   }


}
