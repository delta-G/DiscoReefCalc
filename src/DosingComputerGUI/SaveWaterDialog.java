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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dosingcomputer.Water;
import dosingcomputer.WaterFile;
import dosingcomputer.Parameter;

public class SaveWaterDialog extends JDialog {

	private JTextField nameField;
	private WaterSetupPanel wsp;
	private JButton okButton;
	private JButton cancelButton;

	public SaveWaterDialog() {
		initComponents();
	}
	
	public SaveWaterDialog(Water awater){
		initComponents();
		for(Parameter p : awater.getValues().keySet()){
			wsp.setValueConverted(p, awater.getValue(p));
		}
	}

	private void initComponents() {

		nameField = new JTextField("" , 15);
		wsp = new WaterSetupPanel();
		okButton = new JButton("OK");
		okButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				okButtonActionPerformed(ae);
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				cancelButtonActionPerformed(ae);
			}
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new java.awt.Insets(5, 5, 5, 5);

		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(new JLabel("Water Name :"), constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.add(nameField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 5;
		this.add(wsp, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_END;

		constraints.gridx = 3;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.add(okButton, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;

		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		this.add(cancelButton, constraints);

		this.pack();

	}

	private void okButtonActionPerformed(java.awt.event.ActionEvent ae) {
		String nameStr = nameField.getText();
		if (nameStr.equals("")) {
			ErrorDialog.showErrorDialog("Please Enter A Name");
			return;
		}
		if (WaterFile.getWaterNames().contains(nameStr)) {
			if (!ErrorDialog
					.showErrorInputDialog("Name Already in Use : Overwrite?")) {
				return;
			}
		}
		WaterFile.putWater(nameStr, wsp.getWater());
		
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent ae) {
		this.dispose();
	}

}
