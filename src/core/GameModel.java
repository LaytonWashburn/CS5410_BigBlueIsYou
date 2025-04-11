package core;

import ecs.Components.Position;
import ecs.Components.Text;
import ecs.Entities.*;
import ecs.Systems.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import edu.usu.graphics.Graphics2D;
import edu.usu.utils.Tuple2;
import level.Level;

import java.lang.System;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.joml.Vector2f;
import undo.StackFrame;
import utils.KeyBinds;
import static ecs.Systems.System.*;

import utils.EntityConstants;
import utils.NounType;

public class GameModel {

    private RenderAnimatedSprite sysRenderAnimatedSprite;

    private final Level level;;
    private KeyBinds keybinds;

    private Stack<StackFrame> undoStack;
    private StackFrame initialStackFrame;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    private Entity[][] gameArea; // Holds the game area

    // Systems
    private KeyboardInput sysKeyboardInput;
    private RenderStaticSprite sysRenderStaticSprite;
    private Movement sysMovement;
    private Rules sysRules;
    private GridAlignment sysGridAlignment;


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
        this.sysMovement = new Movement(graphics);
        this.sysGridAlignment = new GridAlignment(this.gameArea);
        this.sysRules = new Rules(keybinds, level);

        this.undoStack = new Stack<>();
        this.initialStackFrame = new StackFrame();
        initializeObjectTypes(level); // Take level and create entities for all objects

        undoStack.push(initialStackFrame);
        System.out.println("UNDO STACK: " +undoStack);
//        var test = CreateSprites.createBigBlue(this.texBigBlue, 0, 0);
//        var test2 = test.clone();
//        var test2Position = test2.get(ecs.Components.Position.class);
//        var test2NounType = test2.get(ecs.Components.Noun.class);
//        test2Position.i = 55;
//        test2NounType.setNounType(NounType.TEXT);
//        System.out.println();

        this.sysRules.scanGamePlayArea(this.gameArea); // Do an initial scan of the game area

    }

    /**
     * Method: Update
     * @param elapsedTime - Elapsed time since the last update
     */
    public void update(double elapsedTime) throws CloneNotSupportedException {
        // Because ECS framework, input processing is now part of the update
        boolean moved = false;
        var changed = new ArrayList<Tuple2<Entity, Boolean>>(); // Update the systems and put in changed map
        for(ecs.Systems.System system : systems) {
            ArrayList<Tuple2<Entity, Boolean>> changedEntities = system.update(elapsedTime);
            changed.addAll(changedEntities); // TODO: does this need to check to see if the entity is already added?
            if (system instanceof Movement && !changedEntities.isEmpty()) {
//                System.out.println(changed.size());
                moved = true;
            }
        }

        if(moved){ // Scan game play area if movement has occurred
            for(ecs.Systems.System system1 : systems) { // Look for the Rules system
                if(system1 instanceof Rules) {
                    ((Rules) system1).scanGamePlayArea(this.gameArea);
                }
            }
            //Add changed entities to a stack frame
            StackFrame stackFrame = new StackFrame();
            for (Tuple2<Entity, Boolean> entityTuple : changed) {
                stackFrame.addEntityTuple(entityTuple);
            }
            undoStack.push(stackFrame);
        }

        for (Tuple2<Entity, Boolean> entityTuple : changed) { // Allow systems to decide if they are interested or not in the changed
            for (var system : systems) {
                system.updatedEntity(entityTuple.item1());
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
    private void initializeObjectTypes(Level level) throws CloneNotSupportedException {

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                createLayout(level.getGroup1()[i][j], i, j); // Create the object
            }
        }
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                createLayout(level.getGroup2()[i][j], i, j);
            }
        }
    }

    /**
     * Method Create Layout
     * Description: Method to take a character and create corresponding object
     * @param symbol - Input symbol
     * @param col - Column placement in game board
     * @param row - Row placement in game board
     */
    private void createLayout(Character symbol, int row, int col) throws CloneNotSupportedException {
        Entity e;

        e = switch (symbol) {
            case 'w' -> CreateSprites.createWall(texWall, row, col, keybinds);
            case 'r' -> CreateSprites.createRock(texRock, row, col, keybinds);
            case 'f' -> CreateSprites.createFlag(texFlag, row, col, keybinds);
            case 'b' -> CreateSprites.createBigBlue(texBigBlue, row, col, keybinds);
            case 'h' -> CreateSprites.createHedge(texHedge, row, col);
            case 'a' -> CreateSprites.createWater(texWater, row, col, keybinds);
            case 'v' -> CreateSprites.createLava(texLava, row, col, keybinds);
            case 'g' -> CreateSprites.createGrass(texGrass, row, col, keybinds);
            case 'l' -> CreateSprites.createFloor(texFloor, row, col, keybinds);
            case 'W' -> CreateSprites.createWordWall(texWordWall, row, col);
            case 'R' -> CreateSprites.createWordRock(texWordRock, row, col);
            case 'F' -> CreateSprites.createWordFlag(texWordFlag, row, col);
            case 'B' -> CreateSprites.createWordBaba(texWordBaba, row, col);
            case 'I' -> CreateSprites.createWordIs(texWordIs, row, col);
            case 'S' -> CreateSprites.createWordStop(texWordStop, row, col);
            case 'P' -> CreateSprites.createWordPush(texWordPush, row, col);
            case 'V' -> CreateSprites.createWordLava(texWordLava, row, col);
            case 'A' -> CreateSprites.createWordWater(texWordWater, row, col);
            case 'Y' -> CreateSprites.createWordYou(texWordYou, row, col);
            case 'X' -> CreateSprites.createWordWin(texWordWin, row, col);
            case 'N' -> CreateSprites.createWordSink(texWordSink, row, col);
            case 'K' -> CreateSprites.createWordKill(texWordKill, row, col);
            default -> null;
        };

        if (e != null) {
            add(e, row, col);
            initialStackFrame.addEntityTuple(new Tuple2<>(e, false));
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
        if(entity.contains(ecs.Components.Text.class)) {
            this.gameArea[row][col] = entity;
        }

    }

    public void triggerUndo() throws CloneNotSupportedException {
        if (undoStack.size() > 1) {
            StackFrame poppedFrame = undoStack.pop();
            ArrayList<Tuple2<Entity, Boolean>> poppedEntities = poppedFrame.getEntities();
            for (Tuple2<Entity, Boolean> entityTuple : poppedEntities) {
                Entity entityToReplace = entityTuple.item1().clone();
                for (var system : systems) {
                    system.replaceEntity(entityToReplace);
                }
            }
        }
    }


}
