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
   int fovNum_;
   @Element
   int nFovAcq_=0;
   @ElementList
   ArrayList<Integer> fovAcqNum_ = new ArrayList<Integer>();

   public FovMetadata(){}
}