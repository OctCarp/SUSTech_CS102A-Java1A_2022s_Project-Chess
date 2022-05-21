package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlay {
    private static Clip bgm;//背景乐
    private static Clip hit;//音效
    private static AudioInputStream ais;

    public AudioPlay() {
    }

    public static void playBgm() {
        try {
            bgm = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("./resources/audios/boardBGM.wav"));
            bgm.open(audioInput);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void playHit() {
        try {
            hit = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("./resources/audios/chessHit.wav"));
            hit.open(audioInput);
            hit.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void stopBgm () {
        if (ais != null)
            bgm.close();
    }
}
