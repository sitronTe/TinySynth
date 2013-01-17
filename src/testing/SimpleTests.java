package testing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

//import synth.FadeOutEffect;
//import synth.InstrumentBox;
//import synth.Note;
//import synth.SimpleTrack;
//import synth.SingleInstrumentBox;
//import synth.SoundSource;
//import synth.StdNote;
//import synth.Track;
import synth.WaveInstrument;
import tinyEdge.AbstractSoundEventDispatcher;
import tinyEdge.BindByMarkSingleDrivenSource;
import tinyEdge.SoundEvent;
import tinyEdge.SoundSourceFactory;
import tinyEdge.SynthSoundFactory;
//import tools.CloneableSourceFactory;
//import tools.Converter;
//import tools.Player;
import tools.SimplePlayer;
import tools.SimpleSoundSourceConverter;

public class SimpleTests extends AbstractSoundEventDispatcher implements
		MouseListener {
	private String[] eventString = { "a4", "a#4", "e4", "c5" };
	private int eventNr = 0;

	public SimpleTests() {
		SoundSourceFactory fac = new SynthSoundFactory(new WaveInstrument(), 2);
		BindByMarkSingleDrivenSource ds = new BindByMarkSingleDrivenSource(2);
		ds.bindFactoryByMark(fac, 1);
		this.addSoundListener(ds);
		SimplePlayer p = new SimplePlayer(4410);
		p.setSource(new SimpleSoundSourceConverter(ds));
		p.play();
	}

	public static void main(String[] args) {
		/*
		 * Track track = new SimpleTrack(2); InstrumentBox keeper = new
		 * SingleInstrumentBox(new WaveInstrument(), 2);
		 * keeper.attachSoundEffect(new FadeOutEffect(2, 441, 22050)); Note note
		 * = new StdNote("e4"); note.setLength(400); track.insertSound(keeper,
		 * note, 0 * 44, 450 * 44); note = new StdNote("e4");
		 * note.setVolume((Short.MAX_VALUE) / 2); note.setLength(400);
		 * track.insertSound(keeper, note, 500 * 44, 88200); note = new
		 * StdNote("f4"); note.setLength(400); note.setVolume(Short.MAX_VALUE /
		 * 5); track.insertSound(keeper, note, 1000 * 44, 450 * 44); note = new
		 * StdNote("g4"); note.setLength(400); note.setVolume((Short.MAX_VALUE)
		 * / 2); track.insertSound(keeper, note, 1500 * 44, 450 * 44); note =
		 * new StdNote("g4"); note.setLength(400);
		 * note.setVolume((Short.MAX_VALUE * 1) / 4); track.insertSound(keeper,
		 * note, 2000 * 44, 450 * 44); note = new StdNote("f4");
		 * note.setLength(400); note.setVolume((Short.MAX_VALUE) / 2);
		 * track.insertSound(keeper, note, 2500 * 44, 450 * 44); note = new
		 * StdNote("e4"); note.setLength(400); note.setVolume((Short.MAX_VALUE *
		 * 2) / 4); track.insertSound(keeper, note, 3000 * 44, 450 * 44); note =
		 * new StdNote("d4"); note.setLength(400); track.insertSound(keeper,
		 * note, 3500 * 44, 450 * 44); note = new StdNote("c4");
		 * note.setLength(400); track.insertSound(keeper, note, 4000 * 44, 450 *
		 * 44); note = new StdNote("c4"); note.setLength(400);
		 * note.setVolume((Short.MAX_VALUE * 3) / 8); track.insertSound(keeper,
		 * note, 4500 * 44, 450 * 44); note = new StdNote("d4");
		 * note.setLength(400); track.insertSound(keeper, note, 5000 * 44, 450 *
		 * 44); note = new StdNote("e4"); note.setLength(400);
		 * track.insertSound(keeper, note, 5500 * 44, 450 * 44); note = new
		 * StdNote("e4"); note.setLength(400); track.insertSound(keeper, note,
		 * 6000 * 44, 450 * 44); note = new StdNote("d4"); note.setLength(400);
		 * track.insertSound(keeper, note, 6500 * 44, 450 * 44); note = new
		 * StdNote("d4"); note.setLength(400); track.insertSound(keeper, note,
		 * 7000 * 44, 450 * 44);
		 * 
		 * Converter conv = new SimpleSoundSourceConverter(track); Player player
		 * = new SimplePlayer(); player.setSource(conv);
		 * 
		 * // TODO Perform test //System.out.println("Start"); player.play();
		 * //System.out.println("play performed"); try {
		 * //System.out.println("sleeping"); Thread.sleep(1000);
		 * //System.out.println("stopping"); player.stop(); try {
		 * player.setSource(conv); } catch (Exception e) {
		 * System.out.println("Expected exception caught!");
		 * e.printStackTrace(); } //System.out.println("stopped");
		 * Thread.sleep(1000); System.out.println("continuing");
		 * //System.out.println("play"); System.out.println(player.isAlive());
		 * player.play(); //player.kill(); //System.out.println("playing"); }
		 * catch (InterruptedException e) { System.out.println("Interrupted!");
		 * }
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				SimpleTests myself = new SimpleTests();
				createGUI(myself);
			}
		});
	}

	private static void createGUI(SimpleTests myself) {
		JFrame frame = new JFrame("SimpleTests");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(myself);

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		fireSoundEvent(new SoundEvent(this, eventString[eventNr], 1,
				new Integer(1)));
		eventNr++;
		if (eventNr >= eventString.length)
			eventNr = 0;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		fireSoundEvent(new SoundEvent(this, 2));
	}

}
