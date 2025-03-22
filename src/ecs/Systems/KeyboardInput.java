package ecs.Systems;

import ecs.Entities.Entity;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class KeyboardInput extends System {

    private final long window;


    public KeyboardInput(long window) {
        super();

        this.window = window;
    }

    @Override
    public void update(double gameTime) {

    }
}