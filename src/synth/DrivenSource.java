/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

import tools.SoundListener;

/**
 * The <code>DrivenSource</code> is intended to be a thread safe
 * {@link SoundSource} for playing sound events.
 * 
 * @author Sitron Te
 * 
 */
public interface DrivenSource extends SoundSource, SoundListener {
	/**
	 * A <code>DrivenSource</code> is a <code>SoundSource</code> which listens
	 * to {@link SoundEvent}s, so by default it should return that this
	 * <code>DrivenSource</code> has more frames to deliver. However this
	 * behavior is not guaranteed, so look to the documentation of the
	 * implementation.
	 */
	@Override
	public boolean hasNext();
}
