/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
