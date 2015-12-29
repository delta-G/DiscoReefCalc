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
package DosingComputerGUI;

import dosingcomputer.DosingComputer;
import dosingcomputer.Tank;
import dosingcomputer.TankRecord;
import dosingcomputer.TankRecordFile;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;

/**
 *
 * @author Dave
 */
public class EventFileViewPanel extends javax.swing.JPanel {

    DefaultListModel listModel = new DefaultListModel();
    
    public EventFileViewPanel() {
        initComponents();
        popList(DosingComputer.guiFrame.getCurrentTank());
        jList1.setModel(listModel);
        
    }
    
    private void popList(Tank tank){
        listModel.removeAllElements();
        listModel.addElement("Start of List");
        for(TankRecord trec : tank.getTankRecordFile()){
            listModel.addElement(formatRecord(trec));
        }
        listModel.addElement("End of List");
    }
    
    private String formatRecord(TankRecord trec){
        String retval = "";
        Date d = new Date(trec.getTime());
        retval += d.toString() + " <-> ";
        retval += trec.toString();
        
        return retval;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        removeRecordButton = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        removeRecordButton.setText("Remove Selected Records");
        removeRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRecordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeRecordButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(removeRecordButton)
                .addContainerGap(86, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRecordButtonActionPerformed
        // TODO add your handling code here:
        
        ArrayList<String> selectedVals = new ArrayList<>();
        for (Object o : jList1.getSelectedValuesList()) {
            String st = o.toString().substring(o.toString().indexOf("<-> ") + 4);
            TankRecord trec = TankRecordFile.tankRecordFactory(st);
            DosingComputer.guiFrame.getCurrentTank().getTankRecordFile().removeRecord(trec);
        }
        popList(DosingComputer.guiFrame.getCurrentTank());
    }//GEN-LAST:event_removeRecordButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeRecordButton;
    // End of variables declaration//GEN-END:variables
}
