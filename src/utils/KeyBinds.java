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

}
