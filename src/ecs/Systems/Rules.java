package ecs.Systems;

import ecs.Components.*;
import ecs.Entities.Entity;
import edu.usu.graphics.Texture;
import edu.usu.utils.Tuple2;
import level.Level;
import org.joml.Vector2f;
import utils.*;
import utils.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class: Rules
 * Description:
 *  -
 */
public class Rules extends System{

    private final KeyBinds keybinds;
    private final Level level;
    private ParticleSystem sysParticle;

    private final Map<Long, Entity> entitiesCopy = new HashMap<>();

    private ArrayList<Tuple2<Entity, Boolean>> changed;

    private Texture texFlag = new Texture("resources/sprites/sprites/objects/flag/flag.png");
    private Texture texRock = new Texture("resources/sprites/sprites/objects/rock/rock.png");
    private Texture texWall = new Texture("resources/sprites/sprites/objects/wall/wall.png");
    private Texture texFloor = new Texture("resources/sprites/sprites/objects/floor/floor.png");
    private Texture texGrass = new Texture("resources/sprites/sprites/objects/grass/grass.png");
    private Texture texWater = new Texture("resources/sprites/sprites/objects/water/water.png");
    private Texture texLava = new Texture("resources/sprites/sprites/objects/lava/lava.png");
    private Texture texHedge = new Texture("resources/sprites/sprites/objects/hedge/hedge.png");
    private Texture texBigBlue = new Texture("resources/sprites/sprites/objects/bigblue/BigBlue.png");


    public Rules(KeyBinds keyBinds, Level level, ParticleSystem sysParticle) {
        super( // ecs.Components.Property.class,
                ecs.Components.Position.class); // Must have a location on the map
        this.level = level;
        this.keybinds = keyBinds;
        this.sysParticle = sysParticle;
        this.changed = new ArrayList<>();
    }

    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) {
        return new ArrayList<Tuple2<Entity, Boolean>>(){{}};
    }

    /**
     * Method: Rules.java
     * Description: Scans the game play area when movement is triggered
     */
    public ArrayList<Tuple2<Entity, Boolean>> scanGamePlayArea(Entity[][] grid) throws CloneNotSupportedException {
        this.changed.clear();

        entitiesCopy.clear();
        for (Entity entity : entities.values()) {
            entitiesCopy.put(entity.getId(), entity.clone());
        }

        cleanComponents(grid); // Remove all components from non TEXT entities
        makeNewRuleSet(grid); // Apply new rules

        return changed;
    }

    /**
     * Method: Scan Horizontal
     * Description:
     */
    public void scanHorizontal(Entity[][] grid, int row, int col,  int maxRow, int maxCol) throws CloneNotSupportedException {

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
    public void scanVertical(Entity[][] grid, int row, int col,  int maxRow, int maxCol) throws CloneNotSupportedException {

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
    public void addComponents(Entity textEntity, Entity second) throws CloneNotSupportedException {

        var textNoun = textEntity.get(ecs.Components.Represent.class); // Get the Noun type for what the text represents

        for (Entity entity : entities.values()) { // Loop through the entities
            if (entity.contains(Noun.class) &&
                    entity.get(Noun.class).getNounType() == textNoun.getNounType()) {

                if(second.contains(ecs.Components.Text.class) &&
                        second.get(ecs.Components.Text.class).getTextType() == TextType.NOUN) {

                    Represent noun = second.get(ecs.Components.Represent.class);
                    Position position = entity.get(ecs.Components.Position.class);

                    AnimatedSprite as;
                    Noun nounChanged;
                    switch (noun.getNounType()) {
                        case NounType.ROCK:
                            as = changeAnimatedSprite(position.i, position.j, texRock);
                            nounChanged = new Noun(NounType.ROCK);
                            break;
                        case NounType.FLAG:
                            as = changeAnimatedSprite(position.i, position.j, texFlag);
                            nounChanged = new Noun(NounType.FLAG);
                            break;
                        case NounType.WALL:
                            as = changeAnimatedSprite(position.i, position.j, texWall);
                            nounChanged = new Noun(NounType.WALL);
                            break;
                        case NounType.FLOOR:
                            as = changeAnimatedSprite(position.i, position.j, texFloor);
                            nounChanged = new Noun(NounType.FLOOR);
                            break;
                        case NounType.GRASS:
                            as = changeAnimatedSprite(position.i, position.j, texGrass);
                            nounChanged = new Noun(NounType.GRASS);
                            break;
                        case NounType.LAVA:
                            as = changeAnimatedSprite(position.i, position.j, texLava);
                            nounChanged = new Noun(NounType.LAVA);
                            break;
                        case NounType.BIGBLUE:
                            as = changeAnimatedSprite(position.i, position.j, texBigBlue); // Need to switch this one
                            nounChanged = new Noun(NounType.BIGBLUE);
                            break;
                        case NounType.WATER:
                            as = changeAnimatedSprite(position.i, position.j, texWater);
                            nounChanged = new Noun(NounType.WATER);
                            break;
                        case NounType.HEDGE:
                            as = changeAnimatedSprite(position.i, position.j, texHedge);
                            nounChanged = new Noun(NounType.HEDGE);
                            break;
                        default:
                            as = null;
                            nounChanged = null;
                    };

                    changed.add(new Tuple2<>(entity.clone(), false));

                    entity.remove(ecs.Components.AnimatedSprite.class);
                    entity.remove(ecs.Components.Noun.class);
                    entity.add(as);
                    entity.add(nounChanged);

                }

                if(second.contains(ecs.Components.Action.class)) {
                    Entity entityCopy = entitiesCopy.get(entity.getId());

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
                            if (!entityCopy.get(ecs.Components.Property.class).getProperties().contains(Properties.YOU)) {
                                sysParticle.playerIsYou(entity.get(ecs.Components.Position.class), level);
                            }
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
    }

    public AnimatedSprite changeAnimatedSprite(int i, int j, Texture texture) {
        return new ecs.Components.AnimatedSprite(new edu.usu.graphics.AnimatedSprite(texture,

                new float[] {EntityConstants.frameTime, EntityConstants.frameTime, EntityConstants.frameTime},
                new Vector2f(EntityConstants.rectSize, EntityConstants.rectSize),
                new Vector2f(i, j)));
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
                // If the entity has properties and is not a TEXT entity AND if the entity is not a Hedge (hedges are unchangeable)
                if(e.contains(ecs.Components.Property.class) && !e.contains(ecs.Components.Text.class) &&
                        (e.contains(ecs.Components.Noun.class) && e.get(ecs.Components.Noun.class).getNounType()!=NounType.HEDGE)) {
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
                            e.get(ecs.Components.Property.class).getProperties().remove(Properties.MOVE);
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
    public void makeNewRuleSet(Entity[][] grid) throws CloneNotSupportedException {
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
