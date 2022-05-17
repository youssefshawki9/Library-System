import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		Scanner scap = new Scanner(System.in);
		System.out.println("Welcome Mr Admin");
		System.out.println("\nPlease enter the capacity of the library");

		int numberofbooks = scap.nextInt();
		Books[] books = new Books[numberofbooks];
		int lastbookadded = 0;

		System.out.println("\nPlease enter the number of users");
		int numberofusers = scap.nextInt();
		User[] users = new User[numberofusers];
		int lastuseradded = 0;
		
		System.out.println("Please enter books' data");
		lastbookadded += insertBooks(books, lastbookadded);
		
		System.out.println("Please enter users' data");
		lastuseradded += insertUsers(users, numberofusers, lastuseradded);

		for (int i = 0;; i++) {

			Scanner scan = new Scanner(System.in);
			System.out.println("\nWhich one of the following operations would you like to do?" + "\nPlease press..."
					+ "\n1 to insert a book" + "\n2 to insert a user" + "\n3 to borrow a book"
					+ "\n4 to check in a book" + "\n5 to search for a book" + "\n6 to search for an author"
					+ "\n7 to delete a copy of a book" + "\n8 to search for a user" + "\n9 to delete a user"
					+ "\n0 to exit the program");

			int press;

			do {
				press = scan.nextInt();
				switch (press) {
				case 1:
					lastbookadded += insertBooks(books, lastbookadded);
					break;
				case 2:
					lastuseradded += insertUsers(users, numberofusers, lastuseradded);
					// insert user data
					break;
				case 3:
					borrow(books, users, lastbookadded, lastuseradded);
					break;
				case 4:
					checkIn(books, users, lastbookadded, lastuseradded);
					break;
				case 5:
					Scanner s = new Scanner(System.in);
					System.out.println("Please enter book title");
					String title = s.next();
					Books bresult = bookSearch(books, title, lastbookadded);
					System.out.println("Book title: " + bresult.title);
					System.out.println("Book author: " + bresult.author);
					System.out.println("Date of publishing of book: " + bresult.date);
					System.out.println("Number of available copies: " + bresult.copy);
					break;
				case 6:
					Scanner as = new Scanner(System.in);
					System.out.println("Please enter the name of the author");
					String authorname = as.next();
					authorSearch(books, authorname, lastbookadded);
					break;
				case 7:
					bookDelete(books, lastbookadded);
					break;
				case 8:
					Scanner input = new Scanner(System.in);
					System.out.println("Please enter the name of the user");
					String name = input.next();
					User uresult = userSearch(users, name, lastuseradded);
					System.out.println("Username: " + uresult.username);
					System.out.println("Borrowed books: ");
					for (int j = 0; j < uresult.numberofborrowedbooks; j++) {
						System.out.println("Book title: " + uresult.booksborrowed[j].title);
						System.out.println("Number of copies: " + uresult.booksborrowed[j].copy);
					}

					break;

				case 9:
					lastuseradded = userDelete(users, numberofusers, lastuseradded, lastbookadded, books);
					System.out.println("Number of users has changed to " + lastuseradded);
					break;
				case 0:
					case0();
					break;
				}

				if ((press >= 0 && press <= 9) == false) {
					System.out.println("Invalid input, please try again");
				}

			} while ((press >= 0 && press <= 9) == false);

		}

	}

	private static int insertBooks(Books[] books, int lastbookadded) {
		// insert books

		Scanner s = new Scanner(System.in);
		System.out.println("Please insert number of books to be added");
		int newbooks = s.nextInt();
		for (int i = lastbookadded; i < newbooks + lastbookadded; i++) {
			books[i] = readbookinfo();
		}
		return newbooks;

	}

	private static Books readbookinfo() {
		Scanner s = new Scanner(System.in);
		Books book1 = new Books();
		System.out.println("Please enter book title");
		book1.title = s.next();
		System.out.println("Please enter book author");
		book1.author = s.next();
		System.out.println("Please enter date of publishing");
		book1.date = s.next();
		System.out.println("Please enter number of copies available for this book");
		book1.copy = s.nextInt();
		return book1;
	}

	private static int insertUsers(User[] users, int numberofusers, int lastuseradded) {
		// insert user data
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter number of users to be added");
		int newusers = input.nextInt();
		for (int i = lastuseradded; i < newusers + lastuseradded; i++) {
			System.out.println("Please enter name of user no." + (i + 1));

			users[i] = userInfo();
		}
		return newusers;
	}

	private static User userInfo() {
		Scanner input = new Scanner(System.in);
		User userset = new User();
		userset.username = input.next();
		return userset;
	}

	private static void borrow(Books[] books, User[] users, int lastbookadded, int lastuseradded) {
		// borrow books

		Scanner s = new Scanner(System.in);
		Books bookborrowed = new Books();
		System.out.println("Please type the user name");
		String user = s.next();
		boolean finduser = false;
		while (finduser == false) {
			for (int j = 0; j < lastuseradded; j++) {

				if (users[j].username.equals(user)) {
					finduser = true;
					System.out.println("Please insert book title");
					String title = s.next();
					boolean findbook = false;
					while (findbook == false) {
						for (int i = 0; i < lastbookadded; i++) {
							int copy = 0;
							if (books[i].title.equals(title)) {
								findbook = true;
								System.out.println("Please insert number of copies needed");
								copy = s.nextInt();
								while (books[i].copy < copy) {
									System.out.println("Number of copies isn't available."
											+ "\nThe available number of copies is " + books[i].copy);
									copy = s.nextInt();
								}
								System.out.println("Please enter duration of borrowing");
								int duration = s.nextInt();
								while (duration > 13) {
									System.out.println("No more than 13 days allowed");
									duration = s.nextInt();
								}

								books[i].copy -= copy;
								bookborrowed.title = books[i].title;
								bookborrowed.copy = copy;
								bookborrowed.author = books[i].author;
								bookborrowed.date = books[i].date;

								users[j].booksborrowed[users[j].numberofborrowedbooks] = bookborrowed;
								users[j].numberofborrowedbooks++;
								break;
							}
						}
						if (findbook == false) {
							System.out.println(
									"This book doesn't exist in the library. Please enter another book title.");
							title = s.next();
						}
					}
					break;
				}
			}
			if (finduser == false) {
				System.out.println("The user with this name doesn't exist. Please enter another name.");
				user = s.next();
			}
		}
	}

	private static void checkIn(Books[] books, User[] users, int lastbookadded, int lastuseradded) {
		// check in a book

		Scanner s = new Scanner(System.in);
		boolean finduser = false;
		System.out.println("Please type the user name");
		String user = s.next();
		while (finduser == false) {

			for (int j = 0; j < lastuseradded; j++) {

				if (users[j].username.equals(user)) {
					finduser = true;
					System.out.println("Please insert book title");
					String title = s.next();

					boolean findbook = false;
					while (findbook == false) {

						for (int f = 0; f < users[j].numberofborrowedbooks; f++) {

							if (users[j].booksborrowed[f].title.equals(title)) {
								findbook = true;
								System.out.println("Please insert the number of copies");
								int copy = s.nextInt();
								while (users[j].booksborrowed[f].copy < copy) {

									System.out.println("Number of copies isn't available."
											+ "\nThe available number of copies is " + users[j].booksborrowed[f].copy);
									copy = s.nextInt();
								}
								users[j].booksborrowed[f].copy -= copy;

								for (int i = 0; i < lastbookadded; i++) {

									if (books[i].title.equals(title)) {
										books[i].copy += copy;

										if (users[j].booksborrowed[f].copy == 0) {
											for (int q = f; q < users[j].numberofborrowedbooks; q++) {

												users[j].booksborrowed[q] = users[j].booksborrowed[q + 1];
												users[j].booksborrowed[q + 1] = null;
											}
										}
									}
								}
							}
						}
						if (findbook == false) {
							System.out.println(
									"This book doesn't exist in the library. Please enter another book title.");
							title = s.next();
						}
					}
				}
			}
			if (finduser == false) {
				System.out.println("The user with this name doesn't exist. Please enter another name.");
				user = s.next();
			}
		}
	}

	private static Books bookSearch(Books[] books, String title, int lastbookadded) {
		// search a book

		Books bresult = new Books();
		boolean findbook = false;
		while (findbook == false) {

			for (int i = 0; i < lastbookadded; i++) {

				if (books[i].title.equals(title)) {
					findbook = true;
					bresult.title = books[i].title;
					bresult.author = books[i].author;
					bresult.date = books[i].date;
					bresult.copy = books[i].copy;
					break;
				}
			}
			if (findbook == false) {
				Scanner s = new Scanner(System.in);
				System.out.println("This book doesn't exist in the library. Please enter another book title.");
				title = s.next();
			}
		}
		return bresult;
	}
	
	private static void authorSearch(Books[] books, String authorname, int lastbookadded) {
		// search by author
		
		boolean findauthor = false;
		while (findauthor == false) {

		for (int i = 0; i < lastbookadded; i++) {
			if (books[i].author.equals(authorname)) {
				findauthor = true;
				System.out.println("Book Title: " + books[i].title);
				System.out.println("Date of publishing: " + books[i].date);
				System.out.println("No. of available copies: " + books[i].copy + "\n");
			}
		}
		if (findauthor == false) {
			Scanner s = new Scanner(System.in);
			System.out.println("This author has no books in the library. Please enter another author name.");
			authorname = s.next();
		}
		}
	}
	
	private static int bookDelete(Books[] books, int lastbookadded) {
		// delete a copy of a book

		Scanner s = new Scanner(System.in);
		System.out.println("Please insert book title");
		String title = s.next();
		boolean findbook = false;
		int i = 0;
		while (findbook == false) {
			for (i = 0; i < lastbookadded; i++) {

				if (books[i].title.equals(title)) {
					findbook = true;
					System.out.println("Do you want to delete a copy of this book?");
					System.out.println("If yes then press 1" + "\nIf no then press any other number");
					int option = s.nextInt();
					if (option == 1) {
						books[i].copy--;
					}
				}
			}
			if (findbook == false) {
				System.out.println("This book doesn't exist in the library. Please enter another book title.");
				title = s.next();
			}
		}
		return i;
	}

	private static User userSearch(User[] users, String name, int lastuseradded) {
		// search a user and display borrowing history

		User uresult = new User();
		boolean finduser = false;
		while (finduser == false) {
			for (int i = 0; i < lastuseradded; i++) {

				if (users[i].username.equals(name)) {
					finduser = true;
					uresult.username = users[i].username;
					break;
				}
			}
			if (finduser == false) {
				Scanner input = new Scanner(System.in);
				System.out.println("The user with this name doesn't exist. Please enter another name.");
				name = input.next();
			}
		}
		return uresult;
	}

	private static int userDelete(User[] users, int numberOfusers, int lastuseradded, int lastbookadded,
			Books[] books) {
		// delete a user

		System.out.println("Please enter the username ");
		Scanner input = new Scanner(System.in);
		String username = input.next();
		boolean finduser = false;
		while (finduser == false) {
			for (int i = 0; i < lastuseradded; i++) {
				if (users[i].username.equals(username)) {
					finduser = true;
					System.out.println("Do you want to delete this user?");
					System.out.println("If yes then press 1" + "\nIf no then press any other number");
					int option = input.nextInt();
					if (option == 1) {
						if (users[i].numberofborrowedbooks > 0) {
							for (int k = 0; k < users[i].numberofborrowedbooks; k++) {
								for (int z = 0; z < lastbookadded; z++) {
									if (users[i].booksborrowed[k].title.equals(books[z].title))
										books[z].copy += users[i].booksborrowed[k].copy;
								}

							}
						}
						for (int j = i; j < lastuseradded; j++) {
							users[j] = users[j + 1];
							users[j + 1] = null;
						}
						lastuseradded--;
					}
				}
			}
			if (finduser == false) {
				System.out.println("The user with this name doesn't exist. Please enter another name.");
				username = input.next();
			}
		}
		return lastuseradded;
	}

	private static void case0() {
		System.exit(0);
	}

}
