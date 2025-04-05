package ecs.Systems;

import ecs.Entities.Entity;

import java.util.ArrayList;

public class AnimatedSprite extends System{
    ArrayList<Entity> modified;

    public AnimatedSprite(){
        super(ecs.Components.Sprite.class,
                ecs.Components.Animated.class);
    }
    @Override
    public ArrayList<Entity> update(double elapsedTime) {
        return new ArrayList<>(entities.values());
    }
}
