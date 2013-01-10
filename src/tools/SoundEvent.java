/**
 * General TinySynth Heading.
 * We live in 16 bit signed 44100 Hz sound. 
 */
package tools;

import java.util.EventObject;

/**
 * This is a <code>SoundEvent</code> for use in the <code>TinySynth</code>
 * environment. It is described by either a textual description, an integer
 * event mark or both plus an <code>Integer</code> eventId.
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
	 * not described by event marks. This value is 0.
	 */
	public static final int ILLEGAL_EVENT_MARK = 0;

	private final String description;
	private final int eventMark;
	private final Integer eventId;

	/**
	 * Creates a new <code>SoundEvent</code> bound by an integer event mark. The
	 * event mark must not be 0.
	 * 
	 * @param source
	 *            source of this <code>SoundEvent</code>
	 * @param eventMark
	 *            a mark defining this <code>SoundEvent</code>. Must not be 0.
	 * @throws IllegalArgumentException
	 *             if <code>eventMark == ILLEGAL_EVENT_MARK</code>, that is if
	 *             event mark is 0.
	 */
	public SoundEvent(Object source, int eventMark) {
		super(source);
		if (eventMark == ILLEGAL_EVENT_MARK)
			throw new IllegalArgumentException("EventMark must be legal mark!");
		description = null;
		this.eventMark = eventMark;
		eventId = null;
	}

	/**
	 * Creates a new <code>SoundEvent</code> described textually.
	 * 
	 * @param source
	 *            source of this <code>SoundEvent</code>
	 * @param description
	 *            a description defining this <code>SoundEvent</code>
	 * @throws NullPointerException
	 *             if <code>description == null</code>.
	 */
	public SoundEvent(Object source, String description) {
		super(source);
		if (description == null)
			throw new NullPointerException("Description must be set!");
		this.description = description;
		this.eventMark = ILLEGAL_EVENT_MARK;
		eventId = null;
	}

	/**
	 * Creates a new <code>SoundEvent</code> described textually and bound by an
	 * integer event mark. The event mark must not be 0.
	 * 
	 * @param source
	 *            source of this <code>SoundEvent</code>
	 * @param description
	 *            a description defining this <code>SoundEvent</code>
	 * @param eventMark
	 *            a mark defining this <code>SoundEvent</code>. Must not be 0.
	 * @param eventId
	 *            an identifier for this <code>SoundEvent</code>. If not used,
	 *            set this to <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>eventMark == ILLEGAL_EVENT_MARK</code>, that is if
	 *             event mark is 0.
	 * @throws NullPointerException
	 *             if <code>description == null</code>.
	 */
	public SoundEvent(Object source, String description, int eventMark,
			Integer eventId) {
		super(source);
		if (description == null)
			throw new NullPointerException("Description must be set!");
		if (eventMark == ILLEGAL_EVENT_MARK)
			throw new IllegalArgumentException("EventMark must be legal mark!");
		this.description = description;
		this.eventMark = eventMark;
		this.eventId = eventId;
	}

	/**
	 * Gets description of this <code>SoundEvent</code> if description is set.
	 * 
	 * @return description if set. <code>null</code> otherwise.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the event mark of this <code>SoundEvent</code>. If this
	 * <code>SoundEvent</code> is not bound by an event mark, the value returned
	 * will be <code>ILLEGAL_EVENT_MARK</code>.
	 * 
	 * @return event mark if this is bound by one.
	 *         <code>ILLEGAL_EVENT_MARK</code> otherwise.
	 */
	public int getEventMark() {
		return eventMark;
	}

	/**
	 * Gets the identifier for this <code>SoundEvent</code> if set.
	 * 
	 * @return Identifier if set. <code>null</code> otherwise.
	 */
	public Integer getEventId() {
		return eventId;
	}
}
