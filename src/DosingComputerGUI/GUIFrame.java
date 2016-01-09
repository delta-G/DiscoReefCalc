/**     Copyright (C) 2015  David Caldwell  disco47dave at gmail dot com

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
import dosingcomputer.DosingProduct;
import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import dosingcomputer.TankFile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author David
 */
public class GUIFrame  extends JFrame {
    private DefaultComboBoxModel comboModel;
    private Tank currentTank;
    
    private JMenuItem dosingproductsMenuItemAddNewProduct;
    private JMenuItem dosingproductsMenuItemRemoveProduct;
    private JMenuItem dosingproductsMenuItemSetDosage;
    private JMenuItem fileMenuItemCreateTank;
    private JMenuItem fileMenuItemSaveTank;
    private JMenuItem editMenuItemCalculator;
    private JMenuItem editMenuItemPreferences;
    private JButton exitButton;
//    private JButton jButton2;
//    private JButton jButton3;
//    private JButton jButton4;
    private JLabel labelSaysTankName;
    private JLabel labelSaysVolume;
    private JMenu jMenu_File;
    private JMenu jMenu_Edit;
    private JMenu jMenu_Tank;
    private JMenu jMenu_DosingProducts;
    private JMenuBar jMenuBar;
    private JPanel parameterPanel;
    private JPanel prodPanel;
    private JMenuItem tankMenuItemAddWaterTest;
    private JMenuItem tankMenuItemCalculateConsumption;
    private JMenuItem tankMenuItemCalculateDosing;
    private JMenuItem tankMenuItemManualDose;
    private JMenuItem tankMenuItemSetParameterTargets;
    private JMenuItem tankMenuItemWaterChange;
    private JComboBox tankNameComboBox;
    private JLabel tankNameLabel;
    private JLabel tankVolumeLabel;
    private JLabel volumeUnitLabel;
    

    public final Tank getCurrentTank() {
        return currentTank;
    }

    public void addNewTank(String name, Double vol) {
        if ((!name.equals("")) && (!DosingComputer.getTankNames().contains(name))) {
            Tank newTank = new Tank(name, vol);
            DosingComputer.addTank(newTank);

            setMeUp(newTank.getName());

        }

    }

