/*
 * Class: Render Animated Sprite Class
 * Date Started: 04-02-2025
 * Date Finished: TBH
 * Description:
 *   -  Renders animated sprites
 *
 */

package ecs.Systems;

import ecs.Components.AnimatedSprite;
import ecs.Components.Position;
import ecs.Entities.Entity;

import edu.usu.graphics.*;
import org.joml.Vector2f;

import java.util.ArrayList;

public class RenderAnimatedSprite extends System{

    private Graphics2D graphics;

    public RenderAnimatedSprite(Graphics2D graphics) {
        super(AnimatedSprite.class);

        this.graphics = graphics;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {   // Both updates and renders the animated sprites. These could be separated into different Systems

        for (Entity entity : entities.values()) {
            var animatedSprite = entity.get(AnimatedSprite.class);
            var position = entity.get(Position.class);

            animatedSprite.animatedSprite.setCenter(new Vector2f(position.posX, position.posY));    // Sync up the entity position with sprite position

            animatedSprite.animatedSprite.update(elapsedTime);
            animatedSprite.animatedSprite.draw(graphics, edu.usu.graphics.Color.WHITE);
        }

        return new ArrayList<>(entities.values());
    }
}
