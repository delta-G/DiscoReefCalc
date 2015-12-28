/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.Tank;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */

/*
        This is messed up because:
1:  It doesn't handle the volume unit thing so you gotta enter in mL
2:  it doesn't really do anything that CreateTankFrame can't do
    So, we shouldn't try to do it like this.  
    But we'll hang onto the code because it might be useful later 
*/

public class NewTankWizard {
    
    private static final String titleString = "New Tank Wizard";
    
    public static Tank runWizard(JFrame parent){
        
        
        String nameInput = JOptionPane.showInputDialog(parent, "Enter Tank Name", titleString, JOptionPane.PLAIN_MESSAGE);
        if("".equals(nameInput)){
            return null;
        }
        String volInput = JOptionPane.showInputDialog(parent, "Enter Volume", titleString, JOptionPane.PLAIN_MESSAGE);
        Double vol = Double.parseDouble(volInput);
        Tank newTank = new Tank(nameInput , vol);

        return newTank;
    }
    
    
}
