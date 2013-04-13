package guiEdge;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class WaveTableView extends JComponent {
	private static final long serialVersionUID = 1L;
	// TODO Should listen to a property change for a wavetable.
	// private short[] waveWindow;
	private Color gridColor = null, centerColor = null, waveBGColor = null;
	private int gridLines = 0;
	private boolean centerShown = true;
	
	private short[] waveTable = null;

	public Color getGridColor() {
		return gridColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
		repaint();
	}

	public Color getCenterColor() {
		return centerColor;
	}

	public void setCenterColor(Color centerColor) {
		this.centerColor = centerColor;
		repaint();
	}

	public Color getWaveBGColor() {
		return waveBGColor;
	}

	public void setWaveBGColor(Color waveBGColor) {
		this.waveBGColor = waveBGColor;
		repaint();
	}

	public int getGridLines() {
		return gridLines;
	}

	public void setGridLines(int gridLines) {
		this.gridLines = gridLines;
		repaint();
	}

	public boolean isCenterShown() {
		return centerShown;
	}

	public void setCenterShown(boolean centerShown) {
		this.centerShown = centerShown;
		repaint();
	}
	
	public void setWaveTable(short[] waveTable) {
		this.waveTable = waveTable;
	}
	
	public short[] getWaveTable() {
		return this.waveTable;
	}

	@Override
	public void paint(Graphics g) {
		int w = getWidth(), h = getHeight();
		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);
		g.setColor(getForeground());
		// TODO Painting for testing, remove:
		g.drawLine(w / 2, 0, w / 2, h);
		g.drawLine(0, h / 2, w, h / 2);
		g.drawLine(0, 0, w, h);
		g.drawLine(0, h, w, 0);
		// TODO Remember insets
		// TODO Paint this things graphics
	}

}
