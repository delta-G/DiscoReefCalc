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

import DosingComputerGUI.ErrorDialog;
import DosingComputerGUI.StartupWizard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dave
 */
public class Preferences {

    public static final HashMap<String, String> prefMap = new HashMap<>();

    static {
        prefMap.put("Home Directory", "./");
        prefMap.put("Tanks Directory", "./");
        prefMap.put("Alkalinity Units", "dKH");
        prefMap.put("Salinity Units", "S.G.");
        prefMap.put("Months to Keep History", "6");
        prefMap.put("Tank Volume Units", VolumeUnits.GALLON.toString());
        prefMap.put("Dose Volume Units", VolumeUnits.MILLILITER.toString());
    }

    public static String getPreference(String aPref) {
        String retval;
        if (prefMap.keySet().contains(aPref)) {
            retval = prefMap.get(aPref);
        } else {
            retval = "ERROR";
            ErrorDialog.showErrorDialog("Found Bad Preference Key");
        }
        return retval;
    }
    
    // returns the old value in case you want it
    public static String setPreference(String aPref , String aValue){
        String retval;
        if (prefMap.keySet().contains(aPref)) {
            retval = prefMap.get(aPref);
            prefMap.put(aPref, aValue);
        } else {
            retval = "ERROR";
            ErrorDialog.showErrorDialog("Found Bad Preference Key");
        }
        return retval;
    }

    public static File getHomeDirectory() {
        return new File(prefMap.get("Home Directory"));
    }

    public static void setHomeDirectory(File file) {
        prefMap.put("Home Directory", file.getAbsolutePath());
    }

    public static File getTankDirectory() {
        return new File(prefMap.get("Tanks Directory"));
    }

    public static void setTankDirectory(File file) {
        prefMap.put("Tanks Directory", file.getAbsolutePath());
    }

    public static String getAlkUnit() {
        return prefMap.get("Alkalinity Units");
    }
    
    

    public static void setAlkUnit(String ustr) {

        if (Arrays.asList(Parameter.ALKALINITY.getUnits()).contains(ustr)) {
            prefMap.put("Alkalinity Units", ustr);
        } else {
            ErrorDialog.showErrorDialog("Bad Alkalinity Unit" + ustr);
        }
    }
    
    public static String getSalinityUnit() {
        return prefMap.get("Salinity Units");
    }
    
    public static void setSalinityUnit(String ustr) {

        if (Arrays.asList(Parameter.SALINITY.getUnits()).contains(ustr)) {
            prefMap.put("Salinity Units", ustr);
        } else {
            ErrorDialog.showErrorDialog("Bad Salinity Unit" + ustr);
        }
    }

    public static Integer getMonthsForHistory() {
        return Integer.parseInt(prefMap.get("Months to Keep History"));
    }

    public static void setMonthsForHistory(Integer mon) {
        prefMap.put("Tanks Directory", mon.toString());
    }

    public static VolumeUnits getTankVolumeUnit() {
        return VolumeUnits.parseUnit(prefMap.get("Tank Volume Units"));
    }

    public static void setTankVolumeUnit(VolumeUnits vu) {
        prefMap.put("Tank Volume Units", vu.toString());
    }

    public static VolumeUnits getDoseVolumeUnit() {
        return VolumeUnits.parseUnit(prefMap.get("Dose Volume Units"));
    }

    public static void setDoseVolumeUnit(VolumeUnits vu) {
        prefMap.put("Dose Volume Units", vu.toString());
    }

    public static void savePreferences() {

        try (BufferedWriter outWriter = new BufferedWriter(new FileWriter("./preferences.txt"))) {

            for (String s : prefMap.keySet()) {
                outWriter.write("<" + s + ">" + prefMap.get(s) + "\n");
            }

        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadPreferences() {
    	
    	File saveFile = new File("./preferences.txt");
    	if(!saveFile.exists()){
    		try {
				saveFile.createNewFile();
				StartupWizard.runWizard();
			} catch (IOException e) {
				ErrorDialog.showExceptionDialog(e);
			}
    	}

        try (BufferedReader inReader = new BufferedReader(new FileReader(saveFile))) {

            String line = inReader.readLine();

            while (line != null) {
                String key;
                String value;

                if (line.startsWith("<")) {
                    int keyindex1 = line.indexOf("<") + 1;
                    int keyindex2 = line.indexOf(">");

                    key = line.substring(keyindex1, keyindex2);
                    value = line.substring(keyindex2 + 1);

                    if (prefMap.keySet().contains(key)) {
                        prefMap.put(key, value);
                    } else {
                        ErrorDialog.showErrorDialog("Found Bad Preference Key");
                    }

                }
                line = inReader.readLine();
            }
        } catch (FileNotFoundException ex){
            generateDefaultPrefFile();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void generateDefaultPrefFile(){
        
        try {
            File file = new File("./preferences.txt");
            file.createNewFile();
            savePreferences();
            
        } catch (IOException ex) {
            System.out.println("Error generating default pref file!");
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

}
