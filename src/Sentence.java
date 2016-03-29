import java.util.*;
import java.util.Map.Entry;

/**
 * The Class Sentence.
 */
public class Sentence implements Comparable<Sentence>{

	/** The position of sentence within page*/
	private int pos;
	
	/** The page of sentence*/
	private int page;

	/** The frequencies of collisions with keywords locally. */
	private double freqCollisionLocal;


	/** The frequencies of collisions with keywords global. */
	private double freqCollisionGlobal;

	/** The content. */
	private String content;

	/** The content stemmed. */
	private String contentStemmed;

	/** The word frequency */
	private Map<String, Double> wordFreLocal;

	/** The word frequency */
	private GlobalKeywords globalKeywords;

	/** The sentence count. */
	private double sentenceCount;

	/** The score. */
	private double score;

	/**Top n globally*/
	private boolean top;

	/**
	 * Instantiates a new sentence.
	 *
	 * @param content the content
	 * @param contentStemmed the content stemmed
	 * @param pos the position in PDf
	 * @param wordFre the word frequency
	 * @param sentenceCount the sentence count
	 */
	public Sentence(String content, String contentStemmed, int pos, int page, Map<String, Double> wordFreLocal, GlobalKeywords globalKeywords, double sentenceCount){
		this.pos = pos;
		this.page = page;
		this.content = content;
		this.contentStemmed = contentStemmed;
		this.wordFreLocal = wordFreLocal;
		this.globalKeywords = globalKeywords;
		this.sentenceCount = sentenceCount;
		this.freqCollisionLocal = calculateClashValueLocal();
		this.freqCollisionGlobal = calculateClashValueGlobal();
		this.score = calculateScore();
	}

	/**
	 * Gets the position in PDF
	 *
	 * @return the position
	 */
	//getters
	public int getPos(){
		return this.pos;
	}

	/**
	 * Gets the frequency collision locally.
	 *
	 * @return the frequency collision
	 */
	public double getfreqCollisionLocal(){
		return this.freqCollisionLocal;
	}
	
	/**
	 * Gets the page.
	 *
	 * @return the page number
	 */
	public int getPage(){
		return this.page;
	}

	/**
	 * Gets the frequency collision globally.
	 *
	 * @return the frequency collision
	 */
	public double getfreqCollisionGlobal(){
		return this.freqCollisionGlobal;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getcontent(){
		return this.content;
	}

	/**
	 * Gets the content stemmed.
	 *
	 * @return the content stemmed
	 */
	public String getcontentStemmed(){
		return this.contentStemmed;
	}


	/**
	 * Sets the position
	 *
	 * @param pos the new frequency 
	 */

	public void setPos(int pos){
		this.pos = pos;
	}

	/**
	 * Sets the frequency collision locally.
	 *
	 * @param freqCollision the frequency collision
	 * @return the frequency collision
	 */
	public void setfreqCollisionLocal(double freqCollisionLocal){
		this.freqCollisionLocal = freqCollisionLocal;
	}


	/**
	 * Sets the frequency collision globally.
	 *
	 * @param freqCollision the frequency collision
	 * @return the frequency collision
	 */
	public void setfreqCollisionGlobal(double freqCollisionGlobal){
		this.freqCollisionGlobal = freqCollisionGlobal;
	}


	/**
	 * Sets the content.
	 *
	 * @param content the content
	 * @return the content
	 */
	public void setcontent(String content){
		this.content = content;
	}

	/**
	 * Sets the content stemmed.
	 *
	 * @param contentStemmed the content stemmed
	 * @return the content stemmed
	 */
	public void setcontentStemmed(String contentStemmed){
		this.contentStemmed = contentStemmed;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public double getScore(){
		return this.score;
	}

	public void setTop(boolean set){
		this.top = set;
	}

	public boolean checkTop(){
		return this.top;
	}

	/**
	 * Calculate clash value local.
	 */
	private double calculateClashValueLocal(){
		Map<String, Double> sorted = this.wordFreLocal;
		double count = 0;
		double total = 0;

		for (double f : sorted.values()) {
			total += f;
		}

		for(Entry<String, Double> entry : sorted.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			if(contentStemmed.contains(key)){
				count += value;
			}

		}

		return count/total;

	}

	/**
	 * Calculate clash value global.
	 */
	private double calculateClashValueGlobal(){
		Map<String, Double> sorted = globalKeywords.getwordFreqGlobal();
		double count = 0;
		double total = 0;

		for (double f : sorted.values()) {
			total += f;
		}

		for(Entry<String, Double> entry : sorted.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			if(contentStemmed.contains(key)){
				count += value;
			}

		}

		return count/total;

	}



	/**
	 * Calculate score.
	 */
	private double calculateScore(){
		double wordFreLocal = this.freqCollisionLocal;
		double wordFreGlobal = this.freqCollisionGlobal;
		double pos = this.pos;

		if(pos<(sentenceCount/2)){
			pos = pos/(sentenceCount/2);
		}else{
			pos = (sentenceCount-pos)/(sentenceCount/2);
		}

		return (Config.posWeight*pos)+(Config.wordFreWeightLocal*wordFreLocal)+(Config.wordFreWeightGlobal*wordFreGlobal);  

	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Sentence o) {
		if (this.score > o.getScore()) return -1;
		if (this.score < o.getScore()) return 1;
		return 0;
	}
}
