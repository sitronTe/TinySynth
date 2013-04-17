package guiEdge;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import synth.WaveInstrument;

public class WaveInstrumentModel {

	public static String WAVE_TABLE_PROPERTY = "WaveTable";
	public static String HARMONICS_PROPERTY = "Harmonics";

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private WaveInstrument instrument;
	private short[] waveTable;

	public WaveInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(WaveInstrument instrument) {
		WaveInstrument oldInstrument = this.instrument;
		short[] oldWaveTable = waveTable;
		int[] oldConstrArgs = oldInstrument == null ? null : oldInstrument
				.getConstructorArgs();
		int[] constrArgs = instrument == null ? null : instrument
				.getConstructorArgs();
		this.waveTable = instrument == null ? null : instrument
				.getWaveTableClone();
		this.instrument = instrument;
		pcs.firePropertyChange(HARMONICS_PROPERTY, oldConstrArgs, constrArgs);
		pcs.firePropertyChange(WAVE_TABLE_PROPERTY, oldWaveTable, waveTable);
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
}
