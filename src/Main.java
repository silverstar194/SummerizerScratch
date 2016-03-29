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
		GlobalKeywords globalKeywords = null;
		Helper.loadStopWords();

		PDFReader pdfReader = null;

		Scanner in = new Scanner(System.in);
		System.out.println("=====Please Insert Filepath=====");

		PDFDocument newDocument = null;

		while(newDocument == null){
			System.out.print("Filename: ");

			//get file path from user
			if(in.hasNextLine()){
				pdfReader = new PDFReader(in.nextLine());
			}
			globalKeywords = new GlobalKeywords(pdfReader.ToText(1, pdfReader.totalPages()), pdfReader.totalPages());

			try{
				newDocument = new PDFDocument();
				for(int i=0; i<pdfReader.totalPages()-1; i++){
					newDocument.add(i, new PDFPage(pdfReader.ToText(i+1,i+1), globalKeywords, i+1));
				}

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
		ArrayList<PDFPage> pageList = newDocument.getPages();
		PriorityQueue<Sentence> allSentences = new PriorityQueue<Sentence>(new SentenceScoreComparator());


		//add all sentences to priorityQueue
		for(PDFPage p : pageList){
			PriorityQueue<Sentence> sentenceList = p.getSentenceList();

			for(Sentence s  : sentenceList){
				if(s.getScore() > 0){
					allSentences.add(s);
				}
			}
		}

		int size = allSentences.size();
		for(int i=0; i<size; i++){
			Sentence s =  allSentences.poll();
			System.out.println("Page: "+s.getPage()+" Pos: " +s.getPos()+" Score: "+ s.getScore());
		}
		
		PriorityQueue<Sentence> allSentencesSorted = new PriorityQueue<Sentence>(new SentencePositionComparator());
		
		int size1 = allSentences.size();
		for(int i=0; i<size1/Config.maxCompressRatio; i++){
			Sentence s =  allSentences.poll();
			
			//marked topPercent
			if(i<(size/Config.maxCompressRatio)/Config.topPercent){
				s.setTop(true);
			}
			allSentencesSorted.add(s);
		}
				

		//use priority queue to resort by position and send out

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
				int sentenceCount = allSentencesSorted.size();
		//iterate through all sentences
				int pageCount = 0;
				for(int i=0; i<sentenceCount; i++){
					Sentence output = allSentencesSorted.poll();
					
					//print out page number
					if(pageCount == 0){	
						content.setFont(PDType1Font.TIMES_BOLD, 12);
						content.drawString("Page 1 Summary");
						pageCount = output.getPage();
					}else if(pageCount != output.getPage()){
						content.moveTextPositionByAmount(0, -30);
						newPageCount +=3;
						content.setFont(PDType1Font.TIMES_BOLD, 12);
						content.drawString("Page "+output.getPage()+" Summary");
						pageCount = output.getPage();
					}
					
					//bolds if topPercent sentence
					if(output.checkTop()){
						content.setFont(PDType1Font.TIMES_BOLD, 11);
					}else{
						content.setFont(PDType1Font.TIMES_ROMAN, 11);
					}

					String outputString =  output.getcontent().replaceAll("[^\\x00-\\x7F]", "");
					String[] tokens = outputString.split(" ");
					content.moveTextPositionByAmount(0, -30);
					newPageCount +=3;
					content.drawString("-");
		
					//iterate through each token in sentence
					for(String token : tokens){
						newLineCount += token.length();
						content.drawString(" "+token+ " ");
		
						//check is newline is needed
						if(newLineCount > 70){	
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
							
							if(output.checkTop()){
								content.setFont(PDType1Font.TIMES_BOLD, 11);
							}else{
								content.setFont(PDType1Font.TIMES_ROMAN, 11);
							}
							
							content.moveTextPositionByAmount(25, 750);
							newLineCount = 0;
							newPageCount = 0;
						}
					}
					content.drawString("(Page: "+output.getPage()+" #Sentence: "+output.getPos()+")");
					newLineCount  = 0;
				}
		
				//close resources and write out
				content.endText();
				content.close();
				doc.save(fileName);
				doc.close();


		System.out.println("Your Summary Created. Enjoy!");

	}
}

