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
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * The <code>WaveInstrument</code> is a wavetable implementation of the
 * interface <code>Instrument</code>.
 * <p>
 * This implementation is based on an array with 4410 cells. This would
 * correspond to a 10 Hz sound playing in 44100 Hz sample frequency.
 * <p>
 * As mentioned in the interface, this class is generally not thread safe.
 * 
 * @author Sitron Te
 * 
 */
public class WaveInstrument implements Instrument, Cloneable {
	private static final int WAVE_LENGTH = 4410;

	private final short[] waveTable;
	private final int[] conArgs;
	// choose to take length here because I have the belief that if will go
	// faster looking up this variable than asking the note for its length all
	// the time.
	private transient int place = 0, length = -1, singleCycle = 0, vol = -1;
	private transient short[] currentPlay = null;
	private transient Note note = null;

	/**
	 * Constructs this <code>WaveInstrument</code> as a sinusoidal sound.
	 */
	public WaveInstrument() {
		short[] table = new short[WAVE_LENGTH];
		for (int i = 0; i < WAVE_LENGTH; i++) {
			double v = Math.PI * (double) 2 * (double) i;
			v = v / (double) WAVE_LENGTH;
			v = Math.sin(v);
			table[i] = (short) (v * (double) Short.MAX_VALUE);
		}
		waveTable = table;
		conArgs = new int[1];
		conArgs[0] = 1;
	}

	/**
	 * Constructs this <code>WaveInstrument</code> with a wavetable copied from
	 * the given array. The array is copied completely; this
	 * <code>WaveInstrument</code> is not altered due to later changes in the
	 * given array. Length of array must be 4410, and it must sum up to be
	 * within [-100 , 100].
	 * 
	 * @param wavetable
	 *            the array to construct this <code>WaveInstrument</code>s
	 *            wavetable from
	 * @throws IllegalArgumentException
	 *             if <code>wavetable.length != 4410</code> returns
	 *             <code>true</code>
	 * @throws IllegalArgumentException
	 *             if the absolute value of the sum of the entire
	 *             <code>wavetable</code> is 100 or more.
	 */
	public WaveInstrument(short[] wavetable) {
		if (wavetable.length != WAVE_LENGTH)
			throw new IllegalArgumentException("Wavetable must be "
					+ WAVE_LENGTH + " frames long!");
		int tot = 0;
		short[] t = new short[WAVE_LENGTH];
		for (int i = 0; i < WAVE_LENGTH; i++) {
			t[i] = wavetable[i];
			tot += wavetable[i];
		}
		if (Math.abs(tot) >= 100)
			throw new IllegalArgumentException(
					"Wavetable was not properly balanced!");
		waveTable = t;
		conArgs = null;
	}

	/**
	 * Constructs this <code>WaveInstrument</code> with harmonies with relative
	 * volume contained in the brought along array.
	 * 
	 * @param harmonies
	 *            the relative volume of all harmonies in this sound. The base
	 *            frequency is <code>harmonies[0]</code>. Higher absolute value
	 *            is higher relative volume. Negative value means opposite
	 *            phase. Final volume is unchangeable.
	 * @throws IllegalArgumentException
	 *             If a relative volume is set to be negative.
	 * 
	 */
	public WaveInstrument(int[] harmonies) {
		// test for illegal arguments
		int totVol = 0;
		for (int v : harmonies) {
			totVol += Math.abs(v);
		}
		if (totVol == 0)
			throw new IllegalArgumentException("Silenced sound is not allowed!");

		// create the wavetable
		short[] table = new short[WAVE_LENGTH];
		for (int i = 0; i < WAVE_LENGTH; i++) {
			double t;
			int v = 0;
			// add all harmonies
			for (int j = 1; j <= harmonies.length; j++) {
				t = ((double) i / (double) WAVE_LENGTH) * (double) 2
						* (double) j * Math.PI;
				t = Math.sin(t) * harmonies[j - 1] * Short.MAX_VALUE;
				v += (int) t;
			}
			v = v / totVol;
			table[i] = (short) v;
		}
		waveTable = table;
		conArgs = new int[harmonies.length];
		for (int i = 0; i < harmonies.length; i++)
			conArgs[i] = harmonies[i];
	}

	/**
	 * Constructs this <code>WaveInstrument</code> cloned from the brought along
	 * <code>WaveInstrument</code>. The same result as calling
	 * <code>original.clone()</code>.
	 * 
	 * @param original
	 *            the <code>WaveInstrument</code> to create a clone from.
	 */
	public WaveInstrument(WaveInstrument original) {
		this.waveTable = original.waveTable;
		this.conArgs = original.conArgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannel#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (note == null)
			return false;
		if (length < 0)
			return true;
		if (place <= length)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannel#next()
	 */
	@Override
	public short next() {
		// TODO there should be an option to make use of the complete wavetable.
		// That is, take the average of those places we right now are skipping.
		if (!hasNext())
			throw new NoSuchElementException(
					"There are no more sound in this instrument!");
		if (vol < 0)
			return currentPlay[place++ % singleCycle];
		else {
			int ret = currentPlay[place++ % singleCycle] * vol;
			ret = ret / Short.MAX_VALUE;
			return (short) ret;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.SoundChannel#next(int)
	 */
	@Override
	public short[] next(int frames) {
		// TODO This may be made more effective, however to complete it fast, I
		// choose this.
		if (!hasNext())
			throw new NoSuchElementException(
					"There are no more sound in this instrument!");
		if (frames <= 0)
			throw new IllegalArgumentException(
					"Must ask for more than 0 frames, but you asked for "
							+ frames);
		short[] ret = new short[frames];
		for (int i = 0; i < frames; i++) {
			if (hasNext())
				ret[i] = next();
			else {
				short[] r = new short[i];
				for (int j = 0; j < i; j++)
					r[j] = ret[j];
				return r;
			}
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Instrument#play(synth.Note)
	 */
	@Override
	public void play(Note note) {
		place = 0;
		this.note = note;
		vol = note.getVolume();
		length = note.getLengthSampleCount44100Hz();
		singleCycle = note.getSampleCount44100Hz();
		currentPlay = new short[singleCycle];
		for (int i = 0; i < singleCycle; i++)
			currentPlay[i] = waveTable[(i * WAVE_LENGTH) / singleCycle];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Instrument#clone()
	 */
	@Override
	public Instrument clone() {
		return new WaveInstrument(this);
	}

	private boolean hasConArgs(WaveInstrument w) {
		return w.conArgs != null;
	}

	private int[] getConArgs(WaveInstrument w) {
		return w.conArgs;
	}

	private short[] getWaveTable(WaveInstrument w) {
		return w.waveTable;
	}

	private class MyDelegate extends DefaultPersistenceDelegate {
		protected Expression instantiate(Object oldInstance, Encoder out) {
			// In case of misuse, we don't know what to do..
			if (oldInstance.getClass() != WaveInstrument.class)
				return null;
			if (hasConArgs((WaveInstrument) oldInstance))
				return new Expression(
						oldInstance,
						oldInstance.getClass(),
						"new",
						new Object[] { getConArgs((WaveInstrument) oldInstance) });
			else
				return new Expression(
						oldInstance,
						oldInstance.getClass(),
						"new",
						new Object[] { getWaveTable((WaveInstrument) oldInstance) });
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(waveTable);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaveInstrument other = (WaveInstrument) obj;
		if (!Arrays.equals(waveTable, other.waveTable))
			return false;
		return true;
	}

	@Override
	public void registerPersistenceDelegate(XMLEncoder encoder) {
		encoder.setPersistenceDelegate(this.getClass(), new MyDelegate());
	}

}
