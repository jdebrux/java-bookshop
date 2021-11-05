package bookshop;
/**
 * @author F015011
 * This class is used to sort the books stored based on their retail price.
 */
import java.util.Comparator;

public class PriceCompare implements Comparator<Book> {
	/**
	 * This method compares the retail prices of two given books
	 * @return An integer; 1 if the first book's retail price is greater, and a -1 if the second's is.
	 */
	public int compare(Book b1, Book b2) {
		if (b2.getRetailPrice() > b1.getRetailPrice())
			return -1;
		if (b1.getRetailPrice() > b2.getRetailPrice())
			return 1;
		else
			return 0;
	}
}
