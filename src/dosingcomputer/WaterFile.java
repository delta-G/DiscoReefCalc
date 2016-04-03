/**     Copyright (C) 2016  David Caldwell  disco47dave at gmail dot com

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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import DosingComputerGUI.ErrorDialog;

public class WaterFile {

	private static final File waterFile = new File(
			Preferences.getHomeDirectory() + "/water_file.wtf");
	private static HashMap<String, Water> waterMap;
	static {
		waterMap = new HashMap<>();
		if (!waterFile.isFile()) {
			try {
				waterFile.createNewFile();
			} catch (IOException ex) {
				Logger.getLogger(TankRecordFile.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		readWaters();
	}

	public WaterFile() {

	}

	public static void saveWaters() {

		try (BufferedWriter outWriter = new BufferedWriter(new FileWriter(
				waterFile, false))) { // no append - overwrite

			for (String s : getWaterNames()) {
				outWriter.write(s);
				outWriter.write(",");
				outWriter.write(waterMap.get(s).toString());
				outWriter.write("\n");

			}

		} catch (IOException ex) {
			ErrorDialog.showExceptionDialog(ex);
		}

	}

	private static void readWaters() {
		try (BufferedReader inReader = new BufferedReader(new FileReader(
				waterFile))) {
			String instr = inReader.readLine();
			while (instr != null) {
				String splitstr[] = instr.split(",", 2);
				waterMap.put(splitstr[0], new Water(splitstr[1]));
				instr = inReader.readLine();
			}
		} catch (IOException ex) {
			ErrorDialog.showExceptionDialog(ex);
		}
	}

	public static String[] getWaterNamesArray() {
		String[] dummy = new String[1];
		return waterMap.keySet().toArray(dummy);
	}
	
	public static ArrayList<String> getWaterNames() {
		return new ArrayList<String>(waterMap.keySet());
	}

	public static Water getWater(String aName) {
		Water retval = new Water();
		if (waterMap.containsKey(aName)) {
			retval = waterMap.get(aName);
		}
		return retval;
	}

	public static void putWater(String aName, Water aWater) {
		waterMap.put(aName, aWater);
	}

}
