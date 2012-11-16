/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

/**
 * The <code>SoundEffect</code> represents a general sound effect.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>SoundEffect</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundEffect {
	/**
	 * Alters the brought along array <code>sound</code> according to what this
	 * <code>SoundEffect</code> is initialized to do. The array
	 * <code>sound</code> must be a complete number of frames, that is
	 * <code>sound.length % getChannelCount() == 0</code> must be
	 * <code>true</code>.
	 * 
	 * @param sound
	 *            the sound to alter.
	 * @throws IllegalArgumentException
	 *             if <code>sound.length % getChannelCount() == 0</code>
	 *             evaluates to <code>false</code>
	 */
	public void performEffect(short[] sound);

	/**
	 * Gets the number of audio channels this <code>SoundEffect</code> is
	 * initialized with.
	 * 
	 * @return number of audio channels
	 */
	public int getChannelCount();

	/**
	 * Resets this <code>SoundEffect</code> to a newly initialized state. That
	 * is, it flushes all its memory and starts counting frames again. It does
	 * not alter any specializations this <code>SoundEffect</code> has when
	 * newly created.
	 */
	public void reset();

	/**
	 * Creates a fresh clone of this <code>SoundEffect</code>. That is it
	 * returns a <code>SoundEffect</code> that is newly initialized.
	 * 
	 * @return a new <code>SoundEffect</code> with the same properties as this
	 */
	public SoundEffect clone();

}
