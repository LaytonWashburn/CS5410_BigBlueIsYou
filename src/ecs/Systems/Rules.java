package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Graphics2D;
import level.Level;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class: Rules
 * Description:
 *  -
 */
public class Rules extends System{

    private final KeyBinds keybinds;
    private final Level level;

    public Rules(KeyBinds keyBinds, Level level) {
        super( // ecs.Components.Property.class,
                ecs.Components.Position.class); // Must have a location on the map
        this.level = level;
        this.keybinds = keyBinds;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {
        return scanGamePlayArea(elapsedTime);
    }

    /**
     * Method: Scan Game Area
     * Description:
     *  - Scans the game play area for a change of rules and add / removes components accordingly
     *  - Does this need to happen every update ?
     * */

    public ArrayList<Entity> scanGamePlayArea(double elapsedTime) {

        ArrayList<Entity> changed = new ArrayList<>(); // Array list to hold the changed entities


        for (Entity entity : entities.values()) {   // Ideally these don't have to be checked every update cycle, only after certain events are detected
            var position = entity.get(ecs.Components.Position.class);


            if(entity.contains(ecs.Components.Property.class)) {

                var property = entity.get(ecs.Components.Property.class);


                if (property.getProperties().contains(Properties.MOVE) && !entity.contains(ecs.Components.KeyboardControlled.class)) {
                    entity.add(new ecs.Components.Movement(Direction.STOP));
                    entity.add(new ecs.Components.KeyboardControlled(
                            Map.of(keybinds.UP, Direction.UP,
                                    keybinds.DOWN, Direction.DOWN,
                                    keybinds.RIGHT, Direction.RIGHT,
                                    keybinds.LEFT, Direction.LEFT
                            )
                    ));

                    changed.add(entity);
                }
                if (!property.getProperties().contains(Properties.MOVE) && entity.contains(ecs.Components.KeyboardControlled.class)) {
                    entity.remove(ecs.Components.KeyboardControlled.class);
                    entity.remove(ecs.Components.Movement.class);

                    changed.add(entity);
                }
            }

        }



        return changed;

    }


    public void updateRule(Entity entity, TextType textType, NounType nounType) {

//        switch (textType) {
//            case
//        }

    }




}
