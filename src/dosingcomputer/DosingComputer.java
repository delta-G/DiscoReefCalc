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

//import DosingComputerGUI.GUIFrameOld;
import DosingComputerGUI.GUIFrame;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Dave
 */

/*
 * In order for things to work properly, units are important.  
 * Until we build a unit class (Enum) **That's done now** the user has to know this
 * All Volumes have to be in milliliters
 * All Concentrations have to be in Molar (for alkalinity use molarity of carbonate equivalents)
 * but for some reason right now they're all in ppm. 
 * All times are UNIX time in milliseconds and use the java.util.Date library.
 * 
 */
public class DosingComputer {
    
    public static final long dayInMillis = 86400000;
    public static Long timeWarp = 0L * dayInMillis;  // allows me to adjust the time by days for testing set the number of days in the past you want the program to think it is. 
    
    //  This array will be replaced by a class and a file full of records
    public static ArrayList<DosingProduct> dosingProductsList = new ArrayList<>(Arrays.asList(
            new DosingProduct("Alkalinity Part", Parameter.ALKALINITY, 925.0),
            new DosingProduct("Calcium Part", Parameter.CALCIUM, 925.0),
            new DosingProduct("Magnesium Part", Parameter.MAGNESIUM, 1927.0)));

    public static DosingProduct parseDosingProduct(String s) {
        for (DosingProduct dp : dosingProductsList) {
            if (dp.getName().equals(s)) {
                return dp;
            }
        }
        return null;
    }
   
    public static HashMap<String, Tank> tankMap = new HashMap<>();
    public static GUIFrame guiFrame;

    public static void main(String[] args) {
        Preferences.loadPreferences();
        loadAllTanks();
        guiFrame = new GUIFrame();
        guiFrame.setMeUp();
        guiFrame.setLocation(300, 200);
        //guiFrame.setLocationRelativeTo(null);
        guiFrame.setVisible(true);
    }

    private static void loadAllTanks() {

        for (File file : Preferences.getTankDirectory().listFiles()) {
            if (file.isFile()) {
                if (file.getName().endsWith(".tsf")) {
                    addTank(TankFile.openTankFromFile(file));
                }
            }
        }

    }

    public static void addTank(Tank tank) {
        tankMap.put(tank.getName(), tank);
    }

    public static Tank getTank(String name) {
        return tankMap.get(name);
    }

    public static TreeSet<String> getTankNames() {
        return new TreeSet(tankMap.keySet());
    }

    public static Long getTheTime() {
        return System.currentTimeMillis() - timeWarp;
    }

    public static void setTimeWarp(Long tl) {
        timeWarp = tl;
    }

    public static long getTimeWarp() {
        return timeWarp;
    }
    
    
}
