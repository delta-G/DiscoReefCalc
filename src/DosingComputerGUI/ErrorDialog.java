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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Dave
 */
public class ErrorDialog {

    public ErrorDialog() {
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(DosingComputer.guiFrame,
                message,
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }

    public static Boolean showErrorInputDialog(String message) {

        final JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);

        final JDialog dialog = new JDialog(DosingComputer.guiFrame,
                "Please choose an option:",
                true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                String prop = e.getPropertyName();

                if (dialog.isVisible()
                        && (e.getSource() == optionPane)
                        && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                    //If you were going to check something
                    //before closing the window, you'd do
                    //it here.
                    dialog.setVisible(false);
                }
            }
        });
        dialog.pack();
        dialog.setVisible(true);

        int value = ((Integer) optionPane.getValue()).intValue();
        if (value == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;

        }
    }
    
    public static void showExceptionDialog(Exception e){
        String message = "";
        message += "An Exception has occurred \n";
        message += e.getMessage();
        message += " \n\n";
        for (StackTraceElement elem : e.getStackTrace()){
            message += elem.toString();
            message += "\n";
        }
        JOptionPane.showMessageDialog(DosingComputer.guiFrame,
                message,
                "Exception",
                JOptionPane.WARNING_MESSAGE);
    }
    
}
