package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.Properties;
import utils.NounType;

public class Rock {

    public static Entity create(Texture texture, float posX, float posY, boolean pushable){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity rock = new Entity();

        rock.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        rock.add(new ecs.Components.Position(posX, posY));
        rock.add(new ecs.Components.Noun(NounType.ROCK));
        if(pushable){
            rock.add(new ecs.Components.Property(Properties.PUSHABLE));
        }

        return rock;

    }
}
