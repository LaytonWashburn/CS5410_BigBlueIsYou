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
import edu.usu.utils.Tuple2;
import level.Level;
import org.joml.Vector2f;
import utils.EntityConstants;

import java.util.ArrayList;

public class RenderAnimatedSprite extends System{

    private Graphics2D graphics;

    private Level level;

    public RenderAnimatedSprite(Graphics2D graphics, Level level) {
        super(AnimatedSprite.class);

        this.graphics = graphics;
        this.level = level;
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) {   // Both updates and renders the animated sprites. These could be separated into different Systems

        for (Entity entity : entities.values()) {
            var animatedSprite = entity.get(AnimatedSprite.class);
            var position = entity.get(Position.class);

            animatedSprite.animatedSprite.setCenter(new Vector2f((-EntityConstants.rectSize * ((float) level.getWidth() / 2) + position.j*EntityConstants.rectSize + EntityConstants.rectSize/2),
                    (-EntityConstants.rectSize * ((float) level.getHeight() / 2)) + position.i*EntityConstants.rectSize + EntityConstants.rectSize/2));    // Sync up the entity position with sprite position

            animatedSprite.animatedSprite.update(elapsedTime);
            animatedSprite.animatedSprite.draw(graphics, edu.usu.graphics.Color.WHITE);
        }

        return new ArrayList<>(); // entities.values()
    }
}
