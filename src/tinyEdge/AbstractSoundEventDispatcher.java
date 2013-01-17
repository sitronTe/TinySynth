/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tinyEdge;

import java.util.ArrayList;
import java.util.List;


/**
 * An <code>AbstractSoundEventDispatcher</code> is an abstract class to hold all
 * {@link SoundListener}s that needs to be notified about {@link SoundEvent}s
 * generated from the subclass of this class.
 * 
 * @author Sitron Te
 * 
 */
public abstract class AbstractSoundEventDispatcher {
	private List<SoundListener> listeners = new ArrayList<SoundListener>();

	/**
	 * Adds a <code>SoundListener</code> to be notified about
	 * <code>SoundEvent</code>s.
	 * 
	 * @param s
	 *            the <code>SoundListener</code> to add
	 */
	public synchronized void addSoundListener(SoundListener s) {
		listeners.add(s);
	}

	/**
	 * Removes this <code>SoundListener</code> from the list of
	 * <code>SoundListener</code>s to be notified about <code>SoundEvent</code>
	 * s.
	 * 
	 * @param s
	 *            the <code>SoundEvent</code> to tell about
	 */
	public synchronized void removeSoundListener(SoundListener s) {
		listeners.remove(s);
	}

	/**
	 * Clears the list of <code>SoundListener</code>s to notify.
	 */
	public synchronized void removeAllSoundListeners() {
		listeners.clear();
	}

	/**
	 * Notifies all <code>SoundListener</code>s connected to this.
	 * 
	 * @param s
	 *            the <code>SoundEvent</code> to notify all
	 *            <code>SoundListener</code>s about.
	 */
	protected synchronized void fireSoundEvent(SoundEvent s) {
		for (SoundListener sl : listeners)
			sl.fireSound(s);
	}
}
