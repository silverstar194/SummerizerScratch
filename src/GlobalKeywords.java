import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class GlobalKeywords {

	/*This class holds global keywords*/
	
	/** The word frequency */
	private Map<String, Double> wordFreqGlobal;

	
	/** The content stemmed. */
	private String[] contentStemmed;
	
	
	/** The total pages in document*/
	private int totalPages;
	
	
	/**
	 * Instantiates a new Global Keyword list.
	 *
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public GlobalKeywords(String content, int totalPages) throws IOException{
		this.contentStemmed = Helper.extractSentences(content);
		this.totalPages = totalPages;

		for(int i=0; i<contentStemmed.length; i++){
			contentStemmed[i] = Helper.removeStops(contentStemmed[i]);
			String holder = Helper.removeStops(contentStemmed[i]);
			contentStemmed[i] = Stemmer.stem(holder);
		}

		this.wordFreqGlobal = Helper.wordCountGlobal(this);
		
	}
	
	
	
	/**
	 * Gets the word frequency
	 *
	 * @return the word frequency
	 */
	public Map<String, Double> getwordFreqGlobal(){
		return this.wordFreqGlobal;
	}
	
	
	/**
	 * Gets the content stemmed
	 *
	 * @return the content stemmed
	 */
	public String[] getcontentStemned(){
		return this.contentStemmed;
	}
	
	
	/**
	 * Gets the total pages
	 *
	 * @return the total pages
	 */
	public int gettotalPages(){
		return this.totalPages;
	}

}
