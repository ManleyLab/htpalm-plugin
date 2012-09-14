/**
 * SpiralMosiaic - for HT PALM automation
 * Based on Karl Belluve's BigWell plugin
 * Author: Seamus Holden, EPFL, Switzerland
 *
 */

package HTPALM_Plugin;
import java.util.Vector;


public class SpiralMosiaic{
   
   private static final int NDIM = 2;
	
   private double pos_start[] = new double[NDIM];
   private double mosaicBox[] = new double[4]; 
   private int nFOV_total, nFOV_current;
   FOV[] FOVArray;

   class FOV{
      private double[] pos = new double[NDIM];
      private Boolean skipFOV;
      FOV(){}
     
      void initialize(double[] pos, Boolean skipFOV){
         this.pos = pos;
         this.skipFOV = skipFOV;
      }
      
      void setPos(double[] pos){
         this.pos = pos;
      }
      
      double[] getPos(){
         return pos;
      }

      public Boolean getSkipFOV() {
         return skipFOV;
      }
      
      public void setSkipFOV(Boolean skipFOV) {
         this.skipFOV = skipFOV;
      }
   }
           
   public SpiralMosiaic(){}
   
   public void initialize(double posStart_X, double posStart_Y, double[] mosaicBox, int nFOV_total){
      double[] pos = new double[NDIM];
      pos[0] = posStart_X;
      pos[1] = posStart_Y;
      this.initialize(pos, mosaicBox,nFOV_total);
   }

   public void initialize(double[] posStart, double[] mosaicBox, int nFOV_total){
      this.nFOV_total = nFOV_total;
      this.pos_start = posStart;
      this.mosaicBox = mosaicBox;
      this.FOVArray = new FOV[nFOV_total];

      this.setupFOV();
   }

   private void setupFOV(){
      //TODO Assign the positions here
      
   }
   
   public double[] getFOVPos(int nFOV){
      return this.FOVArray[nFOV].getPos();
   }

   public void setSkipWell(int nFOV, Boolean skipVal){
     this.FOVArray[nFOV].setSkipFOV(skipVal); 
   }

   public Boolean getSkipWell(int nFOV){
      return this.FOVArray[nFOV].getSkipFOV();
   }
           
}




