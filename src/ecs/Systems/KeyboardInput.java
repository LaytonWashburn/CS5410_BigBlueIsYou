package ecs.Systems;

import ecs.Entities.Entity;
import utils.Direction;
import utils.KeyBinds;
import utils.EntityConstants;

import java.security.Key;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class KeyboardInput extends System {

    private final long window;

    KeyBinds keyBinds;

    public KeyboardInput(long window, KeyBinds keyBinds) {
        super(ecs.Components.KeyboardControlled.class);

        this.window = window;
        this.keyBinds = keyBinds;
    }

    @Override
    public ArrayList<Entity> update(double gameTime) {

        for(Entity entity : entities.values()){
            var input = entity.get(ecs.Components.KeyboardControlled.class);
            var moving = entity.get(ecs.Components.Movement.class);

            moving.moving = Direction.STOP;

            if (glfwGetKey(window, input.lookup.get(Direction.LEFT)) == GLFW_PRESS && !input.keysPressed.get(Direction.LEFT)) {
                moving.moving = Direction.LEFT;
                input.keysPressed.put(Direction.LEFT, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.RIGHT)) == GLFW_PRESS && !input.keysPressed.get(Direction.RIGHT)) {
                moving.moving = Direction.RIGHT;
                input.keysPressed.put(Direction.RIGHT, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.UP)) == GLFW_PRESS && !input.keysPressed.get(Direction.UP)) {
                moving.moving = Direction.UP;
                input.keysPressed.put(Direction.UP, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.DOWN)) == GLFW_PRESS && !input.keysPressed.get(Direction.DOWN)) {
                moving.moving = Direction.DOWN;
                input.keysPressed.put(Direction.DOWN, true);
            }

            input.keysPressed.put(Direction.LEFT, glfwGetKey(window, keyBinds.LEFT) == GLFW_PRESS);
            input.keysPressed.put(Direction.RIGHT, glfwGetKey(window, keyBinds.RIGHT) == GLFW_PRESS);
            input.keysPressed.put(Direction.UP, glfwGetKey(window, keyBinds.UP) == GLFW_PRESS);
            input.keysPressed.put(Direction.DOWN, glfwGetKey(window, keyBinds.DOWN) == GLFW_PRESS);

        }
        return new ArrayList<>();
    }
}