package ecs.Entities;

import utils.NounType;

public class Wall
{

    public static Entity create(float posX, float posY){

        Entity wall = new Entity();

        wall.add(new ecs.Components.Sprite());
        wall.add(new ecs.Components.Position(posX, posY));
        wall.add(new ecs.Components.Noun(NounType.WALL));

        return wall;

    }
}
