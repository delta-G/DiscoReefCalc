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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dave
 */
public class TankFile {

    public static void saveTankToFile(Tank tank) {
        File file = new File(Preferences.getTankDirectory().getPath() + "/" + tank.getName() + ".tsf");
        

        try (BufferedWriter outWriter = new BufferedWriter(new FileWriter(file, false))) { //Overwrites current file

            outWriter.write("## ## ## Tank Begin:\n");
            outWriter.write(new Long(DosingComputer.getTheTime()).toString() + '\n');
            outWriter.write(tank.getName() + '\n');
            outWriter.write(tank.getVolume().toString() + '\n');
            outWriter.write(tank.getTankRecordFile().getFile().getName() + '\n');
            
            outWriter.write("## ## ## Dosing Products:\n");
            for (DosingProduct product : tank.getDosingProducts()) {
                outWriter.write(product.getName() + "," + tank.getDailyDose(product).toString() + '\n');
            }
            
            outWriter.write("## ## ## Parameters:\n");
            for (Parameter p : tank.getParameters()) {

                outWriter.write(p.getName() + ',');
                outWriter.write(tank.getLastTestedValue(p).toString() + ',');
                outWriter.write(tank.getTarget(p).toString() + ',');
                outWriter.write(tank.getDailyConsumption(p).toString() + ',');
                outWriter.write('\n');
            }
            
            
            outWriter.write ("## ## ## Tank End:\n");

        } catch (IOException ex) {
            Logger.getLogger(TankFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static Tank openTankFromFile(File file) {
        
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            
            String line = in.readLine();
            while (! line.equals("## ## ## Tank Begin:")){
                line = in.readLine();
            }
            Long lastSaveTime = Long.parseLong(in.readLine());
            String name = in.readLine();
            Double volume = Double.parseDouble(in.readLine());
            
            
            Tank tank = new Tank(name, volume);
            String trfStr = Preferences.getHomeDirectory() + "/" + in.readLine();
            tank.setTankRecordFile(new TankRecordFile(new File(trfStr)));
            
            line = in.readLine();
            if(line.equals("## ## ## Dosing Products:")){
                line = in.readLine();
                while(! line.equals("## ## ## Parameters:")){
                    String[] inArr = line.split(",");
                    DosingProduct dp = DosingComputer.parseDosingProduct(inArr[0]);
                    if(dp != null){
                        tank.addProduct(dp, Double.parseDouble(inArr[1]));
                    }
                    line = in.readLine();
                }               
            }
            if(line.equals("## ## ## Parameters:")){
                line = in.readLine();
                while(! line.equals("## ## ## Tank End:")){
                    String[] inArr = line.split(",");
                    Parameter par = Parameter.parseParam(inArr[0]);
                    tank.getTankWater().setValue(par, Double.parseDouble(inArr[1]));
                    
                    tank.setTarget(par, Double.parseDouble(inArr[2]));
                   //tank.setupStuffFromFile(par, Double.parseDouble(inArr[3]), Double.parseDouble(inArr[1]));
                    line = in.readLine();
                }
            }
            
            return tank;
            
        } catch (IOException ex) {
            Logger.getLogger(TankFile.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error in Open Tank From File");
            return null;
        }        
    }
    
    
}
