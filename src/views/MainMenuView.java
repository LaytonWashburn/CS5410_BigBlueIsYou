
package views;

import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class MainMenuView extends GameStateView {

    private enum MenuState {
        NewGame,
        Controls,
        Credits,
        Exit;

        public MenuState next() {
            int nextOrdinal = (this.ordinal() + 1) % MenuState.values().length;
            return MenuState.values()[nextOrdinal];
        }

        public MenuState previous() {
            int previousOrdinal = (this.ordinal() - 1) % MenuState.values().length;
            if (previousOrdinal < 0) {
                previousOrdinal = Exit.ordinal();
            }
            return MenuState.values()[previousOrdinal];
        }
    }

    private MenuState currentSelection = MenuState.NewGame;
    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.MainMenu;
    private Font fontMenu;
    private Font fontSelected;
    private Texture texTitle;

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        fontMenu = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);
        fontSelected = new Font("resources/fonts/ChakraPetch-Bold.ttf", 48, false);

        texTitle = new Texture("resources/images/big_blue_is_you.png");

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
            nextGameState = switch (currentSelection) {
                case MenuState.NewGame -> GameStateEnum.NewGame;
                case MenuState.Controls -> GameStateEnum.Controls;
                case MenuState.Credits -> GameStateEnum.Credits;
                case MenuState.Exit -> GameStateEnum.Exit;
            };
        });
    }

    @Override
    public void initializeSession() {
        nextGameState = GameStateEnum.MainMenu;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) throws CloneNotSupportedException {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void render(double elapsedTime) {
        Rectangle titleRect = new Rectangle(-.3f, -.5f, .6f, .2f);
        graphics.draw(texTitle, titleRect, Color.WHITE);

        final float HEIGHT_MENU_ITEM = 0.075f;
        float top = -0.2f;
        top = renderMenuItem(currentSelection == MenuState.NewGame ? fontSelected : fontMenu, "New Game", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.NewGame ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == MenuState.Controls ? fontSelected : fontMenu, "Controls", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Controls ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == MenuState.Credits ? fontSelected : fontMenu, "Credits", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Credits ? Color.GREEN : Color.WHITE);
        renderMenuItem(currentSelection == MenuState.Exit ? fontSelected : fontMenu, "Exit", top, HEIGHT_MENU_ITEM, currentSelection == MenuState.Exit ? Color.GREEN : Color.WHITE);
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
