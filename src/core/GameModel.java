package core;

import ecs.Entities.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import level.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import utils.KeyBinds;

public class GameModel {

    private final Level level;
    private KeyBinds keybinds;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();
    private KeyboardInput sysKeyboardInput;
    private ArrayList<ecs.Systems.System> systems;
    private Map changed;
    private Graphics2D graphics;

    private Texture texFlag = new Texture("resources/sprites/sprites/objects/flag/flag.png");
    private AnimatedSprite flagAnimated = new AnimatedSprite(texFlag, new float[] {.1f, .1f, .1f}, new Vector2f(.2f, .2f), new Vector2f(0f, 0f));

    public GameModel(Level level, KeyBinds keybinds) {

        this.level = level;
        this.keybinds = keybinds;
        systems = new ArrayList<>();
        this.changed = new HashMap<Long, Entity>();
    }

    public void initialize(Graphics2D graphics) {
        sysKeyboardInput = new KeyboardInput(graphics.getWindow());
        systems.add(sysKeyboardInput);

        this.graphics = graphics;

        System.out.println("GameModel initialized with level: " + level);
        keybinds.printKeyBinds();

        initalizeLevel(level);

    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update


//        for (var system : systems) {
//            for (var entity : system.update(elapsedTime)) {
//                changed.put(entity.getId(), entity);
//            }
//        }
//
//        for (var entity : changed) {
//            for (var system : systems) {
//                system.updatedEntity(entity);
//            }
//        }


        sysKeyboardInput.update(elapsedTime);

        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

        for (var entity : addThese) {
            addEntity(entity);
        }
        addThese.clear();

        // Because ECS framework, rendering is now part of the update
        flagAnimated.update(elapsedTime);
        flagAnimated.draw(graphics, Color.WHITE);
    }

    private void addEntity(Entity entity) {
        sysKeyboardInput.add(entity);

    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
    }

    private void initalizeLevel(Level level){

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                System.out.print(level.getGroup1()[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                System.out.print(level.getGroup2()[i][j]);
            }
            System.out.println();
        }

    }

//    private Entity createObjectTypes(Character symbol){
//
//        switch (symbol){
//            case 'w':
//                return Wall.create(0, 0);
//            case 'r':
//            case 'f':
//            case 'b':
//            case 'l':
//            case 'g':
//            case 'a':
//            case 'v':
//            case 'h':
//            default:
//        }
//
//    }

//    private Entity createTextTypes(Character symbol){
//
//        switch (symbol){
//            case 'W':
//            case 'R':
//            case 'F':
//            case 'B':
//            case 'L':
//            case 'S':
//            case 'P':
//            case 'V':
//            case 'A':
//            case 'Y':
//            case 'X':
//            case 'N':
//            case 'K':
//            default:
//        }
//
//    }


}
