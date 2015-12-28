/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dosingcomputer;

/**
 *
 * @author Dave
 */

//  milliliter will be the standard unit for the program
public enum VolumeUnits {
    
    MILLILITER("mL", 1.0),
    LITER("L", 1000.0),
    GALLON("gal", 3785.0);
    
    private final String name;
    private final Double conFac;
    
    VolumeUnits(String n, Double d){
        this.name = n;
        this.conFac = d;
    }
    
    public String getName(){
        return name;
    }
    
    public Double getConversionFactor(){
        return conFac;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static VolumeUnits parseUnit(String uname){
        switch (uname){
            case "mL":
                return MILLILITER;
            case "L":
                return LITER;
            case "gal":
                return GALLON;
        }
        return null;
    }
    
    // converts from this unit to mL
    // this is going user to program
    public Double convertFromUnit(Double val){
        return val * getConversionFactor();        
    }
    
    // converts mL to this unit
    // this is going program to user
    public Double convertToUnit(Double val) {
        return val / getConversionFactor();
    }
    
    public static Double convertFromUnit(String uname, Double val){
        VolumeUnits fval = parseUnit(uname);
        return val * fval.getConversionFactor();
    }
    
    public static Double convertToUnit(String uname, Double val){
        VolumeUnits fval = parseUnit(uname);
        return val / fval.getConversionFactor();
    }    
}
