package ie.atu.sw;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        VirtualThreadFileParser is an abstract class. The function of this
 *        class is to parse text files and CSV files using virtual threads.
 * 
 *        Citations: (Ireeder 2014; Healy 2022a, 2022b; Lee 2022).
 */
public abstract class VirtualThreadFileParser {
	private int lineCounter = 0;
	private int pageNumber = 1;
	private Map<String, List<Integer>> parsedTextFile = new ConcurrentSkipListMap<String, List<Integer>>();
	private Map<String, String> parsedCSVFile = new ConcurrentSkipListMap<String, String>();

	/**
	 * Getter for the maps that collects data from a text-file.
	 * 
	 * @return Returns the text-file map that contains a String of words as keys,
	 *         and a List of integers as values.
	 */
	protected Map<String, List<Integer>> getParsedTextFile() {
		return parsedTextFile;
	}

	/**
	 * Getter for the map that collect data from a CSV file.
	 * 
	 * @return Returns a dictionary map that contains String as keys and String as
	 *         values.
	 */
	protected Map<String, String> getParsedCSVFile() {
		return parsedCSVFile;
	}

	/**
	 * Parses lines of text from a text-file and keeps a count of the lines in the
	 * text-file.
	 * 
	 * @param file: text-file directory
	 * @throws Exception
	 */
	public void parseTextFile(String file) throws Exception {
		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			// BIG O: O(n)
			Files.lines(Paths.get(file)).forEach(line -> pool.execute(() -> processTextFile(line, lineCounter++)));
		}
		this.lineCounter = 0;
		this.pageNumber = 1;
	}

	/**
	 * Processes the lines of text from the text-file into words.
	 * 
	 * @param line:        line of text.
	 * @param lineCounter: line count
	 */
	private void processTextFile(String line, int lineCounter) {
		String[] words = line.split("\\s+");
		for (String word : words) {
			word = word.trim().toLowerCase().replaceAll("[^a-zA-Z]", "");
			addWord(word);
		}
	}

	/**
	 * Adds the word to the text-file map. Additionally, calculates page numbers and
	 * adds them to the text-file map.
	 * 
	 * @param word: word from line of text.
	 */
	private void addWord(String word) {
		List<Integer> pages;
		if (parsedTextFile.containsKey(word)) {
			pages = parsedTextFile.get(word);
		} else {
			pages = new ArrayList<>();
		}
		if (lineCounter % 40 == 0)
			pageNumber++;
		if (pages.contains(pageNumber))
			return;
		pages.add(pageNumber);

		if (word.length() > 0)
			parsedTextFile.put(word, pages);
	}

	/**
	 * Parses the data from a CSV file.
	 * 
	 * @param file: file directory
	 * @throws Exception
	 */
	public void parseCSVFile(String file) throws Exception {
		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			// BIG O: O(n)
			Files.lines(Paths.get(file)).forEach(line -> pool.execute(() -> processCSVFile(line)));
		}
	}

	/**
	 * Processes the data from each entry in a CSV file and adds that data to a map.
	 * 
	 * @param line: line or entry in parsed from the CSV file.
	 */
	private void processCSVFile(String line) {
		String[] entry = line.split(",");
		parsedCSVFile.put(entry[0].trim().toLowerCase().replaceAll("[^a-zA-Z]", " "), entry[1]);
	}

}
