package views;

import core.GameStateEnum;
import core.KeyboardInput;
import edu.usu.graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class CreditsView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.Credits;
    private Font font;
    private Texture texTitle;

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        font = new Font("resources/fonts/ChakraPetch-Regular.ttf", 48, false);

        texTitle = new Texture("resources/images/big_blue_is_you.png");

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });
    }

    @Override
    public void initializeSession() {
        nextGameState = GameStateEnum.Credits;
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

        Color secondaryTextColor = new Color(1f,.52f,.72f);

        final float HEIGHT_CREDITS_LINE = 0.075f;
        float top = -0.2f;
        top = renderCreditsLine(font, "Based on 'Baba Is You' from Game Jam 2017", top, HEIGHT_CREDITS_LINE, Color.WHITE);
        top = renderCreditsLine(font, "Created by Layton Washburn and Ben Tomlinson", top, HEIGHT_CREDITS_LINE, secondaryTextColor);
        top = renderCreditsLine(font, "Using libraries by Dean Mathias and LWJGL", top, HEIGHT_CREDITS_LINE, Color.WHITE);
        top = renderCreditsLine(font, "Textures from Baba Is You", top, HEIGHT_CREDITS_LINE, secondaryTextColor);
        top = renderCreditsLine(font, "Music/Sounds from freesound.org", top, HEIGHT_CREDITS_LINE, Color.WHITE);

    }

    private float renderCreditsLine(Font font, String text, float top, float height, Color color) {
        float width = font.measureTextWidth(text, height);
        graphics.drawTextByHeight(font, text, 0.0f - width / 2, top, height, color);

        return top + height;
    }
}