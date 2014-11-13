/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphml.architecture.layout;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyNode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.Attributes;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.layout.plugin.AbstractLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
import org.gephi.visualization.VizController;
import org.gephi.visualization.VizModel;
import org.openide.util.Lookup;

/**
 * This class contains the layout
 * @author Thomas Stocker
 */
public class GraphmlLayout extends AbstractLayout implements Layout {

    private Graph graph;
    private boolean converged;
    private String visualization = Constants.rows[0];
    private boolean error = false;
    
    private Float[][] restorePoints;
    
    /**
     * Constructor
     * @param layoutBuilder the layout builder so that he can be retrieved
     */
    public GraphmlLayout(LayoutBuilder layoutBuilder) {
        // Attention: as in Gephi the Y-Coordinate goes from bottom to top, but the coordinate in the graphml format from top to bottom, a minus has to be added here
    
        super(layoutBuilder);
    }


    
    /**
     * Overridden method to initialize the algorithm
     */
    @Override
    public void initAlgo() {
        converged = false;
        
        // get graph and nodes
        graph = graphModel.getUndirectedGraph();
        Node[] nodes = graph.getNodes().toArray();
        
        // check attributes
        AttributeModel attrModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        AttributeColumn[] columnsNodes = attrModel.getNodeTable().getColumns();
        AttributeColumn[] columnsEdges = attrModel.getEdgeTable().getColumns();
    
        if(!exists(Constants.attrRoomType, columnsNodes) || !exists(Constants.attrCenter, columnsNodes) || !exists(Constants.attrCorners, columnsNodes) || !exists(Constants.attrEdgeType, columnsEdges)) {
            JOptionPane.showMessageDialog(null, "Necessary columns not available.", "Error initializing layout", JOptionPane.ERROR_MESSAGE);
            converged = true;
            error = true;
            return;
        }
        
        // delete corner nodes
        deleteCornerNodes(nodes);
        
        // color edges
        Edge[] edges = graph.getEdges().toArray();
        
        String edgeType;
        for(Edge e : edges) {
            edgeType = (String)(e.getAttributes().getValue(Constants.attrEdgeType));
            if(edgeType == null) continue;
            for(int i = 0; i < Constants.edgeType.length; i++) {
                if(edgeType.equals(Constants.edgeType[i])) {
                    e.getEdgeData().setColor(Constants.edgeTypeColor[i].getRed(), Constants.edgeTypeColor[i].getGreen(), Constants.edgeTypeColor[i].getBlue());
                    break;
                }
            }
        }
        
        // set node names
        Attributes a;
        for(Node n : nodes) {
            a = n.getAttributes();
            a.setValue(Constants.attrRoomType, a.getValue(Constants.attrId) + " - " + a.getValue(Constants.attrRoomType));
        }
        
        // Show the labels of the nodes
        VizModel vizModel = VizController.getInstance().getVizModel();
        vizModel.getTextModel().setShowNodeLabels(true);
        vizModel.getTextModel().setShowEdgeLabels(true);

        // declare the columns that shall be displayed
        AttributeColumn[] c1 = {attrModel.getNodeTable().getColumn(Constants.attrRoomType)};
        AttributeColumn[] c2 = {attrModel.getEdgeTable().getColumn(Constants.attrEdgeType) };
        vizModel.getTextModel().setTextColumns(c1, c2);
        
        // ####################################################################
        // Editing Mode
        // ####################################################################
        if(visualization.equals(Constants.rows[0])) {
            // attributes
            Attributes attr;
                        
            // get restore points
            restorePoints = new Float[nodes.length][3];
            for(int i = 0; i < nodes.length; i++) {
                restorePoints[i][0] = Float.valueOf(nodes[i].getId());
                restorePoints[i][1] = nodes[i].getNodeData().x();
                restorePoints[i][2] = nodes[i].getNodeData().y();
            }
            
            // set node positions according to attribute
            for(Node n : nodes) {
                attr = n.getAttributes();
                
                // skip if attribute not set
                if(attr.getValue(Constants.attrCenter) == null) continue;
                
                n.getNodeData().setX(Float.valueOf(MyNode.getCenterCoordinateX((String)(attr.getValue(Constants.attrCenter)))));
                n.getNodeData().setY(-Float.valueOf(MyNode.getCenterCoordinateY((String)(attr.getValue(Constants.attrCenter)))));
            }
            
            // add relative nodes
            addRelativeCorners(nodes, graph);
        }
        // ####################################################################
        // Groundplan Mode
        // ####################################################################
        else if(visualization.equals(Constants.rows[1])) {
            // add relative nodes
            addRelativeCorners(nodes, graph);
        }
    }