    public void doTankChange() {
        if (currentTank != null) {
            currentTank.releaseAllPropertyChangeListeners();
        }
        currentTank = DosingComputer.getTank(tankNameComboBox.getSelectedItem().toString());

        setupParamPanels();
        setupProductPanels();

        tankNameLabel.setText(this.getCurrentTank().getName());
        Double volDoub = Preferences.getTankVolumeUnit().convertToUnit((double) this.getCurrentTank().getVolume());
        tankVolumeLabel.setText(String.format("%.2f", volDoub));
        volumeUnitLabel.setText(Preferences.getTankVolumeUnit().toString());

        this.getCurrentTank().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String propName = pce.getPropertyName();

                switch (propName) {
                    case "removeProduct":
                    case "addProduct":
                        prodPanel.removeAll();
                        setupProductPanels();
                        break;
                }

            }
        });

    }

    public void setMeUp() {
        comboModel = new DefaultComboBoxModel(DosingComputer.getTankNames().toArray());
        tankNameComboBox.setModel(comboModel);
        tankNameComboBox.setSelectedItem(0);

        doTankChange();
        this.pack();
    }
    
    public void setMeUp(String aTankName) {
        comboModel = new DefaultComboBoxModel(DosingComputer.getTankNames().toArray());
        tankNameComboBox.setModel(comboModel);
        tankNameComboBox.setSelectedItem(aTankName);

        doTankChange();
        this.pack();
    }

    public GUIFrame() {
        initComponents();
        this.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent e) {
                DosingComputer.closeOut();
            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    private void setupParamPanels() {
        parameterPanel.removeAll();
        parameterPanel.setLayout(new GridLayout(1, 0));
        ParameterInfoPanel labelPanel = new ParameterInfoPanel();
        parameterPanel.add(labelPanel);
        for (Parameter p : this.getCurrentTank().getParameters()) {
            parameterPanel.add(new ParameterInfoPanel(p));
        }
        parameterPanel.revalidate();

    }

    private void setupProductPanels() {
        prodPanel.removeAll();
//        prodPanel.setLayout(new GridLayout(0, 1));
//        DosePanel labelPanel = new DosePanel();
//        prodPanel.add(labelPanel);
//        for (DosingProduct dp : this.getCurrentTank().getDosingProducts()) {
//            prodPanel.add(new DosePanel(dp));
//        }
        
        JPanel npan = new JPanel();
        JPanel dpan = new JPanel();
        
        npan.setLayout(new GridLayout(0, 1));
        dpan.setLayout(new GridLayout(0, 1));
        npan.add(new JLabel("Dosing Product Name"));
        dpan.add(new JLabel("Daily Dosage"));
        for (DosingProduct dp : this.getCurrentTank().getDosingProducts()) {
        	npan.add(new JLabel(dp.getName()));
        	JPanel ppan = new JPanel();
        	ppan.setLayout(new BoxLayout(ppan, BoxLayout.X_AXIS));
        	JLabel doslab = new JLabel();
        	JLabel unitlab = new JLabel();
        	doslab.setText(String.format("%.2f", Preferences.getDoseVolumeUnit().convertToUnit(this.getCurrentTank().getDailyDose(dp))));
        	unitlab.setText(Preferences.getDoseVolumeUnit().toString());
        	ppan.add(doslab);
        	ppan.add(Box.createRigidArea(new Dimension(10,0)));
        	ppan.add(unitlab);
        	
        	dpan.add(ppan);
        }
        prodPanel.setLayout(new BoxLayout(prodPanel, BoxLayout.X_AXIS));
        prodPanel.add(npan);
        prodPanel.add(Box.createRigidArea(new Dimension(35,0)));
        prodPanel.add(dpan);
        
        prodPanel.revalidate();
        
    }
    
    
    private void initComponents() {
        
//        jButton2 = new JButton();
        parameterPanel = new JPanel();
        prodPanel = new JPanel();
        tankNameLabel = new JLabel();
        tankVolumeLabel = new JLabel();
        labelSaysTankName = new JLabel();
        labelSaysVolume = new JLabel();
        tankNameComboBox = new JComboBox();
        exitButton = new JButton();
        volumeUnitLabel = new JLabel();
//        jButton3 = new JButton();
//        jButton4 = new JButton();
        jMenuBar = new JMenuBar();
        jMenu_File = new JMenu();
        fileMenuItemCreateTank = new JMenuItem();
        fileMenuItemSaveTank = new JMenuItem();
        editMenuItemCalculator = new JMenuItem();
        editMenuItemPreferences = new JMenuItem();
        jMenu_Edit = new JMenu();
        jMenu_Tank = new JMenu();
        tankMenuItemAddWaterTest = new JMenuItem();
        tankMenuItemSetParameterTargets = new JMenuItem();
        jMenu_DosingProducts = new JMenu();
        dosingproductsMenuItemAddNewProduct = new JMenuItem();
        dosingproductsMenuItemRemoveProduct = new JMenuItem();
        dosingproductsMenuItemSetDosage = new JMenuItem();
        tankMenuItemCalculateConsumption = new JMenuItem();
        tankMenuItemCalculateDosing = new JMenuItem();
        tankMenuItemManualDose = new JMenuItem();
        tankMenuItemWaterChange = new JMenuItem();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        tankNameLabel.setText("tank name");

        tankVolumeLabel.setText("tank volume");

        labelSaysTankName.setText("Tank Name");

        labelSaysVolume.setText("Volume");

        tankNameComboBox.setPrototypeDisplayValue("Areallylongnameforatanktohave");
        tankNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankNameComboBoxActionPerformed(evt);
            }
        });
        

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        volumeUnitLabel.setText("Vol Unit");

//        jButton3.setText("Time Spoof");
//        jButton3.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton3ActionPerformed(evt);
//            }
//        });

