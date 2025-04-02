package views;

import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;
import utils.KeyBindSerializer;
import utils.KeyBinds;

import static org.lwjgl.glfw.GLFW.*;

public class ControlsView extends GameStateView {

    enum ControlState {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        RESET,
        UNDO,
        EXIT;

        public ControlsView.ControlState next() {
            int nextOrdinal = (this.ordinal() + 1) % ControlsView.ControlState.values().length;
            return ControlsView.ControlState.values()[nextOrdinal];
        }

        public ControlsView.ControlState previous() {
            int previousOrdinal = (this.ordinal() - 1) % ControlsView.ControlState.values().length;
            if (previousOrdinal < 0) {
                previousOrdinal = EXIT.ordinal();
            }
            return ControlsView.ControlState.values()[previousOrdinal];
        }
    }

    private ControlState currentSelection;
    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.Controls;
    private Font font;
    private Font fontMenu;
    private Font fontSelected;
    private boolean waitingForNewKey;
    private boolean initialized;
    private boolean deregisterArrows;
    private KeyBindSerializer keyBindSerializer;
    private KeyBinds keyBinds;

    public ControlsView(KeyBindSerializer keyBindSerializer, KeyBinds keyBinds){
        this.keyBindSerializer = keyBindSerializer;
        this.keyBinds = keyBinds;
        this.initialized = false;
        this.waitingForNewKey = false;
        this.deregisterArrows = false;
    }


    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);


        font = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);
        fontMenu = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);
        fontSelected = new Font("resources/fonts/ChakraPetch-Bold.ttf", 48, false);
        currentSelection = ControlState.UP;
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
            if(currentSelection == ControlState.EXIT){
                nextGameState = GameStateEnum.MainMenu;
            }

//            // Remove the current key bind
//            this.inputKeyboard.deregisterCommand(glfwGetKeyScancode(this.currentSelection.ordinal()));

            if(initialized){
                waitingForNewKey = true; // !waitingForNewKey;
                deregisterArrows = true;
            }

        });

        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });

        waitingForNewKey = false;

    }

    @Override
    public void initializeSession() {
        inputKeyboard.setKeyPressed(GLFW_KEY_ENTER);
        inputKeyboard.setKeyPressed(GLFW_KEY_ESCAPE);
        nextGameState = GameStateEnum.Controls;
        waitingForNewKey = false;
        inputKeyboard.registerCommand(GLFW_KEY_UP, true, (double elapsedTime) -> {
            currentSelection = currentSelection.previous();
        });
        inputKeyboard.registerCommand(GLFW_KEY_DOWN, true, (double elapsedTime) -> {
            currentSelection = currentSelection.next();
        });
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);

        if(deregisterArrows){
            this.inputKeyboard.deregisterCommand(GLFW_KEY_UP);
            this.inputKeyboard.deregisterCommand(GLFW_KEY_DOWN);
            deregisterArrows = false;
        }

        if(waitingForNewKey){

            for (int key = GLFW_KEY_SPACE; key <= GLFW_KEY_LAST; key++) {

                // This is a hacky solution, using the second part of the boolean
                if (glfwGetKey(graphics.getWindow(), key) == GLFW_PRESS && glfwGetKeyName(key, glfwGetKeyScancode(key)) != null) {
                    switch (currentSelection) {
                        case ControlState.UP:
                            this.keyBinds.UP = key;
                            break;
                        case ControlState.DOWN:
                            this.keyBinds.DOWN = key;
                            break;
                        case ControlState.LEFT:
                            this.keyBinds.LEFT = key;
                            break;
                        case ControlState.RIGHT:
                            this.keyBinds.RIGHT = key;
                            break;
                        case ControlState.RESET:
                            this.keyBinds.RESET = key;
                            break;
                        case ControlState.UNDO:
                            this.keyBinds.UNDO = key;
                            break;
                        default:
                            break;
                    }
                    this.keyBindSerializer.saveGameState(this.keyBinds); // Save the new key binds
                    waitingForNewKey = false;

                    // Arrow keys to navigate the menu
                    inputKeyboard.registerCommand(GLFW_KEY_UP, true, (double elapsedTime_) -> {
                        currentSelection = currentSelection.previous();
                    });
                    inputKeyboard.registerCommand(GLFW_KEY_DOWN, true, (double elapsedTime_) -> {
                        currentSelection = currentSelection.next();
                    });
                }
            }
        }

        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {

        initialized = true; // This will make this true every update, need to find a new place

    }

    @Override
    public void render(double elapsedTime) {
        final float HEIGHT_MENU_ITEM = 0.075f;
        float top = -0.2f;

        this.keyBindSerializer.loadGameState(this.keyBinds); // Find a better spot for this

        top = renderMenuItem(currentSelection == ControlsView.ControlState.UP ? fontSelected : fontMenu, "UP : " + glfwGetKeyName(this.keyBinds.UP, glfwGetKeyScancode(this.keyBinds.UP)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.UP? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.DOWN ? fontSelected : fontMenu, "DOWN : " + glfwGetKeyName(this.keyBinds.DOWN, glfwGetKeyScancode(this.keyBinds.DOWN)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.DOWN ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.LEFT ? fontSelected : fontMenu, "LEFT : " + glfwGetKeyName(this.keyBinds.LEFT, glfwGetKeyScancode(this.keyBinds.LEFT)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.LEFT ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.RIGHT ? fontSelected : fontMenu, "RIGHT : " + glfwGetKeyName(this.keyBinds.RIGHT, glfwGetKeyScancode(this.keyBinds.RIGHT)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.RIGHT? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.RESET ? fontSelected : fontMenu, "RESET : " + glfwGetKeyName(this.keyBinds.RESET, glfwGetKeyScancode(this.keyBinds.RESET)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.RESET ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.UNDO ? fontSelected : fontMenu, "UNDO : " + glfwGetKeyName(this.keyBinds.UNDO, glfwGetKeyScancode(this.keyBinds.UNDO)), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.UNDO ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.EXIT ? fontSelected : fontMenu, "EXIT", top, HEIGHT_MENU_ITEM, currentSelection == ControlState.EXIT ? Color.GREEN : Color.WHITE);

        if(waitingForNewKey){
            renderMenuItem(fontSelected , "SETTING NEW KEY", top, HEIGHT_MENU_ITEM, Color.RED);
        }
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
