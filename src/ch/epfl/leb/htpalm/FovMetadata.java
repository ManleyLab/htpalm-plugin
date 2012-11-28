/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class FovMetadata {
   @Element
   private int fovNum_;
   @Element
   private int nAcq_=0;
   @ElementList
   private ArrayList<Integer> fovAcqNum_ = new ArrayList<Integer>();

   public FovMetadata(){}

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
   public ArrayList<Integer> getFovAcqNum_() {
      return fovAcqNum_;
   }

   /**
    * @param fovAcqNum_ the fovAcqNum_ to set
    */
   public void setFovAcqNum_(ArrayList<Integer> fovAcqNum_) {
      this.fovAcqNum_ = fovAcqNum_;
   }

   /**
    * @return the nAcq_
    */
   public int getnAcq_() {
      return nAcq_;
   }

   /**
    * @param nAcq_ the nAcq_ to set
    */
   public void setnAcq_(int nAcq_) {
      this.nAcq_ = nAcq_;
   }
}