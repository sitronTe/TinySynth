/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

/**
 * The <code>SoundSourceMix</code> interface represents a mixer for
 * <code>SoundChannel</code>s.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>SoundSourceMix</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundSourceMix extends Mix {
	/**
	 * Attaches a <code>SoundSource</code> to this mixer and returns positive
	 * value if the attach was successful.
	 * 
	 * @param source
	 *            the <code>SoundSource</code> to attach
	 * @return index assigned to this <code>SoundSource</code>
	 */
	public int attach(SoundSource source);

	/**
	 * Removes the <code>SoundSource</code> from this mixer. If this mixer is
	 * not attached to mentioned <code>SoundSource</code> this method returns
	 * without doing anything.
	 * 
	 * @param source
	 *            the <code>SoundSource</code> to remove.
	 */
	public void remove(SoundSource source);

	/**
	 * Removes the <code>SoundSource</code> indicated by the index. If this is
	 * an illegal value, the method returns without doing anything.
	 * 
	 * @param source
	 *            index of <code>SoundSource</code> to remove
	 */
	public void remove(int source);

	/**
	 * Checks if this <code>SoundSourceMix</code> is connected to the
	 * <code>SoundSource</code> in question.
	 * 
	 * @param source
	 *            the <code>SoundSource</code> to check for.
	 * @return whether the <code>SoundSource</code> is connected.
	 */
	public boolean contains(SoundSource source);

}
