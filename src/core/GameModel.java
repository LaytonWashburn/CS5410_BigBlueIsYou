package core;

import ecs.Entities.*;
import ecs.Systems.KeyboardInput;
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


    // Constructor
    public GameModel(Level level, KeyBinds keybinds) {
        this.level = level;
        this.spriteRectCenters = getSpriteRectCenters();
        this.keybinds = keybinds;
    }

    public void initialize(Graphics2D graphics) {
        sysKeyboardInput = new KeyboardInput(graphics.getWindow(), keybinds);
        sysRenderSprite = new RenderSprite(graphics);
        this.graphics = graphics;

        this.sysRenderAnimatedSprite = new RenderAnimatedSprite(graphics);

        keybinds.printKeyBinds();

        initializeObjectTypes(level);

//        addEntityTemp(Flag.create(texFlag, 0f, 0f));
//        addEntityTemp(Rock.create(texRock, .25f, .25f, true));
    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);
        sysRenderSprite.update(elapsedTime);
        sysRenderAnimatedSprite.update(elapsedTime);

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

    private void addEntity(Entity entity) {
        sysKeyboardInput.add(entity);
        sysRenderSprite.add(entity);

    }

    private void addEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.add(entity);
    }

    private void removeEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.remove(entity.getId());
    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
        sysRenderSprite.remove(entity.getId());
    }

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

    private void createLayout(Character symbol, int col, int row){
        switch (symbol) {
            case 'w': // wall
                sysRenderAnimatedSprite.add(Wall.create(texWall, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'r': // rock
                sysRenderAnimatedSprite.add(Rock.create(texRock, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y, true));
                break;
            case 'f': // flag
                sysRenderAnimatedSprite.add(Floor.create(texFlag, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'b': // big blue
                addEntity(BigBlue.create(texBigBlue, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y, keybinds));
                break;
            case 'l':  // floor
                sysRenderAnimatedSprite.add(CreateSprites.createFloor(texFloor, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'h': // hedge
                sysRenderAnimatedSprite.add(Hedge.create(texHedge, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'W': // Word Wall
                sysRenderAnimatedSprite.add(CreateSprites.createWordWall(texWordWall, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
                break;
            case 'R': // Word Rock
                sysRenderAnimatedSprite.add(Rock.create(texWordRock, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y, true));
                break;
            case 'F': // Word Flag
                sysRenderAnimatedSprite.add(Floor.create(texWordFlag, spriteRectCenters[row][col].x, spriteRectCenters[row][col].y));
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
            case 'Y': // Word You
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
