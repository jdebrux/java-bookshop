package bookshop;
/**
 * This class is used to create and view the users of the system
 * @author F015011
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	/**
	 * This method reads from the user file and creates instances of User objects based on their role,
	 * @param null
	 * @return ArrayList of users of the system
	 */
	public static ArrayList<User> createUsers() throws FileNotFoundException {
		File userAccounts = new File("UserAccounts.txt");
		Scanner scanner = new Scanner(userAccounts);
		ArrayList<User> users = new ArrayList<User>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] user = line.split(", ");
			Address address = new Address(Integer.parseInt(user[3]), user[4], user[5]);
			ArrayList<Book> emptyBasket = new ArrayList<Book>();
			switch (user[6]) {
			case "admin":
				Admin admin = new Admin(Integer.parseInt(user[0]), user[1], user[2], address, Role.admin);
				users.add(admin);
				break;
			case "customer":
				Customer customer = new Customer(Integer.parseInt(user[0]), user[1], user[2], address, Role.customer,
						emptyBasket);
				users.add(customer);
				break;
			default:
				System.out.println("Error: Incorrect user selection");
				break;
			}
		}
		scanner.close();
		return users;
	}
	
	/**
	 * This method was used for testing purposes to see all users recognised using the createUser() method
	 * @param null
	 * @return void
	 */
	public static void viewAllUsers() throws FileNotFoundException {
		ArrayList<User> users = createUsers();
		for (User user : users) {
			System.out.println(user.getUsername());
		}
	}

	/**
	 * This method is used to get all the usernames of the users for the system and place them in an array,
	 * @param null
	 * @return A string array of usernames
	 */
	public static String[] createUsernameArray() throws FileNotFoundException {
		ArrayList<User> users = createUsers();
		String[] usernameArray = new String[users.size()];
		for (int i = 0; i < usernameArray.length; i++) {
			usernameArray[i] = users.get(i).getUsername();
		}
		return usernameArray;
	}

}
