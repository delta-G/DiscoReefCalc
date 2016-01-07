package DosingComputerGUI;

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
