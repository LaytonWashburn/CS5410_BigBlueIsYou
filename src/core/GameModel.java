package core;

import ecs.Entities.*;
import ecs.Systems.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import edu.usu.utils.Tuple2;
import level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import undo.StackFrame;
import utils.KeyBinds;
import static ecs.Systems.System.*;

import utils.ParticleSystem;

public class GameModel {

    private final Level level;;
    private final KeyBinds keybinds;

    private Stack<StackFrame> undoStack;
    private StackFrame initialStackFrame;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    private final Entity[][] gameArea; // Holds the game area

    // Systems
    private RenderAnimatedSprite sysRenderAnimatedSprite;
    private KeyboardInput sysKeyboardInput;
    private Movement sysMovement;
    private Rules sysRules;
    private GridAlignment sysGridAlignment;
    private Win sysWin;
    private Kill sysKill;
    private Sink sysSink;
    private ParticleSystem sysParticle;

    // Sound
    public BackgroundMusic backgroundMusic;
    public GameSounds gameSounds;

    private Graphics2D graphics;

    private Texture texParticle = new Texture("resources/images/particle.png");

    // Texture Objects
    private final Texture texFlag = new Texture("resources/sprites/sprites/objects/flag/flag.png");
    private final Texture texRock = new Texture("resources/sprites/sprites/objects/rock/rock.png");
    private final Texture texWall = new Texture("resources/sprites/sprites/objects/wall/wall.png");
    private final Texture texFloor = new Texture("resources/sprites/sprites/objects/floor/floor.png");
    private final Texture texGrass = new Texture("resources/sprites/sprites/objects/grass/grass.png");
    private final Texture texWater = new Texture("resources/sprites/sprites/objects/water/water.png");
    private final Texture texLava = new Texture("resources/sprites/sprites/objects/lava/lava.png");
    private final Texture texHedge = new Texture("resources/sprites/sprites/objects/hedge/hedge.png");
    private final Texture texBigBlue = new Texture("resources/sprites/sprites/objects/bigblue/BigBlue.png");

    // Textures Words
    private final Texture texWordWall = new Texture("resources/sprites/sprites/words/wall/word-wall.png");
    private final Texture texWordRock = new Texture("resources/sprites/sprites/words/rock/word-rock.png");
    private final Texture texWordFlag = new Texture("resources/sprites/sprites/words/flag/word-flag.png");
    private final Texture texWordBaba = new Texture("resources/sprites/sprites/words/baba/word-baba.png");
    private final Texture texWordIs = new Texture("resources/sprites/sprites/words/is/word-is.png");
    private final Texture texWordStop = new Texture("resources/sprites/sprites/words/stop/word-stop.png");
    private final Texture texWordPush = new Texture("resources/sprites/sprites/words/push/word-push.png");
    private final Texture texWordLava = new Texture("resources/sprites/sprites/words/lava/word-lava.png");
    private final Texture texWordWater = new Texture("resources/sprites/sprites/words/water/word-water.png");
    private final Texture texWordYou = new Texture("resources/sprites/sprites/words/you/word-you.png");
    private final Texture texWordWin = new Texture("resources/sprites/sprites/words/win/word-win.png");
    private final Texture texWordSink = new Texture("resources/sprites/sprites/words/sink/word-sink.png");
    private final Texture texWordKill = new Texture("resources/sprites/sprites/words/kill/word-kill.png");


    /**
     * Method: Game Model
     * @param level - Current level to wrap the game model around
     * @param keybinds - Current key bind configuration for the game
     */
    public GameModel(Level level, KeyBinds keybinds, BackgroundMusic backgroundMusic) {
        this.level = level;
        this.keybinds = keybinds;
        this.gameArea = new Entity[level.getHeight()][level.getWidth()];
        this.backgroundMusic = backgroundMusic;
        this.gameSounds = new GameSounds(this.backgroundMusic.soundManager);
        systems.clear(); // clear the system list if you're starting a fresh game

    }


    /**
     * Method: Initialize
     * @param graphics - Graphics 2D object
     */
    public void initialize(Graphics2D graphics) throws CloneNotSupportedException{
        this.backgroundMusic.play();
        this.graphics = graphics;

        this.sysParticle = new ParticleSystem(texParticle, graphics, gameSounds);

        this.sysKeyboardInput = new KeyboardInput(graphics.getWindow(), keybinds);
        this.sysRenderAnimatedSprite = new RenderAnimatedSprite(graphics, level);
        this.sysMovement = new Movement(graphics, gameSounds);
        this.sysGridAlignment = new GridAlignment(this.gameArea);
        this.sysRules = new Rules(level, sysParticle);
        this.sysWin = new Win(backgroundMusic, level, sysParticle, gameSounds);
        this.sysKill = new Kill(level, sysParticle, gameSounds);
        this.sysSink = new Sink(level, sysParticle, gameSounds);


        this.undoStack = new Stack<>();
        this.initialStackFrame = new StackFrame();
        initializeObjectTypes(level); // Take level and create entities for all objects

        undoStack.push(initialStackFrame);

        this.sysGridAlignment.updateGrid();
        this.sysRules.scanGamePlayArea(this.gameArea); // Do an initial scan of the game area

    }

