package core;

import ecs.Entities.*;
import ecs.Systems.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import edu.usu.graphics.Graphics2D;
import level.Level;

import java.lang.System;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector2f;
import utils.KeyBinds;
import static ecs.Systems.System.*;

import utils.EntityConstants;

public class GameModel {

    private RenderAnimatedSprite sysRenderAnimatedSprite;

    private final Level level;;
    private KeyBinds keybinds;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    private Entity[][] gameArea; // Holds the game area

    // Systems
    private KeyboardInput sysKeyboardInput;
    private RenderStaticSprite sysRenderStaticSprite;
    private Movement sysMovement;
    private Rules sysRules;


    private Graphics2D graphics;

    // Texture Objects
    private Texture texFlag = new Texture("resources/sprites/sprites/objects/flag/flag.png");
    private Texture texRock = new Texture("resources/sprites/sprites/objects/rock/rock.png");
    private Texture texWall = new Texture("resources/sprites/sprites/objects/wall/wall.png");
    private Texture texFloor = new Texture("resources/sprites/sprites/objects/floor/floor.png");
    private Texture texGrass = new Texture("resources/sprites/sprites/objects/grass/grass.png");
    private Texture texWater = new Texture("resources/sprites/sprites/objects/water/water.png");
    private Texture texLava = new Texture("resources/sprites/sprites/objects/lava/lava.png");
    private Texture texHedge = new Texture("resources/sprites/sprites/objects/hedge/hedge.png");
    private Texture texBigBlue = new Texture("resources/sprites/sprites/objects/bigblue/BigBlue.png");

    // Textures Words
    private Texture texWordWall = new Texture("resources/sprites/sprites/words/wall/word-wall.png");
    private Texture texWordRock = new Texture("resources/sprites/sprites/words/rock/word-rock.png");
    private Texture texWordFlag = new Texture("resources/sprites/sprites/words/flag/word-flag.png");
    private Texture texWordBaba = new Texture("resources/sprites/sprites/words/baba/word-baba.png");
    private Texture texWordIs = new Texture("resources/sprites/sprites/words/is/word-is.png");
    private Texture texWordStop = new Texture("resources/sprites/sprites/words/stop/word-stop.png");
    private Texture texWordPush = new Texture("resources/sprites/sprites/words/push/word-push.png");
    private Texture texWordLava = new Texture("resources/sprites/sprites/words/lava/word-lava.png");
    private Texture texWordWater = new Texture("resources/sprites/sprites/words/water/word-water.png");
    private Texture texWordYou = new Texture("resources/sprites/sprites/words/you/word-you.png");
    private Texture texWordWin = new Texture("resources/sprites/sprites/words/win/word-win.png");
    private Texture texWordSink = new Texture("resources/sprites/sprites/words/sink/word-sink.png");
    private Texture texWordKill = new Texture("resources/sprites/sprites/words/kill/word-kill.png");


    /**
     * Method: Game Model
     * @param level - Current level to wrap the game model around
     * @param keybinds - Current key bind configuration for the game
     */
    public GameModel(Level level, KeyBinds keybinds) {
        this.level = level;
        System.out.println("Level: " + level.getName());
        System.out.println("Level Width: " + level.getWidth());
        System.out.println("Level Height: " + level.getHeight());
        this.keybinds = keybinds;
        this.gameArea = new Entity[level.getHeight()][level.getWidth()];
        systems.clear(); // clear the system list if you're starting a fresh game
    }

    /**
     * Method: Initialize
     * @param graphics - Graphics 2D object
     */
    public void initialize(Graphics2D graphics) throws CloneNotSupportedException{
        this.graphics = graphics;

        this.sysKeyboardInput = new KeyboardInput(graphics.getWindow(), keybinds);
        this.sysRenderStaticSprite = new RenderStaticSprite(graphics, level);
        this.sysRenderAnimatedSprite = new RenderAnimatedSprite(graphics, level);
        this.sysRules = new Rules(keybinds, level);
        this.sysMovement = new Movement(graphics);

        System.out.println("Size of the systems is: " + systems.size());

        initializeObjectTypes(level);

        Entity a = CreateSprites.createLava(texLava, 0, 0);
        Entity b = a.clone();
        System.out.println(b);
    }

    /**
     * Method: Update
     * @param elapsedTime - Elapsed time since the last update
     */
    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update

        var changed = new HashMap<Long, Entity>(); // Update the systems and put in changed map
        for(ecs.Systems.System system : systems) {
            ArrayList<Entity> changedEntities = system.update(elapsedTime);
            for(Entity entity : changedEntities){ // Loop through the changed
                changed.put(entity.getId(), entity);
            }
            if (system instanceof Movement && !changed.isEmpty()) {
                java.lang.System.out.println("Update rules here");
            }
        }

