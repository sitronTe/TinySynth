/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * A single note for use in <code>TinySynth</code> environment. Contains
 * information about frequency, volume and length. Length and/or volume
 * information may be omitted, or just not evaluated by the user of the
 * <code>Note</code>.
 * 
 * @author Sitron Te
 * 
 */
public interface Note {
	/**
	 * Gets the frequency this <code>Note</code> is representing in
	 * <code>double</code> precision. Will always return a positive value
	 * between 10 and 22 050.
	 * 
	 * @return the frequency represented.
	 */
	public double getFrequency();

	/**
	 * Gets the number of samples this <code>Note</code> will have in one
	 * revolution in a 44 100 samples per second environment. By one revolution
	 * it is meant the number of samples contained within one cycle of the note.
	 * For example, if <code>Note n.getFrequency()</code> returns 440, this
	 * method will return 100.
	 * 
	 * @return the number of samples to complete one cycle of this
	 *         <code>Note</code>.
	 */
	public int getSampleCount44100Hz();

	/**
	 * Gets the volume if set in range 0 to <code>Short.MAX_VALUE</code>, else
	 * -1.
	 * 
	 * @return the volume if set in range 0 to <code>Short.MAX_VALUE</code>,
	 *         else -1.
	 */
	public int getVolume();

	/**
	 * Set the volume in range 0 to <code>Short.MAX_VALUE</code>. Will remove
	 * any volume if set to -1.
	 * 
	 * @param volume
	 *            the new volume. Valid range -1 to <code>Short.MAX_VALUE</code>
	 * @throws IllegalArgumentException
	 *             if <code>volume</code> is not invalid range.
	 */
	public void setVolume(int volume);

	/**
	 * Gets the length of this <code>Note</code> in milliseconds, if set. If not
	 * set, this method will return -1.
	 * 
	 * @return the length of this <code>Note</code> in milliseconds. -1 if not
	 *         set.
	 */
	public int getLength();

	/**
	 * Gets the length of this <code>Note</code> in number of samples according
	 * to a 44 100 frames per second environment. WARNING! This method will
	 * return an integer such that
	 * <code>Note.getLengthSampleCount44100Hz % Note.getSampleCount44100Hz</code>
	 * will equal 0! It will choose the closest integer that satisfies this
	 * requirement, that is length may be rounded both up and down. Worst case
	 * scenario is when <code>Note.getFrequency()</code> returns 10. You then
	 * may get a length that differs with 0,05 seconds from intended length!
	 * 
	 * @return length of this <code>Note</code> in frames in a 44 100 Hz
	 *         environment.
	 */
	public int getLengthSampleCount44100Hz();

	/**
	 * Sets the length of this <code>Note</code> measured in milliseconds.
	 * Negative value will remove length information.
	 * 
	 * @param length
	 *            the new length in milliseconds
	 */
	public void setLength(int length);

}
