package testing;

import synth.SimpleTrack;
//import synth.SingleInstrumentBox;
import synth.SoundSource;
//import synth.StdNote;
//import synth.WaveInstrument;
import tools.SoundSourceFactory;
import tools.CloneableSourceFactory;

public class NoSoundTest {
	public static void main(String[] args) {
		//SoundSource s = new SingleInstrumentBox(new WaveInstrument(), new StdNote("a4"), 2);
		SoundSource s = new SimpleTrack(2);
		SoundSourceFactory fac = new CloneableSourceFactory(s);
		s = fac.getSoundSourceInstance();
		try {
			s = fac.getSoundSourceInstance("c4");
		} catch (Exception e) {
			System.out.println("Expected exception caught");
			e.printStackTrace();
		}
		System.out.println(s.toString());
	}

}
