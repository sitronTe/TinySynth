/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tools;

import java.util.EventObject;

/**
 * This will become the standard for <code>SoundEvent</code>s. It is however not
 * yet designed, so it will be filled in a while.
 * 
 * @author Sitron Te
 * 
 */
public class SoundEvent extends EventObject {

	/**
	 * Version of this <code>SoundEvent</code>.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A <code>int</code> that is reserved for <code>SoundEvent</code>s that are
	 * not described by event marks.
	 */
	public static final int ILLEGAL_EVENT_MARK = 0;

	private final String description;
	private final int eventMark;

	/**
	 * 
	 * @param source
	 * @param eventMark
	 * @throws IllegalArgumentException
	 */
	public SoundEvent(Object source, int eventMark) {
		super(source);
		if (eventMark == ILLEGAL_EVENT_MARK)
			throw new IllegalArgumentException("EventMark must be legal mark!");
		description = null;
		this.eventMark = eventMark;
	}

	/**
	 * 
	 * @param source
	 * @param description
	 * @throws NullPointerException
	 */
	public SoundEvent(Object source, String description) {
		super(source);
		if (description == null)
			throw new NullPointerException("Description must be set!");
		this.description = description;
		this.eventMark = ILLEGAL_EVENT_MARK;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

}
