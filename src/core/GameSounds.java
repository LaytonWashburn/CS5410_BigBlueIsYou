package core;

import edu.usu.audio.Sound;
import edu.usu.audio.SoundManager;

public class GameSounds {

    public SoundManager gameSoundManager;
    public Sound levelWin;
    public Sound movement;
    public Sound winCondition;

    public GameSounds() {
        this.gameSoundManager = new SoundManager();
        this.levelWin = this.gameSoundManager.load("levelWin", "resources/sound/level_win.ogg", false);
        this.movement = this.gameSoundManager.load("movement", "resources/sound/movement.ogg", false);
        this.winCondition = this.gameSoundManager.load("winCondition", "resources/sound/win_condition_changed.ogg", false);
    }
}
