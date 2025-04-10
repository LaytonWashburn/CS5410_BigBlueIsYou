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
                if(entity != null)
                {
                    // entity.contains(ecs.Components.Text.class) && // Only TEXT entities can get in
                    if(entity.get(ecs.Components.Text.class).getTextType() == TextType.VERB) { // VERB IS

                        // java.lang.System.out.println("IS");
                        scanHorizontal(grid, row, col, level.getHeight(), level.getWidth());
                        scanVertical(grid, row, col, level.getHeight(), level.getWidth());

                    }

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

        Entity left = grid[row][col-1]; // Grab the left text entity
        Entity right = grid[row][col+1]; // Grab the right text entity

        // If there's a rule horizontal
        if(rule) {
            // java.lang.System.out.println("A rule was detected Horizontally");
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

        Entity up = grid[row-1][col]; // Grab the left text entity
        Entity down = grid[row+1][col]; // Grab the right text entity

        // If there's a rule horizontal, no need to grab the center VERB
        if(rule) {
            // java.lang.System.out.println("A rule was detected Vertically");
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
            return grid[row][col] != null; // Only TEXT entities in the grid // && grid[row][col].contains(ecs.Components.Text.class);
        }

        return false;

    }

    /**
     * Method: Add Components
     * Description: Adds component to entity
     * @param textEntity - Entity to add the component to
     */
    public void addComponents(Entity textEntity, Entity second, Property properties) {

        var textNoun = textEntity.get(ecs.Components.Represent.class); // Get the Noun type for what the text represents

        for (Entity entity : entities.values()) { // Loop through the entities
            if (entity.contains(Noun.class) &&
                    entity.get(Noun.class).getNounType() == textNoun.getNounType()) {

                // java.lang.System.out.println(entity.get(ecs.Components.Noun.class).getNounType());

                var action = second.get(ecs.Components.Action.class).getAction();

                // java.lang.System.out.println("Here is the associated action: " + action);

                var p = entity.contains(ecs.Components.Property.class) ? entity.get(ecs.Components.Property.class) : new Property();
                switch (action) {
                    case Action.STOP :
                        p.getProperties().add(Properties.STOP);
                        break;
                    case Action.WIN :
                        p.getProperties().add(Properties.WIN);
                        break;
                    case Action.KILL :
                        p.getProperties().add(Properties.KILL);
                        break;
                    case Action.YOU :
                        p.getProperties().add(Properties.YOU);
                        break;
                    case Action.PUSH :
                        p.getProperties().add(Properties.PUSHABLE);
                        break;
                    case Action.SINK :
                        p.getProperties().add(Properties.SINK);
                        break;
                }

                if(!entity.contains(ecs.Components.Property.class)) {
                    entity.add(p);
                }



            }
        }
    }

//    /**
//     * Method: Remove Components
//     * @param entity - Entity that to remove component from
//     * @param component - Component to remove
//     */
//    public void removeComponents(Entity entity, Component component) {
//
//        if(entity != null && entity.get(ecs.Components.Text.class).getTextType() != TextType.VERB) { // If noun or adjective
//
//            if(entity.get(ecs.Components.Text.class).getTextType() == TextType.NOUN) {
//                for(Entity e : entities.values()) {
//                    if (e.contains(ecs.Components.Noun.class) &&
//                        e.get(ecs.Components.Noun.class).getNounType() == entity.get(ecs.Components.Represent.class).getNounType()) {
//                        e.remove();
//                    }
//                }
//            }
//
//            if(entity.get(ecs.Components.Text.class).getTextType() == TextType.ADJECTIVE) {
//
//            }
//
//        }
//
//        //entity.remove(component.getClass());
//    }


}
