package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.NounType;
import utils.TextType;

public class Noun {
    public static Entity create(Texture texture, float posX, float posY){

        Entity noun = new Entity();

        noun.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {.2f, .2f, .2f}, new Vector2f(.1f, .1f), new Vector2f(posX, posY))));
        noun.add(new ecs.Components.Position(posX, posY));
        noun.add(new ecs.Components.Noun(NounType.TEXT));
        noun.add(new ecs.Components.Text(TextType.NOUN));

        return noun;

    }
}
