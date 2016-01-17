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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
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
	private JRadioButton bPanelRadioButton;
	private JRadioButton cPanelRadioButton;
	private ButtonGroup panelButtonGroup;

	private JRadioButton addRadioButton;
	private JRadioButton changeRadioButton;
	private ButtonGroup addChangeButtonGroup;

	private JRadioButton volRadioButton;
	private JRadioButton conRadioButton;
	private ButtonGroup volConButtonGroup;

	public CalculatorDialog() {
		initComponents();
	}

	private void initComponents() {

		this.setTitle("Calculator");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		panelButtonGroup = new ButtonGroup();
		addChangeButtonGroup = new ButtonGroup();
		volConButtonGroup = new ButtonGroup();

		bPanelRadioButton = new JRadioButton("Back Calculate");
		bPanelRadioButton.setActionCommand("Back");
		bPanelRadioButton
				.addActionListener(new java.awt.event.ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ae) {
						radioButtonsActionPerformed(ae);

					}

				});

		cPanelRadioButton = new JRadioButton("Calculate");
		cPanelRadioButton.setActionCommand("Forward");
		cPanelRadioButton.setSelected(true);
		cPanelRadioButton
				.addActionListener(new java.awt.event.ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ae) {
						radioButtonsActionPerformed(ae);

					}

				});

		panelButtonGroup.add(bPanelRadioButton);
		panelButtonGroup.add(cPanelRadioButton);

		addRadioButton = new JRadioButton("Add Water");
		addRadioButton.setActionCommand("Add");
		addRadioButton.setSelected(true);
		addRadioButton
		.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				radioButtonsActionPerformed(ae);

			}

		});

		changeRadioButton = new JRadioButton("Change Water");
		changeRadioButton.setActionCommand("Change");
		changeRadioButton
		.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				radioButtonsActionPerformed(ae);

			}

		});

		addChangeButtonGroup.add(addRadioButton);
		addChangeButtonGroup.add(changeRadioButton);

		volRadioButton = new JRadioButton("Volume");
		volRadioButton.setActionCommand("Vol");
		volRadioButton.setSelected(true);
		volRadioButton
		.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				radioButtonsActionPerformed(ae);

			}

		});

		conRadioButton = new JRadioButton("Concentration");
		conRadioButton.setActionCommand("Conc");
		conRadioButton
		.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				radioButtonsActionPerformed(ae);

			}

		});

		volConButtonGroup.add(volRadioButton);
		volConButtonGroup.add(conRadioButton);

		origWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		origWaterPanel.setLabelString("Starting Water");
		deltaWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		deltaWaterPanel.setLabelString("Add / Change Water");
		resultWaterPanel = new CalculatorWaterPanel(Parameter.ALKALINITY);
		resultWaterPanel.setLabelString("Result Water");
		setupCalcMode();

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

		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(addRadioButton, constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		this.add(changeRadioButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		this.add(bPanelRadioButton, constraints);

		constraints.gridx = 2;
		constraints.gridy = 1;
		this.add(cPanelRadioButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		this.add(volRadioButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 1;
		this.add(conRadioButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(origWaterPanel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		this.add(deltaWaterPanel, constraints);

		constraints.gridx = 2;
		constraints.gridy = 2;
		this.add(resultWaterPanel, constraints);

		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(calculateButton, constraints);

		constraints.gridx = 3;
		this.add(closeButton, constraints);

		this.pack();

	}

	private void closeButtonActionPerformed(ActionEvent ae) {
		this.dispose();
	}

	private void forwardCalculate(String acStr) {
		try {
			Double volA = origWaterPanel.getVolume();
			Double volB = deltaWaterPanel.getVolume();
			Double volF = volA + volB;

			if (acStr.equals("Add")) {
				volA = origWaterPanel.getVolume();
				volB = deltaWaterPanel.getVolume();
				volF = volA + volB;
			} else if (acStr.equals("Change")) {
				volB = deltaWaterPanel.getVolume();
				volA = origWaterPanel.getVolume() - volB;
				volF = volA + volB;
			}

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

	private void backCalculate(String acStr) {
		Integer mode = getCalcMode();
		if (mode <= 1) {
			ErrorDialog.showErrorDialog(this,
					"In Back Calculate with Forward Calculate Mode");
			return;
			
		//  Back calculating Volume	
		} else if (mode == 2 || mode == 3) {
			try {
				Double volA = origWaterPanel.getVolume();
				Double volF = resultWaterPanel.getVolume();
				Double volB = volF - volA;
				
				if (mode == 2) {
					// Adding water back calculating volume
					volA = origWaterPanel.getVolume();
					
				} else if (mode == 3) {
					// Changing water back calculating volume
					volF = origWaterPanel.getVolume();
					
					
					
				}
				ArrayList<Double> vals = new ArrayList<>();
				for (Parameter p : origWaterPanel.getParams()) {
					Double conA = origWaterPanel.getValue(p);
					Double conF = resultWaterPanel.getValue(p);
					Double conB = deltaWaterPanel.getValue(p);
					
					Double resVal = ((conF * volA)- (conA * volA) / (conB - conF));
					vals.add(resVal);
				}
				//  If there were more than one parameter, we'll average the volume 
				//  result for each and update the final values with the best approximation
				Double bestVal = 0.0;
				if(vals.size() > 1){
					for(Double d : vals){
						bestVal += d;
					}
					bestVal /= vals.size();
					
					deltaWaterPanel.setVolume(bestVal);
					
					//  Use forward calculate and the volume we just calculated to calculate
					//  the best possible outcome of final water
					if(mode == 2){
						forwardCalculate("Add");
					}
					else if(mode == 3){
						forwardCalculate("Change");
					}
				}
				else {
					//  Just one parameter up, so just set the volume
					deltaWaterPanel.setVolume(vals.get(0));
				}
				
			} catch (NumberFormatException ex) {
				ErrorDialog.showErrorDialog(this, "Please Enter Valid Value!");
			}
			
			
			
			
			
		//  Back Calculating Concentration	
		} else if (mode == 4 || mode == 5) {
			try {
				Double volA = origWaterPanel.getVolume();
				Double volF = resultWaterPanel.getVolume();
				Double volB = volF - volA;
				
				if (mode == 4) {
					// Adding water back calculating concentration
					volA = origWaterPanel.getVolume();
					volB = deltaWaterPanel.getVolume();
					volF = volA + volB;
					
					resultWaterPanel.setVolume(volF);
					
				} else if (mode == 5) {
					// Changing water back calculating concentration
					volF = origWaterPanel.getVolume();
					volB = deltaWaterPanel.getVolume();
					volA = volF - volB;
					resultWaterPanel.setVolume(volF);
				}

				for (Parameter p : origWaterPanel.getParams()) {
					Double conA = origWaterPanel.getValue(p);
					Double conF = resultWaterPanel.getValue(p);

					Double conB = ((volF * conF) - (volA * conA)) / volB;
					deltaWaterPanel.setValue(p, conB);
				}
			} catch (NumberFormatException ex) {
				ErrorDialog.showErrorDialog(this, "Please Enter Valid Value!");
			}
		}
	}

	private void calculateButtonActionPerformed(ActionEvent ae) {

		String comStr = panelButtonGroup.getSelection().getActionCommand();
		String acStr = addChangeButtonGroup.getSelection().getActionCommand();

		if (comStr.equals("Forward")) {
			forwardCalculate(acStr);
		} else if (comStr.equals("Back")) {
			backCalculate(acStr);
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

	private Integer getCalcMode() {

		/*
		 * 
		 * Need 6 states here: (VF comes from origWaterPanel in change states)
		 * 
		 * Retval Type Have Can get Looking for Can then get 0 Add V1C1 V2C2
		 * VF=V1+V2 CF 1 Change C1 V2C2 VF V1=VF-V2 CF 2 Add V1C1 C2 CF V2
		 * VF=V1+V2 3 Change C1 C2 VFCF V2 V1=VF-V2 4 Add V1C1 V2 CF VF=V1+V2 C2
		 * 5 Change C1 V2 VFCF V1=VF-V2 C2
		 * 
		 * So there are two forward (add and change) and four backcalculates
		 * (back-calc-vol add and change) and (back calc conc add and change)
		 */

		Integer retval = -1;

		String panStr = panelButtonGroup.getSelection().getActionCommand();
		String addStr = addChangeButtonGroup.getSelection().getActionCommand();
		String volStr = volConButtonGroup.getSelection().getActionCommand();

		if (panStr.equals("Forward")) {
			if (addStr.equals("Add")) {
				retval = 0;
			} else if (addStr.equals("Change")) {
				retval = 1;
			}
		} else if (panStr.equals("Back")) {
			if (volStr.equals("Vol")) {
				if (addStr.equals("Add")) {
					retval = 2;
				} else if (addStr.equals("Change")) {
					retval = 3;
				}
			} else if (volStr.equals("Conc")) {
				if (addStr.equals("Add")) {
					retval = 4;
				} else if (addStr.equals("Change")) {
					retval = 5;
				}
			}
		}
		return retval;

	}

	private void setupCalcMode() {
		Integer mode = getCalcMode();
		// invalid input from one or more radio buttons
		if (mode == -1) {
			ErrorDialog
					.showErrorDialog(this,
							"Error Setting up calc mode.  One or more radio buttons returning invalid data");
			return;
		}
		// Forward Calculation.
		else if (mode == 0 || mode == 1) {

			resultWaterPanel.setParamEnabled(false);
			resultWaterPanel.setVolEnabled(false);
			deltaWaterPanel.setParamEnabled(true);
			deltaWaterPanel.setVolEnabled(true);
			
			volRadioButton.setEnabled(false);
			conRadioButton.setEnabled(false);
			
		}
		// Back Calculating Volume
		else if (mode == 2 || mode == 3) {
			resultWaterPanel.setParamEnabled(true);
			resultWaterPanel.setVolEnabled(false);
			deltaWaterPanel.setParamEnabled(true);
			deltaWaterPanel.setVolEnabled(false);
			
			volRadioButton.setEnabled(true);
			conRadioButton.setEnabled(true);
		}
		// Back Calculating Concentration
		else if (mode == 4 || mode == 5) {
			resultWaterPanel.setParamEnabled(true);
			resultWaterPanel.setVolEnabled(false);
			deltaWaterPanel.setParamEnabled(false);
			deltaWaterPanel.setVolEnabled(true);
			
			volRadioButton.setEnabled(true);
			conRadioButton.setEnabled(true);
		}
		
		// Even numbered modes are add modes
		if(mode%2 == 0){
			deltaWaterPanel.setLabelString("Water To Add");
		} else {
			deltaWaterPanel.setLabelString("Change Water");
		}
		this.revalidate();
	}

	private void radioButtonsActionPerformed(java.awt.event.ActionEvent ae) {

		setupCalcMode();
		
	}

}
