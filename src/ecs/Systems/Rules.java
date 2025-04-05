package ecs.Systems;

import ecs.Entities.Entity;

import java.util.ArrayList;

public class Rules extends System{

    @Override
    public ArrayList<Entity> update(double elapsedTime) {
        return new ArrayList<>(entities.values());
    }


}
