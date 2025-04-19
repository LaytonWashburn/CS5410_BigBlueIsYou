package ecs.Systems;

import core.BackgroundMusic;
import core.GameSounds;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import level.Level;
import utils.ParticleSystem;
import utils.Properties;

import java.util.ArrayList;

public class Win extends System{

    BackgroundMusic backgroundMusic;
    GameSounds gameSounds;
    private final Level level;
    private final ParticleSystem sysParticle;

    public Win(BackgroundMusic backgroundMusic, Level level, ParticleSystem sysParticle, GameSounds gameSounds) {
        super(ecs.Components.Position.class);
        this.backgroundMusic = backgroundMusic;
        this.level = level;
        this.sysParticle = sysParticle;
        this.gameSounds = gameSounds;
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {

        for (Entity controllable : findControllable()) {
            var moveablePosition = controllable.get(ecs.Components.Position.class);

            for(Entity entity : entities.values()) {

                if(entity != controllable) {

                    if(entity.contains(ecs.Components.Position.class) &&
                            entity.contains(ecs.Components.Property.class) &&
                            entity.get(ecs.Components.Property.class).getProperties().contains(Properties.WIN)) {

                        var entityPosition = entity.get(ecs.Components.Position.class);

                        if(moveablePosition.i == entityPosition.i && moveablePosition.j == entityPosition.j) {
                            sysParticle.levelWin(entity.get(ecs.Components.Position.class), level);
                            for (Entity otherControllable : findControllable()) {
                                // Remove the move property for every single controllable entity
                                var otherProperties = otherControllable.get(ecs.Components.Property.class);
                                otherProperties.removeProperty(Properties.MOVE);
                            }
                            backgroundMusic.stop();
                            gameSounds.levelWin.play();
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
