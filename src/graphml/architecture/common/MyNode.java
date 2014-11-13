package graphml.architecture.common;

import java.awt.Point;
import java.util.ArrayList;

/**
 * This class represents a node, that will exported to gephi
 * @author Thomas Stocker
 */
public class MyNode {
    // attributes
    private final String roomType;
    private final long centerX;
    private final long centerY;
    private final int id;
    private final ArrayList<Point> corners;

    /**
     * The constructor
     * @param roomType the room type of the node
     * @param centerX the x-coordinate of the node
     * @param centerY the y-coordinate of the node
     * @param id the id of the node
     */
    public MyNode(String roomType, long centerX, long centerY, int id) {
        // initialize
        this.corners = new ArrayList<Point>();
        
        // set values
        this.roomType = roomType;
        this.centerX = centerX;
        this.centerY = centerY;
        this.id = id;
    }
    
    /**
     * Constructor with center as string
     * @param roomType the room type of the node
     * @param center the center coordinate as string
     * @param id the id of the node
     */
    public MyNode(String roomType, String center, int id) {
        // call constructor with seperated center variable
        this(roomType, Long.parseLong(center.split(",")[0]), Long.parseLong(center.split(",")[1]), id);
    }

    /**
     * This method adds a new corner to the node
     * @param p the new corner
     */
    public void addCorner(Point p) {
        corners.add(p);
    }

    /**
     * This method returns the room type
     * @return the room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * This method returns the X-coordinate of the node
     * @return the X-coordinate
     */
    public long getCenterX() {
        return centerX;
    }

    /**
     * This method returns the Y-coordinate of the node
     * @return the Y-coordinate
     */
    public long getCenterY() {
        return centerY;
    }
    
    /**
     * This method returns the X- and Y-Coordinate as string
     * @return the center coordinate as string, seperated by a ','
     */
    public String getCenter() {
        return centerX + "," + centerY;
    }

    /**
     * This method returns the ArrayList with all corners
     * @return List with all corners of the node
     */
    public ArrayList<Point> getCorners() {
        return corners;
    }

    /**
     * This method returns the node id
     * @return the node id
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns the node id and its room type as String and overrides the "toString()" method.
     * @return The following String is returned: "Room #ID (#ROOMTYPE)"
     */
    @Override
    public String toString() {
        return "Room " + id + " (" + roomType + ")";
    }

    /**
     * This method checks, if corners are available or if the corner list is empty
     * @return true if there are corners available, false if the list is empty
     */
    public boolean checkCornersAvailable() {
        return !corners.isEmpty();
    }

    /**
     * This methods transforms the corners of a node as string (to be stored)
     * @return the string with all corners (x- and y-coordinate seperated by ","; corners seperated by ";")
     */
    public String getCornersAsString() {
        String s = "";
        
        // go through corners
        for(int i = 0; i < corners.size(); i++) {
            s += corners.get(i).x + "," + corners.get(i).y;
            if(i != corners.size() - 1) {
                // not last element, so add semicolon
                s += ";";
            }
        }
        return s;
    }
    
    /**
     * This method splits the center coordinate to retrieve x
     * @param s the center coordinate
     * @return the x coordinate
     */
    public static Long getCenterCoordinateX(String s) {
        return Long.parseLong(s.split(",")[0]);
    }

    /**
     * This method splits the center coordinate to retrieve y
     * @param s the center coordinate
     * @return the y coordinate
     */
    public static Long getCenterCoordinateY(String s) {
        return Long.parseLong(s.split(",")[1]);
    }

    
}
