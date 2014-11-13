package graphml.architecture.common;

/**
 * This class represents an edge between two nodes that will be exported to Gephi
 * @author Thomas Stocker
 */
public class MyEdge {	
    // attributes
    private final MyNode node1;
    private final MyNode node2;
    private final String edgeType;
    private final int edgeId;

    /**
     * The constructor
     * @param node1 the first node of the edge
     * @param node2 the second node of the edge
     * @param edgeType the type of the edge
     * @param id id of the edge
     */
    public MyEdge(MyNode node1, MyNode node2, String edgeType, int id) {
        // set values
        this.node1 = node1;
        this.node2 = node2;
        this.edgeType = edgeType;
        this.edgeId = id;
    }

    /**
     * This method returns the first node of the edge
     * @return the first node
     */
    public MyNode getNode1() {
        return node1;
    }

    /**
     * This method returns the second node of the edge
     * @return the second node
     */
    public MyNode getNode2() {
        return node2;
    }

    /**
     * This method returns the type of the edge
     * @return the type of the edge
     */
    public String getEdgeType() {
        return edgeType;
    }
    
    /**
     * This method returns the corresponding two nodes and the edge type as string and overrides the "toString()" method
     * @return The following String is returned: "Edge from #ID to #ID (#EDGETYPE)"
     */
    @Override
    public String toString() {
        return node1.getId() + "-" + node2.getId() + " (" + edgeType + ")";
    }

    /**
     * This method returns the id of the edge
     * @return id of the edge
     */
    public int getId() {
        return edgeId;
    }
}