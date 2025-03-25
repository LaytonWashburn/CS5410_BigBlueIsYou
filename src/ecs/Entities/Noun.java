package ecs.Entities;

import utils.NounType;

public class Noun {
    public static Entity create(float posX, float posY){

        Entity noun = new Entity();

        noun.add(new ecs.Components.Sprite());
        noun.add(new ecs.Components.Position(posX, posY));
        noun.add(new ecs.Components.Noun(NounType.TEXT));
        noun.add(new ecs.Components.Text("Noun"));

        return noun;

    }
}
