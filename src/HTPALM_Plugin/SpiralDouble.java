/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM_Plugin;

/**
 *
 * @author laboleb
 */
public class SpiralDouble{
   private double[] X, Y;
   private double Xoffset, Yoffset, dX, dY;
   private int nPos;
   private Spiral spiral;

   public SpiralDouble(){
   }

   public SpiralDouble(int nPos,double Xoffset, double Yoffset, double dX, double dY){
      this.initialize(nPos, Xoffset, Yoffset,dX,dY);
   }

   public final void initialize(int nPos,double Xoffset, double Yoffset,double dX, double dY){
      this.nPos     = nPos;
      this.X = new double[nPos];
      this.Y = new double[nPos];
      this.Xoffset  = Xoffset;
      this.Yoffset  = Yoffset;
      this.dX = dX;
      this.dY = dY;
      this.spiral= new Spiral(nPos);
      this.assignSpiralPos();
   } 

   private void assignSpiralPos(){
      for(int ii=0;ii<this.getnPos(); ii++){
         this.X[ii] = spiral.getXii(ii)*this.dX+this.Xoffset;
         this.Y[ii] = spiral.getYii(ii)*this.dY+this.Yoffset;
      }
   }

   /**
    * @return the X
    */
   public double[] getX() {
      return X;
   }

   /**
    * @return the Y
    */
   public double[] getY() {
      return Y;
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
    * @return the dX 
    */
   public double getdX() {
      return dX;
   }

   /**
    * @return the dY
    */
   public double getdY() {
      return dY;
   }

   /**
    * @return the nPos
    */
   public int getnPos() {
      return nPos;
   }
    /**
    * @return the iith value of X
    */
   public double getXii(int ii) {
      return X[ii];
   }

   /**
    * @return the iith value of Y
    */
   public double getYii(int ii) {
      return Y[ii];
   }
}
