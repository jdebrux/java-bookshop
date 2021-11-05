package bookshop;
/**
 * @author F015011
 * This class is used to construct the Customer object, inheriting from the User class
 */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Customer extends User {
	private ArrayList<Book> shoppingBasket;

	public Customer(int userID, String username, String surname, Address address, Role role,
			ArrayList<Book> shoppingBasket) {
		super(userID, username, surname, address, role);
		this.setShoppingBasket(shoppingBasket);
	}

	/**
	 * This method is used to search the books in the system based on their title.
	 * @param title
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByTitle(String title) throws FileNotFoundException, ParseException {
		ArrayList<Book> books = createAllBooks();
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getTitle().contains(title)) {
				found.add(book);
			}
		}
		return found;
	}

	/**
	 * This method is used to search the books in the system based on their ISBN.
	 * @param ISBN
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByISBN(int ISBN, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		// ArrayList<Book> books = createAllBooks();
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getISBN() == ISBN) {
				found.add(book);
			}
		}
		return found;
	}
	
	/**
	 * This method is used to search the paperback and e-books in the system based on their number of pages.
	 * @param pages
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByPages(int pages, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getType().equals("paperback")) {
				Paperback paperback = (Paperback) book;
				if (paperback.getNoOfPages() > pages) {
					found.add(paperback);
				}
			} else if (book.getType().equals("ebook")) {
				Ebook ebook = (Ebook) book;
				if (ebook.getNoOfPages() > pages) {
					found.add(ebook);
				}
			}
		}
		return found;
	}
	
	/**
	 * This method is used to search the audio books in the system based on their length.
	 * @param length
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByLength(Float length, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getType().equals("audiobook")) {
				Audiobook audiobook = (Audiobook) book;
				if (audiobook.getListeningLength() > length) {
					found.add(audiobook);
				}
			}
		}
		return found;
	}
	
	/**
	 * This method is used to search the books in the system based on their type.
	 * @param type
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByType(String type, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		// ArrayList<Book> books = createAllBooks();
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getType().equals(type)) {
				found.add(book);
			}
		}
		return found;
	}

	/**
	 * This method is used to search the books in the system based on their genre.
	 * @param genre
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByGenre(String genre, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		// ArrayList<Book> books = createAllBooks();
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getGenre().equals(genre)) {
				found.add(book);
			}
		}
		return found;
	}
	
	/**
	 * This method is used to search the books in the system based on their language.
	 * @param language
	 * @param books
	 * @return ArrayList of found books
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> searchBooksByLanguage(String language, ArrayList<Book> books)
			throws FileNotFoundException, ParseException {
		// ArrayList<Book> books = createAllBooks();
		ArrayList<Book> found = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getLanguage().equals(language)) {
				found.add(book);
			}
		}
		return found;
	}
	
	/**
	 * This method is used to search the customer's shopping basket for a given book
	 * @param ISBN
	 * @return foundBook will be either the book found or null if it is not in their basket.
	 */
	public Book searchBasket(int ISBN) {
		Book foundBook = null;
		for (Book book : shoppingBasket) {
			if (book.getISBN() == ISBN) {
				foundBook = book;
			}
		}
		return foundBook;
	}
	
	/**
	 * This method will be used to add a given book to the user's basket based on the ISBN.
	 * @param ISBN
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void addToBasket(int ISBN) throws FileNotFoundException, ParseException {
		ArrayList<Book> books = createAllBooks();
		for (Book book : books) {
			if (book.getISBN() == ISBN) {
				book.setQuantity(1);
				shoppingBasket.add(book);
			}
		}
	}
	
	/**
	 * This method will be used to log any payments made by customers to the log file.
	 * @param paymentMethod
	 * @throws IOException
	 */
	public void logPayment(String paymentMethod) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		String nowFormatted = now.format(dtf);
		FileWriter writer = new FileWriter("ActivityLog.txt", true);
		for (Book book : shoppingBasket) {
			writer.write(this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + book.getISBN() + ", "
					+ book.getRetailPrice() + ", " + book.getQuantity() + ", purchased, " + paymentMethod + ", "
					+ nowFormatted + "\n");
		}
		writer.close();
	}
	
	/**
	 * This method will be used to log any cancellations made by customers to the log file.
	 * @throws IOException
	 */
	public void logCancelation() throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		String nowFormatted = now.format(dtf);
		FileWriter writer = new FileWriter("ActivityLog.txt", true);
		for (Book book : shoppingBasket) {
			writer.write(this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + book.getISBN() + ", "
					+ book.getRetailPrice() + ", " + book.getQuantity() + ", cancelled, , " + nowFormatted + "\n");
		}
		writer.close();
	}
	
	/**
	 * This method will be used to remove a given book from a customer's basket
	 * @param ISBN
	 */
	public void removeBook(int ISBN) {
		Book found = null;
		for (Book book : shoppingBasket) {
			if (book.getISBN() == ISBN) {
				found = book;
			}
		}
		shoppingBasket.remove(found);
	}
	
	/*
	 * This method will be used to empty the user's basket  y removing all stored books in the ArrayList
	 */
	public void emptyBasket() {
		shoppingBasket.clear();
		// log
	}

	public ArrayList<Book> getShoppingBasket() {
		return shoppingBasket;
	}

	public void setShoppingBasket(ArrayList<Book> shoppingBasket) {
		this.shoppingBasket = shoppingBasket;
	}
}
