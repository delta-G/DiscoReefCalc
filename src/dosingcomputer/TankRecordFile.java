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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dave
 */
public class TankRecordFile implements Iterable<TankRecord> {

    private final File eventFile;
    private Integer numberOfRecords;

    public TankRecordFile(File file) {
        eventFile = file;
        if (!eventFile.isFile()) {
            try {
                eventFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TankRecordFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try (RandomAccessFile rfile = new RandomAccessFile(eventFile, "r")) {
            numberOfRecords = (int) (rfile.length() / 100);
        } catch (IOException ex) {
            System.out.println("Got an Exception in EventFile constructor");
        }

    }
    
    public void removeRecord(TankRecord atrec){
        File tempFile = new File(Preferences.getHomeDirectory().getPath() + "tmp.trf");
        BufferedWriter outwriter = null;
        BufferedReader inreader = null;
        try {
            
            outwriter = new BufferedWriter(new FileWriter(tempFile));
            inreader = new BufferedReader(new FileReader(eventFile));
            
            String line = inreader.readLine();
            
            while (line != null) {
                if (tankRecordFactory(line).toString().equals(atrec.toString())   ){
                    numberOfRecords--;
                } else {
                    outwriter.write(line + '\n');
                }
                line = inreader.readLine();
            }
            outwriter.close();
            inreader.close();
            Files.copy(tempFile.toPath(), eventFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException ex) {
            Logger.getLogger(TankRecordFile.class.getName()).log(Level.SEVERE, null, ex);
        }         
        
    }

    public File getFile() {
        return eventFile;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }
    
    public Integer getIndexForRecord(Long rectime){
        Integer retval = numberOfRecords - 1;  
        
        for (TankRecord trec : this){
            if (trec.getTime() > rectime){
                retval--;
                continue;
            }
            if (trec.getTime() < rectime){
                break;
            }
            // If trec.getTime() == rectime keep looping until you find one that is less
            //  You'll return the earliest one with the right time or the first one with a later time if none
            //  Have the right time. 
        }
        
        return retval;
    }
    
    

    public void addRecord(TankRecord atrec) {
        
        //  Gather all the records that are chronilogically after this one
        ArrayList<TankRecord> reclist = new ArrayList<>();
        //  The records will be in the reverse order you want to write them
        for (TankRecord tr : this) {
            if (tr.getTime() > atrec.getTime()) {
                reclist.add(tr);
            } else if (tr.getTime() < atrec.getTime()) {  // no need to search the whole file
                break;
            }
            
        }
        // add the new record to the end of that list
        reclist.add(atrec);

        try (RandomAccessFile rfile = new RandomAccessFile(eventFile, "rw")) {
            rfile.seek((numberOfRecords - (reclist.size()-1)) * 100);

            int index = reclist.size();
            
            //write out all the records in the list in reverse order
            while (index > 0) {
                writeRecord(reclist.get(--index), rfile);
            }

            numberOfRecords += 1;

        } catch (IOException ex) {
            Logger.getLogger(TankRecordFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeRecord(TankRecord atrec, RandomAccessFile outFile) throws IOException {
        String event = atrec.toString();

        if (!event.endsWith(",")) {
            event += ",";
        }
        if (event.length() > 99) {
            throw (new ArrayIndexOutOfBoundsException());
        } else {
            while (event.length() < 99) {
                event += " ";
            }
            event += '\n';
        }
        outFile.writeBytes(event);
    }

    public TankRecord getRecordAt(int aIndex) {
        String recString = "";
        try (RandomAccessFile rFile = new RandomAccessFile(eventFile, "r")) {

            rFile.seek(aIndex * 100);
            char c = (char) rFile.readByte();
            while (c != '\n') {
                recString += c;
                c = (char) rFile.readByte();
            }

            return tankRecordFactory(recString);

        } catch (IOException ex) {
            Logger.getLogger(TankRecordFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     *Creates an ArrayList of the latest water tests for a given parameter
     * @param num The number of records to include (will return a lesser amount if there aren't enough)
     * @param p The parameter of interest
     * @return an ArrayList of num WaterTestRecords for Parameter p (Index 0 is newest latest test)
     */
    public ArrayList<WaterTestRecord> getLastTests(int num, Parameter p){
        
        ArrayList<WaterTestRecord> retval = new ArrayList<>();
        
        for (TankRecord trec : this) {
            if ("Water Test".equals(trec.getType())){
                if (((WaterTestRecord)trec).getParameter().equals(p)) {
                    retval.add((WaterTestRecord) trec);
                }
                if (retval.size() == num){
                    break;
                }
            }
        }        
        return retval;
    }
    
    private int sindex;  //persistent variable for getLast method
    private String stypeStr;   //persistent variable for getLast method
    public TankRecord getLast(String atypeStr){
        
        if(!atypeStr.equals("")){
            stypeStr = atypeStr;
            sindex = numberOfRecords -1;
        }
        TankRecord retval = this.getRecordAt(sindex);
        
        while((!retval.getType().equals(stypeStr)) && sindex >=0){
            retval = this.getRecordAt(sindex--);
        }
        // calling function needs to check for null
        if(sindex < 0){
            return null;
        }
        return retval;
        //sindex is now one behind retval.  So if you call again with "" set for the type you'll just keep rolling through.
    }

    public Iterator<TankRecord> getForwardIterator(int index) {
        return new ForwardRecordIterator(index);
    }

    @Override
    public Iterator<TankRecord> iterator() {
        return new ReverseRecordIterator();
    }

    public static TankRecord tankRecordFactory(String line) {

        String[] inArr = line.split("[,]");
        switch (inArr[1]) {

            case "Water Test":
                return new WaterTestRecord(
                        new Date(Long.parseLong(inArr[0])),
                        Parameter.parseParam(inArr[2]),
                        Double.parseDouble(inArr[3]));

            case "Consumption Set":
                return new ConsumptionSetRecord(
                        new Date(Long.parseLong(inArr[0])),
                        Parameter.parseParam(inArr[2]),
                        Double.parseDouble(inArr[3]),
                        Double.parseDouble(inArr[4]));

            case "Dosage Set":
                return new DosageSetRecord(
                        new Date(Long.parseLong(inArr[0])),
                        inArr[2],
                        Double.parseDouble(inArr[3]),
                        Double.parseDouble(inArr[4]));
            case "Manual Dose":
                return new ManualDoseRecord(
                        new Date(Long.parseLong(inArr[0])),
                        DosingComputer.parseDosingProduct(inArr[2]),
                        Double.parseDouble(inArr[3]));
            case "Water Change":
                return new WaterChangeRecord(
                        new Date(Long.parseLong(inArr[0])),
                        Double.parseDouble(inArr[1]),
                        new Water(line));                
            default:
                return null;
        }

    }

    private class ReverseRecordIterator implements Iterator<TankRecord> {

        private Integer itIndex;   // Holds the index of the currently open record. ie. returned by the last call to next()

        public ReverseRecordIterator() {
            itIndex = numberOfRecords;
        }

        @Override
        public boolean hasNext() {
            return (itIndex > 0);
        }

        @Override
        public TankRecord next() {
            TankRecord retval = getRecordAt(--itIndex);
            return retval;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public Integer getCurrentIndex(){
            return itIndex;
        }
    }

    private class ForwardRecordIterator implements Iterator<TankRecord> {

        private Integer itIndex;

        public ForwardRecordIterator(int aindex) {
            itIndex = aindex - 1;  // The first call to next will advance before reading.
        }

        @Override
        public boolean hasNext() {
            return (itIndex < numberOfRecords - 1);
        }

        @Override
        public TankRecord next() {
            TankRecord retval = getRecordAt(++itIndex);
            return retval;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        public Integer getCurrentIndex(){
            return itIndex;
        }
    }
}
