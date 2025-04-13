package ecs.Systems;

import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import utils.Properties;

import java.util.ArrayList;

public class Win extends System{

    public Win() {
        super(ecs.Components.Position.class);
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {


        Entity moveable = findMovable().getFirst();
        Position moveablePosition = moveable.get(ecs.Components.Position.class);

        for(Entity entity : entities.values()) {

            if(entity != moveable) {

                if(entity.contains(ecs.Components.Position.class) &&
                   entity.contains(ecs.Components.Property.class) &&
                   entity.get(ecs.Components.Property.class).getProperties().contains(Properties.WIN)) {

                    var entityPosition = entity.get(ecs.Components.Position.class);

                    java.lang.System.out.println("In the Win System Update : I:" + entityPosition.i + " J: " + entityPosition.j + " Character: I: " + moveablePosition.j + " J: " + moveablePosition.j);

                    if(moveablePosition.i == entityPosition.i && moveablePosition.j == entityPosition.j) {
                        // Call the Particle System for Win
                        moveable.remove(ecs.Components.Movement.class);
                        java.lang.System.out.println("GAME WON!!!!!!!");
                    }
                }
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
