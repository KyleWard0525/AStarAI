/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import astarai.gui.Agent;
import astarai.gui.GameWindow;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import astarai.utils.GameExceptions.SpriteException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * This is the main engine that controls the GUI elements along with running the
 * A* search algorithm
 *
 * @author kward60
 */
public class GameEngine {

    private int mapWidth;
    private int mapHeight;
    private int nodeWidth;
    private int nodeHeight;
    private int nodeCount;
    private double blockChance;
    private boolean startSelected;
    private boolean goalSelected;
    private ClickListener clickListen;
    protected static Agent agent;
    private Random rand;
    private Timer timer;
    protected static JPanel gamePanel;
    private static JPanel agentSprite;
    private GameWindow gw;
    protected static AStar algo;
    private final Color startCol = Color.orange;
    private final Color goalCol = Color.BLUE;
    protected static Node startNode;
    protected static Node goalNode;
    protected static Node[][] nodeMap;
    protected static int xSize;
    protected static int ySize;
    protected static ArrayList<Node> nodes;
    public static boolean ready;

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
        this.timer = new Timer(16, listener); //Renders at ~60fps
        this.ready = false;

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
        this.nodeWidth = mapWidth / xSize;
        this.nodeHeight = mapHeight / ySize;

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
                    Node n = new Node(i * 65, j * 58, 1);
                    n.setWidth(nodeWidth);
                    n.setHeight(nodeHeight);
                    n.setID(nodes.size());
                    n.setRow(j);
                    n.setCol(i);

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
                    Node n = new Node(i * 65, j * 58, 0);
                    n.setWidth(nodeWidth);
                    n.setHeight(nodeHeight);
                    n.setID(nodes.size());
                    n.setRow(j);
                    n.setCol(i);

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

            //Initialize nodePanel
            nodePanel.add(nodeID);
            nodePanel.addMouseListener(clickListen);
            nodePanel.setSize((int) n.getSprite().getWidth(), (int) n.getSprite().getHeight());
            nodePanel.setLocation(n.getX(), n.getY());
            nodePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

            //Check node type
            if (n.getType() == 0) {
                //Node is traversable, Color is green
                nodePanel.setBackground(Color.GREEN);
            } //Node is not traversable
            else {
                nodePanel.setBackground(Color.RED);
            }

            //Set node's JPanel
            n.setPanel(nodePanel);

            //Add node panel to game panel
            gamePanel.add(nodePanel);
        }

        //Refresh game panel
        gamePanel.repaint();
        gamePanel.validate();

        timer.start();
    }

    /**
     * Draw agent on the screen
     */
    public void drawAgent() {
        //Start position not selected
        if (!startSelected) {
            System.out.println("Error: no start position selected");
            return;
        }

        //Check if agent has been activated
        if (!agent.isActive()) {
            //Initialize variables
            JPanel agentSprite = agent.getAgentSprite();

            //Get current panel and add agent sprite
            JPanel currPanel = agent.getCurrPanel();
            currPanel.add(agentSprite);
        }
    }

    /**
     * Move agent sprite to next node
     */
    public static void moveAgent() {
        ArrayList<Node> path = algo.getPath();

        //Loop through path and move agent
        for (Node n : path) {
            //Redraw 
            agent.setCurrPanel(agent.getCurrNode().getPanel());
            agent.getCurrPanel().add(agentSprite);

            //Refresh gamelPanel
            gamePanel.revalidate();
            gamePanel.repaint();

            agent.setCurrNode(n);
            agent.setCurrPanel(n.getPanel());
        }

    }

    /**
     * Handle the game over scenario
     */
    public void handleGameOver() {
        try {

            System.out.println("\nGame Over!");

            //Stop game timer and show game over message
            JOptionPane.showMessageDialog(gw, "The map has been solved!", "Game Over", JOptionPane.PLAIN_MESSAGE);

            Thread.sleep(500);
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (agent != null) {
                agent.setCurrPanel(agent.getCurrNode().getPanel());
                agent.getCurrPanel().add(agentSprite);

                if (agent.getCurrNode().isGoal()) {
                    //Handle game over
                    moveAgent();
                    handleGameOver();
                }
            }

            //Refresh gamelPanel
            gamePanel.revalidate();
            gamePanel.repaint();
        }

    };

    /**
     * Helper class used to listen for mouse clicks
     */
    private class ClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            //Check if click was on JPanel
            Object src = me.getSource();

            System.out.println("");

            if (src instanceof JPanel) {
                //Create JPanel and corresponding rectangle
                JPanel clickedPanel = (JPanel) src;

                //Search through nodes and store start/goal nodes
                for (Node n : nodes) {
                    //Node found
                    if (n.getX() == clickedPanel.getX() && n.getY() == clickedPanel.getY()) {
                        //Check which node it is
                        if (!startSelected) {
                            startNode = n;
                            System.out.println("Start node selected!");
                            System.out.println("Start node: " + n.toString());

                            //Initialize AI
                            agent = new Agent(startNode);
                            agent.setCurrPanel(clickedPanel);
                            agent.setNodeList(nodes);
                            agentSprite = agent.getAgentSprite();

                            //Update start node label
                            gw.getLblStartNode().setForeground(startCol);
                            gw.getLblStartNode().setText(startNode.toString());
                        } else if (!goalSelected) {
                            goalNode = n;
                            goalNode.setGoal(true);
                            System.out.println("Goal node selected!");
                            System.out.println("Goal node: " + n.toString());

                            //Update end node label
                            gw.getLblEndNode().setForeground(goalCol);
                            gw.getLblEndNode().setText(goalNode.toString());

                            //Initialize A* algorithm
                            algo = new AStar();
                        }
                    }

                }

                //Color the start and goal panels' borders
                if (!goalSelected) {
                    //Check if this panel is the first pressed (start panel)
                    if (!startSelected) {
                        clickedPanel.setBorder(BorderFactory.createLineBorder(startCol, 3));
                        startSelected = true;

                        //Draw agent sprite
                        drawAgent();
                    } //Goal panel
                    else {
                        clickedPanel.setBorder(BorderFactory.createLineBorder(goalCol, 3));
                        goalSelected = true;
                        agent.setActive(true);
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

    public Agent getAgent() {
        return agent;
    }

}
