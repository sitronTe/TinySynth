package testing;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import synth.BalanceEffect;
import synth.FadeInEffect;
import synth.FadeOutEffect;
import synth.Instrument;
import synth.InstrumentBox;
import synth.Note;
import synth.SimpleTrack;
import synth.SingleInstrumentBox;
import synth.SoundEffect;
import synth.StdNote;
import synth.Track;
import synth.WaveInstrument;
import tools.Converter;
import tools.SimpleSoundSourceConverter;

public class TestClass {

	public static void main(String[] args) {
		// Clicks in sounds seems to emerge from sounds being cut short before
		// they are muted
		Note note;
		int[] koo = { 15, 10, 1, 0 };
		int[] kooo = { 10, 0, 0, 1 };
		Instrument instr = new WaveInstrument(koo);
		Instrument instru = new WaveInstrument(kooo);

		BalanceEffect bal = new BalanceEffect(2);
		SoundEffect fIn = new FadeInEffect(2, 4410);
		SoundEffect fOut = new FadeOutEffect(2, 4450, 22050);

		bal.setVolume(0, Short.MAX_VALUE);
		bal.setVolume(1, 0);

		InstrumentBox keeper = new SingleInstrumentBox(instr, 2);
		InstrumentBox keeper2 = new SingleInstrumentBox(instru, 2);
		keeper.attachSoundEffect(fIn);
		keeper.attachSoundEffect(fOut);

		keeper2.attachSoundEffect(fIn);
		Track track = new SimpleTrack(2);
		Converter ready = new SimpleSoundSourceConverter(track);

		note = new StdNote("e4");
		note.setLength(400);
		track.insertSound(keeper, note, 0 * 44, 450 * 44);
		note = new StdNote("e4");
		note.setVolume((Short.MAX_VALUE) / 2);
		note.setLength(1950);
		keeper2.attachSoundEffect(bal);
		track.insertSound(keeper2, note, 500 * 44, 88200);
		keeper2.removeSoundEffect(bal);
		bal.setVolume(0, 0);
		bal.setVolume(1, Short.MAX_VALUE);
		note = new StdNote("f4");
		note.setVolume(Short.MAX_VALUE / 5);
		track.insertSound(keeper, note, 1000 * 44, 450 * 44);
		note = new StdNote("g4");
		note.setVolume((Short.MAX_VALUE) / 2);
		keeper2.attachSoundEffect(bal);
		track.insertSound(keeper2, note, 1500 * 44, 450 * 44);
		keeper2.removeSoundEffect(bal);
		note = new StdNote("g4");
		note.setVolume((Short.MAX_VALUE * 1) / 4);
		track.insertSound(keeper2, note, 2000 * 44, 450 * 44);
		note = new StdNote("f4");
		note.setVolume((Short.MAX_VALUE) / 2);
		track.insertSound(keeper2, note, 2500 * 44, 450 * 44);
		note = new StdNote("e4");
		note.setVolume((Short.MAX_VALUE * 2) / 4);
		track.insertSound(keeper, note, 3000 * 44, 450 * 44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 3500 * 44, 450 * 44);
		note = new StdNote("c4");
		track.insertSound(keeper, note, 4000 * 44, 450 * 44);
		note = new StdNote("c4");
		note.setVolume((Short.MAX_VALUE * 3) / 8);
		track.insertSound(keeper, note, 4500 * 44, 450 * 44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 5000 * 44, 450 * 44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 5500 * 44, 450 * 44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 6000 * 44, 450 * 44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 6500 * 44, 450 * 44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 7000 * 44, 450 * 44);

		int bufferstorr = 44100;
		AudioFormat audioformat = ready.getAudioFormat();
		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioformat);
		byte[] sound;

		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Dette funker ikke");
		} else {
			try {
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(audioformat, bufferstorr);
				line.start();
				long temp = System.currentTimeMillis();
				while (track.hasNext()) {
					sound = ready.next(100);
					line.write(sound, 0, sound.length);
				}
				line.drain();
				temp = System.currentTimeMillis() - temp;
				System.out.println(temp);
			} catch (LineUnavailableException ex) {
				System.out.println("Uff...");
			}

			if (line != null) {
				line.stop();
				line.close();
				line = null;
			}
		}

		System.exit(0);
	}
	/*
	 * public static byte[] translateShortToBytes(short[] shortList, boolean
	 * bigEndian) { if (shortList != null) { int i; byte[] utMat = new byte[2 *
	 * shortList.length]; for (i = 0; i < shortList.length; i++) { int
	 * placeLarge, placeSmall; placeLarge = bigEndian ? 2 * i : (2 * i) + 1;
	 * placeSmall = bigEndian ? (2 * i) + 1 : 2 * i; utMat[placeLarge] = (byte)
	 * (shortList[i] >>> 8); // MSB utMat[placeSmall] = (byte) (shortList[i] &
	 * 0x00FF); // LSB } return utMat; } return null; }
	 */
}
