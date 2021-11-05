package bookshop;
/**
 * @author F015011
 * This class is used to construct the paperback object, inheriting from the Book class.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Paperback extends Book {
	private int noOfPages;
	private String condition;

	public Paperback(String type, int ISBN, String title, String language, String genre, Date releaseDate,
			float retailPrice, int quantity, int noOfPages, String condition) {
		super(type, ISBN, title, language, genre, releaseDate, retailPrice, quantity);
		this.setNoOfPages(noOfPages);
		this.setCondition(condition);

	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/**
	 * This method overrides the toString() function to correctly convert and format a paperback book as a string.
	 * @return a string representing an paperback object's attributes
	 */
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		String strDate = dateFormat.format(getReleaseDate());
		return getISBN() + ", " + getType() + ", " + getTitle() + ", " + getLanguage() + ", " + getGenre() + ", "
				+ strDate + ", " + getRetailPrice() + ", " + getQuantity() + ", " + getNoOfPages() + ", "
				+ getCondition();
	}

}
