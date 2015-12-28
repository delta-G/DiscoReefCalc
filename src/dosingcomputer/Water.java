/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dosingcomputer;

import java.util.HashMap;

/**
 *
 * @author Dave
 */
public class Water {

    private final HashMap<Parameter, Double> currentParamValues;
    //private Integer volume;  

    public Water() {
        //volume = v;
        currentParamValues = new HashMap<>();
        for (Parameter p : Parameter.values()) {
            currentParamValues.put(p, 0.0);
        }
    }
    
    public Water(HashMap<Parameter, Double> aMap){
        currentParamValues = new HashMap<>();
        for(Parameter p : aMap.keySet()){
            this.setValue(p, aMap.get(p));
        }
    }

    public Water(String aStr) {
        currentParamValues = new HashMap<>();
        this.setValues(Parameter.parseParameters(aStr));
    }

    public final void setValue(Parameter parameter, Double value) {
        currentParamValues.put(parameter, value);
    }

    public final void setValues(HashMap<Parameter, Double> aMap) {
        for (Parameter p : aMap.keySet()) {
            this.setValue(p, aMap.get(p));
        }
    }

    public Double getValue(Parameter parameter) {
        return currentParamValues.get(parameter);
    }
    
    public HashMap<Parameter, Double> getValues(){
        return new HashMap<>(currentParamValues);
    }

    public void doWaterMix(Double aVolThis, Water aWater, Double aVolOther) {
        this.setValues(calculateWaterMix(this, aVolThis, aWater, aVolOther));
    }

    public static HashMap<Parameter, Double> calculateWaterMix(Water thisWater, Double thisWaterVolume, Water otherWater, Double otherWaterVolume) {
        HashMap<Parameter, Double> retval = new HashMap<>();
        for (Parameter p : Parameter.values()) {
            Double newCon = (((thisWater.getValue(p) * thisWaterVolume) + (otherWater.getValue(p) * otherWaterVolume)) / (thisWaterVolume + otherWaterVolume));
            retval.put(p, newCon);
        }
        return retval;
    }

    @Override
    public String toString() {
        return Parameter.stringifyParameters(currentParamValues);
    }

}
