package level;

import java.util.ArrayList;

public class LevelState {
    public boolean initialized;
    public boolean errorOccurred;

    public ArrayList<Level> levels;

    public LevelState() {
        this.levels = new ArrayList<>();

        this.initialized = false;
    }

    public LevelState(ArrayList<Level> levels) {
        this.levels = levels;

        this.initialized = true;
    }

    public void add(Level level) {
        levels.add(level);

        this.initialized = true;
    }
}
