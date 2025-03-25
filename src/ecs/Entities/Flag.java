package ecs.Entities;

import utils.NounType;

public class Flag {

    public static Entity create(float posX, float posY){

        Entity flag = new Entity();

        flag.add(new ecs.Components.Sprite());
        flag.add(new ecs.Components.Position(posX, posY));
        flag.add(new ecs.Components.Noun(NounType.FLAG));

        return flag;

    }

}
