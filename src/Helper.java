import java.io.*;
import java.util.*;
import java.text.BreakIterator;

/**
 * The Class Helper holds methods to help other classes function.
 */
public class Helper {

	/** The stop words  to remove from text. */
	private static ArrayList<String> stopWords;

	/**
	 * Extract sentences from text.
	 *
	 * @param content the content
	 * @return the sentences in a string[]
	 */
	
	public static String[] extractSentences(String content) {
		BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.US);
		boundary.setText(content);

		List<String> sentences = new ArrayList<String>();
		int start = boundary.first();
		int end = boundary.next();
		while (end != BreakIterator.DONE) {
			
			//replace all non-standard characters
			String sentence = content.substring(start, end).trim().replaceAll("\\p{C}", "");

			if (!sentence.isEmpty()) {
				sentences.add(sentence);
			}

			start = end;
			end = boundary.next();
		}

		return sentences.toArray(new String[sentences.size()]);
	}


	/**
	 * Checks if is stop word.
	 *
	 * @param e the string to check
	 * @return true, if is stop word
	 */
	public static boolean isStopWord(String e){

		e = e.toLowerCase();
		if(stopWords.contains(e)){
			return true;
		}
		else{
			return false;
		}

	}


	/**
	 * Load stop words from text file
	 */
	public static void loadStopWords(){
		Scanner in = null;
		stopWords = new ArrayList<String>();

		try {
			in = new Scanner(new File("stopwordsNoDup.txt"));

			while(in.hasNextLine()){
				String e = in.nextLine();
				stopWords.add(e);
			}

		} catch (FileNotFoundException e1) {
			System.out.println("CANNOT LOAD STOPWORDS");
			e1.printStackTrace();
		}finally{
			in.close();
		}

	}

	/**
	 * Removes the stop words
	 *
	 * @param string to remove stop words from
	 * @return the string without stop words
	 */
	public static String removeStops(String sentence){
		Scanner scn = new Scanner(sentence);
		String output = "";

		while(scn.hasNext()){
			String test = scn.next();
			if(!isStopWord(test)){
				output += " "+test;
			}

		}
		scn.close();
		return output;
	}

	/**
	 * Counts number of keywords found in each sentence
	 *
	 * @param string to check words in
	 * @return the map of <Words, Number of Occurrences>
	 */
	public static Map<String, Double> wordCount(String[] stringArray){
		Map<String, Double> map = new HashMap<String, Double>();

		for(String sentence : stringArray){
			String[] tokenize = sentence.split(" ");

			for(String r : tokenize){
				if(!map.containsKey(r)){
					map.put(r,0.0);
				}else{
					double count = map.get(r);
					count++;
					map.replace(r, count);
				}
			}
			//removes blank words
			map.remove("");
			
			Iterator<Map.Entry<String,Double>> iter = map.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry<String,Double> entry = iter.next();
				
				//removes words with low value of occurrences
				if(Config.minOccurences >= entry.getValue()){
					iter.remove();
				}
			}
		}
		
		//sorts low to high based in value
		map = sortByComparator(map);
		return map;
	}

	/**
	 * Sort by comparator.
	 *
	 * @param unsortMap the unsort map
	 * @return the map
	 */
	//Taken from http://www.mkyong.com/java/how-to-sort-a-map-in-java/
	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Double>> list = 
				new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
