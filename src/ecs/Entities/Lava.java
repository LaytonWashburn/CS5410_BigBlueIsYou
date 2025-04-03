package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.NounType;

public class Lava {
    public static Entity create(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity lava = new Entity();

        lava.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        lava.add(new ecs.Components.Position(posX, posY));
        lava.add(new ecs.Components.Noun(NounType.LAVA));

        return lava;

    }
}
