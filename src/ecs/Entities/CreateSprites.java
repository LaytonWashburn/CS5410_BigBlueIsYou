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
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWall(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity wall = new Entity();

        wall.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wall.add(new ecs.Components.Position(posX, posY));
        wall.add(new ecs.Components.Noun(NounType.WALL));

        return wall;

    }

    /**
     * Method: Create Rock
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @param pushable - Boolean to represent it's pushable or not
     * @return Entity - Entity created
     */
    public static Entity createRock(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity rock = new Entity();

        rock.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        rock.add(new ecs.Components.Position(posX, posY));
        rock.add(new ecs.Components.Noun(NounType.ROCK));

        return rock;

    }

    /**
     * Method: Create Flag
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createFlag(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity flag = new Entity();

        flag.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        flag.add(new ecs.Components.Position(posX, posY));
        flag.add(new ecs.Components.Noun(NounType.FLAG));

        return flag;

    }

    /**
     * Method: Create Big Blue
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createBigBlue(Texture texture, float posX, float posY, KeyBinds keybinds){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity bigBlue = new Entity();

        bigBlue.add(new ecs.Components.BigBlue());
        bigBlue.add(new StaticSprite(texture));
        bigBlue.add(new ecs.Components.Position(posX - EntityConstants.rectSize / 2, posY - EntityConstants.rectSize / 2));
        bigBlue.add(new ecs.Components.Noun(NounType.BIGBLUE));
        bigBlue.add(new ecs.Components.Movement(Direction.STOP));
        bigBlue.add(new ecs.Components.Property(Properties.MOVE));
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
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createFloor(Texture texture, float posX, float posY){
        Entity floor = new Entity();

        floor.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        floor.add(new ecs.Components.Position(posX, posY));
        floor.add(new ecs.Components.Noun(NounType.FLOOR));

        return floor;

    }

    /**
     * Method: Create Grass
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createGrass(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity grass = new Entity();

        grass.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        grass.add(new ecs.Components.Position(posX, posY));
        grass.add(new ecs.Components.Noun(NounType.GRASS));

        return grass;

    }

    /**
     * Method: Create Water
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWater(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity water = new Entity();

        water.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        water.add(new ecs.Components.Position(posX, posY));
        water.add(new ecs.Components.Noun(NounType.WATER));

        return water;

    }

    /**
     * Method: Create Lava
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createLava(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity lava = new Entity();

        lava.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        lava.add(new ecs.Components.Position(posX, posY));
        lava.add(new ecs.Components.Noun(NounType.LAVA));

        return lava;

    }

    /**
     * Method: Create Hedge
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createHedge(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity hedge = new Entity();

        hedge.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        hedge.add(new ecs.Components.Position(posX, posY));
        hedge.add(new ecs.Components.Noun(NounType.HEDGE));

        return hedge;
    }


    /**
     * Method: Create Word Wall
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWall(Texture texture, float posX, float posY){
        Entity wordWall = new Entity();

        wordWall.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWall.add(new ecs.Components.Position(posX, posY));
        wordWall.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWall;

    }

    /**
     * Method: Create Word Rock
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordRock(Texture texture, float posX, float posY){
        Entity wordRock = new Entity();

        wordRock.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordRock.add(new ecs.Components.Position(posX, posY));
        wordRock.add(new ecs.Components.Noun(NounType.LAVA));

        return wordRock;

    }

    /**
     * Method: Create Word Is
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordIs(Texture texture, float posX, float posY){
        Entity wordIs = new Entity();

        wordIs.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordIs.add(new ecs.Components.Position(posX, posY));
        wordIs.add(new ecs.Components.Noun(NounType.LAVA));

        return wordIs;

    }

    /**
     * Method: Create Word Stop
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordStop(Texture texture, float posX, float posY){
        Entity wordStop = new Entity();

        wordStop.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordStop.add(new ecs.Components.Position(posX, posY));
        wordStop.add(new ecs.Components.Noun(NounType.LAVA));

        return wordStop;

    }

    /**
     * Method: Create Word Push
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordPush(Texture texture, float posX, float posY){
        Entity wordPush = new Entity();

        wordPush.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordPush.add(new ecs.Components.Position(posX, posY));
        wordPush.add(new ecs.Components.Noun(NounType.LAVA));

        return wordPush;

    }

    /**
     * Method: Create Word With
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWin(Texture texture, float posX, float posY){
        Entity wordWin = new Entity();

        wordWin.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWin.add(new ecs.Components.Position(posX, posY));
        wordWin.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWin;

    }

    /**
     * Method: Create Word You
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordYou(Texture texture, float posX, float posY){
        Entity wordYou = new Entity();

        wordYou.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordYou.add(new ecs.Components.Position(posX, posY));
        wordYou.add(new ecs.Components.Noun(NounType.LAVA));

        return wordYou;

    }

    /**
     * Method: Create Word Sink
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordSink(Texture texture, float posX, float posY){
        Entity wordSink = new Entity();

        wordSink.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordSink.add(new ecs.Components.Position(posX, posY));
        wordSink.add(new ecs.Components.Noun(NounType.LAVA));

        return wordSink;

    }

    /**
     * Method: Create Word Kill
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordKill(Texture texture, float posX, float posY){
        Entity wordKill = new Entity();

        wordKill.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordKill.add(new ecs.Components.Position(posX, posY));
        wordKill.add(new ecs.Components.Noun(NounType.LAVA));

        return wordKill;

    }


    /**
     * Method: Create Word Baba
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordBaba(Texture texture, float posX, float posY){
        Entity wordBaba = new Entity();

        wordBaba.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordBaba.add(new ecs.Components.Position(posX, posY));
        wordBaba.add(new ecs.Components.Noun(NounType.LAVA));

        return wordBaba;

    }

    /**
     * Method: Create Word Lava
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordLava(Texture texture, float posX, float posY){
        Entity wordLava = new Entity();

        wordLava.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordLava.add(new ecs.Components.Position(posX, posY));
        wordLava.add(new ecs.Components.Noun(NounType.LAVA));

        return wordLava;

    }

    /**
     * Method: Create Word Water
     * @param texture - Texture to bind to the entity
     * @param posX - X position of object initially
     * @param posY - Y position of object initially
     * @return Entity - Entity created
     */
    public static Entity createWordWater(Texture texture, float posX, float posY){
        Entity wordWater = new Entity();

        wordWater.add(new ecs.Components.AnimatedSprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWater.add(new ecs.Components.Position(posX, posY));
        wordWater.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWater;

    }


}
