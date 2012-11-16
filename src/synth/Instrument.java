/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * The <code>Instrument</code> interface represents a single mono instrument.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>Instrument</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface Instrument extends SoundChannel {
	/**
	 * Sets the next note this instruments shall play. After this is called
	 * <code>next()</code> and <code>next(int frames)</code> will return data
	 * for this note. It will take heed to length and volume information in this {@link Note}.
	 * 
	 * @param note
	 *            the <code>Note</code> to be played.
	 * @throws NullPointerException
	 *             if <code>note</code> is set to <code>null</code>
	 */
	public void play(Note note);

	/**
	 * Creates a shallow, uninitialized clone of this instrument. The clone will
	 * not have set current note to be played.
	 * 
	 * @return an uninitialized clone of this instrument.
	 */
	public Instrument clone();
}
