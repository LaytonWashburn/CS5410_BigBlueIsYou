package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.NounType;

public class Flag {

    public static Entity create(Texture texture, float posX, float posY){

        Entity flag = new Entity();

        flag.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {.2f, .2f, .2f}, new Vector2f(.1f, .1f), new Vector2f(posX, posY))));
        flag.add(new ecs.Components.Position(posX, posY));
        flag.add(new ecs.Components.Noun(NounType.FLAG));

        return flag;

    }

}
