/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author David
 */
public class DateTimePanel extends JPanel {
    
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    
        public DateTimePanel() {
        initComponents();

        dateSpinner.setModel(new SpinnerDateModel(
                new Date(DosingComputer.getTheTime()),
                new Date(1357020000L),
                new Date(DosingComputer.getTheTime()),
                Calendar.DAY_OF_MONTH));
        
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner , "EEE - MM - dd - yyyy"));
        
        
        timeSpinner.setModel(new SpinnerDateModel(
                //new Date(978328800000L),
                new Date(DosingComputer.getTheTime()),
                new Date(86340L),
                new Date(DosingComputer.getTheTime()),
                Calendar.MINUTE));
        
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH : mm"));


    }
    
    
    public Date getSelectedDate(){
        Date ddate = (Date)(dateSpinner.getModel().getValue());
        Date tdate = (Date)(timeSpinner.getModel().getValue());
        //jLabel3.setText("ddate  " + ddate.toString() + "  " + ddate.getTime());
        //jLabel4.setText("tdate  " + tdate.toString() + "  " + tdate.getTime());
        Calendar cal = new GregorianCalendar();
        
        //Long daypart = ddate.getTime() - (ddate.getTime() % 86400000) + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
        Long timepart = (tdate.getTime() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET))%86400000 ;
        //Long daypart = ddate.getTime() - (ddate.getTime() % 86400000);
        Long daypart = ddate.getTime() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
        daypart = daypart - (daypart % 86400000);
        daypart = daypart - cal.get(Calendar.ZONE_OFFSET) - cal.get(Calendar.DST_OFFSET);
        //Long timepart = (tdate.getTime()%86400000);
        DateFormat df = new SimpleDateFormat("EEE - MM - dd - yyyy  @  HH : mm");
        //jLabel5.setText("daypart  " + df.format(daypart));
        //jLabel6.setText("timepart  " + df.format(timepart));
        Date retval = new Date(daypart + timepart);
        return retval;
    }
    
    
    
    private void initComponents(){
        
        dateSpinner = new JSpinner();
        timeSpinner = new JSpinner();
        
        GridLayout layout = new GridLayout(0,1);
        layout.setVgap(10);
        this.setLayout(layout);
        
        this.add(dateSpinner);
        this.add(timeSpinner);        
    }
    
}
