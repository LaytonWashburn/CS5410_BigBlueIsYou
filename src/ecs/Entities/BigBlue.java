package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.*;
import utils.EntityConstants;

import java.util.Map;

public class BigBlue {

    public static Entity create(Texture texture, float posX, float posY, KeyBinds keybinds){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity bigBlue = new Entity();

        bigBlue.add(new ecs.Components.BigBlue());
        bigBlue.add(new ecs.Components.Sprite());
        bigBlue.add(new ecs.Components.Appearance(texture));
        bigBlue.add(new ecs.Components.Position(posX - EntityConstants.rectSize / 2, posY - EntityConstants.rectSize / 2));
        bigBlue.add(new ecs.Components.Noun(NounType.BIGBLUE));
        bigBlue.add(new ecs.Components.Movement(Direction.STOP));
        bigBlue.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return  bigBlue;

    }
}
