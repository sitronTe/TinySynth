/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

import java.util.ArrayList;

/**
 * A single threaded {@link SoundSourceMix} with no optimization. Will always
 * have sound to play.
 * 
 * @author Sitron Te
 * 
 */
public class SimpleSoundSourceMix extends AbstractMix implements SoundSourceMix {
	private final int channelCount;
	private ArrayList<SoundSource> sources = new ArrayList<SoundSource>();

	public SimpleSoundSourceMix(int outputChannels) {
		super(outputChannels);
		channelCount = outputChannels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSourceMix#attach(synth.SoundSource)
	 */
	@Override
	public int attach(SoundSource source) {
		if (source.getChannelCount() != channelCount)
			return -1;
		sources.add(source);
		if (super.extend() != sources.size())
			throw new IllegalStateException("Incoherency detected in self!");
		return sources.size() - 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSourceMix#remove(synth.SoundSource)
	 */
	@Override
	public void remove(SoundSource source) {
		remove(sources.indexOf(source));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSourceMix#remove(int)
	 */
	@Override
	public void remove(int source) {
		if (source < 0 || source >= sources.size())
			return;
		sources.set(source, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundSourceMix#contains(synth.SoundSource)
	 */
	@Override
	public boolean contains(SoundSource source) {
		return sources.contains(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractMix#isFull()
	 */
	@Override
	public boolean isFull() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractMix#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractMix#next()
	 */
	@Override
	public short[] next() {
		short[] sound = new short[channelCount];
		for (int i = 0; i < sources.size(); i++) {
			SoundSource source = sources.get(i);
			if (source != null) {
				short[] s = source.next();
				super.performEffects(i, s);
				super.performVolumeMix(i, s);
				for (int j = 0; j < channelCount; j++)
					sound[j] += s[j];
			}
		}
		super.performMasterEffectAndVolumeMix(sound);
		return sound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractMix#getInputChannelOutputChannelCount(int)
	 */
	@Override
	protected int getInputChannelOutputChannelCount(int inputChannel) {
		return channelCount;
	}

}
