import java.io.*;
import java.util.*;



/**
 * The Class PDFDocument. Holds the extracted PDF
 */
public class PDFDocument{

	/** The content. */
	private String[] content;
	
	/** The content stemmed. */
	private String[] contentStemmed;
	
	/** The sentence list. */
	private ArrayList<Sentence> sentenceList = new ArrayList<>();
	
	/** The sentence count. */
	private double sentenceCount;
	
	/** The word frequency */
	private Map<String, Double> wordFreq;
	
	/** The title. UNUSED IN THIS GENERATION*/
	private String title;

	/**
	 * Instantiates a new PDF document.
	 *
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PDFDocument(String content) throws IOException{
		this.content =  Helper.extractSentences(content);
		this.contentStemmed = Helper.extractSentences(content);

		for(int i=0; i<contentStemmed.length; i++){
			contentStemmed[i] = Helper.removeStops(contentStemmed[i]);
			String holder = Helper.removeStops(contentStemmed[i]);
			contentStemmed[i] = Stemmer.stem(holder);
		}
		this.wordFreq = Helper.wordCount(contentStemmed);
		this.sentenceCount = content.length();

		for(int i=0; i<this.content.length; i++){
			sentenceList.add(new Sentence(this.content[i], contentStemmed[i], i, wordFreq, sentenceCount));
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
	public Map<String, Double> getwordFreq(){
		return this.wordFreq;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String gettitle(){
		return this.title;
	}
	
	/**
	 * Gets the sentence list.
	 *
	 * @return the sentence list
	 */
	public ArrayList<Sentence> getSentenceList(){
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
	public void setwordFreq(Map<String, Double> wordFreq){
		this.wordFreq = wordFreq;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void settitle(String title){
		this.title= title;
	}
	




}
