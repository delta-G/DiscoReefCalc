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
