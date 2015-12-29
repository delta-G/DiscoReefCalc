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

import dosingcomputer.DosingComputer;
import dosingcomputer.Preferences;
import javax.swing.JDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author David
 */
public class CreateTankDialog extends JDialog {

    private JButton cancelButton;
    private JButton okButton;
    private JLabel nameLabel;
    private JLabel volumeLabel;
    private JTextField nameField;
    private JTextField volumeField;
    private JLabel unitLabel;

    public CreateTankDialog() {
        initComponents();
    }

    private void initComponents() {
        
        this.setTitle("Create New Tank");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        cancelButton = new JButton();
        okButton = new JButton();
        nameLabel = new JLabel("Tank Name:");
        volumeLabel = new JLabel("Net Water Volume:");
        nameField = new JTextField();
        volumeField = new JTextField();
        unitLabel = new JLabel(Preferences.getTankVolumeUnit().toString());

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelButtonActionPerformed(ae);
            }
        });

        okButton.setText("Create");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                okButtonActionPerformed(ae);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(volumeLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        this.add(nameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(volumeField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 0;
        gbc.gridx = 3;
        gbc.weightx = 0.0;
        this.add(unitLabel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridwidth = 1;
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.weighty = 0.7;
        this.add(cancelButton, gbc);

        gbc.gridx = 3;
        this.add(okButton, gbc);

        this.pack();

    }

    private void okButtonActionPerformed(ActionEvent ae) {

        String newName = nameField.getText();
        Double newVol;
        if ("".equals(newName)) {
            ErrorDialog.showErrorDialog("Please Enter a Name for the Tank");
            return;
        }
        try {
            newVol = Double.parseDouble(volumeField.getText());

            if (newVol.isNaN() || newVol <= 0.0) {
                ErrorDialog.showErrorDialog("Please Enter a Valid Volume");
                return;
            }
        }
        catch (java.lang.NumberFormatException ex) {
            ErrorDialog.showErrorDialog("Please Enter a Valid Volume");
                return;
        }

        // If we get here we have a valid name and volume
        DosingComputer.guiFrame.addNewTank(newName, Preferences.getTankVolumeUnit().convertFromUnit(newVol));
        this.dispose();
    }

    private void cancelButtonActionPerformed(ActionEvent ae) {
        this.dispose();
    }

}
