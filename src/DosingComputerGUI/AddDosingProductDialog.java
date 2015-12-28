/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author David
 */
public class AddDosingProductDialog extends JDialog {
    
    DefaultComboBoxModel model; 
    
    private JLabel inLabel;
    private JButton cancelButton;
    private JButton okButton;
    private JComboBox productComboBox;
    private JTextField doseField;
    private JLabel unitLabel;
    
    private final Tank tank;
    
    public AddDosingProductDialog(Tank atank) {
        this.tank = atank;
        initComponents();
    }

    private void initComponents() {
        
        this.setTitle("Add Dosing Product");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        inLabel = new JLabel("Choose Product From List and Enter Dosage:");
        cancelButton = new JButton();
        okButton = new JButton();
        productComboBox = new JComboBox();
        doseField = new JTextField("0.0");
        unitLabel = new JLabel(Preferences.getDoseVolumeUnit().toString());
        
        model = new DefaultComboBoxModel(DosingComputer.dosingProductsList.toArray());
        productComboBox.setModel(model);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(20,20,5,5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 40;
        this.add(productComboBox, gbc);
        
        gbc.insets = new Insets(20,5,5,5);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 30;
        this.add(doseField, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        this.add(unitLabel, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.7;
        this.add(okButton, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0;
        this.add(cancelButton, gbc);
        
        this.pack();

    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.dispose();
    }                                            

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        Double ret;
        try{
        ret = Double.parseDouble(this.doseField.getText());
        } catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Invalid Input");
            return;
        }
        if(ret.isNaN() || ret <= 0.0){
            ret = 0.0;
        }
       this.tank.addProduct(DosingComputer.parseDosingProduct(model.getSelectedItem().toString()), Preferences.getDoseVolumeUnit().convertFromUnit(ret));
       
       this.dispose();
    } 
}
