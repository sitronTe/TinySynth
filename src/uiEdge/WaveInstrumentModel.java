package uiEdge;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import synth.StdNote;
import synth.StdNoteCore;
import synth.WaveInstrument;

public class WaveInstrumentModel {

	public static final String WAVE_TABLE_PROPERTY = "WaveTable";
	public static final String HARMONICS_PROPERTY = "Harmonics";
	public static final String INSTRUMENT_PROPERTY = "Instrument";
	public static final String NOTE_CORE_PROPERTY = "TestNote";
	public static final String NOTE_SET_WAVE_TABLE_PROPERTY = "NoteSetWaveTableProperty";
	public static final String VOLUME_PROPERTY = "VolumeProperty";
	// TODO Should this really be part of this model?
	public static final String IS_PLAYING_PROPERTY = "IsPlayingProperty";

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private WaveInstrument instrument;
	private short[] waveTable;
	private short[] noteSetWaveTable;
	private StdNote note = new StdNote(StdNoteCore.A4);
	private StdNoteCore noteCore = StdNoteCore.A4;
	// TODO Should this be part of model?
	private boolean isPlaying = false;

	public WaveInstrumentModel() {
		note.setVolume(Short.MAX_VALUE / 4);
	}

	public WaveInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(WaveInstrument instrument) {
		WaveInstrument oldInstrument = this.instrument;
		short[] oldWaveTable = waveTable;
		short[] oldNoteSetTable = noteSetWaveTable;
		int[] constrArgs;
		int[] oldConstrArgs = oldInstrument == null ? null : oldInstrument
				.getConstructorArgs();
		this.instrument = instrument;
		if (instrument == null) {
			constrArgs = null;
			this.waveTable = null;
			this.noteSetWaveTable = null;
		} else {
			constrArgs = instrument.getConstructorArgs();
			this.waveTable = instrument.getWaveTableClone();
			if (this.note != null) {
				this.instrument.play(note);
				this.noteSetWaveTable = this.instrument.next(note
						.getSampleCount44100Hz());
				this.instrument.play(note);
			} else
				this.noteSetWaveTable = null;
		}
		pcs.firePropertyChange(INSTRUMENT_PROPERTY, oldInstrument, instrument);
		pcs.firePropertyChange(HARMONICS_PROPERTY, oldConstrArgs, constrArgs);
		pcs.firePropertyChange(WAVE_TABLE_PROPERTY, oldWaveTable, waveTable);
		if (this.noteSetWaveTable != oldNoteSetTable)
			pcs.firePropertyChange(NOTE_SET_WAVE_TABLE_PROPERTY,
					oldNoteSetTable, this.noteSetWaveTable);
	}

	public short[] getWaveTable() {
		return waveTable;
	}

	public void setWaveTable(short[] waveTable) {
		setInstrument(new WaveInstrument(waveTable));
	}

	public int[] getInstrumentHarmonics() {
		return instrument == null ? null : instrument.getConstructorArgs();
	}

	public void setInstrumentHarmonics(int[] harmonics) {
		if (harmonics == null)
			setInstrument(new WaveInstrument());
		else
			setInstrument(new WaveInstrument(harmonics));
	}

	public void setNoteCore(StdNoteCore noteCore) {
		WaveInstrument oldInstr = instrument;
		short[] oldNoteSetWave = noteSetWaveTable;
		StdNoteCore oldStdNoteCore = this.noteCore;
		int vol = note.getVolume();
		this.noteCore = noteCore;
		note = new StdNote(noteCore);
		note.setVolume(vol);
		pcs.firePropertyChange(NOTE_CORE_PROPERTY, oldStdNoteCore, noteCore);
		if (instrument != null) {
			instrument = (WaveInstrument) instrument.clone();
			instrument.play(note);
			noteSetWaveTable = instrument.next(note.getSampleCount44100Hz());
			instrument.play(note);
			pcs.firePropertyChange(NOTE_SET_WAVE_TABLE_PROPERTY,
					oldNoteSetWave, noteSetWaveTable);
			pcs.firePropertyChange(INSTRUMENT_PROPERTY, oldInstr, instrument);
		}
	}

	public StdNoteCore getNoteCore() {
		return noteCore;
	}

	public void setVolume(int volume) {
		int oldVolume = note.getVolume();
		note.setVolume(volume);
		pcs.firePropertyChange(VOLUME_PROPERTY, oldVolume, volume);
		setNoteCore(noteCore);
	}

	public int getVolume() {
		return note.getVolume();
	}

	public short[] getNoteSetWaveTable() {
		return noteSetWaveTable;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * @return the isPlaying
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * @param isPlaying the isPlaying to set
	 */
	public void setPlaying(boolean isPlaying) {
		boolean oldIsPlaing = this.isPlaying;
		this.isPlaying = isPlaying;
		pcs.firePropertyChange(IS_PLAYING_PROPERTY, oldIsPlaing, isPlaying);
	}
}
