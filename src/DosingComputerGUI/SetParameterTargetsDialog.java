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

import dosingcomputer.Parameter;
import dosingcomputer.Tank;
import dosingcomputer.Water;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.LineBorder;

/**
 *
 * @author David
 */
public class SetParameterTargetsDialog extends JDialog {
    
    private JButton okButton;
    private JButton cancelButton;
    private WaterSetupPanel wsPanel;
    
    private final Tank tank;
    
    public SetParameterTargetsDialog(Tank atank) {
        this.tank = atank;
        initComponents();
    }
    
    private void initComponents() {
        
        this.setTitle("Set Parameter Targets");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        okButton = new JButton();
        cancelButton = new JButton();
        wsPanel = new WaterSetupPanel();
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelButtonActionPerformed(ae);
            }
            
        });
        
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                okButtonActionPerformed(ae);
            }
            
        });
        
        HashMap<Parameter, Double> parMap = new HashMap<>();
        for(Parameter p : tank.getParameters()){
            parMap.put(p, tank.getTarget(p));
        }
        wsPanel.setWater(new Water(parMap));
        
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints wspc = new GridBagConstraints();
        
        wspc.fill = GridBagConstraints.NONE;
        wspc.anchor = GridBagConstraints.LINE_START;
        wspc.gridx = 0;
        wspc.gridy = 1;
        wspc.gridwidth = 2;
        wspc.insets = new Insets(30,5,5,5);
        wsPanel.setBorder(new LineBorder(Color.black));
        this.add(wsPanel, wspc);
        
        GridBagConstraints butc = new GridBagConstraints();
        
        butc.fill = GridBagConstraints.NONE;
        butc.gridx = 2;
        butc.gridy = 2;
        butc.weighty = 0.7;
        butc.insets = new Insets(5,5,5,5);
        butc.anchor = GridBagConstraints.LAST_LINE_END;        
        this.add(cancelButton, butc);
        
        butc.gridx = 1;
        butc.weightx = 0.7;
        this.add(okButton, butc);
        
        this.pack();        
        
    }
    
    
    void cancelButtonActionPerformed(ActionEvent ae) {
        this.dispose();
    }
    
    void okButtonActionPerformed(ActionEvent ae) {
        Water retWater;
        try {
        retWater = wsPanel.getWater();
        } catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Invalid Input");
            return;
        }
        for(Parameter p : tank.getParameters()){
            tank.setTarget(p, retWater.getValue(p));
        }
        
        this.dispose();
    }
    
}
