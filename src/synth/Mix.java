/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * The <code>Mix</code> interface is a general interface to make all mixers
 * consistent.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * 
 * @author Sitron Te
 * 
 */
public interface Mix extends SoundSource {
	/**
	 * Checks if this mixer has more space left, or if it is full.
	 * 
	 * @return if this mixer is full
	 */
	public boolean isFull();

	/**
	 * Gets the master volume, values between <code>0</code> to
	 * <code>Short.MAX_VALUE</code>.
	 * 
	 * @return volume
	 */
	public int getMasterVolume();

	/**
	 * Sets the master volume for all output channels, values must be in range
	 * of 0 to <code>Short.MAX_VALUE</code>.
	 * 
	 * @param volume
	 *            volume to set. Values must be in range 0 to
	 *            <code>Short.MAX_VALUE</code>
	 * @throws IllegalArgumentException
	 *             if volume is in illegal range
	 */
	public void setMasterVolume(int volume);

	/**
	 * Gets the volume for the input channel and output channel in question. If
	 * all output channels are in question, the highest value will be returned.
	 * 
	 * @param inputChannel
	 *            number of the input channel to get from. Negative value will
	 *            get from the master output.
	 * @param outputChannel
	 *            the output channel to get volume for. Negative value will get
	 *            the highest value of the output channels.
	 * @return volume value of volume in range 0 to <code>Short.MAX_VALUE</code>
	 * @throws IndexOutOfBoundsException
	 *             if either input or output channels are too high
	 */
	public int getChannelVolume(int inputChannel, int outputChannel);

	/**
	 * Sets the volume for the input and output channels in question, use this
	 * if you wish to change how much sound to each output channel for master
	 * channel.
	 * 
	 * @param inputChannel
	 *            the input channel to set volume for. Negative value means the
	 *            master channel
	 * @param outputChannel
	 *            the output channel to set volume for. Negative value means all
	 *            output channels
	 * @param volume
	 *            the new volume level in range 0 to <code>Short.MAX_VALUE</code>
	 * @throws IllegalArgumentException
	 *             if volume is in illegal range
	 * @throws IndexOutOfBoundsException
	 *             if either input or output channel are too high
	 */
	public void setChannelVolume(int inputChannel, int outputChannel, int volume);

	/**
	 * Attaches a sound effect for a channel before volume mix.
	 * 
	 * @param effect
	 *            effect to change
	 * @param channel
	 *            the channel to attach it to. Negative value will attach it to
	 *            master channel
	 * @return the index of this effect relative to this channel
	 * @throws IndexOutOfBoundsException
	 *             if input channel is too high
	 */
	public int attachSoundEffect(SoundEffect effect, int channel);

	/**
	 * Removes this <code>SoundEffect</code> from the channel in question.
	 * Negative value of channel will count it as the master output. If no such
	 * effect is attached, this method will return without doing anything.
	 * 
	 * @param effect
	 *            effect to remove
	 * @param channel
	 *            channel to remove from. Negative value indicates the master
	 *            output
	 * @throws IndexOutOfBoundsException
	 *             if channel is too high
	 */
	public void removeSoundEffect(SoundEffect effect, int channel);

	/**
	 * Removes the <code>SoundEffect</code> indicated by index and channel. If
	 * index does not refer to an effect, this method returns without doing
	 * anything. Negative value on channel indicates master output.
	 * 
	 * @param effectNumber
	 *            index of effect
	 * @param channel
	 *            channel to remove from
	 * @throws IndexOutOfBoundsException
	 *             if channel is too high
	 */
	public void removeSoundEffect(int effectNumber, int channel);

}
