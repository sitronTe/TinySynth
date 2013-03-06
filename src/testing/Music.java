package testing;

import synth.Instrument;
import synth.InstrumentBox;
import synth.LinearFadeOutEffect;
import synth.Note;
import synth.SimpleTrack;
import synth.SingleInstrumentBox;
import synth.StdNote;
import synth.StdNoteCore;
import synth.Track;
import synth.WaveInstrument;
import tools.Converter;
import tools.Player;
import tools.SimplePlayer;
import tools.SimpleSoundSourceConverter;

public class Music {

	public static void main(String[] args) {
		int note, oct;
		int[] t = {50, 10, 25};
		Instrument instr = new WaveInstrument(t);
		InstrumentBox box = new SingleInstrumentBox(2);
		box.attachInstrument(instr);
		Track track = new SimpleTrack(2);
		for (int i=0; i<100; i++) {
			String noteStr;
			note = (int)(Math.random()*7);
			oct = (int) (Math.random()*4) + 3;
			switch (note) {
			case 0: noteStr = "C"; break;
			case 1: noteStr = "D"; break;
			case 2: noteStr = "E"; break;
			case 3: noteStr = "F"; break;
			case 4: noteStr = "G"; break;
			case 5: noteStr = "A"; break;
			case 6: noteStr = "B"; break;
			default: noteStr = "C";
			}
			Note no = new StdNote(StdNoteCore.valueOf(noteStr + oct)); 
			no.setVolume((int)(Math.random() * Short.MAX_VALUE)/2 + 100);
			box.removeAllSoundEffects();
			box.attachSoundEffect(new LinearFadeOutEffect(2, 440, (int)(Math.random() * 44100) + 4410));
			box.play(no);
			track.insertSound(box, no, i*12525, 88200);
		}
		Converter c = new SimpleSoundSourceConverter(track);
		Player pl = new SimplePlayer();
		pl.setSource(c);
		pl.play();
	}

}
