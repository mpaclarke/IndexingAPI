package ie.atu.sw;

import static java.lang.System.*;

import java.util.*;

/**
 * @author Michael Clarke
 * @version 1.0
 * @since JavaSE-19
 * 
 *        The <i>interface</i> provides two methods. reverseOrder() reversed the
 *        order of a map. frequentWords() calculates the most and least frequent
 *        words based on their page number index.
 * 
 *        Citations: (Techie Delight n.d; Verma 2022)
 */
public interface Indexable {

	/**
	 * Reveres the order of a map.
	 * 
	 * @param map: takes in a generic map.
	 * @return Returns a reverse order of the map entered.
	 */
	public default <K extends Comparable, V> Map<K, V> reverseOrder(Map<K, V> map) {
		out.println("Reversing order of index...");
		Map<K, V> temp = new TreeMap<>(new Comparator<K>() {
			@Override
			public int compare(K a, K b) {
				return b.compareTo(a);
			}
		});
		// BIG O: O(n log n)
		temp.putAll(map);
		return temp;
	}

	/**
	 * Prints a list of the most, and least, frequent words in the map inputed to
	 * the method.
	 * 
	 * @param map:    takes in a map with key String and value Word.
	 * @param count:  specifies the number of words in the list.
	 * @param choice: true equals MOST frequent: false equals LEAST frequent.
	 * @throws Exception
	 */
	public default void frequentWords(Map<String, WordDetail> map, int count, boolean choice) throws Exception {
		Map<String, Integer> temp = new TreeMap<>();
		List<Integer> list = new ArrayList<Integer>();
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

		// BIG O: O(n log n)
		for (Map.Entry<String, WordDetail> entry : map.entrySet()) {
			int i = entry.getValue().getPageNumbers().size();
			temp.put(entry.getKey(), i);
		}
		// BIG O: O(n)
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			list.add(entry.getValue());
		}

		// BIG O: O(n)
		Collections.sort(list, new Comparator<Integer>() {
			public int compare(Integer i, Integer j) {
				if (choice == true) {
					return (j).compareTo(i);
				} else {
					return (i).compareTo(j);
				}
			}
		});

		// BIG O: O(n)
		for (Integer str : list) {
			for (Map.Entry<String, Integer> entry : temp.entrySet()) {
				if (entry.getValue().equals(str)) {
					sortedMap.put(entry.getKey(), str);
				}
			}
		}

		// BIG O: O(n)
		int i = 1;
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			System.out.print(i + ")" + entry.getKey() + "\n");
			i++;
			if (i > count)
				return;
		}
	}
}
