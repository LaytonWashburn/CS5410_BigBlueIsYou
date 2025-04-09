package ecs.Systems;

import ecs.Components.Component;
import ecs.Components.Noun;
import ecs.Components.Property;
import ecs.Entities.Entity;
import level.Level;
import utils.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
        return renderEntity(elapsedTime);
    }

    /**
     * Method: Scan Game Area
     * Description:
     *  - Scans the game play area for a change of rules and add / removes components accordingly
     *  - Does this need to happen every update ?
     * */

    public ArrayList<Entity> renderEntity(double elapsedTime) {


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

    /**
     * Method: Rules.java
     * Description: Scans the game play area when movement is triggered
     */
    public void scanGamePlayArea(Entity[][] grid) {

        // Iterate through the grid game area
        for(int row = 0; row < level.getHeight(); row++) {

            for(int col = 0; col < level.getWidth(); col++) {

                Entity entity = grid[row][col]; // Grab the entity from the grid

//                java.lang.System.out.println("Row: " + row + " Col: " + col);
                // If the entity is TEXT and a VERB (IS)
                if(entity != null &&
                   entity.contains(ecs.Components.Text.class) &&
                   entity.get(ecs.Components.Text.class).getTextType() == TextType.VERB) {
                    java.lang.System.out.println("Row: " + row + " Col: " + col);
                    //java.lang.System.out.println("Caught Verb: " + entity.get(ecs.Components.Text.class).getTextType());
                    //java.lang.System.out.println(entity);
                    scanHorizontal(grid, row, col, level.getHeight(), level.getWidth());
                    scanVertical(grid, row, col, level.getHeight(), level.getWidth());

                }

            }

        }

    }

    /**
     * Method: Scan Horizontal
     * Description:
     */
    public void scanHorizontal(Entity[][] grid, int row, int col,  int maxRow, int maxCol) {

        // Check if there is a valid rule
        boolean rule = checkNext(grid, row, col - 1, maxRow, maxCol) && checkNext(grid, row, col + 1, maxRow, maxCol);

        // If there's a rule horizontal
        if(rule) {
            java.lang.System.out.println("A rule was detected");
            Entity center = grid[row][col]; // Grab the middle text entity, this might be unneeded
            Entity left = grid[row][col-1]; // Grab the left text entity
            Entity right = grid[row][col+1]; // Grab the right text entity

            addComponents(left, right, right.get(ecs.Components.Property.class)); // Apply the correct components

        }


    }

    /**
     * Method: Scan Vertical
     * Description:
     */
    public void scanVertical(Entity[][] grid, int row, int col,  int maxRow, int maxCol) {
        // Check if there is a valid rule
        boolean rule = checkNext(grid, row - 1, col, maxRow, maxCol) && checkNext(grid, row + 1, col, maxRow, maxCol);

        // If there's a rule horizontal, no need to grab the center VERB
        if(rule) {
            java.lang.System.out.println("A rule was detected: " + " Row: " + row + " Col: " + col);
            java.lang.System.out.println("Above: " + (row - 1) + " Col: " + col + " Below: " + (row + 1) + " Col: " + col);
            Entity up = grid[row-1][col]; // Grab the left text entity
            Entity down = grid[row+1][col]; // Grab the right text entity

            addComponents(up, down,  down.get(ecs.Components.Property.class)); // Apply the correct components

        }
    }


    /**
     * Method: Check next
     * @param grid - 2D Array representing the game area
     * @param maxRow -
     * @param maxCol -
     */
    public boolean checkNext(Entity[][] grid, int row, int col, int maxCol, int maxRow) {

        if(row >= 0 && row <= maxRow && col >= 0 && col <= maxCol ) { // If the matrix position falls withing bounds
            // Check if there's an entity and that entity is a TEXT
            return grid[row][col] != null && grid[row][col].contains(ecs.Components.Text.class);
        }

        return false;

    }

    /**
     * Method: Add Components
     * Description: Adds component to entity
     * @param textEntity - Entity to add the component to
     */
    public void addComponents(Entity textEntity, Entity second, Property properties) {
        // java.lang.System.out.println("In the add Components");
        // java.lang.System.out.println(textEntity);
        var nounText = textEntity.get(ecs.Components.Represent.class);

        for (Entity entity : entities.values()) {
            if (entity.contains(Noun.class) &&
                    entity.get(Noun.class).getNounType() == nounText.getNounType()) {
                java.lang.System.out.println("The Right Noun Type: " + nounText.getNounType() + " Match is: " + entity.get(Noun.class).getNounType());
                java.lang.System.out.println(second);
                var action = second.get(ecs.Components.Action.class).getAction();
                java.lang.System.out.println("Adding: " + action);
                var p = entity.contains(ecs.Components.Property.class) ? entity.get(ecs.Components.Property.class) : new Property() ;
                switch (action) {
                    case Action.STOP -> p.getProperties().add(Properties.STOP);
                    case Action.WIN -> p.getProperties().add(Properties.WIN);
                    case Action.KILL -> p.getProperties().add(Properties.KILL);
                    case Action.YOU -> p.getProperties().add(Properties.YOU);
                    case Action.PUSH -> p.getProperties().add(Properties.PUSHABLE);
                    case Action.SINK -> p.getProperties().add(Properties.SINK);
                }




            }
        }
    }
//    public void addComponents(Entity textEntity, ecs.Components.Property properties) {
//        java.lang.System.out.println("In the add Components");
//        java.lang.System.out.println(textEntity);
//        var nounText = textEntity.get(ecs.Components.Represent.class);
//
//        for(Entity entity : entities.values()) {
//
//            // If the noun type's match
//            if(entity.contains(ecs.Components.Noun.class) &&
//               entity.get(ecs.Components.Noun.class).getNounType() == nounText.getNounType()) {
//
//                Set<Properties> set = new HashSet<>(properties.getProperties());
//                if(entity.contains(ecs.Components.Property.class) &&
//                   !entity.get(ecs.Components.Property.class).getProperties().contains(properties)) {
//
//                }
//                entity.add(new ecs.Components.Property(Properties.MOVE, Properties.KILL));
//                entity.add(new Property(set.toArray(new Properties[0])));
//
//            }
//
//        }
//    }

    /**
     * Method: Remove Components
     * @param entity - Entity that to remove component from
     * @param component - Component to remove
     */
    public void removeComponents(Entity entity, Component component) {
        entity.remove(component.getClass());
    }


}
