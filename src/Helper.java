import java.io.File;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Helper {

	private static ArrayList<String> stopWords;


	public static String[] extractSentences(String content) {
		BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.US);
		boundary.setText(content);

		List<String> sentences = new ArrayList<String>();
		int start = boundary.first();
		int end = boundary.next();
		while (end != BreakIterator.DONE) {
			String sentence = content.substring(start, end).trim();

			if (!sentence.isEmpty()) {
				sentences.add(sentence);
			}
			start = end;
			end = boundary.next();
		}

		return sentences.toArray(new String[sentences.size()]);
	}


	public static boolean isStopWord(String e){
		e = e.toLowerCase();
		if(stopWords.contains(e)){
			return true;
		}
		else{
			return false;
		}

	}


	public static void loadStopWords(){
		Scanner scanner;
		stopWords = new ArrayList<String>();
		try {
			scanner = new Scanner(new File("stopwords.txt"));

			while(scanner.hasNextLine()){
				String e = scanner.nextLine();
				stopWords.add(e);
			}

		} catch (FileNotFoundException e1) {
			System.out.println("CANNOT LOAD STOPWORDS");
			e1.printStackTrace();
		}

	}

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

	public static Map<String, Double> wordCount(String[] e){
		Map<String, Double> map = new HashMap<String, Double>();

		for(String s : e){
			String[] tokenize = s.split(" ");
	
			for(String r : tokenize){
				if(!map.containsKey(r)){
					map.put(r,0.0);
				}else{
					double count = map.get(r);
					count++;
					map.replace(r, count);
				}
			}
		}
		return map;
	}

}
