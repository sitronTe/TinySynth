/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tools;

import synth.Instrument;
import synth.InstrumentBox;
import synth.SingleInstrumentBox;
import synth.SoundEffect;
import synth.SoundSource;
import synth.StdNote;

/**
 * The <code>SynthSoundFactory</code> is a {@link SoundSourceFactory} which
 * actually delivers {@link InstrumentBox}es that are ready to play.
 * 
 * @author Sitron Te
 * 
 */
public class SynthSoundFactory implements SoundSourceFactory {
	private InstrumentBox source;

	/**
	 * Creates a new <code>SynthSoundFactory</code> based on the
	 * {@link Instrument} delivered. No {@link SoundEffect}s are connected to
	 * the <code>Instrument</code> in the beginning.
	 * 
	 * @param instrument
	 *            the <code>Instrument</code> to deliver sound from
	 * @param outputChannels
	 *            number of output channels for <code>SoundSource</code>s
	 *            delivered from this <code>SynthSoundFactory</code>
	 * @throws IllegalArgumentException
	 *             if output channels is set to 0 or less
	 */
	public SynthSoundFactory(Instrument instrument, int outputChannels) {
		if (outputChannels < 1)
			throw new IllegalArgumentException(
					"Output channels must be at least 1!");
		source = new SingleInstrumentBox(instrument.clone(), outputChannels);
	}

	/**
	 * Creates a new <code>SynthSoundFactory</code> based on the
	 * <code>InstrumentBox</code> delivered.
	 * 
	 * @param instrumentBox
	 *            the <code>InstrumentBox</code> to use in this
	 *            <code>SynthSoundFactory</code>
	 */
	public SynthSoundFactory(InstrumentBox instrumentBox) {
		source = instrumentBox.clone();
	}

	/**
	 * Returns a clone of this <code>SynthSoundFactory</code>s
	 * <code>InstrumentBox</code> with no {@link Note} set.
	 * 
	 * @see SoundSourceFactory#getSoundSourceInstance()
	 */
	@Override
	public SoundSource getSoundSourceInstance() {
		return source.clone();
	}

	/**
	 * Returns an <code>InstrumentBox</code> with <code>Note</code> set based on
	 * the string delivered
	 * 
	 * @see SoundSourceFactory#getSoundSourceInstance(java.lang.String)
	 * @see StdNote
	 */
	@Override
	public SoundSource getSoundSourceInstance(String description) {
		InstrumentBox ret = source.clone();
		ret.play(new StdNote(description));
		return ret;
	}

	/**
	 * Attaches a <code>SoundEffect</code> to this
	 * <code>SynthSoundFactory</code>s internal <code>InstrumentBox</code>.
	 * 
	 * @param e
	 *            the <code>SoundEffect</code> to attach
	 * @see InstrumentBox#attachSoundEffect(SoundEffect)
	 */
	public synchronized void addSoundEffect(SoundEffect e) {
		source.attachSoundEffect(e);
	}

	/**
	 * Removes this <code>SoundEffect</code> from this
	 * <code>SynthSoundFactory</code>s internal <code>InstrumentBox</code>.
	 * 
	 * @param e
	 *            the <code>SoundEffect</code> to remove
	 * @see InstrumentBox#removeSoundEffect(SoundEffect)
	 */
	public synchronized void removeSoundEffect(SoundEffect e) {
		source.removeSoundEffect(e);
	}

	/**
	 * Removes all <code>SoundEffect</code>s from this
	 * <code>SynthSoundFactory</code>s internal <code>InstrumentBox</code>.
	 * 
	 * @see InstrumentBox#removeAllSoundEffects()
	 */
	public synchronized void removeAllSoundEffects() {
		source.removeAllSoundEffects();
	}

	/**
	 * Removes all <code>Instrument</code>s connected to this
	 * <code>SynthSoundFactory</code>s internal <code>InstrumentBox</code> and
	 * replaces them with the delivered <code>Instrument</code>
	 * 
	 * @param i
	 *            the <code>Instrument</code> to deliver from
	 */
	public synchronized void setInstrument(Instrument i) {
		source.detachInstrument();
		source.attachInstrument(i);
	}

	/**
	 * Sets a new <code>InstrumentBox</code> for this
	 * <code>SynthSoundSourceFactory</code>. It will automatically be cloned.
	 * 
	 * @param i
	 *            the <code>InstrumentBox</code> to deliver from
	 */
	public synchronized void setInstrument(InstrumentBox i) {
		source = i.clone();
	}

}
