/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphml.architecture.layout;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutUI;
import org.openide.util.lookup.ServiceProvider;

/**
 * This class is the base for the new layout and implements the LayoutBuilder and it opens the layout when initialized.
 * @author Thomas Stocker
 */
@ServiceProvider(service = LayoutBuilder.class)
public class Graphml implements LayoutBuilder {

    private final GraphmlLayoutUI ui = new GraphmlLayoutUI();

    /**
     * Returns the name of the Layout
     * @return name of the layout
     */
    @Override
    public String getName() {
        return "Architectural GraphML";
    }

    /**
     * Starts the layout
     * @return the layout
     */
    @Override
    public Layout buildLayout() {
        return new GraphmlLayout(this);
    }

    /**
     * Returns the UI
     * @return the UI
     */
    @Override
    public LayoutUI getUI() {
        return ui;
    }

    /**
     * This class is supposed to wrap the description of the class
     */
    private static class GraphmlLayoutUI implements LayoutUI {

        /**
         * This method returns the descrition
         * @return the description
         */
        @Override
        public String getDescription() {
            return "Layout to display the GraphML-format extended to support architectural features";
        }

        /**
         * This method returns the icon
         * @return null, because no icon is existing
         */
        @Override
        public Icon getIcon() {
            return null;
        }

        /**
         * This method returns a simple panel
         * @param layout Layout of the panel
         * @return null, because no simple panel available
         */
        @Override
        public JPanel getSimplePanel(Layout layout) {
            return null;
        }

        /**
         * This method returns the quality ranking
         * @return -1, because not available
         */
        @Override
        public int getQualityRank() {
            return -1;
        }

        /**
         * This method returns the speed ranking
         * @return -1, because not available
         */
        @Override
        public int getSpeedRank() {
            return -1;
        }
    }
}
