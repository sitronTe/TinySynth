/**
 * 
 */
package synth;

/**
 * @author Inge
 * 
 */
public abstract class AbstractMix extends AbstractSoundSource implements Mix {

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#isFull()
	 */
	@Override
	public abstract boolean isFull();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#getMasterVolume()
	 */
	@Override
	public int getMasterVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#setMasterVolume(int)
	 */
	@Override
	public void setMasterVolume(int volume) {
		isVolumeLegal(volume);
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#getChannelVolume(int, int)
	 */
	@Override
	public int getChannelVolume(int inputChannel, int outputChannel) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#setChannelVolume(int, int, int)
	 */
	@Override
	public void setChannelVolume(int inputChannel, int outputChannel, int volume) {
		isVolumeLegal(volume);
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#attachSoundEffect(synth.SoundEffect, int)
	 */
	@Override
	public int attachSoundEffect(SoundEffect effect, int channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#removeSoundEffect(synth.SoundEffect, int)
	 */
	@Override
	public void removeSoundEffect(SoundEffect effect, int channel) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.Mix#removeSoundEffect(int, int)
	 */
	@Override
	public void removeSoundEffect(int effectNumber, int channel) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#hasNext()
	 */
	@Override
	public abstract boolean hasNext();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#next()
	 */
	@Override
	public abstract short[] next();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#getChannelCount()
	 */
	@Override
	public abstract int getChannelCount();

	/*
	 * (non-Javadoc)
	 * 
	 * @see synth.AbstractSoundSource#reset()
	 */
	@Override
	public abstract void reset();

	private void isVolumeLegal(int volume) {
		if (volume < 0 || volume > Short.MAX_VALUE)
			throw new IllegalArgumentException("Volume must be in range 0 to "
					+ Short.MAX_VALUE + ", but was " + volume);
	}

}
