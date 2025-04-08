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

            if(entity == movable) { // If movable

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
     * @param entity - Entity
     * @param targetX - Target x coordinate
     * @param targetY - Target y coordinate
     * @return - True for collision and false for no collision
     */
    public boolean checkCollisionAt(Entity movingEntity, float targetX, float targetY, Direction movingDirection) {
        float squareSideLength = EntityConstants.rectSize;

        for (Entity otherEntity : entities.values()) {
            if (otherEntity == movingEntity) continue;

            Position otherPosition = otherEntity.get(ecs.Components.Position.class);
            float otherX = otherPosition.posX;
            float otherY = otherPosition.posY;

            boolean xOverlap = targetX < otherX + squareSideLength && targetX + squareSideLength > otherX;
            boolean yOverlap = targetY < otherY + squareSideLength && targetY + squareSideLength > otherY;

            if (xOverlap && yOverlap) {
                java.lang.System.out.println("Collision at (" + targetX + ", " + targetY + ") with (" + otherX + ", " + otherY + ")");

                float pushAmount = squareSideLength;
                float nextOtherX = otherX;
                float nextOtherY = otherY;

                switch (movingDirection) {
                    case UP: nextOtherY -= pushAmount; break;
                    case DOWN: nextOtherY += pushAmount; break;
                    case LEFT: nextOtherX -= pushAmount; break;
                    case RIGHT: nextOtherX += pushAmount; break;
                    default: return true; // Collision, no push
                }

                if (!checkCollisionAt(otherEntity, nextOtherX, nextOtherY, Direction.STOP)) {
                    otherPosition.posX = nextOtherX;
                    otherPosition.posY = nextOtherY;
                    return true; // Collision handled by push
                } else {
                    return true; // Collision, push failed
                }
            }
        }
        return false; // No collision
    }
//    public boolean checkCollisionAt(Entity entity, float targetX, float targetY, Direction moving) {
//
//        for(Entity other : entities.values()) {
//
//            //if (other == entity) continue; // If the entity is itself, jump to next loop
//
//            Position otherPosition = other.get(ecs.Components.Position.class);
//            float otherX = otherPosition.posX;
//            float otherY = otherPosition.posY;
//
//            // Check for X-axis overlap
//            boolean xOverlap = targetX < otherX && targetX  > otherX;
//
//            // Check for Y-axis overlap
//            boolean yOverlap = targetY < otherY && targetY  > otherY;
//
//            if (xOverlap && yOverlap) { // If there is a collision
//                java.lang.System.out.println("Potential collision for entity " + entity.getId() +
//                        " at (" + targetX + ", " + targetY + ") with entity " + other.getId());
//
//                switch (moving){
//                    case Direction.UP:
//                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, moving); // Recursive call
//                        otherPosition.posY -= EntityConstants.rectSize;
//                        break;
//                    case Direction.DOWN:
//                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, moving); // Recursive call
//                        otherPosition.posY += EntityConstants.rectSize;
//                        break;
//                    case Direction.LEFT:
//                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, moving); // Recursive call
//                        otherPosition.posX -= EntityConstants.rectSize;
//                        break;
//                    case Direction.RIGHT:
//                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, moving); // Recursive call
//                        otherPosition.posX += EntityConstants.rectSize;
//                        break;
//                    default:
//                }
//                return true;
//            } else {
//                // java.lang.System.out.println("No collision");
//            }
//        }
//        return false;
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
