package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.EntityConstants;
import utils.NounType;

public class BigBlue {

    public static Entity create(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity bigBlue = new Entity();

        bigBlue.add(new ecs.Components.BigBlue());
        bigBlue.add(new ecs.Components.Sprite());
        bigBlue.add(new ecs.Components.Appearance(texture));
        bigBlue.add(new ecs.Components.Position(posX, posY));
        bigBlue.add(new ecs.Components.Noun(NounType.BIGBLUE));

        return  bigBlue;

    }
}
