package bookshop;
/**
 * This class is used to construct the audiobook object, inheriting from the book class
 * @author F015011
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audiobook extends Book {
	private Float listeningLength;
	private String format;

	public Audiobook(String type, int ISBN, String title, String language, String genre, Date releaseDate,
			float retailPrice, int quantity, Float listeningLength, String format) {
		super(type, ISBN, title, language, genre, releaseDate, retailPrice, quantity);
		this.setListeningLength(listeningLength);
		this.setFormat(format);
	}

	public Float getListeningLength() {
		return listeningLength;
	}

	public void setListeningLength(Float listeningLength) {
		this.listeningLength = listeningLength;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * This method overrides the toString() function to correctly convert and format an audiobook as a string
	 * @return a string representing an ebook object's attributes
	 */
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
		String strDate = dateFormat.format(getReleaseDate());
		return getISBN() + ", " + getType() + ", " + getTitle() + ", " + getLanguage() + ", " + getGenre() + ", "
				+ strDate + ", " + getRetailPrice() + ", " + getQuantity() + ", " + getListeningLength() + ", "
				+ getFormat();
	}
}
