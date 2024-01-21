package ie.atu.sw;

import java.util.List;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        WordDetail stores the definition and the pages numbers for the E-Book
 *        index.
 *
 *        @See EBookIndexer
 * 
 *        Citations: (Baeldung 2022; Coding Cleverly 2020; Shubham 2022).
 */
public class WordDetail {
	private String definition;
	private List<Integer> pageNumbers;

	/**
	 * 
	 * @param definition:  the dictionary definition for the word.
	 * @param pageNumbers: the page numbers for the word.
	 */
	public WordDetail(String definition, List<Integer> pageNumbers) {
		this.definition = definition;
		this.pageNumbers = pageNumbers;
	}

	/**
	 * Getter for the page numbers.
	 * 
	 * @return Returns the list of page numbers.
	 */
	protected List<Integer> getPageNumbers() {
		return pageNumbers;
	}

	/**
	 * Overrides the toString() to return a formatted string of the definition and
	 * the page numbers
	 */
	@Override
	public String toString() {
		return String.format("%s %n %-16s %s %n %n %-16s %s %n %-16s %s %n", "Definition:", "", this.definition, "",
				"Page(s):", "", this.pageNumbers);
	}
}
