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

        cleanComponents(grid); // Remove all components from non TEXT entities
        makeNewRuleSet(grid); // Apply new rules

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
            addComponents(left, right); // Apply the correct components
        }
    }

    /**
     * Method: Scan Vertical
     * Description:
     */
    public void scanVertical(Entity[][] grid, int row, int col,  int maxRow, int maxCol) {

        // Check if there is a valid rule
        boolean rule = checkNext(grid, row + 1, col, maxRow, maxCol) && checkNext(grid, row - 1, col, maxRow, maxCol);

        Entity up = grid[row-1][col]; // Grab the left text entity
        Entity down = grid[row+1][col]; // Grab the right text entity

        // If there's a rule horizontal, no need to grab the center VERB
        if(rule) {
            addComponents(up, down); // Apply the correct components
        }
    }


    /**
     * Method: Check next
     * @param grid - 2D Array representing the game area
     * @param maxRow - Total number of rows
     * @param maxCol - Total number of cols
     */
    public boolean checkNext(Entity[][] grid, int row, int col, int maxCol, int maxRow) {

        if(row >= 0 && row <= maxRow && col >= 0 && col <= maxCol ) { // If the matrix position falls withing bounds
            return grid[row][col] != null;   // True is Entity False if null
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

                var action = second.get(ecs.Components.Action.class).getAction();
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

    /**
     * Method: Remove Component
     * Description: Removes properties from entities
     * @param entity - Entity to provide action to remove from other entities
     */
    public void removeComponents(Entity entity) {

        if(entity == null) {
            return;
        }

        // If TEXT and ADJECTIVE
        if(entity.get(ecs.Components.Text.class).getTextType() == TextType.ADJECTIVE) {

            var action = entity.get(ecs.Components.Action.class).getAction(); // Grab the action

            for(Entity e : entities.values()){ // Loop through the entities
                // If the entity has properties and is not a TEXT entity
                if(e.contains(ecs.Components.Property.class) && !e.contains(ecs.Components.Text.class)) {
                    switch (action){ // Based on the action, remove specific properties
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

    /**
     * Method: Clean Components
     * Description: Removes all properties off non VERB TEXT entities to make new rule set
     * @param grid - 2D array of TEXT Entities
     */
    public void cleanComponents(Entity[][] grid){
        // Iterate through the grid game area
        for(int row = 0; row < level.getHeight(); row++) {

            for(int col = 0; col < level.getWidth(); col++) {

                Entity entity = grid[row][col]; // Grab the entity from the grid

                // If the entity is TEXT and not a VERB (IS)
                if(entity != null && entity.get(ecs.Components.Text.class).getTextType() != TextType.VERB)
                {
                    removeComponents(grid[row][col]);
                }

            }

        }
    }


    /**
     * Method: Make New Rule Set
     * Description: Scans the grid for complete rules
     * @param grid - 2D array of TEXT Entities
     */
    public void makeNewRuleSet(Entity[][] grid) {
        // Iterate through the grid game area
        for(int row = 0; row < level.getHeight(); row++) {

            for(int col = 0; col < level.getWidth(); col++) {

                Entity entity = grid[row][col]; // Grab the entity from the grid

                // If the entity is TEXT and a VERB (IS)
                if(entity != null)
                {
                    // Only TEXT entities that are VERBS
                    if(entity.get(ecs.Components.Text.class).getTextType() == TextType.VERB) { // VERB IS
                        scanVertical(grid, row, col, level.getHeight(), level.getWidth()); // Scan for vertical rules
                        scanHorizontal(grid, row, col, level.getHeight(), level.getWidth()); // Scan for horizontal rules
                    }

                }

            }

        }
    }



}
