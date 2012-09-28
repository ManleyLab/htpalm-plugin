/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htpalm;

/**
 *
 * @author seamus.holden@epfl.ch
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.*;
 
public class XmlFilter extends FileFilter {
 
    //Accept all directories and all xml files.
    public boolean accept(File f) {
       
        String fname = f.getName().toLowerCase();
        if (f.isDirectory() ||
            fname.endsWith(".xml")){
               return true;
        } else {
            return false;
        }
    }
 
    public String getDescription(){
        return "*.xml";
    }

    public static File makeXml(File f){
      if (!f.getName().toLowerCase().endsWith(".xml")){//make sure the name ends with .xml
         try {
            String fname;
            fname = f.getCanonicalPath()+".xml";
            f = new File(fname);
         } catch (IOException ex) {
            Logger.getLogger(XmlFilter.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
      return f;
    }
}
