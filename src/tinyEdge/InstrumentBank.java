package tinyEdge;

import java.util.HashMap;
import java.util.Set;

import synth.Instrument;

public class InstrumentBank {
	private int place = 0;
	private HashMap<Integer, Instrument> bank = new HashMap<>();

	public InstrumentBank() {
		// Default constructor
	}

	public int addInstrument(Instrument instr) {
		if (bank.containsValue(instr)) {
			return getKey(instr);
		}
		if (place == Integer.MAX_VALUE)
			throw new IndexOutOfBoundsException("This InstrumentBank is full!");
		place++;
		while (bank.containsKey(place)) {
			if (place == Integer.MAX_VALUE)
				throw new IndexOutOfBoundsException(
						"This InstrumentBank is full!");
			place++;
		}
		bank.put(place, instr);
		return place;
	}

	/**
	 * Return the key value for this instrument, if contained within this
	 * <code>InstrumentBank</code>. Otherwise -1. <br>
	 * This method may be very costly to perform. Use it with caution.
	 * 
	 * @param instr
	 *            the <code>Instrument</code> to look for.
	 * @return the key value for this <code>Instrument</code> if found. -1
	 *         otherwise.
	 */
	public int getKey(Instrument instr) {
		Set<Integer> k = bank.keySet();
		for (Integer i : k)
			if (bank.get(i).equals(instr))
				return i;
		return -1;
	}

	public void setInstrument(int key, Instrument instr) {
		bank.put(key, instr);
	}

	public Instrument getInstrument(int key) {
		return bank.get(key);
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public HashMap<Integer, Instrument> getBank() {
		return bank;
	}

	public void setBank(HashMap<Integer, Instrument> bank) {
		this.bank = bank;
	}

}