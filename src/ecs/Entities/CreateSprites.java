package ecs.Entities;

import ecs.Components.StaticSprite;
import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.*;

import java.util.Map;

/**
 * Class: Create Sprites
 * Description: Class to hold create method on all game objects
 */
public class CreateSprites {
    static float frameTime = EntityConstants.frameTime;
    static float rectSize = EntityConstants.rectSize;


    /**
     * Method: Create Wall
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWall(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity wall = new Entity();

        wall.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wall.add(new ecs.Components.Position(i, j));
        wall.add(new ecs.Components.Noun(NounType.WALL));
        wall.add(new ecs.Components.Property());
        wall.add(new ecs.Components.Movement(Direction.STOP));
        wall.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return wall;

    }

    /**
     * Method: Create Rock
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createRock(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity rock = new Entity();

        rock.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        rock.add(new ecs.Components.Position(i, j));
        rock.add(new ecs.Components.Noun(NounType.ROCK));
        rock.add(new ecs.Components.Property());
        rock.add(new ecs.Components.Movement(Direction.STOP));
        rock.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return rock;

    }

    /**
     * Method: Create Flag
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createFlag(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity flag = new Entity();

        flag.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        flag.add(new ecs.Components.Position(i, j));
        flag.add(new ecs.Components.Noun(NounType.FLAG));
        flag.add(new ecs.Components.Property());
        flag.add(new ecs.Components.Movement(Direction.STOP));
        flag.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return flag;

    }

    /**
     * Method: Create Big Blue
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createBigBlue(Texture texture, int i, int j, KeyBinds keybinds) {
        Entity bigBlue = new Entity();

        bigBlue.add(new ecs.Components.BigBlue()); // Don't know if this is needed
        bigBlue.add(new StaticSprite(texture));
        bigBlue.add(new ecs.Components.Position(i, j));
        bigBlue.add(new ecs.Components.Noun(NounType.BIGBLUE));
        bigBlue.add(new ecs.Components.Property());
        bigBlue.add(new ecs.Components.Movement(Direction.STOP));
        bigBlue.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return  bigBlue;

    }


    /**
     * Method: Create Floor
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createFloor(Texture texture, int i, int j, KeyBinds keybinds) {
        Entity floor = new Entity();

        floor.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j), -1)));
        floor.add(new ecs.Components.Position(i, j));
        floor.add(new ecs.Components.Noun(NounType.FLOOR));
        floor.add(new ecs.Components.Property());
        floor.add(new ecs.Components.Movement(Direction.STOP));
        floor.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return floor;

    }

    /**
     * Method: Create Grass
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createGrass(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity grass = new Entity();

        grass.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        grass.add(new ecs.Components.Position(i, j));
        grass.add(new ecs.Components.Noun(NounType.GRASS));
        grass.add(new ecs.Components.Property());
        grass.add(new ecs.Components.Movement(Direction.STOP));
        grass.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return grass;

    }

    /**
     * Method: Create Water
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWater(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity water = new Entity();

        water.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        water.add(new ecs.Components.Position(i, j));
        water.add(new ecs.Components.Noun(NounType.WATER));
        water.add(new ecs.Components.Property());
        water.add(new ecs.Components.Movement(Direction.STOP));
        water.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return water;

    }

    /**
     * Method: Create Lava
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createLava(Texture texture, int i, int j, KeyBinds keybinds) {
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity lava = new Entity();

        lava.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        lava.add(new ecs.Components.Position(i, j));
        lava.add(new ecs.Components.Noun(NounType.LAVA));
        lava.add(new ecs.Components.Property());
        lava.add(new ecs.Components.Movement(Direction.STOP));
        lava.add(new ecs.Components.KeyboardControlled(
                Map.of(keybinds.UP, Direction.UP,
                        keybinds.DOWN, Direction.DOWN,
                        keybinds.RIGHT, Direction.RIGHT,
                        keybinds.LEFT, Direction.LEFT
                )
        ));

        return lava;

    }

    /**
     * Method: Create Hedge
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createHedge(Texture texture, int i, int j){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity hedge = new Entity();

        hedge.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        hedge.add(new ecs.Components.Position(i, j));
        hedge.add(new ecs.Components.Noun(NounType.HEDGE));
        hedge.add(new ecs.Components.Property(Properties.STOP));

        return hedge;
    }


    /**
     * Method: Create Word Wall
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWall(Texture texture, int i, int j){
        Entity wordWall = new Entity();

        wordWall.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordWall.add(new ecs.Components.Position(i, j));
        wordWall.add(new ecs.Components.Noun(NounType.TEXT));
        wordWall.add(new ecs.Components.Text(TextType.NOUN));
        wordWall.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordWall.add(new ecs.Components.Represent(NounType.WALL));

        return wordWall;

    }

    /**
     * Method: Create Word Rock
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordRock(Texture texture, int i, int j){
        Entity wordRock = new Entity();

        wordRock.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordRock.add(new ecs.Components.Position(i, j));
        wordRock.add(new ecs.Components.Noun(NounType.TEXT));
        wordRock.add(new ecs.Components.Text(TextType.NOUN));
        wordRock.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordRock.add(new ecs.Components.Represent(NounType.ROCK));
        return wordRock;

    }

    /**
     * Method: Create Word Is
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordIs(Texture texture, int i, int j){
        Entity wordIs = new Entity();

        wordIs.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordIs.add(new ecs.Components.Position(i, j));
        wordIs.add(new ecs.Components.Noun(NounType.TEXT));
        wordIs.add(new ecs.Components.Text(TextType.VERB));
        wordIs.add(new ecs.Components.Property(Properties.PUSHABLE));

        return wordIs;

    }

    /**
     * Method: Create Word Stop
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordStop(Texture texture, int i, int j){
        Entity wordStop = new Entity();

        wordStop.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordStop.add(new ecs.Components.Position(i, j));
        wordStop.add(new ecs.Components.Noun(NounType.TEXT));
        wordStop.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordStop.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordStop.add(new ecs.Components.Action(Action.STOP));

        return wordStop;

    }

    /**
     * Method: Create Word Push
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordPush(Texture texture, int i, int j){
        Entity wordPush = new Entity();

        wordPush.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordPush.add(new ecs.Components.Position(i, j));
        wordPush.add(new ecs.Components.Noun(NounType.TEXT));
        wordPush.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordPush.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordPush.add(new ecs.Components.Action(Action.PUSH));
        return wordPush;

    }

    /**
     * Method: Create Word With
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWin(Texture texture, int i, int j){
        Entity wordWin = new Entity();

        wordWin.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordWin.add(new ecs.Components.Position(i, j));
        wordWin.add(new ecs.Components.Noun(NounType.TEXT));
        wordWin.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordWin.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordWin.add(new ecs.Components.Action(Action.WIN));

        return wordWin;

    }

    /**
     * Method: Create Word You
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordYou(Texture texture, int i, int j){
        Entity wordYou = new Entity();

        wordYou.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordYou.add(new ecs.Components.Position(i, j));
        wordYou.add(new ecs.Components.Noun(NounType.TEXT));
        wordYou.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordYou.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordYou.add(new ecs.Components.Action(Action.YOU));

        return wordYou;

    }

    /**
     * Method: Create Word Sink
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordSink(Texture texture, int i, int j){
        Entity wordSink = new Entity();

        wordSink.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordSink.add(new ecs.Components.Position(i, j));
        wordSink.add(new ecs.Components.Noun(NounType.TEXT));
        wordSink.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordSink.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordSink.add(new ecs.Components.Action(Action.SINK));

        return wordSink;

    }

    /**
     * Method: Create Word Kill
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordKill(Texture texture, int i, int j){
        Entity wordKill = new Entity();

        wordKill.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordKill.add(new ecs.Components.Position(i, j));
        wordKill.add(new ecs.Components.Noun(NounType.TEXT));
        wordKill.add(new ecs.Components.Text(TextType.ADJECTIVE));
        wordKill.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordKill.add(new ecs.Components.Action(Action.KILL));


        return wordKill;

    }


    /**
     * Method: Create Word Baba
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordBaba(Texture texture, int i, int j){
        Entity wordBaba = new Entity();

        wordBaba.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordBaba.add(new ecs.Components.Position(i, j));
        wordBaba.add(new ecs.Components.Noun(NounType.TEXT));
        wordBaba.add(new ecs.Components.Text(TextType.NOUN));
        wordBaba.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordBaba.add(new ecs.Components.Represent(NounType.BIGBLUE));

        return wordBaba;

    }

    /**
     * Method: Create Word Lava
     * @param texture - Texture to bind to the entity
     * @param i - X position of object initially
     * @param j - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordLava(Texture texture, int i, int j){
        Entity wordLava = new Entity();

        wordLava.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordLava.add(new ecs.Components.Position(i, j));
        wordLava.add(new ecs.Components.Noun(NounType.TEXT));
        wordLava.add(new ecs.Components.Text(TextType.NOUN));
        wordLava.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordLava.add(new ecs.Components.Represent(NounType.LAVA));

        return wordLava;

    }

    /**
     * Method: Create Word Water
     * @param texture - Texture to bind to the entity
     * @param i - Y position of object initially
     * @param j - X position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWater(Texture texture, int i, int j){
        Entity wordWater = new Entity();

        wordWater.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordWater.add(new ecs.Components.Position(i, j));
        wordWater.add(new ecs.Components.Noun(NounType.TEXT));
        wordWater.add(new ecs.Components.Text(TextType.NOUN));
        wordWater.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordWater.add(new ecs.Components.Represent(NounType.WATER));
        return wordWater;

    }

    /**
     * Method: Create Word Flag
     * @param texture - Texture to bind to the entity
     * @param i - Y position of object initially
     * @param j - X position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordFlag(Texture texture, int i, int j){
        Entity wordFlag = new Entity();

        wordFlag.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(i, j))));
        wordFlag.add(new ecs.Components.Position(i, j));
        wordFlag.add(new ecs.Components.Noun(NounType.TEXT));
        wordFlag.add(new ecs.Components.Text(TextType.NOUN));
        wordFlag.add(new ecs.Components.Property(Properties.PUSHABLE));
        wordFlag.add(new ecs.Components.Represent(NounType.FLAG));
        return wordFlag;

    }

}