package astarai;

public class Node {

    private int x, y, f, g, h, type;
    private int width, height;
    private Node parent;

    public Node(int x, int y, int t) {
        this.x = x;
        this.y = y;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
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
    
    

    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return x == n.getX() && y == n.getY();
    }

    public String toString() {
        return "Node: " + x + "_" + y;
    }

}
