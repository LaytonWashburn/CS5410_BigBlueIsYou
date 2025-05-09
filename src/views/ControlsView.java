package views;

import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;
import utils.KeyBindSerializer;
import utils.KeyBinds;

import java.util.HashMap;

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
    private Font fontMenu;
    private Font fontSelected;
    private boolean waitingForNewKey;
    private boolean initialized;
    private boolean deregisterArrows;
    private final KeyBindSerializer keyBindSerializer;
    private final KeyBinds keyBinds;

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
    public GameStateEnum processInput(double elapsedTime) throws CloneNotSupportedException {
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
                if (glfwGetKey(graphics.getWindow(), key) == GLFW_PRESS &&
                        (glfwGetKeyName(key, glfwGetKeyScancode(key)) != null || (262 <= key && key <= 265))) {
                    switch (currentSelection) {
                        case ControlState.UP:
                            this.keyBinds.UP = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
                            break;
                        case ControlState.DOWN:
                            this.keyBinds.DOWN = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
                            break;
                        case ControlState.LEFT:
                            this.keyBinds.LEFT = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
                            break;
                        case ControlState.RIGHT:
                            this.keyBinds.RIGHT = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
                            break;
                        case ControlState.RESET:
                            this.keyBinds.RESET = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
                            break;
                        case ControlState.UNDO:
                            this.keyBinds.UNDO = key;
                            this.keyBindSerializer.saveGameState(this.keyBinds);
                            this.keyBindSerializer.saveSomething();
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
                    inputKeyboard.setKeyPressed(GLFW_KEY_UP);   // Prevent the arrow keys from immediately triggering if they were bound as controls
                    inputKeyboard.setKeyPressed(GLFW_KEY_DOWN);
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
        float top = -0.35f;

        this.keyBindSerializer.loadGameState(this.keyBinds); // Find a better spot for this

        HashMap<Integer, String> keysAndNames = new HashMap<>();
        HashMap<Integer, String> keysAndNamesToAdd = new HashMap<>();
        keysAndNames.put(this.keyBinds.UP, glfwGetKeyName(this.keyBinds.UP, glfwGetKeyScancode(this.keyBinds.UP)));
        keysAndNames.put(this.keyBinds.DOWN, glfwGetKeyName(this.keyBinds.DOWN, glfwGetKeyScancode(this.keyBinds.DOWN)));
        keysAndNames.put(this.keyBinds.LEFT, glfwGetKeyName(this.keyBinds.LEFT, glfwGetKeyScancode(this.keyBinds.LEFT)));
        keysAndNames.put(this.keyBinds.RIGHT, glfwGetKeyName(this.keyBinds.RIGHT, glfwGetKeyScancode(this.keyBinds.RIGHT)));
        keysAndNames.put(this.keyBinds.RESET, glfwGetKeyName(this.keyBinds.RESET, glfwGetKeyScancode(this.keyBinds.RESET)));
        keysAndNames.put(this.keyBinds.UNDO, glfwGetKeyName(this.keyBinds.UNDO, glfwGetKeyScancode(this.keyBinds.UNDO)));

        for (Integer key : keysAndNames.keySet()) {
            if (!interpretKeyName(key).equals("null")) {
                keysAndNamesToAdd.put(key, interpretKeyName(key));
            }
        }

        for (Integer key : keysAndNamesToAdd.keySet()) {
            keysAndNames.remove(key);
            keysAndNames.put(key, keysAndNamesToAdd.get(key));
        }

        top = renderMenuItem(currentSelection == ControlsView.ControlState.UP ? fontSelected : fontMenu, "UP : " + keysAndNames.get(this.keyBinds.UP), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.UP? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.DOWN ? fontSelected : fontMenu, "DOWN : " + keysAndNames.get(this.keyBinds.DOWN), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.DOWN ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.LEFT ? fontSelected : fontMenu, "LEFT : " + keysAndNames.get(this.keyBinds.LEFT), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.LEFT ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.RIGHT ? fontSelected : fontMenu, "RIGHT : " + keysAndNames.get(this.keyBinds.RIGHT), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.RIGHT? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.RESET ? fontSelected : fontMenu, "RESET : " + keysAndNames.get(this.keyBinds.RESET), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.RESET ? Color.GREEN : Color.WHITE);
        top = renderMenuItem(currentSelection == ControlsView.ControlState.UNDO ? fontSelected : fontMenu, "UNDO : " + keysAndNames.get(this.keyBinds.UNDO), top, HEIGHT_MENU_ITEM, currentSelection == ControlState.UNDO ? Color.GREEN : Color.WHITE);
        renderMenuItem(currentSelection == ControlsView.ControlState.EXIT ? fontSelected : fontMenu, "EXIT", top+.1f, HEIGHT_MENU_ITEM, currentSelection == ControlState.EXIT ? Color.GREEN : Color.WHITE);

        if(waitingForNewKey){
            renderMenuItem(fontSelected , "SETTING NEW KEY", top, HEIGHT_MENU_ITEM, Color.RED);
        }
    }

    /**
     * This function is used to interpret the names of any keys that do not have a native, printable name.
     * Right now it is being used to interpret the names of only the arrow keys,
     * as they are the only special keys that we want to be able to be bound as controls.
     */
    private String interpretKeyName(Integer keyCode) {
        return switch (keyCode) {
            case (262) -> "Right";
            case (263) -> "Left";
            case (264) -> "Down";
            case (265) -> "Up";
            default -> "null";
        };
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