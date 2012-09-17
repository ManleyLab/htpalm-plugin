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
	
   private double Xstart,Ystart;
   private double xStep, yStep;
   private int nFOV_total, nFOV_current;
   private FOV[] FOVArray;

   private class FOV{
      private double X,Y;
      private Boolean skipFOV;
      FOV(){}
      FOV(double X, double Y, Boolean skipFOV){
         this.initialize(X,Y,skipFOV);
      }

      /*
       * Default setting of skip FOV is false
       */
      FOV(double X, double Y ){
         this.initialize(X,Y);
      }
     
      /*
       * Default setting of skip FOV is false
       */
      final void initialize(double X, double Y){
         Boolean skipFOV_default = false;
         this.initialize(X, Y, skipFOV_default);
      }
      
      final void initialize(double X, double Y, Boolean skipFOV){
         this.X=X;
         this.Y=Y;
         this.skipFOV = skipFOV;
      }
      
      void setPos(double X, double Y){
         this.X = X;
         this.Y = Y;
      }
      
      double getX(){
         return X;
      }

      double getY(){
         return Y;
      }
      
      public Boolean getSkipFOV() {
         return skipFOV;
      }
      
      public void setSkipFOV(Boolean skipFOV) {
         this.skipFOV = skipFOV;
      }
   }
           
   public SpiralMosiaic(){}
   

   public SpiralMosiaic(double Xstart, double Ystart, double xStep, double yStep, int nFOV_total){
      this.initialize(Xstart, Ystart, xStep,yStep, nFOV_total);
   }
   
   public final void initialize(double Xstart, double Ystart, double xStep, double yStep, int nFOV_total){
      this.nFOV_total = nFOV_total;
      this.Xstart= Xstart;
      this.Ystart= Ystart;
      this.xStep = xStep;
      this.yStep = yStep;
      this.FOVArray = new FOV[nFOV_total];

      this.setupFOV();
   }

   //Assign the positions here
   private void setupFOV(){
      double Xoffset, Yoffset,dX,dY;
      Xoffset = this.Xstart;
      Yoffset = this.Ystart;
      dX = this.xStep;
      dY = this.yStep;

      Spiral spiral = new Spiral( this.nFOV_total,Xoffset, Yoffset, dX,dY);
      for (int ii = 0; ii<nFOV_total; ii++){
         this.FOVArray[ii] = new FOV(spiral.getX(ii),spiral.getY(ii));
      }
         
   }
   
   public double getX(int nFOV){
      return this.FOVArray[nFOV].getX();
   }

   public double getY(int nFOV){
      return this.FOVArray[nFOV].getY();
   }

   public void setSkipWell(int nFOV, Boolean skipVal){
     this.FOVArray[nFOV].setSkipFOV(skipVal); 
   }

   public Boolean getSkipWell(int nFOV){
      return this.FOVArray[nFOV].getSkipFOV();
   }
           
}




