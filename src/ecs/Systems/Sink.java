package ecs.Systems;

import ecs.Components.Noun;
import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;
import level.Level;
import utils.NounType;
import utils.ParticleSystem;
import utils.Properties;

import java.util.ArrayList;

public class Sink extends System{

    private final ParticleSystem sysParticle;
    private final Level level;

    public Sink(Level level, ParticleSystem sysParticle) {
        super(Position.class, Noun.class);

        this.level = level;
        this.sysParticle = sysParticle;
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {

        ArrayList<Tuple2<Entity, Boolean>> sunk = new ArrayList<>();

        for (Entity entity1 : entities.values()) {
            var entity1Position = entity1.get(Position.class);
            var entity1Noun = entity1.get(Noun.class);

            for(Entity entity2 : entities.values()) {
                var entity2Noun = entity2.get(Noun.class);

                if(entity2 != entity1 && entity1Noun.getNounType() != NounType.TEXT && entity2Noun.getNounType() != NounType.TEXT) {

                    if(entity2.contains(Position.class) &&
                            entity2.contains(ecs.Components.Property.class) &&
                            entity2.get(ecs.Components.Property.class).getProperties().contains(Properties.SINK)) {

                        var entityPosition = entity2.get(Position.class);

                        if(entity1Position.i == entityPosition.i && entity1Position.j == entityPosition.j) {
                            if (entity1.get(ecs.Components.Property.class).getProperties().contains(Properties.YOU) ||
                                    entity2.get(ecs.Components.Property.class).getProperties().contains(Properties.YOU)) {
                                sysParticle.playerDeath(entity1.get(ecs.Components.Position.class), level);
                                sysParticle.objectDeath(entity2.get(ecs.Components.Position.class), level);
                            } else {
                                sysParticle.objectDeath(entity1.get(ecs.Components.Position.class), level);
                                sysParticle.objectDeath(entity2.get(ecs.Components.Position.class), level);
                            }
                            sunk.add(new Tuple2<>(entity1, true));
                            sunk.add(new Tuple2<>(entity2, true));
                        }
                    }
                }
            }
        }

        return sunk;
    }

}
