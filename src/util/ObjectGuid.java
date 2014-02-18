package util;

/**
 * Handles the assignment of GUIDs (globally unique identifiers).
 * (Not used atm, but this class might/should be used in the future)
 * @author Niklas
 */
public class ObjectGuid {

	private ObjectGuid() {}
	
	private static int nextId = 0;
	/**
	 * Get the next available object Id.
	 */
	public static int nextId() {
		return nextId++;
	}
	
}
