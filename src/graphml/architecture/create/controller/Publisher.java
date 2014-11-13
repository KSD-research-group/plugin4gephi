/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.controller;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.view.Subscriber;
import java.util.ArrayList;

/**
 * This abstract class implements the Publisher / Subscriber pattern. 
 * This part is the publisher, that contacts all subscriber.
 * @author Thomas Stocker
 */
abstract class Publisher {
    // ArrayList with subscriber
    ArrayList<Subscriber> subscriber = new ArrayList<Subscriber>();
    
    /**
     * This method adds a subscriber
     * @param s the subscriber
     */
    public void addSubscriber(Subscriber s) {
        subscriber.add(s);
    }
    
    /**
     * This method updates all edges of the subsribers
     * @param e list of edges
     */
    protected void updateEdges(ArrayList<MyEdge> e) {
        for(int i = 0; i < subscriber.size(); i++) {
            subscriber.get(i).updateEdges(e);
        }
    }
    /**
     * This method updates all nodes of the subscribers
     * @param n list of nodes
     */
    protected void updateNodes(ArrayList<MyNode> n) {
        for(int i = 0; i < subscriber.size(); i++) {
            subscriber.get(i).updateNodes(n);
        }
    }
    /**
     * This method sets the action of the subscribers
     * @param myAction the action id
     */
    protected void setAction(Constants.Actions myAction) {
        for(int i = 0; i < subscriber.size(); i++) {
            subscriber.get(i).setAction(myAction);
        }
    }
}
