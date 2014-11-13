/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphml.architecture.create.controller;

import graphml.architecture.create.model.MessageHandler;
import graphml.architecture.create.view.ImagePanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.min;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class represents the (static) loading of an image
 * @author Thomas Stocker
 */
public class ImageLoader {
    
    /**
     * This method loads an image
     * @param f Pointer to MainFrame (for messages)
     * @param imagePanel Pointer to the imagePanel to store the image
     * @param messages Pointer to the MessageHandler to publish messages
     */
    public static void loadImage(JFrame f, ImagePanel imagePanel, MessageHandler messages) {
        // load image
        MessageHandler.showWizzardMessage(f, "Please load the image that shall be captured.");
        
        BufferedImage img = loadImageFromFile(f);
        // check image
        while(img == null) {
            JOptionPane.showMessageDialog(f, "File could not be loaded. Please try again.", "Error loading file", JOptionPane.ERROR_MESSAGE);
            img = loadImageFromFile(f);
        }
        imagePanel.setImage(img);
        
        // resize mainFrame
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize((int)(min(screensize.getWidth() - 10, img.getWidth() + 350)), (int)(min(screensize.getHeight() - 50, img.getHeight() + 100)));

        // set tooltip
        messages.setTooltip("Start capturing of first room via the menu");
    }

    /**
     * This method actually loads the image
     * @param comp Component to display messages
     * @return BufferedImage
     */
    private static BufferedImage loadImageFromFile(Component comp) {
        String path = "";
        // get path
        while(path.equals("")) {
            path = getPath(comp);
        }

        BufferedImage myImage = null;
        
        // load image
        File f = new File(path);
        try {
            myImage = ImageIO.read(f);
        } catch (IOException ex) {
            // do nothing
        }
        
        return myImage;
    }    

    /**
     * This method returns the file path
     * @param f the component for messages
     * @return the file path; method exits, if nothing is chosen
     */
    private static String getPath(Component f) {
        // get path
        String path = "";
        JFileChooser chooser = new JFileChooser();
        // load image, in case "OK" has been clicked
        if(chooser.showDialog(f, "Load image") == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            // cancel clicked
            System.exit(0);
        }

        // loadImage
        return path;
    }

}
