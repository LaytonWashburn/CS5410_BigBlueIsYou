package ecs.Entities;

import utils.NounType;
import utils.TextType;

public class Verb {
    public static Entity create(float posX, float posY){

        Entity verb = new Entity();

        verb.add(new ecs.Components.Sprite());
        verb.add(new ecs.Components.Position(posX, posY));
        verb.add(new ecs.Components.Noun(NounType.TEXT));
        verb.add(new ecs.Components.Text(TextType.VERB));

        return verb;

    }
}
