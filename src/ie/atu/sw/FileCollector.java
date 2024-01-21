package ie.atu.sw;

import java.util.*;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        FileCollector is an abstract class the extends the abstract class
 *        VirtualThreadFileParser. Additionally, FileCollector implements
 *        ProgressBar.
 * 
 *        The function of this class is to collect data parsed from
 *        VirtualThreadFileParser.
 * 
 *        @See VirtualThreadFileParser 
 *        @See ProgressBar
 */
public abstract class FileCollector extends VirtualThreadFileParser implements ProgressBar {
	private Map<String, List<Integer>> localCommonWords = new TreeMap<String, List<Integer>>();
	private Map<String, String> localDictionary = new TreeMap<String, String>();
	private Map<String, List<Integer>> localTextFile = new TreeMap<String, List<Integer>>();
	private String commonWordsDir;
	private String dictionaryDir;
	private String textFileDir;

	/**
	 * Initializes the instance variables that store the file directories.
	 * 
	 * @param commonWordsDir: common words directory
	 * @param dictionaryDir:  dictionary directory
	 * @param textFileDir:    text-file directory
	 */
	public FileCollector(String commonWordsDir, String dictionaryDir, String textFileDir) {
		this.commonWordsDir = commonWordsDir;
		this.dictionaryDir = dictionaryDir;
		this.textFileDir = textFileDir;
	}

	/**
	 * Getter for the common words map.
	 * 
	 * @return Returns the common words map.
	 */
	protected Map<String, List<Integer>> getLocalCommonWords() {
		return localCommonWords;
	}

	/**
	 * Getter for the dictionary map.
	 * 
	 * @return Returns the dictionary map.
	 */
	protected Map<String, String> getLocalDictionary() {
		return localDictionary;
	}

	/**
	 * Getter for the text-file map.
	 * 
	 * @return Returns the text-file map.
	 */
	protected Map<String, List<Integer>> getLocalTextFile() {
		return localTextFile;
	}

	/**
	 * Getter for the common words directory.
	 * 
	 * @return Returns the common words directory.
	 */
	protected String getCommonWordsDir() {
		return commonWordsDir;
	}

	/**
	 * Getter for the dictionary directory.
	 * 
	 * @return Returns the dictionary directory.
	 */
	protected String getDictionaryDir() {
		return dictionaryDir;
	}

	/**
	 * Getter for the text-file directory.
	 * 
	 * @return Returns the text-file directory.
	 */
	protected String getTextFileDir() {
		return textFileDir;
	}
}
