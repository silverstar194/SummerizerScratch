import java.util.Comparator;

public class SentencePositionComparator implements Comparator<Sentence>{

	@Override
	public int compare(Sentence o1, Sentence o2) {
		//sort by page first
		if(o1.getPage() < o2.getPage()) return -1;
		if(o1.getPage() > o2.getPage()) return 1;
		
		//sort by pos within page
		if(o1.getPos() < o2.getPos()) return -1;
		if(o1.getPos() > o2.getPos()) return 1;
		
		return 0;
	}

}
