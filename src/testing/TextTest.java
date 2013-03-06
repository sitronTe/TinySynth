package testing;

import synth.StdNoteCore;

public class TextTest {
	public static void main(String[] args) {
		for (StdNoteCore c: StdNoteCore.values())
			System.out.println(c + " freq: " + c.getFrequency());
	}
}
