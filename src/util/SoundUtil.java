package util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundUtil {
	//play the fire sound, after the player or the computer fire
    public static void playFireSound(){
        AudioInputStream astream = null;
        try {
            File afile = new File("src/assets/sounds/explosion.wav");
            astream = AudioSystem.getAudioInputStream(afile);
            Clip audio = AudioSystem.getClip();
            audio.open(astream);
            //audio.setFramePosition(0);
            audio.start();
        } catch (LineUnavailableException ex) {
        	ErrorLogger.log(ex.getStackTrace());
            Logger.getLogger(SoundUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
        	ErrorLogger.log(ex.getStackTrace());
            Logger.getLogger(SoundUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        	ErrorLogger.log(ex.getStackTrace());
            Logger.getLogger(SoundUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                astream.close();
            } catch (IOException ex) {
            	ErrorLogger.log(ex.getStackTrace());
                Logger.getLogger(SoundUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
