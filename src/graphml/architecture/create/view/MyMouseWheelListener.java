package graphml.architecture.create.view;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * This mouse wheel listener overwrites the zooming functionality
 * @author Thomas Stocker
 */
public class MyMouseWheelListener implements MouseWheelListener {
	
    // attributes
    ImagePanel imagePanel;
	
    /**
     * Constructor
     * @param iPanel imagePanel to zoom in
     */
    public MyMouseWheelListener(ImagePanel iPanel) {
        this.imagePanel = iPanel;
    }

    /**
     * This method overrides the mousewheelMoved-Method to zoom into the image
     * @param e MouseWheelEvent to get the rotation count
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // forward mouse wheel rotation
        int steps = e.getWheelRotation();
        imagePanel.zoom(steps < 0);
    }
}
