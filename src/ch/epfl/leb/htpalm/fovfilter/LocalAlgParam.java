/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.fovfilter;

import org.simpleframework.xml.*;

   /**
 *
 * @author Holden
 */
@Root
public class LocalAlgParam {
   public static final int BERNSEN=0, MEAN=1, MEDIAN=2, MIDGREY=3, NIBLACK=4, SAUVOLA=5;
   public static final String[] methodValues= {"Bersen","Mean","Median","MidGrey","Niblack","Sauvola"};

   @Element
   private int nMethod = 5;
   @Element
   private String method = "Sauvola";
   @Element
   private int radius=15;
   @Element
   private double par1 = 0.;
   @Element
   private double par2 = 0.;
   @Element
   private boolean doIwhite = false;//dark objects on white bg -for now, always the case


   public void updateMethod(int nMeth){
      if (nMeth >=0 && nMeth < methodValues.length){
         this.nMethod = nMeth;
         this.method = methodValues[nMeth];
      } else {
         throw new RuntimeException("Supplied method value out of range");
      }
   }

   /**
    * @return the method
    */
   public String getMethod() {
      return method;
   }

   /**
    * @return the nMethod
    */
   public int getnMethod() {
      return nMethod;
   }

   /**
    * @param nMethod the nMethod to set
    */
   public void setMethod(int nMeth) {
      updateMethod(nMeth);
   }

   /**
    * @return the radius
    */
   public int getRadius() {
      return radius;
   }

   /**
    * @param radius the radius to set
    */
   public void setRadius(int radius) {
      this.radius = radius;
   }

   /**
    * @return the par1
    */
   public double getPar1() {
      return par1;
   }

   /**
    * @param par1 the par1 to set
    */
   public void setPar1(double par1) {
      this.par1 = par1;
   }

   /**
    * @return the par2
    */
   public double getPar2() {
      return par2;
   }

   /**
    * @param par2 the par2 to set
    */
   public void setPar2(double par2) {
      this.par2 = par2;
   }

   /**
    * @return the doIwhite
    */
   public boolean isDoIwhite() {
      return doIwhite;
   }

   /**
    * @param doIwhite the doIwhite to set
    */
   public void setDoIwhite(boolean doIwhite) {
      this.doIwhite = doIwhite;
   }
   
}
