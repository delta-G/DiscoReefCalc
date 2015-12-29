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

import java.util.Date;

/**
 *
 * @author David
 */
public class WaterChangeRecord implements TankRecord {
    
    private Date date;
    private Water waterDelta;
    private Double volumeChanged;
    
    public WaterChangeRecord (Date aDate, Double aVol, Water aWater){
        this.date = aDate;
        this.waterDelta = aWater;
        this.volumeChanged = aVol;        
    }

    public Date getDate() {
        return date;
    }

    public Water getWaterDelta() {
        return waterDelta;
    }

    public Double getVolumeChanged() {
        return volumeChanged;
    }
    
    public String toString() {
        long mTime = date.getTime();
        
        String evtStr = String.valueOf(mTime);
        evtStr += "," + this.getType();
        evtStr += "," + this.getVolumeChanged();
        
        for (Parameter p : Parameter.values()){
            evtStr += "," + p.getName() + "@" + waterDelta.getValue(p);
        }
        
        return evtStr;
    }
    
    
    @Override
    public String getType() {
        return "Water Change";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
    
    
}
