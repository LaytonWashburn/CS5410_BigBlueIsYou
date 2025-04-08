package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.NounType;
import utils.TextType;

public class Verb {
    public static Entity create(Texture texture, int i, int j){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity verb = new Entity();

        verb.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        verb.add(new ecs.Components.Position(i, j));
        verb.add(new ecs.Components.Noun(NounType.TEXT));
        verb.add(new ecs.Components.Text(TextType.VERB));

        return verb;

    }
}
