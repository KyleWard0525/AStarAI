package astarai;

import java.awt.Rectangle;
import javax.swing.JPanel;
import astarai.utils.GameExceptions;

public class Node {

    //G = cost from start node to this node, H = heuristic, F = G + H
    private int x, y, f, g, h, type;
    private int row, col;
    private int id;
    private int width, height;
    private Node parent;
    private boolean goal;
    private Rectangle sprite;
    protected Node goalNode;
    private JPanel panel;

    public Node(int x, int y, int t) {
        this.x = x;
        this.y = y;
        this.panel = new JPanel();
        this.goalNode = GameEngine.goalNode;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
    }
    
    public void createSprite() throws GameExceptions.SpriteException
    {
        //Error: width and height not set
        if(width == 0 || height == 0)
        {
            throw new GameExceptions.SpriteException("Sprite width or height not set!");
        }
        //Otherwise, create sprite
        sprite = new Rectangle(x,y,width,height);
    }
    
    //mutator methods to set values
    public void setF() {
        f = g + h;
    }

    public void setG(int value) {
        g = value;
    }

    public void setH(int value) {
        h = value;
    }

    public void setParent(Node n) {
        parent = n;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }
    
    public void setID(int id)
    {
        this.id = id;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    
   
    //accessor methods to get values
    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public Node getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public Rectangle getSprite()
    {
        return sprite;
    }

    public boolean isGoal() {
        return goal;
    }

    public int getId() {
        return id;
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    

    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return row == n.getRow() && col == n.getCol();
    }

    public String toString() {
        if(type != 1)
        {
        return "Node:(" + row + ", " + col + "), id = " + id;
        }
        else {
            return "Node:(" + row + ", " + col + ") BLOCKED, id = " + id;
        }
    }

}
