/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.colorizer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * This class overwrites the ListCellRenderer to allow different background-colors in a table
 * @author Thomas Stocker
 */
public class MyListCellRenderer extends DefaultListCellRenderer {
    
    /**
     * Overwritten method to return list cell renderer component
     * @param list JList for super constructor
     * @param value Object for super constructor
     * @param index Index for super constructor
     * @param isSelected Indicator if value is selected for super constructor
     * @param cellHasFocus Indicator if cell has focus for super constructor
     * @return the list cell renderer component
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // invoke super method first
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        // the set color depending on input value
        Color c = (Color) value;
        setForeground(c);
        
        // return CellRenderer
        return this;
    }
}
