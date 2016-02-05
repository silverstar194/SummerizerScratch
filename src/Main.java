import java.io.*;
import java.util.*;


public class Main {

	public static void main(String args[]) throws IOException{
		Helper.loadStopWords();
				PDFReader pdfReader = new PDFReader();
				pdfReader.setFilePath("Moby_Dick_NT.pdf");
				PDFDocument newDocument = new PDFDocument(pdfReader.ToText());
				
				String[] u = newDocument.getcontentStemmed();
				Map<String, Double> test = newDocument.getwordFreq();
				System.out.println(test);
				
				
//		ArrayList<String> words = new ArrayList<>();
//		ArrayList<String> duups = new ArrayList<>();
//
//
//		Set<String> hs = new HashSet<>();
//		hs.addAll(words);
//		words.clear();
//		words.addAll(hs);
//		
//		PrintWriter out = new PrintWriter("file.txt");
//		for(String e : words){
//			out.println(e);
//		}
//		
//		out.flush();
//		out.close();

	}
}

