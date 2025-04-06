package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import utils.Direction;
import utils.KeyBinds;
import utils.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rules extends System{

    private final KeyBinds keybinds;

    public Rules(KeyBinds keyBinds) {
        super(ecs.Components.Property.class);

        this.keybinds = keyBinds;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {
        ArrayList<Entity> changed = new ArrayList<>();

        for (Entity entity : entities.values()) {   // Ideally these don't have to be checked every update cycle, only after certain events are detected
            var property = entity.get(ecs.Components.Property.class);

            if (property.getProperties().contains(Properties.MOVE) && !entity.contains(ecs.Components.KeyboardControlled.class)) {
                entity.add(new ecs.Components.Movement(Direction.STOP));
                entity.add(new ecs.Components.KeyboardControlled(
                        Map.of(keybinds.UP, Direction.UP,
                                keybinds.DOWN, Direction.DOWN,
                                keybinds.RIGHT, Direction.RIGHT,
                                keybinds.LEFT, Direction.LEFT
                        )
                ));

                changed.add(entity);
            }
            if (!property.getProperties().contains(Properties.MOVE) && entity.contains(ecs.Components.KeyboardControlled.class)) {
                entity.remove(ecs.Components.KeyboardControlled.class);
                entity.remove(ecs.Components.Movement.class);

                changed.add(entity);
            }

        }

        return changed;
    }

}
