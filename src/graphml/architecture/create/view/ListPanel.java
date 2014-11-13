package graphml.architecture.create.view;

import graphml.architecture.common.Constants;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.controller.Controller;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
/**
 * This class holds the list panel
 * @author Thomas Stocker
 */
@SuppressWarnings("serial")
public class ListPanel extends JPanel implements Subscriber {
    
    // controller pointer
    Controller controller;

    // two lists
    private final DefaultListModel<MyNode> nodeListModel = new DefaultListModel<MyNode>();
    private final JList<MyNode> nodeList  = new JList<MyNode>(nodeListModel);;
    private final DefaultListModel<MyEdge> edgeListModel = new DefaultListModel<MyEdge>();
    private final JList<MyEdge> edgeList  = new JList<MyEdge>(edgeListModel);;

    // delete-buttons at bottom
    private final Button bNode = new Button("Remove Node");
    private final Button bEdge = new Button("Remove Edge");

    /**
     * Constructor
     */
    public ListPanel(Controller controller) {
        // init
        super();
        this.controller = controller;
        
        // Panel
        JPanel pLeft = new JPanel(new BorderLayout());
        JPanel pRight = new JPanel(new BorderLayout());
        
        // set size
        this.setSize(new Dimension(310, 150));

        // Fill panel left
        nodeList.setBorder(BorderFactory.createLineBorder(Color.black));
        nodeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        nodeList.setVisibleRowCount(30);
        nodeList.setFixedCellWidth(150);
        JScrollPane scrollPane1 = new JScrollPane(nodeList);
        pLeft.add(scrollPane1, BorderLayout.CENTER);
        bNode.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent evt) { removeNode(); }});
        pLeft.add(bNode, BorderLayout.PAGE_END);
        
        // Fill panel right
        edgeList.setBorder(BorderFactory.createLineBorder(Color.black));
        edgeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        edgeList.setVisibleRowCount(30);
        edgeList.setFixedCellWidth(150);
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setViewportView(edgeList);
        pRight.add(scrollPane2, BorderLayout.CENTER);
        bEdge.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent evt) { removeEdge(); }});
        pRight.add(bEdge, BorderLayout.PAGE_END);

        // add panel
        this.add(pLeft);
        this.add(pRight);
        
        // add to publisher
        controller.addSubscriber(this);
    }

    /**
     * This method starts the removal of a node
     */
    private void removeNode() {
        // remove selected nodes
        MyNode n = nodeList.getSelectedValue();
        controller.deleteNode(n);
    }
    
    /**
     * This method starts the removal of an edge
     */
    private void removeEdge() {
        // remove selected edges
        MyEdge e = edgeList.getSelectedValue();
        controller.deleteEdge(e);
    }

    /**
     * This method updates the node lists, if new data is available
     */
    @Override
    public void updateNodes(ArrayList<MyNode> n) {
    	// update nodes
    	nodeListModel.clear();
    	for(int i = 0; i < n.size(); i++) {
            nodeListModel.addElement(n.get(i));
    	}
    }
    
    /**
     * This method updates the edge lists, if new data is available
     */
    @Override
    public void updateEdges(ArrayList<MyEdge> e) {
    	// update edges
    	edgeListModel.clear();
    	for(int i = 0; i < e.size(); i++) {
            edgeListModel.addElement(e.get(i));
    	}
    }	

    /**
     * This method from the Publisher-Subscriber-Pattern is not used
     * @param myAction the action-id
     */
    @Override
    public void setAction(Constants.Actions myAction) {
        // do nothing
    }
}