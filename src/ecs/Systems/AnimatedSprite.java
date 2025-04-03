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
    public void update(double elapsedTime) {

    }
}
