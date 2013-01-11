/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tools;

import synth.SoundSource;

/**
 * A factory for cloneable {@link SoundSource}s.
 * 
 * @author Sitron Te
 * 
 */
public class CloneableSourceFactory implements SoundSourceFactory {
	private final SoundSource mySource;

	/**
	 * Creates a new <code>CloneableSourceFactory</code> which will attempt to
	 * clone the <code>SoundSource</code> brought along.
	 * 
	 * @param s
	 *            the source to clone and deal out later. Must implement the
	 *            <code>Cloneable</code> interface
	 * @throws IllegalArgumentException
	 *             if the <code>SoundSource</code> does not implement
	 *             <code>Cloneable</code>
	 */
	public CloneableSourceFactory(SoundSource s) {
		if (s instanceof Cloneable)
			try {
				mySource = (SoundSource) s.getClass().getMethod("clone")
						.invoke(s);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"SoundSource not cloneable!", e);
			}
		else
			throw new IllegalArgumentException("SoundSource not cloneable!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.SoundSourceFactory#getSoundSourceInstance()
	 */
	@Override
	public synchronized SoundSource getSoundSourceInstance() {
		SoundSource copy = null;
		try {
			copy = (SoundSource) mySource.getClass().getMethod("clone")
					.invoke(mySource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return copy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tools.SoundSourceFactory#getSoundSourceInstance(java.lang.String)
	 */
	@Override
	public SoundSource getSoundSourceInstance(String description) {
		throw new UnsupportedOperationException(
				"This factory does not support this method!");
	}

}
