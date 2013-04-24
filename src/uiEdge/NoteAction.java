package uiEdge;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class NoteAction extends AbstractAction {

	/**
	 * The serial version
	 */
	private static final long serialVersionUID = 1L;

	public NoteAction(String noteName, int octave) {
		super(noteName + " " + octave);
		String noteEnumName = "";
		if (octave < 0) {
			noteEnumName = "_";
			octave = -octave;
		}
		if (noteName.length() == 1) {
			noteEnumName = noteName + noteEnumName + octave;
		} else {
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
