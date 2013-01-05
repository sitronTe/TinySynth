/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package tools;

/**
 * A <code>Player</code> is an automated sound player within the
 * <code>TinySynth</code> environment. It takes its data from a
 * {@link Converter} provided, and sends sound out of your speakers. It is meant
 * to be thread safe, and make your life easier. It should spawn a new thread to
 * work with the sound, be warned however that the sources for sound you provide
 * may NOT be thread safe (unless otherwise specified), so please take this into
 * account when writing code.
 * 
 * @author Sitron Te
 * 
 */
public interface Player {
	/**
	 * Checks if this <code>Player</code> is currently playing sound.
	 * 
	 * @return <code>true</code> if this player plays sound. <code>false</code>
	 *         otherwise
	 */
	public boolean isAlive();

	/**
	 * Checks if it is safe to set new sound source.
	 * 
	 * @return <code>true</code> if it is safe to run <code>setSource</code>.
	 *         <code>false</code> otherwise
	 */
	public boolean isSourceChangeReady();

	/**
	 * Sets a new source for sound into this player. Does not cause this player
	 * to start playing. Player must be ready to accept new source.
	 * 
	 * @param source
	 *            the source of sound
	 * @throws IllegalStateException
	 *             if <code>isSourceChangeReady()</code> returns false
	 */
	public void setSource(Converter source);

	/**
	 * Starts playing sound from the <code>Converter</code> attached to this
	 * <code>Player</code>. If this <code>Player</code> is already playing or no
	 * <code>Converter</code> is attached, this method returns without doing
	 * anything.
	 */
	public void play();

	/**
	 * Stops playing sound from the <code>Converter</code> attached to this
	 * <code>Player</code>. Will cause <code>isAlive()</code> to return
	 * <code>false</code>. If this <code>Player</code> currently is not playing,
	 * this method will return without doing anything.
	 */
	public void stop();

	/**
	 * Stops this <code>Player</code> from playing, and releases all resources
	 * it uses. Does so no <code>Converter</code> is attached to this
	 * <code>Player</code>. May be possible to resurrect by running the
	 * <code>setSource</code> and <code>play</code> commands.
	 */
	public void kill();
}
