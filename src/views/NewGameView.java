package views;

import core.GameStateEnum;
import core.KeyboardInput;
import core.GameViewManager;
import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

public class NewGameView extends GameStateView {

    private enum MenuState {
        Level1,
        Level2,
        Level3,
        Level4,
        Level5;

        public MenuState next() {
            int nextOrdinal = (this.ordinal() + 1) % MenuState.values().length;
            return MenuState.values()[nextOrdinal];
        }

        public MenuState previous() {
            int previousOrdinal = (this.ordinal() - 1) % MenuState.values().length;
            if (previousOrdinal < 0) {
                previousOrdinal = Level5.ordinal();
            }
            return MenuState.values()[previousOrdinal];
        }
    }

    private MenuState currentSelection = MenuState.Level1;
    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.NewGame;
    private Font fontMenu;
    private Font fontSelected;
    private int selectedLevel = 1;  // Default to level 1

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        fontMenu = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);
        fontSelected = new Font("resources/fonts/ChakraPetch-Bold.ttf", 48, false);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // Arrow keys to navigate the menu
        inputKeyboard.registerCommand(GLFW_KEY_UP, true, (double elapsedTime) -> {
            currentSelection = currentSelection.previous();
        });
        inputKeyboard.registerCommand(GLFW_KEY_DOWN, true, (double elapsedTime) -> {
            currentSelection = currentSelection.next();
        });
        // When Enter is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ENTER, true, (double elapsedTime) -> {
            selectedLevel = currentSelection.ordinal() + 1;
            // Pass the selected level to GameViewManager
            gameViewManager.setSelectedLevel(selectedLevel);
            nextGameState = GameStateEnum.GamePlay;
        });
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });
    }

    @Override
    public void initializeSession() {
        inputKeyboard.setKeyPressed(GLFW_KEY_ENTER);
        inputKeyboard.setKeyPressed(GLFW_KEY_ESCAPE);
        nextGameState = GameStateEnum.NewGame;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void render(double elapsedTime) {
        final float HEIGHT_MENU_ITEM = 0.075f;
        float top = -0.25f;
        top = renderMenuItem(currentSelection == MenuState.Level1 ? fontSelected : fontMenu, "Level 1", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Level1 ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == MenuState.Level2 ? fontSelected : fontMenu, "Level 2", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Level2 ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == MenuState.Level3 ? fontSelected : fontMenu, "Level 3", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Level3 ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == MenuState.Level4 ? fontSelected : fontMenu, "Level 4", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Level4 ? Color.GREEN : Color.WHITE);
        renderMenuItem(currentSelection == MenuState.Level5 ? fontSelected : fontMenu, "Level 5", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Level5 ? Color.GREEN : Color.WHITE);
    }

    /**
     * Centers the text horizontally, at the specified top position.
     * It also returns the vertical position to draw the next menu item
     */
    private float renderMenuItem(Font font, String text, float top, float height, Color color) {
        float width = font.measureTextWidth(text, height);
        graphics.drawTextByHeight(font, text, 0.0f - width / 2, top, height, color);

        return top + height;
    }
}
