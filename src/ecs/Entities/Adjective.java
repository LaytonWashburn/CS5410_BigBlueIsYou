package ecs.Entities;

import utils.NounType;
import utils.TextType;

public class Adjective {
    public static Entity create(float posX, float posY){

        Entity adjective = new Entity();

        adjective.add(new ecs.Components.Sprite());
        adjective.add(new ecs.Components.Position(posX, posY));
        adjective.add(new ecs.Components.Noun(NounType.TEXT));
        adjective.add(new ecs.Components.Text(TextType.ADJECTIVE));

        return adjective;

    }
}
