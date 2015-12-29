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
