package views;

import core.GameStateEnum;
import core.IGameState;
import core.GameViewManager;
import edu.usu.graphics.Graphics2D;

public abstract class GameStateView implements IGameState {
    protected Graphics2D graphics;
    protected GameViewManager gameViewManager;

    @Override
    public void initialize(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void setGameViewManager(GameViewManager manager) {
        this.gameViewManager = manager;
    }

    @Override
    public void initializeSession() {};

    @Override
    public abstract GameStateEnum processInput(double elapsedTime) throws CloneNotSupportedException;

    @Override
    public abstract void update(double elapsedTime) throws CloneNotSupportedException;

    @Override
    public abstract void render(double elapsedTime);
}