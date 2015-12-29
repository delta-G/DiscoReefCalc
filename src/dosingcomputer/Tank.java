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

import DosingComputerGUI.ErrorDialog;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Dave
 */
public class Tank {

    //private final Double ALKTOCALFACTOR = 1.0;  // This is right if they're both in mM
    private String name = "";
    private Double volume;
    private TreeSet<Parameter> parameterList;
    private HashMap<Parameter, Double> paramTargets;
    private HashMap<DosingProduct, Double> dosages;
    private TankRecordFile tankRecordFile;
    private Water tankWater;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void releaseAllPropertyChangeListeners() {
        for (PropertyChangeListener listener : propertyChangeSupport.getPropertyChangeListeners()) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    public Tank(String nam, Double vol) {
        this.name = nam;
        this.volume = vol;
        paramTargets = new HashMap<>();
        dosages = new HashMap<>();
        tankRecordFile = new TankRecordFile(new File(Preferences.getTankDirectory().getPath() + this.name + ".trf"));
        tankWater = new Water();

        for (Parameter p : Parameter.values()) {
            paramTargets.put(p, 0.0);
            //dailyConsumption.put(p, 0.0);
            //lastTested.put(p, 0.0);
        }
        parameterList = new TreeSet(paramTargets.keySet());
    }

    public void setTarget(Parameter parameter, Double target) {
        Double oldTarg = paramTargets.get(parameter);
        paramTargets.put(parameter, target);
        propertyChangeSupport.firePropertyChange("targetUpdate:" + parameter.getName(), oldTarg, target);

    }

    public Double getTarget(Parameter parameter) {
        return paramTargets.get(parameter);
    }
    
    public void doWaterChange(Double aVol, Water aWater){
        doWaterChange(aVol, aWater, DosingComputer.getTheTime());
    }

    public void doWaterChange(Double aVol, Water aWater, Long aTime) {
        this.predictValues(aTime);
        HashMap<Parameter, Double> deltaMap = this.tankWater.getValues();
        this.tankWater.doWaterMix(this.volume - aVol, aWater, aVol);
        for (Parameter p : deltaMap.keySet()) {
            Double delta = this.tankWater.getValue(p) - deltaMap.get(p);
            deltaMap.put(p, delta);
        }
        this.tankRecordFile.addRecord(new WaterChangeRecord(
                new Date(aTime),
                aVol,
                new Water(deltaMap)));
        this.predictValues();  // Just in case the added change was long time ago.
    }

    public void makeDailyConsumptionRecord(Parameter parameter, Double consumption) {

        //Double oldCons = dailyConsumption.get(parameter);
        Double oldCons = this.getDailyConsumption(parameter);
        //dailyConsumption.put(parameter, consumption);

        // File record and fire property change
        ConsumptionSetRecord csr = new ConsumptionSetRecord(
                new Date(DosingComputer.getTheTime()),
                parameter,
                oldCons,
                consumption);
        this.tankRecordFile.addRecord(csr);

        propertyChangeSupport.firePropertyChange("consumptionUpdate:" + parameter.getName(), oldCons, consumption);

    }

    public Double getDailyConsumption(Parameter parameter) {
        //return dailyConsumption.get(parameter);
        return this.calculateConsumption(parameter);
    }

    public void setDailyDose(HashMap<DosingProduct, Double> dmap) {
        for (DosingProduct dp : dmap.keySet()) {
            setDailyDose(dp, dmap.get(dp));
        }
    }

    public void setDailyDose(DosingProduct product, Double dose) {
        Double old = dosages.get(product);
        dosages.put(product, dose);

        // File record and fire property change
        DosageSetRecord rec = new DosageSetRecord(
                new Date(DosingComputer.getTheTime()),
                product.getName(),
                old,
                dosages.get(product));
        this.tankRecordFile.addRecord(rec);

        propertyChangeSupport.firePropertyChange("dosageUpdate:" + product.getName(), old, dosages.get(product));

    }

    public Double getDailyDose(DosingProduct product) {
        return dosages.get(product);
    }

    public Set<DosingProduct> getDosingProducts() {
        return dosages.keySet();
    }

    public TreeSet<Parameter> getParameters() {
        return parameterList;
    }

    public void addProduct(DosingProduct product, Double dose) {
        //dosingProducts.add(product);
        dosages.put(product, dose);

        propertyChangeSupport.firePropertyChange("addProduct", null, product);
    }

    public void removeProduct(DosingProduct product) {
        //dosingProducts.remove(product);
        dosages.remove(product);

        propertyChangeSupport.firePropertyChange("removeProduct", null, product);
    }

    public TankRecordFile getTankRecordFile() {
        return tankRecordFile;
    }

    public void setTankRecordFile(TankRecordFile file) {
        tankRecordFile = file;
    }

    public Double getLastTestedValue(Parameter p) {
        //return lastTested.get(p);
        Double retval = 0.0;
        for (TankRecord trec : tankRecordFile) {
            if (trec.getType().equals("Water Test")) {
                WaterTestRecord wtr = (WaterTestRecord) trec;
                if (wtr.getParameter().equals(p)) {
                    retval = wtr.getResult();
                    break;
                }
            }
        }

        return retval;
    }

    public Water getTankWater() {
        return tankWater;
    }

    public String getName() {
        return name;
    }

    public Double getVolume() {
        return volume;
    }

    public void addTest(WaterTestRecord wtr) {
        Parameter p = wtr.getParameter();
        Double r = wtr.getResult();
        //Double oldVal = lastTested.get(p);
        Double oldVal = this.getLastTestedValue(p);
        tankWater.setValue(p, r);
        //lastTested.put(p, r);
        tankRecordFile.addRecord(wtr);
        propertyChangeSupport.firePropertyChange("waterTest:" + p.getName(), oldVal, r);
    }

    public void addManualDose(ManualDoseRecord mdr) {
        tankRecordFile.addRecord(mdr);
    }

    public void setConsumption() {
        for (Parameter p : paramTargets.keySet()) {
            //  set the newly calculated consumption rate
            //  calculate will return the old value if there is any problem
            makeDailyConsumptionRecord(p, calculateConsumption(p));
        }
    }

    public void setConsumptionAdvanced() {
        //  Maybe this would also be a calculate function and would return a hashmap.
        //  That way the consumption numbers could be set with the old one and the 
        //  dosing numbers could be setup with this fancy new one. 

        // Setup a hashmap to hold all the calculated consumptions
        HashMap<Parameter, Double> calcons = new HashMap<>();
        //populate the hashmap
        for (Parameter p : paramTargets.keySet()) {
            calcons.put(p, calculateConsumption(p));
        }
        // Check the new alkalinity consumption against the old one
        Double delAlk = Math.abs(this.getDailyConsumption(Parameter.ALKALINITY) - calcons.get(Parameter.ALKALINITY));

        // If delAlk is less than some small amount, ignore the difference or average it out or whatever
        if (delAlk < 5) {
        }

        // If delAlk is greater than some large amount, throw a dialog and suggest retesting or looking for a problem
        /*TODO:  Below Should check the delAlk number against delCal instead*/
        // Get a predicted calcium drop
        Double predCal = calcons.get(Parameter.ALKALINITY);
        // If the difference between what we predict from alkalinity and what
        // we measure is less than testing noise then use the predicted number.

        if (Math.abs(predCal - calcons.get(Parameter.CALCIUM)) < 5) {
            calcons.put(Parameter.CALCIUM, predCal);
        }
        // Check the new calcium consumption against the old one
        Double delCal = Math.abs(this.getDailyConsumption(Parameter.CALCIUM) - calcons.get(Parameter.CALCIUM));

        // If delCal is unusually large or small, take appropriate action.
        // Check the calcium consumption against the alkalinity.  Should we match dose?
        // Are any changes in calcium reflected in alkalinity and vice versa
        // Calculate an expected change in calcium given the alkainity number and 
        //  then write code to justify that number or not
        //  if the calculated number is lower than the resolution of the kit and the 
        //  tested change is 0 then maybe we should match dose.
        //  if he calculated number is 40ppm but the tested change is 0 then maybe we shouldn't.
        //  We could start with something as simple as if calcium isn't 40ppm or more off target
        //  call it testing noise and do nothing. 
        // if the magnesium dose is small recommend just watching and bolus dosing instead of constant.
    }

    public Double calculateConsumption(Parameter param) {
        //  get last two test points
        WaterTestRecord[] tests = new WaterTestRecord[2];
        int count = 0;

        for (TankRecord trec : tankRecordFile) {
            // If you see that consumption has been set since the last test bail out.
            if ((count == 0) && ("Consumption Set".equals(trec.getType())) && (param.equals(((ConsumptionSetRecord) trec).getParameter()))) {
                //ErrorDialog.showErrorDialog("Consumption has been set since the last test for " + param.getName());
                //return this.getDailyConsumption(param);
                return ((ConsumptionSetRecord) trec).getNewVal();
            }
            if ("Water Test".equals(trec.getType())) {

                WaterTestRecord wtr = (WaterTestRecord) trec;

                if (param == wtr.getParameter()) {
                    tests[count++] = wtr;
                    if (count == 2) {
                        // Found the two records we needed. 
                        break;
                    }
                }
            }
        }

        // If you didn't find two records, and didn't find a ConsumptionSetRecord (would have returned) then bail!
        if (count < 2) {
            //ErrorDialog.showErrorDialog("Must have at least two test records to set consumption for " + param.getName());
            return 0.0;
        }

        //  calculate the length of time in days
        // tests[0] is the later (newer) test and tests[1] is the earlier (older) one. 
        Long delT = tests[0].getTime() - tests[1].getTime();
        Double days = (double) (delT / DosingComputer.dayInMillis);
        /* 
         * delC is change in concentration
         * since tests[0] is the newer test and [1] is older
         * positive delC means the concentration went up over time
         * as the new result is higher than the old one. 
         */
        Double delC = tests[0].getResult() - tests[1].getResult();

        //  calculate how much has been dosed and subtract delC to get the total consumption
        //  subtract delC because it will be positive if the param went up meaning we didn't consume
        //  all we dosed and have to set consumption less than the amount dosed. 
        Double dailyConsumption = ((calculateDosingAddition(param, tests[1].getTime(), tests[0].getTime())) - delC) / days;

        //  set the newly calculated consumption rate
        //setDailyConsumption(param, dailyCons);
        return dailyConsumption;

    }

    /**
     * Calculates how much of a certain parameter has been dosed over a given
     * time. Includes water changes.
     *
     * @param param The parameter to calculate for
     * @param start Start time in UNIX time
     * @param end End time in UNIX time
     * @return A Double with the amount dosed.
     */
    public Double calculateDosingAddition(Parameter param, Long start, Long end) {

        ArrayList<DosingProduct> prods = new ArrayList<>();
        HashMap<DosingProduct, Double> doses = new HashMap<>();
        HashMap<DosingProduct, Long> lastChange = new HashMap<>();

        for (DosingProduct dp : this.getDosingProducts()) {
            if (dp.getTargetParameters().contains(param)) {
                prods.add(dp);
                doses.put(dp, dosages.get(dp));
                lastChange.put(dp, end);
            }
        }

        Double retval = 0.0;

        for (TankRecord tr : tankRecordFile) {
            // Searching through backwards
            if (tr.getTime() < start) {
                break;
            }
            if (tr.getTime() > end) {
                continue;
            }
            //  Working backwards through the records adding up the dosing.
            if ("Dosage Set".equals(tr.getType())) {

                DosageSetRecord rec = (DosageSetRecord) tr;
                for (DosingProduct dp : prods) {
                    if (dp.getName().equals(rec.getProductName())) {
                        Long dtime = rec.getTime();
                        Double ddays = ((double) lastChange.get(dp) - dtime) / DosingComputer.dayInMillis;
                        lastChange.put(dp, dtime);
                        retval += dp.calculateChange(param, this, doses.get(dp)) * ddays;
                        doses.put(dp, rec.getOldVal());   // We're going backwards through the records, use the old value 
                        break;  // found the product, no need to keep searching through dosing products
                    }
                }
            }
            // add in any manual doses
            if ("Manual Dose".equals(tr.getType())) {
                ManualDoseRecord rec = (ManualDoseRecord) tr;
                retval += rec.getProduct().calculateChange(param, this, rec.getAmount());
            }
            // add in any changes due to water changes
            if ("Water Change".equals(tr.getType())) {
                WaterChangeRecord wcr = (WaterChangeRecord) tr;
                retval += wcr.getWaterDelta().getValue(param);
            }

        }

        //  Roll through and clean up from the last change to the start time. 
        for (DosingProduct dp : prods) {
            Double days = ((double) lastChange.get(dp) - start) / DosingComputer.dayInMillis;
            retval += dp.calculateChange(param, this, doses.get(dp)) * days;
        }

        return retval;
    }

    /**
     * Calculates recommended doses for all products assuming the consumption
     * has been set
     *
     * @return a HashMap<DosingProduct, Double> with recommended doses for each
     * product.
     */
    public HashMap<DosingProduct, Double> computeRequiredDailyDoses() {
        HashMap<DosingProduct, Double> retval = new HashMap<>();

        for (DosingProduct dp : this.getDosingProducts()) {
            Parameter tp = dp.getTargetParameters().get(0);
            retval.put(dp, dp.calculateDailyDose(this, tp));
        }

        return retval;
    }
    
    public void predictValues() {
        predictValues(DosingComputer.getTheTime());
    }

    public void predictValues(Long aTime) {
        for (Parameter p : Parameter.values()) {
            Double pred = this.calculatePredictedValue(p , aTime);
            this.tankWater.setValue(p, pred);
            this.tankRecordFile.addRecord(new PredictedValueRecord(
                    new Date(DosingComputer.getTheTime()),
                    p,
                    pred));
        }

    }
    
    public Double calculatePredictedValue(Parameter param) {
        return calculatePredictedValue(param, DosingComputer.getTheTime());
    }

    public Double calculatePredictedValue(Parameter param , Long aTime) {

        //Double lval = 0.0;
        Long ltime = 0L;

        // Find last predicted value update if there is one or last tested value whichever you find first
        for (TankRecord trec : tankRecordFile) {
            Boolean foundit = false;
            // No records from the future...
            if (trec.getTime() > aTime) {
                continue;
            }

            switch (trec.getType()) {
                case "Predicted Value Set":
                    PredictedValueRecord pvr = (PredictedValueRecord) trec;
                    if (pvr.getParameter().equals(param)) {
                        // lval = pvr.getValue();
                        ltime = pvr.getTime();
                        foundit = true;
                    } else {
                        continue;
                    }
                    break;
                case "Water Test":
                    WaterTestRecord wtr = (WaterTestRecord) trec;
                    if (wtr.getParameter().equals(param)) {
                        //lval = wtr.getResult();
                        ltime = wtr.getTime();
                        foundit = true;
                    } else {
                        continue;
                    }
                    break;
            }
            if (foundit) {
                break;
            }
        }

        // If you don't find one return the value you currently have
        if (ltime == 0) {
            return this.getTankWater().getValue(param);
        }

        //  Calculate the time since then
        //Long curtime = aTime;  // Do it this way in case there are any funny conversions to how we've been using date

        //  calculate the change due to dosing (calculateDosingAddition()) and consumption over the timeperiod
        Double addval = this.calculateDosingAddition(param, ltime, aTime);

        Double delT = (double) (aTime - ltime);

        Double conval = this.getDailyConsumption(param) * (delT / DosingComputer.dayInMillis);

        //  use those amounts to update the value
        Double retval = this.getTankWater().getValue(param) + addval - conval;

        return retval;
    }
}
