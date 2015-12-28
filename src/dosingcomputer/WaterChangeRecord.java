/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
