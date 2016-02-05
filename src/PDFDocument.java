import java.io.IOException;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class PDFDocument{

	private String[] content;
	private String[] contentStemmed;
	private double sentenceCount;
	private Map<String, Double> wordFreq;
	private String title;

	public PDFDocument(String content) throws IOException{
		this.content =  Helper.extractSentences(content);
		this.contentStemmed = Helper.extractSentences(content);

		for(int i=0; i<contentStemmed.length; i++){
			String holder = Helper.removeStops(contentStemmed[i]);
			contentStemmed[i] = Stemmer.stem(holder);
		}
		this.wordFreq = Helper.wordCount(contentStemmed);

		this.sentenceCount = calculateSentenceCount();
	}

	//getters
	public String[] getcontent(){

		return this.content;
	}

	public String[] getcontentStemmed(){

		return this.contentStemmed;
	}


	public double getsentenceCount(){
		return this.sentenceCount;
	}

	public Map<String, Double> getwordFreq(){
		return this.wordFreq;
	}

	public String gettitle(){
		return this.title;
	}

	//setters
	public void setcontent(String[] content){

		this.content = content;
	}

	public void setsentenceCount(double sentenceCount){
		this.sentenceCount = sentenceCount;
	}

	public void setwordFreq(Map<String, Double> wordFreq){
		this.wordFreq = wordFreq;
	}

	public void settitle(String title){
		this.title= title;
	}

	private double calculateSentenceCount(){
		double matches = 0;

		for(String e : Config.endSentencesChars){
			for(String z : content){
				matches += StringUtils.countMatches(z, e);
			}
		}
		return matches;

	}



}
