/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM_Plugin;

/**
 *
 * @author laboleb
 */
public class Spiral {

   private int[] X,Y;
   private int nPos;
 
   public Spiral(){
   }

   public Spiral(int nPos){
      this.initialize(nPos);
   }

   public final void initialize(int nPos){
      this.X= new int[nPos];
      this.Y= new int[nPos];
      this.nPos = nPos;

      this.assignSpiralPos(nPos);
   }

   private void assignSpiralPos(int nPos){
      //start at 00
      this.X[0] = 0;
      this.Y[0] = 0;
      int dX=1, dY=0;

      for(int ii =1; ii < nPos; ii++){
         this.X[ii] = this.X[ii-1]+dX;
         this.Y[ii] = this.Y[ii-1]+dY;
         int Xii, Yii;
         Xii =this.X[ii];
         Yii =this.Y[ii];
         if ( (Xii==Yii) || (Xii < 0 && Xii == -Yii) || (Xii > 0 && Xii == 1-Yii) ) { //if at edge
            // apply a 90deg rotation to (dX, dY)
            //(dX') = (0 -1)(dX)
            //(dY')   (1  0)(dY)
            int t;
            t = dX;
            dX = -dY;
            dY = t;
            if (Debug.DEBUG){
               System.out.println("dX "+ dX+" dY "+dY);
            }
         }
      }
   }

   /**
    * @return the X
    */
   public int[] getX() {
      return X;
   }

   /**
    * @return the Y
    */
   public int[] getY() {
      return Y;
   }
    /**
    * @return the iith value of X
    */
   public int getXii(int ii) {
      return X[ii];
   }

   /**
    * @return the iith value of Y
    */
   public int getYii(int ii) {
      return Y[ii];
   }

   /**
    * @return the nPos
    */
   public int getnPos() {
      return nPos;
   }


}
