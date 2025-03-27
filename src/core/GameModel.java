package core;

import ecs.Entities.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import level.Level;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final Level level;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();
    private KeyboardInput sysKeyboardInput;

    public GameModel(Level level) {
        this.level = level;
    }

    public void initialize(Graphics2D graphics) {
        sysKeyboardInput = new KeyboardInput(graphics.getWindow());

        System.out.println("GameModel initialized with level: " + level);

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                System.out.print(level.getGroup1()[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                System.out.print(level.getGroup2()[i][j]);
            }
            System.out.println();
        }

    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);

        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

        for (var entity : addThese) {
            addEntity(entity);
        }
        addThese.clear();

        // Because ECS framework, rendering is now part of the update
    }

    private void addEntity(Entity entity) {
        sysKeyboardInput.add(entity);

    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
    }


}
