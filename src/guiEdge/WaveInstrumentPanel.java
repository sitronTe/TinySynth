package guiEdge;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WaveInstrumentPanel extends JPanel implements
		PropertyChangeListener {

	/**
	 * Version of this class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Model of this panel. Encapsulates instruments.
	 */
	private WaveInstrumentModel model = null;

	/**
	 * The component that shows the wave.
	 */
	private WaveTableView waveView;

	public WaveInstrumentPanel() {
		waveView = new WaveTableView();
		setLayout(new BorderLayout());
		add(waveView, BorderLayout.CENTER);
		add(new AlterInstrumentPanel(), BorderLayout.EAST);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set LookAndFeel. The same for all elements in TinySynth.
		LookAndFeelSetter.setLookAndFeel();
		JFrame fr = new JFrame();
		WaveInstrumentPanel panel = new WaveInstrumentPanel();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.add(panel);
		fr.pack();
		fr.setVisible(true);
	}

	/**
	 * @return the model
	 */
	public WaveInstrumentModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(WaveInstrumentModel model) {
		if (this.model != null)
			this.model.removePropertyChangeListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addPropertyChangeListener(this);
		else {
			// TODO disable possibility to alter model stuff
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(
				WaveInstrumentModel.WAVE_TABLE_PROPERTY)) {
			waveView.setWaveTable((short[]) event.getNewValue());
		}
	}

	/**
	 * Panel to show controls to alter instrument.
	 * 
	 * @author Sitron Te
	 * 
	 */
	private class AlterInstrumentPanel extends JPanel {
		/**
		 * The serial version
		 */
		private static final long serialVersionUID = 1L;

		AlterInstrumentPanel() {
			setLayout(new GridBagLayout());
			add(new JButton("ButtonButtonButtonMutton?"));
		}
	}
}
