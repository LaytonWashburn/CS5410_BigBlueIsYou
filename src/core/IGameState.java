package core;

import edu.usu.graphics.Graphics2D;

public interface IGameState {
    void initialize(Graphics2D graphics);

    void initializeSession();

    GameStateEnum processInput(double elapsedTime) throws CloneNotSupportedException;

    void update(double elapsedTime) throws CloneNotSupportedException;

    void render(double elapsedTime);
}