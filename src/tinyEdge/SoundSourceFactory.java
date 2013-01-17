/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tinyEdge;

import synth.SoundSource;

/**
 * A <code>SoundSourceFactory</code> is a provider of new instances of
 * {@link SoundSource}s. Look to implementations for further specifications.
 * 
 * @author Sitron Te
 * 
 */
public interface SoundSourceFactory {
	/**
	 * Generates a new instance of a <code>SoundSource</code> bound to this
	 * <code>SoundSourceFactory</code>.
	 * 
	 * @return the newly created <code>SoundSource</code>, <code>null</code> if
	 *         this factory could not create a instance with the information
	 *         given.
	 */
	public SoundSource getSoundSourceInstance();

	/**
	 * Optional operation, gets a new instance of the <code>SoundSource</code>
	 * bound to this <code>SoundSourceFactory</code>.
	 * 
	 * @param description
	 *            string that describes the <code>SoundSource</code> asked for
	 * @return the newly created <code>SoundSource</code> based on the
	 *         description given
	 * @throws UnsupportedMethodException
	 *             if this <code>SoundSourceFactory</code> does not support this
	 *             functionality
	 */
	public SoundSource getSoundSourceInstance(String description);
}
