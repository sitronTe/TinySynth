package uiEdge;

import java.awt.Color;

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
	public static final String waveGraphBG = "waveGraphBG";
	public static final String waveGraphGrid = "waveGraphGrid";
	public static final String waveGraphGridCenterLine = "waveGraphGridCenterLine";
	public static final String waveGraph = "waveGraph";

	public static void setLookAndFeel() {
		setCustomColors();
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

	private static void setCustomColors() {
		UIManager.put(waveGraphBG, new Color(0, 0, 0));
		UIManager.put(waveGraphGrid, new Color(0x33, 0x33, 0x33));
		UIManager.put(waveGraphGridCenterLine, new Color(0xcc, 0x44, 0x44));
		UIManager.put(waveGraph, new Color(0xdd, 0xdd, 0xdd));
	}
}
