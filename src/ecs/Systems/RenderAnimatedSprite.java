/*
 * Class: Render Animated Sprite Class
 * Date Started: 04-02-2025
 * Date Finished: TBH
 * Description:
 *   -  Renders animated sprites
 *
 */

package ecs.Systems;

public class RenderAnimatedSprite extends System{

    public RenderAnimatedSprite(){

        super(ecs.Components.Sprite.class,
              ecs.Components.Animated.class);
    }

    @Override
    public void update(double elapsedTime) {

    }
}
