package testing;

import guiEdge.WaveTableView;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import synth.Instrument;
import synth.StdNote;
//import synth.SimpleTrack;
//import synth.SingleInstrumentBox;
//import synth.SoundSource;
//import tinyEdge.CloneableSourceFactory;
//import tinyEdge.SoundSourceFactory;
//import synth.StdNote;
import synth.WaveInstrument;
import tinyEdge.InstrumentBank;

public class NoSoundTest {
	public static void main(String[] args) {
		// SoundSource s = new SingleInstrumentBox(new WaveInstrument(), new
		// StdNote("a4"), 2);
		// SoundSource s = new SimpleTrack(2);
		// SoundSourceFactory fac = new CloneableSourceFactory(s);
		// s = fac.getSoundSourceInstance();
		// try {
		// s = fac.getSoundSourceInstance("c4");
		// } catch (Exception e) {
		// System.out.println("Expected exception caught");
		// e.printStackTrace();
		// }
		// System.out.println(s.toString());
		System.out.println(new StdNote("a4").getFrequency());
		int[] h = { 10, 5 };
		short[] s = new short[4410];
		s[0] = 10;
		s[100] = -10;
		s[1000] = 5;
		s[1500] = -5;
		Instrument in = new WaveInstrument(h);
		XMLEncoder encoder = null;
		InstrumentBank bank = new InstrumentBank();
		bank.addInstrument(in);
		try {
			encoder = new XMLEncoder(System.out);
			in.registerPersistenceDelegate(encoder);
			encoder.writeObject(in);
			in = new WaveInstrument(s);
			bank.addInstrument(in);
			encoder.writeObject(in);
			in = new WaveInstrument();
			bank.addInstrument(in);
			in =in.clone();
			bank.addInstrument(in);
			encoder.writeObject(in);
			encoder.writeObject(bank);
		} catch (Exception e) {
			System.err.println("Exception caught");
			e.printStackTrace();
		} finally {
			if (encoder != null) {
				encoder.close();
			}
		}
		/*
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new FileInputStream("test_static.xml"));
			System.out.println(decoder.readObject().toString());
			System.out.println(decoder.readObject().toString());
			System.out.println(decoder.readObject().toString());
		} catch (Exception e) {
			System.err.println("Decoder exception caught!");
			e.printStackTrace();
		} finally {
			if (decoder != null)
				decoder.close();
		}
		/*
		 * try { Field f = WaveInstrument.class.getDeclaredField("waveTable");
		 * f.setAccessible(true); Object o = f.get(in);
		 * System.out.println(Array.getShort(o, 100)); short[] oAsS =
		 * (short[])o; System.out.println("" + oAsS.length); /*for (int i=0;
		 * i<oAsS.length; i++) { System.out.println(oAsS[i]); }
		 * System.out.println(o.toString()); /* short[] wt = (short[])
		 * f.get(in); for (short v : wt) { System.out.print(" " + (int)v); }
		 * 
		 * } catch (Exception e) { System.err.println("Could not get field");
		 * e.printStackTrace(); }
		 */
		/*
		 * Instrument dec = null; /*try {/* ObjectOutputStream oos = new
		 * ObjectOutputStream(new BufferedOutputStream(new
		 * FileOutputStream("test.ser"))); oos.writeObject(in); oos.close();
		 * 
		 * ObjectInputStream ois = new ObjectInputStream(new
		 * FileInputStream("test.ser")); dec = (Instrument) ois.readObject();
		 * ois.close();
		 */
		/*
		 * XMLEncoder en = new XMLEncoder(new FileOutputStream("test.xml"));
		 * en.setPersistenceDelegate(WaveInstrument.class, new
		 * DefaultPersistenceDelegate() { protected Expression
		 * instantiate(Object oldInstance, Encoder out) { return new
		 * Expression(oldInstance, oldInstance .getClass(), "new", new Object[]
		 * { h }); } }); en.writeObject(in); en.close();
		 * 
		 * XMLDecoder de = new XMLDecoder(new BufferedInputStream( new
		 * FileInputStream("test.xml"))); dec = (Instrument) de.readObject();
		 * de.close();
		 * 
		 * } catch (Exception e) { System.err.println("Exception caught");
		 * e.printStackTrace(); } System.out.println(dec.toString());
		 */
	}

}
