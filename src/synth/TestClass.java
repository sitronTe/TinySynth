package synth;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class TestClass {
	// TODO Test SimpleTrack

	public static void main(String[] args) {
		Note note;
		int[] koo = { 10, 5, 0, 2, 0, 1 };
		Instrument instr = new WaveInstrument(koo);
		InstrumentBox keeper = new SingleInstrumentBox(instr, 2);
		Track track= new SimpleTrack(2);
		Converter ready = new SimpleSoundSourceConverter(track);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 0*44, 450*44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 500*44, 450*44);
		note = new StdNote("f4");
		track.insertSound(keeper, note, 1000*44, 450*44);
		note = new StdNote("g4");
		track.insertSound(keeper, note, 1500*44, 450*44);
		note = new StdNote("g4");
		track.insertSound(keeper, note, 2000*44, 450*44);
		note = new StdNote("f4");
		track.insertSound(keeper, note, 2500*44, 450*44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 3000*44, 450*44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 3500*44, 450*44);
		note = new StdNote("c4");
		track.insertSound(keeper, note, 4000*44, 450*44);
		note = new StdNote("c4");
		track.insertSound(keeper, note, 4500*44, 450*44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 5000*44, 450*44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 5500*44, 450*44);
		note = new StdNote("e4");
		track.insertSound(keeper, note, 6000*44, 450*44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 6500*44, 450*44);
		note = new StdNote("d4");
		track.insertSound(keeper, note, 7000*44, 450*44);

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
				long temp =System.currentTimeMillis();
				while (track.hasNext()) {
					sound = ready.next(100);
					line.write(sound, 0, sound.length);
				}
				line.drain();
				temp=System.currentTimeMillis() - temp;
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

	public static byte[] translateShortToBytes(short[] shortList,
			boolean bigEndian) {
		if (shortList != null) {
			int i;
			byte[] utMat = new byte[2 * shortList.length];
			for (i = 0; i < shortList.length; i++) {
				int placeLarge, placeSmall;
				placeLarge = bigEndian ? 2 * i : (2 * i) + 1;
				placeSmall = bigEndian ? (2 * i) + 1 : 2 * i;
				utMat[placeLarge] = (byte) (shortList[i] >>> 8); // MSB
				utMat[placeSmall] = (byte) (shortList[i] & 0x00FF); // LSB
			}
			return utMat;
		}
		return null;
	}
/*
	private class testNote implements Note {
		private double freq = 440;
		private int vol = Short.MAX_VALUE / 16;

		@Override
		public double getFrequency() {
			return freq;
		}

		@Override
		public int getSampleCount44100Hz() {
			return 44100 / (int) freq;
		}

		@Override
		public int getVolume() {
			return vol;
		}

		@Override
		public void setVolume(int volume) {
			System.out.println("Ikke støttet. Inget behov.");
		}

		@Override
		public int getLength() {
			System.out.println("5 sekunder spilletid");
			return 5000;
		}

		@Override
		public int getLengthSampleCount44100Hz() {
			System.out.println("Ikke nøyaktig lagd.");
			return 5 * 44100;
		}

		@Override
		public void setLength(int length) {
			System.out.println("Ikke støttet. Inget behov.");

		}

	}
*/
}
