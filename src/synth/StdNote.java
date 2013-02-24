/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.XMLEncoder;

/**
 * A <code>StdNote</code> is meant to be a standard representation of a
 * <code>Note</code> in the <code>TinySynth</code> environment. It contains all
 * information needed for <code>TinySynth</code> to work.
 * 
 * @author Sitron Te
 * 
 */
public class StdNote implements Note, Cloneable {
	private StdNoteCore noteCore;
	private int volume = -1, lengthMS = -1;

	/**
	 * Creates a new <code>StdNote</code> with the given
	 * <code>StdNoteCore</code>.
	 * 
	 * @param core
	 *            the <code>StdNoteCore</code> to use.
	 */
	public StdNote(StdNoteCore core) {
		noteCore = core;
	}

	/**
	 * Creates a new <code>StdNote</code>.
	 * 
	 * @param note
	 *            the literal name of the note. Must be the same as name of a
	 *            {@link StdNoteCore}.
	 * @throws IllegalArgumentException
	 *             if argument has illegal format (too long, too short, illegal
	 *             values etc.).
	 */
	public StdNote(String note) {
		noteCore = StdNoteCore.valueOf(note);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#getFrequency()
	 */
	@Override
	public double getFrequency() {
		return noteCore.getFrequency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#getSampleCount44100Hz()
	 */
	@Override
	public int getSampleCount44100Hz() {
		return (int) (44100 / getFrequency());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#getVolume()
	 */
	@Override
	public int getVolume() {
		return volume;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#setVolume(int)
	 */
	@Override
	public void setVolume(int volume) {
		if (volume < -1 || volume > Short.MAX_VALUE)
			throw new IllegalArgumentException(
					"Volume must be in range -1 - Short.MAX_VALUE, but was "
							+ volume);
		this.volume = volume;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#getLength()
	 */
	@Override
	public int getLength() {
		return lengthMS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#getLengthSampleCount44100Hz()
	 */
	@Override
	public int getLengthSampleCount44100Hz() {
		if (lengthMS < 0)
			return Integer.MAX_VALUE;
		double l = lengthMS * 44.100;
		l /= getSampleCount44100Hz();
		l = Math.rint(l);
		l *= getSampleCount44100Hz();
		return (int) l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Note#setLength(int)
	 */
	@Override
	public void setLength(int length) {
		if (length < 0)
			lengthMS = -1;
		else
			lengthMS = length;
	}

	/**
	 * Gets a note with the relative distance from this <code>StdNote</code>.
	 * 
	 * @param jump
	 *            the relative distance from this <code>StdNote</code>
	 * @return a new <code>StdNote</code> with no volume or length set.
	 * @throws IllegalArgumentException
	 *             if the note you try to reach is out of defined frequency
	 *             range.
	 */
	public StdNote getNote(int jump) {
		return new StdNote(noteCore.getNoteCoreRelativeDistance(jump));
	}

	@Override
	public StdNote clone() {
		StdNote ret = new StdNote(noteCore);
		ret.lengthMS = lengthMS;
		ret.volume = volume;
		return ret;
	}

	@Override
	public void registerPersistenceDelegate(XMLEncoder encoder) {
		encoder.setPersistenceDelegate(getClass(), new MyDelegate());
	}

	private class MyDelegate extends DefaultPersistenceDelegate {
		protected Expression instantiate(Object oldInstance, Encoder out) {
			// In case of misuse, we don't know what to do..
			if (oldInstance.getClass() != StdNote.class)
				return null;
			StdNote old = (StdNote) oldInstance;
			return new Expression(oldInstance, oldInstance.getClass(), "new",
					new Object[] { old.noteCore });
		}
	}

}
