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
public class ManualDoseRecord implements TankRecord {

    private Date date;
    private DosingProduct product;
    private Double amount;
    
    public ManualDoseRecord (Date pdate, DosingProduct pprod, Double pamount) {
        this.date = pdate;
        this.product = pprod;
        this.amount = pamount;
    }

    public Date getDate() {
        return date;
    }

    public DosingProduct getProduct() {
        return product;
    }

    public Double getAmount() {
        return amount;
    }
    
    

    public String toString() {

        long mTime = date.getTime();

        String evtStr = String.valueOf(mTime);
        evtStr += "," + this.getType();
        evtStr += "," + product.getName();
        evtStr += "," + amount;

        return evtStr;

    }

    @Override
    public String getType() {
        return "Manual Dose";
    }

    @Override
    public Long getTime() {
        return date.getTime();
    }
}
