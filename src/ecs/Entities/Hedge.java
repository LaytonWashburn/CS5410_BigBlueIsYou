package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.NounType;

public class Hedge {

    public static Entity create(Texture texHedge, float posX, float posY){
        var hedge = new Entity();
        hedge.add(new ecs.Components.Sprite(new AnimatedSprite(texHedge, new float[] {.2f, .2f, .2f}, new Vector2f(.1f, .1f), new Vector2f(posX, posY))));
        hedge.add(new ecs.Components.Position(posX, posY));
        hedge.add(new ecs.Components.Noun(NounType.WALL));

        return hedge;
    }
}
