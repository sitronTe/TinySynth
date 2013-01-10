/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package synth;

/**
 * Exception for use in the <code>TinySynth</code> environment. Used when
 * something tries to bind a factory when seat is taken.
 * 
 * @author Sitron Te
 * 
 */
public class AlreadyBoundFactoryException extends RuntimeException {

	/**
	 * Version number of this <code>Exception</code>.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new <code>AlreadyBoundFactoryException</code>.
	 */
	public AlreadyBoundFactoryException() {
	}

	/**
	 * Creates a new <code>AlreadyBoundFactoryException</code> with description.
	 * @param arg0
	 */
	public AlreadyBoundFactoryException(String arg0) {
		super(arg0);
	}
}
