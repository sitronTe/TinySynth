/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package tools;

import javax.sound.sampled.AudioFormat;

/**
 * A <code>Converter</code> is a converter from raw <code>TinySynth</code> data
 * to playable audio. Use it to get sound from your <code>TinySynth</code>.
 * 
 * @author Sitron Te
 * 
 */
public interface Converter {
	/**
	 * Gets the <code>javax.sound.sampled.AudioFormat</code> this
	 * <code>Converter</code> is initialized with.
	 * 
	 * @return the <code>AudioFormat</code> data is converted to.
	 */
	public AudioFormat getAudioFormat();

	/**
	 * Checks if this <code>Converter</code> currently has more data that can be
	 * read and converted to sound.
	 * 
	 * @return <code>true</code> if this converter is ready to get some more
	 *         data. <code>false</code> otherwise.
	 */
	public boolean hasNext();

	/**
	 * Gets the playable data requested. Data is fetched in blocks of frames on
	 * request. The return array should be ready to feed directly to a
	 * <code>javax.sound.sampled.SourceDataLine</code>.
	 * 
	 * @param frames
	 *            number of frames to fetch.
	 * @return playable data if more data can be fetched. <code>null</code>
	 *         otherwise.
	 */
	public byte[] next(int frames);

}
