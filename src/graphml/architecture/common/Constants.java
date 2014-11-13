package graphml.architecture.common;

import java.awt.Color;
import java.net.URL;
import org.gephi.data.attributes.api.AttributeType;

/**
 * This class contains all constants so that they can be changed easily
 * @author Thomas Stocker
 */
public class Constants {
    /**
     * Listing of different possible edge types according to AGraphML specification
     */
    public static final String[] edgeType = {"DOOR", "ENTRANCE", "PASSAGE", "SLAB", "STAIRS", "WALL", "WINDOW"};
    /**
     * Definition of the color of the edge types (order corresponding to edgeType), for colorize template
     */
    public static final Color[] edgeTypeColor = {Color.YELLOW, Color.YELLOW, Color.GREEN, Color.RED, Color.YELLOW, Color.RED, Color.YELLOW};
    /**
     * Definition of the weight of the edge types (order corresponding to edgeType), for colorize template
     */
    public static final Double[] edgeTypeWeight = {0.5, 0.5, 1.0, 0.1, 0.75, 0.01, 0.5};
    /**
     * Listing of different possible room types according to AGraphML specification
     */
    public static final String[] roomType = {"ROOM", "KITCHEN", "LIVING", "SLEEPING", "WORKING", "CORRIDOR", "TOILET", "BATH", "EXTERIOR", "STORAGE", "BUILDINGSERVICES", "CHILDREN", "PARKING"};
    /**
     * The two different layout modes
     */
    public static final String[] rows = {"Editing mode", "Groundplan mode"};
    
    /**
     * Attribute name definition for ID
     */
    public static final String attrId = "Id";
    /**
     * Attribute name definition for roomType
     */
    public static final String attrRoomType = "roomType";
    /**
     * Attribute name definition for name
     */
    public static final String attrName = "name";
    /**
     * Attribute name definition for center
     */
    public static final String attrCenter = "center";
    /**
     * Attribute name definition for corners
     */
    public static final String attrCorners = "corners";
    /**
     * Attribute name definition for attribute if window exists
     */
    public static final String attrWindowExist = "windowExist";
    /**
     * Attribute name definition for enclosed room
     */
    public static final String attrEnclosedRoom = "enclosedRoom";
    /**
     * Attribute name definition for are
     */
    public static final String attrArea = "area";
    /**
     * Attribute name definition for light
     */
    public static final String attrLight = "light";
    /**
     * Attribute name definition for privacy
     */
    public static final String attrPrivacy = "privacy";
    /**
     * Attribute name definition for zone
     */
    public static final String attrZone = "zone";
    /**
     * Attribute name definition for corner node
     */
    public static final String attrIsCornerNode = "isCornerNode";
    
    /**
     * Listing of all new node columns that will be added
     */
    public static final String[] newColumnsNode = {attrId, attrRoomType, attrName, attrCenter, attrCorners, attrWindowExist, attrEnclosedRoom, attrArea, attrLight, attrPrivacy, attrZone, attrIsCornerNode};
    /**
     * Listing of the type of the new columns
     */
    public static final AttributeType[] newColumnsNodeType = {AttributeType.STRING, AttributeType.STRING, AttributeType.STRING, AttributeType.STRING, AttributeType.STRING, AttributeType.BOOLEAN, AttributeType.BOOLEAN, AttributeType.FLOAT, AttributeType.INT, AttributeType.INT, AttributeType.STRING, AttributeType.BOOLEAN};
    
    /**
     * Attribute name definition for edge type
     */
    public static final String attrEdgeType = "edgeType";
    /**
     * Attribute name definition for weight
     */
    public static final String attrWeight = "weight";
    /**
     * Attribute name definition for linear distance
     */
    public static final String attrLinearDistance = "linearDistance";
    /**
     * Attribute name definition for position
     */
    public static final String attrPosition = "position";
    /**
     * Attribute name definition for walking distance
     */
    public static final String attrWalkingDistance = "walkingDistance";
    /**
     * Attribute name definition for felt distance
     */
    public static final String attrFeltDistance = "feltDistance";
    /**
     * Attribute name definition for view relation
     */
    public static final String attrViewRelation = "viewRelation";

    /**
     * Listing of all new edge columns that will be added
     */
    public static final String[] newColumnsEdge = {attrId, attrEdgeType, attrWeight, attrLinearDistance, attrPosition, attrWalkingDistance, attrFeltDistance, attrViewRelation};
    /**
     * Type of those newcolumns
     */
    public static final AttributeType[] newColumnsEdgeType = {AttributeType.STRING, AttributeType.STRING, AttributeType.FLOAT, AttributeType.FLOAT, AttributeType.STRING, AttributeType.FLOAT, AttributeType.FLOAT, AttributeType.INT};
    
    /**
     * Different states of the cursor
     */    
    public static enum Cursor {ADDNODE, ADDCORNER, DEFAULTCURSOR, STARTEDGE, FINISHEDGE};
    /**
     * URL of the cursor for adding a node
     */
    public static final URL urlAddNode = Constants.class.getClassLoader().getResource("graphml/architecture/images/cursorAddNodeCenter.png");
    /**
     * URL of the cursor for adding a corner
     */
    public static final URL urlAddCorner = Constants.class.getClassLoader().getResource("graphml/architecture/images/cursorAddCorner.png");
    /**
     * URL of the cursor for starting an edge
     */
    public static final URL urlStartEdge = Constants.class.getClassLoader().getResource("graphml/architecture/images/cursorStartEdge.png");
    /**
     * URL of the cursor for finishing an edge
     */
    public static final URL urlFinishEdge = Constants.class.getClassLoader().getResource("graphml/architecture/images/cursorFinishEdge.png");
    
    /**
     * Listing of all possible actions to communicate with the UIs
     */
    public static enum Actions {ADDNODESTART, ADDNODEEND, ADDEDGESTART, ADDEDGEEND, ABORT};
}