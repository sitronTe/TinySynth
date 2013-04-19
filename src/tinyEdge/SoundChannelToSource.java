package tinyEdge;

import synth.AbstractSoundSource;
import synth.SoundChannel;

public class SoundChannelToSource extends AbstractSoundSource {

	private final int channelCount;
	private final SoundChannel mySource;

	public SoundChannelToSource(SoundChannel source) {
		this.channelCount = 1;
		mySource = source;
	}

	public SoundChannelToSource(SoundChannel source, int channelCount) {
		if (channelCount < 1)
			throw new IllegalArgumentException(
					"Channel count must be 1 or larger!");
		this.channelCount = channelCount;
		mySource = source;
	}

	@Override
	public boolean hasNext() {
		return mySource.hasNext();
	}

	@Override
	public short[] next() {
		short next = mySource.next();
		short[] ret = new short[channelCount];
		for (int i = 0; i < channelCount; i++)
			ret[i] = next;
		return ret;
	}

	@Override
	public int getChannelCount() {
		return channelCount;
	}

	@Override
	public void reset() {
		throw new UnsupportedOperationException(
				"SoundChannel has no reset. So we have no reset.");
	}

}
