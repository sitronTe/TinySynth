package guiEdge;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Contains a single method to set the LookAndFeel for all TinySynth
 * applications. This is needed because TinySynth is built on several stand
 * alone applications.
 * 
 * @author Sitron Te
 * 
 */
public class LookAndFeelSetter {
	public static void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Could not set apropriate LookAndFeel");
		}
	}
}
