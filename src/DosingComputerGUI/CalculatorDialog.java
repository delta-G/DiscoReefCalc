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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dosingcomputer.Parameter;
import dosingcomputer.Preferences;

public class CalculatorDialog extends JDialog {

	private CalculatorWaterPanel origWaterPanel;
	private CalculatorWaterPanel deltaWaterPanel;
	private CalculatorWaterPanel resultWaterPanel;

	private JButton calculateButton;
	private JButton closeButton;
	private JButton chooseParamsButton;

	public CalculatorDialog() {
		initComponents();
	}

	private void initComponents() {
		
		this.setTitle("Calculator");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		origWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		deltaWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		resultWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		resultWaterPanel.setCWPEnabled(false);

		calculateButton = new JButton("Calculate");
		closeButton = new JButton("Close");
		chooseParamsButton = new JButton("Choose Params:");

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				closeButtonActionPerformed(ae);
			}

		});

		calculateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				calculateButtonActionPerformed(ae);
			}

		});

		chooseParamsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						chooseParamsButtonActionPerformed(ae);
					}

				});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		this.add(chooseParamsButton, constraints);

		constraints.gridy = 1;
		this.add(origWaterPanel, constraints);

		constraints.gridx = 1;
		this.add(deltaWaterPanel, constraints);

		constraints.gridx = 2;
		this.add(resultWaterPanel, constraints);

		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(calculateButton, constraints);

		constraints.gridx = 3;
		this.add(closeButton, constraints);

		this.pack();

	}

	private void closeButtonActionPerformed(ActionEvent ae) {
		this.dispose();
	}

	private void calculateButtonActionPerformed(ActionEvent ae) {
		try {
			Double volA = origWaterPanel.getVolume();
			Double volB = deltaWaterPanel.getVolume();
			Double volF = volA + volB;
			resultWaterPanel.setVolume(volF);

			for (Parameter p : origWaterPanel.getParams()) {
				Double conA = origWaterPanel.getValue(p);
				Double conB = deltaWaterPanel.getValue(p);

				Double conF = ((volA * conA) + (volB * conB)) / volF;
				resultWaterPanel.setValue(p, conF);
			}
		} catch (NumberFormatException ex) {
			ErrorDialog.showErrorDialog(this, "Please Enter Valid Value!");
		}

	}

	private void chooseParamsButtonActionPerformed(ActionEvent ae) {
		ArrayList<Parameter> newps = new ArrayList<>();
		try {
			newps = ParamPickerDialog.getParameters(origWaterPanel.getParams());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (newps != null) {
			origWaterPanel.setParams(newps);
			deltaWaterPanel.setParams(newps);
			resultWaterPanel.setParams(newps);
		}

		this.pack();
		this.revalidate();

	}

}
