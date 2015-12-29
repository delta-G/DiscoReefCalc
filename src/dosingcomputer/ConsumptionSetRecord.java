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
