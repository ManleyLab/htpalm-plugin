/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.fovfilter.regions;

import ij.process.ImageProcessor;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

/**
 * Adds ability to delete regions
 * @author Holden
 */
public class BreadthFirstLabelingFilterable extends BreadthFirstLabeling{

   static final int PIXEL_MIN = 0, PIXEL_MAX = Integer.MAX_VALUE;
   int minPix=PIXEL_MIN, maxPix=PIXEL_MAX;
   int ROI_x_,ROI_y_,ROI_w_,ROI_h_;
   int[] labelsUnfiltered = null;
	/**
    *
    * @param ip
    */
   public BreadthFirstLabelingFilterable(ImageProcessor ip) {
		super(ip);
      labelsUnfiltered = labels.clone();
	}
   
 /**
    * Resets the labelling to default form before any filtering
    * Method created SH121026
    * @param ip
    */
   public void resetLabeling(){
      labels = labelsUnfiltered.clone();
      collectRegions();
   }
   
   public void filterParticles(int minPixel, int maxPixel, int ROI_x_, int ROI_y_, int ROI_w_, int ROI_h_){

      resetLabeling();

      this.minPix = minPixel;
      this.maxPix = maxPixel;
      this.ROI_x_=ROI_x_;
      this.ROI_y_=ROI_y_;
      this.ROI_w_=ROI_w_;
      this.ROI_h_=ROI_h_;
      
      int nParticleMax = regions.size();
      // first filter  by size
      for (Iterator<BinaryRegion> itr = regions.iterator(); itr.hasNext();){
         BinaryRegion reg = itr.next();
         int nPix = reg.getSize();
         if (nPix < minPixel || nPix > maxPixel){
            deleteRegion(reg);
            itr.remove();
         }
      }

      //then filter outside of image region
      /*
       * for each particle
       *    for each bounding box
       *       is outside region?
       */
      Rectangle boundBox;
      
      for (Iterator<BinaryRegion> itr = regions.iterator(); itr.hasNext();){
         BinaryRegion reg = itr.next();
         boundBox = reg.getBoundingBox();
         if (boundBox.x < ROI_x_ 
                 || boundBox.y < ROI_y_  
                 || boundBox.x + boundBox.width  > ROI_x_ + ROI_w_
                 || boundBox.y + boundBox.height > ROI_y_ + ROI_h_){
            deleteRegion(reg);
            itr.remove();
         }
      }
      
   }
 
   private void deleteRegion(BinaryRegion reg){
      int regionLabel = reg.getLabel();
      
      // scan the labels array and collect the coordinates for each region
		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int lb = labels[v * width + u];
				if (lb == regionLabel){
               labels[v * width + u] = BACKGROUND;
				}
			}
		}
   }
   
}
