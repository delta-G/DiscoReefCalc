/**     Copyright (C) 2015  David Caldwell  disco47dave at gmail dot com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.



*/
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.DosingProduct;
import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import dosingcomputer.TankFile;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Dave
 */
public class GUIFrameOld extends javax.swing.JFrame {

    /**
     * Creates new form GUIFrame
     */
    private DefaultComboBoxModel comboModel;
    private Tank currentTank;

    public final Tank getCurrentTank() {
        return currentTank;
    }

    public void addNewTank(String name, Double vol) {
        if ((!name.equals("")) && (!DosingComputer.getTankNames().contains(name))) {
            Tank newTank = new Tank(name, vol);
            DosingComputer.addTank(newTank);

            setMeUp(newTank.getName());

        }

    }

    public void doTankChange() {
        if (currentTank != null) {
            currentTank.releaseAllPropertyChangeListeners();
        }
        currentTank = DosingComputer.getTank(tankNameComboBox.getSelectedItem().toString());

        setupParamPanels();
        setupProductPanels();

        tankNameLabel.setText(this.getCurrentTank().getName());
        Double volDoub = Preferences.getTankVolumeUnit().convertToUnit((double) this.getCurrentTank().getVolume());
        tankVolumeLabel.setText(String.format("%.2f", volDoub));
        volumeUnitLabel.setText(Preferences.getTankVolumeUnit().toString());

        this.getCurrentTank().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String propName = pce.getPropertyName();

                switch (propName) {
                    case "removeProduct":
                    case "addProduct":
                        prodPanel.removeAll();
                        setupProductPanels();
                        break;
                }

            }
        });

    }

    public void setMeUp() {
        comboModel = new DefaultComboBoxModel(DosingComputer.getTankNames().toArray());
        tankNameComboBox.setModel(comboModel);
        tankNameComboBox.setSelectedItem(0);

        doTankChange();
    }
    
    public void setMeUp(String aTankName) {
        comboModel = new DefaultComboBoxModel(DosingComputer.getTankNames().toArray());
        tankNameComboBox.setModel(comboModel);
        tankNameComboBox.setSelectedItem(aTankName);

        doTankChange();
    }

    public GUIFrameOld() {
        initComponents();
        this.addWindowListener(new WindowEventHandler());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    class WindowEventHandler extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            TreeSet<String> names = DosingComputer.getTankNames();
            for (String tn : names) {
                TankFile.saveTankToFile(DosingComputer.getTank(tn));
            }
            Preferences.savePreferences();
            System.exit(0);
        }
    }

    private void setupParamPanels() {
        parameterPanel.removeAll();
        parameterPanel.setLayout(new GridLayout(1, 0));
        ParameterInfoPanel labelPanel = new ParameterInfoPanel();
        parameterPanel.add(labelPanel);
        for (Parameter p : this.getCurrentTank().getParameters()) {
            parameterPanel.add(new ParameterInfoPanel(p));
        }
        parameterPanel.revalidate();

    }

    private void setupProductPanels() {
        prodPanel.removeAll();
        prodPanel.setLayout(new GridLayout(0, 1));
        DosePanel labelPanel = new DosePanel();
        prodPanel.add(labelPanel);
        for (DosingProduct dp : this.getCurrentTank().getDosingProducts()) {
            prodPanel.add(new DosePanel(dp));
        }
        prodPanel.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        parameterPanel = new javax.swing.JPanel();
        prodPanel = new javax.swing.JPanel();
        tankNameLabel = new javax.swing.JLabel();
        tankVolumeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tankNameComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        volumeUnitLabel = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        fileMenuItemCreateTank = new javax.swing.JMenuItem();
        fileMenuItemSaveTank = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        tankMenuItemAddWaterTest = new javax.swing.JMenuItem();
        tankMenuItemSetParameterTargets = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        dosingproductsMenuItemAddNewProduct = new javax.swing.JMenuItem();
        dosingproductsMenuItemRemoveProduct = new javax.swing.JMenuItem();
        dosingproductsMenuItemSetDosage = new javax.swing.JMenuItem();
        tankMenuItemCalculateConsumption = new javax.swing.JMenuItem();
        tankMenuItemCalculateDosing = new javax.swing.JMenuItem();
        tankMenuItemManualDose = new javax.swing.JMenuItem();
        tankMenuItemWaterChange = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("Print Event File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        parameterPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout parameterPanelLayout = new javax.swing.GroupLayout(parameterPanel);
        parameterPanel.setLayout(parameterPanelLayout);
        parameterPanelLayout.setHorizontalGroup(
            parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 693, Short.MAX_VALUE)
        );
        parameterPanelLayout.setVerticalGroup(
            parameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );

        prodPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout prodPanelLayout = new javax.swing.GroupLayout(prodPanel);
        prodPanel.setLayout(prodPanelLayout);
        prodPanelLayout.setHorizontalGroup(
            prodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        prodPanelLayout.setVerticalGroup(
            prodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
        );

        tankNameLabel.setText("tank name");

        tankVolumeLabel.setText("tank volume");

        jLabel1.setText("Tank Name");

        jLabel2.setText("Volume");

        tankNameComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tankNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankNameComboBoxActionPerformed(evt);
            }
        });

        jButton1.setText("setupParamPanels();");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        volumeUnitLabel.setText("Vol Unit");

        jButton3.setText("Time Spoof");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        fileMenuItemCreateTank.setText("Create Tank");
        fileMenuItemCreateTank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuItemCreateTankActionPerformed(evt);
            }
        });
        jMenu1.add(fileMenuItemCreateTank);

        fileMenuItemSaveTank.setText("Save Tank");
        fileMenuItemSaveTank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuItemSaveTankActionPerformed(evt);
            }
        });
        jMenu1.add(fileMenuItemSaveTank);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tank");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        tankMenuItemAddWaterTest.setText("Add Water Test");
        tankMenuItemAddWaterTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemAddWaterTestActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemAddWaterTest);

        tankMenuItemSetParameterTargets.setText("Set Parameter Targets");
        tankMenuItemSetParameterTargets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemSetParameterTargetsActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemSetParameterTargets);

        jMenu4.setText("Dosing Products");

        dosingproductsMenuItemAddNewProduct.setText("Add New Product");
        dosingproductsMenuItemAddNewProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosingproductsMenuItemAddNewProductActionPerformed(evt);
            }
        });
        jMenu4.add(dosingproductsMenuItemAddNewProduct);

        dosingproductsMenuItemRemoveProduct.setText("Remove Product");
        jMenu4.add(dosingproductsMenuItemRemoveProduct);

        dosingproductsMenuItemSetDosage.setText("Set Dosage");
        dosingproductsMenuItemSetDosage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosingproductsMenuItemSetDosageActionPerformed(evt);
            }
        });
        jMenu4.add(dosingproductsMenuItemSetDosage);

        jMenu3.add(jMenu4);

        tankMenuItemCalculateConsumption.setText("Calculate Consumption");
        tankMenuItemCalculateConsumption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemCalculateConsumptionActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemCalculateConsumption);

        tankMenuItemCalculateDosing.setText("Calculate Recommended Dosing");
        tankMenuItemCalculateDosing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemCalculateDosingActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemCalculateDosing);

        tankMenuItemManualDose.setText("Manual Dose");
        tankMenuItemManualDose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemManualDoseActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemManualDose);

        tankMenuItemWaterChange.setText("Water Change");
        tankMenuItemWaterChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemWaterChangeActionPerformed(evt);
            }
        });
        jMenu3.add(tankMenuItemWaterChange);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addGap(54, 54, 54)
                .addComponent(jButton2)
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(prodPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parameterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tankNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tankNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tankVolumeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(volumeUnitLabel)))
                .addContainerGap(341, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tankNameLabel)
                    .addComponent(jLabel1)
                    .addComponent(tankNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tankVolumeLabel)
                    .addComponent(jLabel2)
                    .addComponent(volumeUnitLabel))
                .addGap(18, 18, 18)
                .addComponent(parameterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(prodPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
//        System.out.println("******** Tank Redord Start *******");
//        for (TankRecord rec : this.getCurrentTank().getTankRecordFile()) {
//            System.out.println(rec.toString());
//        }
//        System.out.println("******** Tank Redord End *******");

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.add(new EventFileViewPanel());
        jf.setSize(800, 600);

        jf.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void fileMenuItemCreateTankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuItemCreateTankActionPerformed
        CreateTankDialog ctf = new CreateTankDialog();
        ctf.setLocationRelativeTo(this); 
        ctf.setMinimumSize(ctf.getPreferredSize());
        ctf.setModal(true);
        ctf.setVisible(true);
    }//GEN-LAST:event_fileMenuItemCreateTankActionPerformed

    private void fileMenuItemSaveTankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuItemSaveTankActionPerformed
        TankFile.saveTankToFile(this.getCurrentTank());
    }//GEN-LAST:event_fileMenuItemSaveTankActionPerformed

    private void tankMenuItemAddWaterTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemAddWaterTestActionPerformed
        AddWaterTestDialog resframe = new AddWaterTestDialog(this.getCurrentTank());
        resframe.setLocationRelativeTo(this); 
        resframe.setMinimumSize(resframe.getPreferredSize());
        resframe.setModal(true);
        resframe.setVisible(true);
    }//GEN-LAST:event_tankMenuItemAddWaterTestActionPerformed

    private void tankMenuItemSetParameterTargetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemSetParameterTargetsActionPerformed
        SetParameterTargetsDialog sptf = new SetParameterTargetsDialog(this.getCurrentTank());
        sptf.setLocationRelativeTo(this);
        sptf.setMinimumSize(sptf.getPreferredSize());
        sptf.setModal(true);
        sptf.setVisible(true);
    }//GEN-LAST:event_tankMenuItemSetParameterTargetsActionPerformed

    private void dosingproductsMenuItemAddNewProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosingproductsMenuItemAddNewProductActionPerformed
        AddDosingProductDialog apf = new AddDosingProductDialog(this.getCurrentTank());
        apf.setLocationRelativeTo(this); 
        apf.setMinimumSize(apf.getPreferredSize());
        apf.setModal(true);
        apf.setVisible(true);
    }//GEN-LAST:event_dosingproductsMenuItemAddNewProductActionPerformed

    private void tankMenuItemCalculateConsumptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemCalculateConsumptionActionPerformed
        this.getCurrentTank().setConsumption();

    }//GEN-LAST:event_tankMenuItemCalculateConsumptionActionPerformed

    private void dosingproductsMenuItemSetDosageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosingproductsMenuItemSetDosageActionPerformed
        SetDailyDoseDialog sdf = new SetDailyDoseDialog(this.getCurrentTank());
        sdf.setLocationRelativeTo(this); 
        sdf.setMinimumSize(sdf.getPreferredSize());
        sdf.setModal(true);
        sdf.setVisible(true);
    }//GEN-LAST:event_dosingproductsMenuItemSetDosageActionPerformed

    private void tankMenuItemCalculateDosingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemCalculateDosingActionPerformed
        CalculateDosesDialog cdf = new CalculateDosesDialog(this.getCurrentTank());
        cdf.setLocationRelativeTo(this);  
        cdf.setMinimumSize(cdf.getPreferredSize());
        cdf.setModal(true);
        cdf.setVisible(true);

    }//GEN-LAST:event_tankMenuItemCalculateDosingActionPerformed

    private void tankNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankNameComboBoxActionPerformed
        doTankChange();
    }//GEN-LAST:event_tankNameComboBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//////////        JFrame fr = new JFrame();
