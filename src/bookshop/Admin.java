package bookshop;
/**
 * This class is used construct the Admin object, inheriting from the User class
 * @author F015011
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin extends User {

	public Admin(int userID, String username, String surname, Address address, Role role) {
		super(userID, username, surname, address, role);

	}
	
	/**
	 * This method is used to add a new book to the stock file.
	 * @param ISBN
	 * @param type
	 * @param title
	 * @param language
	 * @param genre
	 * @param releaseDate
	 * @param retailPrice
	 * @param quantity
	 * @param extra1 used to store extra information depending on book type, passed as a string and converted in the method due to conflicting data types
	 * @param extra2 used to store extra information depending on book type, passed as a string and converted in the method due to conflicting data types
	 * @throws FileNotFoundException 
	 * @throws ParseException
	 */
	public static void addBook(int ISBN, String type, String title, String language, String genre, String releaseDate,
			float retailPrice, int quantity, String extra1, String extra2)
			throws FileNotFoundException, ParseException {
		// create all books as objects
		ArrayList<Book> books = createAllBooks();
		boolean found = false;
		for (Book book : books) {
			// System.out.println(book.getTitle());
			if (book.getISBN() == ISBN) { // search book titles for match
				int storedQuantity = book.getQuantity();
				storedQuantity++; // increment quantity for those already in the file
				book.setQuantity(storedQuantity);
				found = true;
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date releaseDateF = formatter.parse(releaseDate);
		if (!found) {
			switch (type) {
			case "paperback":
				// create Paperback
				Paperback paperback = new Paperback(type, ISBN, title, language, genre, releaseDateF, retailPrice,
						quantity, Integer.valueOf(extra1), extra2);
				books.add(paperback);
				break;
			case "ebook":
				// create Ebook
				Ebook ebook = new Ebook(type, ISBN, title, language, genre, releaseDateF, retailPrice, quantity,
						Integer.valueOf(extra1), extra2);
				books.add(ebook);
				break;
			case "audiobook":
				// create Audiobook
				Audiobook audiobook = new Audiobook(type, ISBN, title, language, genre, releaseDateF, retailPrice,
						quantity, Float.valueOf(extra1), extra2);
				books.add(audiobook);
				break;
			default:
				System.out.println("Incorrect type entered.");
				break;
			}
		}
		try {
			writeToFile(books);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to write the stock, after any potential changes have been made, to the stock file.
	 * @param books
	 * @throws IOException
	 */
	public static void writeToFile(ArrayList<Book> books) throws IOException {
		FileWriter writer = new FileWriter("Stock.txt", false);
		writer.write(""); //clear file
		FileWriter writer2 = new FileWriter("Stock.txt", true);
		for (Book book : books) {
			writer2.write(book.toString() + "\n"); //write all books to file
		}
		System.out.println("Successfully wrote to file.");
		writer2.close();
		writer.close();
	}

}
