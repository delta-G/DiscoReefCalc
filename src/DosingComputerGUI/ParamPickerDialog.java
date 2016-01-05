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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import dosingcomputer.Parameter;

public class ParamPickerDialog extends JDialog {
	
	private HashMap<Parameter , JCheckBox> boxes;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel boxPanel;
	
	private Integer selectionState = 0;
	
	
	
	
	public ParamPickerDialog(){
		boxes = new HashMap<>();
		initComponents();
		this.setModal(true);
	}
	
	public ParamPickerDialog(ArrayList<Parameter> aList){
		boxes = new HashMap<>();
		initComponents();
		for(Parameter p : aList){
			boxes.get(p).setSelected(true);
		}
		this.setModal(true);
	}
	
	private void initComponents(){
		
		okButton = new JButton("OK");
		okButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent ae){
				okButtonActionPerformed(ae);
			}			
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent ae){
				cancelButtonActionPerformed(ae);
			}			
		});
		
		boxPanel = new JPanel();
		
		setupBoxes();
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		this.add(boxPanel, constraints);
		
		constraints.gridy = 1;
		this.add(cancelButton, constraints);
		constraints.gridx = 1;
		this.add(okButton, constraints);
		
		this.pack();
		
		
	}
	
	private void setupBoxes(){
		for(Parameter p:Parameter.values()){
			boxes.put(p , new JCheckBox(p.toString()));
		}
		
		boxPanel.removeAll();
		for(Parameter p : boxes.keySet()){
			boxPanel.add(boxes.get(p));
		}		
	}
	
	private ArrayList<Parameter> readBoxes() {
		ArrayList<Parameter> retval = new ArrayList<>();
		for(Parameter p : boxes.keySet()){
			if(boxes.get(p).isSelected()){
				retval.add(p);
			}
		}
		return retval;
	}
	
	private Integer getSelectionState(){
		return selectionState;
	}
	
	public static ArrayList<Parameter> getParameters() throws InterruptedException {
		
		ArrayList<Parameter> retval = new ArrayList<>();
		
		ParamPickerDialog ppd = new ParamPickerDialog();
		ppd.setVisible(true);
		
		if(ppd.getSelectionState() == 1){
			retval = ppd.readBoxes();
		}
		
		ppd.dispose();
		return retval;	
	}
	
public static ArrayList<Parameter> getParameters(ArrayList<Parameter> aList) throws InterruptedException {
		
		ArrayList<Parameter> retval = new ArrayList<>();
		
		ParamPickerDialog ppd = new ParamPickerDialog(aList);
		ppd.setVisible(true);
		
		if(ppd.getSelectionState() == 1){
			retval = ppd.readBoxes();
		} else if(ppd.getSelectionState() == 2){
			retval = aList;
		}
		
		ppd.dispose();
		return retval;	
	}
	
	private void okButtonActionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		selectionState = 1;
		this.dispose();
		
	}
	
	private void cancelButtonActionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		selectionState = 2;
		this.dispose();
		
	}

}
