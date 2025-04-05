/*
* Class: Render Sprite Class
* Date Started: 04-02-2025
* Date Finished: TBH
* Description:
*   - Renders static images
*
*/

package ecs.Systems;


import ecs.Entities.Entity;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import utils.EntityConstants;

import java.util.ArrayList;

public class RenderSprite extends System{

    Graphics2D graphics;
    Rectangle rectangle;
    public RenderSprite(Graphics2D graphics){
        super(ecs.Components.BigBlue.class,
              ecs.Components.Sprite.class);
        this.graphics = graphics;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {

        for(Entity entity : entities.values()){
            renderEntity(entity);
        }
        return new ArrayList<>(entities.values());
    }

    public void renderEntity(Entity entity){
        var texture = entity.get(ecs.Components.Appearance.class).getTexture();
        var position = entity.get(ecs.Components.Position.class);
        rectangle = new Rectangle(position.posX, position.posY, EntityConstants.rectSize, EntityConstants.rectSize, 1.0f);
        graphics.draw(texture, rectangle, Color.WHITE);
    }


}
