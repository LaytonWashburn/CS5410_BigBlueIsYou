package views;

import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;
import level.Level;
import level.LevelState;
import level.Serializer;

import static org.lwjgl.glfw.GLFW.*;

public class NewGameView extends GameStateView {
    private Serializer serializer;
    private LevelState levelState;
    private boolean areLevelsLoaded;

    private int currentSelection = 0;  // Index of currently selected level
    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.NewGame;
    private Font fontMenu;
    private Font fontSelected;
    private final Graphics2D graphics;

    public NewGameView(Serializer serializer, Graphics2D graphics){
        this.serializer = serializer;
        this.graphics =  graphics;
    }
    private Level selectedLevel = new Level("Null", 0, 0, new char[0][0], new char[0][0]);  // Default to empty level

    @Override
    public void initialize(Graphics2D graphic) {

        super.initialize(graphics);

        // this.serializer = new Serializer();
        this.levelState = new LevelState();

        // Set up callback for when loading completes
        this.serializer.setOnLoadComplete(() -> {
            if (this.levelState.initialized && !this.levelState.errorOccurred) {
                this.areLevelsLoaded = true;
                // Reset selection if it's out of bounds
                if (currentSelection >= this.levelState.levels.size()) {
                    currentSelection = 0;
                }
            } else {
                System.out.println("Levels not loaded");
            }
        });

        // Start loading
        this.serializer.loadLevelState(this.levelState);

        fontMenu = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);
        fontSelected = new Font("resources/fonts/ChakraPetch-Bold.ttf", 48, false);

        inputKeyboard = new KeyboardInput(this.graphics.getWindow());
        // Arrow keys to navigate the menu
        inputKeyboard.registerCommand(GLFW_KEY_UP, true, (double elapsedTime) -> {
            if (areLevelsLoaded && !this.levelState.levels.isEmpty()) {
                currentSelection = (currentSelection - 1 + this.levelState.levels.size()) % this.levelState.levels.size();
            }
        });
        inputKeyboard.registerCommand(GLFW_KEY_DOWN, true, (double elapsedTime) -> {
            if (areLevelsLoaded && !this.levelState.levels.isEmpty()) {
                currentSelection = (currentSelection + 1) % this.levelState.levels.size();
            }
        });
        // When Enter is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ENTER, true, (double elapsedTime) -> {
            if (areLevelsLoaded && !this.levelState.levels.isEmpty()) {
                selectedLevel = this.levelState.levels.get(currentSelection);
                // Pass the selected level to GameViewManager
                gameViewManager.setSelectedLevel(selectedLevel);
                nextGameState = GameStateEnum.GamePlay;
            }
        });
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
            // serializer.shutdown();
        });
    }

    @Override
    public void initializeSession() {
        inputKeyboard.setKeyPressed(GLFW_KEY_ENTER);
        inputKeyboard.setKeyPressed(GLFW_KEY_ESCAPE);
        nextGameState = GameStateEnum.NewGame;
        currentSelection = 0;  // Reset selection when returning to this view
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
        final float HEIGHT_MENU_ITEM = 0.075f;
        float top = -0.5f;

        if (areLevelsLoaded && !this.levelState.levels.isEmpty()) {
            // Render each level as a menu item
            for (int i = 0; i < this.levelState.levels.size(); i++) {
                Level level = this.levelState.levels.get(i);
                String displayName = level.getName();
                top = renderMenuItem(
                        currentSelection == i ? fontSelected : fontMenu,
                        displayName,
                        top,
                        HEIGHT_MENU_ITEM,
                        currentSelection == i ? Color.GREEN : Color.WHITE
                );
            }
        } else {
            // Show loading message if levels aren't loaded yet
            String loadingMessage = "Loading levels...";
            float width = fontMenu.measureTextWidth(loadingMessage, HEIGHT_MENU_ITEM);
            graphics.drawTextByHeight(fontMenu, loadingMessage, 0.0f - width / 2, top, HEIGHT_MENU_ITEM, Color.WHITE);
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