/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author David
 */
public class WaterSetupPanel extends javax.swing.JPanel {
    
    ArrayList<PVUPanel> lines;
    
    public WaterSetupPanel() {
        lines = new ArrayList<>();
        initComponents();
    }
    
    private void initComponents() {
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.LINE_START;
        
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
    
}
