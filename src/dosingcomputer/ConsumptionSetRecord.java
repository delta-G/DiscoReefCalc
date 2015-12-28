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
public class ConsumptionSetRecord implements TankRecord {

    private Date date;
    private Parameter parameter;
    private Double oldVal;
    private Double newVal;

    public ConsumptionSetRecord(Date dat, Parameter par, Double old, Double newv) {
        this.date = dat;
        this.parameter = par;
        this.oldVal = old;
        this.newVal = newv;
    }

    public Date getDate() {
        return date;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Double getOldVal() {
        return oldVal;
    }

    public Double getNewVal() {
        return newVal;
    }
    
    

    @Override
    public String toString() {
        long mTime = date.getTime();

        String evtStr = String.valueOf(mTime);
        evtStr += "," + this.getType();
        evtStr += "," + parameter.getName();
        evtStr += "," + oldVal;
        evtStr += "," + newVal;

        return evtStr;
    }

    @Override
    public String getType() {
        return "Consumption Set";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
}
