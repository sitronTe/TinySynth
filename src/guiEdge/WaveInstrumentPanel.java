package guiEdge;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WaveInstrumentPanel extends JPanel {

	/**
	 * Version of this class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Model of this panel. Encapsulates instruments.
	 */
	private WaveInstrumentModel model;

	public WaveInstrumentPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();
		add(new WaveTableView(), constr);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.add(new WaveInstrumentPanel());
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
		this.model = model;
	}

}
