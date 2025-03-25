package ecs.Entities;

import utils.NounType;

public class Lava {
    public static Entity create(float posX, float posY){

        Entity lava = new Entity();

        lava.add(new ecs.Components.Sprite());
        lava.add(new ecs.Components.Position(posX, posY));
        lava.add(new ecs.Components.Noun(NounType.LAVA));

        return lava;

    }
}
