package ecs.Entities;

import utils.NounType;

public class BigBlue {

    public static Entity create(float posX, float posY){

        Entity blueBlue = new Entity();

        blueBlue.add(new ecs.Components.Sprite());
        blueBlue.add(new ecs.Components.Position(posX, posY));
        blueBlue.add(new ecs.Components.Noun(NounType.BIGBLUE));

        return  blueBlue;

    }
}
