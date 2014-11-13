/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.model;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import java.awt.Point;
import java.util.ArrayList;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.openide.util.Lookup;

/**
 * This class implements the direct connection to Gephi
 * @author Thomas Stocker
 */
public class DataLoaderGephi {
    
    // #################################################### 
    // variables
    // #################################################### 
    private GraphModel model = Lookup.getDefault().lookup(GraphController.class).getModel();
    private Node[] nodesGephi;
    private Edge[] edgesGephi;
    
    // #################################################### 
    // init
    // #################################################### 
    /**
     * This method initalizes the connection
     * @return true if successful, false otherwise
     */
    public boolean initialize() {
        // get model
        model = Lookup.getDefault().lookup(GraphController.class).getModel();
        return (model != null);
    }
    
    // #################################################### 
    // add and remove
    // #################################################### 
    /**
     * This method adds a node
     * @param n the ndoe
     * @return true if successful, false otherwise
     */
    public boolean addNodeToGephi(MyNode n) {
        // fill node with attributes
        Node nodeGephi = model.factory().newNode();
        nodeGephi.getAttributes().setValue(Constants.attrRoomType, n.getRoomType());
        nodeGephi.getAttributes().setValue(Constants.attrCenter, n.getCenter());                    
        nodeGephi.getAttributes().setValue(Constants.attrCorners, n.getCornersAsString());
        nodeGephi.getNodeData().setSize(10);

        // add node and get new graph
        boolean result = model.getUndirectedGraph().addNode(nodeGephi);
        nodesGephi = model.getUndirectedGraph().getNodes().toArray();
        
        return result;
    }
    
    /**
     * This method adds an edge
     * @param e the edge
     * @return true if successful, false otherwise
     */
    public boolean addEdgeToGephi(MyEdge e) {
        // find corresponding nodes
        Node n1 = findNodeToId(nodesGephi, e.getNode1().getId());
        Node n2 = findNodeToId(nodesGephi, e.getNode2().getId());
        if(n1 == null || n2 == null) return false;

        // create edge
        Edge eGephi = model.factory().newEdge(n1, n2, 1, false);
        if(eGephi == null) return false;

        // set attributes and add edge
        eGephi.getAttributes().setValue(Constants.attrEdgeType, e.getEdgeType());
        boolean result = model.getUndirectedGraph().addEdge(eGephi);
        
        // get new graph
        edgesGephi = model.getUndirectedGraph().getEdges().toArray();
        
        return result;
    }
    
    /**
     * This method deletes a node
     * @param n the node
     * @return true if successful, false otherwise
     */
    public boolean deleteNodeFromGephi(MyNode n) {
        // get node and delete it
        Node nGephi = findNodeToId(nodesGephi, n.getId());
        boolean result = model.getUndirectedGraph().removeNode(nGephi);
        
        // update arrays
        nodesGephi = model.getUndirectedGraph().getNodes().toArray();
        edgesGephi = model.getUndirectedGraph().getEdges().toArray();
        
        return result;
    }
     
    /**
     * This method deletes an edge
     * @param e the edge
     * @return true if successful, false otherwise
     */
    public boolean deleteEdgeFromGephi(MyEdge e) {
        // get edge and delete it
        Edge eGephi = findEdgeToId(edgesGephi, e.getId());
        boolean result = model.getUndirectedGraph().removeEdge(eGephi);
        
        // update array
        edgesGephi = model.getUndirectedGraph().getEdges().toArray();
        return result;
    }

    // #################################################### 
    // data converting
    // ####################################################     
    /**
     * This method returns all nodes (from a temporary copy)
     * @return list of nodes
     */
    public ArrayList<MyNode> getNodes() {
        // attributes
        ArrayList<MyNode> resultList = new ArrayList<MyNode>();
        MyNode n;
        Point p;
        for (Node nodesGephi1 : nodesGephi) {
            // check node attributes
            String roomname = (String) (nodesGephi1.getAttributes().getValue(Constants.attrRoomType));
            String center = (String) (nodesGephi1.getAttributes().getValue(Constants.attrCenter));
            Integer id = nodesGephi1.getId();
            if(roomname == null || center == null) continue;
            // get node attributes
            n = new MyNode(roomname, center, id);
            String corner = (String) (nodesGephi1.getAttributes().getValue(Constants.attrCorners));
            if(corner != null && !corner.isEmpty()) {
                String[] corners = corner.split(";");
                for (String corner1 : corners) {
                    p = new Point(Integer.valueOf(corner1.split(",")[0]), Integer.valueOf(corner1.split(",")[1]));
                    n.addCorner(p);
                }
            }
            // add node
            resultList.add(n);
        }
        
        return resultList;        
    }

    /**
     * This method returns all edges (from a temporary copy)
     * @param nodes list with all nodes to create the edge attribute
     * @return the list with all edges
     */
    public ArrayList<MyEdge> getEdges(ArrayList<MyNode> nodes) {
        // attributes
        ArrayList<MyEdge> resultList = new ArrayList<MyEdge>();
        MyEdge e;
        for (Edge edgesGephi1 : edgesGephi) {
            // check edge attribute
            String connectionType = (String) (edgesGephi1.getAttributes().getValue(Constants.attrEdgeType));
            MyNode n1 = findNodeToId(nodes, edgesGephi1.getSource().getId());
            MyNode n2 = findNodeToId(nodes, edgesGephi1.getTarget().getId());
            Integer id = edgesGephi1.getId();
            if(connectionType == null || n1 == null || n2 == null) continue;
            // create edge
            e = new MyEdge(n1, n2, connectionType, id);
            // add edge
            resultList.add(e);
        }

        return resultList;           
    }
    
    // #################################################### 
    // data loading
    // ####################################################     
    /**
     * This method loads all nodes from gephi and converts them suitable for the plugin
     * @return the node list
     */
    public ArrayList<MyNode> getNodesFromGephi() {
        nodesGephi = model.getUndirectedGraph().getNodes().toArray();
        return getNodes();
    }

    /**
     * This method loads all edges from gephi and converts them suitable for the plugin
     * @param nodes List of all nodes
     * @return the edge list
     */
    public ArrayList<MyEdge> getEdgesFromGephi(ArrayList<MyNode> nodes) {
        edgesGephi = model.getUndirectedGraph().getEdges().toArray();        
        return getEdges(nodes);
    }
    
    // #################################################### 
    // helper
    // #################################################### 
    /**
     * This method finds the node to an id (Gephi nodes)
     * @param nodeArray all nodes
     * @param id node-id to be found
     * @return the node; null if not found
     */
    private static Node findNodeToId(Node[] nodeArray, int id) {
        for (Node nodeArray1 : nodeArray) {
            if (nodeArray1.getId() == id) {
                return nodeArray1;
            }
        }
        return null;
    }
    
    /**
     * This method finds a node to an id (Plugin nodes)
     * @param nodeList all nodes
     * @param id node-id to be found
     * @return  the node; null if not found
     */
    private static MyNode findNodeToId(ArrayList<MyNode> nodeList, int id) {
        for(int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i).getId() == id) return nodeList.get(i);
        }
        return null;
    }    
    /**
     * This method finds an edge that has an specific id
     * @param edgeArray list of all edges
     * @param id id to be found
     * @return  the edge; null if not found
     */
    private static Edge findEdgeToId(Edge[] edgeArray, int id) {
        for (Edge edgeArray1 : edgeArray) {
            if (edgeArray1.getId() == id) {
                return edgeArray1;
            }
        }
        return null;
    }   
}
