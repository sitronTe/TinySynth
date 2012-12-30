/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.util.ArrayList;

/**
 * Written to make implementation of single-threaded mixers simpler. Has no
 * optimizations.
 * 
 * @author Sitron Te
 * 
 */
public abstract class AbstractMix extends AbstractSoundSource implements Mix {
	private final int[] mVolume;
	private final int channelCount;
	private ArrayList<int[]> channelVolumes = new ArrayList<int[]>();
	private ArrayList<SoundEffect> mEffects = new ArrayList<SoundEffect>();
	private ArrayList<ArrayList<SoundEffect>> chEffects = new ArrayList<ArrayList<SoundEffect>>();

	/**
	 * Creates a new <code>AbstractMix</code> with the number of output channels
	 * brought along.
	 * 
	 * @param channelCount
	 *            number of output channels.
	 */
	public AbstractMix(int channelCount) {
		this.channelCount = channelCount;
		mVolume = new int[channelCount];
		for (int i = 0; i < channelCount; i++)
			mVolume[i] = Short.MAX_VALUE;
	}

	/**
	 * Extends this <code>AbstractMix</code> so that new channels can be
	 * manipulated. Must be called when new channels are made available for
	 * attach. There are no way to remove or alter these channels (except those
	 * methods explained in documentation). Volumes for new channels are
	 * defaulted to 0.
	 * 
	 * @return current number of channels this mixer holds
	 */
	protected int extend() {
		channelVolumes.add(new int[channelCount]);
		chEffects.add(new ArrayList<SoundEffect>());
		if (channelVolumes.size() != chEffects.size())
			throw new IllegalStateException("Incoherency detected in self!");
		return channelVolumes.size();
	}

	/**
	 * Performs the <code>SoundEffects</code> attached to this input channel.
	 * Meant to be performed before anything else is done to signal from source.
	 * <code>SoundEffects</code> to be performed is initialized to same number
	 * of output channels as input channel has.
	 * 
	 * @param inputChannel
	 *            the channel to perform <code>SoundEffects</code> on
	 * @param sound
	 *            the sound to perform effects on
	 */
	protected void performEffects(int inputChannel, short[] sound) {
		for (SoundEffect e : chEffects.get(inputChannel))
			e.performEffect(sound);
	}

	/**
	 * Mixes volume for the input channel here in question. Will fail if content
	 * of sound does not correspond to an integer number of frames. Output
	 * channel count of sound should be corrected to this mixers output channel
	 * count before this method is invoked.
	 * 
	 * @param inputChannel
	 *            the channel to mix volume for
	 * @param sound
	 *            the sound to mix volume on
	 */
	protected void performVolumeMix(int inputChannel, short[] sound) {
		int i = 0;
		int[] vols = channelVolumes.get(inputChannel);
		while (i < sound.length) {
			for (int v : vols) {
				int s = sound[i] * v;
				s /= Short.MAX_VALUE;
				sound[i] = (short) s;
				i++;
			}
		}
	}

