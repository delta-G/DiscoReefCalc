/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dosingcomputer;

import java.util.Date;

/**
 *
 * @author Dave
 */
public class PredictedValueRecord implements TankRecord{
    
    private Date date;
    private Parameter parameter;
    private Double value;
    
    
    public PredictedValueRecord(Date pdate, Parameter pparam, Double pvalue) {
        this.date = pdate;
        this.parameter = pparam;
        this.value = pvalue;
        
    }

    public Date getDate() {
        return date;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        long mTime = date.getTime();
        String evtStr = String.valueOf(mTime);
        evtStr += "," + this.getType();
        evtStr += "," + this.parameter.getName();
        evtStr += "," + this.value;
        
        return evtStr;
    }

    @Override
    public String getType() {
        return "Predicted Value Set";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
    
    
    
    
    
}
