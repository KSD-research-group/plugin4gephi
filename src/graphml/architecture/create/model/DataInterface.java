/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.model;

import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import java.util.ArrayList;

/**
 * This is the interface for the controller to get the data
 * @author Thomas Stocker
 */
public interface DataInterface {

    /**
     * This method initializes the storage and should be called at the beginning
     * @return true if init was successful, false otherwise
     */
    public boolean initialize();
    /**
     * This method returns all nodes
     * @return list with nodes
     */
    public ArrayList<MyNode> getNodes();
    /**
     * This method returns all edges
     * @return list with all edges
     */
    public ArrayList<MyEdge> getEdges();
    /**
     * This method adds a node to gephi
     * @param n the node
     * @return true if successful, false otherwise
     */
    public boolean addNode(MyNode n);
    /**
     * This method adds an edge to gephi
     * @param e the edge
     * @return true if successful, false otherwise
     */
    public boolean addEdge(MyEdge e);
    /**
     * This method deletes a node from gephi
     * @param n the node
     * @return true if successful, false otherwise
     */
    public boolean deleteNode(MyNode n);
    /**
     * This method deletes an edge from gephi
     * @param e the edge
     * @return true if successful, false otherwise
     */
    public boolean deleteEdge(MyEdge e);
    /**
     * This method loads all data from Gephi
     * @return true if successful, false otherwise
     */
    public boolean loadDataFromGephi();
}
