package uiEdge;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import synth.SoundSource;
import tinyEdge.SoundChannelToSource;
import tinyEdge.SynchronizedSingleSoundSource;
import tools.SimplePlayer;
import tools.SimpleSoundSourceConverter;

public class WaveSoundController implements PropertyChangeListener {
	// TODO Maybe lay this property in UIManager?
	private final int channelCount = 1;
	private SynchronizedSingleSoundSource soundSource;
	private WaveInstrumentModel model = null;

	public WaveSoundController() {
		soundSource = new SynchronizedSingleSoundSource(channelCount);
		// TODO Should maybe be common for all classes?
		SimplePlayer player = new SimplePlayer(1000);
		player.setSource(new SimpleSoundSourceConverter(soundSource));
		player.play();
	}

	public void setModel(WaveInstrumentModel model) {
		if (this.model != null)
			this.model.removePropertyChangeListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addPropertyChangeListener(this);
		else
			soundSource.setPlaying(false);
	}
	
	public void setPlaying(boolean playing) {
		soundSource.setPlaying(playing);
	}

	public WaveInstrumentModel getModel() {
		return model;
	}
	
	public SoundSource getSoundSource() {
		return soundSource;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (WaveInstrumentModel.INSTRUMENT_PROPERTY.equals(evt
				.getPropertyName())) {
			soundSource.changeSoundSource(new SoundChannelToSource(model
					.getInstrument(), channelCount));
		}
		if (WaveInstrumentModel.IS_PLAYING_PROPERTY.equals(evt.getPropertyName())) {
			soundSource.setPlaying((boolean)evt.getNewValue());
		}
	}
}
