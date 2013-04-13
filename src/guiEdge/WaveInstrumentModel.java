package guiEdge;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import synth.WaveInstrument;

public class WaveInstrumentModel {

	public static String WAVE_TABLE_PROPERTY = "WaveTable";
	public static String HARMONICS_PROPERTY = "Harmonics";

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private WaveInstrument instrument;

	public WaveInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(WaveInstrument instrument) {
		WaveInstrument oldInstrument = this.instrument;
		this.instrument = instrument;
		// TODO Fire property changes.
		oldInstrument.toString();
	}

	public short[] getWaveTable() {
		// TODO
		return null;
	}

	public void setWaveTable(short[] waveTable) {
		// TODO
	}

	public int[] getInstrumentHarmonics() {
		// TODO
		return null;
	}

	public void setInstrumentHarmonics(int[] harmonics) {
		// TODO
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
