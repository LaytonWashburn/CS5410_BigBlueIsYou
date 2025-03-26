package core;

import edu.usu.graphics.*;
import level.Level;
import utils.KeyBindSerializer;
import utils.KeyBinds;
import views.*;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public class GameViewManager {
    private final Graphics2D graphics;
    private HashMap<GameStateEnum, IGameState> states;
    private IGameState currentState;
    GameStateEnum nextStateEnum = GameStateEnum.MainMenu;
    GameStateEnum prevStateEnum = GameStateEnum.MainMenu;
    private Level selectedLevel = new Level("Null", 0, 0, new char[0][0], new char[0][0]);

    public KeyBindSerializer keyBindSerializer;
    public KeyBinds keyBind;

    public GameViewManager(Graphics2D graphics) {

        this.graphics = graphics;
        this.keyBind = new KeyBinds();

    }

    public void initialize() {

        this.keyBindSerializer = new KeyBindSerializer();

        states = new HashMap<>() {
            {
                put(GameStateEnum.MainMenu, new MainMenuView());
                put(GameStateEnum.NewGame, new NewGameView());
                put(GameStateEnum.GamePlay, new GamePlayView());
                put(GameStateEnum.Controls, new ControlsView(keyBindSerializer, keyBind));
                put(GameStateEnum.Credits, new CreditsView());
            }
        };

        // Give all game states a chance to initialize, other than the constructor
        for (var state : states.values()) {
            if (state instanceof GameStateView) {
                ((GameStateView)state).setGameViewManager(this);    // Each view has a reference to this Manager (to keep track of selected level)
            }
            state.initialize(graphics);
        }

        currentState = states.get(GameStateEnum.MainMenu);
        currentState.initializeSession();
    }

    public void setSelectedLevel(Level level) {
        this.selectedLevel = level;
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public void shutdown() {
    }

    public void run() {
        // Grab the first time
        double previousTime = glfwGetTime();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!graphics.shouldClose()) {
            double currentTime = glfwGetTime();
            double elapsedTime = currentTime - previousTime;    // elapsed time is in seconds
            previousTime = currentTime;

            processInput(elapsedTime);
            update(elapsedTime);
            render(elapsedTime);
        }
    }

    private void processInput(double elapsedTime) {
        // Poll for window events: required in order for window, keyboard, etc events are captured.
        glfwPollEvents();

        nextStateEnum = currentState.processInput(elapsedTime);
    }

    private void update(double elapsedTime) {
        // Special case for exiting the game
        if (nextStateEnum == GameStateEnum.Exit) {
            glfwSetWindowShouldClose(graphics.getWindow(), true);
        } else {
            if (nextStateEnum == prevStateEnum) {
                currentState.update(elapsedTime);
            } else {
                currentState = states.get(nextStateEnum);
                currentState.initializeSession();
                prevStateEnum = nextStateEnum;
            }
        }
    }

    private void render(double elapsedTime) {
        graphics.begin();

        currentState.render(elapsedTime);

        graphics.end();
    }
}
