package uiEdge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synth.WaveInstrument;

// TODO Clean code. Too messy.
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

	/**
	 * The panel that allow you to alter instruments.
	 */
	private AlterInstrumentPanel alterInstrumentPanel;

	private SpinnerChangeListener spinnerListener = new SpinnerChangeListener();
	private JCheckBox autoUpdateInstrument = new JCheckBox("Auto");
	private JRadioButton viewNoteWaveTable = new JRadioButton("View note");
	private JRadioButton viewWaveTable = new JRadioButton("View wavetable");

	public WaveInstrumentPanel() {
		waveView = new WaveTableView();
		alterInstrumentPanel = new AlterInstrumentPanel();
		setLayout(new BorderLayout());
		add(waveView, BorderLayout.CENTER);
		add(alterInstrumentPanel, BorderLayout.EAST);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set LookAndFeel. The same for all elements in TinySynth.
		LookAndFeelSetter.setLookAndFeel();
		JFrame fr = new JFrame();
		WaveInstrumentPanel panel = new WaveInstrumentPanel();
		WaveInstrumentModel model = new WaveInstrumentModel();
		WaveSoundController soundCtrl = new WaveSoundController();
		soundCtrl.setModel(model);
		panel.setModel(model);
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

	private void updateInstrument() {
		alterInstrumentPanel.updateInstrumentNow();
	}

	/**
	 * Panel to show controls to alter instrument.
	 * 
	 * @author Sitron Te
	 * 
	 */
	private class AlterInstrumentPanel extends JPanel {

		JButton addOvertoneButton, updateNowButon;
		List<JSpinner> spinnerList;
		ScrollOvertonePanel overtoneListPanel;

		/**
		 * The serial version
		 */
		private static final long serialVersionUID = 1L;

		AlterInstrumentPanel() {
			Insets insets = new Insets(3, 0, 3, 0);
			GridBagConstraints constraints = new GridBagConstraints(0, 1, 2, 1,
					0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			setLayout(new GridBagLayout());

			spinnerList = new ArrayList<>();
			addOvertoneSpinner();
			spinnerList.get(0).setValue(1);

			addOvertoneButton = new JButton("Add overtone");
			addOvertoneButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addOvertoneSpinner();
				}
			});
			add(addOvertoneButton, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 0;
			constraints.gridx = 0;
			constraints.gridwidth = 1;
			add(autoUpdateInstrument, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 0;
			constraints.gridx = 1;
			constraints.gridwidth = 1;
			updateNowButon = new JButton("Update instrument");
			updateNowButon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					updateInstrument();
				}
			});
			add(updateNowButon, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 2;
			constraints.gridx = 0;
			constraints.gridwidth = 2;
			overtoneListPanel = new ScrollOvertonePanel();
			add(overtoneListPanel, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 3;
			constraints.gridx = 0;
			constraints.gridwidth = 2;
			// TODO Bar choose note

			ButtonGroup grp = new ButtonGroup();
			grp.add(viewNoteWaveTable);
			grp.add(viewWaveTable);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 4;
			constraints.gridx = 0;
			constraints.gridwidth = 1;
			add(viewWaveTable, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 4;
			constraints.gridx = 1;
			add(viewNoteWaveTable, constraints);

			WaveTableViewActionListener wl = new WaveTableViewActionListener();
			viewNoteWaveTable.addActionListener(wl);
			viewWaveTable.addActionListener(wl);
			viewWaveTable.setSelected(true);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridy = 5;
			constraints.gridx = 0;
			constraints.gridwidth = 1;
			JButton btn = new JButton("Play");
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (model != null)
						model.setPlaying(true);
				}
			});
			add(btn, constraints);

			constraints = (GridBagConstraints) constraints.clone();
			constraints.gridx = 1;
			btn = new JButton("Stop");
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (model != null)
						model.setPlaying(false);
				}
			});
			add(btn, constraints);
		}

		void addOvertoneSpinner() {
			JSpinner spinner = new JSpinner();
			spinner.addChangeListener(spinnerListener);
			spinnerList.add(spinner);
			if (overtoneListPanel != null)
				overtoneListPanel.updateContent();
		}

		void updateInstrumentNow() {
			// TODO find better way to deal with all 0.
			if (spinnerList.size() == 0)
				model.setInstrument(new WaveInstrument());
			else {
				int[] harm = new int[spinnerList.size()];
				boolean all0 = true;
				for (int i = 0; i < spinnerList.size(); i++) {
					int val = (int) spinnerList.get(i).getValue();
					all0 = all0 && val == 0;
					harm[i] = val;
				}
				if (all0)
					model.setInstrument(new WaveInstrument());
				else
					model.setInstrumentHarmonics(harm);
			}
		}

		private class ScrollOvertonePanel extends JPanel {
			/**
			 * The serial version
			 */
			private static final long serialVersionUID = 1L;
			final static int VISIBLE_OVERTONE_COUNT = 5;
			int position = 0;
			JButton next, previous;
			List<JLabel> labelList;
			GridBagConstraints constraints, nextConstr, prevConstr;
			Stack<Component> visibleComps = new Stack<>();

			public ScrollOvertonePanel() {
				setLayout(new GridBagLayout());
				Insets insets = new Insets(0, 0, 0, 0);
				prevConstr = new GridBagConstraints(0, 0, 2, 1, 0, 0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						insets, 0, 0);

				previous = new JButton("^");
				add(previous, prevConstr);

				nextConstr = (GridBagConstraints) prevConstr.clone();
				nextConstr.gridy = VISIBLE_OVERTONE_COUNT + 1;
				next = new JButton("v");
				add(next, nextConstr);

				labelList = new ArrayList<>();

				updateContent();

				next.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (position < spinnerList.size()
								- VISIBLE_OVERTONE_COUNT)
							position++;
						updateContent();
					}
				});
				previous.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (position > 0)
							position--;
						updateContent();
					}
				});
			}

			void updateContent() {
				Insets lblInsets = new Insets(0, 5, 0, 5);
				Insets spinnerInsets = new Insets(0, 5, 0, 5);
				int lblSize = labelList.size(), totSize = spinnerList.size();
				for (int i = lblSize; i < totSize; i++)
					labelList.add(new JLabel("" + (i + 1)));
				while (visibleComps.size() > 0)
					remove(visibleComps.pop());
				for (int i = 0; i < totSize && i < VISIBLE_OVERTONE_COUNT; i++) {
					Component comp;
					constraints = new GridBagConstraints(0, i + 1, 1, 1, 0.5,
							0.5, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, lblInsets, 0, 0);
					comp = labelList.get(i + position);
					add(comp, constraints);
					visibleComps.push(comp);
					constraints = (GridBagConstraints) constraints.clone();
					constraints.insets = spinnerInsets;
					constraints.gridx = 1;
					comp = spinnerList.get(i + position);
					add(comp, constraints);
					visibleComps.push(comp);
				}
				// To make components repaint in correct way.
				revalidate();
			}

			@Override
			public void setEnabled(boolean enabled) {
				super.setEnabled(enabled);
				updateContent();
			}
		}
	}

	private class SpinnerChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (autoUpdateInstrument.isSelected())
				updateInstrument();
		}
	}

	private class WaveTableViewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model != null) {
				if (viewNoteWaveTable.isSelected()) {
					waveView.setWaveTable(model.getNoteSetWaveTable());
				} else {
					waveView.setWaveTable(model.getWaveTable());
				}
			}
		}
	}
}
