import java.util.*;
import java.util.Map.Entry;

/**
 * The Class Sentence.
 */
public class Sentence implements Comparable<Sentence>{
	
	/** The position of sentence */
	private double pos;
	
	/** The frequencies of collisions with keywords. */
	private double freqCollision;
	
	/** The title collision. (UNUSED) */
	private double titleCollision;
	
	/** The cue collision. (UNUSED)*/
	private double cueCollision;
	
	/** The content. */
	private String content;
	
	/** The content stemmed. */
	private String contentStemmed;
	
	/** The word frequency */
	private Map<String, Double> wordFre;
	
	/** The sentence count. */
	private double sentenceCount;
	
	/** The score. */
	private double score;
	
	/**
	 * Instantiates a new sentence.
	 *
	 * @param content the content
	 * @param contentStemmed the content stemmed
	 * @param pos the position in PDf
	 * @param wordFre the word frequency
	 * @param sentenceCount the sentence count
	 */
	public Sentence(String content, String contentStemmed, int pos, Map<String, Double> wordFre, double sentenceCount){
		this.pos = pos;
		this.content = content;
		this.contentStemmed = contentStemmed;
		this.wordFre = wordFre;
		this.sentenceCount = sentenceCount;
		calculateClashValue();
		calculateScore();
	}
	
	/**
	 * Gets the position in PDF
	 *
	 * @return the position
	 */
	//getters
	public double getPos(){
		return this.pos;
	}
	
	/**
	 * Gets the frequency collision.
	 *
	 * @return the frequency collision
	 */
	public double getfreqCollision(){
		return this.freqCollision;
	}
	
	/**
	 * Gets the title collision.
	 *
	 * @return the title collision
	 */
	public double gettitleCollision(){
		return this.titleCollision;
	}
	
	/**
	 * Gets the cue collision.
	 *
	 * @return the cue collision
	 */
	public double getcueCollision(){
		return this.cueCollision;
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

	public void setPos(double pos){
		this.pos = pos;
	}
	
	/**
	 * Gets the frequency collision.
	 *
	 * @param freqCollision the frequency collision
	 * @return the frequency collision
	 */
	public void getfreqCollision(double freqCollision){
		this.freqCollision = freqCollision;
	}
	
	/**
	 * Gets the title collision.
	 *
	 * @param titleCollision the title collision
	 * @return the title collision
	 */
	public void gettitleCollision(double titleCollision){
		this.titleCollision = titleCollision;
	}
	
	/**
	 * Gets the cue collision.
	 *
	 * @param cueCollision the cue collision
	 * @return the cue collision
	 */
	public void getcueCollision(double cueCollision){
		this.cueCollision = cueCollision;
	}
	
	/**
	 * Gets the content.
	 *
	 * @param content the content
	 * @return the content
	 */
	public void getcontent(String content){
		this.content = content;
	}
	
	/**
	 * Gets the content stemmed.
	 *
	 * @param contentStemmed the content stemmed
	 * @return the content stemmed
	 */
	public void getcontentStemmed(String contentStemmed){
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
	
	/**
	 * Calculate clash value.
	 */
	private void calculateClashValue(){
		 Map<String, Double> sorted = this.wordFre;
		 double count = 0;
		 double total = 0;
		 for (double f : sorted .values()) {
			 total += f;
			}
		 
		 for(Entry<String, Double> entry : sorted.entrySet()) {
		     String key = entry.getKey();
		     Double value = entry.getValue();
		    if(contentStemmed.contains(key)){
		    	count += value;
		    }
		    	
		 }
		 
		 count = count/total;
		 
		 this.freqCollision = count;
	}


	
	/**
	 * Calculate score.
	 */
	private void calculateScore(){
		double wordFre = this.freqCollision;
		double pos = this.pos;
		
		if(pos<(sentenceCount/2)){
			pos = pos/(sentenceCount/2);
		}else{
			pos = (sentenceCount-pos)/(sentenceCount/2);
		}
	
		this.score = (Config.posWeight*pos)+(Config.wordFreWeight*wordFre);  
		
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
