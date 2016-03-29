
import java.io.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
 
/**
 * The Class PDFReader. Reads in PDF from file and converts to text.
 * Taken from http://radixcode.com/pdfbox-example-code-how-to-extract-text-from-pdf-file-with-java/
 */
public class PDFReader {
    
   /** The parser. */
   private PDFParser parser;
   
   /** The pdf stripper. */
   private PDFTextStripper pdfStripper;
   
   /** The pd doc. */
   private PDDocument pdDoc ;
   
   /** The cos doc. */
   private COSDocument cosDoc ;
   
   /** The Text. */
   private String text ;
   
   /** The file path. */
   private String filePath;
   
   /** The file. */
   private File file;
 
    /**
     * Instantiates a new PDF reader.
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public PDFReader(String filePath) throws FileNotFoundException, IOException{
    	setFilePath(filePath);
    	
        this.pdfStripper = null;
        this.pdDoc = null;
        this.cosDoc = null;
        
        file = new File(filePath);
        parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0
        
        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
    	
    }
    
   /**
    * Converts PDF to text
    *
    * @return text of PDF
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public String ToText(int startPage, int endPage) throws IOException
   {
 
       pdDoc.getNumberOfPages();
       pdfStripper.setStartPage(startPage);
       pdfStripper.setEndPage(endPage);

       text = pdfStripper.getText(pdDoc);
       return text;
   }
 
    /**
     * Sets the file path.
     *
     * @param filePath the new file path
     */
    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    

    
    
    /**
     *  Gets total page count
     *
     * 
     */
    public int totalPages(){
    	return pdDoc.getNumberOfPages();
    }
   
}