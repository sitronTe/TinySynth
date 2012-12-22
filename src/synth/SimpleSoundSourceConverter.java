/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import javax.sound.sampled.AudioFormat;

/**
 * A <code>SimpleSoundSourceConverter</code> is an implementation of a
 * {@link Converter} for a {@link SoundSource}. It is built to make life a
 * little simpler while using the <code>TinySynth</code>. It is not possible to
 * make any specifications of the <code>javax.sound.sampled.AudioFormat</code>
 * you are using. It is automatically set up according to the
 * <code>SoundSource</code> this <code>SimpleSoundSourceConverter</code> is
 * created for.
 * 
 * @author Sitron Te
 * 
 */
public class SimpleSoundSourceConverter implements Converter {
	private final SoundSource source;
	private final boolean bigEndian = true;

	/**
	 * Creates a new <code>SimpleSoundSourceConverter</code> ready to play the
	 * brought along <code>SoundSource</code>.
	 * 
	 * @param source
	 *            the <code>SoundSource</code> to play from.
	 */
	public SimpleSoundSourceConverter(SoundSource source) {
		this.source = source;
	}

	@Override
	public AudioFormat getAudioFormat() {
		return new AudioFormat(44100, 16, source.getChannelCount(), true, bigEndian);
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	@Override
	public byte[] next(int frames) {
		return translateShortToBytes(source.next(frames), bigEndian);
	}

	private byte[] translateShortToBytes(short[] shortList, boolean bigEndian) {
		if (shortList != null) {
			int i;
			byte[] utMat = new byte[2 * shortList.length];
			for (i = 0; i < shortList.length; i++) {
				int placeLarge, placeSmall;
				placeLarge = bigEndian ? 2 * i : (2 * i) + 1;
				placeSmall = bigEndian ? (2 * i) + 1 : 2 * i;
				utMat[placeLarge] = (byte) (shortList[i] >>> 8); // MSB
				utMat[placeSmall] = (byte) (shortList[i] & 0x00FF); // LSB
			}
			return utMat;
		}
		return null;
	}
}
