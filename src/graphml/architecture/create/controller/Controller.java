/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.controller;

import graphml.architecture.common.Constants;
import graphml.architecture.common.Constants.Actions;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.model.DataInterface;
import graphml.architecture.create.model.MessageHandler;
import graphml.architecture.create.model.Storage;
import graphml.architecture.create.view.ImagePanel;
import graphml.architecture.create.view.ListPanel;
import graphml.architecture.create.view.MainFrame;
import graphml.architecture.create.view.MainPanel;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.GraphController;
import org.openide.util.Lookup;

/**
 * This class is the controller with the whole business logic
 * @author Thomas Stocker
 */
public class Controller extends Publisher {

    // #################################################### 
    // Controller attributes
    // #################################################### 
    private final DataInterface storage = new Storage();
    private final MessageHandler messages = MessageHandler.getInstance();
    private final ImagePanel imagePanel;
    private final ListPanel listPanel;
    private final MainFrame mainFrame;

    /**
     * Constructor
     */
    public Controller() {
        // initialize
        storage.initialize();
        imagePanel = new ImagePanel(this);
        listPanel = new ListPanel(this);
        mainFrame = new MainFrame(this, new MainPanel(imagePanel, listPanel));
        
        // checks for beginning
        if(Lookup.getDefault().lookup(GraphController.class).getModel() == null) {
            // found null pointer
            MessageHandler.showErrorMessage("No project created. Please create a new project in Gephi first.");
            mainFrame.dispose();
            System.exit(0);
        }
        
        // load image
        ImageLoader.loadImage(mainFrame, imagePanel, messages);
        
        // ask if load points from gephi
        if(JOptionPane.showConfirmDialog(mainFrame, "Do you want to load your existing data from Gephi into the GraphCreator?", "Existing data", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            storage.loadDataFromGephi();
            updateNodes(storage.getNodes());
            updateEdges(storage.getEdges());
        }
        else {
            // clear current model
            Lookup.getDefault().lookup(GraphController.class).getModel().clear();
        }
    }

    // #################################################### 
    // delete methods
    // ####################################################     
    /**
     * This method deletes a node
     * @param n the node to be deleted
     */
    public void deleteNode(MyNode n) {
        storage.deleteNode(n);
        updateNodes(storage.getNodes());
        updateEdges(storage.getEdges());
    }
    /**
     * This method deletes the edge
     * @param e the edge to be deleted
     */
    public void deleteEdge(MyEdge e) {
        storage.deleteEdge(e);
        updateEdges(storage.getEdges());
    }

    // #################################################### 
    // add method for edge from MyMouseListener
    // ####################################################     
    /**
     * This method adds an edge
     * @param edge edge to be added
     */
    public void addEdge(MyEdge edge) {
        messages.setTooltip("Capturing of edge finished");
        boolean result = storage.addEdge(edge);
        if(result == true) {
            updateEdges(storage.getEdges());
        }
        else {
            JOptionPane.showMessageDialog(mainFrame, "Error adding node. Probably this connection already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        setAction(Actions.ADDEDGEEND);
    }

    // #################################################### 
    // reaction on button-clicks in MainFrame
    // ####################################################     
    /**
     * This method sets the tooltip and sets the action for the subscribers
     */
    public void addNodeStart() {
        messages.setTooltip("Click into the room that should be captured");
        setAction(Actions.ADDNODESTART);
    }
    /**
     * This method finished the capturing of a node
     */
    public void addNodeEnd() {
        MyNode n = imagePanel.getCurrentNode();
                
        // skip steps if node = null
        if(n != null) {
            // check corners
            if(n.checkCornersAvailable() == false) {
                if(JOptionPane.showConfirmDialog(mainFrame, "No corners added to main node. Do you want to continue?", "Corner alert", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            
            messages.setTooltip("Capturing of room finished");
            storage.addNode(n);
            updateNodes(storage.getNodes());
        }
        else {
            messages.setTooltip("Capturing aborted - no room selected");
        }
        setAction(Actions.ADDNODEEND);
    }

    /**
     * This method starts capturing of an edge
     */
    public void addEdgeStart() {
        messages.setTooltip("Click into the room of the starting point of the edge");
        setAction(Actions.ADDEDGESTART);
    }

    /**
     * This method notifies, if the capturing of an edge has been aborted by clicking on finish
     */
    public void addEdgeEnd() {
        messages.setTooltip("Capturing aborted - no edge selected");
        setAction(Actions.ADDEDGEEND);
    }
    
    /**
     * This method handles the action, when "Abort" is clicked
     */
    public void abortClicked() {
        messages.setTooltip("Capturing aborted");
        setAction(Actions.ABORT);
    }    
    
    /**
     * This method handles the action, when the checkbox is changed
     * @param checked state of the checkbox
     */
    public void checkBoxChanged(boolean checked) {
        imagePanel.checkBoxChanged(checked);
    }

    /**
     * This method handles the action, when "finish" is clicked
     */
    public void finishedClicked() {
        // check if really wants to finish
        int result = JOptionPane.showConfirmDialog(mainFrame, "Do you really want to finish?", "Finishing...", JOptionPane.YES_NO_OPTION);
        if(result != JOptionPane.YES_OPTION) {
            return;
        }
        
        // add columns if not already existing
        AttributeModel attrModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        for(int i = 0; i < Constants.newColumnsNode.length; i++) {
            if(attrModel.getNodeTable().getColumn(Constants.newColumnsNode[i]) == null) {
                attrModel.getNodeTable().addColumn(Constants.newColumnsNode[i], Constants.newColumnsNodeType[i]);
            }
        }
        for(int i = 0; i < Constants.newColumnsEdge.length; i++) {
            if(attrModel.getEdgeTable().getColumn(Constants.newColumnsEdge[i]) == null) {
                attrModel.getEdgeTable().addColumn(Constants.newColumnsEdge[i], Constants.newColumnsEdgeType[i]);
            }
        }
        
        // close frame
        mainFrame.dispose();  
        System.exit(0);
    }

    /**
     * This method searches for the nearest node
     * @param p the point around which the node is searched
     * @return the nearest node; null if not found
     */
    public MyNode searchNearestNode(Point p) {
        ArrayList<MyNode> nodes = storage.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            if(Math.abs(nodes.get(i).getCenterX() - p.getX()) < 15 && Math.abs(nodes.get(i).getCenterY() - p.getY()) < 15) {
                return nodes.get(i);
            }
        }
        return null;
    }    
}
