package testing;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import synth.BalanceEffect;
import synth.LinearFadeInEffect;
import synth.LinearFadeOutEffect;
import synth.Instrument;
import synth.InstrumentBox;
import synth.Note;
import synth.RecordedSource;
// import synth.SimpleSoundChannelMix;
import synth.SimpleSoundSourceMix;
import synth.SimpleTrack;
import synth.SingleInstrumentBox;
// import synth.SoundChannelMix;
import synth.SoundEffect;
import synth.SoundSourceMix;
import synth.StdNote;
import synth.Track;
import synth.WaveInstrument;
import tools.Converter;
import tools.SimpleSoundSourceConverter;

public class TestClass {

	public static void main(String[] args) {
		// TODO Write InstrumentBoxDisassembler
		// TODO Write TrackDissasembler
		// TODO Write javadoc for InstrumentBank.
		// TODO Make certain InstrumentBank is a good solution.
		// TODO Test BalanceEffect XML.
		// TODO Test LinearFadeInEffect XML.
		// TODO Test LinearFadeOutEffect XML.
		// TODO Make beans that can serialize and reconstruct all/most classes
		// TODO Add exponential sound change in fade out and in effects
		// TODO Check if InstrumentBox plays longer than note so effects will
		// function properly
		// TODO Make simple GUI
		// TODO Make stuff saveable so no hardcoded sounds are needed
		// TODO Work, work, work
		// TODO Additional testing of SimpleSoundSourceMix
		// TODO Additional testing of SimpleSoundChannelMix
		// TODO Additional testing of "thread safe" events
		Note note;
		int[] koo = { 15, 10, 1, 0 };
		int[] kooo = { 10, 0, 0, 1 };
		Instrument instr = new WaveInstrument(koo);
		Instrument instru = new WaveInstrument(kooo);
		RecordedSource temporary;
		/*
		 * SoundChannelMix mixer = new SimpleSoundChannelMix(2); FadeOutEffect e
		 * = new FadeOutEffect(1, 8820, 44100); Note ko = new StdNote("a3");
		 * ko.setLength(2500); ko.setVolume(Short.MAX_VALUE/2);
		 * System.out.println(ko.getLengthSampleCount44100Hz()); instr.play(ko);
		 * instru.play(ko); int in1 = mixer.attach(instr); int in2 =
		 * mixer.attach(instru); mixer.setChannelVolume(in1, 0,
		 * Short.MAX_VALUE/2); mixer.setChannelVolume(in1, 1, 0);
		 * mixer.setChannelVolume(in2, 1, Short.MAX_VALUE/2);
		 * mixer.setChannelVolume(in2, 0, Short.MAX_VALUE/10);
		 * mixer.attachSoundEffect(e, in1); e = new FadeOutEffect(1, 44100,
		 * 8820); mixer.attachSoundEffect(e, in2);
		 */

		BalanceEffect bal = new BalanceEffect(2);
		bal.setVolume(0, Short.MAX_VALUE);
		bal.setVolume(1, 0);

		InstrumentBox keeper = new SingleInstrumentBox(instr, 2);
		InstrumentBox keeper2 = new SingleInstrumentBox(instru, 2);

		Track track = null;
		SoundSourceMix mixer = new SimpleSoundSourceMix(2);

		for (int i = 0; i < 2; i++) {
			keeper.removeAllSoundEffects();
			keeper2.removeAllSoundEffects();
			SoundEffect fIn = new LinearFadeInEffect(2, (4410 * 2) / (i + 1));
			SoundEffect fOut = new LinearFadeOutEffect(2, (4450 * 2) / (i + 1),
					44100 / (2 * (i + 1)));
			keeper.attachSoundEffect(fIn);
			keeper.attachSoundEffect(fOut);
			keeper2.attachSoundEffect(fIn);
			track = new SimpleTrack(2);
			String oct = "" + (4 - (3 * i));

			note = new StdNote("e" + oct);
			note.setLength(400);
			track.insertSound(keeper, note, 0 * 44, 450 * 44);
			note = new StdNote("e" + oct);
			note.setVolume((Short.MAX_VALUE) / 2);
			note.setLength(1950);
			keeper2.attachSoundEffect(bal);
			track.insertSound(keeper2, note, 500 * 44, 88200);
			keeper2.removeSoundEffect(bal);
			bal.setVolume(0, 0);
			bal.setVolume(1, Short.MAX_VALUE);
			note = new StdNote("f" + oct);
			note.setVolume(Short.MAX_VALUE / 5);
			track.insertSound(keeper, note, 1000 * 44, 450 * 44);
			note = new StdNote("g" + oct);
			note.setVolume((Short.MAX_VALUE) / 2);
			keeper2.attachSoundEffect(bal);
			track.insertSound(keeper2, note, 1500 * 44, 450 * 44);
			keeper2.removeSoundEffect(bal);
			note = new StdNote("g" + oct);
			note.setVolume((Short.MAX_VALUE * 1) / 4);
			track.insertSound(keeper2, note, 2000 * 44, 450 * 44);
			note = new StdNote("f" + oct);
			note.setVolume((Short.MAX_VALUE) / 2);
			track.insertSound(keeper2, note, 2500 * 44, 450 * 44);
			note = new StdNote("e" + oct);
			note.setVolume((Short.MAX_VALUE * 2) / 4);
			track.insertSound(keeper, note, 3000 * 44, 450 * 44);
			note = new StdNote("d" + oct);
			track.insertSound(keeper, note, 3500 * 44, 450 * 44);
			note = new StdNote("c" + oct);
			track.insertSound(keeper, note, 4000 * 44, 450 * 44);
			note = new StdNote("c" + oct);
			note.setVolume((Short.MAX_VALUE * 3) / 8);
			track.insertSound(keeper, note, 4500 * 44, 450 * 44);
			note = new StdNote("d" + oct);
			track.insertSound(keeper, note, 5000 * 44, 450 * 44);
			note = new StdNote("e" + oct);
			track.insertSound(keeper, note, 5500 * 44, 450 * 44);
			note = new StdNote("e" + oct);
			track.insertSound(keeper, note, 6000 * 44, 450 * 44);
			note = new StdNote("d" + oct);
			track.insertSound(keeper, note, 6500 * 44, 450 * 44);
			note = new StdNote("d" + oct);
			track.insertSound(keeper, note, 7000 * 44, 450 * 44);

			int ch = mixer.attach(track);
			mixer.setChannelVolume(ch, -1, Short.MAX_VALUE / 4);
			System.out.println(ch);
		}

		temporary = new RecordedSource(mixer, 88200);
		temporary = temporary.clone();

		Converter ready = new SimpleSoundSourceConverter(temporary);

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
				while (temporary.hasNext()) {
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
