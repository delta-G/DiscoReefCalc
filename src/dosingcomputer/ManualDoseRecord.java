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
