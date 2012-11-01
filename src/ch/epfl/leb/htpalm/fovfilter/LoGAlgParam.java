/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.fovfilter;

/**
 *
 * @author Holden
 */
public class LoGAlgParam {
   double smoothRadius_=2.;

   /**
    * @return the smoothRadius_
    */
   public double getLog_smoothRadius_() {
      return smoothRadius_;
   }

   /**
    * @param smoothRadius_ the smoothRadius_ to set
    */
   public void setLog_smoothRadius_(double log_smoothRadius_) {
      this.smoothRadius_ = log_smoothRadius_;
   }
}
