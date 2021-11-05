package bookshop;
/**
 * @author F015011
 * This class is used to construct the ebook object, inheriting from the Book class
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ebook extends Book{
	private int noOfPages;
	private String format;

	public Ebook(String type,int ISBN, String title, String language, String genre, Date releaseDate, float retailPrice,
			int quantity, int noOfPages, String format) {
		super(type, ISBN, title, language, genre, releaseDate, retailPrice, quantity);
		this.setNoOfPages(noOfPages);
		this.setFormat(format);
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * This method overrides the toString() function to correctly convert and format an ebook as a string.
	 * @return a string representing an ebook object's attributes
	 */
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		String strDate = dateFormat.format(getReleaseDate());
		return getISBN()+", "+getType()+", "+getTitle()+", "+getLanguage()+", "+getGenre()+", "+strDate+", "+getRetailPrice()+", "+getQuantity()+", "+getNoOfPages()+", "+getFormat();
	}

}
