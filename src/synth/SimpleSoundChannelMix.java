/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

import java.util.ArrayList;

/**
 * A single threaded {@link SoundChannelMix} with no optimization. Will always
 * have sound to play.
 * 
 * @author Sitron Te
 * 
 */
public class SimpleSoundChannelMix extends AbstractMix implements
		SoundChannelMix {
	private final int channelCount;
	private ArrayList<SoundChannel> sources = new ArrayList<SoundChannel>();

	public SimpleSoundChannelMix(int outputChannels) {
		super(outputChannels);
		this.channelCount = outputChannels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannelMix#attach(synth.SoundChannel)
	 */
	@Override
	public int attach(SoundChannel channel) {
		sources.add(channel);
		if (super.extend() != sources.size())
			throw new IllegalStateException("Incoherency detected in self!");
		return sources.size() - 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannelMix#remove(synth.SoundChannel)
	 */
	@Override
	public void remove(SoundChannel channel) {
		remove(sources.indexOf(channel));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannelMix#remove(int)
	 */
	@Override
	public void remove(int channel) {
		if (channel < 0 || channel >= sources.size())
			return;
		sources.set(channel, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannelMix#contains(synth.SoundChannel)
	 */
	@Override
	public boolean contains(SoundChannel channel) {
		return sources.contains(channel);
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
		short[] temp = new short[channelCount];
		for (int i = 0; i < sources.size(); i++) {
			SoundChannel channel = sources.get(i);
			if (channel != null && channel.hasNext()) {
				short[] s = new short[1];
				s[0] = channel.next();
				super.performEffects(i, s);
				for (int j = 0; j < channelCount; j++)
					temp[j] = s[0];
				super.performVolumeMix(i, temp);
				for (int j = 0; j < channelCount; j++) {
					int t = sound[j] + temp[j];
					if (t > Short.MAX_VALUE)
						sound[j] = Short.MAX_VALUE;
					else if (t < -Short.MAX_VALUE)
						sound[j] = -Short.MAX_VALUE;
					else
						sound[j] += temp[j];
				}
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
		return 1;
	}

}
