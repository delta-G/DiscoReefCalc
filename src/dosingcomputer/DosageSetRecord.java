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
public class DosageSetRecord implements TankRecord {
    
    private Date date;
    private String productName;
    private Double oldVal;
    private Double newVal;
    
    public DosageSetRecord(Date dat, String nam, Double old, Double newv) {
        this.date = dat;
        this.productName = nam;
        this.oldVal = old;
        this.newVal = newv;
    }

    public Date getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
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
        evtStr += "," + productName;
        evtStr += "," + oldVal;
        evtStr += "," + newVal;

        return evtStr;
    }

    @Override
    public String getType() {
        return "Dosage Set";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
    
    
    
}
