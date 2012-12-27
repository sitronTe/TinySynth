/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

/**
 * The <code>InstrumentBox</code> interface is thought to store an
 * <code>Instrument</code> in a way that it can be played independently of the
 * original instrument. That means it will clone the instrument, and by invoking
 * the play command you can store it for later use. It is also intended to have
 * <code>SoundEffect</code>s attached, so that the sound emerging is directly
 * playable. It is meant to deliver complete audio frames for the user.
 * <p>
 * Locally, within the <code>TinySynth</code> all internal communication is
 * based on 16 bit communication, <code>short</code>, when applicable default
 * frequency is 44100 Hz.
 * <p>
 * A <code>InstrumentBox</code> is thought to be created and used by a single
 * object. It is generally not thread-safe.
 * 
 * @author Sitron Te
 * 
 */
public interface InstrumentBox extends SoundSource {
	/**
	 * Clones this <code>InstrumentBox</code> by invoking the
	 * <code>clone()</code> on both its <code>Instrument</code> and its
	 * <code>SoundEffect</code>s. The clone is delivered, that is it is as if
	 * <code>next()</code> or <code>next(int frames)</code> has not been called.
	 * 
	 * @return a clone
	 */
	public InstrumentBox clone();

	/**
	 * Looks if there is possible to attach any more instruments to this box.
	 * 
	 * @return <code>true</code> if you cannot attach instrument.
	 *         <code>false</code> if you can attach instrument
	 */
	public boolean isTaken();

	/**
	 * Attaches an instrument to this box if possible, the instrument is cloned
	 * automatically. Return <code>true</code> if successful.
	 * 
	 * @param instrument
	 *            the instrument to attach
	 * @return <code>true</code> if attach was successful. <code>false</code> if
	 *         attached failed.
	 */
	public boolean attachInstrument(Instrument instrument);

	/**
	 * Removes any instrument(s) attached to this <code>InstrumentBox</code>.
	 */
	public void detachInstrument();

	/**
	 * Sets the <code>Note</code> to play, and initializes the
	 * <code>Instrument</code>s with it. And as promised by the
	 * {@link Instrument}, it will take heed of the volume and length of the
	 * <code>Note</code> to be played.
	 * 
	 * @param note
	 *            the <code>Note</code> to play
	 * @return if note was successfully set.
	 */
	public boolean play(Note note);

	/**
	 * Gets the <code>Note</code> this <code>InstrumentBox</code> is set to
	 * play.
	 * 
	 * @return the note this <code>InstrumentBox</code> is set to play or
	 *         <code>null</code> if no <code>Note</code> is set.
	 */
	public Note getNote();

	/**
	 * Attaches a <code>SoundEffect</code> to work on output sound. The return
	 * value will tell if this attach fail for some reason. Different attached
	 * effects will do their work in the order they were attached.
	 * 
	 * @param effect
	 *            the effect to attach
	 * @return whether the attach succeeded. Will fail if
	 *         <code>effect.getChannelCount() != this.getChannelCount()</code>
	 */
	public boolean attachSoundEffect(SoundEffect effect);

	/**
	 * Removes this <code>SoundEffect</code> from the list of sound effects. If
	 * this sound effect is not attached, this method returns without doing
	 * anything.
	 * 
	 * @param effect
	 *            effect to remove
	 */
	public void removeSoundEffect(SoundEffect effect);

	/**
	 * Removes the <code>SoundEffect</code> indicated from the list of sound
	 * effects. If <code>effectNumber</code> refers to an effect not assigned,
	 * an exception is thrown.
	 * 
	 * @param effectNumber
	 *            effect to remove. First effect attached has index 0.
	 * @throws IndexOutOfBoundsException
	 *             if <code>effectNumber</code> refers to an illegal effect.
	 */
	public void removeSoundEffect(int effectNumber);

	/**
	 * Removes all <code>SoundEffect</code>s attached to this
	 * <code>InstrumentBox</code>.
	 */
	public void removeAllSoundEffects();

	/**
	 * Checks whether output sound from this <code>InstrumentBox</code> has
	 * attached the <code>SoundEffect</code> in question.
	 * 
	 * @param effect
	 *            effect to check for.
	 * @return if <code>effect</code> works on output sound
	 */
	public boolean containsEffect(SoundEffect effect);

	/**
	 * Gets the total <code>SoundEffect</code> count connected to this
	 * <code>InstrumentBox</code>.
	 * 
	 * @return <code>SoundEffect</code> count
	 */
	public int getSoundEffectCount();
}
