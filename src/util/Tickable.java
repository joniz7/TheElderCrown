package util;

import java.io.Serializable;

public interface Tickable extends Copyable, Serializable {

	public void tick();
	
}
