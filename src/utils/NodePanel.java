/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import astarai.Node;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * This is a JPanel to be used to draw the nodes to 
 * the screen
 * @author kward60
 */
public class NodePanel extends JPanel{
    
    private Color color;
    private String text;
    private Node n;
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(color);
        Rectangle nodeSprite = n.getSprite();
        g.fillRect(n.getX(), n.getY(), (int)nodeSprite.getWidth(), (int)nodeSprite.getHeight());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Node getN() {
        return n;
    }

    public void setN(Node n) {
        this.n = n;
    }
    
    
    
}