//////////        fr.add(new DateEnterPanel());
//////////        fr.setVisible(true);

        setupParamPanels();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tankMenuItemManualDoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemManualDoseActionPerformed
        AddManualDoseDialog mdf = new AddManualDoseDialog();
        mdf.setLocationRelativeTo(this);  
        mdf.setMinimumSize(mdf.getPreferredSize());
        mdf.setModal(true);
        mdf.setVisible(true);
    }//GEN-LAST:event_tankMenuItemManualDoseActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.add(new TimeSpoofPanel());
        jf.setSize(220, 100);

        jf.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jf.add(new DatedGraphPanel(getCurrentTank().getTankRecordFile().getLastTests(10, Parameter.ALKALINITY)));
        jf.setSize(1100, 950);

        jf.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void tankMenuItemWaterChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankMenuItemWaterChangeActionPerformed
        // TODO add your handling code here:
        WaterChangeDialog wcf = new WaterChangeDialog();
        wcf.setLocationRelativeTo(this); 
        wcf.setMinimumSize(wcf.getPreferredSize());
        wcf.setModal(true);
        wcf.setVisible(true);
    }//GEN-LAST:event_tankMenuItemWaterChangeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem dosingproductsMenuItemAddNewProduct;
    private javax.swing.JMenuItem dosingproductsMenuItemRemoveProduct;
    private javax.swing.JMenuItem dosingproductsMenuItemSetDosage;
    private javax.swing.JMenuItem fileMenuItemCreateTank;
    private javax.swing.JMenuItem fileMenuItemSaveTank;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel parameterPanel;
    private javax.swing.JPanel prodPanel;
    private javax.swing.JMenuItem tankMenuItemAddWaterTest;
    private javax.swing.JMenuItem tankMenuItemCalculateConsumption;
    private javax.swing.JMenuItem tankMenuItemCalculateDosing;
    private javax.swing.JMenuItem tankMenuItemManualDose;
    private javax.swing.JMenuItem tankMenuItemSetParameterTargets;
    private javax.swing.JMenuItem tankMenuItemWaterChange;
    private javax.swing.JComboBox tankNameComboBox;
    private javax.swing.JLabel tankNameLabel;
    private javax.swing.JLabel tankVolumeLabel;
    private javax.swing.JLabel volumeUnitLabel;
    // End of variables declaration//GEN-END:variables
}
