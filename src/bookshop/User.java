package bookshop;
/**
 * @author F015011
 * This class is used to create the User object for users of the system.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public abstract class User {
	private int userID;
	private String username;
	private String surname;
	private Address address;
	private Role role;

	public User(int userID, String username, String surname, Address address, Role role) {
		this.setUserID(userID);
		this.setUsername(username);
		this.setSurname(surname);
		this.setAddress(address);
		this.setRole(role);
	}
	
	/**
	 * This method is used to read from the stock file and create all book objects in the system based on the test stored in each file.
	 * @return An ArrayList of Books representing the current stock.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ArrayList<Book> createAllBooks() throws FileNotFoundException, ParseException {
		File stock = new File("Stock.txt");
		Scanner scanner = new Scanner(stock);
		ArrayList<Book> books = new ArrayList<Book>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] book = line.split(", ");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date releaseDate = formatter.parse(book[5]);
			switch (book[1]) { // switch based on the type of book, create new object of that type
			case "paperback":
				Paperback paperback = new Paperback(book[1], Integer.parseInt(book[0]), book[2], book[3], book[4],
						releaseDate, Float.parseFloat(book[6]), Integer.parseInt(book[7]), Integer.parseInt(book[8]),
						book[9]);
				books.add(paperback);
				break;
			case "audiobook":
				Audiobook audiobook = new Audiobook(book[1], Integer.parseInt(book[0]), book[2], book[3], book[4],
						releaseDate, Float.parseFloat(book[6]), Integer.parseInt(book[7]), Float.parseFloat(book[8]),
						book[9]);
				books.add(audiobook);
				break;
			case "ebook":
				Ebook ebook = new Ebook(book[1], Integer.parseInt(book[0]), book[2], book[3], book[4], releaseDate,
						Float.parseFloat(book[6]), Integer.parseInt(book[7]), Integer.parseInt(book[8]), book[9]);
				books.add(ebook);
				break;
			default:
				System.out.println("Error: Incorrect type selection");
				break;
			}
		}
		scanner.close();
		System.out.println("done");
		PriceCompare priceCompare = new PriceCompare();
		Collections.sort(books, priceCompare);
		return books;
	}
	
	/**
	 * This method is used to view all files in the system to test the creation of the Book objects using the createAllBooks() function.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static void viewAllBooks() throws FileNotFoundException, ParseException {
		ArrayList<Book> books = createAllBooks();
		for (Book book : books) {
			System.out.println(book.toString());
		}
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
