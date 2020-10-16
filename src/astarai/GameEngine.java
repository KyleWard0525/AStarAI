/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import astarai.ui.GameWindow;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import utils.GameExceptions.SpriteException;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * This is the main engine that controls the GUI elements along with running the
 * A* search algorithm
 *
 * @author kward60
 */
public class GameEngine {

    private int xSize;
    private int ySize;
    private GameWindow gw;
    private AStar algo;
    private ArrayList<Node> nodes;
    private int mapWidth;
    private int mapHeight;
    private int nodeWidth;
    private int nodeHeight;
    private int nodeCount;
    private Node[][] nodeMap;
    private JPanel gamePanel;
    private double blockChance;
    private Random rand;
    private boolean collision;
    private int collisionCount;

    /**
     * Main Constructor
     *
     * @param gw - Game window
     * @param x - number of rows
     * @param y - number of columns
     */
    public GameEngine(GameWindow gw, int x, int y) {
        this.gw = gw;
        this.xSize = x;
        this.ySize = y;
        init();
    }

    /**
     * Initialize variables and node map
     */
    public void init() {
        this.nodes = new ArrayList<Node>(xSize * ySize);
        this.gamePanel = gw.getGamePanel();
        this.mapWidth = gamePanel.getWidth();
        this.mapHeight = gamePanel.getHeight();
        this.nodeMap = new Node[xSize][ySize];
        this.blockChance = 0.1;
        this.rand = new Random();
        this.nodeCount = xSize * ySize;
        this.collision = false;
        this.collisionCount = 0;

        //Randomize node map
        randomizeMap();
        
        //Draw map
        drawMap();
    }

    /**
     * Randomize the node positions in the map
     */
    private void randomizeMap() {
        //Initialize node and map variables
        this.nodeWidth = mapWidth/xSize;
        this.nodeHeight = mapHeight/ySize;

        //Spawn restraints for nodes
        int xMin = nodeWidth + 1;
        int xMax = gamePanel.getWidth() - nodeWidth - 1;
        int yMin = nodeHeight + 1;
        int yMax = gamePanel.getHeight() - nodeHeight - 1;

        //Randomize map
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                //Check if node should be blocked
                if (blockChance > Math.random()) {
                    //Create and initialize node
                    Node n = new Node(i*65, j*58, 1);
                    n.setWidth(nodeWidth);
                    n.setHeight(nodeHeight);

                    //Create node sprite
                    try {
                        n.createSprite();
                    } catch (SpriteException se) {
                        System.out.println("Error: " + se.getMessage());
                    }

                    //Add node to map and list
                    nodeMap[i][j] = n;
                    nodes.add(n);
                } //Not a blocked node
                else {
                    //Create and initialize node
                    Node n = new Node(i*65, j*58, 0);
                    n.setWidth(nodeWidth);
                    n.setHeight(nodeHeight);

                    //Create node sprite
                    try {
                        n.createSprite();
                    } catch (SpriteException se) {
                        System.out.println("Error:" + se.getMessage());
                    }

                    //Add node to map and list
                    nodeMap[i][j] = n;
                    nodes.add(n);
                }
            }
        }
    }

    /**
     * Finalize node sprites and draw them
     */
    private void drawMap() {
        
        //Loop through nodes and create node panels
        for (int i = 0; i < nodeCount; i++) {
            Node n = nodes.get(i);
            JPanel nodePanel = new JPanel();
            JLabel nodeID = new JLabel(Integer.toString(i));
            nodePanel.add(nodeID);
            nodePanel.setSize((int)n.getSprite().getWidth(), (int)n.getSprite().getHeight());
            nodePanel.setLocation(n.getX(), n.getY());
            nodePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            
            //Check node type
            if(n.getType() == 0)
            {
                //Node is traversable, Color is green
                nodePanel.setBackground(Color.GREEN);
            }
            //Node is not traversable
            else {
                nodePanel.setBackground(Color.RED);
            }
            
            //Add node panel to game panel
            gamePanel.add(nodePanel);
            System.out.println(n.toString());
        }

        //Refresh game panel
        gamePanel.repaint();
        gamePanel.validate();
    }

}
