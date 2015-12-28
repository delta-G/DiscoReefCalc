/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.DosingProduct;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class DosePanel extends JPanel {
    
    private final DosingProduct product;
    private final Tank tank;
    
    private JLabel nameLabel;
    private JLabel doseLabel;
    private JLabel unitLabel;
    
    
        public DosePanel() {
        product = null;
        tank = null;
        
        
        initComponents();
        //beLabels();
    }
    
    public DosePanel(DosingProduct dp) {
        
        product = dp;
        tank = DosingComputer.guiFrame.getCurrentTank();
        
        
        initComponents();
        setupPanel();
        
        tank.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String propName = pce.getPropertyName();
                String[] upStr = propName.split(":");
                
                switch (upStr[0]) {
                    
                    case "dosageUpdate":
                        
                        if (upStr[1].equals(product.getName())) {
                            doseLabel.setText(String.format("%.2f", tank.getDailyDose(product)));
                        }                        
                }
            }
        });
    }
    
    public final void setupPanel() {
        nameLabel.setText(product.getName());
        
        doseLabel.setText(String.format("%.2f", Preferences.getDoseVolumeUnit().convertToUnit(tank.getDailyDose(product))));
        unitLabel.setText(Preferences.getDoseVolumeUnit().toString());
    }
    
    public final void beLabels() {
        nameLabel.setText("Dosing Product Name");
        doseLabel.setText("Daily Dosage");
        unitLabel.setText("");
    }
    
    private void initComponents() {
        
        nameLabel = new JLabel("Dosing Product Name");
        Dimension d = nameLabel.getPreferredSize();
        nameLabel.setMinimumSize(new Dimension (150, (int) d.getHeight()));
        nameLabel.setPreferredSize(new Dimension (150, (int) d.getHeight()));
        nameLabel.setMaximumSize(new Dimension (150, (int) d.getHeight()));
        doseLabel = new JLabel("Daily Dosage");
        unitLabel = new JLabel("    ");
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(nameLabel);
        this.add(doseLabel);
        this.add(Box.createRigidArea(new Dimension(10,0)));
        this.add(unitLabel);
               
        
    }
    
}
