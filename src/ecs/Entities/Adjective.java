package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.*;

import java.util.Map;

public class Adjective {
    public static Entity create(Texture texture, int i, int j, KeyBinds keybinds){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity adjective = new Entity();

        adjective.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        adjective.add(new ecs.Components.Position(i, j));
        adjective.add(new ecs.Components.Noun(NounType.TEXT));
        adjective.add(new ecs.Components.Text(TextType.ADJECTIVE));
        adjective.add(new ecs.Components.Property());
        adjective.add(new ecs.Components.Movement(Direction.STOP));
        adjective.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT,
                        keybinds.UNDO, Direction.UNDO
                )
        ));

        return adjective;

    }
}
