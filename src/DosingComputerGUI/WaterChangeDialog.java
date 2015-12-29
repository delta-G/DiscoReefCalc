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

import dosingcomputer.Preferences;
import dosingcomputer.Water;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import dosingcomputer.DosingComputer;
import dosingcomputer.Tank;
import javax.swing.JDialog;

/**
 *
 * @author David
 */
public class WaterChangeDialog extends JDialog {

    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton calcButton;
    private WaterSetupPanel wsPanel;
    private WaterSetupPanel calPanel;
    private JTextField volumeTextField;
    private JLabel unitLabel;
    private JLabel volumeLabel;
    private DateTimePanel dateTimePanel;

    public WaterChangeDialog() {
        initComponents();
    }

    private void initComponents() {

        this.setTitle("Water Change");

        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        calcButton = new javax.swing.JButton();
        wsPanel = new WaterSetupPanel();
        calPanel = new WaterSetupPanel();
        volumeTextField = new JTextField(5);
        unitLabel = new JLabel();
        volumeLabel = new JLabel();
        dateTimePanel = new DateTimePanel();

        unitLabel.setText(Preferences.getTankVolumeUnit().toString());
        volumeLabel.setText("Enter Volume to Change  ");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelButtonActionPerformed(ae);
            }
        });

        okButton.setText("Do Change");
        okButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                okButtonActionPerformed(ae);
            }
        });

        calcButton.setText("Calculate");
        calcButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                calcButtonActionPerformed(ae);
            }
        });

        this.setLayout(new GridBagLayout());

        GridBagConstraints tfc = new GridBagConstraints();
        tfc.fill = GridBagConstraints.HORIZONTAL;
        tfc.gridx = 0;
        tfc.gridy = 0;
        tfc.gridheight = 1;
        tfc.gridwidth = 1;
        tfc.insets = new Insets(5, 5, 0, 0);
        this.add(volumeLabel, tfc);
        tfc.gridx = 1;
        tfc.gridwidth = 1;
        this.add(volumeTextField, tfc);
        tfc.gridx = 2;
        tfc.gridwidth = 1;
        this.add(unitLabel, tfc);

        GridBagConstraints dtpc = new GridBagConstraints();
        dtpc.fill = GridBagConstraints.NONE;
        dtpc.anchor = GridBagConstraints.LINE_START;
        dtpc.insets = new Insets(5, 5, 5, 5);
        dtpc.gridx = 0;
        dtpc.gridy = 1;
        this.add(dateTimePanel, dtpc);

        GridBagConstraints wspc = new GridBagConstraints();
        wspc.fill = GridBagConstraints.HORIZONTAL;
        wspc.gridx = 0;
        wspc.gridy = 2;
        wspc.weightx = .7;
        wspc.weighty = .7;
        wspc.gridheight = 1;
        wspc.gridwidth = 2;
        wspc.insets = new Insets(0, 0, 0, 30);
        wsPanel.setBorder(new LineBorder(java.awt.Color.BLACK));
        wsPanel.setWater(DosingComputer.guiFrame.getCurrentTank().getTankWater());
        this.add(wsPanel, wspc);

        wspc.gridx = 3;
        wspc.gridwidth = 4;
        wspc.weightx = 0.7;
        calPanel.setBorder(new LineBorder(java.awt.Color.BLACK));
        calPanel.setEnabled(false);
        this.add(calPanel, wspc);

        GridBagConstraints butc = new GridBagConstraints();
        butc.fill = GridBagConstraints.NONE;
        butc.gridx = 6;
        butc.gridy = 3;
        butc.gridheight = 1;
        butc.gridwidth = 1;
        butc.insets = new Insets(10, 0, 0, 5);
        butc.anchor = GridBagConstraints.LAST_LINE_END;

        this.add(cancelButton, butc);
        butc.gridx = 5;
        this.add(okButton, butc);
        butc.gridx = 4;
        butc.weightx = 0.7;
        this.add(calcButton, butc);
        this.pack();
    }

    public Water getChangeWater() {
        return wsPanel.getWater();
    }

    public Water getCalculatedWater() {
        return calPanel.getWater();
    }

    private void doCalculation() throws java.lang.NumberFormatException {
        Tank theTank = DosingComputer.guiFrame.getCurrentTank();
        Water tankWater = theTank.getTankWater();
        Double changeVol = 0.0;
        try {
            changeVol = Preferences.getTankVolumeUnit().convertFromUnit(Double.parseDouble(volumeTextField.getText()));

            if (changeVol.isNaN() || changeVol <= 0.0 || changeVol > theTank.getVolume()) {
                ErrorDialog.showErrorDialog("Please Enter a Valid Volume");
                throw new java.lang.NumberFormatException();
            }

        } catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Please Enter a Valid Volume");
            throw new java.lang.NumberFormatException();
        }

        Water calWater = new Water(Water.calculateWaterMix(tankWater, theTank.getVolume() - changeVol, this.getChangeWater(), changeVol));

        calPanel.setWater(calWater);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            doCalculation();
        } catch (java.lang.NumberFormatException ex) {
            return;
        }
        Double changeVol = Preferences.getTankVolumeUnit().convertFromUnit(Double.parseDouble(volumeTextField.getText()));
        DosingComputer.guiFrame.getCurrentTank().doWaterChange(changeVol, this.getChangeWater(), dateTimePanel.getSelectedDate().getTime());
    }

    private void calcButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doCalculation();
    }

}
