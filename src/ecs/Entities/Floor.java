package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.NounType;

public class Floor {
    public static Entity create(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity floor = new Entity();

        floor.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        floor.add(new ecs.Components.Position(posX, posY));
        floor.add(new ecs.Components.Noun(NounType.LAVA));

        return floor;

    }
}
