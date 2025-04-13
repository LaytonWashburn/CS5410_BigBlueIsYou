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

        for (Entity controllable : findControllable()) {
            var moveableProperties = controllable.get(ecs.Components.Property.class);
            var moveablePosition = controllable.get(ecs.Components.Position.class);

            for(Entity entity : entities.values()) {

                if(entity != controllable) {

                    if(entity.contains(ecs.Components.Position.class) &&
                            entity.contains(ecs.Components.Property.class) &&
                            entity.get(ecs.Components.Property.class).getProperties().contains(Properties.WIN)) {

                        var entityPosition = entity.get(ecs.Components.Position.class);

                        if(moveablePosition.i == entityPosition.i && moveablePosition.j == entityPosition.j) {
                            // Call the Particle System for Win
                            for (Entity otherControllable : findControllable()) {
                                var otherProperties = otherControllable.get(ecs.Components.Property.class);
                                otherProperties.removeProperty(Properties.MOVE);
                                java.lang.System.out.println("GAME WON!!!!!!!");
                            }
                        }
                    }
                }
            }
        }

        return new ArrayList<>();
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
