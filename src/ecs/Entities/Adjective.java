package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.NounType;
import utils.TextType;

public class Adjective {
    public static Entity create(Texture texture, float posX, float posY){

        Entity adjective = new Entity();

        adjective.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {.2f, .2f, .2f}, new Vector2f(.1f, .1f), new Vector2f(posX, posY))));
        adjective.add(new ecs.Components.Position(posX, posY));
        adjective.add(new ecs.Components.Noun(NounType.TEXT));
        adjective.add(new ecs.Components.Text(TextType.ADJECTIVE));

        return adjective;

    }
}
