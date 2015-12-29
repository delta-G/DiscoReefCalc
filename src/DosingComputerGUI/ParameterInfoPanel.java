/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.Parameter;
import dosingcomputer.Preferences;
import dosingcomputer.Tank;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class ParameterInfoPanel extends JPanel {
    
    private Parameter param;
    private Tank tank;
    private String unitStr;
    
    private JLabel consumptionLabel;
    private JLabel lastTestedLabel;
    private JLabel paramLabel;
    private JLabel predictedLabel;
    private JLabel targetLabel;
    

    /**
     * Creates new form ParamPanel
     */
    public ParameterInfoPanel() {
        param = null;
        tank = null;
        initComponents();
        beLabels();
    }
    
    public ParameterInfoPanel(Parameter p) {
        param = p;
        tank = DosingComputer.guiFrame.getCurrentTank();
        if (p == Parameter.ALKALINITY){
            unitStr = Preferences.getAlkUnit();
        }
        else if (p == Parameter.SALINITY){
            unitStr = "S.G.";
        }
        else {
            unitStr = "ppm";
        }
        initComponents();
        setupPanel();

        tank.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String propName = pce.getPropertyName();
                String[] pnArr = propName.split(":");
                Parameter p = null;
                if (pnArr.length >= 2) {
                    p = Parameter.parseParam(pnArr[1]);
                }


                switch (pnArr[0]) {
                    case "targetUpdate":
                        if (p == param) {
                            targetLabel.setText(convertAndFormat(tank.getTarget(param)));
                        }
                        break;
                    case "consumptionUpdate":
                        if (p == param) {
                            consumptionLabel.setText(convertAndFormat(tank.getDailyConsumption(param)));
                        }
                        break;
                    case "waterTest":
                        if (p == param) {
                            lastTestedLabel.setText(convertAndFormat(tank.getLastTestedValue(param)));
                        }
                        break;
                }
                predictedLabel.setText(convertAndFormat(tank.calculatePredictedValue(param)));
            }
        });

    }

    private void setupPanel() {
        paramLabel.setText(param.getName());
        targetLabel.setText(convertAndFormat(tank.getTarget(param)));
        lastTestedLabel.setText(convertAndFormat(tank.getLastTestedValue(param)));
        consumptionLabel.setText(convertAndFormat(tank.getDailyConsumption(param)));
        predictedLabel.setText(convertAndFormat(tank.calculatePredictedValue(param)));
    }
    
    private String convertAndFormat(Double innum) {
        Double outnum = param.convertToUnit(unitStr, innum);
        String formatStr = "%.0f %s";
        if (unitStr.equals("dKH") || unitStr.equals("mM") || unitStr.equals("mEq/L")){
            formatStr = "%.2f %s";
        }
        else if (unitStr.equalsIgnoreCase("S.G.")){
        	formatStr = "%.4f %s";
        }
        return String.format(formatStr, outnum, unitStr);
    }

    public final void beLabels() {
//        paramLabel.setText("Parameter:");
//        targetLabel.setText("Target:");
//        lastTestedLabel.setText("Last:");
//        consumptionLabel.setText("Consumption:");
//        predictedLabel.setText("Predicted:");
        //this.revalidate();
    }
    
    private void initComponents() {
        paramLabel = new JLabel("Parameter:");
        targetLabel = new JLabel("Target:");
        lastTestedLabel = new JLabel("Last Test:");
        consumptionLabel = new JLabel("Consumption:  ");
        predictedLabel = new JLabel("Predicted:");
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.add(paramLabel);
        this.add(targetLabel);
        this.add(lastTestedLabel);
        this.add(consumptionLabel);
        this.add(predictedLabel);
        
        
        
    }
    
    
}
