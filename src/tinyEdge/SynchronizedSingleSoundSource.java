package tinyEdge;

import java.util.NoSuchElementException;

import synth.AbstractSoundSource;
import synth.LinearFadeOutEffect;
import synth.SoundEffect;
import synth.SoundSource;

public class SynchronizedSingleSoundSource extends AbstractSoundSource {

	private enum State {
		PLAYING, STOPPED, STOPPING, CHANGING, EMPTY, KILLING, DEAD
	};

	private final int FADE_OUT_FRAME_COUNT = 2205;
	private final int channelCount;
	private SoundEffect fadeOut;
	private SoundSource currentSound;
	private SoundSource nextSound;
	private State state = State.EMPTY;
	private int fadeOutCountDown = 0;

	public SynchronizedSingleSoundSource() {
		this.channelCount = 1;
	}

	public SynchronizedSingleSoundSource(int channelCount) {
		if (channelCount < 1)
			throw new IllegalArgumentException(
					"Channel count must be larger than 0!");
		this.channelCount = channelCount;
	}

	/**
	 * Will return true until this <code>SynchronizedSingleSoundSource</code>
	 * has been killed.
	 * 
	 * @see SoundSource.hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.state != State.DEAD;
	}

	@Override
	public synchronized short[] next() {
		if (!hasNext())
			throw new NoSuchElementException(
					"Next may only be called while hasNext is true");
		fadeOutCountDown--;
		switch (state) {
		case STOPPING:
			if (fadeOutCountDown < 0) {
				state = State.STOPPED;
				if (nextSound != null) {
					currentSound = nextSound;
					nextSound = null;
				}
				return new short[channelCount];
			}
		case CHANGING:
			if (fadeOutCountDown < 0) {
				state = nextSound == null ? State.EMPTY : State.PLAYING;
				currentSound = nextSound;
				nextSound = null;
				return next();
			}
		case KILLING:
			if (fadeOutCountDown < 0) {
				state = State.DEAD;
				nextSound = null;
				return new short[channelCount];
			}
			if (currentSound.hasNext()) {
				short[] ret = currentSound.next();
				fadeOut.performEffect(ret);
				return ret;
			} else {
				return new short[channelCount];
			}
		case PLAYING:
			if (currentSound.hasNext())
				return currentSound.next();
		case STOPPED:
		case EMPTY:
			return new short[channelCount];
		default:
			return null;
		}
	}

	@Override
	public int getChannelCount() {
		return channelCount;
	}

	/**
	 * Resets this <code>SynchronizedSingleSoundSource</code> to an empty state.
	 * If it is currently playing it will allow a short fade out.
	 */
	@Override
	public synchronized void reset() {
		if (state == State.KILLING)
			state = State.STOPPING;
		setNewSoundSource(null);
	}

	/**
	 * Sets if this <code>SynchronizedSingleSoundSource</code> should play sound
	 * or not. If no <code>SoundSource is set, this will not do anything.
	 * 
	 * @param playing
	 *            if this should play(<code>true</code>) or stop(
	 *            <code>false</code>).
	 */
	public synchronized void setPlaying(boolean playing) {
		if (playing) {
			switch (state) {
			case STOPPED:
			case STOPPING:
				// TODO Add fade in?
				state = State.PLAYING;
				break;
			default:
				break;
			}
		} else {
			switch (state) {
			case PLAYING:
				resetFadeOut();
			case CHANGING:
				state = State.STOPPING;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Sees if there are still sound actually coming out of this
	 * <code>SynchronizedSingleSoundSource</code>.
	 * 
	 * @return true if it is playing, stopping or changing
	 *         <code>SoundSource</code>
	 */
	public boolean isPlaying() {
		return state == State.PLAYING || state == State.STOPPING
				|| state == State.CHANGING || state == State.KILLING;
	}

	/**
	 * Stops this <code>SynchronizedSingleSoundSource</code> from playing any
	 * more sound. Will allow the predetermined frames for fade out play. After
	 * those frames has finished playing, the <code>hasNext</code> will return
	 * <code>false</code>. May not be undone.
	 */
	public synchronized void killSound() {
		switch (state) {
		case PLAYING:
			resetFadeOut();
		case STOPPING:
		case CHANGING:
			state = State.KILLING;
			break;
		case EMPTY:
		case STOPPED:
			state = State.DEAD;
			break;
		default:
			break;
		}
	}

	/**
	 * Changes which <code>SoundSource</code> to play from. Will allow a short
	 * fade out before the new sound is played. May only be invoked before
	 * <code>killSound()</code> is invoked.
	 * 
	 * @param newSource
	 *            the new source to play from
	 * @throws IllegalStateException
	 *             if this <code>SynchronizedSingleSoundSource</code> is killed.
	 */
	public synchronized void changeSoundSource(SoundSource newSource) {
		if (state == State.DEAD || state == State.KILLING)
			throw new IllegalStateException(
					"SoundSource may not be changed after kill command is issued.");
		if (newSource != null && newSource.getChannelCount() != channelCount)
			throw new IllegalArgumentException(
					"New sound source must have same channel count as this.");
		setNewSoundSource(newSource);
	}

	/**
	 * Sets the new <code>SoundSource</code> to play from. Does not throw any
	 * exceptions.
	 * 
	 * @param newSource
	 *            the new <code>SoundSource</code>
	 */
	private synchronized void setNewSoundSource(SoundSource newSource) {
		switch (state) {
		case PLAYING:
			resetFadeOut();
			state = State.CHANGING;
		case STOPPING:
		case CHANGING:
			nextSound = newSource;
			break;
		case EMPTY:
		case STOPPED:
			state = newSource == null ? State.EMPTY : State.STOPPED;
			currentSound = newSource;
			break;
		default:
			break;
		}
	}

	/**
	 * Resets the fade out effect. Does not perform any legality tests.
	 */
	private synchronized void resetFadeOut() {
		fadeOutCountDown = FADE_OUT_FRAME_COUNT;
		if (fadeOut == null)
			fadeOut = new LinearFadeOutEffect(channelCount, 0,
					FADE_OUT_FRAME_COUNT);
		else
			fadeOut.reset();
	}
}
