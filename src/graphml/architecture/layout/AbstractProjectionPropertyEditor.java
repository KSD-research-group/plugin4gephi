/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.layout;

/**
 * This class is responsible for the content of the combo box in the layout panel
 * @author Thomas Stocker
 */
import graphml.architecture.common.Constants;
import java.beans.PropertyEditorSupport;

abstract class AbstractProjectionPropertyEditor extends PropertyEditorSupport {

    // attributes
    private String selectedRow;

    /**
     * Returns the two different modes (all stored in "graphml.architecture.common.Constants.java")
     * @return an array with all available modes
     */
    @Override
    public String[] getTags() {
        return Constants.rows;
    }

    /**
     * Returns the selected row value (set by "setValue()"
     * @return the selected row value
     */
    @Override
    public Object getValue() {
        return selectedRow;
    }

    /**
     * This method sets the selected row value, if existing in list
     * @param value the selected value in the combo box
     */
    @Override
    public void setValue(Object value) {
        for (String row : Constants.rows) {
            if (row.equals((String) value)) {
                selectedRow = row;
                break;
            }
        }
    }

    /**
     * This method returns the selected value as string
     * @return selected value as string
     */
    @Override
    public String getAsText() {
        return (String) getValue();
    }

    /**
     * This method sets the selectedRow-Value as text
     * @param text the text to be set
     * @throws IllegalArgumentException An Exception to be thrown, if the given argutment is no fitting text
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text);
    }

    /**
     * This method checks if the given colun is a number column
     * @param column the name of the column
     * @return false
     */
    public boolean isNumberColumn(String column) {
        return false;
    }

    /**
     * This method checks, if the given column is a string column
     * @param column the name of the column
     * @return true
     */
    public boolean isStringColumn(String column) {
        return true;
    }
}