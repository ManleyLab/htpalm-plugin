/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.beanutils.BeanUtils;
import org.micromanager.utils.ReportingUtils;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class InitOptionDialog extends javax.swing.JDialog {

   ConfigurationOptions newConfig_;
   ConfigurationOptions config_;

   /**
    * Creates new form InitOptionDialog
    */
   public InitOptionDialog(java.awt.Frame parent, ConfigurationOptions config_,boolean modal) {
      super(parent, modal);
      initComponents();
      this.config_ = config_;
      newConfig_ = new ConfigurationOptions();
      
      try {
         BeanUtils.copyProperties(newConfig_,this.config_);//copy the properties of config into newConfig_
         reloadSettings();
      } catch (IllegalAccessException ex) {
         ReportingUtils.logError(ex, "Failed to make copy of settings");
      } catch (InvocationTargetException ex) {
         ReportingUtils.logError(ex, "Failed to make copy of settings");
      }
   }
   
   private void reloadSettings(){
      jTextField_StepSizeX.setText(Double.toString(newConfig_.mosaicStepSizeX_));
      jTextField_StepSizeY.setText(Double.toString(newConfig_.mosaicStepSizeY_));
      jTextField_MosaicNFov.setText(Integer.toString(newConfig_.getMosaicNFov()));
      jTextField_AcqFolderName.setText(newConfig_.fileAcqFolder_);
      jTextField_BaseFileName.setText(newConfig_.fileBaseName_);
      jTextField_EmccdCamName.setText(newConfig_.camEmccdName_);
      jTextField_EmccdExposureTime.setText(Double.toString(newConfig_.camEmccdExposureMs_));
      jTextField_EmccdNumFrames.setText(Integer.toString(newConfig_.camEmccdNumFrames_));
      jTextField_PhCamName.setText(newConfig_.camPhName_);
      jTextField_PhExposureTime.setText(Double.toString(newConfig_.camPhExposureMs_));
      jTextField_PhCamDelayTime.setText(Double.toString(newConfig_.camPhDelayMs_));
      jCheckBox_ConvertPhExposureMsToSec.setSelected(newConfig_.isCamConvertPhExposureToSec());
      jTextField_ExcitationDacLabel.setText(newConfig_.getLaserExDacName(0));
      jTextField_ExcitationTtlLabel.setText(newConfig_.getLaserExTtlName(0));
      jTextField_ActivationLabel.setText(newConfig_.getLaserActDacName(0));
      jTextField_ActivationTtlLabel.setText(newConfig_.getLaserActTtlName(0));
      jTextField_LaserShutterTtLabel.setText(newConfig_.getLaserShutterTtlName(0));
      jTextField_PhLampTtlLabel.setText(newConfig_.getPhLampTtlName(0));
      jTextField_ExcitationDacProp.setText(newConfig_.getLaserExDacName(1));
      jTextField_ExcitationTtlProp.setText(newConfig_.getLaserExTtlName(1));
      jTextField_ActivationDacProp.setText(newConfig_.getLaserActDacName(1));
      jTextField_ActivationTtlProp.setText(newConfig_.getLaserActTtlName(1));
      jTextField_LaserShutterTtlProp.setText(newConfig_.getLaserShutterTtlName(1));
      jTextField_PhLampTtlProp.setText(newConfig_.getPhLampTtlName(1));
      
   }

   private void updateSettings(){
      newConfig_.mosaicStepSizeX_= Double.parseDouble(jTextField_StepSizeX.getText());
      newConfig_.mosaicStepSizeY_= Double.parseDouble(jTextField_StepSizeY.getText());
      newConfig_.setMosaicNFov(Integer.parseInt(jTextField_MosaicNFov.getText()));
      newConfig_.fileAcqFolder_ = jTextField_AcqFolderName.getText();
      newConfig_.fileBaseName_ = jTextField_BaseFileName.getText();
      newConfig_.camEmccdName_ = jTextField_EmccdCamName.getText();
      newConfig_.camEmccdExposureMs_ = Double.parseDouble(jTextField_EmccdExposureTime.getText());
      newConfig_.camEmccdNumFrames_= Integer.parseInt(jTextField_EmccdNumFrames.getText());
      newConfig_.camPhName_ = jTextField_PhCamName.getText(); 
      newConfig_.camPhExposureMs_= Double.parseDouble(jTextField_PhExposureTime.getText());
      newConfig_.camPhDelayMs_= Double.parseDouble(jTextField_PhCamDelayTime.getText());
      newConfig_.setCamConvertPhExposureToSec(jCheckBox_ConvertPhExposureMsToSec.isSelected());
      newConfig_.setLaserExDacName(jTextField_ExcitationDacLabel.getText(),0);
      newConfig_.setLaserExTtlName(jTextField_ExcitationTtlLabel.getText(),0);
      newConfig_.setLaserActDacName(jTextField_ActivationLabel.getText(),0);
      newConfig_.setLaserActTtlName(jTextField_ActivationTtlLabel.getText(),0);
      newConfig_.setLaserShutterTtlName(jTextField_LaserShutterTtLabel.getText(),0);
      newConfig_.setPhLampTtlName(jTextField_PhLampTtlLabel.getText(),0);
      newConfig_.setLaserExDacName(jTextField_ExcitationDacProp.getText(),1);
      newConfig_.setLaserExTtlName(jTextField_ExcitationTtlProp.getText(),1);
      newConfig_.setLaserActDacName(jTextField_ActivationDacProp.getText(),1);
      newConfig_.setLaserActTtlName(jTextField_ActivationTtlProp.getText(),1);
      newConfig_.setLaserShutterTtlName(jTextField_LaserShutterTtlProp.getText(),1);
      newConfig_.setPhLampTtlName(jTextField_PhLampTtlProp.getText(),1);
   }

   /*
    *  Copy the properties of newConfig_ into config_
    */
   private void updateConfig(){
      try {
         BeanUtils.copyProperties(config_,newConfig_);//copy the properties of newConfig_ into config_
      } catch (IllegalAccessException ex) {
         ReportingUtils.logError(ex, "Failed to make copy of settings");
      } catch (InvocationTargetException ex) {
         ReportingUtils.logError(ex, "Failed to make copy of settings");
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

      jTabbedPane_Settings = new javax.swing.JTabbedPane();
      jPanel1 = new javax.swing.JPanel();
      jLabel_StepSizeX = new javax.swing.JLabel();
      jTextField_StepSizeX = new javax.swing.JTextField();
      jLabel_StepSizeY = new javax.swing.JLabel();
      jTextField_StepSizeY = new javax.swing.JTextField();
      jLabel_MosaicNFov = new javax.swing.JLabel();
      jTextField_MosaicNFov = new javax.swing.JTextField();
      jPanel3 = new javax.swing.JPanel();
      jLabel_AcqFolderName = new javax.swing.JLabel();
      jLabel_BaseFileName = new javax.swing.JLabel();
      jTextField_BaseFileName = new javax.swing.JTextField();
      jTextField_AcqFolderName = new javax.swing.JTextField();
      jButton_BrowseAcqFolder = new javax.swing.JButton();
      jPanel4 = new javax.swing.JPanel();
      jLabel_EmccdCamName = new javax.swing.JLabel();
      jLabel_EmccdExposureTime = new javax.swing.JLabel();
      jLabel_PhCamName = new javax.swing.JLabel();
      jLabel_PhExposureTime = new javax.swing.JLabel();
      jLabel_PhCamDelayTime = new javax.swing.JLabel();
      jCheckBox_ConvertPhExposureMsToSec = new javax.swing.JCheckBox();
      jTextField_EmccdCamName = new javax.swing.JTextField();
      jTextField_EmccdExposureTime = new javax.swing.JTextField();
      jTextField_PhCamName = new javax.swing.JTextField();
      jTextField_PhExposureTime = new javax.swing.JTextField();
      jTextField_PhCamDelayTime = new javax.swing.JTextField();
      jLabel_EmccdNumFrames = new javax.swing.JLabel();
      jTextField_EmccdNumFrames = new javax.swing.JTextField();
      jPanel2 = new javax.swing.JPanel();
      jLabel_ExcitationDacName = new javax.swing.JLabel();
      jLabel_ExcitationTtlName = new javax.swing.JLabel();
      jLabel_ActivationDacName = new javax.swing.JLabel();
      jLabel_ActivationTtlName = new javax.swing.JLabel();
      jLabel_LaserShutterTtlName = new javax.swing.JLabel();
      jLabel_PhLampTtlName = new javax.swing.JLabel();
      jTextField_ExcitationDacLabel = new javax.swing.JTextField();
      jTextField_ExcitationTtlLabel = new javax.swing.JTextField();
      jTextField_ActivationLabel = new javax.swing.JTextField();
      jTextField_ActivationTtlLabel = new javax.swing.JTextField();
      jTextField_LaserShutterTtLabel = new javax.swing.JTextField();
      jTextField_PhLampTtlLabel = new javax.swing.JTextField();
      jTextField_ExcitationDacProp = new javax.swing.JTextField();
      jTextField_PhLampTtlProp = new javax.swing.JTextField();
      jTextField_ExcitationTtlProp = new javax.swing.JTextField();
      jTextField_ActivationDacProp = new javax.swing.JTextField();
      jTextField_ActivationTtlProp = new javax.swing.JTextField();
      jTextField_LaserShutterTtlProp = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      jPanel5 = new javax.swing.JPanel();
      jPanel6 = new javax.swing.JPanel();
      jLabel4 = new javax.swing.JLabel();
      jTextField_Counting_bgRad = new javax.swing.JTextField();
      jPanel7 = new javax.swing.JPanel();
      jLabel6 = new javax.swing.JLabel();
      jComboBox_Counting_SelectAlg = new javax.swing.JComboBox();
      jLabel3 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      jTextField_Counting_blurRad = new javax.swing.JTextField();
      jLabel7 = new javax.swing.JLabel();
      jLabel8 = new javax.swing.JLabel();
      jComboBox_Counting_localMethod = new javax.swing.JComboBox();
      jLabel9 = new javax.swing.JLabel();
      jLabel10 = new javax.swing.JLabel();
      jLabel11 = new javax.swing.JLabel();
      jTextField_Counting_LocalRad = new javax.swing.JTextField();
      jTextField_Counting_LocalPar1 = new javax.swing.JTextField();
      jTextField_Counting_Par2 = new javax.swing.JTextField();
      jPanel8 = new javax.swing.JPanel();
      jLabel12 = new javax.swing.JLabel();
      jLabel13 = new javax.swing.JLabel();
      jTextField_Counting_maxPixSize = new javax.swing.JTextField();
      jTextField_Counting_minPixSize = new javax.swing.JTextField();
      jPanel9 = new javax.swing.JPanel();
      jLabel14 = new javax.swing.JLabel();
      jComboBox_roiCamera = new javax.swing.JComboBox();
      jLabel15 = new javax.swing.JLabel();
      jLabel16 = new javax.swing.JLabel();
      jLabel17 = new javax.swing.JLabel();
      jLabel18 = new javax.swing.JLabel();
      jTextField_roiX = new javax.swing.JTextField();
      jTextField_roiY = new javax.swing.JTextField();
      jTextField_roiW = new javax.swing.JTextField();
      jTextField_roiH = new javax.swing.JTextField();
      jButton_Apply = new javax.swing.JButton();
      jButton_Cancel = new javax.swing.JButton();
      jButton_Ok = new javax.swing.JButton();
      jButton_SaveSettingsAsDefault = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("HTPALM options");

      jLabel_StepSizeX.setText("Step size X (um):");

      jTextField_StepSizeX.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_StepSizeXActionPerformed(evt);
         }
      });

      jLabel_StepSizeY.setText("Step size Y (um):");

      jTextField_StepSizeY.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_StepSizeYActionPerformed(evt);
         }
      });

      jLabel_MosaicNFov.setText("Number of FOV:");
      jLabel_MosaicNFov.setToolTipText("Number of fields of view to be acquired");

      jTextField_MosaicNFov.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_MosaicNFovActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel_StepSizeX)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jTextField_StepSizeX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel_StepSizeY)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jTextField_StepSizeY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel_MosaicNFov)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jTextField_MosaicNFov, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(208, Short.MAX_VALUE))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_StepSizeX)
               .add(jTextField_StepSizeX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_StepSizeY)
               .add(jTextField_StepSizeY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_MosaicNFov)
               .add(jTextField_MosaicNFov, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(250, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("Mosaic settings", jPanel1);

      jLabel_AcqFolderName.setText("Acquisition folder:");

      jLabel_BaseFileName.setText("Base file name:");

      jTextField_BaseFileName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_BaseFileName.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_BaseFileNameActionPerformed(evt);
         }
      });

      jTextField_AcqFolderName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_AcqFolderName.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_AcqFolderNameActionPerformed(evt);
         }
      });

      jButton_BrowseAcqFolder.setText("...");
      jButton_BrowseAcqFolder.setToolTipText("Browse");
      jButton_BrowseAcqFolder.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_BrowseAcqFolderActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jLabel_AcqFolderName)
                  .add(1, 4, Short.MAX_VALUE))
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jLabel_BaseFileName)
                  .add(0, 0, Short.MAX_VALUE)))
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_BaseFileName)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_AcqFolderName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButton_BrowseAcqFolder, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_AcqFolderName)
               .add(jTextField_AcqFolderName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jButton_BrowseAcqFolder))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_BaseFileName)
               .add(jTextField_BaseFileName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(273, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("File locations", jPanel3);

      jLabel_EmccdCamName.setText("EMCCD camera name:");

      jLabel_EmccdExposureTime.setText("EMCCD exposure (ms):");

      jLabel_PhCamName.setText("PH camera name:");

      jLabel_PhExposureTime.setText("PH exposure time (ms):");

      jLabel_PhCamDelayTime.setText("PH delay time (ms):");
      jLabel_PhCamDelayTime.setToolTipText("Time to wait after PH acquisition");

      jCheckBox_ConvertPhExposureMsToSec.setText("Convert PH exposure time to seconds");
      jCheckBox_ConvertPhExposureMsToSec.setToolTipText("Workaround to correct for TISCam bug");
      jCheckBox_ConvertPhExposureMsToSec.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox_ConvertPhExposureMsToSecActionPerformed(evt);
         }
      });

      jTextField_EmccdCamName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_EmccdCamName.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_EmccdCamNameActionPerformed(evt);
         }
      });

      jTextField_EmccdExposureTime.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_EmccdExposureTime.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_EmccdExposureTimeActionPerformed(evt);
         }
      });

      jTextField_PhCamName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_PhCamName.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_PhCamNameActionPerformed(evt);
         }
      });

      jTextField_PhExposureTime.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_PhExposureTime.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_PhExposureTimeActionPerformed(evt);
         }
      });

      jTextField_PhCamDelayTime.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_PhCamDelayTime.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_PhCamDelayTimeActionPerformed(evt);
         }
      });

      jLabel_EmccdNumFrames.setText("EMCCD num. frames:");

      jTextField_EmccdNumFrames.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_EmccdNumFrames.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_EmccdNumFramesActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel4Layout.createSequentialGroup()
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel_EmccdCamName)
                     .add(jLabel_EmccdExposureTime))
                  .add(66, 66, 66)
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_EmccdCamName)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_EmccdExposureTime)))
               .add(jPanel4Layout.createSequentialGroup()
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel_PhCamName)
                     .add(jLabel_PhExposureTime)
                     .add(jLabel_PhCamDelayTime))
                  .add(65, 65, 65)
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_PhCamName)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_PhExposureTime)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_PhCamDelayTime)))
               .add(jPanel4Layout.createSequentialGroup()
                  .add(10, 10, 10)
                  .add(jCheckBox_ConvertPhExposureMsToSec)
                  .add(117, 117, 117))
               .add(jPanel4Layout.createSequentialGroup()
                  .add(jLabel_EmccdNumFrames)
                  .add(75, 75, 75)
                  .add(jTextField_EmccdNumFrames)))
            .addContainerGap())
      );
      jPanel4Layout.setVerticalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_EmccdCamName)
               .add(jTextField_EmccdCamName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_EmccdExposureTime)
               .add(jTextField_EmccdExposureTime, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(4, 4, 4)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_EmccdNumFrames)
               .add(jTextField_EmccdNumFrames, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_PhCamName)
               .add(jTextField_PhCamName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_PhExposureTime)
               .add(jTextField_PhExposureTime, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel_PhCamDelayTime)
               .add(jTextField_PhCamDelayTime, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jCheckBox_ConvertPhExposureMsToSec)
            .addContainerGap(144, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("Camera settings", jPanel4);

      jLabel_ExcitationDacName.setText("Excitation laser DAC Name:");

      jLabel_ExcitationTtlName.setText("Excitation laser TTL Name:");

      jLabel_ActivationDacName.setText("Photoactivation laser DAC Name:");

      jLabel_ActivationTtlName.setText("Photoactivation laser TTL Name:");

      jLabel_LaserShutterTtlName.setText("Laser shutter TTL name:");

      jLabel_PhLampTtlName.setText("PH lamp TTL name:");

      jTextField_ExcitationDacLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ExcitationDacLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ExcitationDacLabelActionPerformed(evt);
         }
      });

      jTextField_ExcitationTtlLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ExcitationTtlLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ExcitationTtlLabelActionPerformed(evt);
         }
      });

      jTextField_ActivationLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ActivationLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ActivationLabelActionPerformed(evt);
         }
      });

      jTextField_ActivationTtlLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ActivationTtlLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ActivationTtlLabelActionPerformed(evt);
         }
      });

      jTextField_LaserShutterTtLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_LaserShutterTtLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_LaserShutterTtLabelActionPerformed(evt);
         }
      });

      jTextField_PhLampTtlLabel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_PhLampTtlLabel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_PhLampTtlLabelActionPerformed(evt);
         }
      });

      jTextField_ExcitationDacProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ExcitationDacProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ExcitationDacPropActionPerformed(evt);
         }
      });

      jTextField_PhLampTtlProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_PhLampTtlProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_PhLampTtlPropActionPerformed(evt);
         }
      });

      jTextField_ExcitationTtlProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ExcitationTtlProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ExcitationTtlPropActionPerformed(evt);
         }
      });

      jTextField_ActivationDacProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ActivationDacProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ActivationDacPropActionPerformed(evt);
         }
      });

      jTextField_ActivationTtlProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_ActivationTtlProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_ActivationTtlPropActionPerformed(evt);
         }
      });

      jTextField_LaserShutterTtlProp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_LaserShutterTtlProp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField_LaserShutterTtlPropActionPerformed(evt);
         }
      });

      jLabel1.setText("Label:");

      jLabel2.setText("Property:");

      org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel_ExcitationDacName)
               .add(jLabel_ExcitationTtlName)
               .add(jLabel_ActivationDacName)
               .add(jLabel_ActivationTtlName)
               .add(jLabel_LaserShutterTtlName)
               .add(jLabel_PhLampTtlName))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_LaserShutterTtLabel)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_ActivationTtlLabel)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_ActivationLabel)
               .add(jTextField_ExcitationTtlLabel)
               .add(jTextField_PhLampTtlLabel)
               .add(jTextField_ExcitationDacLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel1))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_ActivationTtlProp)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_ActivationDacProp)
               .add(jTextField_ExcitationTtlProp)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_LaserShutterTtlProp)
               .add(jTextField_PhLampTtlProp)
               .add(jTextField_ExcitationDacProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel2))
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .add(13, 13, 13)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel2)
               .add(jLabel1))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(jPanel2Layout.createSequentialGroup()
                  .add(jTextField_ExcitationDacProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jTextField_ExcitationTtlProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jTextField_ActivationDacProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jTextField_ActivationTtlProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jTextField_LaserShutterTtlProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jTextField_PhLampTtlProp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel2Layout.createSequentialGroup()
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_ExcitationDacName)
                     .add(jTextField_ExcitationDacLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_ExcitationTtlName)
                     .add(jTextField_ExcitationTtlLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_ActivationDacName)
                     .add(jTextField_ActivationLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_ActivationTtlName)
                     .add(jTextField_ActivationTtlLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_LaserShutterTtlName)
                     .add(jTextField_LaserShutterTtLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel_PhLampTtlName)
                     .add(jTextField_PhLampTtlLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(150, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("Lasers & shutters", jPanel2);

      jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Background subtraction"));

      jLabel4.setText("Rolling ball radius:");
      jLabel4.setToolTipText("Rolling ball radius (pixels)");

      jTextField_Counting_bgRad.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_Counting_bgRad.setText("50.0");

      org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
      jPanel6.setLayout(jPanel6Layout);
      jPanel6Layout.setHorizontalGroup(
         jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .add(jLabel4)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jTextField_Counting_bgRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel6Layout.setVerticalGroup(
         jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(jLabel4)
            .add(jTextField_Counting_bgRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
      );

      jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Image segmentation"));

      jLabel6.setText("Algorithm:");

      jComboBox_Counting_SelectAlg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LoG", "Sauvola" }));
      jComboBox_Counting_SelectAlg.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox_Counting_SelectAlgActionPerformed(evt);
         }
      });

      jLabel3.setText("LoG parameters:");

      jLabel5.setText("Blur radius:");
      jLabel5.setToolTipText("Gaussian blur radius (pixels)");

      jTextField_Counting_blurRad.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_Counting_blurRad.setText("2.0");

      jLabel7.setText("Local threshold parameters:");

      jLabel8.setText("Method:");

      jComboBox_Counting_localMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

      jLabel9.setText("Radius:");

      jLabel10.setText("Par 1:");
      jLabel10.setToolTipText("0 indicates default value");

      jLabel11.setText("Par 2:");
      jLabel11.setToolTipText("0 indicates default value");

      jTextField_Counting_LocalRad.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_Counting_LocalRad.setText("15.0");

      jTextField_Counting_LocalPar1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_Counting_LocalPar1.setText("0.0");

      jTextField_Counting_Par2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      jTextField_Counting_Par2.setText("0.0");

      org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
      jPanel7.setLayout(jPanel7Layout);
      jPanel7Layout.setHorizontalGroup(
         jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel7Layout.createSequentialGroup()
                  .add(jLabel6)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jComboBox_Counting_SelectAlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel7Layout.createSequentialGroup()
                  .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel3)
                     .add(jPanel7Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jTextField_Counting_blurRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                  .add(21, 21, 21)
                  .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel7)
                     .add(jPanel7Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                              .add(jLabel8)
                              .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                              .add(jComboBox_Counting_localMethod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                              .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jLabel9)
                                 .add(jLabel10)
                                 .add(jLabel11))
                              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                 .add(jTextField_Counting_Par2)
                                 .add(jTextField_Counting_LocalPar1)
                                 .add(jTextField_Counting_LocalRad, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))))))))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel7Layout.setVerticalGroup(
         jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel7Layout.createSequentialGroup()
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel6)
               .add(jComboBox_Counting_SelectAlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel3)
               .add(jLabel7))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel5)
               .add(jTextField_Counting_blurRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel8)
               .add(jComboBox_Counting_localMethod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel9)
               .add(jTextField_Counting_LocalRad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel10)
               .add(jTextField_Counting_LocalPar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel11)
               .add(jTextField_Counting_Par2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Morphology filtering"));

      jLabel12.setText("Min size bacteria:");
      jLabel12.setToolTipText("Minimum size of bacteria (pixel^2)");

      jLabel13.setText("Max size bacteria:");
      jLabel13.setToolTipText("Minimum size of bacteria (pixel^2)");

      jTextField_Counting_maxPixSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextField_Counting_maxPixSize.setText("Inf");
      jTextField_Counting_maxPixSize.setToolTipText("Maximum size of bacteria (no. of pixels)");

      jTextField_Counting_minPixSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextField_Counting_minPixSize.setText("0");
      jTextField_Counting_minPixSize.setToolTipText("Minimum size of bacteria (no. of pixels)");

      org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
      jPanel8.setLayout(jPanel8Layout);
      jPanel8Layout.setHorizontalGroup(
         jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel8Layout.createSequentialGroup()
                  .add(jLabel12)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jTextField_Counting_minPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel8Layout.createSequentialGroup()
                  .add(jLabel13)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 5, Short.MAX_VALUE)
                  .add(jTextField_Counting_maxPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .add(186, 186, 186))
      );
      jPanel8Layout.setVerticalGroup(
         jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel8Layout.createSequentialGroup()
            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel12)
               .add(jTextField_Counting_minPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel13)
               .add(jTextField_Counting_maxPixSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
      );

      org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
      jPanel5.setLayout(jPanel5Layout);
      jPanel5Layout.setHorizontalGroup(
         jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .add(jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
      );
      jPanel5Layout.setVerticalGroup(
         jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 179, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("FOV filtering", jPanel5);

      jLabel14.setText("Acquisition channel:");

      jComboBox_roiCamera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Phase contrast", "Fluorescence" }));
      jComboBox_roiCamera.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox_roiCameraActionPerformed(evt);
         }
      });

      jLabel15.setText("ROI start X:");
      jLabel15.setToolTipText("ROI start X (pixels)");

      jLabel16.setText("ROI start Y:");
      jLabel16.setToolTipText("ROI start Y (pixels)");

      jLabel17.setText("ROI width:");
      jLabel17.setToolTipText("ROI width (pixels)");

      jLabel18.setText("ROI height:");
      jLabel18.setToolTipText("ROI height (pixels)");

      jTextField_roiX.setText("0");

      jTextField_roiY.setText("0");

      jTextField_roiW.setText("0");

      jTextField_roiH.setText("0");

      org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
      jPanel9.setLayout(jPanel9Layout);
      jPanel9Layout.setHorizontalGroup(
         jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel9Layout.createSequentialGroup()
                  .add(jLabel14)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jComboBox_roiCamera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel9Layout.createSequentialGroup()
                  .add(10, 10, 10)
                  .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                     .add(jLabel16)
                     .add(jLabel15)
                     .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel17)
                     .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel18))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jTextField_roiX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jTextField_roiY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jTextField_roiW, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jTextField_roiH, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(118, Short.MAX_VALUE))
      );
      jPanel9Layout.setVerticalGroup(
         jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel14)
               .add(jComboBox_roiCamera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel15)
               .add(jTextField_roiX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel16)
               .add(jTextField_roiY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel17)
               .add(jTextField_roiW, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel18)
               .add(jTextField_roiH, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(198, Short.MAX_VALUE))
      );

      jTabbedPane_Settings.addTab("Camera ROIs", jPanel9);

      jButton_Apply.setText("Apply");
      jButton_Apply.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_ApplyActionPerformed(evt);
         }
      });

      jButton_Cancel.setText("Cancel");
      jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_CancelActionPerformed(evt);
         }
      });

      jButton_Ok.setText("OK");
      jButton_Ok.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_OkActionPerformed(evt);
         }
      });

      jButton_SaveSettingsAsDefault.setText("Set as default");
      jButton_SaveSettingsAsDefault.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_SaveSettingsAsDefaultActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
               .add(layout.createSequentialGroup()
                  .add(jButton_SaveSettingsAsDefault)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jButton_Ok)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jButton_Apply)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jButton_Cancel))
               .add(jTabbedPane_Settings, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 359, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jTabbedPane_Settings, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jButton_Apply)
               .add(jButton_Cancel)
               .add(jButton_Ok)
               .add(jButton_SaveSettingsAsDefault)))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
      dispose();
   }//GEN-LAST:event_jButton_CancelActionPerformed

   private void jButton_ApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ApplyActionPerformed
      updateSettings();
      updateConfig();
   }//GEN-LAST:event_jButton_ApplyActionPerformed

   private void jTextField_ExcitationDacLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ExcitationDacLabelActionPerformed
      newConfig_.setLaserExDacName(jTextField_ExcitationDacLabel.getText(),0);
   }//GEN-LAST:event_jTextField_ExcitationDacLabelActionPerformed

   private void jTextField_EmccdCamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_EmccdCamNameActionPerformed
      newConfig_.camEmccdName_ = jTextField_EmccdCamName.getText();
   }//GEN-LAST:event_jTextField_EmccdCamNameActionPerformed

   private void jTextField_EmccdExposureTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_EmccdExposureTimeActionPerformed
     newConfig_.camEmccdExposureMs_ = Double.parseDouble(jTextField_EmccdExposureTime.getText());
   }//GEN-LAST:event_jTextField_EmccdExposureTimeActionPerformed

   private void jTextField_PhCamDelayTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PhCamDelayTimeActionPerformed
     newConfig_.camPhDelayMs_= Double.parseDouble(jTextField_PhCamDelayTime.getText());
   }//GEN-LAST:event_jTextField_PhCamDelayTimeActionPerformed

   private void jTextField_PhCamNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PhCamNameActionPerformed
     newConfig_.camPhName_ = jTextField_PhCamName.getText(); 
   }//GEN-LAST:event_jTextField_PhCamNameActionPerformed

   private void jTextField_PhExposureTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PhExposureTimeActionPerformed
     newConfig_.camPhExposureMs_= Double.parseDouble(jTextField_PhExposureTime.getText());
   }//GEN-LAST:event_jTextField_PhExposureTimeActionPerformed

   private void jTextField_BaseFileNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_BaseFileNameActionPerformed
      newConfig_.fileBaseName_ = jTextField_BaseFileName.getText();
   }//GEN-LAST:event_jTextField_BaseFileNameActionPerformed

   private void jTextField_AcqFolderNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_AcqFolderNameActionPerformed
      newConfig_.fileAcqFolder_ = jTextField_AcqFolderName.getText();
   }//GEN-LAST:event_jTextField_AcqFolderNameActionPerformed

   private void jButton_BrowseAcqFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BrowseAcqFolderActionPerformed
      //select a root directory
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select target directory");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
         try {
            File f = chooser.getSelectedFile();
            String folderName = f.getCanonicalPath();
            newConfig_.fileAcqFolder_ = folderName;
            jTextField_AcqFolderName.setText(newConfig_.fileAcqFolder_);
            
         } catch (IOException ex) {
            ReportingUtils.logError(ex, "Unable to get path");
         }
      }
   }//GEN-LAST:event_jButton_BrowseAcqFolderActionPerformed

   private void jTextField_StepSizeXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_StepSizeXActionPerformed
     newConfig_.mosaicStepSizeX_= Double.parseDouble(jTextField_StepSizeX.getText());
   }//GEN-LAST:event_jTextField_StepSizeXActionPerformed

   private void jTextField_StepSizeYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_StepSizeYActionPerformed
     newConfig_.mosaicStepSizeY_= Double.parseDouble(jTextField_StepSizeY.getText());
   }//GEN-LAST:event_jTextField_StepSizeYActionPerformed

   private void jButton_SaveSettingsAsDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveSettingsAsDefaultActionPerformed
      updateSettings();
      newConfig_.saveConfig();
   }//GEN-LAST:event_jButton_SaveSettingsAsDefaultActionPerformed

   private void jButton_OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OkActionPerformed
      updateSettings();
      updateConfig();
      dispose();
   }//GEN-LAST:event_jButton_OkActionPerformed

   private void jCheckBox_ConvertPhExposureMsToSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ConvertPhExposureMsToSecActionPerformed
      newConfig_.setCamConvertPhExposureToSec(jCheckBox_ConvertPhExposureMsToSec.isSelected());
   }//GEN-LAST:event_jCheckBox_ConvertPhExposureMsToSecActionPerformed

   private void jTextField_ExcitationTtlLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ExcitationTtlLabelActionPerformed
      newConfig_.setLaserExTtlName(jTextField_ExcitationTtlLabel.getText(),0);
   }//GEN-LAST:event_jTextField_ExcitationTtlLabelActionPerformed

   private void jTextField_ActivationLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ActivationLabelActionPerformed
      newConfig_.setLaserActDacName(jTextField_ActivationLabel.getText(),0);
   }//GEN-LAST:event_jTextField_ActivationLabelActionPerformed

   private void jTextField_ActivationTtlLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ActivationTtlLabelActionPerformed
      newConfig_.setLaserActTtlName(jTextField_ActivationTtlLabel.getText(),0);
   }//GEN-LAST:event_jTextField_ActivationTtlLabelActionPerformed

   private void jTextField_LaserShutterTtLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_LaserShutterTtLabelActionPerformed
      newConfig_.setLaserShutterTtlName(jTextField_LaserShutterTtLabel.getText(),0);
   }//GEN-LAST:event_jTextField_LaserShutterTtLabelActionPerformed

   private void jTextField_PhLampTtlLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PhLampTtlLabelActionPerformed
      newConfig_.setPhLampTtlName(jTextField_PhLampTtlLabel.getText(),0);
   }//GEN-LAST:event_jTextField_PhLampTtlLabelActionPerformed

   private void jTextField_MosaicNFovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_MosaicNFovActionPerformed
      newConfig_.setMosaicNFov(Integer.parseInt(jTextField_MosaicNFov.getText()));
   }//GEN-LAST:event_jTextField_MosaicNFovActionPerformed

   private void jTextField_ExcitationDacPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ExcitationDacPropActionPerformed
      newConfig_.setLaserExDacName(jTextField_ExcitationDacProp.getText(),1);
   }//GEN-LAST:event_jTextField_ExcitationDacPropActionPerformed

   private void jTextField_PhLampTtlPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PhLampTtlPropActionPerformed
      newConfig_.setPhLampTtlName(jTextField_PhLampTtlProp.getText(),1);
   }//GEN-LAST:event_jTextField_PhLampTtlPropActionPerformed

   private void jTextField_ExcitationTtlPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ExcitationTtlPropActionPerformed
      newConfig_.setLaserExTtlName(jTextField_ExcitationTtlProp.getText(),1);
   }//GEN-LAST:event_jTextField_ExcitationTtlPropActionPerformed

   private void jTextField_ActivationDacPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ActivationDacPropActionPerformed
      newConfig_.setLaserActDacName(jTextField_ActivationDacProp.getText(),1);
   }//GEN-LAST:event_jTextField_ActivationDacPropActionPerformed

   private void jTextField_ActivationTtlPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ActivationTtlPropActionPerformed
      newConfig_.setLaserActTtlName(jTextField_ActivationTtlProp.getText(),1);
   }//GEN-LAST:event_jTextField_ActivationTtlPropActionPerformed

   private void jTextField_LaserShutterTtlPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_LaserShutterTtlPropActionPerformed
      newConfig_.setLaserShutterTtlName(jTextField_LaserShutterTtlProp.getText(),1);
   }//GEN-LAST:event_jTextField_LaserShutterTtlPropActionPerformed

   private void jTextField_EmccdNumFramesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_EmccdNumFramesActionPerformed
      newConfig_.camEmccdNumFrames_= Integer.parseInt(jTextField_EmccdNumFrames.getText());
   }//GEN-LAST:event_jTextField_EmccdNumFramesActionPerformed

   private void jComboBox_Counting_SelectAlgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Counting_SelectAlgActionPerformed
   }//GEN-LAST:event_jComboBox_Counting_SelectAlgActionPerformed

   private void jComboBox_roiCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_roiCameraActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jComboBox_roiCameraActionPerformed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton_Apply;
   private javax.swing.JButton jButton_BrowseAcqFolder;
   private javax.swing.JButton jButton_Cancel;
   private javax.swing.JButton jButton_Ok;
   private javax.swing.JButton jButton_SaveSettingsAsDefault;
   private javax.swing.JCheckBox jCheckBox_ConvertPhExposureMsToSec;
   private javax.swing.JComboBox jComboBox_Counting_SelectAlg;
   private javax.swing.JComboBox jComboBox_Counting_localMethod;
   private javax.swing.JComboBox jComboBox_roiCamera;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel14;
   private javax.swing.JLabel jLabel15;
   private javax.swing.JLabel jLabel16;
   private javax.swing.JLabel jLabel17;
   private javax.swing.JLabel jLabel18;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JLabel jLabel_AcqFolderName;
   private javax.swing.JLabel jLabel_ActivationDacName;
   private javax.swing.JLabel jLabel_ActivationTtlName;
   private javax.swing.JLabel jLabel_BaseFileName;
   private javax.swing.JLabel jLabel_EmccdCamName;
   private javax.swing.JLabel jLabel_EmccdExposureTime;
   private javax.swing.JLabel jLabel_EmccdNumFrames;
   private javax.swing.JLabel jLabel_ExcitationDacName;
   private javax.swing.JLabel jLabel_ExcitationTtlName;
   private javax.swing.JLabel jLabel_LaserShutterTtlName;
   private javax.swing.JLabel jLabel_MosaicNFov;
   private javax.swing.JLabel jLabel_PhCamDelayTime;
   private javax.swing.JLabel jLabel_PhCamName;
   private javax.swing.JLabel jLabel_PhExposureTime;
   private javax.swing.JLabel jLabel_PhLampTtlName;
   private javax.swing.JLabel jLabel_StepSizeX;
   private javax.swing.JLabel jLabel_StepSizeY;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JPanel jPanel6;
   private javax.swing.JPanel jPanel7;
   private javax.swing.JPanel jPanel8;
   private javax.swing.JPanel jPanel9;
   private javax.swing.JTabbedPane jTabbedPane_Settings;
   private javax.swing.JTextField jTextField_AcqFolderName;
   private javax.swing.JTextField jTextField_ActivationDacProp;
   private javax.swing.JTextField jTextField_ActivationLabel;
   private javax.swing.JTextField jTextField_ActivationTtlLabel;
   private javax.swing.JTextField jTextField_ActivationTtlProp;
   private javax.swing.JTextField jTextField_BaseFileName;
   private javax.swing.JTextField jTextField_Counting_LocalPar1;
   private javax.swing.JTextField jTextField_Counting_LocalRad;
   private javax.swing.JTextField jTextField_Counting_Par2;
   private javax.swing.JTextField jTextField_Counting_bgRad;
   private javax.swing.JTextField jTextField_Counting_blurRad;
   private javax.swing.JTextField jTextField_Counting_maxPixSize;
   private javax.swing.JTextField jTextField_Counting_minPixSize;
   private javax.swing.JTextField jTextField_EmccdCamName;
   private javax.swing.JTextField jTextField_EmccdExposureTime;
   private javax.swing.JTextField jTextField_EmccdNumFrames;
   private javax.swing.JTextField jTextField_ExcitationDacLabel;
   private javax.swing.JTextField jTextField_ExcitationDacProp;
   private javax.swing.JTextField jTextField_ExcitationTtlLabel;
   private javax.swing.JTextField jTextField_ExcitationTtlProp;
   private javax.swing.JTextField jTextField_LaserShutterTtLabel;
   private javax.swing.JTextField jTextField_LaserShutterTtlProp;
   private javax.swing.JTextField jTextField_MosaicNFov;
   private javax.swing.JTextField jTextField_PhCamDelayTime;
   private javax.swing.JTextField jTextField_PhCamName;
   private javax.swing.JTextField jTextField_PhExposureTime;
   private javax.swing.JTextField jTextField_PhLampTtlLabel;
   private javax.swing.JTextField jTextField_PhLampTtlProp;
   private javax.swing.JTextField jTextField_StepSizeX;
   private javax.swing.JTextField jTextField_StepSizeY;
   private javax.swing.JTextField jTextField_roiH;
   private javax.swing.JTextField jTextField_roiW;
   private javax.swing.JTextField jTextField_roiX;
   private javax.swing.JTextField jTextField_roiY;
   // End of variables declaration//GEN-END:variables
}
