/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import static astarai.GameEngine.agent;
import astarai.gui.Agent;
import astarai.utils.GameExceptions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    protected int pathLength;
    
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
        this.openList = new PriorityQueue<>(GameEngine.xSize*GameEngine.ySize, nodeComp);
        this.closedList = new LinkedList<Node>();
        this.nodeMap = GameEngine.nodeMap;
        this.startNode = GameEngine.startNode;
        this.goalNode = GameEngine.goalNode;
        this.pathLength = 0;
        
        //Initialize startNode values
        startNode.setG(0);
        computeNodeH(startNode);
        startNode.setF();
        
        //Add startNode to the open list
        openList.add(startNode);
    }
    
    /**
     * Compute node's heuristic value using the Manhattan Distance
     * 
     * @param n 
     */
    public void computeNodeH(Node n)
    {
        n.setH(Math.abs(goalNode.getRow() - n.getRow()) + Math.abs(goalNode.getCol() - n.getCol()));
    }
    
    /**
     * Compute the optimal path from the start node to goal
     * node
     * 
     * @return true if path found, false is none 
     */
    public boolean solve()
    {
        ArrayList<Node> children = new ArrayList<>(8); //All child nodes
        int[] moveValues = new int[]{-16, -15, -14, -1, 1, 14, 15, 16};
        
        //Loop while open list is not empty
        while(!openList.isEmpty()) {
            
        children.clear();
        
        //Get the node at the head of the queue
        currNode = openList.poll();
        
        //Skip blocked nodes
        while(currNode.getType() == 1)
        {
            currNode = openList.poll();
        }
        
        
        //Add current node to closed list
        closedList.add(currNode);
        System.out.println("Current node: " + currNode.toString());
        System.out.println("Path length: " + closedList.size());
        
        //Check if currNode is goal node
        if(currNode.isGoal())
        {
            //Return game over flag
            return true;
        }
        
        //Add all children to list
        int currId = currNode.getId();
        
        //Check if the child node exists
        for(int i = 0; i < moveValues.length; i++)
        {
            //Check if id is out of bounds
            if(currId + moveValues[i] >= 0 && currId + moveValues[i] <= (GameEngine.xSize*GameEngine.ySize)-1)
            {
                children.add(nodeList.get(currId + moveValues[i]));
            }
            
        }
        
        System.out.println("Children size before removal: " + children.size());
        
        //Remove all blocked nodes from children
        for(int i = 0; i < children.size()-1; i++)
        {
            //Check if node is a blocked node
            if(children.get(i).getType() == 1)
            {
                //Remove from children list
                closedList.add(children.get(i));
                
                //Remove from open list
                if(openList.contains(children.get(i)))
                {
                    openList.remove(children.get(i));
                }
                
                children.remove(i);
            }
        }
        
        System.out.println("Children size after removal: " + children.size() + "\n");
        
        //Loop through children
        for(Node child : children)
        {
            //GOAL FOUND
            if(child.isGoal())
            {
                child.setParent(currNode);
                currNode = child;
                return true;
            }
            
            //Add child with lowest path cost to open list
            if(openList.contains(child))
            {
                child.setG(getPathLength() + 1);
                
                
                //Parent has lower cost than child
                if(currNode.getG() < child.getG())
                {
                    openList.remove(child);
                    child.setG(currNode.getG()+1);
                    child.setParent(currNode);
                    computeNodeH(child);
                    child.setF();
                    openList.add(child);
                }
                //Child has lower g cost that parent
                else{
                    child.setParent(currNode);
                    computeNodeH(child);
                    child.setF();
                    openList.add(child);
                }
                
            }
            else if(!closedList.contains(child))
            {
                openList.add(child);
            }
        }
            
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public int getPathLength()
    {
     int length = 0;
     Node c = currNode;
     
     while(c.getParent() != null)
     {
         length++;
         c = c.getParent();
     }
     return length;
    }
    
    
    public ArrayList<Node> getPath()
    {
        if(solve())
        {
        ArrayList<Node> path = new ArrayList<>();
        path.add(currNode);
        
        //Loop through nodes and add their parent to the path
        while(currNode.getParent() != null)
        {
            path.add(0,currNode);
            
            //Set currNode to its parent
            currNode = currNode.getParent();
        }
        return path;
        }
        System.out.println("Error occured while solving map!"); 
        return null;
    }
    

    /*** GETTERS AND SETTERS ***/
    public Node getCurrNode() {
        return currNode;
    }
    
    public void setCurrNode(Node currNode) {
        this.currNode = currNode;
    }

    public Queue<Node> getClosedList() {
        return closedList;
    }

    private static Comparator<Node> nodeComp = new Comparator<Node>() {
        @Override
        public int compare(Node t, Node t1) {
            return (int)(t.getF() - t1.getF());
        }
        
    };
}
