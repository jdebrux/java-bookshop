package bookshop;
/**
 * @author F015011
 * This class is used to construct the Book object.
 */
import java.util.Date;

public class Book {
	private String type;
	private int ISBN;
	private String title;
	private String language;
	private String genre;
	private Date releaseDate;
	private float retailPrice;
	private int quantity;

	public Book(String type, int ISBN, String title, String language, String genre, Date releaseDate, float retailPrice,
			int quantity) {
		this.setType(type);
		this.setISBN(ISBN);
		this.setTitle(title);
		this.setLanguage(language);
		this.setGenre(genre);
		this.setReleaseDate(releaseDate);
		this.setRetailPrice(retailPrice);
		this.setQuantity(quantity);
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public float getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
