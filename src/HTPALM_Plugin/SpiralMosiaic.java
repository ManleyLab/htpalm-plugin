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
   private double xStep, yStep;
   private int nFOV_total, nFOV_current;
   private FOV[] FOVArray;

   private class FOV{
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
   

   public SpiralMosiaic(double posStart_X, double posStart_Y, double xStep, double yStep, int nFOV_total){
      this.initialize(posStart_X, posStart_Y, xStep,yStep, nFOV_total);
   }
   public SpiralMosiaic(double[] posStart, double xStep, double yStep, int nFOV_total){
      this.initialize(posStart, xStep,yStep, nFOV_total);
   }
   
   public void initialize(double posStart_X, double posStart_Y, double xStep, double yStep, int nFOV_total){
      double[] pos = new double[NDIM];
      pos[0] = posStart_X;
      pos[1] = posStart_Y;
      this.initialize(pos, xStep, yStep,nFOV_total);
   }

   public void initialize(double[] posStart, double xStep, double yStep, int nFOV_total){
      this.nFOV_total = nFOV_total;
      this.pos_start = posStart;
      this.xStep = xStep;
      this.yStep = yStep;
      this.FOVArray = new FOV[nFOV_total];

      this.setupFOV();
   }

   //Assign the positions here
   private void setupFOV(){
      double Xoffset, Yoffset,dX,dY;
      Xoffset = this.pos_start[0];
      Yoffset = this.pos_start[1];
      dX = this.xStep;
      dY = this.yStep;
      double[] pos = new double[NDIM];

      SpiralDouble spiral = new SpiralDouble( this.nFOV_total,Xoffset, Yoffset, dX,dY);
      for (int ii = 0; ii<nFOV_total; ii++){
         pos[1]= spiral.getXii(ii);
         pos[2]= spiral.getYii(ii);
         this.FOVArray[ii].setPos(pos);
      }
         
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




