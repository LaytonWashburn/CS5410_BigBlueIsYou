package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import utils.Direction;
import utils.EntityConstants;

import java.util.ArrayList;

public class Movement extends System{

    Graphics2D graphics;

    public Movement(Graphics2D graphics){
        super(ecs.Components.KeyboardControlled.class,
                ecs.Components.BigBlue.class);

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
        var position = entity.get(ecs.Components.Position.class);
        var moving = entity.get(ecs.Components.Movement.class);

        switch (moving.moving){
            case Direction.UP:
                position.posY -= EntityConstants.rectSize;
                break;
            case Direction.DOWN:
                position.posY += EntityConstants.rectSize;
                break;
            case Direction.LEFT:
                position.posX -= EntityConstants.rectSize;
                break;
            case Direction.RIGHT:
                position.posX += EntityConstants.rectSize;
                break;
            default:
        }
    }
}
