/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import java.util.Random;
import javax.swing.JPanel;

/**
 * This is the implementation of the A* algorithm
 * @author kward60
 */
public class AStar {
    
    private int numNodes;
    private int mapWidth;
    private int mapHeight;
    private Node[][] map;
    private JPanel gamePanel;
    private double blockChance;
    private Random rand;
    
    /**
     * Main constructor
     * @param w - map width
     * @param h - map height
     */
    public AStar(int w, int h, JPanel gp)
    {
        this.mapWidth = w;
        this.mapHeight = h;
        this.map = new Node[w][h];
        this.numNodes = w * h;
        this.gamePanel = gp;
        this.blockChance = 0.1; //10%
        this.rand = new Random();
    }
    
    /**
     * Initialize the algorithm
     */
    public void init()
    {
        int nodeW = gamePanel.getWidth() / (numNodes * 2);
        int nodeH = gamePanel.getHeight() / (numNodes * 2);
        
        //Spawn restraints for nodes
        int xMin = nodeW+1;
        int xMax = gamePanel.getWidth() - nodeW - 1;
        int yMin = nodeH+1;
        int yMax = gamePanel.getHeight() - nodeH - 1;
        
        //Randomize map
        for(int i = 0; i < mapWidth; i++)
        {
            for(int j = 0; j < mapHeight; j++)
            {
                //Check if node should be blocked
                if(blockChance > Math.random())
                {
                    map[i][j] = new Node(rand.nextInt(xMax-xMin)+xMin, rand.nextInt(yMax-yMin)+yMin, 1);
                    map[i][j].setWidth(nodeW);
                    map[i][j].setHeight(nodeH);
                }
                //Not a blocked node
                else {
                    map[i][j] = new Node(rand.nextInt(xMax-xMin)+xMin, rand.nextInt(yMax-yMin)+yMin, 0);
                    map[i][j].setWidth(nodeW);
                    map[i][j].setHeight(nodeH);
                }
            }
        }
    }
}
