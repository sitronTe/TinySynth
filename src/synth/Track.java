/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

import java.util.Iterator;

/**
 * The <code>Track</code> interface is thought to store an audio track with
 * <code>InstrumentBox</code>es and <code>SoundSource</code>s attached at
 * specified sound frames. At the specified frame it will attempt to reset
 * <code>SoundSource</code> (if supported), and issue the play command to
 * <code>InstrumentBox</code>. It will clone any <code>InstrumentBox</code>
 * attached. It is meant to deliver complete audio frames for the user.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>Track</code> is thought to be created and used by a single object. It
 * is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface Track extends SoundSource {
	/**
	 * Inserts an </code>InstrumentBox</code> that should start playing at the
	 * given frame and plays for the indicated length. The
	 * <code>InstrumentBox</code> in question will be cloned, so you can use the
	 * same box for all sounds with the same <code>Instrument</code>.
	 * 
	 * @param sound
	 *            the <code>InstrumentBox</code> to clone and play from
	 * @param note
	 *            the <code>Note</code> to play
	 * @param frame
	 *            the frame to play from
	 * @param length
	 *            number of frames to play
	 * @return the unique identifier so the <code>Sound</code> can be removed or
	 *         altered.
	 * @throws IllegalArgumentException
	 *             if <code>frame</code> or <code>length</code> is less than 0.
	 * @throws NullPointerException
	 *             if either the <code>InstrumentBox</code> or <code>Note</code>
	 *             brought along is<code>null</code>
	 */
	public int insertSound(InstrumentBox sound, Note note, long frame,
			int length);

	/**
	 * Inserts an </code>SoundSource</code> that should start playing at the
	 * given frame and plays for the indicated length. When playing the
	 * <code>reset</code> command will be attempted, so the
	 * <code>SoundSource</code> is inserted and alive more than one place, it
	 * will affect all places.
	 * 
	 * @param sound
	 *            the <code>SoundSource</code> to play
	 * @param frame
	 *            the frame to play from
	 * @param length
	 *            number of frames to play
	 * @return the unique identifier so the <code>Sound</code> can be removed or
	 *         altered.
	 * @throws IllegalArgumentException
	 *             if <code>sound instanceof InstrumentBox</code> returns true.
	 * @throws IllegalArgumentException if <code>frame</code> or <code>length</code> is less than 0.
	 * @throws NullPointerException if either the <code>SoundSource</code> brought along is<code>null</code>
	 */
	public int insertSound(SoundSource sound, long frame, int length);

	/**
	 * Removes the sound identified by the identifier indicated, if identifier
	 * is not used, nothing happens.
	 * 
	 * @param uniqueIdentifier
	 *            identifier for the sound
	 */
	public void removeSound(int uniqueIdentifier);

	/**
	 * Gets an {@link Iterator} over the sounds playing at the given frame. That
	 * is its start frame has passed, but we have not passed over its complete
	 * length.
	 * 
	 * @param frame
	 *            frame to look for alive sounds in
	 * @return iterator over alive sounds
	 */
	public Iterator<Sound> getAliveSounds(long frame);

	/**
	 * Gets an {@link Iterator} over all the sounds contained within this
	 * <code>Track</code>.
	 * 
	 * @return iterator over all sounds
	 */
	public Iterator<Sound> getAllSounds();

	/**
	 * Gets the <code>Sound</code> identified by this unique identifier, or
	 * <code>null</code> if no <code>Sound</code>s has this identifier.
	 * 
	 * @param uniqueIdentifier
	 *            the identifier for the sound
	 * @return the <code>Sound</code> requested, or <code>null</code> if no such
	 *         <code>Sound</code> exists
	 */
	public Sound getSound(int uniqueIdentifier);

	/**
	 * The <code>Sound</code> interface is an interface meant for use for inside
	 * information within <code>Track</code>s. It is used for altering placement
	 * of sounds already placed in an track, getting unique identifiers for
	 * sounds and altering other properties about them. It will be meaningless
	 * outside the framework of a <code>Track</code>.
	 * 
	 * @author Sitron Te
	 * 
	 */
	public interface Sound {
		/**
		 * Gets the unique identifier needed for removal of this sound from
		 * track.
		 * 
		 * @return identifier for this <code>Sound</code>
		 */
		public int getUniqueIdentifier();

		/**
		 * Gets the start frame for this <code>Sound</code> within its
		 * <code>Track</code>.
		 * 
		 * @return start frame number
		 */
		public long getStart();

		/**
		 * Sets the start frame for this <code>Sound</code> within its
		 * <code>Track</code>.
		 * 
		 * @param frame
		 *            new start frame number
		 */
		public void setStart(long frame);

		/**
		 * Gets the number of frames this <code>Sound</code> plays for within
		 * its <code>Track</code>.
		 * 
		 * @return frames alive
		 */
		public int getLength();

		/**
		 * Sets the number of frames this <code>Sound</code> plays for within
		 * its <code>Track</code>.
		 * 
		 * @param length
		 *            frames alive
		 */
		public void setLength(int length);

		/**
		 * Checks if this <code>Sound</code> refers to an
		 * <code>InstrumentBox</code> sound, and thereby whether
		 * <code>setNote(Note note)</code> is legal.
		 * 
		 * @return <code>true</code> if this sound refers to an
		 *         <code>InstrumentBox</code>. <code>false</code> otherwise.
		 */
		public boolean isInstrumentBox();

		/**
		 * Gets the <code>Note</code> for this <code>Sound</code> if it is an
		 * <code>InstrumentBox</code>.
		 * 
		 * @return current <code>Note</code>.
		 * @throws IllegalStateException
		 *             if this <code>Sound</code> is not an
		 *             <code>InstrumentBox</code>
		 */
		public Note getNote();

		/**
		 * Sets a new <code>Note</code> this sound should play.
		 * 
		 * @param note
		 *            <code>Note</code> to play
		 * @throws IllegalStateException
		 *             if this <code>Sound</code> is not an
		 *             <code>InstrumentBox</code>
		 */
		public void setNote(Note note);
	}

}
