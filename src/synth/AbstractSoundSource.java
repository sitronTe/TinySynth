/**
 * 
 */
package synth;

import java.util.NoSuchElementException;

/**
 * Only made to make implementation faster to write. Has no optimization.
 * 
 * @author Sitron Te
 * 
 */
public abstract class AbstractSoundSource implements SoundSource {

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSource#hasNext()
	 */
	@Override
	public abstract boolean hasNext();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSource#next()
	 */
	@Override
	public abstract short[] next();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSource#next(int)
	 */
	@Override
	public short[] next(int frames) {
		if (!hasNext())
			throw new NoSuchElementException(
					"This SoundSource have no more sound!");
		if (frames <= 0)
			throw new IllegalArgumentException(
					"Must ask for more than 0 frames, but was asked for "
							+ frames);
		int chCount = getChannelCount();
		short[] ret = new short[frames * chCount];
		short[] next = null;
		for (int i = 0; i < ret.length; i++) {
			if (i % chCount == 0) {
				if (!hasNext()) {
					short[] r = new short[i];
					for (int j = 0; j < i; j++)
						r[j] = ret[j];
					return r;
				}
				next = next();
			}
			ret[i] = next[i % chCount];
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSource#getChannelCount()
	 */
	@Override
	public abstract int getChannelCount();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSource#reset()
	 */
	@Override
	public abstract void reset();

}
