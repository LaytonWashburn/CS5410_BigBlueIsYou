package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.NounType;
import utils.TextType;

public class Noun {
    public static Entity create(Texture texture, int i, int j){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity noun = new Entity();

        noun.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        noun.add(new ecs.Components.Position(i, j));
        noun.add(new ecs.Components.Noun(NounType.TEXT));
        noun.add(new ecs.Components.Text(TextType.NOUN));

        return noun;

    }
}
