/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * This is a utility class that stores custom
 * exceptions for the game
 * 
 * @author kward60
 */
public class GameExceptions {

    public static class SpriteException extends Exception {

        public SpriteException(String message) {
            super(message);
        }
    }
    
}
