/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

/**
 *
 * @author laboleb
 */
public class Spiral {

   private double[] X, Y;
   private double Xoffset, Yoffset, Xstep, Ystep;
   private int nPos;
 
   public Spiral(){
   }

   public Spiral(int nPos,double Xoffset, double Yoffset, double Xstep, double Ystep){
      this.initialize(nPos, Xoffset, Yoffset,Xstep,Ystep);
   }

   public final void initialize(int nPos,double Xoffset, double Yoffset,double Xstep, double Ystep){
      this.nPos = nPos;
      this.X = new double[nPos];
      this.Y = new double[nPos];
      this.Xoffset  = Xoffset;
      this.Yoffset  = Yoffset;
      this.Xstep= Xstep;
      this.Ystep = Ystep;
      this.assignSpiralPos();
   } 
   private void assignSpiralPos(){
      // calculate raw coordinates as integerst to allow equality comparisons
      int[] Xint=new int[nPos],Yint=new int[nPos];
      int dXint=1, dYint=0;
      //start at 00
      Xint[0] = 0;
      Yint[0] = 0;
      X[0] = Xint[0]*Xstep+Xoffset;
      Y[0] = Yint[0]*Ystep+Yoffset;

      for(int ii =1; ii < nPos; ii++){
         // raw coordinates
         Xint[ii] = Xint[ii-1]+dXint;
         Yint[ii] = Yint[ii-1]+dYint;
         // real space coordinates
         X[ii] = Xint[ii]*Xstep+Xoffset;
         Y[ii] = Yint[ii]*Ystep+Yoffset;
         
         if ( (Xint[ii]==Yint[ii]) || (Xint[ii] < 0 && Xint[ii] == -Yint[ii]) || (Xint[ii] > 0 && Xint[ii] == 1-Yint[ii]) ) { //if at edge
            // apply a 90deg rotation to (dX, dY)
            //(dX') = (0 -1)(dX)
            //(dY')   (1  0)(dY)
            int t;
            t = dXint;
            dXint = -dYint;
            dYint = t;
            if (Debug.DEBUG){
               System.out.println("dX "+ dXint+" dY "+dYint);
            }
         }
      }
   }

    /**
    * @return the iith value of X
    */
   public double getX(int ii) {
      return X[ii];
   }

   /**
    * @return the iith value of Y
    */
   public double getY(int ii) {
      return Y[ii];
   }

   /**
    * @return the nPos
    */
   public int getnPos() {
      return nPos;
   }

   /**
    * @return the Xoffset
    */
   public double getXoffset() {
      return Xoffset;
   }

   /**
    * @return the Yoffset
    */
   public double getYoffset() {
      return Yoffset;
   }

   /**
    * @return the Xstep 
    */
   public double getXstep() {
      return Xstep;
   }

   /**
    * @return the Ystep 
    */
   public double Ystep() {
      return Ystep;
   }

}
