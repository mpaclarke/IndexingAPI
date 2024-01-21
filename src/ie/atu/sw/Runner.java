package ie.atu.sw;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        This is a concrete class used to instantiate the app.
 */
public class Runner {

	/**
	 * This method starts the application by calling the start methods in
	 * ApplicationInterface.
	 * 
	 * @param args: an array of String arguments
	 * 
	 *              @See ApplicationInterface
	 */
	public static void main(String[] args) {
		new ApplicationInterface().start();
	}
}