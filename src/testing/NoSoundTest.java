package testing;

import tools.SoundEvent;

public class NoSoundTest {
	public static void main(String[] args) {
		SoundEvent s = new SoundEvent(new Object(), "koo");
		System.out.println(s.toString());
	}

}