        for (var entity : changed.values()) { // Allow systems to decide if they are interested or not in the changed
            for (var system : systems) {
                system.updatedEntity(entity);
            }
        }

        // Remove entities
        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

        // Add Entities
        for (var entity : addThese) {
            addEntity(entity);
        }
        addThese.clear();

        // Because ECS framework, rendering is now part of the update
    }

    /**
     * Method: Add Entity
     * Description: Passes entity to systems and each system determines if they are interested
     * @param entity - Entity to add to systems
     */
    private void addEntity(Entity entity) {
        for (ecs.Systems.System system : systems) {
            system.add(entity);
        }
    }

    /**
     * Method: Remove Entity
     * Description: Passes entity to system and systems
     * @param entity - Entity to remove
     */
    private void removeEntity(Entity entity) {
        for (ecs.Systems.System system : systems) {
            system.remove(entity.getId());
        }
    }


    /**
     * Method: Initialize Object Types
     * Description: Method to iterate through the level input and call create layout method
     * @param level - Input Level
     */
    private void initializeObjectTypes(Level level){

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
//                System.out.print(level.getGroup1()[i][j]);
                createLayout(level.getGroup1()[i][j], i, j); // Create the object
            }
//            System.out.println();
        }
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
//                System.out.print(level.getGroup2()[i][j]);
                createLayout(level.getGroup2()[i][j], i, j);
            }
//            System.out.println();
        }
    }

    /**
     * Method Create Layout
     * Description: Method to take a character and create corresponding object
     * @param symbol - Input symbol
     * @param col - Column placement in game board
     * @param row - Row placement in game board
     */
    private void createLayout(Character symbol, int row, int col){
        switch (symbol) {

            case 'w': // wall
                add(CreateSprites.createWall(texWall, row, col), row, col);
                break;
            case 'r': // rock
                add(CreateSprites.createRock(texRock, row, col), row, col);
                break;
            case 'f': // flag
                add(CreateSprites.createFlag(texFlag, row, col), row, col);
                break;
            case 'b': // big blue
                add(CreateSprites.createBigBlue(texBigBlue, row, col), row, col);
                break;
            case 'h': // hedge
                add(CreateSprites.createHedge(texHedge, row, col), row, col);
                break;
            case 'a': // Water / goop
                add(CreateSprites.createWater(texWater, row, col), row, col);
                break;
            case 'v': // Lava
                add(CreateSprites.createLava(texLava, row, col), row, col);
                break;
            case 'g': // Grass
                add(CreateSprites.createGrass(texGrass, row, col), row, col);
                break;
            case 'l':  // floor
                add(CreateSprites.createFloor(texFloor, row, col), row, col);
                break;
            case 'W': // Word Wall
                add(CreateSprites.createWordWall(texWordWall, row, col), row, col);
                break;
            case 'R': // Word Rock
                add(CreateSprites.createWordRock(texWordRock, row, col), row, col);
                break;
            case 'F': // Word Flag
                add(CreateSprites.createWordFlag(texWordFlag, row, col), row, col);
                break;
            case 'B': // Word Big Blue
                add(CreateSprites.createWordBaba(texWordBaba, row, col), row, col);
                break;
            case 'I': // Word Is
                add(CreateSprites.createWordIs(texWordIs, row, col), row, col);
                break;
            case 'S': // Word Stop
                add(CreateSprites.createWordStop(texWordStop, row, col), row, col);
                break;
            case 'P': // Word Push
                add(CreateSprites.createWordPush(texWordPush, row, col), row, col);
                break;
            case  'V': // Word Lava
                add(CreateSprites.createWordLava(texWordLava, row, col), row, col);
                break;
            case 'A': // Word Water
                add(CreateSprites.createWordWater(texWordWater, row, col), row, col);
                break;
            case 'Y': // Word, You
                add(CreateSprites.createWordYou(texWordYou, row, col), row, col);
                break;
            case 'X': // Word Win
                add(CreateSprites.createWordWin(texWordWin, row, col), row, col);
                break;
            case 'N': // Word Sink
                add(CreateSprites.createWordSink(texWordSink, row, col), row, col);
                break;
            case 'K': // Word Kill
                add(CreateSprites.createWordKill(texWordKill, row, col), row, col);
                break;
            default:
        }
    }

    /**
     * Method : add
     * @param entity - Entity to add
     * @param row - Row position to add the entity
     * @param col - Col position to add the entity
     */
    public void add(Entity entity, int row, int col) {
        addEntity(entity);
        this.gameArea[row][col] = entity;
    }



}
