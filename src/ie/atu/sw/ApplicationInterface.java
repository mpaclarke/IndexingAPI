package ie.atu.sw;

import java.util.*;
import static java.lang.System.*;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        ApplicationInterface Performs the following functions: <br>
 *        * Displays the menu interface</br>
 *        <br>
 *        * Collects data from the user and stores it as instance variables</br>
 *        <br>
 *        * Catches exceptions from all classes</br>
 *        <br>
 *        * Executes and closes the application</br>
 * 
 *        Additionally, ApplicationInterface implements Indexable and Utils.
 * 
 * @See Indexable
 * @See Utils
 */
public class ApplicationInterface implements Indexable, Utils {
	private boolean keepMenuRunning;
	private Scanner userInput;
	private String commonWordsDir;
	private String dictionarysDir;
	private String textFileDir;
	private String outputDir;

	/**
	 * Null constructor that initializes the private variables, keepMenuRunning and
	 * userInput;
	 */
	public ApplicationInterface() {
		this.keepMenuRunning = true;
		this.userInput = new Scanner(System.in);
	}

	/**
	 * Starts the do-loop that contains the menu items and calls the key methods in
	 * ApplicationInterface
	 */
	public void start() {
		do {
			new MenuOptions().showMainMenuOptions();
			var choice = userInput.nextInt();

			switch (choice) {
			case 1 -> getTextFileDir();
			case 2 -> getDictionarysDir();
			case 3 -> getCommonWordsDir();
			case 4 -> getOutputFile();
			case 5 -> execute();
			case 6 -> {
				System.out.println(ConsoleColour.CYAN + "[INFO] The application is shutting down. Please wait...");
				resetApp();
				keepMenuRunning = false; // Sets the instance variable that closes the do loop
			}
			default -> {
				System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Invalid input...");
				System.out.println(
						ConsoleColour.CYAN + "[INFO] Please select one of the options listed 1 to 6 in the Main Menu.");
			}
			}

		} while (keepMenuRunning);
		System.out.println(ConsoleColour.CYAN + "[INFO] The application has shutdown.");
	}

	/**
	 * Gets the text file directory from the user.
	 */
	private void getTextFileDir() {
		out.println(ConsoleColour.CYAN + "[INFO] Enter the Text's file path and file extension:>");
		this.textFileDir = userInput.next();
		out.println(ConsoleColour.CYAN + "[INFO] The application will process " + textFileDir);
	}

	/**
	 * Gets the dictionary directory from the user.
	 */
	private void getDictionarysDir() {
		out.println(ConsoleColour.CYAN + "[INFO] Enter the Dictionary's file path and file extension:>");
		this.dictionarysDir = userInput.next();
		out.println(ConsoleColour.CYAN + "[INFO] The application will process " + dictionarysDir);
	}

	/**
	 * Gets the common words directory from the user.
	 */
	private void getCommonWordsDir() {
		out.println(ConsoleColour.CYAN + "[INFO] Enter the Common Words file path and file extension:>");
		this.commonWordsDir = userInput.next();
		out.println(ConsoleColour.CYAN + "[INFO] The application will process " + commonWordsDir);
	}

	/**
	 * Gets the output file directory from the user.
	 */
	private void getOutputFile() {
		out.println(ConsoleColour.CYAN + "[INFO] Enter the Output's file path and file extension:>");
		this.outputDir = userInput.next();
		out.println(ConsoleColour.CYAN + "[INFO] The application will process " + outputDir);
	}

	/**
	 * Executes the key functions of this application. @See EBookIndexer
	 */
	private void execute() {
		try {
			checkInputFeilds(textFileDir, dictionarysDir, commonWordsDir, outputDir);

			EBookIndexer ebi = new EBookIndexer(commonWordsDir, dictionarysDir, textFileDir);
			ebi.start();
			printOrder(ebi.geteBookIndex());

			printStats(ebi.geteBookIndex(), 5, true);
			printStats(ebi.geteBookIndex(), 5, false);

			out.println("[INFO] Returning to the Main Menu...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes in the map generated by EBookIndexer and saves it either in forward or
	 * reverse order depending on the user's choice. Implements methods in Indexable
	 * and Utils
	 * 
	 * @See Indexable
	 * @See Utils
	 * 
	 * @param map the index map generated by EBookIndexer
	 * @throws Exception
	 */
	private void printOrder(Map<String, WordDetail> map) throws Exception {
		out.println(
				"[INFO] Do you want the index to be printed in reverse order? \n[INFO] Enter Y for Yes or N for No>");
		String choice = userInput.next();
		if (choice.equalsIgnoreCase("y")) {
			save(outputDir, reverseOrder(map));

		} else {
			save(outputDir, map);
		}
		out.println("[INFO] File saved to " + outputDir);
	}

	/**
	 * Prints the n number of most or least frequent words passed to map.
	 * 
	 * @param map:    index map generated by EBookIndexer
	 * @param count:  number of words printed in the list
	 * @param choice: true equals MOST frequent: false equals LEAST frequent
	 * @throws Exception
	 */
	private void printStats(Map<String, WordDetail> map, int count, boolean choice) throws Exception {
		out.println("**********************   STATS   ***************************");
		if (choice == true) {
			out.println("Top five MOST frequent words:");
			frequentWords(map, count, true);
			out.println();
		} else {
			out.println("Top five LEAST frequent words");
			frequentWords(map, count, false);
			out.println();
		}
	}

	/**
	 * Resets the key instance variable associated with user input
	 */
	private void resetApp() {
		out.println("[INFO] Resetting the application...");
		this.textFileDir = null;
		this.dictionarysDir = null;
		this.commonWordsDir = null;
		this.outputDir = null;
	}

	/**
	 * 
	 * @author Michael Clarke
	 * @version 1.0
	 * @since JavaSE-19
	 * 
	 *        MenuOptions contains the menu options available to the user.
	 */
	private class MenuOptions {

		/**
		 * Prints the main menu options available to the user.
		 */
		private void showMainMenuOptions() {
			out.println(ConsoleColour.CYAN);
			out.println("************************************************************");
			out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
			out.println("*                                                          *");
			out.println("*              Virtual Threaded Text Indexer               *");
			out.println("*                                                          *");
			out.println("*                        Main Menu                         *");
			out.println("************************************************************");
			out.println("(1) Specify Text File");
			out.println("(2) Configure Dictionary");
			out.println("(3) Configure Common Words");
			out.println("(4) Specify Output File");
			out.println("(5) Execute");
			out.println("(6) Quit");

			out.print(ConsoleColour.CYAN_BOLD_BRIGHT);
			out.print("Select Option [1-6]>");
			out.println();
		}
	}
}