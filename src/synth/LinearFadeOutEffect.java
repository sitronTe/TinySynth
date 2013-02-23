/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

/**
 * @author Sitron Te
 * 
 */
public class LinearFadeOutEffect implements SoundEffect, Cloneable {
	private final int channelCount, startFrame, length;
	private int pos = 0, frame = 0;

	/**
	 * Creates a new <code>FadeOutEffect</code> with the same properties as the
	 * original had when newly created.
	 * 
	 * @param original
	 *            the effect to mimic
	 */
	public LinearFadeOutEffect(LinearFadeOutEffect original) {
		this.channelCount = original.channelCount;
		this.startFrame = original.startFrame;
		this.length = original.length;
	}

	/**
	 * Creates a new <code>FadeOutEffect</code> with linear volume decrease
	 * speed.
	 * 
	 * @param channelCount
	 *            number of channels this <code>FadeOutEffect</code> has
	 * @param startframe
	 *            the frame where fade out starts
	 * @param length
	 *            how long until sound is completely faded
	 * @throws IllegalArgumenException
	 *             if startFrame is negative or channel count is 0 or less or if
	 *             length is 0 or less
	 */
	public LinearFadeOutEffect(int channelCount, int startframe, int length) {
		if (channelCount <= 0)
			throw new IllegalArgumentException(
					"Must have minimum one sound channel!");
		if (startframe < 0)
			throw new IllegalArgumentException(
					"Fade out must start after start!");
		if (length <= 0)
			throw new IllegalArgumentException("Fade out must have length!");
		this.channelCount = channelCount;
		this.startFrame = startframe;
		this.length = length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#performEffect(short[])
	 */
	@Override
	public void performEffect(short[] sound) {
		// TODO Look at documentation for ways to simplify.
		int end = startFrame + length;
		for (int i = 0; i < sound.length; i++) {
			if (frame >= startFrame) {
				int s = sound[i] * (end - frame);
				s /= length;
				sound[i] = (short) s;
			}
			pos++;
			if (pos == channelCount) {
				frame = frame == end ? end : ++frame;
				pos = 0;
			}
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
		pos = 0;
		frame = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#clone()
	 */
	@Override
	public SoundEffect clone() {
		return new LinearFadeOutEffect(this);
	}

	private class MyDelegate extends DefaultPersistenceDelegate {
		protected Expression instantiate(Object oldInstance, Encoder out) {
			if (oldInstance.getClass() != LinearFadeOutEffect.class)
				return null;
			Object[] o = new Object[] {
					((LinearFadeOutEffect) oldInstance).channelCount,
					((LinearFadeOutEffect) oldInstance).startFrame,
					((LinearFadeOutEffect) oldInstance).length };
			return new Expression(oldInstance, oldInstance.getClass(), "new", o);
		}
	}

	@Override
	public void registerPersistenceDelegate(XMLEncoder encoder) {
		encoder.setPersistenceDelegate(this.getClass(), new MyDelegate());
	}
}
