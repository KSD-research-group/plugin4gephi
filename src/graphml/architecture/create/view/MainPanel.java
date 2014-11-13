/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.view;

import graphml.architecture.create.model.MessageHandler;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This is the main panel with the part for the edges, the image and the status message
 * @author Thomas Stocker
 */
public class MainPanel extends JComponent {

    private static final long serialVersionUID = 1L;
    
    // attributes
    private final MessageHandler messager = MessageHandler.getInstance();

    private final JPanel statusPanel = new JPanel(new BorderLayout());;
    private final JLabel message = new JLabel();

    /**
     * Constructor
     */
    public MainPanel(ImagePanel imagePanel, ListPanel listPanel) {
        // set panel properties
        this.setLayout(new BorderLayout());

        // ImagePanel
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPanel = new JScrollPane(imagePanel);
        
        // JPanel on bottom
        statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        statusPanel.add(message,BorderLayout.LINE_START);

        // add all to panel
        this.add(listPanel, BorderLayout.LINE_START);
        this.add(scrollPanel, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.PAGE_END);
        
        // add message panel
        messager.setLabel(message);
    }	
}