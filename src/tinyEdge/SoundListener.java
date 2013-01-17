/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tinyEdge;


/**
 * A <code>SoundListener</code> is a listener for {@link SoundEvent}s.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundListener {
	/**
	 * Fires a <code>SoundEvent</code>. Look to the documentations of the
	 * specific <code>SoundListener</code> for detailed information of what
	 * happens when it is fired.
	 * 
	 * @param s
	 *            the <code>SoundEvent</code> that happens.
	 */
	public void fireSound(SoundEvent s);
}
