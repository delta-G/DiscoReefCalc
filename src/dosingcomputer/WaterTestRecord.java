/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dosingcomputer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dave
 */
public class WaterTestRecord implements TankRecord{
    
    private Date date;
    private Parameter parameter;
    private Double result;
    
    
    public WaterTestRecord (Date pdate, Parameter pparameter, Double presult){
        this.date = pdate;
        this.parameter = pparameter;
        this.result = presult;
    }

    public Date getDate() {
        return date;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Double getResult() {
        return result;
    }

    @Override
    public String toString() {
            long mTime = date.getTime();
            
            String evtStr = String.valueOf(mTime);
            evtStr += "," + this.getType();
            evtStr += "," + parameter.getName();
            evtStr += "," + result;
            
            return evtStr;         
    }

    @Override
    public String getType() {
        return "Water Test";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
    
    
}
