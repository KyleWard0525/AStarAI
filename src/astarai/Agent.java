/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the implementation of the A* algorithm
 * @author kward60
 */
public class Agent {
    
    private final int width = 40;
    private final int height = 40;
    private int x;
    private int y;
    private ImageIcon imgSprite;
    private Node currNode;
    private JPanel agentPanel;
    
    
    public Agent(Node node)
    {
        this.currNode = node;
        init();
    }
    
    /**
     * Initialize variables
     */
    public void init()
    {
        this.x = currNode.getX();
        this.y = currNode.getY();  
        
        //Load agent's sprite image from file
        try {
            BufferedImage img = ImageIO.read(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\AStarAI\\agent.png"));
            this.imgSprite = new ImageIcon(img);
        } catch (IOException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Initialize agent's JPanel sprite
        this.agentPanel = new JPanel();
        agentPanel.setSize(40,40);
        JLabel sprtImg = new JLabel(imgSprite);
        agentPanel.add(sprtImg);
    }
    
    //Getters and Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Node getCurrNode() {
        return currNode;
    }

    public void setCurrNode(Node currNode) {
        this.currNode = currNode;
    }

    public JPanel getAgentPanel() {
        return agentPanel;
    }
    
    
}
