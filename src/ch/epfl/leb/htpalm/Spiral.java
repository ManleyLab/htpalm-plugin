/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

/**
 *
 * @author laboleb
 */
public class Spiral  implements FovList{

   private double xOffset_, yOffset_, xStep_, yStep_;
   private double x_,y_;
   private int dXint_=1, dYint_=0, xInt_=0,yInt_=0, nCur_=0;
 
   public Spiral(){
   }

   public Spiral(double Xoffset, double Yoffset, double Xstep, double Ystep){
      this.initialize(Xoffset, Yoffset,Xstep,Ystep);
   }

   public final void initialize(double Xoffset, double Yoffset,double Xstep, double Ystep){
      this.xOffset_  = Xoffset;
      this.yOffset_  = Yoffset;
      this.xStep_= Xstep;
      this.yStep_ = Ystep;
   } 


   /**
    * @return the xOffset_
    */
   public double getXoffset() {
      return xOffset_;
   }

   /**
    * @return the yOffset_
    */
   public double getYoffset() {
      return yOffset_;
   }

   /**
    * @return the xStep_ 
    */
   public double getXstep() {
      return xStep_;
   }

   /**
    * @return the yStep_ 
    */
   public double getYstep() {
      return yStep_;
   }

   public double getX() {
      return x_;
   }

   public double getY() {
      return y_;
   }

   public int getNCur() {
      return nCur_;
   }

   public void gotoFov(int n) {
       if (n< 0){
         throw new RuntimeException("FOV number < 0 requested - not possible for Spiral FOV");
      } else if (nCur_ < n){
         while (nCur_ < n){
            gotoNextFov();
         }
      } else if (nCur_ > n){
         while (nCur_ > n){
            gotoPrevFov();
         }
      } // otherwise do nothing as already in correct position
   }

   public void gotoNextFov() {
      xInt_ += dXint_;
      yInt_ += dYint_;
      x_ = xInt_*xStep_+xOffset_;
      y_ = yInt_*yStep_+yOffset_;
      nCur_ ++;
      
      //update dy dx
      if ( (xInt_==yInt_) || (xInt_ < 0 && xInt_ == -yInt_) || (xInt_ > 0 && xInt_ == 1-yInt_) ) { //if at edge
            // apply a 90deg rotation to (dX, dY)
            //(dX') = (0 -1)(dX)
            //(dY')   (1  0)(dY)
            int t;
            t = dXint_;
            dXint_ = -dYint_;
            dYint_ = t;
      }
      
   }

   public void gotoPrevFov() {
      if (nCur_ > 0) {
         //going backwards so need to update dx dy first
         if ( (xInt_==yInt_) || (xInt_ < 0 && xInt_ == -yInt_) || (xInt_ > 0 && xInt_ == 1-yInt_) ) { //if at edge
            // apply a -90deg rotation to (dX, dY)
            //(dX') = (0   1)(dX)
            //(dY')   (-1  0)(dY)

            int t;
            t = dXint_;
            dXint_ = dYint_;
            dYint_ = -t;
         }

         xInt_ -= dXint_;
         yInt_ -= dYint_;
         x_ = xInt_*xStep_+xOffset_;
         y_ = yInt_*yStep_+yOffset_;
         nCur_ --;
         
      } else {
         throw new RuntimeException("FOV number < 0 requested - not possible for Spiral FOV");
      }
   }

}

