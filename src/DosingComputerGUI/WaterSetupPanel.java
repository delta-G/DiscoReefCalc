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

import dosingcomputer.Parameter;
import dosingcomputer.Water;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;

/**
 *
 * @author David
 */
public class WaterSetupPanel extends javax.swing.JPanel {
    
    private ArrayList<PVUPanel> lines;
    private JCheckBox lockToValCheckBox;
    
    public WaterSetupPanel() {
        lines = new ArrayList<>();
        initComponents();
    }
    
    private void initComponents() {
    	
    	lockToValCheckBox = new JCheckBox("Lock To Values");
    	lockToValCheckBox.setSelected(false);
    	lockToValCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockToValCheckBoxActionPerformed(evt);
            }
        });
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.LINE_START;
        
        this.add(lockToValCheckBox, gbc);
        gbc.gridy = 1;
        
        for(Parameter p : Parameter.values()){
            PVUPanel pvup = new PVUPanel(p);
            lines.add(pvup);
            this.add(pvup , gbc);
            gbc.gridy += 1;
        }        
    }
    
    public Water getWater()throws java.lang.NumberFormatException {
        HashMap<Parameter, Double> watmap = new HashMap<>();
        for (PVUPanel pv : lines){
            watmap.put(pv.getParameterSelection(), pv.getConvertedValue());
        }
        return new Water(watmap);       
    }
    
    public void setWater(Water aWater){
        for(PVUPanel pv : lines){
            pv.setValueConverted(aWater.getValue(pv.getParameterSelection()));
        }
    }
    
    public Double getValue(Parameter p)throws java.lang.NumberFormatException {
        for(PVUPanel pv : lines){
            if(pv.getParameterSelection() == p){
                return pv.getConvertedValue();
            }
        }
        return 0.0;
    }
    
    public void setValueConverted(Parameter p, Double aVal){
        for(PVUPanel pv : lines){
            if(pv.getParameterSelection() == p){
                pv.setValueConverted(aVal);
            }
        }
    }
    
    @Override
    public void setEnabled(boolean aBoo){
        for (PVUPanel pv : lines){
            pv.setPVUEnabled(aBoo);
        }
    }
    
    public void setLockToValue(Boolean aboo){
    	this.lockToValCheckBox.setSelected(aboo);
    	this.lockToValCheckBoxActionPerformed(null);
    }
    
    private void lockToValCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        for (PVUPanel pv : lines){
        	pv.setLockToValue(lockToValCheckBox.isSelected());
        }
    }
    
}
