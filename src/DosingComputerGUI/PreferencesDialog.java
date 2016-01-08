package DosingComputerGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import dosingcomputer.VolumeUnits;

public class PreferencesDialog extends JDialog {

	private JFileChooser fileChooser = new JFileChooser();

	private JTextField homeDirectoryTextField;
	private JButton browseHomeDirectoryButton;
	private JTextField tankDirectoryTextField;
	private JButton browseTankDirectoryButton;
	private JLabel alkUnitLabel;
	private JComboBox<String> alkUnitComboBox;
	private JLabel salUnitLabel;
	private JComboBox<String> salUnitComboBox;
	private JLabel tankVolUnitLabel;
	private JComboBox<VolumeUnits> tankVolUnitComboBox;
	private JLabel doseVolUnitLabel;
	private JComboBox<VolumeUnits> doseVolUnitComboBox;
	private JButton savePrefsButton;
	private JButton cancelButton;
	private JButton resetButton;

	public PreferencesDialog() {

		initComponents();
	}

	private void initComponents() {
		this.setTitle("Preferences");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		browseHomeDirectoryButton = new JButton("Browse");
		browseHomeDirectoryButton
				.addActionListener(new java.awt.event.ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ae) {
						browseHomeDirectoryButtonActionPerformed(ae);
					}
				});

		homeDirectoryTextField = new JTextField(Preferences.getHomeDirectory()
				.getAbsolutePath());
		homeDirectoryTextField.setEditable(false);

		browseTankDirectoryButton = new JButton("Browse");
		browseTankDirectoryButton
				.addActionListener(new java.awt.event.ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ae) {
						browseTankDirectoryButtonActionPerformed(ae);
					}
				});

		tankDirectoryTextField = new JTextField(Preferences.getTankDirectory()
				.getAbsolutePath());
		tankDirectoryTextField.setEditable(false);

		alkUnitLabel = new JLabel("Alk Units");
		alkUnitComboBox = new JComboBox<>();
		alkUnitComboBox.setModel(new DefaultComboBoxModel<String>(
				Parameter.ALKALINITY.getUnits()));
		alkUnitComboBox.setSelectedItem(Preferences.getAlkUnit());

		salUnitLabel = new JLabel("Salinity Units");
		salUnitComboBox = new JComboBox<>();
		salUnitComboBox.setModel(new DefaultComboBoxModel<String>(
				Parameter.SALINITY.getUnits()));
		salUnitComboBox.setSelectedItem(Preferences.getSalinityUnit());

		tankVolUnitLabel = new JLabel("Tank Volume Units");
		tankVolUnitComboBox = new JComboBox<>();
		tankVolUnitComboBox.setModel(new DefaultComboBoxModel<VolumeUnits>(
				VolumeUnits.values()));
		tankVolUnitComboBox.setSelectedItem(Preferences.getTankVolumeUnit());

		doseVolUnitLabel = new JLabel("Dose Volume Units");
		doseVolUnitComboBox = new JComboBox<>();
		doseVolUnitComboBox.setModel(new DefaultComboBoxModel<VolumeUnits>(
				VolumeUnits.values()));
		doseVolUnitComboBox.setSelectedItem(Preferences.getDoseVolumeUnit());

		savePrefsButton = new JButton("Save");
		savePrefsButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				savePrefsButtonActionPerformed(ae);
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				cancelButtonActionPerformed(ae);
			}
		});

		resetButton = new JButton("Reset");
		resetButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				resetButtonActionPerformed(ae);
			}
		});
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5,5,5,5);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(new JLabel("Update Preferences :"), constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(homeDirectoryTextField, constraints);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(browseHomeDirectoryButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(tankDirectoryTextField, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(browseTankDirectoryButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(alkUnitLabel, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(alkUnitComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(salUnitLabel, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(salUnitComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(tankVolUnitLabel, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(tankVolUnitComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(doseVolUnitLabel, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		this.add(doseVolUnitComboBox, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		
		constraints.gridx = 1;
		constraints.gridy = 7;
		this.add(resetButton, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 7;
		this.add(cancelButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 7;
		this.add(savePrefsButton, constraints);
		
		
		
		this.pack();
		

	}

	private void browseHomeDirectoryButtonActionPerformed(
			java.awt.event.ActionEvent ae) {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = fileChooser.showOpenDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION) {
//			Preferences.setHomeDirectory(fileChooser.getSelectedFile());
			homeDirectoryTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		
	}

	private void browseTankDirectoryButtonActionPerformed(
			java.awt.event.ActionEvent ae) {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = fileChooser.showOpenDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION) {
//			Preferences.setTankDirectory(fileChooser.getSelectedFile());
			tankDirectoryTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		
	}

	private void savePrefsButtonActionPerformed(java.awt.event.ActionEvent ae) {
		Preferences.setHomeDirectory(new File(homeDirectoryTextField.getText()));
		Preferences.setTankDirectory(new File(tankDirectoryTextField.getText()));
		Preferences.setAlkUnit(alkUnitComboBox.getSelectedItem().toString());
		Preferences.setSalinityUnit(salUnitComboBox.getSelectedItem().toString());
		Preferences.setTankVolumeUnit((VolumeUnits)tankVolUnitComboBox.getSelectedItem());
		Preferences.setDoseVolumeUnit((VolumeUnits)doseVolUnitComboBox.getSelectedItem());
		Preferences.savePreferences();
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent ae) {
		this.dispose();
	}

	private void resetButtonActionPerformed(java.awt.event.ActionEvent ae) {
		this.homeDirectoryTextField.setText(Preferences.getHomeDirectory().getAbsolutePath());
		this.tankDirectoryTextField.setText(Preferences.getTankDirectory().getAbsolutePath());
		this.alkUnitComboBox.setSelectedItem(Preferences.getAlkUnit());
		this.salUnitComboBox.setSelectedItem(Preferences.getSalinityUnit());
		this.tankVolUnitComboBox.setSelectedItem(Preferences.getTankVolumeUnit());
		this.doseVolUnitComboBox.setSelectedItem(Preferences.getDoseVolumeUnit());
		
	}

}
