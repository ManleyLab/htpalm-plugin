/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM_Plugin;

/**
 *
 * @author laboleb
 */
public class TestSpiral {
   

      /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      int nPos = 50;
      double Xstart=10,Ystart =15;
      double xStep=2,yStep=10;
   
      //Spiral testSpiral = new Spiral( nPos);
      SpiralMosiaic testSpiral = new SpiralMosiaic(Xstart, Ystart, xStep, yStep, nPos);
      for (int ii=0;ii<nPos; ii++){
         double x,y;
         x= testSpiral.getX(ii);
         y= testSpiral.getY(ii);
         System.out.println(x+" "+y);
      }

   }
}
 
