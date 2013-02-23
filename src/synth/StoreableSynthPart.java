/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound.
 */
package synth;

import java.beans.XMLEncoder;

/**
 * The <code>StoreableSynthPart</code> interface is an interface designed for
 * recording <code>TinySynth</code> objects in XML format.
 * 
 * @author Sitron Te
 * 
 */
public interface StoreableSynthPart {
	/**
	 * Registers a <code>PersistenceDelegate</code> with this
	 * <code>XMLEncoder</code> so it can encode this class in XML format. This
	 * method should also be called recursively on all pieces this
	 * <code>StoreableSynthPart</code> may contain.
	 * 
	 * @param encoder
	 *            the encoder to register the <code>PersistenceDelegate</code>
	 *            to.
	 */
	public void registerPersistenceDelegate(XMLEncoder encoder);
}
