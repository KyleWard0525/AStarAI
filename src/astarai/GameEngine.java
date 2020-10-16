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
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private boolean startSelected;
    private boolean goalSelected;
    private ClickListener clickListen;
    private Node startNode;
    private Node goalNode;
    private final Color startCol = Color.orange;
    private final Color goalCol = Color.BLUE;
    private Random rand;


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
        this.startSelected = false;
        this.goalSelected = false;
        this.clickListen = new ClickListener();

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
                    n.setID(nodes.size());

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
                    n.setID(nodes.size());

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
            nodePanel.addMouseListener(clickListen);
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

    /**
     * Helper class used to listen for mouse clicks
     */
    private class ClickListener implements MouseListener
    {
        private ArrayList<Node> nodeList;
        //public ClickListener()

        @Override
        public void mouseClicked(MouseEvent me) {
            //Check if click was on JPanel
            Object src = me.getSource();
            
            System.out.println("");
            
            if(src instanceof JPanel)
            {
                //Create JPanel and corresponding rectangle
                JPanel clickedPanel = (JPanel) src;
                
                
                //Search through nodes and store start/goal nodes
                for(Node n : nodes)
                {
                    //Node found
                    if(n.getX() == clickedPanel.getX() && n.getY() == clickedPanel.getY())
                    {
                        //Check which node it is
                        if(!startSelected)
                        {
                            startNode = n;
                            System.out.println("Start node selected!");
                            System.out.println("Start node: " + n.toString());
                        }
                        else if(!goalSelected){
                            goalNode = n;
                            System.out.println("Goal node selected!");
                            System.out.println("Goal node: " + n.toString());
                        }
                    }
                    
                }
                
                //Color the start and goal panels' borders
                if(!goalSelected)
                {
                //Check if this panel is the first pressed (start panel)
                if(!startSelected)
                {
                    clickedPanel.setBorder(BorderFactory.createLineBorder(startCol, 3));
                    startSelected = true;
                }
                //Goal panel
                else {
                    clickedPanel.setBorder(BorderFactory.createLineBorder(goalCol, 3));
                    goalSelected = true;
                }
                
                
                
                }
                
            }
            
        }

        @Override
        public void mousePressed(MouseEvent me) {
            
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            
        }

        @Override
        public void mouseExited(MouseEvent me) {
            
        }
        
    }
    
}
