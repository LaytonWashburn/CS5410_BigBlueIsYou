package ecs.Systems;

import core.GameSounds;
import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import edu.usu.utils.Tuple2;
import utils.Direction;
import utils.Properties;

import java.util.ArrayList;

public class Movement extends System{

    Graphics2D graphics;
    GameSounds gameSounds;
    private ArrayList<Tuple2<Entity, Boolean>> moved;

    public Movement(Graphics2D graphics, GameSounds gameSounds){
        super(ecs.Components.Position.class);

        this.graphics = graphics;
        this.gameSounds = gameSounds;

    }
    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) throws CloneNotSupportedException {
        moved = new ArrayList<>();
        ArrayList<Entity> movables = findMovable(); // Get the movable entities

        for (Entity entity : movables) { // Loop through the entities
            var properties = entity.get(ecs.Components.Property.class);
            var position = entity.get(ecs.Components.Position.class);
            var moving = entity.get(ecs.Components.Movement.class);

            boolean hasMoveProperty = properties.getProperties().contains(Properties.MOVE);

            if (hasMoveProperty) {
                switch (moving.moving){
                    case Direction.UP:
                        if (!checkCollisionAt(position.i-1, position.j, moving.moving)) {
                            moved.add(new Tuple2<>(entity.clone(), false));
                            position.i -= 1;
                            gameSounds.movement.play();
                        }
                        break;
                    case Direction.DOWN:
                        if (!checkCollisionAt(position.i+1, position.j, moving.moving)) {
                            moved.add(new Tuple2<>(entity.clone(), false));
                            position.i += 1;
                            gameSounds.movement.play();
                        }
                        break;
                    case Direction.LEFT:
                        if (!checkCollisionAt(position.i, position.j-1, moving.moving)) {
                            moved.add(new Tuple2<>(entity.clone(), false));
                            position.j -= 1;
                            gameSounds.movement.play();
                        }
                        break;
                    case Direction.RIGHT:
                        if (!checkCollisionAt(position.i, position.j+1, moving.moving)) {
                            moved.add(new Tuple2<>(entity.clone(), false));
                            position.j += 1;
                            gameSounds.movement.play();
                        }
                        break;
                    default:
                }
            }

        }
        return moved;
    }

    /**
     * Method: Check Collision At
     * Description: Checks is the locations collide
     * @param targetI - Target i coordinate
     * @param targetJ - Target j coordinate
     * @return - True for collision and false for no collision
     */
    public boolean checkCollisionAt(float targetI, float targetJ, Direction movingDirection) throws CloneNotSupportedException {

        boolean immovableHit = false;

        for (Entity otherEntity : entities.values()) {

            if(otherEntity.contains(ecs.Components.Property.class)) {

                var otherProperties = otherEntity.get(ecs.Components.Property.class);

                if(!otherProperties.getProperties().contains(Properties.MOVE)) {

                    var otherPosition = otherEntity.get(ecs.Components.Position.class);

                    if (targetI == otherPosition.i && targetJ == otherPosition.j && otherProperties.getProperties().contains(Properties.STOP)) {  // If there is a collision and the other entity is immovable
                        immovableHit = true;
                        gameSounds.wallHit.play();
                    }

                    if (targetI == otherPosition.i && targetJ == otherPosition.j && otherProperties.getProperties().contains(Properties.PUSHABLE) && !immovableHit) { // If there is a collision and the other entity is pushable

                        switch (movingDirection) {
                            case Direction.UP:
                                immovableHit = checkCollisionAt(otherPosition.i - 1, otherPosition.j, movingDirection); // Recursive call
                                if (!immovableHit) {
                                    moved.add(new Tuple2<>(otherEntity.clone(), false));
                                    otherPosition.i -= 1;
                                }
                                break;
                            case Direction.DOWN:
                                immovableHit = checkCollisionAt(otherPosition.i + 1, otherPosition.j, movingDirection); // Recursive call
                                if (!immovableHit) {
                                    moved.add(new Tuple2<>(otherEntity.clone(), false));
                                    otherPosition.i += 1;
                                }
                                break;
                            case Direction.LEFT:
                                immovableHit = checkCollisionAt(otherPosition.i, otherPosition.j - 1, movingDirection); // Recursive call
                                if (!immovableHit) {
                                    moved.add(new Tuple2<>(otherEntity.clone(), false));
                                    otherPosition.j -= 1;
                                }
                                break;
                            case Direction.RIGHT:
                                immovableHit = checkCollisionAt(otherPosition.i, otherPosition.j + 1, movingDirection); // Recursive call
                                if (!immovableHit) {
                                    moved.add(new Tuple2<>(otherEntity.clone(), false));
                                    otherPosition.j += 1;
                                }
                                break;
                            default:
                        }

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