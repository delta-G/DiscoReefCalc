/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
