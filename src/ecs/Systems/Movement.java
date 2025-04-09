package ecs.Systems;

import ecs.Components.Position;
import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import utils.Direction;
import utils.EntityConstants;
import utils.Properties;

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
        java.lang.System.out.println("In the Movement update");
        ArrayList<Entity> movables = findMovable(); // Get the movable entities

        for (Entity entity : movables) { // Loop through the entities
            java.lang.System.out.println(entity);
            var position = entity.get(ecs.Components.Position.class);
            var moving = entity.get(ecs.Components.Movement.class);

            switch (moving.moving){
                case Direction.UP:
                    if (!checkCollisionAt(position.i-1, position.j, moving.moving))
                        position.i -= 1;
                    break;
                case Direction.DOWN:
                    if (!checkCollisionAt(position.i+1, position.j, moving.moving))
                        position.i += 1;
                    break;
                case Direction.LEFT:
                    if (!checkCollisionAt(position.i, position.j-1, moving.moving))
                        position.j -= 1;
                    break;
                case Direction.RIGHT:
                    if (!checkCollisionAt(position.i, position.j+1, moving.moving))
                        position.j += 1;
                    break;
                default:
            }
       }
        return new ArrayList<>(entities.values());
    }

    /**
     * Method: Check Collision At
     * Description: Checks is the locations collide
     * @param targetI - Target i coordinate
     * @param targetJ - Target j coordinate
     * @return - True for collision and false for no collision
     */
    public boolean checkCollisionAt(float targetI, float targetJ, Direction movingDirection) {

        boolean immovableHit = false;

        for (Entity otherEntity : entities.values()) {

            var otherProperties = otherEntity.get(ecs.Components.Property.class);

            if(!otherProperties.getProperties().contains(Properties.MOVE)) {

                var otherPosition = otherEntity.get(ecs.Components.Position.class);

                if (targetI == otherPosition.i && targetJ == otherPosition.j && otherProperties.getProperties().contains(Properties.STOP)) {  // If there is a collision and the other entity is immovable
                    immovableHit = true;
                }

                if (targetI == otherPosition.i && targetJ == otherPosition.j && otherProperties.getProperties().contains(Properties.PUSHABLE) && !immovableHit) { // If there is a collision and the other entity is pushable

                    switch (movingDirection) {
                        case Direction.UP:
                            immovableHit = checkCollisionAt(otherPosition.i - 1, otherPosition.j, movingDirection); // Recursive call
                            if (!immovableHit) {
                                otherPosition.i -= 1;
                            }
                            break;
                        case Direction.DOWN:
                            immovableHit = checkCollisionAt(otherPosition.i + 1, otherPosition.j, movingDirection); // Recursive call
                            if (!immovableHit) {
                                otherPosition.i += 1;
                            }
                            break;
                        case Direction.LEFT:
                            immovableHit = checkCollisionAt(otherPosition.i, otherPosition.j - 1, movingDirection); // Recursive call
                            if (!immovableHit) {
                                otherPosition.j -= 1;
                            }
                            break;
                        case Direction.RIGHT:
                            immovableHit = checkCollisionAt(otherPosition.i, otherPosition.j + 1, movingDirection); // Recursive call
                            if (!immovableHit) {
                                otherPosition.j += 1;
                            }
                            break;
                        default:
                    }

                }

            }

        }

        return immovableHit;
    }

    /**
     * Returns a collection of all the movable entities.
     */
    private ArrayList<Entity> findMovable() {

        ArrayList<Entity> movables = new ArrayList<>();

        for (var entity : entities.values()) {
            if (entity.contains(ecs.Components.KeyboardControlled.class)) {
                movables.add(entity);
            }
        }

        return movables;
    }




}
