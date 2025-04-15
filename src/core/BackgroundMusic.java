package core;

import edu.usu.audio.Sound;
import edu.usu.audio.SoundManager;

/**
 * Class: Background Music
 * Description: PLays background music during the game play
 */
public class BackgroundMusic {

    SoundManager soundManager;
    Sound backgroundMusic;


    public BackgroundMusic() {
        soundManager = new SoundManager();
        backgroundMusic = this.soundManager.load("background",
                                                "resources/sound/baba_is_you_puzzle_theme.ogg",
                                                    true);
    }


    public void play() {
        this.backgroundMusic.play();
    }

    public void stop() {
        this.backgroundMusic.stop();
    }
}
