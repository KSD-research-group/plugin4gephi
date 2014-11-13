/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.view;

import graphml.architecture.common.Constants;
import graphml.architecture.common.Constants.Actions;
import graphml.architecture.common.MyEdge;
import graphml.architecture.common.MyNode;
import graphml.architecture.create.controller.Controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

/**
 * This class holds the image panel
 * @author Thomas
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements Subscriber {

    // attributes
    private BufferedImage image;
    private double scale = 1;
    private boolean fillRect = false;

    // current node
    private MyNode newNode;
    
    // lists with node and edges
    private ArrayList<MyEdge> edges = new ArrayList<MyEdge>();
    private ArrayList<MyNode> room = new ArrayList<MyNode>();
    
    // mouse listener
    MyMouseListener l;
    
    /**
     * Constructor
     */
    public ImagePanel(Controller controller) {
        //init
        l = new MyMouseListener(this, controller);
        this.addMouseWheelListener(new MyMouseWheelListener(this));
        this.addMouseListener(l);
        // add to publisher
        controller.addSubscriber(this);
    }
    
    /**
     * This method overrides the paintComponent method to paint the image with its nodes and corners. This method is automatically called when repainted.
     * @param g the graphics of the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // reset panel
        super.paintComponent(g);
        // reset color
        g.setColor(Color.BLACK);
        
        // draw image
        if(image != null) {
            Graphics2D g2 = (Graphics2D)g.create();

            g2.scale(scale, scale);
            g2.drawImage(image, 0, 0, this);
            g2.dispose();
        }

        // draw corners of rooms (with the corresponding number inside)
        for(int i = 0; i < room.size(); i++) {
            drawNodeCorners(g, room.get(i), new Color(0x2ECCFA), fillRect);
        }
        
        // draw edges, so that the circles can be above the edges (and the number is still readable)
        for(int i = 0; i < edges.size(); i++) {
        	// set color depending on edge type
        	for(int j = 0; j < Constants.edgeType.length; j++) {
        		if(Constants.edgeType[j].equals(edges.get(i).getEdgeType())) {
                            g.setColor(Constants.edgeTypeColor[j]);
                            break;
        		}
        	}
            g.drawLine((int)(edges.get(i).getNode1().getCenterX()*scale), (int)(edges.get(i).getNode1().getCenterY()*scale), (int)(edges.get(i).getNode2().getCenterX()*scale), (int)(edges.get(i).getNode2().getCenterY()*scale));
        }

        // draw nodes
        for(int i = 0; i < room.size(); i++) {
            drawNode(g, room.get(i), new Color(0xFE642E), true);
        }
        
        // draw newly created node if not null
        if(newNode != null) {
            drawNodeCorners(g, newNode, Color.BLUE, false);
            drawNode(g, newNode, Color.RED, false);
        }
    }
    
    /**
     * This method draws the corners of a node
     * @param g Graphics object
     * @param n the node
     * @param colorCorners the color of the corners
     * @param fillPolygon indicator if the polygon shall be filled
     */
    private void drawNodeCorners(Graphics g, MyNode n, Color colorCorners, boolean fillPolygon) {
        // draw circles for corners
        int size = (int)(10*scale);
        if(size < 10) size = 10;
        g.setColor(colorCorners);
        ArrayList<Point> corner = n.getCorners();
        for(int i = 0; i < corner.size(); i++) {
            g.fillOval((int)(corner.get(i).x*scale - 5*scale), (int)(corner.get(i).y*scale - 5*scale), size, size);
        }        

        // calculate polygon
        int[] xPoints = new int[corner.size()];
        int[] yPoints = new int[corner.size()];
        for(int i = 0; i < corner.size(); i++) {
            xPoints[i] = (int)(corner.get(i).getX()*scale);
            yPoints[i] = (int)(corner.get(i).getY()*scale);
        }

        // draw polygon
        if(fillPolygon == false) {
            g.drawPolygon(xPoints, yPoints, corner.size());
        }
        else {
            g.fillPolygon(xPoints, yPoints, corner.size());
        }
    }

    /**
     * This method draws a node
     * @param g Grapics-Object
     * @param n the node
     * @param colorNode the color of the node
     * @param displayText indicator if the id shall be displayed
     */
    private void drawNode(Graphics g, MyNode n, Color colorNode, boolean displayText) {
        // determine size
        int size = (int)(20*scale);
        if(size < 20) size = 20;

        // draw oval
        g.setColor(colorNode);
        g.fillOval((int)((n.getCenterX() - 10)*scale), (int)((n.getCenterY() - 10)*scale), size, size);

        // draw string if wished
        if(displayText == true) {
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(n.getId()), (int)((n.getCenterX() - 3)*scale), (int)((n.getCenterY() + 5)*scale));        
        }
    }
	
    /**
    * This method sets the image
    * @param image the image
    */
   public void setImage(BufferedImage image) {
       this.image = image;
       setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
       repaint();
   }
    
    /**
     * This method adds the currently added node to be painted 
     * @param n the new node
     */
    public void setCurrentNode(MyNode n) {
        // set cursor
    	newNode = n;
    	repaint();
    }
    
    /**
     * Method to zoom into the image
     * @param direction direction of the mouse wheel
     */
    public void zoom(boolean direction) {
        if(image != null) {
            // set scale with factor 0.1 in the corresponding direction
            scale = direction == true ? scale + 0.1 : scale - 0.1;
            // make sure, that it never zooms too small
            if(scale < 0.5) scale = 0.5;
                
            int w = (int)(scale * image.getWidth());  
            int h = (int)(scale * image.getHeight());  
            setPreferredSize(new Dimension(w, h));
            revalidate();
            repaint();
    	}
    }

    /**
     * Setter of room-list
     * @param room Room-list
     */
    @Override
    public void updateNodes(ArrayList<MyNode> room) {
        this.room = room;
        repaint();
    }
    
    /**
     * Setter of edge-list
     * @param edges Edge-list
     */
    @Override
    public void updateEdges(ArrayList<MyEdge> edges) {
        this.edges = edges;
        repaint();
    }

    /**
     * Gets the current scaling factor
     * @return current scaling factor
     */
    public double getScale() {
        return scale;
    }
    
    /**
     * This method sets the cursor of this panel
     * @param cursorType the type the cursor should have
     */
    public void setCursor(Constants.Cursor cursorType) {
        URL url;
        
        switch(cursorType) {
            case ADDNODE: 
                url = Constants.urlAddNode;
                break;
            case ADDCORNER:
                url = Constants.urlAddCorner;
                break;
            case STARTEDGE:
                url = Constants.urlStartEdge;
                break;
            case FINISHEDGE:
                url = Constants.urlFinishEdge;
                break;
            case DEFAULTCURSOR:
                // set default cursor
                this.setCursor(Cursor.getDefaultCursor());
                return;
            default:
                return;
        }
        
        try {
            BufferedImage myImage;
            myImage = ImageIO.read(url);
            Point hotSpot = new Point(15, 15);
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(myImage, hotSpot, "MyCursor");
            this.setCursor(cursor);
        } catch (IOException e) {
           // e.printStackTrace();
        } 
    }
    
    /**
     * This method sets the action
     * @param myAction the action type
     */
    @Override
    public void setAction(Actions myAction) {
        l.setAction(myAction);

        // switch action according to action
        switch(myAction) {
            case ADDNODESTART:
                setCursor(Constants.Cursor.ADDNODE);
                break;
            case ADDNODEEND:
                setCurrentNode(null);
                setCursor(Constants.Cursor.DEFAULTCURSOR);
                break;
            case ADDEDGESTART:
                setCursor(Constants.Cursor.STARTEDGE);
                break;
            case ABORT:
                setCurrentNode(null);
                setCursor(Constants.Cursor.DEFAULTCURSOR);
                break;
            default:
                setCursor(Constants.Cursor.DEFAULTCURSOR);
                break;
        }
    }

    /**
     * This method returns the current node
     * @return the currently edited node
     */
    public MyNode getCurrentNode() {
        return newNode;
    }

    /**
     * This method handles the event, if the checkbox to fill the rectangle has been changed and updates the view
     * @param checked indicator wether the checkbox is checked or not
     */
    public void checkBoxChanged(boolean checked) {
        fillRect = checked;
        this.repaint();
    }
}