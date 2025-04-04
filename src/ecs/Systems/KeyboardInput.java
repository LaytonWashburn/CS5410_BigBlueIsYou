package ecs.Systems;

import ecs.Entities.Entity;
import utils.Direction;
import utils.KeyBinds;
import utils.EntityConstants;

import java.security.Key;

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
    public void update(double gameTime) {

        for(Entity entity : entities.values()){
            var input = entity.get(ecs.Components.KeyboardControlled.class);
            var position = entity.get(ecs.Components.Position.class);

            if (glfwGetKey(window, input.lookup.get(Direction.LEFT)) == GLFW_PRESS && !input.keysPressed.get(Direction.LEFT)) {
                position.posX -= EntityConstants.rectSize;
                input.keysPressed.put(Direction.LEFT, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.RIGHT)) == GLFW_PRESS && !input.keysPressed.get(Direction.RIGHT)) {
                position.posX += EntityConstants.rectSize;
                input.keysPressed.put(Direction.RIGHT, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.UP)) == GLFW_PRESS && !input.keysPressed.get(Direction.UP)) {
                position.posY -= EntityConstants.rectSize;
                input.keysPressed.put(Direction.UP, true);
            }
            if (glfwGetKey(window, input.lookup.get(Direction.DOWN)) == GLFW_PRESS && !input.keysPressed.get(Direction.DOWN)) {
                position.posY += EntityConstants.rectSize;
                input.keysPressed.put(Direction.DOWN, true);
            }

            input.keysPressed.put(Direction.LEFT, glfwGetKey(window, keyBinds.LEFT) == GLFW_PRESS);
            input.keysPressed.put(Direction.RIGHT, glfwGetKey(window, keyBinds.RIGHT) == GLFW_PRESS);
            input.keysPressed.put(Direction.UP, glfwGetKey(window, keyBinds.UP) == GLFW_PRESS);
            input.keysPressed.put(Direction.DOWN, glfwGetKey(window, keyBinds.DOWN) == GLFW_PRESS);

        }

    }
}