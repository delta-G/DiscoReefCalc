/**     Copyright (C) 2016  David Caldwell  disco47dave@gmail.com

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dosingcomputer.DosingComputer;
import dosingcomputer.Preferences;
import dosingcomputer.Water;
import dosingcomputer.WaterFile;

public class ChooseWaterDialog extends JDialog {


public ChooseWaterDialog() {
	initComponents();
}

private void initComponents(){
	
}

public static Water getWaterSelection(String aMessage) {
	
	ComboBoxModel<String> model;
	JComboBox<String> comboBox;
	
	model = new DefaultComboBoxModel<String>(WaterFile.getWaterNamesArray());
	
	
	//model.setSelectedItem("Alk");
	comboBox = new JComboBox<>();
	comboBox.setModel(model);
	
	final JOptionPane optionPane = new JOptionPane(aMessage,
			JOptionPane.QUESTION_MESSAGE,
			JOptionPane.OK_CANCEL_OPTION);
	optionPane.add(comboBox);
	
	final JDialog dialog = new JDialog(DosingComputer.guiFrame,
			"Please choose a Parameter:",
			true);
	dialog.setContentPane(optionPane);
	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	
	optionPane.addPropertyChangeListener(new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent aEvnt) {
			String prop = aEvnt.getPropertyName();
			if(dialog.isVisible() && (aEvnt.getSource() == optionPane) && prop.equals(JOptionPane.VALUE_PROPERTY)) {
				
				dialog.setVisible(false);
			}
		}
	});
	
	dialog.pack();
	dialog.setVisible(true);
	Water retval = new Water();
	int value = ((Integer) optionPane.getValue()).intValue();
	if (value == JOptionPane.OK_OPTION){
		String rws = model.getSelectedItem().toString();
		retval = WaterFile.getWater(rws);
	} else if (value == JOptionPane.CANCEL_OPTION){
		
	}
	return retval;
			
	
}

}
