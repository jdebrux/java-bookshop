package bookshop;
/**
 * @author F015011
 * This class is used to construct and organise the GUI for the book management system, in addition to any data processing required within each frame.
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panel_login;
	private JPanel panel_view;
	private JPanel panel_basket;
	private JPanel panel_add;
	private JPanel panel_search;
	ArrayList<User> users;
	ArrayList<Book> books;
	ArrayList<Book> found;
	private User currentUser;
	private Customer currentCustomer;
	private Admin currentAdmin;
	private JComboBox cbUsers;
	public boolean loggedIn = false;
	private JTable tblBooks;
	private DefaultTableModel dtmBooks;
	private DefaultTableModel dtmFound;
	private DefaultTableModel dtmBasket;
	private JComboBox cbType;
	private JTextField tfISBN;
	private JTextField tfTitle;
	private JTextField tfLanguage;
	private JTextField tfGenre;
	private JTextField tfRelease;
	private JLabel lblISBNInput;
	private JLabel lblTitleInput;
	private JLabel lblLanguageInput;
	private JLabel lblGenreInput;
	private JLabel lblReleaseDateInput;
	private JLabel lblTypeInput;
	private JLabel lblRRPInput;
	private JLabel lblQuantityInput;
	private JLabel lblPages;
	private JLabel lblCondition;
	private JLabel lblStockAlert;
	private JLabel lblResult;
	private JButton btnAddBook;
	private JLabel lblAudioFormat;
	private JComboBox cbaFormat;
	private JSpinner spRRP;
	private JSpinner spQuant;
	private JComboBox cbTypeS;
	private JTextField tfGenreS;
	private JTable searchTable;
	private JTextField tfTitleS;
	private JTextField tfLanguageS;
	private JTextField tfISBNS;
	private JTable tblShoppingBasket;
	private JButton btnRemove;
	private JButton btnCancel;
	private JButton btnPayByCard;
	private JButton btnPaypal;
	private JTextField tfCardNumber;
	private JTextField tfSecurityCode;
	private JTextField tfEmail;
	private JLabel lblTitle;
	private JLabel lblISBNVerify;
	private JLabel lblVerifyType;
	private JLabel lblTitleVerify;
	private JLabel lblLanguageVerify;
	private JLabel lblGenreVerify;
	private JLabel lblReleaseDateVerify;
	private JLabel lblRRPVerify;
	private JLabel lblQuantityVerify;
	private JLabel lblVerifyResult;
	private JLabel lblAdditionalVerify;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public MainFrame() throws FileNotFoundException, ParseException {
		setBackground(Color.MAGENTA);
		// VARIABLE INIT
		users = Main.createUsers();
		books = User.createAllBooks();
		found = null;
		String[] usernames = Main.createUsernameArray();

		// JFRAME INIT
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(Color.MAGENTA);
		tabbedPane.setBounds(6, 6, 1280, 720);
		contentPane.add(tabbedPane);

		// LOGIN TAB
		panel_login = new JPanel();
		panel_login.setBackground(Color.WHITE);
		tabbedPane.addTab("Login", null, panel_login, null);
		panel_login.setLayout(null);

		cbUsers = new JComboBox(usernames);
		cbUsers.setForeground(Color.MAGENTA);
		cbUsers.setBounds(554, 278, 150, 27);
		panel_login.add(cbUsers);

		JTextArea taUserInfo = new JTextArea();
		taUserInfo.setBackground(Color.WHITE);
		taUserInfo.setEditable(false);
		taUserInfo.setBounds(6, 6, 231, 118);
		panel_login.add(taUserInfo);

		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.MAGENTA);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedUsername = String.valueOf(cbUsers.getSelectedItem());
				for (User user : users) {
					if (user.getUsername().equals(selectedUsername)) {
						setCurrentUser(user);
						loggedIn = true;
					}
				}
				if (loggedIn) {
					taUserInfo.setText(
							"Current User: " + currentUser.getUsername() + "\nUser ID: " + currentUser.getUserID()
									+ "\nSurname: " + currentUser.getSurname() + "\nRole: " + currentUser.getRole());
				}
				switch (currentUser.getRole()) {
				case admin:
					setCurrentAdmin((Admin) currentUser);
					createAdminMenu();
					break;
				case customer:
					setCurrentCustomer((Customer) currentUser);
					createCustomerMenu();
					break;
				}
			}
		});

		btnLogin.setBounds(554, 307, 150, 46);
		panel_login.add(btnLogin);

		lblTitle = new JLabel("iluffbooks");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.MAGENTA);
		lblTitle.setFont(new Font("Corsiva Hebrew", Font.BOLD, 59));
		lblTitle.setBounds(442, 183, 375, 83);
		panel_login.add(lblTitle);

		// VIEW BOOKS TAB
		panel_view = new JPanel();
		//tabbedPane.addTab("View Books", null, panel_view, null);
		panel_view.setLayout(null);
		panel_view.setVisible(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 90, 1166, 493);
		panel_view.add(scrollPane);

		tblBooks = new JTable();
		scrollPane.setViewportView(tblBooks);

		dtmBooks = new DefaultTableModel();

		JButton btnView = new JButton("View Books");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					books = User.createAllBooks();
				} catch (FileNotFoundException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dtmBooks.setRowCount(0);
				dtmBooks.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				tblBooks.setModel(dtmBooks);
				showBooks(books, dtmBooks);
			}
		});
		btnView.setBounds(518, 16, 222, 58);
		panel_view.add(btnView);

		// SHOPPING BASKET TAB
		panel_basket = new JPanel();
		//tabbedPane.addTab("Shopping Basket", null, panel_basket, null);
		panel_basket.setLayout(null);

		JScrollPane spBasket = new JScrollPane();
		spBasket.setBounds(39, 26, 613, 380);
		panel_basket.add(spBasket);

		tblShoppingBasket = new JTable();
		spBasket.setViewportView(tblShoppingBasket);

		JLabel lblTotal = new JLabel("Total(£):");
		lblTotal.setBounds(549, 409, 61, 16);
		panel_basket.add(lblTotal);

		JLabel lblTotalVal = new JLabel("0.00");
		lblTotalVal.setBounds(608, 409, 61, 16);
		panel_basket.add(lblTotalVal);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = searchTable.getSelectedRow();
				int selectedISBN = (int) searchTable.getModel().getValueAt(row, column);

				currentCustomer.removeBook(selectedISBN);
				dtmBasket.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				tblShoppingBasket.setModel(dtmBasket);
				showBooks(currentCustomer.getShoppingBasket(), dtmBasket);
			}
		});
		btnRemove.setBounds(664, 22, 117, 29);
		panel_basket.add(btnRemove);

		btnCancel = new JButton("Cancel Order");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					currentCustomer.logCancelation();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				currentCustomer.emptyBasket();
				try {
					books = User.createAllBooks(); // refresh stock
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}
				dtmBasket.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				tblShoppingBasket.setModel(dtmBasket);
				showBooks(currentCustomer.getShoppingBasket(), dtmBasket);
			}
		});
		btnCancel.setBounds(664, 52, 117, 29);
		panel_basket.add(btnCancel);

		btnPayByCard = new JButton("Pay By Card");
		btnPayByCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentCustomer.getShoppingBasket().isEmpty()) {
					lblResult.setForeground(Color.red);
					lblResult.setText("Error: empty basket.");
				} else {
					String inputCardNumber = tfCardNumber.getText();
					String inputSecurityNumber = tfSecurityCode.getText();
					if (isNumeric(inputCardNumber) && isNumeric(inputSecurityNumber)) {
						if (inputCardNumber.length() == 6 && inputSecurityNumber.length() == 3) {
							lblResult.setForeground(Color.green);
							float total = totalCalc(dtmBasket);
							lblResult.setText("£" + total + " paid using Credit Card.");
							try {
								Admin.writeToFile(books); // update stock levels
							} catch (IOException e2) {
								e2.printStackTrace();
							}
							try {
								currentCustomer.logPayment("Credit Card");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							currentCustomer.emptyBasket();
							dtmBasket.setRowCount(0);
							dtmFound.setColumnIdentifiers(
									new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE", "RETAIL PRICE",
											"QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
							tblShoppingBasket.setModel(dtmBasket);
							showBooks(currentCustomer.getShoppingBasket(), dtmBasket);
						} else {
							lblResult.setForeground(Color.red);
							lblResult.setText("Error: invalid card number length.");
						}
					} else {
						lblResult.setForeground(Color.red);
						lblResult.setText("Error: card number must be numeric.");
					}
				}

			}
		});
		btnPayByCard.setBounds(664, 265, 467, 29);
		panel_basket.add(btnPayByCard);

		btnPaypal = new JButton("Pay With PayPal");
		btnPaypal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentCustomer.getShoppingBasket().isEmpty()) {
					lblResult.setForeground(Color.red);
					lblResult.setText("Error: empty basket.");
				} else {
					String inputEmail = tfEmail.getText();
					String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(inputEmail);
					if (matcher.matches()) {
						lblResult.setForeground(Color.green);
						float total = totalCalc(dtmBasket);
						lblResult.setText("£" + total + " paid using PayPal.");
						try {
							Admin.writeToFile(books); // update stock levels
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						try {
							currentCustomer.logPayment("PayPal");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						currentCustomer.emptyBasket();
						dtmBasket.setRowCount(0);
						dtmFound.setColumnIdentifiers(
								new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE", "RETAIL PRICE",
										"QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
						tblShoppingBasket.setModel(dtmBasket);
						showBooks(currentCustomer.getShoppingBasket(), dtmBasket);
					} else {
						lblResult.setForeground(Color.red);
						lblResult.setText("Error: invalid email.");
					}
				}
			}
		});
		btnPaypal.setBounds(664, 344, 186, 29);
		panel_basket.add(btnPaypal);

		tfCardNumber = new JTextField();
		tfCardNumber.setBounds(763, 233, 130, 26);
		panel_basket.add(tfCardNumber);
		tfCardNumber.setColumns(16);

		tfSecurityCode = new JTextField();
		tfSecurityCode.setBounds(1001, 233, 130, 26);
		panel_basket.add(tfSecurityCode);
		tfSecurityCode.setColumns(10);

		JLabel lblCardNo = new JLabel("Card Number:");
		lblCardNo.setBounds(673, 238, 104, 16);
		panel_basket.add(lblCardNo);

		JLabel lblSecurityCode = new JLabel("Security Code:");
		lblSecurityCode.setBounds(905, 238, 97, 16);
		panel_basket.add(lblSecurityCode);

		tfEmail = new JTextField();
		tfEmail.setBounds(720, 311, 130, 26);
		panel_basket.add(tfEmail);
		tfEmail.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(674, 316, 61, 16);
		panel_basket.add(lblEmail);

		lblResult = new JLabel(" ");
		lblResult.setBounds(664, 390, 338, 16);
		panel_basket.add(lblResult);

		// ADD BOOK TAB
		panel_add = new JPanel();
		//tabbedPane.addTab("Add Book", null, panel_add, null);
		panel_add.setLayout(null);

		String[] types = { "type...", "paperback", "audiobook", "ebook" };
		cbType = new JComboBox(types);
		cbType.setBounds(360, 62, 110, 27);
		panel_add.add(cbType);

		tfISBN = new JTextField();
		tfISBN.setBounds(360, 101, 130, 26);
		panel_add.add(tfISBN);
		tfISBN.setColumns(10);

		tfTitle = new JTextField();
		tfTitle.setBounds(360, 157, 130, 26);
		panel_add.add(tfTitle);
		tfTitle.setColumns(10);

		tfLanguage = new JTextField();
		tfLanguage.setBounds(360, 217, 130, 26);
		panel_add.add(tfLanguage);
		tfLanguage.setColumns(10);

		tfGenre = new JTextField();
		tfGenre.setBounds(360, 273, 130, 26);
		panel_add.add(tfGenre);
		tfGenre.setColumns(10);

		tfRelease = new JTextField();
		tfRelease.setBounds(360, 326, 130, 26);
		panel_add.add(tfRelease);
		tfRelease.setColumns(10);

		lblISBNInput = new JLabel("ISBN:");
		lblISBNInput.setBounds(165, 106, 61, 16);
		panel_add.add(lblISBNInput);

		lblTitleInput = new JLabel("TITLE:");
		lblTitleInput.setBounds(165, 162, 61, 16);
		panel_add.add(lblTitleInput);

		lblLanguageInput = new JLabel("LANGUAGE:");
		lblLanguageInput.setBounds(165, 222, 96, 16);
		panel_add.add(lblLanguageInput);

		lblGenreInput = new JLabel("GENRE:");
		lblGenreInput.setBounds(165, 278, 54, 16);
		panel_add.add(lblGenreInput);

		lblReleaseDateInput = new JLabel("RELEASE DATE (DD-MM-YYY):");
		lblReleaseDateInput.setBounds(165, 331, 193, 16);
		panel_add.add(lblReleaseDateInput);

		lblTypeInput = new JLabel("TYPE:");
		lblTypeInput.setBounds(165, 66, 61, 16);
		panel_add.add(lblTypeInput);

		lblRRPInput = new JLabel("RRP:");
		lblRRPInput.setBounds(165, 376, 61, 16);
		panel_add.add(lblRRPInput);
		
		SpinnerNumberModel sPriceModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
		spRRP = new JSpinner();
		spRRP.setModel(sPriceModel);
		spRRP.setBounds(360, 371, 130, 26);
		panel_add.add(spRRP);

		lblQuantityInput = new JLabel("QUANTITY:");
		lblQuantityInput.setBounds(165, 433, 83, 16);
		panel_add.add(lblQuantityInput);
		
		SpinnerNumberModel sQuantModel = new SpinnerNumberModel(0, 0, null, 1);
		spQuant = new JSpinner();
		spQuant.setModel(sQuantModel);
		spQuant.setBounds(360, 428, 130, 26);
		panel_add.add(spQuant);

		lblPages = new JLabel("NO. OF PAGES:");
		lblPages.setBounds(561, 106, 96, 16);
		panel_add.add(lblPages);
		lblPages.setVisible(false);

		lblCondition = new JLabel("CONDITION:");
		lblCondition.setBounds(561, 134, 96, 16);
		panel_add.add(lblCondition);
		lblCondition.setVisible(false);

		String[] conditions = { "new", "old" };
		JComboBox cbCondition = new JComboBox(conditions);
		cbCondition.setBounds(657, 130, 81, 27);
		panel_add.add(cbCondition);
		cbCondition.setVisible(false);

		JLabel lblEformat = new JLabel("E-FORMAT:");
		lblEformat.setBounds(561, 134, 83, 16);
		panel_add.add(lblEformat);
		lblEformat.setVisible(false);

		String[] eFormats = { "EPUB", "MOBI", "AZW3", "PDF" };
		JComboBox cbeFormat = new JComboBox(eFormats);
		cbeFormat.setBounds(657, 130, 81, 27);
		panel_add.add(cbeFormat);
		cbeFormat.setVisible(false);

		lblAudioFormat = new JLabel("AUDIO-FORMAT:");
		lblAudioFormat.setBounds(561, 134, 110, 16);
		panel_add.add(lblAudioFormat);
		lblAudioFormat.setVisible(false);

		String[] aFormats = { "MP3", "WMA", "AAC" };
		JComboBox cbaFormat = new JComboBox(aFormats);
		cbaFormat.setBounds(669, 130, 91, 27);
		panel_add.add(cbaFormat);
		cbaFormat.setVisible(false);
		
		SpinnerNumberModel sPageModel = new SpinnerNumberModel(0, 0, null, 1);
		JSpinner spPages = new JSpinner();
		spPages.setModel(sPageModel);
		spPages.setBounds(669, 101, 61, 26);
		panel_add.add(spPages);
		spPages.setVisible(false);

		JLabel lblLength = new JLabel("LENGTH:");
		lblLength.setBounds(561, 106, 61, 16);
		panel_add.add(lblLength);
		lblLength.setVisible(false);

		SpinnerNumberModel sLengthModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
		JSpinner spLength = new JSpinner();
		spLength.setModel(sLengthModel);
		spLength.setBounds(669, 101, 61, 26);
		panel_add.add(spLength);
		spLength.setVisible(false);

		cbType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch ((String) cbType.getSelectedItem()) {
				case "paperback":
					// pages,condition
					spPages.setVisible(true);
					lblPages.setVisible(true);
					lblCondition.setVisible(true);
					cbCondition.setVisible(true);

					lblLength.setVisible(false);
					spLength.setVisible(false);
					lblAudioFormat.setVisible(false);
					cbaFormat.setVisible(false);

					lblEformat.setVisible(false);
					cbeFormat.setVisible(false);
					break;
				case "audiobook":
					// length,format
					lblLength.setVisible(true);
					spLength.setVisible(true);
					lblAudioFormat.setVisible(true);
					cbaFormat.setVisible(true);

					spPages.setVisible(false);
					lblPages.setVisible(false);
					lblCondition.setVisible(false);
					cbCondition.setVisible(false);

					lblEformat.setVisible(false);
					cbeFormat.setVisible(false);
					break;
				case "ebook":
					// pages,format
					spPages.setVisible(true);
					lblPages.setVisible(true);
					lblEformat.setVisible(true);
					cbeFormat.setVisible(true);

					lblLength.setVisible(false);
					spLength.setVisible(false);
					lblAudioFormat.setVisible(false);
					cbaFormat.setVisible(false);

					lblCondition.setVisible(false);
					cbCondition.setVisible(false);
					break;
				}
			}
		});

		btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblVerifyResult.setText("");
				lblISBNVerify.setText("");
				lblTitleVerify.setText("");
				lblLanguageVerify.setText("");
				lblGenreVerify.setText("");
				lblReleaseDateVerify.setText("");
				lblRRPVerify.setText("");
				lblQuantityVerify.setText("");

				boolean validType = !cbType.getSelectedItem().equals("type...");
				if (!validType) {
					lblVerifyType.setText("!");
				}
				boolean validISBN = isNumeric(tfISBN.getText()) && tfISBN.getText().length() == 8;
				if (!validISBN) {
					lblISBNVerify.setText("!");
				}
				boolean validTitle = tfTitle.getText().length() > 0;
				if (!validTitle) {
					lblTitleVerify.setText("!");
				}
				boolean validLanguage = tfLanguage.getText().length() > 0;
				if (!validLanguage) {
					lblLanguageVerify.setText("!");
				}
				boolean validGenre = tfGenre.getText().length() > 0;
				if (!validGenre) {
					lblGenreVerify.setText("!");
				}
				boolean validDate = isCorrectDate(tfRelease.getText());
				if (!validDate) {
					lblReleaseDateVerify.setText("!");
				}
				boolean validRRP = isFloat(spRRP.getValue().toString());// && ((Double)
																		// spRRP.getValue()).floatValue()>0;
				if (!validRRP) {
					lblRRPVerify.setText("!");
				}
				boolean validQuantity = (int) spQuant.getValue() > 0;
				if (!validQuantity) {
					lblQuantityVerify.setText("!");
				}

				boolean validPages = (int) spPages.getValue() > 0;
				boolean validLength = isFloat(spLength.getValue().toString());

				// boolean validLength = spLength.getValue();

				boolean all = validISBN && validTitle && validLanguage && validGenre && validRRP && validQuantity
						&& validDate;
				boolean bookVerify = all && validPages; // ebook and paperback
				boolean audiobookVerify = all && validLength; // audiobook

				if (validType) {
					switch (String.valueOf(cbType.getSelectedItem())) {
					case "paperback": // pages,condition
						if (!validPages) {
							lblAdditionalVerify.setText("!");
						}
						if (bookVerify) {
							try {
								Admin.addBook(Integer.valueOf(tfISBN.getText()),
										String.valueOf(cbType.getSelectedItem()), tfTitle.getText(),
										tfLanguage.getText(), tfGenre.getText(), tfRelease.getText(),
										((Double) spRRP.getValue()).floatValue(), (int) spQuant.getValue(),
										spPages.getValue().toString(), cbCondition.getSelectedItem().toString());
								lblVerifyResult.setForeground(Color.GREEN);
								lblVerifyResult.setText("SUCCESS.");
							} catch (NumberFormatException | FileNotFoundException | ParseException e2) {
								e2.printStackTrace();
							}
						} else {
							lblVerifyResult.setForeground(Color.RED);
							lblVerifyResult.setText("FAIL.");
						}

						break;
					case "audiobook": // length,format
						if (!validLength) {
							lblAdditionalVerify.setText("!");
						}
						if (audiobookVerify) {
							try {
								Admin.addBook(Integer.valueOf(tfISBN.getText()),
										String.valueOf(cbType.getSelectedItem()), tfTitle.getText(),
										tfLanguage.getText(), tfGenre.getText(), tfRelease.getText(),
										((Double) spRRP.getValue()).floatValue(), (int) spQuant.getValue(),
										spLength.getValue().toString(), cbaFormat.getSelectedItem().toString());
								lblVerifyResult.setForeground(Color.GREEN);
								lblVerifyResult.setText("SUCCESS.");
							} catch (NumberFormatException | FileNotFoundException | ParseException e1) {
								e1.printStackTrace();
							}
						} else {
							lblVerifyResult.setForeground(Color.RED);
							lblVerifyResult.setText("FAIL.");
						}
						break;
					case "ebook": // pages,format
						if (!validPages) {
							lblVerifyResult.setForeground(Color.RED);
							lblAdditionalVerify.setText("!");
						}

						if (bookVerify) {
							try {
								Admin.addBook(Integer.valueOf(tfISBN.getText()),
										String.valueOf(cbType.getSelectedItem()), tfTitle.getText(),
										tfLanguage.getText(), tfGenre.getText(), tfRelease.getText(),
										((Double) spRRP.getValue()).floatValue(), (int) spQuant.getValue(),
										spPages.getValue().toString(), cbeFormat.getSelectedItem().toString());
								lblVerifyResult.setForeground(Color.GREEN);
								lblVerifyResult.setText("SUCCESS.");
							} catch (NumberFormatException | FileNotFoundException | ParseException e1) {
								// Audiobook(type, ISBN, title, language, genre, releaseDate, retailPrice,
								// quantity, Float.valueOf(extra1), extra2);
								e1.printStackTrace();
							}
						} else {
							lblVerifyResult.setForeground(Color.RED);
							lblVerifyResult.setText("FAIL.");
						}

						break;
					}
				}

			}
		});
		btnAddBook.setBounds(623, 418, 137, 49);
		panel_add.add(btnAddBook);

		lblISBNVerify = new JLabel("");
		lblISBNVerify.setForeground(Color.RED);
		lblISBNVerify.setBounds(360, 129, 61, 16);
		panel_add.add(lblISBNVerify);

		lblTitleVerify = new JLabel("");
		lblTitleVerify.setForeground(Color.RED);
		lblTitleVerify.setBounds(360, 184, 61, 16);
		panel_add.add(lblTitleVerify);

		lblLanguageVerify = new JLabel("");
		lblLanguageVerify.setForeground(Color.RED);
		lblLanguageVerify.setBounds(360, 245, 61, 16);
		panel_add.add(lblLanguageVerify);

		lblGenreVerify = new JLabel("");
		lblGenreVerify.setForeground(Color.RED);
		lblGenreVerify.setBounds(360, 298, 61, 16);
		panel_add.add(lblGenreVerify);

		lblReleaseDateVerify = new JLabel("");
		lblReleaseDateVerify.setForeground(Color.RED);
		lblReleaseDateVerify.setBounds(360, 350, 61, 16);
		panel_add.add(lblReleaseDateVerify);

		lblRRPVerify = new JLabel("");
		lblRRPVerify.setForeground(Color.RED);
		lblRRPVerify.setBounds(360, 395, 61, 16);
		panel_add.add(lblRRPVerify);

		lblQuantityVerify = new JLabel("");
		lblQuantityVerify.setForeground(Color.RED);
		lblQuantityVerify.setBounds(360, 455, 61, 16);
		panel_add.add(lblQuantityVerify);

		lblVerifyResult = new JLabel("");
		lblVerifyResult.setBounds(623, 469, 209, 16);
		panel_add.add(lblVerifyResult);

		lblAdditionalVerify = new JLabel("");
		lblAdditionalVerify.setBounds(742, 106, 61, 16);
		panel_add.add(lblAdditionalVerify);

		lblVerifyType = new JLabel("");
		lblVerifyType.setForeground(Color.RED);
		lblVerifyType.setBounds(469, 66, 61, 16);
		panel_add.add(lblVerifyType);

		// SEARCH TAB
		panel_search = new JPanel();
		//tabbedPane.addTab("Search", null, panel_search, null);
		panel_search.setLayout(null);

		cbTypeS = new JComboBox(types);
		cbTypeS.setBounds(6, 6, 110, 27);
		panel_search.add(cbTypeS);

		tfGenreS = new JTextField();
		tfGenreS.setBounds(412, 5, 130, 26);
		panel_search.add(tfGenreS);
		tfGenreS.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(31, 90, 923, 493);
		panel_search.add(scrollPane_1);

		searchTable = new JTable();
		scrollPane_1.setViewportView(searchTable);

		dtmFound = new DefaultTableModel();

		JButton btnNewButton = new JButton("Genre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblStockAlert.setText("");
				// ArrayList<Book> foundGenre = null;
				String inputGenre = tfGenreS.getText();
				try {
					found = Customer.searchBooksByGenre(inputGenre, books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}

				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnNewButton.setBounds(422, 32, 117, 29);
		panel_search.add(btnNewButton);

		tfTitleS = new JTextField();
		tfTitleS.setBounds(128, 5, 130, 26);
		panel_search.add(tfTitleS);
		tfTitleS.setColumns(10);

		tfLanguageS = new JTextField();
		tfLanguageS.setBounds(554, 5, 130, 26);
		panel_search.add(tfLanguageS);
		tfLanguageS.setColumns(10);

		tfISBNS = new JTextField();
		tfISBNS.setBounds(270, 5, 130, 26);
		panel_search.add(tfISBNS);
		tfISBNS.setColumns(10);

		JButton btnNewButton_1 = new JButton("Title");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblStockAlert.setText("");
				// ArrayList<Book> foundTitle = null;
				String inputTitle = tfTitleS.getText();
				try {
					found = Customer.searchBooksByTitle(inputTitle);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}

				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnNewButton_1.setBounds(138, 32, 117, 29);
		panel_search.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("ISBN");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblStockAlert.setText("");
				// ArrayList<Book> foundISBN = null;
				try {
					found = Customer.searchBooksByISBN(Integer.valueOf(tfISBNS.getText()), books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}

				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnNewButton_2.setBounds(280, 32, 117, 29);
		panel_search.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Language");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblStockAlert.setText("");
				// ArrayList<Book> foundLanguage = null;
				String inputLanguage = tfLanguageS.getText();
				try {
					found = Customer.searchBooksByLanguage(inputLanguage, books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}

				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnNewButton_3.setBounds(564, 32, 117, 29);
		panel_search.add(btnNewButton_3);

		dtmBasket = new DefaultTableModel();

		JButton btnNewButton_4 = new JButton("Add to Basket");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = searchTable.getSelectedRow();
				int selectedISBN = (int) searchTable.getModel().getValueAt(row, column);
				Book selectedBook = currentCustomer.searchBasket(selectedISBN);
				try {
					if (selectedBook != null && reduceQuant(selectedISBN)) {
						selectedBook.setQuantity(selectedBook.getQuantity() + 1);
					} else if (reduceQuant(selectedISBN)) {
						currentCustomer.addToBasket(selectedISBN);
					}
					dtmBasket.setRowCount(0);
					dtmBasket.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
							"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
					tblShoppingBasket.setModel(dtmBasket);
					showBooks(currentCustomer.getShoppingBasket(), dtmBasket);
					float total = totalCalc(dtmBasket);
					lblTotalVal.setText(String.valueOf(total));
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(966, 86, 117, 29);
		panel_search.add(btnNewButton_4);

		JButton btnType = new JButton("Type");
		btnType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ArrayList<Book> foundType = null;
				try {
					found = Customer.searchBooksByType((String) cbTypeS.getSelectedItem(), books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}

				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnType.setBounds(6, 32, 117, 29);
		panel_search.add(btnType);

		lblStockAlert = new JLabel("");
		lblStockAlert.setBounds(976, 115, 277, 16);
		panel_search.add(lblStockAlert);
		
		SpinnerNumberModel sMinPageModel = new SpinnerNumberModel(0, 0, null, 1);
		JSpinner spMinPages = new JSpinner();
		spMinPages.setModel(sMinPageModel);
		spMinPages.setBounds(696, 5, 114, 26);
		panel_search.add(spMinPages);

		JButton btnMinPges = new JButton("Min Pages");
		btnMinPges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int minPages = (int) spMinPages.getValue();
				try {
					found = Customer.searchBooksByPages(minPages, books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}
				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnMinPges.setBounds(693, 32, 117, 29);
		panel_search.add(btnMinPges);
		
		SpinnerNumberModel sMinLengthModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
		JSpinner spMinLength = new JSpinner();
		spMinLength.setModel(sMinLengthModel);
		spMinLength.setBounds(822, 5, 117, 26);
		panel_search.add(spMinLength);

		JButton btnMinLength = new JButton("Min Length");
		btnMinLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Float minLength = ((Double) spMinLength.getValue()).floatValue();
				try {
					found = Customer.searchBooksByLength(minLength, books);
				} catch (FileNotFoundException | ParseException e1) {
					e1.printStackTrace();
				}
				dtmFound.setRowCount(0);
				dtmFound.setColumnIdentifiers(new Object[] { "ISBN", "TITLE", "GENRE", "LANGUAGE", "RELEASE DATE",
						"RETAIL PRICE", "QUANTITY", "TYPE", "PAGES/LENGTH", "CONDITION/FORMAT" });
				searchTable.setModel(dtmFound);
				showBooks(found, dtmFound);
			}
		});
		btnMinLength.setBounds(822, 32, 117, 29);
		panel_search.add(btnMinLength);
	}
	
	/**
	 * This method is used to reduce the quantity of a given book when a customer adds it to their basket.
	 * @param selectedISBN
	 * @return Boolean value representing a success or failure, representing whether the book is in stock or not.
	 */
	public boolean reduceQuant(int selectedISBN) {
		boolean result = true;
		for (Book book : books) {
			if (book.getISBN() == selectedISBN) {
				if (book.getQuantity() > 0) {
					lblStockAlert.setForeground(Color.green);
					lblStockAlert.setText("Added to basket.");
					book.setQuantity(book.getQuantity() - 1);
				} else {
					lblStockAlert.setForeground(Color.red);
					lblStockAlert.setText("Sorry, selected book is out of stock.");
					result = false;
				}
			}
		}
		return result;
	}
	
	/**
	 * This method is used to calculate the total for a customer's basket based on the quantity and price of their books
	 * @param dtm
	 * @return a float value representing the total price of their books
	 */
	public float totalCalc(DefaultTableModel dtm) {
		float total = 0;
		// iterate over all columns
		for (int i = 0; i < dtm.getRowCount(); i++) {
			total += ((float) dtm.getValueAt(i, 5) * (Integer) dtm.getValueAt(i, 6)); // multiply quantity in basket
																						// with the price, sum to make
																						// total
		}
		return total;
	}
	
	/**
	 * This method is used to present a given ArrayList of books in a given table. The output is different depending on the type of book.
	 * @param books
	 * @param dtm
	 */
	public void showBooks(ArrayList<Book> books, DefaultTableModel dtm) {
		Object[] rowdata = null;
		for (Book book : books) { // output each book in parsed arraylist of books
			switch (book.getType()) { // output depending on the type of book an therefore additional details
			case "paperback":
				Paperback paperback = (Paperback) book;
				rowdata = new Object[] { paperback.getISBN(), paperback.getTitle(), paperback.getGenre(),
						paperback.getLanguage(), paperback.getReleaseDate(), paperback.getRetailPrice(),
						paperback.getQuantity(), paperback.getType(), paperback.getNoOfPages(),
						paperback.getCondition() };
				break;
			case "audiobook":
				Audiobook audiobook = (Audiobook) book;
				rowdata = new Object[] { audiobook.getISBN(), audiobook.getTitle(), audiobook.getGenre(),
						audiobook.getLanguage(), audiobook.getReleaseDate(), audiobook.getRetailPrice(),
						audiobook.getQuantity(), audiobook.getType(), audiobook.getListeningLength(),
						audiobook.getFormat() };
				break;
			case "ebook":
				Ebook ebook = (Ebook) book;
				rowdata = new Object[] { ebook.getISBN(), ebook.getTitle(), ebook.getGenre(), ebook.getLanguage(),
						ebook.getReleaseDate(), ebook.getRetailPrice(), ebook.getQuantity(), ebook.getType(),
						ebook.getNoOfPages(), ebook.getFormat() };
				break;
			default:
				System.out.println("Error: Incorrect type selection");
				break;
			}
			dtm.addRow(rowdata);
		}
	}
	
	/**
	 * This method is used to check if an input value is numeric or not by testing the success of a parse to the Long data type.
	 * @param str
	 * @return Boolean true or false representing the whether the value is numeric (true) or not (false)
	 */
	public static boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
			return true; // valid number
		} catch (NumberFormatException e) {
			return false; // invalid number
		}
	}
	
	/**
	 * This method is used to check if an input value is a float or not by testing the success of a parse to the Float data type.
	 * @param str
	 * @return Boolean true or false representing the whether the value is a float (true) or not (false)
	 */
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true; // valid float value
		} catch (NumberFormatException e) {
			return false; // invalid float value
		}
	}
	
	/**
	 * This method is used to check if an input value is in the correct date format or or not by testing the success of a parse to the date format.
	 * @param strDate
	 * @return Boolean true or false representing the whether the value is in the correct date format (true) or not (false)
	 */
	public static boolean isCorrectDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(false);
		try {
			Date date = formatter.parse(strDate);
		} catch (ParseException e) {
			return false; // invalid date format
		}
		return true; // valid date format
	}
	
	/**
	 * This method is used to display and hide the correct menu options from/to a user based on their role as an Admim.
	 */
	public void createAdminMenu() {
		tabbedPane.remove(panel_basket);
		tabbedPane.remove(panel_search);
		if (tabbedPane.indexOfComponent(panel_add) == -1) {
			tabbedPane.addTab("Add Book", null, panel_add, null);
		}
		if (tabbedPane.indexOfComponent(panel_view) == -1) {
			tabbedPane.addTab("View Books", null, panel_view, null);
		}
	}
	
	/**
	 * This method is used to display and hide the correct menu options from/to a user based on their role as a Customer.
	 */
	public void createCustomerMenu() {
		tabbedPane.remove(panel_add);
		tabbedPane.remove(panel_view);
		if (tabbedPane.indexOfComponent(panel_search) == -1) {
			tabbedPane.addTab("Search", null, panel_search, null);
		}
		if (tabbedPane.indexOfComponent(panel_basket) == -1) {
			tabbedPane.addTab("Shopping Basket", null, panel_basket, null);
		}
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public Admin getCurrentAdmin() {
		return currentAdmin;
	}

	public void setCurrentAdmin(Admin currentAdmin) {
		this.currentAdmin = currentAdmin;
	}
}
