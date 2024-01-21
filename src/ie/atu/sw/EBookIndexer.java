package ie.atu.sw;

import static java.lang.System.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        EBookIndexer is a concrete class that extends the abstract class
 *        FileCollector. Additionally it creates an aggregation of the class
 *        WordDetials.
 * 
 *        This class loops though the data collected by FileCollector and puts
 *        the index data into a map.
 *
 * @See FileCollector
 * @See WordDetail
 * 
 *      Citations: (Healy 2022a, 2022b; Klimek 2009).
 */
public class EBookIndexer extends FileCollector {
	private Map<String, WordDetail> eBookIndex = new ConcurrentSkipListMap<String, WordDetail>();

	/**
	 * Passes file paths to FileCollector.
	 * 
	 * @param commonWordsDir: passes the common words directory to FileCollector.
	 * @param dictionaryDir:  passes the dictionary directory to FileCollector.
	 * @param textFileDir:    passes the text-file directory to FileCollector.
	 */
	public EBookIndexer(String commonWordsDir, String dictionaryDir, String textFileDir) {
		super(commonWordsDir, dictionaryDir, textFileDir);
	}

	/**
	 * Getter for the E-Book Index map generated by this class.
	 * 
	 * @return The E-Book Index generated by this class.
	 */
	protected Map<String, WordDetail> geteBookIndex() {
		return eBookIndex;
	}

	/**
	 * Runs the key functions of this class: collecting the files, building the
	 * index and removing the common words.
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		collectFiles();
		buildIndex();
		removeCommonWords(super.getLocalCommonWords());
		out.println("\n[INFO] The application has processed " + super.getTextFileDir());
	}

	/**
	 * Collects the files by calling methods in VirtualThreadFileParser and
	 * FileCollector.
	 * 
	 * @throws Exception
	 */
	private void collectFiles() throws Exception {
		out.println("[INFO] Collecting words from " + super.getCommonWordsDir());

		// BIG O: O(n log n)
		super.parseTextFile(super.getCommonWordsDir());
		// BIG O: O(n log n)
		super.getLocalCommonWords().putAll(super.getParsedTextFile());

		out.println("[INFO] Collecting words from " + super.getDictionaryDir());
		// BIG O: O(n log n)
		super.parseCSVFile(super.getDictionaryDir());
		// BIG O: O(n log n)
		super.getLocalDictionary().putAll(super.getParsedCSVFile());

		// BIG O: O(1)
		super.getParsedTextFile().clear();
		out.println("[INFO] Collecting words from " + super.getTextFileDir());
		// BIG O: O(n log n)
		super.parseTextFile(super.getTextFileDir());
		// BIG O: O(n log n)
		super.getLocalTextFile().putAll(super.getParsedTextFile());
	}

	/**
	 * Loops though the data collected in FileCollector and adds it to the local
	 * map. Additionally, this method implements printProgress in the ProgressBar
	 * interface. ProgressBar is implemented by FileCollector.
	 * 
	 * @see ProgressBar
	 */
	private void buildIndex() {
		out.println("[INFO] Building index from " + super.getTextFileDir());
		try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
			int index = 0;
			// BIG O: O(n)
			for (Map.Entry<String, List<Integer>> entry : super.getLocalTextFile().entrySet()) {
				if (super.getLocalDictionary().containsKey(entry.getKey()))
					eBookIndex.put(entry.getKey(),
							new WordDetail(super.getLocalDictionary().get(entry.getKey()), entry.getValue()));
				printProgress(index + 1, super.getLocalTextFile().size());
				index++;
			}
		}
	}

	/**
	 * Removes the common words from the E-Book index map.
	 * 
	 * @param map: takes in the common words map from FileCollector.
	 */
	private void removeCommonWords(Map<String, List<Integer>> map) {
		// BIG O: O(n log n)
		this.eBookIndex.keySet().removeAll(map.keySet());
	}
}