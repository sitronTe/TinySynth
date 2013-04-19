package uiEdge;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.UIManager;

public class WaveTableView extends JComponent {
	private static final long serialVersionUID = 1L;

	private int gridVerticalLines = 3, gridHorizontalLines = 3;
	private boolean centerShown = true;

	private short[] waveTable = null;

	public int getGridHorizontalLines() {
		return gridHorizontalLines;
	}

	public void setMinimum(short minimum) {
		// TODO
	}

	public short getMinimum() {
		// TODO
		return 0;
	}

	public void setMaximum(short maximum) {
		// TODO
	}

	public short getMaximum() {
		// TODO
		return 0;
	}

	public void setGridHorizontalLines(int gridLines) {
		this.gridHorizontalLines = gridLines;
		repaint();
	}

	public int getGridVerticalLines() {
		return gridVerticalLines;
	}

	public void setGridVerticalLines(int gridLines) {
		this.gridVerticalLines = gridLines;
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
		repaint();
	}

	public short[] getWaveTable() {
		return this.waveTable;
	}

	@Override
	public void paint(Graphics g) {
		int w = getWidth(), h = getHeight();
		// TODO Should insets be counted in?
		// TODO Check if we overstep our borders, and if so, how much?
		paintBG(g, w, h);
		paintGrid(g, w, h);
		paintGraph(g, w, h);
	}

	private void paintBG(Graphics g, int w, int h) {
		Color c = UIManager.getColor(LookAndFeelSetter.waveGraphBG);
		g.setColor(c == null ? getBackground() : c);
		g.fillRect(0, 0, w, h);
	}

	private void paintGrid(Graphics g, int w, int h) {
		Color c = UIManager.getColor(LookAndFeelSetter.waveGraphGrid);
		g.setColor(c == null ? getForeground() : c);
		// draw vertical lines
		if (gridVerticalLines > 0)
			for (int i = 1; i <= gridVerticalLines; i++) {
				int relW = (w * i) / (gridVerticalLines + 1);
				g.drawLine(relW, 0, relW, h);
			}
		// draw horizontal lines
		if (gridVerticalLines > 0)
			for (int i = 1; i <= gridHorizontalLines; i++) {
				int relH = (h * i) / (gridHorizontalLines + 1);
				g.drawLine(0, relH, w, relH);
			}
		c = UIManager.getColor(LookAndFeelSetter.waveGraphGridCenterLine);
		if (isCenterShown()) {
			g.setColor(c == null ? getForeground() : c);
			// draw center line
			g.drawLine(0, h / 2, w, h / 2);
		}
	}

	private void paintGraph(Graphics g, int w, int h) {
		Color c = UIManager.getColor(LookAndFeelSetter.waveGraph);
		g.setColor(c == null ? getForeground() : c);
		if (waveTable == null) {
			g.drawLine(0, 0, w, h);
			g.drawLine(0, h, w, 0);
		} else {
			// TODO Determine if detail level is correct.
			// TODO This algorithm should be made faster.
			// TODO Take heed of minimum and maximum.
			// First value must always be used. Short may never reach
			// Integer.MAX_VALUE
			int lastX = Integer.MAX_VALUE;
			int lastY = Integer.MAX_VALUE;
			List<Integer> xList = new ArrayList<>();
			List<Integer> yList = new ArrayList<>();
			for (int x = 0; x < waveTable.length; x++) {
				int relX = (x * w) / waveTable.length;
				int relY = (h / 2)
						+ ((waveTable[x] * h) / (2 * Short.MIN_VALUE));
				if (relX != lastX || relY != lastY) {
					xList.add(relX);
					yList.add(relY);
					lastX = relX;
					lastY = relY;
				}
			}
			int[] xArray = new int[xList.size()];
			int[] yArray = new int[yList.size()];
			for (int i = 0; i < xArray.length; i++) {
				xArray[i] = xList.get(i);
				yArray[i] = yList.get(i);
			}
			g.drawPolyline(xArray, yArray, xArray.length);
		}
	}

}
