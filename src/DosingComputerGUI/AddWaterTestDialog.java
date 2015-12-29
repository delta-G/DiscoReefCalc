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

import dosingcomputer.Tank;
import dosingcomputer.WaterTestRecord;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author David
 */
public class AddWaterTestDialog extends JDialog {
    
    private JButton cancelButton;
    private JButton okButton;
    private DateTimePanel dtPanel;
    private PVUPanel pvuPanel;
    private JLabel inLabel;
    Tank tank;
    
    public AddWaterTestDialog(Tank atank) {
        this.tank = atank;
        initComponents();
    }
    
    private void initComponents() {
        
        this.setTitle("Water Test Results");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        cancelButton = new JButton();
        okButton = new JButton();
        dtPanel = new DateTimePanel();
        pvuPanel = new PVUPanel();
        inLabel = new JLabel("Enter Date and Test Results:");
        
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                okButtonActionPerformed(ae);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelButtonActionPerformed(ae);
            }
        });
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(inLabel, gbc);
        
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridy = 1;
        this.add(dtPanel, gbc);       
        
        gbc.gridy = 2;
        this.add(pvuPanel, gbc);
        
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weighty = 0.7;
        this.add(cancelButton, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.add(okButton, gbc);
        
        this.pack();
        
        
    }
    
    private void cancelButtonActionPerformed(ActionEvent ae) {
        this.dispose();
    }
    
    private void okButtonActionPerformed(ActionEvent ae) {
        
        Date date;
        
        date = dtPanel.getSelectedDate();
        Double result; 
        try{
        result = pvuPanel.getConvertedValue();
//        if (result.isNaN() || result < 0.0){
//            ErrorDialog.showErrorDialog("Invalid concentration");
//            return;
//        }
        } catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Invalid concentration");
            return;
        }
        tank.addTest(new WaterTestRecord(
                date,
                pvuPanel.getParameterSelection(),
                result
        ));
        
      this.dispose();
    }
    
}
