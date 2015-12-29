/**     Copyright (C) 2015  David Caldwell  disco47dave@gmail.com

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
public enum Parameter {

	CALCIUM("Calcium"), ALKALINITY("Alkalinity"), MAGNESIUM("Magnesium"), SALINITY(
			"Salinity");

	private String name;
	private HashMap<String, Double> units; // string is unit name, double is
											// conversion factor to multiply by
											// to get molar units.

	Parameter(String n) {
		this.name = n;
		units = new HashMap<>();

		switch (this.name) {
		case "Calcium":
			units.put("ppm", 0.025);
			units.put("mM", 1.0);
			break;
		case "Magnesium":
			units.put("ppm", 0.041);
			units.put("mM", 1.0);
			break;
		case "Alkalinity": // For alkalinity mM is defined as milliMolar
							// equivalent of CaCO3 as with ppm.
			units.put("dKH", 0.17848);
			units.put("mEq/L", 0.5);
			units.put("ppm", 0.01);
			units.put("mM", 1.0);
			break;
		case "Salinity":
			units.put("ppt", 1000.0);
			units.put("S.G.", 1333000.0);
			units.put("ppm", 1.0);
			break;

		}
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String[] getUnits() {
		String[] retval = new String[1]; // Needed to set the type of the array
											// in the return.
		ArrayList<String> retlis = new ArrayList<>();
		for (String s : units.keySet()) {
			retlis.add(s);
		}
		return retlis.toArray(retval);

	}

	public static Parameter parseParam(String paramName) {
		switch (paramName) {
		case "Calcium":
			return CALCIUM;
		case "Alkalinity":
			return ALKALINITY;
		case "Magnesium":
			return MAGNESIUM;
		case "Salinity":
			return SALINITY;
		}
		return null;
	}

	public static HashMap<Parameter, Double> parseParameters(String aStr) {
		HashMap<Parameter, Double> retval = new HashMap<>();

		for (String s : aStr.split("[,]")) {
			if (s.indexOf('@') > 0) {
				String[] inPar = s.split("[@]");
				Parameter p = parseParam(inPar[0]);
				retval.put(p, Double.parseDouble(inPar[1]));
			}
		}
		return retval;
	}

	public static String stringifyParameters(HashMap<Parameter, Double> aMap) {
		String retval = "";
		boolean first = true;
		for (Parameter p : aMap.keySet()) {
			if (first) {
				first = false;
			} else {
				retval += ",";
			}
			retval += p.getName();
			retval += "@";
			retval += aMap.get(p);
		}
		return retval;
	}

	// converts from the specified unit to mM
	// This is going from the user to the program
	public Double convertFromUnit(String unitname, Double val) {
		Double retval = 0.0;
		if (units.keySet().contains(unitname)) {
			if (unitname.equals("S.G.")) {
				retval = (val - 1.0) * units.get(unitname);
			} else {
				retval = val * (units.get(unitname));
			}

		}
		return retval;
	}

	// converts from mM to the specified unit
	// This is going program to user
	public Double convertToUnit(String unitname, Double val) {
		Double retval = 0.0;
		if (units.keySet().contains(unitname)) {
			retval = val / (units.get(unitname));
			if (unitname.equals("S.G.")) {
				retval += 1.0;
			}
		}
		return retval;
	}
}
