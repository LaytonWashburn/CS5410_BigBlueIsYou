package views;

import core.GameModel;
import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.Graphics2D;
import level.Level;
import utils.KeyBindSerializer;
import utils.KeyBinds;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

public class GamePlayView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.GamePlay;
    private GameModel gameModel;

    private KeyBindSerializer keyBindSerializer;
    private KeyBinds keyBinds;

    public GamePlayView(KeyBindSerializer serializer){
        this.keyBindSerializer = serializer;
        this.keyBinds = this.keyBindSerializer.keybinds;
    }

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.NewGame;
        });
        inputKeyboard.registerCommand(GLFW_KEY_Z, true, (double elapsedTime) -> {
            gameModel.triggerUndo();
        });
    }

    @Override
    public void initializeSession() {
        Level level = gameViewManager.getSelectedLevel();
        this.keyBindSerializer.loadGameState(this.keyBinds);
        gameModel = new GameModel(level, this.keyBinds);
        try {
            gameModel.initialize(graphics);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        nextGameState = GameStateEnum.GamePlay;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) throws CloneNotSupportedException {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) throws CloneNotSupportedException {
        gameModel.update(elapsedTime);
    }

    @Override
    public void render(double elapsedTime) {
        // Nothing to do because the render now occurs in the update of the game model
    }
}