    /**
     * Method: Update
     * @param elapsedTime - Elapsed time since the last update
     */
    public void update(double elapsedTime) throws CloneNotSupportedException {
        // Because ECS framework, input processing is now part of the update
        sysParticle.update(elapsedTime);

        boolean moved = false;
        var changed = new ArrayList<Tuple2<Entity, Boolean>>(); // Update the systems and put in changed map
        for(ecs.Systems.System system : systems) {
            ArrayList<Tuple2<Entity, Boolean>> changedEntities = system.update(elapsedTime);
            ArrayList<Tuple2<Entity, Boolean>> changedEntitiesToAdd = new ArrayList<>();
            for (Tuple2<Entity, Boolean> changedEntity : changedEntities) {
                boolean changedAlreadyContainsEntity = false;
                for (Tuple2<Entity, Boolean> changed2 : changed) {
                    if (changedEntity.item1().getId() == changed2.item1().getId()) {
                        changedAlreadyContainsEntity = true;
                        if (!changed2.item2() && changedEntity.item2()) {
                            changedEntitiesToAdd.add(new Tuple2<>(changed2.item1().clone(), true));
                        }
                        break;
                    }
                }
                if (!changedAlreadyContainsEntity) {
                    changed.add(changedEntity);
                }
                for (Tuple2<Entity, Boolean> changedToAdd : changedEntitiesToAdd) {
                    changed.remove(new Tuple2<>(changedToAdd.item1(), false));
                    changed.add(changedToAdd);
                }
            }
            if (system instanceof Movement && !changedEntities.isEmpty()) {
                moved = true;
            }
        }

        if(moved){ // Scan game play area if movement has occurred
            this.sysGridAlignment.updateGrid();
            ArrayList<Tuple2<Entity, Boolean>> rulesChangedEntities = this.sysRules.scanGamePlayArea(this.gameArea);
            changed.addAll(rulesChangedEntities);
            //Add changed entities to a stack frame
            StackFrame stackFrame = new StackFrame();
            for (Tuple2<Entity, Boolean> entityTuple : changed) {
                stackFrame.addEntityTuple(entityTuple);
            }
            undoStack.push(stackFrame);
            this.sysRules.scanGamePlayArea(this.gameArea);
        }

        for (Tuple2<Entity, Boolean> entityTuple : changed) { // Allow systems to decide if they are interested or not in the changed
            for (var system : systems) {
                system.updatedEntity(entityTuple.item1());
            }
        }

        // Remove entities
        for (Tuple2<Entity, Boolean> entity : changed) {
            if (entity.item2()) {
                removeEntity(entity.item1());
            }
        }

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

    public void reset() throws CloneNotSupportedException {

            StackFrame poppedFrame = this.initialStackFrame;
            ArrayList<Tuple2<Entity, Boolean>> poppedEntities = poppedFrame.getEntities();
            for (Tuple2<Entity, Boolean> entityTuple : poppedEntities) {
                Entity entityToReplace = entityTuple.item1();
                for (var system : systems) {
                    system.replaceEntity(entityToReplace);
                }
            }
            this.sysGridAlignment.updateGrid();
            this.sysRules.scanGamePlayArea(this.gameArea);
            for (Tuple2<Entity, Boolean> entityTuple : poppedEntities) { // Allow systems to decide if they are interested or not in the changed
                for (var system : systems) {
                    system.updatedEntity(entityTuple.item1());
                }
            }
            this.undoStack.clear();
            this.undoStack.push(new StackFrame());

            this.undoStack.push(poppedFrame);

    }

    public void triggerUndo() throws CloneNotSupportedException {
        if (undoStack.size() > 1) {
            StackFrame poppedFrame = undoStack.pop();
            ArrayList<Tuple2<Entity, Boolean>> poppedEntities = poppedFrame.getEntities();
            for (Tuple2<Entity, Boolean> entityTuple : poppedEntities) {
                Entity entityToReplace = entityTuple.item1();
                for (var system : systems) {
                    system.replaceEntity(entityToReplace);
                }
            }
            this.sysGridAlignment.updateGrid();
            this.sysRules.scanGamePlayArea(this.gameArea);
            for (Tuple2<Entity, Boolean> entityTuple : poppedEntities) { // Allow systems to decide if they are interested or not in the changed
                for (var system : systems) {
                    system.updatedEntity(entityTuple.item1());
                }
            }
        }
    }


}
