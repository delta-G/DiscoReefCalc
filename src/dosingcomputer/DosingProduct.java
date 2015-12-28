/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dosingcomputer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Dave
 */
public class DosingProduct {
    
    private String name;
    private ArrayList<Parameter> targetParameters;
    private HashMap<Parameter, Double> concentrations;
    
    DosingProduct(String n, ArrayList<Parameter> p, ArrayList<Double> c) {
        this.name = n;
        this.targetParameters = new ArrayList<>(p);
        this.concentrations = new HashMap<>();
        for (int i = 0; i < targetParameters.size(); i++) {
            this.concentrations.put(targetParameters.get(i), c.get(i));
        }
    }
    
    DosingProduct(String n, Parameter p, Double c) {
        this.name = n;
        this.targetParameters = new ArrayList<>();
        this.concentrations = new HashMap<>();
        targetParameters.add(p);
        concentrations.put(p, c);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public ArrayList<Parameter> getTargetParameters() {
        return targetParameters;
    }
    
    public Double getConcentration(Parameter param) {
        return concentrations.get(param);
    }
    
    public Double calculateDose(Parameter param, Double target, Double current, Double volume) {
        return calculateDosage(param, target - current, volume);
    }
    
    // This is the one that does the work
    public Double calculateDosage(Parameter par, Double change, Double volume) {
        if(change <= 0.0){
            return 0.0;
        }
        if (this.targetParameters.contains(par)) {
            return (change * volume) / this.concentrations.get(par);
        } else {
            return 0.0;
        }
    }
    
    public Double calculateDosage(Parameter par, Double target, Tank tank) {
        Double change = target - tank.calculatePredictedValue(par);
        return calculateDosage(par, change, tank.getVolume());
    }
    
    public Double calculateDailyDose(Tank tank) {
        return calculateDosage(targetParameters.get(0), tank.getDailyConsumption(targetParameters.get(0)), tank.getVolume());
    }
    
    public Double calculateDailyDose(Tank tank, Parameter param) {        
        return calculateDosage(param, tank.getDailyConsumption(param), tank.getVolume());        
    }

//    public void addDose(Water water, Integer Volume, Double dosage){        
//        Double amountAdded = (dosage * this.concentration) / Volume;
//        Double currentLevel = water.getValue(this.targetParameter);
//        water.setValue(targetParameter, amountAdded + currentLevel);                
//    }
    public Double calculateChange(Parameter param, Double tankVol, Double doseVol) {
        Double amountAdded = 0.0;
        if (this.targetParameters.contains(param)) {
            amountAdded = (doseVol * this.concentrations.get(param)) / tankVol;
        }
        return amountAdded;
    }
    
    public Double calculateChange(Parameter param, Tank tank, Double doseVol) {
        return calculateChange(param, tank.getVolume(), doseVol);
    }
}
