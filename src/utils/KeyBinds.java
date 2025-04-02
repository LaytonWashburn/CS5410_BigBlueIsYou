package utils;

import utils.Direction;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Class to hold key binds information for gameplay
 */
public class KeyBinds {


    public int LEFT;
    public int RIGHT;
    public int DOWN;
    public int UP;
    public int RESET;
    public int UNDO;
    public boolean initialized = false;

    // Default configuration for the key binds
    public KeyBinds(){
        this.LEFT = GLFW_KEY_A;
        this.RIGHT = GLFW_KEY_D;
        this.UP = GLFW_KEY_W;
        this.DOWN = GLFW_KEY_S;
        this.RESET = GLFW_KEY_R;
        this.UNDO = GLFW_KEY_Z;
        this.initialized = true;
    }

    public void printKeyBinds(){
//        System.out.println("LEFT: " + this.LEFT);
//        System.out.println("RIGHT: " + this.RIGHT);
//        System.out.println("UP: " + this.UP);
//        System.out.println("DOWN: " + this.DOWN);
//        System.out.println("RESET: " + this.RESET);
//        System.out.println("UNDO: " + this.UNDO );
        System.out.println("LEFT: " + glfwGetKeyName(this.LEFT, glfwGetKeyScancode(this.LEFT)));
        System.out.println("RIGHT: " + glfwGetKeyName(this.RIGHT, glfwGetKeyScancode(this.RIGHT)));
        System.out.println("UP: " + glfwGetKeyName(this.UP, glfwGetKeyScancode(this.UP)));
        System.out.println("DOWN: " + glfwGetKeyName(this.DOWN, glfwGetKeyScancode(this.DOWN)));
        System.out.println("RESET: " + glfwGetKeyName(this.RESET, glfwGetKeyScancode(this.RESET)));
        System.out.println("UNDO: " + glfwGetKeyName(this.UNDO, glfwGetKeyScancode(this.UNDO)));
    }

}
