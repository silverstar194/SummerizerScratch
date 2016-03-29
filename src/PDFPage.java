import java.io.*;
import java.util.*;



/**
 * The Class PDFPage. Holds the extracted PDF contents of a page
 */
public class PDFPage{

	/** The content. */
	private String[] content;
	
	/** The content stemmed. */
	private String[] contentStemmed;
	
	/** The sentence list. */
	private PriorityQueue<Sentence> sentenceList;
	
	/** The sentence count. */
	private double sentenceCount;
	
	/** The word frequency */
	private Map<String, Double> wordFreqLocal;
	
	/** The page number*/
	private int pageNumber;

	private GlobalKeywords globalKeywords;

	/**
	 * Instantiates a new PDF page.
	 *
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PDFPage(String content, GlobalKeywords globalKeywords, int pageNumber) throws IOException{
		this.pageNumber = pageNumber;
		sentenceList = new PriorityQueue<Sentence>(new SentenceScoreComparator());
		this.content =  Helper.extractSentences(content);
		this.globalKeywords = globalKeywords;
		this.contentStemmed = Helper.extractSentences(content);

		for(int i=0; i<contentStemmed.length; i++){
			contentStemmed[i] = Helper.removeStops(contentStemmed[i]);
			String holder = Helper.removeStops(contentStemmed[i]);
			contentStemmed[i] = Stemmer.stem(holder);
		}
		this.wordFreqLocal = Helper.wordCountLocal(contentStemmed);
		this.sentenceCount = content.length();

		for(int i=0; i<this.content.length; i++){
			sentenceList.add(new Sentence(this.content[i], contentStemmed[i], i, pageNumber, wordFreqLocal, globalKeywords,  sentenceCount));
		}
		
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String[] getContent(){

		return this.content;
	}
	
	/**
	 * Gets the page number.
	 *
	 * @return the content
	 */
	public int getpageNumber(){

		return this.pageNumber;
	}

	/**
	 * Gets the content stemmed.
	 *
	 * @return the content stemmed
	 */
	public String[] getcontentStemmed(){

		return this.contentStemmed;
	}


	/**
	 * Gets the sentence count.
	 *
	 * @return the sentence count
	 */
	public double getsentenceCount(){
		return this.sentenceCount;
	}

	/**
	 * Gets the word frequency
	 *
	 * @return the word frequency
	 */
	public Map<String, Double> getwordFreqLocal(){
		return this.wordFreqLocal;
	}

	
	/**
	 * Gets the sentence list.
	 *
	 * @return the sentence list
	 */
	public PriorityQueue<Sentence> getSentenceList(){
		return this.sentenceList;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setcontent(String[] content){

		this.content = content;
	}

	/**
	 * Sets the sentence count.
	 *
	 * @param sentenceCount the new sentence count
	 */
	public void setsentenceCount(double sentenceCount){
		this.sentenceCount = sentenceCount;
	}

	/**
	 * Set word frequency
	 *
	 * @param wordFreq the word frequency
	 */
	public void setwordFreq(Map<String, Double> wordFreqLocal){
		this.wordFreqLocal = wordFreqLocal;
	}

}
