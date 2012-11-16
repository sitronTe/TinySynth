/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * The <code>SoundChannelMix</code> interface represents a mixer for one or more
 * <code>SoundChannel</code>s.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>SoundChannelMix</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundChannelMix extends Mix {
	/**
	 * Attaches a <code>SoundChannel</code> to this mixer and splits its signal.
	 * Returns if the attach was successful.
	 * 
	 * @param channel
	 *            the channel to attach
	 * @return index assigned to this <code>SoundChannel</code>
	 */
	public int attach(SoundChannel channel);

	/**
	 * Removes the <code>SoundChannel</code> from this mixer. If this mixer is
	 * not attached to mentioned <code>SoundChannel</code> this method returns
	 * without doing anything.
	 * 
	 * @param channel
	 *            the <code>SoundChannel</code> to remove.
	 */
	public void remove(SoundChannel channel);

	/**
	 * Removes the <code>SoundChannel</code> indicated by the index. If this is
	 * an illegal value, the method returns without doing anything.
	 * 
	 * @param channel	index of <code>SoundChannel</code> to remove
	 */
	public void remove(int channel);

	/**
	 * Checks if this <code>SoundChannelMix</code> is connected to the
	 * <code>SoundChannel</code> in question.
	 * 
	 * @param channel
	 *            the <code>SoundChannel</code> to check for.
	 * @return whether the <code>SoundChannel</code> is connected.
	 */
	public boolean contains(SoundChannel channel);

}
