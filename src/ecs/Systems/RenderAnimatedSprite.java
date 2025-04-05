/*
 * Class: Render Animated Sprite Class
 * Date Started: 04-02-2025
 * Date Finished: TBH
 * Description:
 *   -  Renders animated sprites
 *
 */

package ecs.Systems;

import ecs.Entities.Entity;

import edu.usu.graphics.*;

import java.util.ArrayList;

public class RenderAnimatedSprite extends System{

    private Graphics2D graphics;

    public RenderAnimatedSprite(Graphics2D graphics) {
        super(ecs.Components.Sprite.class);

        this.graphics = graphics;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {

        for (Entity entity : entities.values()) {
            var animatedSprite = entity.get(ecs.Components.Sprite.class);

            animatedSprite.animatedSprite.update(elapsedTime);
            animatedSprite.animatedSprite.draw(graphics, edu.usu.graphics.Color.WHITE);
        }

        return new ArrayList<>(entities.values());
    }
}
