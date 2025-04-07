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

        Entity character = findMovable(entities);

        for (Entity entity : entities.values()) {

            if(entity == character) {
                var position = entity.get(ecs.Components.Position.class);
                var moving = entity.get(ecs.Components.Movement.class);

                switch (moving.moving){
                    case Direction.UP:
                        checkCollisionAt(entity, position.posX, position.posY, moving.moving);
                        position.posY -= EntityConstants.rectSize;
                        break;
                    case Direction.DOWN:
                        checkCollisionAt(entity, position.posX, position.posY, moving.moving);
                        position.posY += EntityConstants.rectSize;
                        break;
                    case Direction.LEFT:
                        checkCollisionAt(entity, position.posX, position.posY,moving.moving);
                        position.posX -= EntityConstants.rectSize;
                        break;
                    case Direction.RIGHT:
                        checkCollisionAt(entity, position.posX, position.posY, moving.moving);
                        position.posX += EntityConstants.rectSize;
                        break;
                    default:
                }

            }
        }
        return new ArrayList<>(entities.values());
    }



    public void collide(Entity entity) {
        var position = entity.get(ecs.Components.Position.class);

        for (Entity other : entities.values()) {
            if (other == entity) continue; // Skip checking against itself

            var otherPosition = other.get(ecs.Components.Position.class);

            boolean collides = position.posX < otherPosition.posX + EntityConstants.rectSize &&
                    position.posX + EntityConstants.rectSize > otherPosition.posX &&
                    position.posY < otherPosition.posY + EntityConstants.rectSize &&
                    position.posY + EntityConstants.rectSize > otherPosition.posY;

            if (collides) {
                java.lang.System.out.println("Collision detected between entity " + entity.getId() +
                        " and entity " + other.getId());
                break; // Optional: stop after first collision
            }
        }
    }


    /**
     * Returns a collection of all the movable entities.
     */
    private Entity findMovable(Map<Long, Entity> entities) {

        for (var entity : entities.values()) {
            if (entity.contains(ecs.Components.KeyboardControlled.class)) {
                return entity;
            }
        }

        return null;
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

        for (Entity other : entities.values()) { // Loop through all entities
            if (other == entity) continue; // If the entity is itself, jump to next loop

            Position otherPosition = other.get(ecs.Components.Position.class);

            // Check if the target position of the 'entity' overlaps with the 'other' entity's position
            boolean collides = targetX < otherPosition.posX + EntityConstants.rectSize &&
                    targetX + EntityConstants.rectSize > otherPosition.posX &&
                    targetY < otherPosition.posY + EntityConstants.rectSize &&
                    targetY + EntityConstants.rectSize > otherPosition.posY;

            if (collides) {
                java.lang.System.out.println("Potential collision for entity " + entity.getId() +
                        " at (" + targetX + ", " + targetY + ") with entity " + other.getId());

                switch (moving){
                    case Direction.UP:
                        otherPosition.posY -= EntityConstants.rectSize;
                        break;
                    case Direction.DOWN:
                        otherPosition.posY += EntityConstants.rectSize;
                        break;
                    case Direction.LEFT:
                        otherPosition.posX -= EntityConstants.rectSize;
                        break;
                    case Direction.RIGHT:
                        otherPosition.posX += EntityConstants.rectSize;
                        break;
                    default:
                }
                return true;
            }
        }
        return false;
    }

}
