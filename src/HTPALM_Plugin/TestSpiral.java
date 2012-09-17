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
      double[] posStart={10,15};
      double dX=2,dY=10;
   
      //Spiral testSpiral = new Spiral( nPos);
      SpiralMosiaic testSpiral = new SpiralMosiaic(posStart, dX,dY, nPos);
      for (int ii=0;ii<nPos; ii++){
         double[] pos;
         pos = testSpiral.getFOVPos(ii);
         System.out.println(pos[0]+" "+pos[1]);
      }

   }
}
 
