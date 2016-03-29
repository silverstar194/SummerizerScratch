import java.util.Comparator;

public class SentenceScoreComparator implements Comparator<Sentence> {


	@Override
	public int compare(Sentence o1, Sentence o2) {
	    return Double.compare(o2.getScore(), o1.getScore());
	}

}