	/**
	 * Performs master effects and volume mix. Will fail if content of sound
	 * does not correspond to an integer number of frames.
	 * 
	 * @param sound
	 *            sound before master effect and volume mix
	 */
	protected void performMasterEffectAndVolumeMix(short[] sound) {
		for (SoundEffect e : mEffects)
			e.performEffect(sound);
		int i = 0;
		while (i < sound.length) {
			for (int v : mVolume) {
				int s = sound[i] * v;
				s /= Short.MAX_VALUE;
				sound[i] = (short) s;
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#isFull()
	 */
	@Override
	public abstract boolean isFull();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#getMasterVolume()
	 */
	@Override
	public int getMasterVolume() {
		int mVol = 0;
		for (int v : mVolume)
			mVol += v;
		return mVol / mVolume.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#setMasterVolume(int)
	 */
	@Override
	public void setMasterVolume(int volume) {
		isVolumeLegal(volume);
		for (int i = 0; i < mVolume.length; i++) {
			mVolume[i] = volume;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#getChannelVolume(int, int)
	 */
	@Override
	public int getChannelVolume(int inputChannel, int outputChannel) {
		if (inputChannel < 0 || inputChannel >= channelVolumes.size())
			throw new IndexOutOfBoundsException(
					"Input channel must be in legal range!");
		if (outputChannel >= channelCount)
			throw new IndexOutOfBoundsException(
					"Output channel must be in legal range!");
		if (outputChannel < 0) {
			int[] v = channelVolumes.get(inputChannel);
			int max = v[0];
			for (int i = 1; i < channelCount; i++)
				max = v[i] > max ? v[i] : max;
			return max;
		} else {
			return channelVolumes.get(inputChannel)[outputChannel];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#setChannelVolume(int, int, int)
	 */
	@Override
	public void setChannelVolume(int inputChannel, int outputChannel, int volume) {
		isVolumeLegal(volume);
		if (inputChannel >= channelVolumes.size())
			throw new IndexOutOfBoundsException(
					"Input channel must be in legal range!");
		if (outputChannel >= channelCount)
			throw new IndexOutOfBoundsException(
					"Output channel must be in legal range!");
		if (inputChannel < 0) {
			if (outputChannel < 0)
				setMasterVolume(volume);
			else
				mVolume[outputChannel] = volume;
			return;
		}
		int[] chVol = channelVolumes.get(inputChannel);
		if (outputChannel < 0)
			for (int i = 0; i < channelCount; i++)
				chVol[i] = volume;
		else
			chVol[outputChannel] = volume;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#attachSoundEffect(synth.SoundEffect, int)
	 */
	@Override
	public int attachSoundEffect(SoundEffect effect, int channel) {
		if (channel > chEffects.size())
			throw new IndexOutOfBoundsException("Channel must be in range!");
		effect = effect.clone();
		if (channel < 0) {
			if (effect.getChannelCount() != channelCount)
				// TODO Look up if this was intended way to deal with this.
				throw new IllegalArgumentException(
						"The effects channel count must be same as this mixers channel count!");
			mEffects.add(effect);
			return mEffects.size() - 1;
		} else {
			if (effect.getChannelCount() != getInputChannelOutputChannelCount(channel))
				throw new IllegalArgumentException(
						"The effects channel count must be the same as input channels channel count!");
			ArrayList<SoundEffect> e = chEffects.get(channel);
			e.add(effect);
			return e.size() - 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#removeSoundEffect(synth.SoundEffect, int)
	 */
	@Override
	public void removeSoundEffect(SoundEffect effect, int channel) {
		if (channel >= chEffects.size())
			throw new IndexOutOfBoundsException(
					"Channel must be in legal range!");
		removeSoundEffect(chEffects.get(channel).indexOf(effect), channel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#removeSoundEffect(int, int)
	 */
	@Override
	public void removeSoundEffect(int effectNumber, int channel) {
		if (channel >= chEffects.size())
			throw new IndexOutOfBoundsException(
					"Channel must be in legal range!");
		if (effectNumber < 0)
			return;
		if (channel < 0) {
			if (effectNumber >= mEffects.size())
				return;
			mEffects.set(effectNumber, null);
		} else {
			ArrayList<SoundEffect> e = chEffects.get(channel);
			if (effectNumber >= e.size())
				return;
			e.set(effectNumber, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#hasNext()
	 */
	@Override
	public abstract boolean hasNext();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#next()
	 */
	@Override
	public abstract short[] next();

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
	public abstract void reset();

	private void isVolumeLegal(int volume) {
		if (volume < 0 || volume > Short.MAX_VALUE)
			throw new IllegalArgumentException("Volume must be in range 0 to "
					+ Short.MAX_VALUE + ", but was " + volume);
	}

	/**
	 * Gets this input channel output channels count.
	 * 
	 * @param inputChannel
	 *            the input channel in question
	 * @return number of output channels
	 */
	protected abstract int getInputChannelOutputChannelCount(int inputChannel);

}
