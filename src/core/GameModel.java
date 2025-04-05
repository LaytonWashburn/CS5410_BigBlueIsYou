package core;

import ecs.Entities.*;
import ecs.Systems.KeyboardInput;
import ecs.Systems.Movement;
import ecs.Systems.RenderAnimatedSprite;
import ecs.Systems.RenderSprite;
import edu.usu.graphics.*;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import level.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import utils.KeyBinds;

import javax.swing.*;

import utils.EntityConstants;

public class GameModel {

    private RenderAnimatedSprite sysRenderAnimatedSprite;

    private final Level level;;
    private Vector2f[][] spriteRectCenters;
    private KeyBinds keybinds;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    // Systems
    private KeyboardInput sysKeyboardInput;
    private RenderSprite sysRenderSprite;
    private Movement sysMovement;


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
        this.spriteRectCenters = getSpriteRectCenters();
        this.keybinds = keybinds;
    }

    /**
     * Method: Initialize
     * @param graphics - Graphics 2D object
     */
    public void initialize(Graphics2D graphics) throws CloneNotSupportedException{
        this.graphics = graphics;

        this.sysKeyboardInput = new KeyboardInput(graphics.getWindow(), keybinds);
        this.sysRenderSprite = new RenderSprite(graphics);
        this.sysMovement = new Movement(graphics);
        this.sysRenderAnimatedSprite = new RenderAnimatedSprite(graphics);

        initializeObjectTypes(level);

        Entity a = CreateSprites.createLava(texLava, 0.0f, 0.0f);
        Entity b = a.clone();
        System.out.println(b);
    }

    /**
     * Method: Update
     * @param elapsedTime - Elapsed time since the last update
     */
    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);
        sysRenderSprite.update(elapsedTime);
        sysRenderAnimatedSprite.update(elapsedTime);
        sysMovement.update(elapsedTime);

        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

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
        sysKeyboardInput.add(entity);
        sysRenderSprite.add(entity);
        sysMovement.add(entity);

    }


    /**
     * Method: Add Entity Temp
     * @param entity - Entity to add to the animated sprite system
     */
    private void addEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.add(entity);
    }

    /**
     * Method: Remove Entity Temp
     * @param entity - Entity to remove from animated sprite system
     */
    private void removeEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.remove(entity.getId());
    }

    /**
     * Method: Remove Entity
     * Description: Passes entity to system and systems
     * @param entity - Entity to remove
     */
    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
        sysRenderSprite.remove(entity.getId());
        sysMovement.remove(entity.getId());
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
     * Method: Get Sprite Rect Centers
     * @return - Returns the top left corners
     */
    private Vector2f[][] getSpriteRectCenters() {
        Vector2f[][] topLeftCorners = new Vector2f[level.getHeight()][level.getWidth()];
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                topLeftCorners[i][j] = new Vector2f((-EntityConstants.rectSize * ((float) level.getWidth() / 2) + i*EntityConstants.rectSize + EntityConstants.rectSize/2),
                        (-EntityConstants.rectSize * ((float) level.getHeight() / 2)) + j*EntityConstants.rectSize + EntityConstants.rectSize/2);
            }
        }
        return topLeftCorners;
    }

    /**
     * Method Create Layout
     * Description: Method to take a character and create corresponding object
     * @param symbol - Input symbol
     * @param col - Column placement in game board
     * @param row - Row placement in game board
     */
    private void createLayout(Character symbol, int col, int row){
        switch (symbol) {

            case 'w': // wall
                sysRenderAnimatedSprite.add(CreateSprites.createWall(texWall, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'r': // rock
                sysRenderAnimatedSprite.add(CreateSprites.createRock(texRock, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y, true));
                break;
            case 'f': // flag
                sysRenderAnimatedSprite.add(CreateSprites.createFlag(texFlag, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'b': // big blue
                addEntity(CreateSprites.createBigBlue(texBigBlue, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y, keybinds));
                break;
            case 'h': // hedge
                sysRenderAnimatedSprite.add(CreateSprites.createHedge(texHedge, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'a': // Water / goop
                sysRenderAnimatedSprite.add(CreateSprites.createWater(texWater, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'v': // Lava
                sysRenderAnimatedSprite.add(CreateSprites.createLava(texLava, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'g': // Grass
                sysRenderAnimatedSprite.add(CreateSprites.createGrass(texGrass, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'l':  // floor
                sysRenderAnimatedSprite.add(CreateSprites.createFloor(texFloor, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'W': // Word Wall
                sysRenderAnimatedSprite.add(CreateSprites.createWordWall(texWordWall, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'R': // Word Rock
                sysRenderAnimatedSprite.add(CreateSprites.createWordRock(texWordRock, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'F': // Word Flag
                sysRenderAnimatedSprite.add(CreateSprites.createFloor(texWordFlag, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'B': // Word Big Blue
                sysRenderAnimatedSprite.add(CreateSprites.createWordBaba(texWordBaba, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'I': // Word Is
                sysRenderAnimatedSprite.add(CreateSprites.createWordIs(texWordIs, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'S': // Word Stop
                sysRenderAnimatedSprite.add(CreateSprites.createWordStop(texWordStop, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'P': // Word Push
                sysRenderAnimatedSprite.add(CreateSprites.createWordPush(texWordPush, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case  'V': // Word Lava
                sysRenderAnimatedSprite.add(CreateSprites.createWordLava(texWordLava, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'A': // Word Water
                sysRenderAnimatedSprite.add(CreateSprites.createWordWater(texWordWater, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'Y': // Word, You
                sysRenderAnimatedSprite.add(CreateSprites.createWordYou(texWordYou, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'X': // Word Win
                sysRenderAnimatedSprite.add(CreateSprites.createWordWin(texWordWin, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'N': // Word Sink
                sysRenderAnimatedSprite.add(CreateSprites.createWordSink(texWordSink, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'K': // Word Kill
                sysRenderAnimatedSprite.add(CreateSprites.createWordKill(texWordKill, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            default:
        }
    }



}
