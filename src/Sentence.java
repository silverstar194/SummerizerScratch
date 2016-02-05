
public class Sentence {
	
	private double pos;
	private double freqCollision;
	private double titleCollision;
	private double cueCollision;
	private String content;
	private String contentStemmed;
	
	private double score;
	
	
	//getters
	public double getPos(){
		return this.pos;
	}
	
	public double getfreqCollision(){
		return this.freqCollision;
	}
	
	public double gettitleCollision(){
		return this.titleCollision;
	}
	
	public double getcueCollision(){
		return this.cueCollision;
	}
	
	public String getcontent(){
		return this.content;
	}
	
	public String getcontentStemmed(){
		return this.contentStemmed;
	}
	
	
	//setters
	public void setPos(double pos){
		this.pos = pos;
	}
	
	public void getfreqCollision(double freqCollision){
		this.freqCollision = freqCollision;
	}
	
	public void gettitleCollision(double titleCollision){
		this.titleCollision = titleCollision;
	}
	
	public void getcueCollision(double cueCollision){
		this.cueCollision = cueCollision;
	}
	
	public void getcontent(String content){
		this.content = content;
	}
	
	public void getcontentStemmed(String contentStemmed){
		 this.contentStemmed = contentStemmed;
	}
	
	


}
