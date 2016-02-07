/*Summary articles and paper based on keywords and position of sentences. This provides general overview
 * and writes out summary to PDF.
 * 
 * Program utilized PDFBox and Apache commons-lang-2.6
 * 
 */
import java.io.*;
import java.util.*;
import java.util.Map.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 * The Class Main. Holder main method
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) throws IOException {
		Helper.loadStopWords();
		PDFReader pdfReader = new PDFReader();

		Scanner in = new Scanner(System.in);
		System.out.println("=====Please Insert Filepath=====");

		PDFDocument newDocument = null;

		while(newDocument == null){
			System.out.print("Filename: ");

			//get file path from user
			if(in.hasNextLine()){
				pdfReader.setFilePath(in.nextLine());

			}


			try{
				newDocument = new PDFDocument(pdfReader.ToText());
			} catch(IOException e){
				System.out.println("Error Reading File -- Try Again");
			}
		}


		boolean ratioBoolean = true;

		System.out.println("=====Compression Ratio (3-10 allowed)=====");
		while(ratioBoolean){
			System.out.print("Ratio of Compression: ");
			try{
				//get compress ratio from user
				if(in.hasNext()){
					int ratio = in.nextInt();
					if(ratio>=Config.minCompressRatio && Config.maxCompressRatio<=10){
						Config.ratio = ratio;
						ratioBoolean = false;
					}else{
						System.out.println("Invalid Value");
					}
				}

			}catch(InputMismatchException e){
				System.out.println("Invalid");
				in.nextLine();
			}
		}
		in.close();

		System.out.println("Creating Summary......");

		//get sentences from doc.
		ArrayList<Sentence> sentenceList = newDocument.getSentenceList();

		//sort based on rating
		Collections.sort(sentenceList);

		Map<Double, String> output = new HashMap<Double, String>();

		for(int i=0; i<sentenceList.size()/Config.ratio; i++){

			if(sentenceList.get(i).getcontent().length()>Config.minLength && sentenceList.get(i).getcontent().length()<Config.maxLength){
				//sort sentences by position in document
				output.put(sentenceList.get(i).getPos(), sentenceList.get(i).getcontent());
			}

		}
		
		//re-sort to have the map in order of sentence position
		Map<Double, String> outputSorted = new TreeMap<Double, String>(output);

		String fileName = "yourSummary_"+Math.floor(Math.random()*1000)+".pdf";

		//generate new PDF and first page
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		doc.addPage(page);
		PDPageContentStream content = new PDPageContentStream(doc, page);

		content.beginText();
		content.setFont(PDType1Font.TIMES_ROMAN, 11);
		content.moveTextPositionByAmount(25, 750);

		//track line length and number of lines to determine new lines and new pages
		int newLineCount = 0;
		int newPageCount = 0;

		//iterate through all sentences
		for(Entry<Double, String> contentString : outputSorted.entrySet()){
			String outputString =  contentString.getValue().replaceAll("[^\\x00-\\x7F]", "");
			String[] tokens = outputString.split(" ");
			content.moveTextPositionByAmount(0, -30);
			newPageCount +=3;
			content.drawString("-");

			//iterate through each token in sentence
			for(String token : tokens){
				newLineCount += token.length();
				content.drawString(token+ " ");

				//check is newline is needed
				if(newLineCount > 80){	
					content.moveTextPositionByAmount(0, -20);
					//counter to track lines for sentences
					newPageCount+= 2;
					newLineCount = 0;
				}
				//check if new page is needed
				if(newPageCount>69){
					content.close();
					page = new PDPage();
					doc.addPage(page);
					content = new PDPageContentStream(doc, page);
					content.beginText();
					content.setFont(PDType1Font.TIMES_ROMAN, 11);
					content.moveTextPositionByAmount(25, 750);
					newLineCount = 0;
					newPageCount = 0;
				}
			}
			content.drawString("(Sentence #: "+contentString.getKey().intValue()+")");
			newLineCount  = 0;
		}


		content.endText();
		content.close();
		doc.save(fileName);
		doc.close();


		System.out.println("Your Summary Created. Enjoy!");

	}
}

