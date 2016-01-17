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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dosingcomputer.Parameter;
import dosingcomputer.Water;

public class CalculatorWaterPanel extends JPanel {

	private JLabel mainLabel;
	private VolumeEntryPanel volPanel;
	private HashMap<Parameter, PVUPanel> lines;
	private JPanel innerPanel;
	private JButton saveButton;
	private Boolean enabled = true;
	private String labelStr = "";

	public CalculatorWaterPanel(Parameter aPar) {
		lines = new HashMap<>();
		lines.put(aPar, new PVUPanel(aPar));
		initComponents();
	}

	public void setParams(ArrayList<Parameter> aList) {
		for (Parameter p : Parameter.values()) {
			if (aList.contains(p)) {
				if (!lines.containsKey(p)) {
					lines.put(p, new PVUPanel(p));
				}
			} else {
				if (lines.containsKey(p)) {
					lines.remove(p);
				}
			}
		}
		setupInnerPanel();
	}
	
	public void setLabelString(String aStr){
		this.labelStr = aStr;
		this.mainLabel.setText(aStr);
	}
	
	public String getLabelString(String aStr){
		return this.labelStr;
	}

	public void setParamEnabled(Boolean aBoo) {
		this.enabled = aBoo;		
		setupInnerPanel();
	}
	
	public void setVolEnabled(Boolean aBoo) {
		this.volPanel.setVEPEnabled(aBoo);
		setupInnerPanel();
	}

	public ArrayList<Parameter> getParams() {
		return new ArrayList<>(lines.keySet());
	}

	public Double getValue(Parameter aPar) {
		return lines.get(aPar).getConvertedValue();
	}

	public void setValue(Parameter aPar, Double aVal) {
		lines.get(aPar).setValueConverted(aVal);
	}

	public Double getVolume() {
		return volPanel.getConvertedValue();
	}

	public void setVolume(Double aVal) {
		volPanel.setValueConverted(aVal);
	}

	public Water getWater() {
		Water retval = new Water();
		for (Parameter p : Parameter.values()) {
			if (lines.keySet().contains(p)) {
				retval.setValue(p, lines.get(p).getConvertedValue());
			} else {
				retval.setValue(p, 0.0);
			}
		}
		return retval;
	}

	private void initComponents() {
		mainLabel = new JLabel(this.labelStr);
		volPanel = new VolumeEntryPanel();
		innerPanel = new JPanel();
		saveButton = new JButton("Save");
		
		saveButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				saveButtonActionPerformed(ae);				
			}
			
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		this.add(mainLabel, constraints);

		constraints.gridy = 1;
		this.add(volPanel, constraints);
		constraints.gridy = 2;

		setupInnerPanel();
		this.add(innerPanel, constraints);
		
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(saveButton, constraints);
	}

	private void setupInnerPanel() {
		innerPanel.removeAll();
		innerPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.LINE_START;

		for (PVUPanel pvup : lines.values()) {
			pvup.setPVUEnabled(this.enabled);
			innerPanel.add(pvup, constraints);
			constraints.gridy += 1;
		}
		this.revalidate();
		this.repaint();

	}

	public void addParameter(Parameter aPar) {
		if (!lines.containsKey(aPar)) {
			lines.put(aPar, new PVUPanel(aPar));
			setupInnerPanel();
		}
	}
	
	private void saveButtonActionPerformed(java.awt.event.ActionEvent ae) {
		SaveWaterDialog swd = new SaveWaterDialog(this.getWater());
    	swd.setLocationRelativeTo(this);
    	swd.setMinimumSize(swd.getPreferredSize());
    	swd.setModal(true);
    	swd.setVisible(true); 
	}

}
