/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package tools;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * A <code>SimplePlayer</code> is a simple player for use in the
 * <code>TinySynth</code> environment. It spawns a new thread which from sound
 * is played.
 * 
 * @author Sitron Te
 * 
 */
public class SimplePlayer implements Player {
	private Thread player = new Thread(new PlayerThread());
	private Converter source = null;
	private boolean play = false, stop = false, kill = false, playing = false;
	private final int bSize;

	/**
	 * Creates a new <code>SimplePlayer</code> with buffer size of 44 100
	 * frames.
	 */
	public SimplePlayer() {
		bSize = 44100;
	}

	/**
	 * Creates a new <code>SimplePlayer</code> with the buffer size brought
	 * along.
	 * 
	 * @param bufferSize
	 *            number of frames to buffer sound. Must be over 0
	 * @throws IllegalArgumentException
	 *             if <code>bufferSize</code> is 0 or less
	 */
	public SimplePlayer(int bufferSize) {
		if (bufferSize < 1)
			throw new IllegalArgumentException("Buffer must have positive size");
		bSize = bufferSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#isAlive()
	 */
	@Override
	public boolean isAlive() {
		return player.isAlive() && playing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#isSourceChangeReady()
	 */
	@Override
	public synchronized boolean isSourceChangeReady() {
		return source == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#setSource(tools.Converter)
	 */
	@Override
	public synchronized void setSource(Converter source) {
		if (!isSourceChangeReady())
			throw new IllegalStateException("Could not change sound source");
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#play()
	 */
	@Override
	public synchronized void play() {
		if (source == null)
			return;
		if (player.isAlive()) {
			play = true;
			player.interrupt();
		} else
			player.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#stop()
	 */
	@Override
	public synchronized void stop() {
		if (!player.isAlive())
			return;
		stop = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.Player#kill()
	 */
	@Override
	public synchronized void kill() {
		if (!player.isAlive()) {
			source = null;
			return;
		}
		kill = true;
		player.interrupt();
	}

	private synchronized void resetPlay() {
		play = false;
	}

	private synchronized void resetStop() {
		stop = false;
	}

	private synchronized void resetKill() {
		kill = false;
	}

	private class PlayerThread implements Runnable {
		@Override
		public void run() {
			AudioFormat aFormat = source.getAudioFormat();
			SourceDataLine line = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					aFormat);
			if (!AudioSystem.isLineSupported(info)) {
				throw new UnsupportedOperationException(
						"This computer does not support this sound format!");
			} else {
				try {
					line = (SourceDataLine) AudioSystem.getLine(info);
					line.open(
							aFormat,
							aFormat.getChannels() * bSize
									* (aFormat.getSampleSizeInBits() / 8));
					line.start();
					playing = true;
					while (source.hasNext()) {
						byte[] sound = source.next(100);
						line.write(sound, 0, sound.length);
						if (stop) {
							line.stop();
							playing = false;
							while (!play)
								try {
									Thread.sleep(Long.MAX_VALUE);
								} catch (InterruptedException e) {
									if (kill)
										break;
								}
							resetStop();
						}
						if (play) {
							resetPlay();
							line.start();
							playing = true;
						}
						if (kill) {
							line.stop();
							line.close();
							line = null;
							break;
						}
					}
				} catch (LineUnavailableException ex) {
					System.err
							.println("Something took all available output lines!");
				}
				if (line != null) {
					line.drain();
					line.stop();
					line.close();
					line = null;
				}
				playing = false;
				resetStop();
				resetPlay();
				resetKill();
				synchronized (source) {
					source = null;
				}
			}

		}
	}

}
