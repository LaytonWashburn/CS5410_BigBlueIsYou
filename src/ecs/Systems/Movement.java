package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;

public class Movement extends System{

    Graphics2D graphics;

    public Movement(Graphics2D graphics){
        super(ecs.Components.KeyboardControlled.class,
                ecs.Components.BigBlue.class);

        this.graphics = graphics;

    }
    @Override
    public void update(double elapsedTime) {


        for(Entity entity : entities.values()){
            renderEntity(entity);
        }

    }

    public void renderEntity(Entity entity){

    }
}
