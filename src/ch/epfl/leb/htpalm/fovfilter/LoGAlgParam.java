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
public class LoGAlgParam {
   @Element
   double smoothRadius_=2.;

   /**
    * @return the smoothRadius_
    */
   public double getSmoothRadius_() {
      return smoothRadius_;
   }

   /**
    * @param smoothRadius_ the smoothRadius_ to set
    */
   public void setSmoothRadius_(double log_smoothRadius_) {
      this.smoothRadius_ = log_smoothRadius_;
   }
}
