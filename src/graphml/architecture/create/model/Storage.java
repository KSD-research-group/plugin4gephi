package graphml.architecture.create.model;

import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import java.util.ArrayList;

/**
 * This class manages the storage of the data
 * @author Thomas Stocker
 */
public class Storage implements DataInterface {

    // #################################################### 
    // storage variables
    // #################################################### 
    private ArrayList<MyNode> nodes = new ArrayList<MyNode>();
    private ArrayList<MyEdge> edges = new ArrayList<MyEdge>();

    // #################################################### 
    // others
    // #################################################### 
    private final DataLoaderGephi loader = new DataLoaderGephi();

    /**
     * This method initializes the storage
     * @return true if successful, false otherwise
     */
    @Override
    public boolean initialize() {
        nodes.clear();
        edges.clear();
        
        return loader.initialize();
    }

    // #################################################### 
    // add & remove
    // #################################################### 
    /**
     * This method adds a node
     * @param n the node
     * @return true if successful, false otherwise
     */
    @Override
    public boolean addNode(MyNode n) {
        // add node to gephi
        boolean result = loader.addNodeToGephi(n);
        
        // get new node list from gephi
        nodes = loader.getNodes();
        
        return result;
    }

    /**
     * This method adds an edge
     * @param e the edge
     * @return true if successful, false otherwise
     */
    @Override
    public boolean addEdge(MyEdge e) {
        // add edge to gephi
        boolean result = loader.addEdgeToGephi(e);
        // get new edge list from gephi
        edges = loader.getEdges(nodes);
        
        return result;
    }

    /**
     * This method deletes a node
     * @param n the node
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deleteNode(MyNode n) {
        // delete node from Gephi
        boolean result = loader.deleteNodeFromGephi(n);
        // get new node and edge list from gephi
        nodes = loader.getNodes();
        edges = loader.getEdges(nodes);

        return result;
    }

    /**
     * This method deletes an edge
     * @param e the edge
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deleteEdge(MyEdge e) {
        // delete node from Gephi
        boolean result = loader.deleteEdgeFromGephi(e);
        // get new edge list from gephi
        edges = loader.getEdges(nodes);

        return result;
    }
    
    // #################################################### 
    // getter
    // #################################################### 
    /**
     * This method returns all nodes
     * @return list with all nodes
     */
    @Override
    public ArrayList<MyNode> getNodes() {
    	return nodes;
    }

    /**
     * This method returns all edges
     * @return list with all edges
     */
    @Override
    public ArrayList<MyEdge> getEdges() {
    	return edges;
    }

    // #################################################### 
    // connection to gephi
    // ####################################################     
    /**
     * This method loads all data from gephi
     * @return true if successful, false otherwise
     */
    @Override
    public boolean loadDataFromGephi() {
        nodes = loader.getNodesFromGephi();
        edges = loader.getEdgesFromGephi(nodes);
        return (nodes != null && edges != null);
    }
}
