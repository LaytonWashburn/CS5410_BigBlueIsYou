package ecs.Entities;

import utils.NounType;

public class Water {
    public static Entity create(float posX, float posY){

        Entity water = new Entity();

        water.add(new ecs.Components.Sprite());
        water.add(new ecs.Components.Position(posX, posY));
        water.add(new ecs.Components.Noun(NounType.WATER));

        return water;

    }
}
