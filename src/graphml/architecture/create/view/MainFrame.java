/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.view;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.controller.Controller;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * This class is the main JFrame for the creation of the GraphML nodes and edges
 * @author Thomas Stocker
 */
public class MainFrame extends JFrame implements Subscriber {
	
    // attributes
    private boolean capturingOn = false;
    Controller controller;
    
    // menu items
    private final JButton bNode = new JButton("Add ROOM");
    private final JButton bEdge = new JButton("Add EDGE");
    private final JButton bFinish = new JButton("Finish");
    private final JCheckBox cbFill = new JCheckBox("Fill rooms", false);
    
    /**
     * Constructor
     * @param controller connection to controller
     * @param mPanel the main panel to be added to the frame
     */
    public MainFrame(Controller controller, MainPanel mPanel) {
        // set attributes
        this.controller = controller;
        
        // set frame
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        // Button Panel
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEADING));
        bNode.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { addNodeClicked(); } });
        bEdge.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { addEdgeClicked(); } });
        bFinish.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { finishClicked(); } });
        cbFill.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { checkBoxChanged(); } });
        
        menu.add(bNode);
        menu.add(bEdge);
        menu.add(bFinish);
        menu.add(cbFill);
        
        // add content panel for image
        this.add(menu, BorderLayout.PAGE_START);
        this.add(mPanel, BorderLayout.CENTER);
        
        // add to publisher
        controller.addSubscriber(this);
    }

    /**
     * This method sets the action that shall be performed when the action changes
     * @param myAction the current action
     */
    @Override
    public void setAction(Constants.Actions myAction) {
        // switch action according to action
        switch(myAction) {
            case ADDNODESTART:
                bNode.setText("Finish ROOM");
                bFinish.setText("Abort");
                bEdge.setEnabled(false);
                capturingOn = true;
                break;

            case ADDNODEEND:
                bNode.setText("New ROOM");
                bFinish.setText("Finish");
                bEdge.setEnabled(true);
                capturingOn = false;    	                
                break;
            
            case ADDEDGESTART:
                bEdge.setText("Finish EDGE");
                bFinish.setText("Abort");
                bNode.setEnabled(false);
                capturingOn = true;      	
                break;

            case ADDEDGEEND:
                bEdge.setText("New EDGE");
                bFinish.setText("Finish");
                bNode.setEnabled(true);
                capturingOn = false;    
                break;
            default:
                bNode.setText("New ROOM");
                bEdge.setText("New EDGE");
                bFinish.setText("Finish");
                bNode.setEnabled(true);
                bEdge.setEnabled(true);
                bFinish.setEnabled(true);
                capturingOn = false;
                break;
        }
    }    

    /**
     * This method informs the controller, that the "addNode"-Button has been clicked
     */
    private void addNodeClicked() {
        // start capturing
        if(capturingOn == false) {
            controller.addNodeStart();
        }
        else {
            controller.addNodeEnd();
        }
    }

    /**
     * This method informs the controller, that the "addEdge"-Button has been clicked
     */
    private void addEdgeClicked() {
        // start capturing
        if(capturingOn == false) {
            controller.addEdgeStart();
        }
        else {
            controller.addEdgeEnd();
        }
    }

    /**
     * This method informs the controller, that the "finish"-Button has been clicked
     */
    private void finishClicked() {
        if(capturingOn == false) {
            controller.finishedClicked();
        }
        else {
            controller.abortClicked();
        }
    }

    /**
     * This method informs the controller about a changed checkbox
     */
    private void checkBoxChanged() {
        controller.checkBoxChanged(cbFill.isSelected());
    }

    /**
     * This overridden method is not implemented
     * @param e 
     */
    @Override
    public void updateEdges(ArrayList<MyEdge> e) {
        // do nothing
    }

    /**
     * This overriden method is not implemented
     * @param n 
     */
    @Override
    public void updateNodes(ArrayList<MyNode> n) {
        // do nothing
    }
}