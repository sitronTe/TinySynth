/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * The <code>SoundChannel</code> interface represents a single audio channel.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>SoundChannel</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundChannel extends StoreableSynthPart {
	/**
	 * Checks if this <code>SoundChannel</code> has any more audio. Returns
	 * <code>false</code> if this is not properly initialized or it has finished
	 * playing, otherwise it is <code>true</code>.
	 * 
	 * @return whether this <code>SoundChannel</code> has more content
	 */
	public boolean hasNext();

	/**
	 * Gets the next audio frame and returns it. Changes this object for both
	 * <code>next()</code> and <code>next(int frames)</code>.
	 * 
	 * @return the next audio frame
	 * @throws NoSuchElementException
	 *             if <code>hasNext()</code> returns false
	 */
	public short next();

	/**
	 * Gets an array with the audio frames requested. If length of this
	 * <code>SoundChannel</code> is shorter than frames requested, only the
	 * frames contained within this <code>SoundChannel</code> is returned.
	 * Changes this object for both <code>next()</code> and
	 * <code>next(int frames)</code>.
	 * 
	 * @param frames
	 *            the number of frames requested. Must be 1 or more.
	 * @return an array with audio frames in chronological order.
	 * @throws NoSuchElementException
	 *             if <code>hasNext()</code> returns <code>false</code>
	 * @throws IllegalArgumentException
	 *             if <code>frames</code> is equal or less than 0.
	 */
	public short[] next(int frames);

}
