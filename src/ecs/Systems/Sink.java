package ecs.Systems;

import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import utils.Properties;

import java.util.ArrayList;

public class Sink extends System{

    public Sink() {
        super(Position.class);
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {

        ArrayList<Tuple2<Entity, Boolean>> sunk = new ArrayList<>();

        for (Entity entity1 : entities.values()) {
            var entity1Position = entity1.get(Position.class);

            for(Entity entity2 : entities.values()) {

                if(entity2 != entity1) {

                    if(entity2.contains(Position.class) &&
                            entity2.contains(ecs.Components.Property.class) &&
                            entity2.get(ecs.Components.Property.class).getProperties().contains(Properties.SINK)) {

                        var entityPosition = entity2.get(Position.class);

                        if(entity1Position.i == entityPosition.i && entity1Position.j == entityPosition.j) {
                            // Call the Particle System for Kill
                            sunk.add(new Tuple2<>(entity1, true));
                            sunk.add(new Tuple2<>(entity2, true));
                        }
                    }
                }
            }
        }

        return sunk;
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
