/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.beans.PersistenceDelegate;

/**
 * The <code>StoreableSynthPart</code> interface is an interface designed for
 * recording <code>TinySynth</code> objects in XML format.
 * 
 * @author Sitron Te
 * 
 */
public interface StoreableSynthPart {
	/**
	 * Gets a <code>PersistenceDelegate</code> that can convert the current
	 * class to XML format.
	 * 
	 * @return a <code>PersistenceDelegate</code> compatible with this class.
	 */
	public PersistenceDelegate getCompatiblePersistenceDelegate();
}
