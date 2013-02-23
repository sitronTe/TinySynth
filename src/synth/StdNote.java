/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

/**
 * A <code>StdNote</code> is meant to be a standard representation of a
 * <code>Note</code> in the <code>TinySynth</code> environment. It contains all
 * information needed for <code>TinySynth</code> to work.
 * 
 * @author Sitron Te
 * 
 */
public class StdNote implements Note {
	// absoluteNoteNumber should be correct for a 12 note scale. A4 (440Hz)
	// should be at 12 * 5 = 60
	private final int absoluteNoteNumber;
	// TODO use StdNoteCore
	private StdNoteCore noteCore;
	private int volume = -1, lengthMS = -1;

	/**
	 * Creates a new <code>StdNote</code>.
	 * 
	 * @param note
	 *            the literal name of the note. Must be two or three characters
	 *            long. First letter is note (a-g ('h' is counted as 'b')),
	 *            second is optional halfstep ('b' for down or '#'for up), third
	 *            is octave (0-9). Only note and octave is mandatory.
	 * @throws IllegalArgumentException
	 *             if argument has illegal format (too long, too short, illegal
	 *             values etc.) or if <code>getFrequency()</code> will return
	 *             illegal value for this note.
	 */
	public StdNote(String note) {
		char noteNumber = 'a';
		int n = 0, o = 0;
		if (note.length() > 3 || note.length() < 2)
			throw new IllegalArgumentException(
					"Note must be minimum 2 and maksimum 3 letters long");
		if ("abcdefgABCDEFGhH".indexOf(note.charAt(0)) < 0)
			throw new IllegalArgumentException(
					"String must start with note name!");
		if ("abcdefgABCDEFGhH".indexOf(note.charAt(0)) > 13)
			noteNumber += 1;
		else
			noteNumber += "abcdefgABCDEFG".indexOf(note.charAt(0)) % 7;
		switch (noteNumber) {
		case 'a':
			n = 0;
			o = 1;
			break;
		case 'b':
			n = 2;
			o = 1;
			break;
		case 'c':
			n = 3;
			o = 0;
			break;
		case 'd':
			n = 5;
			o = 0;
			break;
		case 'e':
			n = 7;
			o = 0;
			break;
		case 'f':
			n = 8;
			o = 0;
			break;
		case 'g':
			n = 10;
			o = 0;
			break;
		}
		if (note.length() == 3) {
			if ("0123456789".indexOf(note.charAt(2)) < 0)
				throw new IllegalArgumentException("Octave not recognized");
			o += (note.charAt(2) - '0');
			if (note.charAt(1) == '#')
				n += 1;
			else if (note.charAt(1) == 'b')
				n -= 1;
			else
				throw new IllegalArgumentException(
						"Illegal note format. When three letters define the note, the second must be b or #");
		} else {
			if ("0123456789".indexOf(note.charAt(1)) < 0)
				throw new IllegalArgumentException("Octave not recognized");
			o += (note.charAt(1) - '0');
		}
		n += 12 * o;
		absoluteNoteNumber = n;

		if (getFrequency() < 10 || getFrequency() > 22050)
			throw new IllegalArgumentException(
					"Note referred to is out of frequency range");
	}

	private StdNote(int absolute) {
		absoluteNoteNumber = absolute;
		if (getFrequency() < 10 || getFrequency() > 22050)
			throw new IllegalArgumentException(
					"Note referred to is out of frequency range");
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
		return new StdNote(absoluteNoteNumber + jump);
	}

}
