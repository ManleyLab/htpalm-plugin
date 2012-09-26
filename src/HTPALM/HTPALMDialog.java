/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HTPALM;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HTPALMDialog extends javax.swing.JDialog {

   HTPALM htpalm;
   /**
    * Creates new form HTPALMDialog
    */
   public HTPALMDialog(java.awt.Frame parent, boolean modal, HTPALM htpalm) {
      super(parent, modal);
      initComponents();

      this.htpalm = htpalm;
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_LaserControl = new javax.swing.ButtonGroup();
        jPanel_PosControl = new javax.swing.JPanel();
        jButton_GotoLastFov = new javax.swing.JButton();
        jButton_GotoNextFov = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel_CurrentFovNumber = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel_Initialize = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_StartX = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_StartY = new javax.swing.JTextField();
        jButton_SetStartAsCurrentPos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_CurrentX = new javax.swing.JLabel();
        jButton_SetPosAsOrigin = new javax.swing.JButton();
        jLabel_CurrentY = new javax.swing.JLabel();
        jPanel_LaserControl = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton_LaserControlManual = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField_PhotoactivationPowerNumber = new javax.swing.JTextField();
        jTextField_ExcitationPowerNumber = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton_LaserControlAuto = new javax.swing.JRadioButton();
        jButton_OpenAutoLase = new javax.swing.JButton();
        jPanel_BactDetection = new javax.swing.JPanel();
        jCheckBox_ExcludeBadFov = new javax.swing.JCheckBox();
        jButton_OpenBactConfig = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton_AcquireAllFov = new javax.swing.JButton();
        jButton_Abort = new javax.swing.JButton();
        jButton_OpenInitOptions = new javax.swing.JButton();
        jButton_Initialize = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HTPALM Control");

        jPanel_PosControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Manual control"));

        jButton_GotoLastFov.setText("<");

        jButton_GotoNextFov.setText(">");

        jLabel2.setText("Current FOV:");

        jLabel_CurrentFovNumber.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_CurrentFovNumber.setText("0");

        jButton3.setText("Acquire 1 FOV");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel_PosControlLayout = new org.jdesktop.layout.GroupLayout(jPanel_PosControl);
        jPanel_PosControl.setLayout(jPanel_PosControlLayout);
        jPanel_PosControlLayout.setHorizontalGroup(
            jPanel_PosControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_PosControlLayout.createSequentialGroup()
                .add(jPanel_PosControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel_PosControlLayout.createSequentialGroup()
                        .add(29, 29, 29)
                        .add(jButton_GotoLastFov)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel_CurrentFovNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton_GotoNextFov)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel_PosControlLayout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton3)))
                .addContainerGap())
        );
        jPanel_PosControlLayout.setVerticalGroup(
            jPanel_PosControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_PosControlLayout.createSequentialGroup()
                .add(jPanel_PosControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton_GotoLastFov)
                    .add(jButton_GotoNextFov)
                    .add(jLabel2)
                    .add(jLabel_CurrentFovNumber))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton3))
        );

        jPanel_Initialize.setBorder(javax.swing.BorderFactory.createTitledBorder("Mosaic setup"));

        jLabel3.setText("Start position:");

        jLabel4.setText("X");

        jTextField_StartX.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextField_StartX.setText("00.00");
        jTextField_StartX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_StartXActionPerformed(evt);
            }
        });

        jLabel5.setText("Y");

        jTextField_StartY.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextField_StartY.setText("00.00");
        jTextField_StartY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_StartYActionPerformed(evt);
            }
        });

        jButton_SetStartAsCurrentPos.setText("Use current position");

        jLabel9.setText("Current position:");

        jLabel10.setText("X");

        jLabel11.setText("Y");

        jLabel_CurrentX.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_CurrentX.setText("00.00");

        jButton_SetPosAsOrigin.setText("Set as origin");
        jButton_SetPosAsOrigin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SetPosAsOriginActionPerformed(evt);
            }
        });

        jLabel_CurrentY.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_CurrentY.setText("00.00");

        org.jdesktop.layout.GroupLayout jPanel_InitializeLayout = new org.jdesktop.layout.GroupLayout(jPanel_Initialize);
        jPanel_Initialize.setLayout(jPanel_InitializeLayout);
        jPanel_InitializeLayout.setHorizontalGroup(
            jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_InitializeLayout.createSequentialGroup()
                    .add(99, 99, 99)
                    .add(jButton_SetStartAsCurrentPos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(jPanel_InitializeLayout.createSequentialGroup()
                    .add(102, 102, 102)
                    .add(jButton_SetPosAsOrigin, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .add(jPanel_InitializeLayout.createSequentialGroup()
                .add(11, 11, 11)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel10)
                .add(10, 10, 10)
                .add(jLabel_CurrentX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel11)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel_CurrentY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanel_InitializeLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jPanel_InitializeLayout.createSequentialGroup()
                        .add(91, 91, 91)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jTextField_StartX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jTextField_StartY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel_InitializeLayout.setVerticalGroup(
            jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel_InitializeLayout.createSequentialGroup()
                .add(jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(jLabel10)
                    .add(jLabel11)
                    .add(jLabel_CurrentX)
                    .add(jLabel_CurrentY))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_SetPosAsOrigin)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel_InitializeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jLabel4)
                    .add(jTextField_StartX, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(jTextField_StartY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(4, 4, 4)
                .add(jButton_SetStartAsCurrentPos))
        );

        jPanel_LaserControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Laser control"));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup_LaserControl.add(jRadioButton_LaserControlManual);
        jRadioButton_LaserControlManual.setText("Manual");
        jRadioButton_LaserControlManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_LaserControlManualActionPerformed(evt);
            }
        });

        jLabel6.setText("Excitation power:");

        jLabel7.setText("Photoactivation power:");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jRadioButton_LaserControlManual)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel7)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                .add(jLabel6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jTextField_ExcitationPowerNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(1, 1, 1))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField_PhotoactivationPowerNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jRadioButton_LaserControlManual)
                .add(0, 0, Short.MAX_VALUE))
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(jTextField_PhotoactivationPowerNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jTextField_ExcitationPowerNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup_LaserControl.add(jRadioButton_LaserControlAuto);
        jRadioButton_LaserControlAuto.setText("Automatic (TODO!)");

        jButton_OpenAutoLase.setText("Open Autolase");
        jButton_OpenAutoLase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OpenAutoLaseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jRadioButton_LaserControlAuto)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton_OpenAutoLase))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jRadioButton_LaserControlAuto)
                .add(jButton_OpenAutoLase))
        );

        org.jdesktop.layout.GroupLayout jPanel_LaserControlLayout = new org.jdesktop.layout.GroupLayout(jPanel_LaserControl);
        jPanel_LaserControl.setLayout(jPanel_LaserControlLayout);
        jPanel_LaserControlLayout.setHorizontalGroup(
            jPanel_LaserControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_LaserControlLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel_LaserControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_LaserControlLayout.setVerticalGroup(
            jPanel_LaserControlLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_LaserControlLayout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_BactDetection.setBorder(javax.swing.BorderFactory.createTitledBorder("Bacteria detection"));

        jCheckBox_ExcludeBadFov.setText("Automatically exclude bad FOVs (TODO!)");
        jCheckBox_ExcludeBadFov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_ExcludeBadFovActionPerformed(evt);
            }
        });

        jButton_OpenBactConfig.setText("Configure");

        org.jdesktop.layout.GroupLayout jPanel_BactDetectionLayout = new org.jdesktop.layout.GroupLayout(jPanel_BactDetection);
        jPanel_BactDetection.setLayout(jPanel_BactDetectionLayout);
        jPanel_BactDetectionLayout.setHorizontalGroup(
            jPanel_BactDetectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_BactDetectionLayout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox_ExcludeBadFov)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 13, Short.MAX_VALUE)
                .add(jButton_OpenBactConfig)
                .addContainerGap())
        );
        jPanel_BactDetectionLayout.setVerticalGroup(
            jPanel_BactDetectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_BactDetectionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jCheckBox_ExcludeBadFov)
                .add(jButton_OpenBactConfig))
        );

        jButton1.setText("Load...");

        jButton2.setText("Save as...");

        jButton_AcquireAllFov.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_AcquireAllFov.setText("Acquire all!");

        jButton_Abort.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Abort.setText("Abort all");

        jButton_OpenInitOptions.setText("Options");
        jButton_OpenInitOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OpenInitOptionsActionPerformed(evt);
            }
        });

        jButton_Initialize.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Initialize.setText("Initialize!");
        jButton_Initialize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InitializeActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(jPanel_Initialize, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jPanel_PosControl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton_AcquireAllFov, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton_Abort, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton_OpenInitOptions, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton_Initialize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel_LaserControl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel_BactDetection, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(layout.createSequentialGroup()
                        .add(jButton_Initialize)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton_AcquireAllFov)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton_Abort)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton_OpenInitOptions))
                    .add(jPanel_Initialize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(jPanel_PosControl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel_LaserControl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel_BactDetection, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   private void jTextField_StartXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_StartXActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jTextField_StartXActionPerformed

   private void jTextField_StartYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_StartYActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jTextField_StartYActionPerformed

   private void jButton_SetPosAsOriginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SetPosAsOriginActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton_SetPosAsOriginActionPerformed

   private void jButton_InitializeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InitializeActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton_InitializeActionPerformed

   private void jRadioButton_LaserControlManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_LaserControlManualActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jRadioButton_LaserControlManualActionPerformed

   private void jButton_OpenAutoLaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OpenAutoLaseActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton_OpenAutoLaseActionPerformed

   private void jCheckBox_ExcludeBadFovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ExcludeBadFovActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jCheckBox_ExcludeBadFovActionPerformed

   private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton3ActionPerformed

   private void jButton_OpenInitOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OpenInitOptionsActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton_OpenInitOptionsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_LaserControl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_Abort;
    private javax.swing.JButton jButton_AcquireAllFov;
    private javax.swing.JButton jButton_GotoLastFov;
    private javax.swing.JButton jButton_GotoNextFov;
    private javax.swing.JButton jButton_Initialize;
    private javax.swing.JButton jButton_OpenAutoLase;
    private javax.swing.JButton jButton_OpenBactConfig;
    private javax.swing.JButton jButton_OpenInitOptions;
    private javax.swing.JButton jButton_SetPosAsOrigin;
    private javax.swing.JButton jButton_SetStartAsCurrentPos;
    private javax.swing.JCheckBox jCheckBox_ExcludeBadFov;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_CurrentFovNumber;
    private javax.swing.JLabel jLabel_CurrentX;
    private javax.swing.JLabel jLabel_CurrentY;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_BactDetection;
    private javax.swing.JPanel jPanel_Initialize;
    private javax.swing.JPanel jPanel_LaserControl;
    private javax.swing.JPanel jPanel_PosControl;
    private javax.swing.JRadioButton jRadioButton_LaserControlAuto;
    private javax.swing.JRadioButton jRadioButton_LaserControlManual;
    private javax.swing.JTextField jTextField_ExcitationPowerNumber;
    private javax.swing.JTextField jTextField_PhotoactivationPowerNumber;
    private javax.swing.JTextField jTextField_StartX;
    private javax.swing.JTextField jTextField_StartY;
    // End of variables declaration//GEN-END:variables
}
