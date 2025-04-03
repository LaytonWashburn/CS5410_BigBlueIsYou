package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.NounType;
import utils.TextType;
import utils.EntityConstants;

public class Adjective {
    public static Entity create(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity adjective = new Entity();

        adjective.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        adjective.add(new ecs.Components.Position(posX, posY));
        adjective.add(new ecs.Components.Noun(NounType.TEXT));
        adjective.add(new ecs.Components.Text(TextType.ADJECTIVE));

        return adjective;

    }
}
