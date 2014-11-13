/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.view;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.model.MessageHandler;
import graphml.architecture.create.controller.Controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This mouse listener reacts on all clicks on the image panel
 * @author Thomas Stocker
 */
public class MyMouseListener implements MouseListener {

    // attributes
    private final ImagePanel imagePanel;
    private final Controller controller;
    private final MessageHandler messages = MessageHandler.getInstance();
    private MyNode node;
    private MyEdge edge;

    private boolean active = false;
    private boolean detectRoom = true;

    /**
     * Constructor
     * @param p calling panel
     * @param controller controller object
     */
    public MyMouseListener(ImagePanel p, Controller controller) {
        // initialize
        this.node = null;
        this.imagePanel = p;
        this.controller = controller;
    }

    /**
     * This method activates and deactivates the MouseListener
     * @param setActive true: activates the MouseListener <br/> false: deactivates the MouseListener
     * @param detectRoom true: activates the room detection <br/> false: activates the edge detection
     */
    private void activate(boolean setActive, boolean detectRoom) {
            this.active = setActive;
            this.detectRoom = detectRoom;
    }

    /**
     * This method defines what happens while clicking the mouse
     * @param e the MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // return if not activated
        if(!active) return;
        
        // check if right-click to end node
        if(e.isMetaDown() == true && node != null) {
            controller.addNodeEnd();
            return;
        }

        // calculate point
        Point p = e.getPoint();
        p.x = (int)(p.x/imagePanel.getScale());
        p.y = (int)(p.y/imagePanel.getScale());

        if(detectRoom == true) {
            if(node == null) {
                // the node is not yet created, so the click is the middle of the room (the other clicks are corners)

                // determine room type
                int result;
                JPanel panel = new JPanel();
                panel.add(new JLabel("What type of room is this:"));
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
                for(int i = 0; i < Constants.roomType.length; i++) {
                    model.addElement(Constants.roomType[i]);
                }
                JComboBox<String> comboBox = new JComboBox<String>(model);
                comboBox.setMaximumRowCount(12);
                panel.add(comboBox);

                switch (JOptionPane.showConfirmDialog(null, panel, "Choose type of room", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                case JOptionPane.OK_OPTION:
                    // Option OK has been selected
                    result = comboBox.getSelectedIndex();
                    break;
                default:
                    // in any other case: return
                    return;
                }

                // input was ok -> add information to node
                node = new MyNode(Constants.roomType[result], (long)(p.getX()), (long)(p.getY()), 0);
                imagePanel.setCursor(Constants.Cursor.ADDCORNER);
            }
            else {
                // node is corner, so add it to the node
                node.addCorner(p);
            }
            // update groundplan and tooltip
            imagePanel.setCurrentNode(node);
            messages.setTooltip("Add corner number " + (node.getCorners().size() + 1));
        }
        else {
            if(edge == null) {
                // the edge is not yet created

                // get corresponding node to click
                MyNode n = controller.searchNearestNode(p);

                if(n == null) {
                    // no node found
                    return;
                }
                
                // store data point
                edge = new MyEdge(n, null, null, -1);
                // set cursor
                imagePanel.setCursor(Constants.Cursor.FINISHEDGE);

                // update groundplan and tooltip
                messages.setTooltip("Now click on destination node");
            }
            else {
                // click on destination node
                MyNode n = controller.searchNearestNode(p);

                if(n == null) {
                    // no node found
                    return;
                }
                
                // determine edge type
                int result;
                JPanel panel = new JPanel();
                panel.add(new JLabel("What type of edge is this:"));
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
                for (String edgeType : Constants.edgeType) {
                    model.addElement(edgeType);
                }
                JComboBox<String> comboBox = new JComboBox<String>(model);
                panel.add(comboBox);

                switch (JOptionPane.showConfirmDialog(imagePanel, panel, "Choose type of edge", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                case JOptionPane.OK_OPTION:
                    // Option OK has been selected
                    result = comboBox.getSelectedIndex();
                    break;
                default:
                    // in any other case: return
                    return;
                }
                
                edge = new MyEdge(edge.getNode1(), n, Constants.edgeType[result], -1);
                controller.addEdge(edge);
            }
        }
    }

    /**
     * Not implemented method from MouseListener
     * @param e 
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing
    }

    /**
     * Not implemented method from MouseListener
     * @param e 
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // nothing
    }

    /**
     * Not implemented method from MouseListener
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // nothing
    }

    /**
     * Not implemented method from MouseListener
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // nothing
    }

    /**
     * This method sets the action
     * @param myAction the action to be done
     */
    void setAction(Constants.Actions myAction) {
        // switch action according to action
        switch(myAction) {
            case ADDNODESTART:
                activate(true, true);
                break;
            case ADDNODEEND:
                activate(false, true);
                node = null;
                break;
            case ADDEDGESTART:
                activate(true, false);
                break;
            case ADDEDGEEND:
                activate(false, false);
                edge = null;
                break;
            case ABORT:
                activate(false, true);
                edge = null;
                node = null;
                break;
            default:
                activate(false, true);
                break;
        }
    }
}
