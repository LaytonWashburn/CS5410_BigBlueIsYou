package ecs.Systems;

import core.GameSounds;
import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import level.Level;
import utils.ParticleSystem;
import utils.Properties;

import java.util.ArrayList;

public class Kill extends System{

    private final Level level;
    private final ParticleSystem sysParticle;
    private GameSounds gameSounds;

    public Kill(Level level, ParticleSystem sysParticle, GameSounds gameSounds) {
        super(Position.class);

        this.level = level;
        this.sysParticle = sysParticle;
        this.gameSounds = gameSounds;
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
                            sysParticle.playerDeath(entity.get(ecs.Components.Position.class), level);
                            killed.add(new Tuple2<>(controllable, true));
                            gameSounds.spriteDestroy.play();
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
