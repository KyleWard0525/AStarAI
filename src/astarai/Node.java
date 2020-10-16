package astarai;

import java.awt.Rectangle;
import utils.GameExceptions;

public class Node {

    private int x, y, f, g, h, type;
    private int id;
    private int width, height;
    private Node parent;
    private boolean goal;
    private Rectangle sprite;

    public Node(int x, int y, int t) {
        this.x = x;
        this.y = y;
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

    /**
     * Heuristic function (Manhattan Distance)
     * @param n
     * @return 
     
    public int error(Node n)
    {
        
    }*/
    
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
    
    

    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return x == n.getX() && y == n.getY();
    }

    public String toString() {
        if(type != 1)
        {
        return "Node: (" + x + ", " + y + "), id = " + id;
        }
        else {
            return "Node: (" + x + ", " + y + ") BLOCKED, id = " + id;
        }
    }

}
