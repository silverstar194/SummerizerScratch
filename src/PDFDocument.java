import java.util.ArrayList;

public class PDFDocument {
	

/**
 * The Class PDFPage. Holds the extracted PDF pages
 */
	
	private ArrayList<PDFPage> pages;
	private int pageCount;
	
	
	public PDFDocument(){
		pages = new ArrayList<>();
	}
	
	/*Getters*/
	public ArrayList<PDFPage> getPages(){
		return pages;
	}
	

	public PDFPage getPage(int pageNum){
		return pages.get(pageNum);
	
	}
	
	public int getPageCount(){
		return pageCount;
	}
	
	
	/*Setters*/
	public void setPages(ArrayList<PDFPage> pages){
		pageCount += pages.size();
		this.pages = pages;
	}
	

	public void add(int pageNum, PDFPage page){
		pages.add(page);
		pageCount++;
	}


}
