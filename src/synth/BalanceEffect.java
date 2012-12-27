/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * The <code>BalanceEffect</code> is just a neat way to control volume balance
 * between different channels. Can also be used as a volume control.
 * 
 * @author Sitron Te
 * 
 */
public class BalanceEffect implements SoundEffect {
	private final int channelCount;
	private final short[] vol;
	private int pos = 0;
	private short[] startVols = null;

	/**
	 * Creates a new <code>BalanceEffect</code> with all channels set to maximum
	 * volume.
	 * 
	 * @param channelCount
	 *            number of channels this <code>BalanceEffect</code> has.
	 * @throws IllegalArgumentException
	 *             if channelCount is less than 1
	 * 
	 */
	public BalanceEffect(int channelCount) {
		if (channelCount < 1)
			throw new IllegalArgumentException("Channel count must be over 0!");
		this.channelCount = channelCount;
		vol = new short[channelCount];
		for (int i = 0; i < vol.length; i++) {
			vol[i] = Short.MAX_VALUE;
		}
	}

	/**
	 * Creates a new <code>BalanceEffect</code> with all channels set to volume
	 * as indicated by volume array.
	 * 
	 * @param channelCount
	 *            number of channels this <code>BalanceEffect</code> has.
	 * @param volume
	 *            volume in range 0 to <code>Short.MAX_VALUE</code> for all
	 *            channels in the order given.
	 * @throws IllegalArgumentException
	 *             if channelCount is less than 1, if number of volumes is not
	 *             equal to channel count or if any of the volumes is less than
	 *             0.
	 * @throws NullPointerException
	 *             if <code>volume</code> is <code>null</code>
	 */
	public BalanceEffect(int channelCount, short[] volume) {
		if (volume == null)
			throw new NullPointerException("MUST bring along volumes!");
		if (channelCount < 1)
			throw new IllegalArgumentException("Channel count must be over 0!");
		if (volume.length != channelCount)
			throw new IllegalArgumentException(
					"Must bring along same number of volumes as channels!");
		for (short v : volume)
			if (v < 0)
				throw new IllegalArgumentException(
						"All volumes must be non-negative!");
		this.channelCount = channelCount;
		vol = new short[channelCount];
		for (int i = 0; i < vol.length; i++) {
			vol[i] = volume[i];
		}
		startVols = vol;
	}

	/**
	 * Creates a new <code>BalanceEffect</code> based on the brought along
	 * <code>BalanceEffect</code>. The new one will start counting from 0.
	 * 
	 * @param original
	 *            The original to mimic
	 */
	public BalanceEffect(BalanceEffect original) {
		this.channelCount = original.channelCount;
		this.vol = new short[original.vol.length];
		for (int i = 0; i < vol.length; i++)
			vol[i] = original.vol[i];
		this.startVols = original.startVols;
	}

	/**
	 * Sets volume for the channel in question.
	 * 
	 * @param channel
	 *            the channel to set volume for, numbering starts at 0.
	 * @param volume
	 *            new volume in range 0 to <code>Short.MAX_VALUE</code>.
	 * @throws IllegalArgumentException
	 *             if channel is outside of current range, if volume is not in
	 *             range 0 to <code>Short.MAX_VALUE</code>
	 */
	public void setVolume(int channel, int volume) {
		if (channel < 0 || channel >= channelCount)
			throw new IllegalArgumentException(
					"Channel must be in legal range!");
		if (volume < 0 || volume > Short.MAX_VALUE)
			throw new IllegalArgumentException("Volume must be in legal range!");
		vol[channel] = (short) volume;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#performEffect(short[])
	 */
	@Override
	public void performEffect(short[] sound) {
		for (int i = 0; i < sound.length; i++) {
			int t = sound[i] * vol[pos];
			t /= Short.MAX_VALUE;
			sound[i] = (short) t;
			pos++;
			if (pos == channelCount)
				pos = 0;
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
		if (startVols != null)
			for (int i = 0; i < vol.length; i++)
				vol[i] = startVols[i];
		else
			for (int i = 0; i < vol.length; i++)
				vol[i] = Short.MAX_VALUE;
		pos = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundEffect#clone()
	 */
	@Override
	public SoundEffect clone() {
		return new BalanceEffect(this);
	}

}
