package core;

import edu.usu.audio.Sound;
import edu.usu.audio.SoundManager;

public class GameSounds {

    public Sound levelWin;
    public Sound movement;
    public Sound winCondition;

    public GameSounds(SoundManager soundManager) {
        this.levelWin = soundManager.load("levelWin", "resources/sound/level_win.ogg", false);
        this.movement = soundManager.load("movement", "resources/sound/movement.ogg", false);
        this.winCondition = soundManager.load("winCondition", "resources/sound/win_condition_changed.ogg", false);
    }
}
