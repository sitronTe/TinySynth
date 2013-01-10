/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * @author Sitron Te
 * 
 */
public class FadeInEffect implements SoundEffect, Cloneable {
	private final int endFrame, channelCount;
	private int frame = 0, pos = 0;

	/**
	 * Creates a new <code>FadeInEffect</code> with the same properties as the
	 * original had when newly created.
	 * 
	 * @param original
	 *            the effect to mimic
	 */
	public FadeInEffect(FadeInEffect original) {
		endFrame = original.endFrame;
		channelCount = original.channelCount;
	}

	/**
	 * Creates a new <code>FadeInEffect</code> with linear volume increase
	 * speed.
	 * 
	 * @param channelCount
	 *            number of channels this <code>FadeInEffect</code> has
	 * @param toFrame
	 *            the frame where sound is maximum
	 * @throws IllegalArgumenException
	 *             if toFrame is negative or channel count is 0 or less
	 */
	public FadeInEffect(int channelCount, int toFrame) {
		if (toFrame < 0)
			throw new IllegalArgumentException("Must have length");
		if (channelCount <= 0)
			throw new IllegalArgumentException(
					"Must have minimum one sound channel!");
		this.channelCount = channelCount;
		endFrame = toFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#performEffect(short[])
	 */
	@Override
	public void performEffect(short[] sound) {
		if (frame >= endFrame)
			return;
		for (int i = 0; i < sound.length; i++) {
			int s = sound[i] * frame;
			s /= endFrame;
			pos++;
			if (pos == channelCount) {
				pos = 0;
				frame++;
			}
			sound[i] = (short) s;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#getChannelCount()
	 */
	@Override
	public int getChannelCount() {
		return channelCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#reset()
	 */
	@Override
	public void reset() {
		frame = 0;
		pos = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#clone()
	 */
	@Override
	public SoundEffect clone() {
		return new FadeInEffect(this);
	}

}
