package util;

/**
 * Something that can be copied. (Cloneable is broken)
 *  
 * @author Niklas
 */
public interface Copyable {

	/**
	 * @return a deep copy of this object
	 */
	public Copyable copy();
	
}
