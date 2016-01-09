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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dosingcomputer.Preferences;

public class StartupWizard {
	
	private static StartupWizard sw = new StartupWizard();

	public StartupWizard() {
		
	}

	public static void runWizard() {
		
		File hfile = new File("");
		
		while(!hfile.isDirectory()){
			hfile = chooseDirectory("Choose Home Directory :" , Preferences.getHomeDirectory());
		}
		
		File tfile = new File("");
		
		while(!tfile.isDirectory()) {
			tfile = chooseDirectory("Choose Tank Directory :" , Preferences.getTankDirectory());
		}
		
//		System.out.println("Home Directory : " + hfile.getAbsolutePath());
//		System.out.println("Tank Directory : " + tfile.getAbsolutePath());
		
		Preferences.setHomeDirectory(hfile);
		Preferences.setTankDirectory(tfile);
		
	}

	private static File chooseDirectory(String aMessage, File aStart) {
		
		FPanel fPanel = sw.new FPanel(aStart);

		String newFileStr = "Fail";
		int ret = JOptionPane.showConfirmDialog(null, fPanel,
				aMessage, JOptionPane.OK_CANCEL_OPTION);

		if (ret == JOptionPane.OK_OPTION) {
			newFileStr = fPanel.fText.getText();
		}

//		System.out.println("newFileStr : " + newFileStr);
		return new File(newFileStr);
	}

	private class FPanel extends JPanel {
		private JTextField fText;
		private JButton browseButton;
		private JLabel warningLabel;

		public FPanel(File aFile) {
			fText = new JTextField(aFile.getAbsolutePath() , 30);
			// fText.setMinimumSize(new Dimension()););
			warningLabel = new JLabel();
			checkFileName();
			browseButton = new JButton();
			browseButton.setText("Browse");
			browseButton.addActionListener(new java.awt.event.ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					browseButtonActionPerformed(ae);

				}

			});

			this.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();

			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.fill = GridBagConstraints.NONE;

			this.add(fText, constraints);

			constraints.gridx = 1;
			this.add(browseButton, constraints);

			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth = 2;
			this.add(warningLabel, constraints);

		}

		private void checkFileName() {
			if (!(new File(fText.getText()).isDirectory())) {
				warningLabel.setText("Invalid Directory Name");
				warningLabel.setForeground(Color.RED);
			} else {
				warningLabel.setText("");
				warningLabel.setForeground(Color.BLACK);
			}
		}

		private void browseButtonActionPerformed(java.awt.event.ActionEvent ae) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retval = fileChooser.showOpenDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				this.fText.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());
			}
			checkFileName();
		}

	}

}
