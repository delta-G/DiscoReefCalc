/**     Copyright (C) 2015  David Caldwell  disco47dave@gmail.com

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

import dosingcomputer.DosingProduct;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author David
 */
public class CalculateDosesDialog extends JDialog {
    
    private final HashMap<DosingProduct, Double> doseMap;
    private final ArrayList<ProdCDFPanel> lines;
    private final Tank tank;
    
    private JLabel accLabel;
    private JButton acceptButton;
    private JButton cancelButton;
    
    
    public CalculateDosesDialog(Tank atank) {
        this.tank = atank;
        doseMap =  this.tank.computeRequiredDailyDoses();
        lines = new ArrayList<>();
        
        for (DosingProduct idp : doseMap.keySet()){
            lines.add(new ProdCDFPanel(idp));
        } 
        initComponents();        
    }
    
    private void initComponents(){
        
        this.setTitle("Calculate Recommended Dosing");
        
        accLabel = new JLabel("Select to Accept Dosing Reccomendation:");
        acceptButton = new JButton();
        cancelButton = new JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });
        
        cancelButton.setText("Reject");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,5,5,5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        
        this.add(accLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        for(ProdCDFPanel pcd : lines){
            this.add(pcd, gbc);
            gbc.gridy = gbc.gridy + 1;
        }
        
        gbc.fill = GridBagConstraints.NONE;

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.7;
        gbc.weightx = 0.7;
        this.add(acceptButton, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        this.add(cancelButton, gbc);
        
        this.pack();
        
    }
    
    private class ProdCDFPanel extends JPanel {
        
        private final JCheckBox acceptCheckBox;
        private final JLabel prodLabel;
        private final JTextField doseTextBox;
        private final JLabel unitLabel;
        private final DosingProduct dosP;
        
        public ProdCDFPanel(DosingProduct adp){
            this.dosP = adp;
            acceptCheckBox = new JCheckBox();
            prodLabel = new JLabel(adp.getName());
            doseTextBox = new JTextField(String.format("%.2f", Preferences.getDoseVolumeUnit().convertToUnit(doseMap.get(this.dosP))));
            unitLabel = new JLabel();
            unitLabel.setText(Preferences.getDoseVolumeUnit().toString());
            
            this.setLayout(new GridBagLayout());
            GridBagConstraints gbci = new GridBagConstraints();
            gbci.insets = new Insets(5,5,5,5);
            gbci.anchor = GridBagConstraints.LINE_START;
            
            gbci.gridx = 0;
            gbci.gridy = 0;
            this.add(acceptCheckBox , gbci);
            
            gbci.gridx = 1;
            gbci.gridy = 0;
            gbci.ipadx = (int) (200 - prodLabel.getPreferredSize().getWidth());
            this.add(prodLabel , gbci);
            
            
            gbci.gridx = 2;
            gbci.gridy = 0;
            gbci.ipadx = 30;
            gbci.anchor = GridBagConstraints.LINE_END;
            this.add(doseTextBox , gbci);   
            
            gbci.gridx = 3;
            gbci.gridy = 0;
            gbci.ipadx = 0;
            gbci.anchor = GridBagConstraints.LINE_START;
            this.add(unitLabel, gbci);
            
            this.setBorder(new LineBorder(Color.black));            
        }
        private boolean isChecked(){
                return this.acceptCheckBox.isSelected();
            }
        
        private DosingProduct getProduct(){
            return this.dosP;
        }
        
        private Double getDose() throws java.lang.NumberFormatException {
            Double retval = Double.parseDouble(this.doseTextBox.getText());
            
            
            if(retval.isNaN() || retval <= 0.0){
                retval = 0.0;
            }
            return Preferences.getDoseVolumeUnit().convertFromUnit(retval);            
        }
    }
    
    
    private void acceptButtonActionPerformed(ActionEvent evt){
        
        for (ProdCDFPanel p : lines){
            try {
                if (p.isChecked()){
                this.tank.setDailyDose(p.getProduct(), p.getDose());
            }
            } catch (java.lang.NumberFormatException ex) {
                ErrorDialog.showErrorDialog("Invalid Volume Entry");
                this.tank.setDailyDose(p.getProduct(), 0.0);
            }
            
        }
        
        
        this.dispose();
    }
    
    private void cancelButtonActionPerformed(ActionEvent evt){
        this.dispose();
    }
    
}
