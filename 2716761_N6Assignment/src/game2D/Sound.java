package game2D;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Sounds but with the added distance variable, uses the DistanceFilterStream
 * and then plays a sound
 * 
 * @author 2716761
 *
 */
public class Sound extends Thread {

	String filename; // The name of the file to play
	boolean finished; // A flag showing that the thread has finished
	private double distance = 0; // 0 by default

	public Sound(String fname) {
		filename = fname;
		finished = false;
	}

	/**
	 * Set the distance between the sound "location" and the player
	 * 
	 * @param dist the distance to set
	 */
	public void setDistance(double dist) {
		distance = dist;
	}

	/**
	 * run will play the actual sound but you should not call it directly. You need
	 * to call the 'start' method of your sound object (inherited from Thread, you
	 * do not need to declare your own). 'run' will eventually be called by 'start'
	 * when it has been scheduled by the process scheduler.
	 */
	public void run() {
		try {
			File file = new File(filename);
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = stream.getFormat();
			DistanceFilterStream filtered = new DistanceFilterStream(stream, distance);
			AudioInputStream f = new AudioInputStream(filtered, format, stream.getFrameLength());
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(f);
			clip.start();
			Thread.sleep(100);
			while (clip.isRunning()) {
				Thread.sleep(100);
			}
			clip.close();
		} catch (Exception e) {
		}
		finished = true;
	}
}
