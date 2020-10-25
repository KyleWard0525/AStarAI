/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import astarai.gui.Agent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is the implementation of the A* algorithm in order
 * to solve the map
 * 
 * @author kward60
 */
public class AStar {
    
    private Queue<Node> openList;
    private Queue<Node> closedList;
    private ArrayList<Node> nodeList;
    private Node[][] nodeMap;
    private Node startNode;
    private Node goalNode;
    private Node currNode;
    
    /**
     * Default constructor
     */
    public AStar()
    {
        init();
    }
    
    /**
     * Initialize global variables
     */
    private void init()
    {
        this.nodeList = GameEngine.nodes;
        this.openList = new PriorityQueue<Node>(nodeList.size(), nodeComp);
        this.closedList = new PriorityQueue<Node>(nodeList.size());
        this.nodeMap = GameEngine.nodeMap;
        this.startNode = GameEngine.startNode;
        this.goalNode = GameEngine.goalNode;
    }

    /*** GETTERS AND SETTERS ***/
    public Node getCurrNode() {
        return currNode;
    }
    
    public void setCurrNode(Node currNode) {
        this.currNode = currNode;
    }

    private static Comparator<Node> nodeComp = new Comparator<Node>() {
        @Override
        public int compare(Node t, Node t1) {
            return (int)(t.getF() - t1.getF());
        }
        
    };
}
