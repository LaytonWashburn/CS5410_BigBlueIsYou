package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.Properties;
import utils.NounType;

public class Rock {

    public static Entity create(Texture texture, float posX, float posY, boolean pushable){

        Entity rock = new Entity();

        rock.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {.2f, .2f, .2f}, new Vector2f(.1f, .1f), new Vector2f(posX, posY))));
        rock.add(new ecs.Components.Position(posX, posY));
        rock.add(new ecs.Components.Noun(NounType.ROCK));
        if(pushable){
            rock.add(new ecs.Components.Property(Properties.PUSHABLE));
        }

        return rock;

    }
}