//        jButton4.setText("jButton4");
//        jButton4.addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton4ActionPerformed(evt);
//            }
//        });

        jMenu_File.setText("File");

        fileMenuItemCreateTank.setText("Create Tank");
        fileMenuItemCreateTank.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuItemCreateTankActionPerformed(evt);
            }
        });
        jMenu_File.add(fileMenuItemCreateTank);

        fileMenuItemSaveTank.setText("Save Tank");
        fileMenuItemSaveTank.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuItemSaveTankActionPerformed(evt);
            }
        });
        jMenu_File.add(fileMenuItemSaveTank);

        jMenuBar.add(jMenu_File);

        jMenu_Edit.setText("Edit");
        
        editMenuItemCalculator.setText("Calculator");
        editMenuItemCalculator.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent ae){
        		editMenuItemCalculatorActionPerformed(ae);
        	}			
        });
        jMenu_Edit.add(editMenuItemCalculator);
        
        editMenuItemPreferences.setText("Preferences");
        editMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				editMenuItemPreferencesActionPerformed(ae);				
			}
        	
        });
        jMenu_Edit.add(editMenuItemPreferences);
        
        jMenuBar.add(jMenu_Edit);

        jMenu_Tank.setText("Tank");
        jMenu_Tank.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        tankMenuItemAddWaterTest.setText("Add Water Test");
        tankMenuItemAddWaterTest.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemAddWaterTestActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemAddWaterTest);

        tankMenuItemSetParameterTargets.setText("Set Parameter Targets");
        tankMenuItemSetParameterTargets.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemSetParameterTargetsActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemSetParameterTargets);

        jMenu_DosingProducts.setText("Dosing Products");

        dosingproductsMenuItemAddNewProduct.setText("Add New Product");
        dosingproductsMenuItemAddNewProduct.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosingproductsMenuItemAddNewProductActionPerformed(evt);
            }
        });
        jMenu_DosingProducts.add(dosingproductsMenuItemAddNewProduct);

        dosingproductsMenuItemRemoveProduct.setText("Remove Product");
        jMenu_DosingProducts.add(dosingproductsMenuItemRemoveProduct);

        dosingproductsMenuItemSetDosage.setText("Set Dosage");
        dosingproductsMenuItemSetDosage.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosingproductsMenuItemSetDosageActionPerformed(evt);
            }
        });
        jMenu_DosingProducts.add(dosingproductsMenuItemSetDosage);

        jMenu_Tank.add(jMenu_DosingProducts);

        tankMenuItemCalculateConsumption.setText("Calculate Consumption");
        tankMenuItemCalculateConsumption.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemCalculateConsumptionActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemCalculateConsumption);

        tankMenuItemCalculateDosing.setText("Calculate Recommended Dosing");
        tankMenuItemCalculateDosing.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemCalculateDosingActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemCalculateDosing);

        tankMenuItemManualDose.setText("Manual Dose");
        tankMenuItemManualDose.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemManualDoseActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemManualDose);

        tankMenuItemWaterChange.setText("Water Change");
        tankMenuItemWaterChange.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankMenuItemWaterChangeActionPerformed(evt);
            }
        });
        jMenu_Tank.add(tankMenuItemWaterChange);

        jMenuBar.add(jMenu_Tank);

        setJMenuBar(jMenuBar);
        
        
        
        //parameterPanel.setBorder(new LineBorder(Color.black, 2, true));
        parameterPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 2, true) , new EmptyBorder(10,10,10,10) ));
        prodPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black, 2, true) , new EmptyBorder(10,10,10,10) ));
        
        
        //**  Layout Section  **//
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(30,10,5,5);
        gbc.fill = GridBagConstraints.NONE;        
        gbc.anchor = GridBagConstraints.LINE_END;        
        gbc.gridy = 1;
        gbc.gridx = 2;
        this.add(labelSaysTankName, gbc);
        
        gbc.insets = new Insets(5,10,5,5);
        gbc.fill = GridBagConstraints.NONE;        
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridy = 2;
        gbc.gridx = 2;
        this.add(labelSaysVolume, gbc);
        
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 2;
        gbc.gridx = 3;
        this.add(tankVolumeLabel, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 2;
        gbc.gridx = 4;
        this.add(volumeUnitLabel, gbc);
        
        gbc.insets = new Insets(30,10,5,5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 1;
        gbc.gridx = 3;
        gbc.gridwidth = 3;
        this.add(tankNameComboBox, gbc);
        
        //setMeUp();
        
        GridBagConstraints pc = new GridBagConstraints();
        pc.insets = new Insets(10, 10, 5, 5);
        pc.fill = GridBagConstraints.NONE;
        pc.anchor = GridBagConstraints.LINE_START;
        
        pc.gridx = 1;
        pc.gridy = 3;
        pc.gridwidth = 5;
        this.add(parameterPanel, pc);
        
        pc.gridy = 4;
        this.add(prodPanel, pc);
        
        pc.gridy = 5;
        pc.gridx = 6;
        pc.weightx = 0.7;
        pc.weighty = 0.7;
        pc.anchor = GridBagConstraints.LINE_END;
        this.add(exitButton, pc);
        
        //this.pack();       
        
        
    }
    
    
    
    
     private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {  
//        System.out.println("******** Tank Redord Start *******");
//        for (TankRecord rec : this.getCurrentTank().getTankRecordFile()) {
//            System.out.println(rec.toString());
//        }
//        System.out.println("******** Tank Redord End *******");

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.add(new EventFileViewPanel());
        jf.setSize(800, 600);

        jf.setVisible(true);

    }                                        

    private void fileMenuItemCreateTankActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        CreateTankDialog ctf = new CreateTankDialog();
        ctf.setLocationRelativeTo(this); 
        ctf.setMinimumSize(ctf.getPreferredSize());
        ctf.setModal(true);
        ctf.setVisible(true);
    }                                                      

    private void fileMenuItemSaveTankActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        TankFile.saveTankToFile(this.getCurrentTank());
    }         
    
    private void editMenuItemCalculatorActionPerformed(java.awt.event.ActionEvent ae) {
		
    	CalculatorDialog cdi = new CalculatorDialog();
    	cdi.setLocationRelativeTo(this);
    	cdi.setMinimumSize(cdi.getPreferredSize());
    	cdi.setModal(true);
    	cdi.setVisible(true);
		
	}
    
    private void editMenuItemPreferencesActionPerformed(java.awt.event.ActionEvent ae) {
    	PreferencesDialog pdi = new PreferencesDialog();
    	pdi.setLocationRelativeTo(this);
    	pdi.setMinimumSize(pdi.getPreferredSize());
    	pdi.setModal(true);
    	pdi.setVisible(true);
    	this.setupParamPanels();
    	this.setupProductPanels();
    	
    }

    private void tankMenuItemAddWaterTestActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        AddWaterTestDialog resframe = new AddWaterTestDialog(this.getCurrentTank());
        resframe.setLocationRelativeTo(this); 
        resframe.setMinimumSize(resframe.getPreferredSize());
        resframe.setModal(true);
        resframe.setVisible(true);
    }                                                        

    private void tankMenuItemSetParameterTargetsActionPerformed(java.awt.event.ActionEvent evt) {                                                                
        SetParameterTargetsDialog sptf = new SetParameterTargetsDialog(this.getCurrentTank());
        sptf.setLocationRelativeTo(this);
        sptf.setMinimumSize(sptf.getPreferredSize());
        sptf.setModal(true);
        sptf.setVisible(true);
    }                                                               

    private void dosingproductsMenuItemAddNewProductActionPerformed(java.awt.event.ActionEvent evt) {                                                                    
        AddDosingProductDialog apf = new AddDosingProductDialog(this.getCurrentTank());
        apf.setLocationRelativeTo(this); 
        apf.setMinimumSize(apf.getPreferredSize());
        apf.setModal(true);
        apf.setVisible(true);
    }                                                                   

    private void tankMenuItemCalculateConsumptionActionPerformed(java.awt.event.ActionEvent evt) {                                                                 
        this.getCurrentTank().setConsumption();

    }                                                                

    private void dosingproductsMenuItemSetDosageActionPerformed(java.awt.event.ActionEvent evt) {                                                                
        SetDailyDoseDialog sdf = new SetDailyDoseDialog(this.getCurrentTank());
        sdf.setLocationRelativeTo(this); 
        sdf.setMinimumSize(sdf.getPreferredSize());
        sdf.setModal(true);
        sdf.setVisible(true);
    }                                                               

    private void tankMenuItemCalculateDosingActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        CalculateDosesDialog cdf = new CalculateDosesDialog(this.getCurrentTank());
        cdf.setLocationRelativeTo(this);  
        cdf.setMinimumSize(cdf.getPreferredSize());
        cdf.setModal(true);
        cdf.setVisible(true);

    }                                                           

    private void tankNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        doTankChange();
    }                                                

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.dispose();
        DosingComputer.closeOut();
    }                                        

    private void tankMenuItemManualDoseActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        AddManualDoseDialog mdf = new AddManualDoseDialog();
        mdf.setLocationRelativeTo(this);  
        mdf.setMinimumSize(mdf.getPreferredSize());
        mdf.setModal(true);
        mdf.setVisible(true);
    }                                                      

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {   
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.add(new TimeSpoofPanel());
        jf.setSize(220, 100);

        jf.setVisible(true);

    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {  
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jf.add(new DatedGraphPanel(getCurrentTank().getTankRecordFile().getLastTests(10, Parameter.ALKALINITY)));
        jf.setSize(1100, 950);

        jf.setVisible(true);

    }                                        

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void tankMenuItemWaterChangeActionPerformed(java.awt.event.ActionEvent evt) {   
        WaterChangeDialog wcf = new WaterChangeDialog();
        wcf.setLocationRelativeTo(this); 
        wcf.setMinimumSize(wcf.getPreferredSize());
        wcf.setModal(true);
        wcf.setVisible(true);
    }                       
    
}
