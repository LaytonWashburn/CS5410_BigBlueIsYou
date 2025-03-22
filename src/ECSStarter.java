import core.GameViewManager;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;

public class ECSStarter {
    public static void main(String[] args) {
        try (Graphics2D graphics = new Graphics2D(1920, 1080, "Starter Project")) {
            graphics.initialize(Color.CORNFLOWER_BLUE);
            GameViewManager game = new GameViewManager(graphics);
            game.initialize();
            game.run();
            game.shutdown();
        }
    }
}