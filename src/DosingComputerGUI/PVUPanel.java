/**     Copyright (C) 2015  David Caldwell  disco47dave@gmail.com

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

import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author David
 */
public class PVUPanel extends JPanel {

    private ComboBoxModel paramModel;
    private ComboBoxModel unitModel;
    private Parameter currentParamSelection;

    private JComboBox paramComboBox;
    private JComboBox unitComboBox;
    private JTextField valueTextBox;

    public PVUPanel() {
        initComponents();

        paramModel = new DefaultComboBoxModel(Parameter.values());
        currentParamSelection = Parameter.parseParam(paramModel.getSelectedItem().toString());
        paramComboBox.setModel(paramModel);
        setupUnitComboBox();
        unitComboBox.setModel(unitModel);
        
    }

    public PVUPanel(Parameter p) {
        
        initComponents();

        Parameter[] parray = new Parameter[1];
        parray[0] = p;
        paramModel = new DefaultComboBoxModel(parray);
        paramModel.setSelectedItem(p);
        currentParamSelection = Parameter.parseParam(paramModel.getSelectedItem().toString());
        paramComboBox.setModel(paramModel);        
        setupUnitComboBox();
        unitComboBox.setModel(unitModel);
        
        paramComboBox.setPrototypeDisplayValue("MagnesiumNit");
        unitComboBox.setPrototypeDisplayValue("12345");
        
    }

    //  **TODO
    //  When we add more parameters, we're not going to be able to use ppm for all of them. 
    private void setupUnitComboBox() {
        unitModel = new DefaultComboBoxModel(currentParamSelection.getUnits());
        if (currentParamSelection == Parameter.ALKALINITY) {
            unitModel.setSelectedItem(Preferences.getAlkUnit());
        } else if(currentParamSelection == Parameter.SALINITY){
        	unitModel.setSelectedItem(Preferences.getSalinityUnit());
        } else {
            unitModel.setSelectedItem("ppm");
        }
        unitComboBox.setModel(unitModel);
    }

    private void setParameterSelection(Parameter p) {
        this.currentParamSelection = p;
    }

    public Parameter getParameterSelection() {
        return this.currentParamSelection;
    }

    public String getUnitSelection() {
        return this.unitComboBox.getSelectedItem().toString();
    }

    public Double getEnteredValue() {
        Double retval = Double.parseDouble(this.valueTextBox.getText());
        return retval;
    }

    public Double getConvertedValue() throws java.lang.NumberFormatException {
        Double retval;
        retval = this.currentParamSelection.convertFromUnit(getUnitSelection(), Double.parseDouble(this.valueTextBox.getText()));
        if(retval.isNaN() || retval.isInfinite() || retval < 0.0){
            throw new java.lang.NumberFormatException();
        }
        return retval;
    }

    public void setEnteredValue(Double aVal) {
        valueTextBox.setText(aVal.toString());
    }

    public void setValueConverted(Double aVal) {
        Double conVal = this.currentParamSelection.convertToUnit(getUnitSelection(), aVal);
        valueTextBox.setText(conVal.toString());
    }

    public void setPVUEnabled(boolean aBoo) {
        this.valueTextBox.setEditable(aBoo);
        this.unitComboBox.setEnabled(aBoo);
    }

    private void initComponents() {

        paramComboBox = new JComboBox();
        valueTextBox = new JTextField();
        valueTextBox.setColumns(5);
        valueTextBox.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unitComboBox = new JComboBox();

        paramComboBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramComboBoxActionPerformed(evt);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        this.add(paramComboBox, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.add(valueTextBox, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.weightx = 0;
        this.add(unitComboBox, gbc);

    }

    private void paramComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Parameter p = Parameter.parseParam(paramComboBox.getSelectedItem().toString());
        if (p != currentParamSelection) {
            setParameterSelection(p);
            setupUnitComboBox();

        }
    }

}