    /**
     * Overriden method to perform layout
     */
    @Override
    public void goAlgo() {
        // ####################################################################
        // Editing Mode
        // ####################################################################
        if(visualization.equals(Constants.rows[0])) {
            // do nothing here
        }
        // ####################################################################
        // Groundplan Mode
        // ####################################################################
        else if(visualization.equals(Constants.rows[1])) {
            // do nothing here
        }
        // sleep a bit
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            // nothing
        }
    }

    /**
     * Method to check, if the algo can be executed
     * @return indicator, if algo can be executed
     */
    @Override
    public boolean canAlgo() {
        return !converged;
    }

    /**
     * Overridden method that is executed, when the algorithm ends
     */
    @Override
    public void endAlgo() {
        if(error == true) return;
        // get graph and nodes
        graph = graphModel.getUndirectedGraph();
        Node[] nodes = graph.getNodes().toArray();

        // reset label
        Attributes a;
        for(Node n : nodes) {
            a = n.getAttributes();
            
            // check on null pointer
            if(a.getValue(Constants.attrId) == null || a.getValue(Constants.attrRoomType) == null)
                continue;
            
            // determine size of id
            int length = (a.getValue(Constants.attrId)).toString().length();
            
            a.setValue(Constants.attrRoomType, a.getValue(Constants.attrRoomType).toString().substring(length + 3));
        }
        
        // ####################################################################
        // Editing Mode
        // ####################################################################
        if(visualization.equals(Constants.rows[0])) {
            deleteCornerNodes(nodes);
            
            // reset position
            nodes = graph.getNodes().toArray();
            /*
            if(nodes.length != restorePoints.length) {
                JOptionPane.showMessageDialog(null, "Node added or deleted. Original state cannot be obtained.", "Error recovering", JOptionPane.ERROR_MESSAGE);
                converged = true;
                return;
            }
            */
            int position;
            for (Node node : nodes) {
                position = findNode(restorePoints, node.getId());
                node.getNodeData().setX(restorePoints[position][1]);
                node.getNodeData().setY(restorePoints[position][2]);
            }
        }
        // ####################################################################
        // Groundplan Mode
        // ####################################################################
        else if(visualization.equals(Constants.rows[1])) {
            // delete created nodes again
            for(Node n : nodes) {
                if(n.getAttributes().getValue(Constants.attrRoomType) == null) continue;
                
                if(n.getAttributes().getValue(Constants.attrIsCornerNode).equals(true)) {
                    graph.removeNode(n);
                }
            }
        }        
        // reset values
        converged = true;
    }

    /**
     * This method sets the layout-properties (like values etc)
     * @return the list with all layout properties
     */
    @Override
    public LayoutProperty[] getProperties() {
        // Currently not implemented
        List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        
        final String GEOLAYOUT = "Geo Layout";

        try {
            properties.add(LayoutProperty.createProperty(
                    this, String.class, "Visualization Method:", GEOLAYOUT,
                    "The mode that can be chosen",
                    "getMethod", "setMethod", CustomComboBoxEditor.class));
        } catch (NoSuchMethodException e) {
        }

        return properties.toArray(new LayoutProperty[0]);
    }

    /**
     * This method returns the name of the selected visualization method
     * @return the name of the method
     */
    public String getMethod() {
        return visualization;
    }
    
    /**
     * This method sets the name of the visualization method
     * @param visualization the name of the method
     */
    public void setMethod(String visualization) {
        this.visualization = visualization;
    }

    /**
     * This method should reset the properties values (not necessary)
     */
    @Override
    public void resetPropertiesValues() {
    }

    /**
     * This method adds corner nodes relatively to the center of a node
     * @param nodes a list of all nodes
     * @param graph the graph where to add the corner nodes
     */
    private void addRelativeCorners(Node[] nodes, Graph graph) {
        // attributes
        String[] splitEdgeNodes;
        Attributes attr;
        Float x, y;
        Node node;
        Edge e;
        Node[] cornerNodes;

        // take nodes, that are middles and paint corner nodes around
        for(Node n : nodes) {
            // get attributes
            attr = n.getAttributes();

            // continue, if no corner attribute available
            if(attr.getValue(Constants.attrCorners) == null || ((String)(attr.getValue(Constants.attrCorners))).isEmpty()) continue;
            if(attr.getValue(Constants.attrCenter) == null) continue;

            // get edge values
            splitEdgeNodes = ((String) attr.getValue(Constants.attrCorners)).split(";");
            cornerNodes = new Node[splitEdgeNodes.length];
            // seperate by comma and add nodes
            for(int i = 0; i < splitEdgeNodes.length; i++) {
                // calculate relative values
                x = Float.valueOf(splitEdgeNodes[i].split(",")[0]) - MyNode.getCenterCoordinateX((String)(attr.getValue(Constants.attrCenter)));
                y = Float.valueOf(splitEdgeNodes[i].split(",")[1]) - MyNode.getCenterCoordinateY((String)(attr.getValue(Constants.attrCenter)));

                node = graphModel.factory().newNode();
                node.getNodeData().setSize(5);
                node.getNodeData().setX(x + n.getNodeData().x());
                node.getNodeData().setY(-y + n.getNodeData().y());
                node.getNodeData().setFixed(true);
                node.getAttributes().setValue(Constants.attrIsCornerNode, true);
                graph.addNode(node);
                cornerNodes[i] = node;
            }

            // Add edges between corners
            for(int i = 0; i < cornerNodes.length; i++) {
                if(i < cornerNodes.length - 1) {
                    // make edge from node n-1 to node n
                    e = graphModel.factory().newEdge(cornerNodes[i], cornerNodes[i+1], 1, false);
                }
                else {
                    // make edge from last to first
                    e = graphModel.factory().newEdge(cornerNodes[i], cornerNodes[0], 1, false);
                }
                e.setWeight((float) 0.01);
                graph.addEdge(e);
            }
        }
    }

    /**
     * This method checks, if the given string exists in all columns
     * @param s string to be checked
     * @param columns columns where to check
     * @return true if existing as column, false if not
     */
    private static boolean exists(String s, AttributeColumn[] columns) {
        for (AttributeColumn column : columns) {
            if (s.equals(column.getTitle())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the position of an id in an array with predefined structure
     * @param restorePoints the array
     * @param id the id
     * @return the position (-1 if not found)
     */
    private static int findNode(Float[][] restorePoints, int id) {
        for(int i = 0; i < restorePoints.length; i++) {
            if(restorePoints[i][0] == id) return i;
        }
        return -1;
    }

    /**
     * This method deletes all nodes, that have as roomtype the string of a corner node
     * @param nodes array of all nodes
     */
    private void deleteCornerNodes(Node[] nodes) {
        // delete created nodes again
        for(Node n : nodes) {
            if(n.getAttributes().getValue(Constants.attrIsCornerNode) == null) continue;

            if(n.getAttributes().getValue(Constants.attrIsCornerNode).equals(true)) {
                graph.removeNode(n);
            }
        }
    }
}
