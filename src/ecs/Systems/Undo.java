package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import edu.usu.utils.Tuple2;
import utils.Direction;

import java.util.ArrayList;

public class Undo extends System{

    public interface IPressedUndo {
        void invoke() throws CloneNotSupportedException;
    }

    private final IPressedUndo pressedUndo;

    public Undo(IPressedUndo pressedUndo) {
        super(ecs.Components.Position.class);

        this.pressedUndo = pressedUndo;

    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {
        ArrayList<Entity> movables = findMovable(); // Get the movable entities

        for (Entity entity : movables) { // Loop through the entities
            var moving = entity.get(ecs.Components.Movement.class);

            if (moving.moving == Direction.UNDO) {
                pressedUndo.invoke();
            }

        }

        return new ArrayList<>();
    }

    /**
     * Returns a collection of all the movable entities.
     */
    private ArrayList<Entity> findMovable() {

        ArrayList<Entity> movables = new ArrayList<>();

        for (var entity : entities.values()) {
            if (entity.contains(ecs.Components.KeyboardControlled.class)) {
                movables.add(entity);
            }
        }

        return movables;
    }
}
