/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

import org.simpleframework.xml.Element;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class AcqMetadata {
   @Element
   private int fovNum_;
   @Element
   private int fovAcqNum_;
   @Element
   private boolean phPreAcquire_;
   @Element
   private boolean phPostAcquire_;
   @Element
   private int[] flCh_;
   @Element
   private String acqNamePhPre_;
   @Element
   private String acqNamePhPost_;
   @Element
   private String[] acqNameFl;

   public AcqMetadata(){}

   /**
    * @return the fovNum_
    */
   public int getFovNum_() {
      return fovNum_;
   }

   /**
    * @param fovNum_ the fovNum_ to set
    */
   public void setFovNum_(int fovNum_) {
      this.fovNum_ = fovNum_;
   }

   /**
    * @return the fovAcqNum_
    */
   public int getFovAcqNum_() {
      return fovAcqNum_;
   }

   /**
    * @param fovAcqNum_ the fovAcqNum_ to set
    */
   public void setFovAcqNum_(int fovAcqNum_) {
      this.fovAcqNum_ = fovAcqNum_;
   }

   /**
    * @return the phPreAcquire_
    */
   public boolean isPhPreAcquire_() {
      return phPreAcquire_;
   }

   /**
    * @param phPreAcquire_ the phPreAcquire_ to set
    */
   public void setPhPreAcquire_(boolean phPreAcquire_) {
      this.phPreAcquire_ = phPreAcquire_;
   }

   /**
    * @return the phPostAcquire_
    */
   public boolean isPhPostAcquire_() {
      return phPostAcquire_;
   }

   /**
    * @param phPostAcquire_ the phPostAcquire_ to set
    */
   public void setPhPostAcquire_(boolean phPostAcquire_) {
      this.phPostAcquire_ = phPostAcquire_;
   }

   /**
    * @return the flCh_
    */
   public int[] getFlCh_() {
      return flCh_;
   }

   /**
    * @param flCh_ the flCh_ to set
    */
   public void setFlCh_(int[] flCh_) {
      this.flCh_ = flCh_;
   }

   /**
    * @return the acqNamePhPre_
    */
   public String getAcqNamePhPre_() {
      return acqNamePhPre_;
   }

   /**
    * @param acqNamePhPre_ the acqNamePhPre_ to set
    */
   public void setAcqNamePhPre_(String acqNamePhPre_) {
      this.acqNamePhPre_ = acqNamePhPre_;
   }

   /**
    * @return the acqNamePhPost_
    */
   public String getAcqNamePhPost_() {
      return acqNamePhPost_;
   }

   /**
    * @param acqNamePhPost_ the acqNamePhPost_ to set
    */
   public void setAcqNamePhPost_(String acqNamePhPost_) {
      this.acqNamePhPost_ = acqNamePhPost_;
   }

   /**
    * @return the acqNameFl
    */
   public String[] getAcqNameFl() {
      return acqNameFl;
   }

   /**
    * @param acqNameFl the acqNameFl to set
    */
   public void setAcqNameFl(String[] acqNameFl) {
      this.acqNameFl = acqNameFl;
   }
}   
