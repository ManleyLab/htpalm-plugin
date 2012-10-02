/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.seamusholden.htpalm;

import org.simpleframework.xml.Element;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class AcqMetadata {
   @Element
   int fovNum_;
   @Element
   int fovAcqNum_;
   @Element
   boolean phPreAcquire_, phPostAcquire_;
   @Element
   int[] flCh_;
   @Element
   String acqNamePhPre_, acqNamePhPost_;
   @Element
   String[] acqNameFl;

   public AcqMetadata(){}
}   
