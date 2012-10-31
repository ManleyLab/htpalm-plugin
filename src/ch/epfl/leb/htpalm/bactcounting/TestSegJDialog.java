/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm.bactcounting;

import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import org.micromanager.MMStudioMainFrame;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class TestSegJDialog extends javax.swing.JDialog  implements ImageListener{

   TestSegMMPlugin testPlugin_;
   TestSeg seg_;
   MMStudioMainFrame gui_;
   double smoothRadius_,rollingBallRad_;
   int minPixels_, maxPixels_;
   ImagePlus im_, labelIm_;
   ImageProcessor labelIp_;
   String origTitle_;
   MultiChannelAlignConfig alignConf_ ;
   int ROI_x_;
   int ROI_y_;
   int ROI_w_;
   int ROI_h_;
   private SegAlgorithm segAlg_;
   private LoGAlgParam logAlgParam_;
   private LocalAlgParam localAlgParam_;
   private Object segAlgParam_;

   /**
    * Creates new form TestSegJDialog
    */
   public TestSegJDialog(java.awt.Frame parent, boolean modal, TestSegMMPlugin testPlugin_) {
      super(parent, modal);
      initComponents();

      this.testPlugin_=testPlugin_;
      gui_ = (MMStudioMainFrame) parent;

      alignConf_  = new MultiChannelAlignConfig();
      alignConf_.initialize();
      ROI_x_ = alignConf_.getChannelConfig_(0).getROI_xPix_();
      ROI_y_ = alignConf_.getChannelConfig_(0).getROI_yPix_();
      ROI_w_ = alignConf_.getChannelConfig_(0).getROI_wPix_();
      ROI_h_ = alignConf_.getChannelConfig_(0).getROI_hPix_();
      //ROI_x_ = 255;
      //ROI_y_ = 191;
      //ROI_w_ = 512;
      //ROI_h_ = 384;

      this.logAlgParam_ = new LoGAlgParam();
      this.localAlgParam_ = new LocalAlgParam();
      segAlg_ = SegAlgorithm.LOG;
      segAlgParam_ = logAlgParam_;
      
      updateGui();
      ImagePlus.addImageListener(this); 

      
   }

   private void updateGui(){
      smoothRadius_ = Double.parseDouble(jTextFieldSmoothRad.getText()); 
      rollingBallRad_ = Double.parseDouble(jTextFieldBallRad.getText()); 
      minPixels_ = Integer.parseInt(jTextField_minPixSize.getText());
      maxPixels_ = getMaxPix(jTextField_maxPixSize.getText());
      updateSelectedAlg();
     
   }
   private int getMaxPix(String maxPixStr){
      if (maxPixStr.equalsIgnoreCase("Inf")){
         return Integer.MAX_VALUE;
      } else {
         return Integer.parseInt(maxPixStr);
      }
   }
   
   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jButton_countBacteria = new javax.swing.JButton();
      jTextFieldBallRad = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jTextFieldSmoothRad = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      jTextField_minPixSize = new javax.swing.JTextField();
      jTextField_maxPixSize = new javax.swing.JTextField();
      jButton_grabCurrentImage = new javax.swing.JButton();
      jLabel5 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      jComboBox_SelectAlg = new javax.swing.JComboBox();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Test Bact Segmentation");

      jButton_countBacteria.setText("Count bacteria");
      jButton_countBacteria.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_countBacteriaActionPerformed(evt);
         }
      });

      jTextFieldBallRad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextFieldBallRad.setText("50.0");
      jTextFieldBallRad.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldBallRadActionPerformed(evt);
         }
      });

      jLabel1.setText("Rolling ball radius");

      jTextFieldSmoothRad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextFieldSmoothRad.setText("2.0");
      jTextFieldSmoothRad.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldSmoothRadActionPerformed(evt);
         }
      });

      jLabel2.setText("Smoothing radius");

      jLabel3.setText("Min size bacteria:");
      jLabel3.setToolTipText("");

      jLabel4.setText("Max size bacteria:");
      jLabel4.setToolTipText("");

      jTextField_minPixSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextField_minPixSize.setText("0");
      jTextField_minPixSize.setToolTipText("Minimum size of bacteria (no. of pixels)");
      jTextField_minPixSize.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_minPixSizeActionPerformed(evt);
         }
      });

      jTextField_maxPixSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextField_maxPixSize.setText("Inf");
      jTextField_maxPixSize.setToolTipText("Maximum size of bacteria (no. of pixels)");
      jTextField_maxPixSize.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_maxPixSizeActionPerformed(evt);
         }
      });

      jButton_grabCurrentImage.setText("Grab current image");
      jButton_grabCurrentImage.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_grabCurrentImageActionPerformed(evt);
         }
      });

      jLabel5.setText("Settings testing:");

      jLabel6.setText("Algorithm:");

      jComboBox_SelectAlg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LoG", "Sauvola" }));
      jComboBox_SelectAlg.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox_SelectAlgActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(layout.createSequentialGroup()
                  .add(jLabel3)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jTextField_minPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(layout.createSequentialGroup()
                  .add(jLabel4)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jTextField_maxPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                  .add(0, 0, Short.MAX_VALUE)
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton_countBacteria)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton_grabCurrentImage)))
               .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(layout.createSequentialGroup()
                     .add(jLabel6)
                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jComboBox_SelectAlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(layout.createSequentialGroup()
                     .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                              .add(jLabel1)
                              .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                              .add(jTextFieldBallRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                              .add(jLabel2)
                              .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                              .add(jTextFieldSmoothRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(jLabel5))
                     .add(0, 0, Short.MAX_VALUE))))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel2)
               .add(jTextFieldSmoothRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel1)
               .add(jTextFieldBallRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel3)
               .add(jTextField_minPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel4)
               .add(jTextField_maxPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel6)
               .add(jComboBox_SelectAlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
            .add(jLabel5)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButton_grabCurrentImage)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jButton_countBacteria)
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_countBacteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_countBacteriaActionPerformed
      updateGui();
      if (im_ == null){
         grabCurrentImage();
      }

      if (im_ != null){// ie exclude case where grabCurrentImage failed to find an image
         //TODO - dont make a new object every time
         seg_ = new TestSeg(im_, rollingBallRad_, segAlg_ , segAlgParam_);
      
         seg_.updateFilter(minPixels_, maxPixels_, ROI_x_, ROI_y_ , ROI_w_, ROI_h_);
         updateLabelIm();
         System.out.println("N particle: "+ seg_.getNParticle());
      }
      
   }//GEN-LAST:event_jButton_countBacteriaActionPerformed

   private void updateLabelIm(){
      if (seg_ != null){
         labelIp_ = seg_.getLabeledImage();

         if (labelIm_ == null){
            labelIm_ = new ImagePlus();
            labelIm_.setProcessor(labelIp_);
            labelIm_.setTitle(origTitle_+"-labeling");
            labelIm_.show();
         } else {
            labelIm_.setProcessor(labelIp_);
            labelIm_.updateAndDraw();
         }
      }else{
         throw new RuntimeException("Cannot show labeled image, seg_ obj does not exist");
      }
      
   }

   private void jTextFieldBallRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBallRadActionPerformed
     rollingBallRad_ = Double.parseDouble(jTextFieldBallRad.getText()); 
   }//GEN-LAST:event_jTextFieldBallRadActionPerformed

   private void jTextFieldSmoothRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSmoothRadActionPerformed
     smoothRadius_ = Double.parseDouble(jTextFieldSmoothRad.getText()); 
   }//GEN-LAST:event_jTextFieldSmoothRadActionPerformed

   private void jTextField_minPixSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_minPixSizeActionPerformed
      minPixels_ = Integer.parseInt(jTextField_minPixSize.getText());
   }//GEN-LAST:event_jTextField_minPixSizeActionPerformed

   private void jTextField_maxPixSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_maxPixSizeActionPerformed
      maxPixels_ = getMaxPix(jTextField_minPixSize.getText());
   }//GEN-LAST:event_jTextField_maxPixSizeActionPerformed

   private void jButton_grabCurrentImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_grabCurrentImageActionPerformed
      grabCurrentImage();
   }//GEN-LAST:event_jButton_grabCurrentImageActionPerformed

   private void jComboBox_SelectAlgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SelectAlgActionPerformed
      updateSelectedAlg();
   }

   private void updateSelectedAlg(){
      if (jComboBox_SelectAlg.getSelectedIndex() == 0) {
         segAlg_= SegAlgorithm.LOG;
         segAlgParam_ = logAlgParam_;
      }
      if (jComboBox_SelectAlg.getSelectedIndex() == 1) {
         segAlg_= SegAlgorithm.LOCALTHRESH;
         segAlgParam_ = localAlgParam_;
      }
   }//GEN-LAST:event_jComboBox_SelectAlgActionPerformed

   private void grabCurrentImage(){
      im_ = IJ.getImage();
      origTitle_ = im_.getTitle();
      if (labelIm_ != null){
         labelIm_.close();
         labelIm_=null;
      }
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton_countBacteria;
   private javax.swing.JButton jButton_grabCurrentImage;
   private javax.swing.JComboBox jComboBox_SelectAlg;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JTextField jTextFieldBallRad;
   private javax.swing.JTextField jTextFieldSmoothRad;
   private javax.swing.JTextField jTextField_maxPixSize;
   private javax.swing.JTextField jTextField_minPixSize;
   // End of variables declaration//GEN-END:variables

   @Override
   public void imageOpened(ImagePlus imp) {
      //DO NOTHING
   }

   @Override
   public void imageClosed(ImagePlus imp) {
      if (imp == this.im_){
         im_ = null;
      } else if (imp == this.labelIm_){
         labelIm_ = null;
         
      }
   }

   @Override
   public void imageUpdated(ImagePlus imp) {
      //DO NOTHING
   }
}
