/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    
    private final int width = 50;
    private final int height = 44;
    private int x;
    private int y;
    private ImageIcon imgSprite;
    private Node currNode;
    private JPanel agentSprite;
    private JPanel currPanel;
    private boolean active;
    private ArrayList<Node> nodeList;
    
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
        this.nodeList = new ArrayList<>();
        this.active = false;
        
        //Load agent's sprite image from file
        try {
            BufferedImage img = ImageIO.read(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\AStarAI\\agent2.png"));
            this.imgSprite = new ImageIcon(img);
        } catch (IOException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Initialize agent's JPanel sprite
        this.agentSprite = new JPanel();
        this.currPanel = new JPanel();
        agentSprite.setSize(width,height);
        JLabel sprtImg = new JLabel(imgSprite);
        agentSprite.add(sprtImg);
    }
    
    /***Agent's movement actuators and decision making***/
    
    /**
     * Move the agent up 1 panel
     */
    public void moveUp()
    {
        //Set currPanel to the panel above it
        this.currPanel.removeAll();
        this.currPanel = nodeList.get(currNode.getId() - 1).getPanel();
        this.currNode = nodeList.get(currNode.getId() - 1);
    }
    
    /**
     * Move the agent down 1 panel
     */
    public void moveDown()
    {
        //Set currPanel to the panel below it
        this.currPanel.removeAll();
        this.currPanel = nodeList.get(currNode.getId() + 1).getPanel();
        this.currNode = nodeList.get(currNode.getId() + 1);
    }
    
    /**
     * Move agent left 1 panel
     */
    public void moveLeft()
    {
        //Set currPanel to the panel left of it
        this.currPanel.removeAll();
        this.currPanel = nodeList.get(currNode.getId() - 15).getPanel();
        this.currNode = nodeList.get(currNode.getId() - 15);
    }
    
    /**
     * Move agent right 1 panel
     */
    public void moveRight()
    {
        //Set currPanel to the panel right of it
        this.currPanel.removeAll();
        this.currPanel = nodeList.get(currNode.getId() + 15).getPanel();
        this.currNode = nodeList.get(currNode.getId() + 15);
    }
    
    /***Getters and Setters***/
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

    public JPanel getAgentSprite() {
        return agentSprite;
    }

    public JPanel getCurrPanel() {
        return currPanel;
    }

    public void setCurrPanel(JPanel currPanel) {
        this.currPanel = currPanel;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
