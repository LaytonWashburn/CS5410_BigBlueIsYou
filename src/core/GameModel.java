package core;

import ecs.Entities.*;
import ecs.Systems.KeyboardInput;
import ecs.Systems.RenderAnimatedSprite;
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

public class GameModel {

    private RenderAnimatedSprite sysRenderAnimatedSprite;

    private final Level level;
    private KeyBinds keybinds;

    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();
    private KeyboardInput sysKeyboardInput;

    private Graphics2D graphics;

    private Texture texFlag = new Texture("resources/sprites/sprites/objects/flag/flag.png");
    private Texture texRock = new Texture("resources/sprites/sprites/objects/rock/rock.png");

    public GameModel(Level level, KeyBinds keybinds) {

        this.level = level;
        this.keybinds = keybinds;
    }

    public void initialize(Graphics2D graphics) {
        sysKeyboardInput = new KeyboardInput(graphics.getWindow());

        this.graphics = graphics;

        this.sysRenderAnimatedSprite = new RenderAnimatedSprite(graphics);

        System.out.println("GameModel initialized with level: " + level);
        keybinds.printKeyBinds();

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

        addEntityTemp(Flag.create(texFlag, 0f, 0f));
        addEntityTemp(Rock.create(texRock, .25f, .25f, true));
    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);

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

    }

    private void addEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.add(entity);
    }

    private void removeEntityTemp(Entity entity) {
        sysRenderAnimatedSprite.remove(entity.getId());
    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
    }


}
