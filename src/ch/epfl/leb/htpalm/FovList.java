/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

/**
 * General interface for HTPALM Fovs
 * Currently used by SpiralMosaic
 * @author seamus.holden@epfl.ch
 */
public interface FovList {
   public abstract double getX();
   public abstract double getY();
   public abstract int getNCur();
   public abstract void gotoFov(int n);
   public abstract void gotoNextFov();
   public abstract void gotoPrevFov();
}
