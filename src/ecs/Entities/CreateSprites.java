package ecs.Entities;

import edu.usu.graphics.AnimatedSprite;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;
import utils.*;

import java.util.Map;

public class CreateSprites {
    static float frameTime = EntityConstants.frameTime;
    static float rectSize = EntityConstants.rectSize;

    public static Entity createLava(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity lava = new Entity();

        lava.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        lava.add(new ecs.Components.Position(posX, posY));
        lava.add(new ecs.Components.Noun(NounType.LAVA));

        return lava;

    }

    public static Entity createWater(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity water = new Entity();

        water.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        water.add(new ecs.Components.Position(posX, posY));
        water.add(new ecs.Components.Noun(NounType.WATER));

        return water;

    }

    public static Entity createHedge(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity hedge = new Entity();

        hedge.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        hedge.add(new ecs.Components.Position(posX, posY));
        hedge.add(new ecs.Components.Noun(NounType.WALL));

        return hedge;
    }

    public static Entity createBigBlue(Texture texture, float posX, float posY, KeyBinds keybinds){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity bigBlue = new Entity();

        bigBlue.add(new ecs.Components.BigBlue());
        bigBlue.add(new ecs.Components.Sprite());
        bigBlue.add(new ecs.Components.Appearance(texture));
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

    public static Entity createFlag(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity flag = new Entity();

        flag.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        flag.add(new ecs.Components.Position(posX, posY));
        flag.add(new ecs.Components.Noun(NounType.FLAG));

        return flag;

    }

    public static Entity createRock(Texture texture, float posX, float posY, boolean pushable){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity rock = new Entity();

        rock.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        rock.add(new ecs.Components.Position(posX, posY));
        rock.add(new ecs.Components.Noun(NounType.ROCK));
        if(pushable){
            rock.add(new ecs.Components.Property(Properties.PUSHABLE));
        }

        return rock;

    }

    public static Entity createWall(Texture texture, float posX, float posY){
        float frameTime = EntityConstants.frameTime;
        float rectSize = EntityConstants.rectSize;

        Entity wall = new Entity();

        wall.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wall.add(new ecs.Components.Position(posX, posY));
        wall.add(new ecs.Components.Noun(NounType.WALL));

        return wall;

    }

    public static Entity createWordWall(Texture texture, float posX, float posY){
        Entity wordWall = new Entity();

        wordWall.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWall.add(new ecs.Components.Position(posX, posY));
        wordWall.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWall;

    }

    public static Entity createWordIs(Texture texture, float posX, float posY){
        Entity wordIs = new Entity();

        wordIs.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordIs.add(new ecs.Components.Position(posX, posY));
        wordIs.add(new ecs.Components.Noun(NounType.LAVA));

        return wordIs;

    }

    public static Entity createWordStop(Texture texture, float posX, float posY){
        Entity wordStop = new Entity();

        wordStop.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordStop.add(new ecs.Components.Position(posX, posY));
        wordStop.add(new ecs.Components.Noun(NounType.LAVA));

        return wordStop;

    }

    public static Entity createWordPush(Texture texture, float posX, float posY){
        Entity wordPush = new Entity();

        wordPush.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordPush.add(new ecs.Components.Position(posX, posY));
        wordPush.add(new ecs.Components.Noun(NounType.LAVA));

        return wordPush;

    }

    public static Entity createWordWin(Texture texture, float posX, float posY){
        Entity wordWin = new Entity();

        wordWin.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWin.add(new ecs.Components.Position(posX, posY));
        wordWin.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWin;

    }

    public static Entity createWordYou(Texture texture, float posX, float posY){
        Entity wordYou = new Entity();

        wordYou.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordYou.add(new ecs.Components.Position(posX, posY));
        wordYou.add(new ecs.Components.Noun(NounType.LAVA));

        return wordYou;

    }

    public static Entity createWordSink(Texture texture, float posX, float posY){
        Entity wordSink = new Entity();

        wordSink.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordSink.add(new ecs.Components.Position(posX, posY));
        wordSink.add(new ecs.Components.Noun(NounType.LAVA));

        return wordSink;

    }

    public static Entity createWordKill(Texture texture, float posX, float posY){
        Entity wordKill = new Entity();

        wordKill.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordKill.add(new ecs.Components.Position(posX, posY));
        wordKill.add(new ecs.Components.Noun(NounType.LAVA));

        return wordKill;

    }


    public static Entity createWordBaba(Texture texture, float posX, float posY){
        Entity wordBaba = new Entity();

        wordBaba.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordBaba.add(new ecs.Components.Position(posX, posY));
        wordBaba.add(new ecs.Components.Noun(NounType.LAVA));

        return wordBaba;

    }

    public static Entity createWordLava(Texture texture, float posX, float posY){
        Entity wordLava = new Entity();

        wordLava.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordLava.add(new ecs.Components.Position(posX, posY));
        wordLava.add(new ecs.Components.Noun(NounType.LAVA));

        return wordLava;

    }

    public static Entity createWordWater(Texture texture, float posX, float posY){
        Entity wordWater = new Entity();

        wordWater.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        wordWater.add(new ecs.Components.Position(posX, posY));
        wordWater.add(new ecs.Components.Noun(NounType.LAVA));

        return wordWater;

    }

    public static Entity createFloor(Texture texture, float posX, float posY){
        Entity floor = new Entity();

        floor.add(new ecs.Components.Sprite(new AnimatedSprite(texture, new float[] {frameTime, frameTime, frameTime}, new Vector2f(rectSize, rectSize), new Vector2f(posX, posY))));
        floor.add(new ecs.Components.Position(posX, posY));
        floor.add(new ecs.Components.Noun(NounType.LAVA));

        return floor;

    }

}
