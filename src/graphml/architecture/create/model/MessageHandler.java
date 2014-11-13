package graphml.architecture.create.model;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class is a singleton that is responsible for the message handling
 * @author Thomas Stocker
 */
public class MessageHandler {
	
    // Singleton-Methods
    private static final MessageHandler instance = new MessageHandler();
    private MessageHandler() {}
    
    /**
     * Gets the singleton instance of the MessageHandler
     * @return the singleton instance
     */
    public static MessageHandler getInstance() {
        return instance;
    }
    
    // Tooltip
    JLabel tooltip;
    /**
     * This method is responsible to set the tooltip Label
     * @param l Tooltip-Label
     */
    public void setLabel(JLabel l) {
    	tooltip = l;
    }
    /**
     * This method sets the tooltip label, if the label is set
     * @param s text to be set as tooltip
     */
    public void setTooltip(String s) {
    	if(tooltip != null) {
    		tooltip.setText(s);
    	}
    }

    /**
     * This method shows a message dialog with the given string
     * @param s text to be displayed
     */
    public static void showWizzardMessage(String s) {
    	// Show message
        JOptionPane.showMessageDialog(null, s, "Creation Wizzard", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method shows a message dialog with the given string
     * @param c Component (as context of the message)
     * @param s text to be displayed
     */
    public static void showWizzardMessage(Component c, String s) {
    	// Show message
        JOptionPane.showMessageDialog(c, s, "Creation Wizzard", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method shows an error message with the given text
     * @param s text to be displayed
     */
    public static void showErrorMessage(String s) {
    	// Show message
        JOptionPane.showMessageDialog(null, s, "Error Handler", JOptionPane.ERROR_MESSAGE);
    }
}
