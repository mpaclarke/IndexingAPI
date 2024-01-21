package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        The <i>interface</i> Utils provides methods that save the map index
 *        and provide validation to ApplicationInterface.
 */
public interface Utils {

	/**
	 * Prints out the map in a formated way.
	 * 
	 * @param outputDir: an output directory.
	 * @param map:       any generic map.
	 * @throws Exception
	 */
	public default void save(String outputDir, Map<?, ?> map) throws Exception {
		System.out.println("[INFO] Saving file...");
		try (FileWriter fw = new FileWriter(new File(outputDir))) {
			fw.write(String.format("%-17S %S %n", "Word", "Detials"));
			for (Entry<?, ?> entry : map.entrySet()) {
				fw.write(String.format("%-17S %s %n", entry.getKey(), entry.getValue()));
			}
		}
	}

	/**
	 * Checks that all fields have been input in the ApplicationInterface.
	 * 
	 * @param textFileDir:    text-file directory
	 * @param dictionaryDir:  dictionary directory
	 * @param commonWordsDir: common words directory
	 * @param outputDir:      output directory
	 * @throws Exception
	 */
	public default void checkInputFeilds(String textFileDir, String dictionaryDir, String commonWordsDir,
			String outputDir) throws Exception {
		if (textFileDir == null) {
			System.out.println(
					ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The text file directory for option 1 is missing.");
		}
		if (dictionaryDir == null) {
			System.out.println(
					ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The dictionary directory for option 2 is missing.");
		}
		if (commonWordsDir == null) {
			System.out.println(
					ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The common words directory for option 3 is missing.");
		}

		if (outputDir == null) {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The output directory for option 4 is missing.");
		}
		if (textFileDir == null || dictionaryDir == null || commonWordsDir == null || outputDir == null) {
			throw new Exception("[ERROR] You must enter data for all feilds before you select \"Execute\"...");
		}
	}
}
