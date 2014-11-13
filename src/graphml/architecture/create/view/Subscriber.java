/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.view;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import java.util.ArrayList;

/**
 * This is a class from the Pubisher-Subscriber-Pattern from the Controller
 * This part handles the subscriber
 * @author Thomas
 */
public interface Subscriber {
    /**
     * This method publishes the updated edges
     * @param e list of edges
     */
    public void updateEdges(ArrayList<MyEdge> e);
    /**
     * This method publishes the updated nodes
     * @param n list of nodes
     */
    public void updateNodes(ArrayList<MyNode> n);
    /**
     * This method publishes the set action
     * @param myAction the action to be done
     */
    public void setAction(Constants.Actions myAction);
}
