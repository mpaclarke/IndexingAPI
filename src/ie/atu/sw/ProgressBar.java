package ie.atu.sw;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        Adapted from Healy(2022). This <i>interface</i> runs a method that prints a
 *        progress bar.
 */
public interface ProgressBar {

	/**
	 * Prints the progress bar.
	 * 
	 * @param index: number that increases with progress.
	 * @param total: total number (or size) of the items to be iterated over. 
	 */
	public default void printProgress(int index, int total) {
		if (index > total)
			return; // Out of range
		int size = 50; // Must be less than console width
		char done = '█'; // Change to whatever you like.
		char todo = '░'; // Change to whatever you like.

		// Compute basic metrics for the meter
		int complete = (100 * index) / total;
		int completeLen = size * complete / 100;

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		// BIG O: O(n)
		for (int i = 0; i < size; i++) {
			sb.append((i < completeLen) ? done : todo);
		}

		/*
		 * The line feed escape character "\r" returns the cursor to the start of the
		 * current line. Calling print(...) overwrites the existing line and creates the
		 * illusion of an animation.
		 */
		System.out.print("\r" + sb + "] " + complete + "%");

		// Once the meter reaches its max, move to a new line.
		if (done == total)
			System.out.println("\n");
	}
}
