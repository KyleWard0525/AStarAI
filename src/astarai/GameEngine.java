/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import astarai.ui.GameWindow;
import java.util.ArrayList;

/**
 * This is the main engine that controls the GUI elements along
 * with running the A* search algorithm
 * @author kward60
 */
public class GameEngine {
    
    private int xSize;
    private int ySize;
    private GameWindow gw;
    private AStar algo;
    private ArrayList<Node> nodes;
    
    /**
     * Main Constructor
     * @param gw - Game window
     * @param x - number of rows
     * @param y - number of columns
     */
    public GameEngine(GameWindow gw, int x, int y)
    {
        this.gw = gw;
        this.xSize = x;
        this.ySize = y;
        this.nodes = new ArrayList<Node>(x*y);
    }
    
}
