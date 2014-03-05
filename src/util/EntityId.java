package util;

/**
 * Handles the assignment of GUIDs (globally unique identifiers).
 * Use nextId() to get the next available Id. 
 * @author Niklas
 */
public class EntityId {

	private EntityId() {}
	
	private static int nextId = 0;
	/**
	 * Get the next available object Id.
	 */
	public static int nextId() {
		return nextId++;
	}
	
}
