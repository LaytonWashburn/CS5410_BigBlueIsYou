package ecs.Systems;

import core.BackgroundMusic;
import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import utils.Properties;

import java.util.ArrayList;

public class Kill extends System{

    public Kill() {
        super(Position.class);
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {

        ArrayList<Tuple2<Entity, Boolean>> killed = new ArrayList<>();

        for (Entity controllable : findControllable()) {
            var moveablePosition = controllable.get(Position.class);

            for(Entity entity : entities.values()) {

                if(entity != controllable) {

                    if(entity.contains(Position.class) &&
                            entity.contains(ecs.Components.Property.class) &&
                            entity.get(ecs.Components.Property.class).getProperties().contains(Properties.KILL)) {

                        var entityPosition = entity.get(Position.class);

                        if(moveablePosition.i == entityPosition.i && moveablePosition.j == entityPosition.j) {
                            // Call the Particle System for Kill
                            killed.add(new Tuple2<>(controllable, true));
                        }
                    }
                }
            }
        }

        return killed;
    }

    /**
     * Returns a collection of all the currently controllable entities.
     */
    private ArrayList<Entity> findControllable() {

        ArrayList<Entity> controllables = new ArrayList<>();

        for (var entity : entities.values()) {
            if (entity.contains(ecs.Components.Property.class)) {
                var property = entity.get(ecs.Components.Property.class);
                if (property.getProperties().contains(Properties.MOVE)) {
                    controllables.add(entity);
                }
            }
        }

        return controllables;
    }


}
