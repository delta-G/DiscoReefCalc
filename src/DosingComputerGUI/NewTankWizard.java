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
