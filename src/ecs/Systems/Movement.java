package ecs.Systems;

import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import utils.Direction;
import utils.EntityConstants;

import java.util.ArrayList;
import java.util.Map;

public class Movement extends System{

    Graphics2D graphics;

    public Movement(Graphics2D graphics){
        super(ecs.Components.Position.class);

        this.graphics = graphics;

    }
    @Override
    public ArrayList<Entity> update(double elapsedTime) {

        Entity movable = findMovable(); // Get the movable entity

        for (Entity entity : entities.values()) { // Loop through the entities

            if(entity == movable) { // If movable entity

                var position = entity.get(ecs.Components.Position.class);
                var moving = entity.get(ecs.Components.Movement.class);

                switch (moving.moving){
                    case Direction.UP:
                        checkCollisionAt(entity, position.posX, position.posY - EntityConstants.rectSize, moving.moving);
                        position.posY -= EntityConstants.rectSize;
                        break;
                    case Direction.DOWN:
                        checkCollisionAt(entity, position.posX, position.posY + EntityConstants.rectSize, moving.moving);
                        position.posY += EntityConstants.rectSize;
                        break;
                    case Direction.LEFT:
                        checkCollisionAt(entity, position.posX - EntityConstants.rectSize, position.posY,moving.moving);
                        position.posX -= EntityConstants.rectSize;
                        break;
                    case Direction.RIGHT:
                        checkCollisionAt(entity, position.posX + EntityConstants.rectSize, position.posY, moving.moving);
                        position.posX += EntityConstants.rectSize;
                        break;
                    default:
                }

            }
       }
        return new ArrayList<>(entities.values());
    }

    /**
     * Method: Check Collision At
     * Description: Checks is the locations collide
     * @param movingEntity - Entity
     * @param targetX - Target x coordinate
     * @param targetY - Target y coordinate
     * @return - True for collision and false for no collision
     */
    public boolean checkCollisionAt(Entity movingEntity, float targetX, float targetY, Direction movingDirection) {

        var position = movingEntity.get(ecs.Components.Position.class);

        for (Entity otherEntity : entities.values()) {

            if(otherEntity != movingEntity) {

                var otherPosition = otherEntity.get(ecs.Components.Position.class);
                boolean x = false;
                boolean y = false;

                switch(movingDirection) {
                    case Direction.DOWN:
                        y = targetY >= otherPosition.posY - EntityConstants.rectSize &&
                            targetY <= otherPosition.posY &&
                            targetX >= otherPosition.posX - EntityConstants.rectSize &&
                            targetX < otherPosition.posX;
                        break;
                    case Direction.UP:
                        y = targetY <= otherPosition.posY &&
                            targetY >= otherPosition.posY - EntityConstants.rectSize &&
                            targetX >= otherPosition.posX - EntityConstants.rectSize &&
                            targetX < otherPosition.posX;
                        break;
                    case Direction.LEFT:
                        x = targetY <= otherPosition.posY &&
                            targetY >= otherPosition.posY + EntityConstants.rectSize &&
                            targetX <= otherPosition.posX - EntityConstants.rectSize  &&
                            targetX > otherPosition.posX ;
                        break;
                    case Direction.RIGHT:
                        x = targetY <= otherPosition.posY &&
                            targetY >= otherPosition.posY &&
                            targetX >= otherPosition.posX &&
                            targetX <= otherPosition.posX + EntityConstants.rectSize;

                        break;
                    default:
                }
                 if (x || y) {

                    switch (movingDirection){
                        case Direction.UP:
                            checkCollisionAt(otherEntity, targetX, otherPosition.posY - EntityConstants.rectSize, movingDirection); // Recursive call
                            otherPosition.posY -= EntityConstants.rectSize;
                            break;
                        case Direction.DOWN:
                            checkCollisionAt(otherEntity, targetX, otherPosition.posY - EntityConstants.rectSize, movingDirection); // Recursive call
                            otherPosition.posY += EntityConstants.rectSize;
                            break;
                        case Direction.LEFT:
                            // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, movingDirection); // Recursive call
                            otherPosition.posX -= EntityConstants.rectSize;
                            break;
                        case Direction.RIGHT:
                            // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, movingDirection); // Recursive call
                            otherPosition.posX += EntityConstants.rectSize;
                            break;
                        default:
                    }


                }

            }



        }

        return false; // No collision
    }


//    public boolean checkCollisionAt(Entity movingEntity, float targetX, float targetY, Direction movingDirection) {
//        float squareSideLength = EntityConstants.rectSize;
//
//        for (Entity otherEntity : entities.values()) {
//
//            if (otherEntity == movingEntity) continue;
//
//            Position otherPosition = otherEntity.get(ecs.Components.Position.class);
//            float otherX = otherPosition.posX;
//            float otherY = otherPosition.posY;
//
//            boolean xOverlap = targetX < otherX + squareSideLength && targetX + squareSideLength > otherX;
//            boolean yOverlap = targetY < otherY + squareSideLength && targetY + squareSideLength > otherY;
//
//            if (xOverlap && yOverlap) {
//                java.lang.System.out.println("Collision at (" + targetX + ", " + targetY + ") with (" + otherX + ", " + otherY + ")");
//
//                float pushAmount = squareSideLength;
//                float nextOtherX = otherX;
//                float nextOtherY = otherY;
//
//                switch (movingDirection){
//                    case Direction.UP:
//                        // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, movingDirection); // Recursive call
//                        otherPosition.posY -= EntityConstants.rectSize;
//                        break;
//                    case Direction.DOWN:
//                        // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, movingDirection); // Recursive call
//                        otherPosition.posY += EntityConstants.rectSize;
//                        break;
//                    case Direction.LEFT:
//                        // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, movingDirection); // Recursive call
//                        otherPosition.posX -= EntityConstants.rectSize;
//                        break;
//                    case Direction.RIGHT:
//                        // checkCollisionAt(otherEntity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, movingDirection); // Recursive call
//                        otherPosition.posX += EntityConstants.rectSize;
//                        break;
//                    default:
//                }
//
//                if (!checkCollisionAt(otherEntity, nextOtherX, nextOtherY, Direction.STOP)) {
//                    otherPosition.posX = nextOtherX;
//                    otherPosition.posY = nextOtherY;
//                    return true; // Collision handled by push
//                } else {
//                    return true; // Collision, push failed
//                }
//            }
//        }
//        return false; // No collision
//    }

    /**
     * Returns a collection of all the movable entities.
     */
    private Entity findMovable() {

        for (var entity : entities.values()) {
            if (entity.contains(ecs.Components.KeyboardControlled.class)) {
                return entity;
            }
        }

        return null;
    }




}
