/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.DosingProduct;
import dosingcomputer.ManualDoseRecord;
import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author David
 */
public class AddManualDoseDialog extends JDialog {

    private JPanel calPanel;
    private JLabel calPanelLabel;
    private JButton calculateDoseButton;
    private JButton cancelButton;
    private JTextField doseTextField;
    private JLabel doseLabel;
    private JButton okButton;
    private PVUPanel pVUPanel1;
    private JComboBox productComboBox;
    private JLabel unitLabel;

    private final ComboBoxModel model = new DefaultComboBoxModel(DosingComputer.guiFrame.getCurrentTank().getDosingProducts().toArray());

    public AddManualDoseDialog() {
        initComponents();
        unitLabel.setText(Preferences.getDoseVolumeUnit().toString());
        productComboBox.setModel(model);

    }

    private void initComponents() {

        this.setTitle("Add Manual Dose");

        calPanel = new JPanel();
        calPanelLabel = new JLabel();
        calPanelLabel.setText("Target for Calculation:");
        pVUPanel1 = new DosingComputerGUI.PVUPanel();
        productComboBox = new javax.swing.JComboBox();
        doseTextField = new javax.swing.JTextField();
        doseLabel = new JLabel();
        doseLabel.setText("Enter amount or use Calculate Button:");
        unitLabel = new javax.swing.JLabel();
        calculateDoseButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        doseTextField.setText("0.0");

        calculateDoseButton.setText("Calculate");
        calculateDoseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateDoseButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("Make Dose");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        Insets insets = new Insets(5, 5, 5, 5);

        //  Calculate Panel
        calPanel.setLayout(new GridBagLayout());
        GridBagConstraints cpCon = new GridBagConstraints();
        cpCon.insets = insets;
        cpCon.anchor = GridBagConstraints.PAGE_END;
        cpCon.gridx = 1;
        cpCon.gridy = 0;
        cpCon.weighty = 0.7;
        calPanel.add(calPanelLabel, cpCon);
        
        cpCon.anchor = GridBagConstraints.CENTER;
        cpCon.gridx = 0;
        cpCon.gridy = 1;
        cpCon.weighty = 0.0;
        calPanel.add(calculateDoseButton, cpCon);
        cpCon.gridx = 1;
        cpCon.gridy = 1;
        calPanel.add(pVUPanel1, cpCon);

        calPanel.setBorder(new LineBorder(Color.black));

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        
        Insets cpInsets = new Insets(20, 20, 10, 20);
        gbc.insets = cpInsets;
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weighty = 0.7;
        this.add(calPanel, gbc);
        gbc.insets = insets;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        this.add(doseLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipadx = 100;
        gbc.weightx = 0.7;
        this.add(productComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.ipadx = 0;
        gbc.weightx = 0.4;
        this.add(doseTextField, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        this.add(unitLabel, gbc);

        gbc.fill = GridBagConstraints.NONE;

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weighty = 0.4;
        this.add(okButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weighty = 0.4;
        this.add(cancelButton, gbc);

        this.pack();

    }

    private void calculateDoseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        DosingProduct dp = DosingComputer.parseDosingProduct(productComboBox.getSelectedItem().toString());
        Double target;
        Parameter parsel = pVUPanel1.getParameterSelection();
        try {
            target = pVUPanel1.getConvertedValue();
        } catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Invalid concentration");
            return;
        }
        if (target < DosingComputer.guiFrame.getCurrentTank().getTankWater().getValue(parsel)){
            ErrorDialog.showErrorDialog("Dosing can't make it go down");
            return;
        }
        doseTextField.setText(String.format("%.2f", dp.calculateDosage(parsel,
                target,
                DosingComputer.guiFrame.getCurrentTank())));
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        DosingComputer.guiFrame.getCurrentTank().addManualDose(new ManualDoseRecord(
                new Date(DosingComputer.getTheTime()),
                DosingComputer.parseDosingProduct(productComboBox.getSelectedItem().toString()),
                Double.parseDouble(doseTextField.getText())
        ));
        this.dispose();
    }

    private void productComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        doseTextField.setText("0.0");
    }

}
