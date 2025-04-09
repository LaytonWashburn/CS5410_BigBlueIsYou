/*
* Class: Render Sprite Class
* Date Started: 04-02-2025
* Date Finished: TBH
* Description:
*   - Renders static images
*
*/

package ecs.Systems;


import ecs.Components.StaticSprite;
import ecs.Entities.Entity;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import level.Level;
import org.joml.Vector2f;
import utils.EntityConstants;

import java.util.ArrayList;

public class RenderStaticSprite extends System{

    Graphics2D graphics;
    Rectangle rectangle;
    private Level level;

    public RenderStaticSprite(Graphics2D graphics, Level level){
        super(ecs.Components.StaticSprite.class);
        this.graphics = graphics;
        this.level = level;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {

        for(Entity entity : entities.values()){
            renderEntity(entity);
        }
        return new ArrayList<>(); // entities.values()
    }

    public void renderEntity(Entity entity){
        var texture = entity.get(StaticSprite.class).getTexture();
        var position = entity.get(ecs.Components.Position.class);

        float renderCenterPosX = -EntityConstants.rectSize * ((float) level.getWidth() / 2) + position.j*EntityConstants.rectSize + EntityConstants.rectSize/2;
        float renderCenterPosY = -EntityConstants.rectSize * ((float) level.getHeight() / 2) + position.i*EntityConstants.rectSize + EntityConstants.rectSize/2;

        rectangle = new Rectangle(renderCenterPosX - EntityConstants.rectSize/2, renderCenterPosY - EntityConstants.rectSize/2, EntityConstants.rectSize, EntityConstants.rectSize, 1.0f);
        graphics.draw(texture, rectangle, Color.WHITE);
    }


}
