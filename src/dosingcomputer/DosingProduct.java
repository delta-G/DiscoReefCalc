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
    
    public String stringifyProduct(){
    	String retval = "";
    	retval += this.name;
    	for(Parameter p : targetParameters){
    		retval += ":>" + p.getName() + ":=" + concentrations.get(p);
    	}
    	return retval;
    }
    
    public static DosingProduct productFactory(String instr){
    	String parts[] = instr.split(":>");
    	int count = 0;
    	String inName = "ERROR";
    	ArrayList<Parameter> inPars = new ArrayList<>();
    	ArrayList<Double> inCons = new ArrayList<>();
    	
    	
    	for(String s : parts){
    		if(count == 0){
    			inName = s;
    			count++;
    		}
    		else {
    			String pst[] = s.split(":=");
    			inPars.add(Parameter.parseParam(pst[0]));
    			inCons.add(Double.parseDouble(pst[1]));
    			count++;
    		}
    	}
    	return new DosingProduct(inName, inPars, inCons);
    }
    
}
