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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dosingcomputer.Preferences;
import dosingcomputer.VolumeUnits;

public class VolumeEntryPanel extends JPanel {

	private ComboBoxModel<VolumeUnits> unitModel;
	private JComboBox<VolumeUnits> unitComboBox;
	private VolumeUnits currentUnitSelection;

	private JTextField valueTextField;

	private boolean lockToValue = false; // changing units converts value in box
											// if true

	public VolumeEntryPanel() {
		initComponents();

		unitModel = new DefaultComboBoxModel<VolumeUnits>(VolumeUnits.values());
		unitModel.setSelectedItem(Preferences.getTankVolumeUnit());
		unitComboBox.setModel(unitModel);
		this.setUnitSelection((VolumeUnits)unitModel.getSelectedItem()); 
	}

	public void setUnitSelection(VolumeUnits avu) {
		currentUnitSelection = avu;
	}

	public VolumeUnits getUnitSelection() {
		return currentUnitSelection;
	}

	public Double getEnteredValue() {
		return Double.parseDouble(this.valueTextField.getText());
	}

	public Double getConvertedValue() throws java.lang.NumberFormatException {
		Double retval;
		retval = this.currentUnitSelection.convertFromUnit(Double.parseDouble(this.valueTextField.getText()));
		if (retval.isNaN() || retval.isInfinite() || retval < 0.0) {
			throw new java.lang.NumberFormatException();
		}
		return retval;
	}

	public void setEnteredValue(Double aVal) {
		valueTextField.setText(aVal.toString());
	}

	public void setValueConverted(Double aVal) {
		Double conval = this.currentUnitSelection.convertToUnit(aVal);
		valueTextField.setText(conval.toString());
	}

	public void setVEPEnabled(boolean aBoo) {
		this.valueTextField.setEditable(aBoo);
		//this.lockToValue = !aBoo;
		//this.unitComboBox.setEnabled(aBoo);
		this.lockToValue = !aBoo;
	}

	private void initComponents() {
		unitComboBox = new JComboBox<>();
		valueTextField = new JTextField();
		valueTextField.setColumns(8);
		valueTextField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

		unitComboBox.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				unitComboBoxActionPerformed(evt);
			}
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.weightx = 0.7;
		this.add(valueTextField, gbc);

		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.weightx = 0;
		this.add(unitComboBox, gbc);
		

	}

	private void unitComboBoxActionPerformed(ActionEvent evt) {
		VolumeUnits vu = (VolumeUnits) unitComboBox.getSelectedItem();
		if (vu != currentUnitSelection) {
			if (lockToValue) {
				Double inval = this.getConvertedValue();
				this.setUnitSelection(vu);
				this.setValueConverted(inval);
			} else {
				this.setUnitSelection(vu);
			}
		}
	}

}
