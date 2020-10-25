/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarai;

import astarai.ui.GameWindow;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Main program driver
 * 
 * @author kward60
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameWindow gw = new GameWindow();
        gw.setVisible(true);
        GameEngine engine = new GameEngine(gw, 15, 15);
        
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        engine.moveAgent();
    }
    
}
