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
    public boolean checkCollisionAt(Entity entity, float targetX, float targetY, Direction moving) {

        for(Entity other : entities.values()) {

            //if (other == entity) continue; // If the entity is itself, jump to next loop

            Position otherPosition = other.get(ecs.Components.Position.class);
            // Check if the target position of the 'entity' overlaps with the 'other' entity's position
            boolean collides = targetX == otherPosition.posX &&
                               targetY == otherPosition.posY;

            java.lang.System.out.println("Other position pos x: " + otherPosition.posX);
            java.lang.System.out.println("Other position pos y: " + otherPosition.posY);


            java.lang.System.out.println("Target position pos x: " + targetX);
            java.lang.System.out.println("Target position pos y: " + targetY);

            if (collides) { // If there is a collision
                java.lang.System.out.println("Potential collision for entity " + entity.getId() +
                        " at (" + targetX + ", " + targetY + ") with entity " + other.getId());

                switch (moving){
                    case Direction.UP:
                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, moving); // Recursive call
                        otherPosition.posY -= EntityConstants.rectSize;
                        break;
                    case Direction.DOWN:
                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, moving); // Recursive call
                        otherPosition.posY += EntityConstants.rectSize;
                        break;
                    case Direction.LEFT:
                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY - EntityConstants.rectSize, moving); // Recursive call
                        otherPosition.posX -= EntityConstants.rectSize;
                        break;
                    case Direction.RIGHT:
                        // checkCollisionAt(entity, otherPosition.posX, otherPosition.posY + EntityConstants.rectSize, moving); // Recursive call
                        otherPosition.posX += EntityConstants.rectSize;
                        break;
                    default:
                }
                return true;
            } else {
                java.lang.System.out.println("No collision");
            }
        }
        return false;
    }


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
