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
                    if(entity.get(ecs.Components.Text.class).getTextType() != TextType.VERB) { // VERB IS
                        removeComponents(grid[row][col]);
                    }

                }

            }

        }

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
                        // java.lang.System.out.println("Original is: Row: " + row + " Col: " + col);
                        scanVertical(grid, row, col, level.getHeight(), level.getWidth());
                        scanHorizontal(grid, row, col, level.getHeight(), level.getWidth());
                    }

                }

            }

        }

    }

    /**
     * Method: Scan Horizontal
     * Description:
     */
    public boolean scanHorizontal(Entity[][] grid, int row, int col,  int maxRow, int maxCol) {

        // Check if there is a valid rule
        boolean leftR = checkNext(grid, row, col - 1, maxRow, maxCol);
        boolean rightR = checkNext(grid, row, col + 1, maxRow, maxCol);
        boolean rule = true;

        Entity left = grid[row][col-1]; // Grab the left text entity
        Entity right = grid[row][col+1]; // Grab the right text entity

        // If there's a rule horizontal
        if(leftR && rightR) {
            //java.lang.System.out.println("A rule was detected Horizontally");
            addComponents(left, right); // Apply the correct components
            return true;
        }
        return false;
    }

    /**
     * Method: Scan Vertical
     * Description:
     */
    public boolean scanVertical(Entity[][] grid, int row, int col,  int maxRow, int maxCol) {
        // java.lang.System.out.println("Scanning Vertically");
        // Check if there is a valid rule

        boolean leftR = checkNext(grid, row - 1, col, maxRow, maxCol);
        boolean rightR =checkNext(grid, row + 1, col, maxRow, maxCol);
        boolean rule = true;
        java.lang.System.out.println("\n\n");
        Entity up = grid[row-1][col]; // Grab the left text entity
        Entity down = grid[row+1][col]; // Grab the right text entity

        // If there's a rule horizontal, no need to grab the center VERB
        if(leftR && rightR) {
            // java.lang.System.out.println("A rule was detected Vertically");
            addComponents(up, down); // Apply the correct components
            return true;
        }
        return false;
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
            // java.lang.System.out.println("Checking: Row: " + row + " Col: + " + col + " is: " + grid[row][col]);
            return grid[row][col] != null; // Only TEXT entities in the grid // && grid[row][col].contains(ecs.Components.Text.class);
        }

        return false;

    }

    /**
     * Method: Add Components
     * Description: Adds component to entity
     * @param textEntity - Entity to add the component to
     */
    public void addComponents(Entity textEntity, Entity second) {

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
                        p.getProperties().add(Properties.MOVE);
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

    public void removeComponents(Entity entity) {
        if(entity == null) {
            return;
        }
        if(entity.get(ecs.Components.Text.class).getTextType() == TextType.NOUN) {
            java.lang.System.out.println("Here is the entity we are looking at: " + entity);
            for(Entity removeEntity : entities.values()) {
                java.lang.System.out.println("Looping through entity: " + removeEntity);
                if(removeEntity.contains(ecs.Components.Noun.class) &&
                   removeEntity.get(ecs.Components.Noun.class).getNounType() == entity.get(ecs.Components.Noun.class).getNounType()) {
                    // removeEntity.remove(ecs.Components.Property.class);
                }
            }

        }

        if(entity.get(ecs.Components.Text.class).getTextType() == TextType.ADJECTIVE) {

            var action = entity.get(ecs.Components.Action.class).getAction();

            for(Entity e : entities.values()){
                if(e.contains(ecs.Components.Property.class) && !e.contains(ecs.Components.Text.class)) {
                    switch (action){
                        case Action.STOP :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.STOP);
                            break;
                        case Action.WIN :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.WIN);
                            break;
                        case Action.KILL :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.KILL);
                            break;
                        case Action.YOU :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.YOU);
                            break;
                        case Action.PUSH :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.PUSHABLE);
                            break;
                        case Action.SINK :
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.SINK);
                            break;
                    }
                }
            }


        }
    }



}
