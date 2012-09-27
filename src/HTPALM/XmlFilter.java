/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

/**
 *
 * @author seamus.holden@epfl.ch
 */
import java.io.File;
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
}
