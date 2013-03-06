package synth;

import java.beans.PersistenceDelegate;

public class NoSoundSoundSource extends AbstractSoundSource {
	// TODO ALSO create listening sound effect

	//@Override
	public PersistenceDelegate getCompatiblePersistenceDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public short[] next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChannelCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
