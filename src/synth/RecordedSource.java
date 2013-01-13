/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.util.NoSuchElementException;

/**
 * This takes a {@link SoundSource} and records the number of frames indicated,
 * or so many frames the <code>SoundSource</code> can deliver. Will use up the
 * <code>SoundSource</code>.
 * 
 * @author Sitron Te
 * 
 */
public class RecordedSource extends AbstractSoundSource implements SoundSource,
		Cloneable {
	private final short[] mySound;
	private final int channelCount, totFrames;
	private int frame = 0;

	/**
	 * Creates a new <code>RecordedSound</code> and records the number of frames
	 * indicated, or if the <code>SoundSource</code> has fewer frames, it will
	 * record the frames the <code>SoundSource</code> has available.
	 * 
	 * @param source
	 *            the source to copy the sound from
	 * @param recFrames
	 *            number of frames to record
	 * @throws IllegalArgumentException
	 *             if recFrames is 0 or less, or if the <code>SoundSource</code>
	 *             does not have any frames to deliver
	 */
	public RecordedSource(SoundSource source, int recFrames) {
		if (recFrames < 1)
			throw new IllegalArgumentException("Must try to record some frames");
		if (!source.hasNext())
			throw new IllegalArgumentException(
					"Sound source must have frames to record!");
		mySound = source.next(recFrames);
		channelCount = source.getChannelCount();
		totFrames = mySound.length / channelCount;
	}

	/**
	 * Creates a fresh copy of this <code>RecordedSource</code> that plays from
	 * the first frame.
	 * 
	 * @param original
	 *            the <code>RecordedSource</code> to copy information from
	 */
	public RecordedSource(RecordedSource original) {
		this.mySound = original.mySound;
		this.channelCount = original.channelCount;
		this.totFrames = original.totFrames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return frame < totFrames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#next()
	 */
	@Override
	public short[] next() {
		if (!hasNext())
			throw new NoSuchElementException(
					"This RecordedSource have no more frames to deliver!");
		short[] ret = new short[channelCount];
		int t = channelCount * frame;
		for (int i = 0; i < channelCount; i++)
			ret[i] = mySound[t + i];
		frame++;
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#getChannelCount()
	 */
	@Override
	public int getChannelCount() {
		return channelCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#reset()
	 */
	@Override
	public void reset() {
		frame = 0;
	}

	/**
	 * Returns a new <code>RecordedSource</code>that starts playing from first
	 * frame.
	 * 
	 * @see #RecordedSource(RecordedSource)
	 */
	@Override
	public RecordedSource clone() {
		return new RecordedSource(this);
	}

}
