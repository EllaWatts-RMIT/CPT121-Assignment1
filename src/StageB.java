// 
// StageB.java is a program constructed for Programming 1.
// It is a reservation manager for the fictional art gallery, 'Artopia'.
// The program allows users to view availability, create reservations and 
// view reservations. In addition, it also allows users to choose from 
// 3 different session times across a day.
// 
import java.util.*;

public class StageB {
	Scanner console = new Scanner(System.in);
	
	int[] availCollection = {45, 45, 45};
	int[] availExhibition = {20, 20, 20};
	int[] availTour       = {15, 15, 15};
	
	double[] priceCollection = { 2.00,  6.00,  5.00};
	double[] priceExhibition = {15.00, 25.00, 20.00};
	double[] priceTour       = {30.00, 40.00, 35.00};
	
	int reservationNum = 1;
	String reservations = "";
	
	//constructor calls the menu function 
	public StageB() {
		System.out.print("Welcome to the Reservation Manager");
		displayMenu();
	}
	
	//displays the menu where users can access all the methods. 
	public void displayMenu() {
		System.out.print("\n\n         Menu\n------------------------\n"
				+ " - Show Availability (A)\n"
				+ " - New reservation   (N)\n"
				+ " - View reservations (V)\n"
				+ " - Quit              (Q)\n"
				+ "Select a menu item (enter the corresponding key): ");
		String menuSelect = console.nextLine().trim();
		if (menuSelect.equalsIgnoreCase("A")) {
			showAvail();
		} else if (menuSelect.equalsIgnoreCase("N")) {
			newRes();
		} else if (menuSelect.equalsIgnoreCase("V")) {
			viewRes();
		} else if (menuSelect.equalsIgnoreCase("Q")) {
			quitMgr();
		} else {
			System.out.print("\n" + menuSelect + " isn't a valid choice. Please try again.");
			displayMenu();
		}
	}
	
	//prints all available places, tabulated, to the console
	public void showAvail() {
		System.out.printf("\n\n   Available places\n------------------------\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n", 
				"Session", "Collection", "Exhibition", "Tour",
				"09.30-11.30am", availCollection[0], availExhibition[0], availTour[0],
				"12.00-02.30pm", availCollection[1], availExhibition[1], availTour[1],
				"03.00-05.00pm", availCollection[2], availExhibition[2], availTour[2]);
		System.out.print("\n\nPress Enter↵ when done.");
		console.nextLine();
		displayMenu();
	}
	
	//this method is called when users want to add a new reservation
	//it takes user inputs through the console and stores in the reservation String
	public void newRes() {
		System.out.print("\n\n    New reservation\n------------------------");
		String seshEx = getSeshExInput();
		int index = getIndex(seshEx);
		double ticketPrice = 0;
		int remVis = 0;
		if (seshEx.charAt(1) == 'C') {
			ticketPrice = priceCollection[index];
			remVis = availCollection[index];
		} else if (seshEx.charAt(1) == 'E') {
			ticketPrice = priceExhibition[index];
			remVis = availExhibition[index];
		} else if (seshEx.charAt(1) == 'T') {
			ticketPrice = priceTour[index];
			remVis = availTour[index];
		} else {
			System.out.print("That's not a valid option.\n");
			getSeshExInput();
		}
		
		System.out.print("How many places should be reserved? " );
		int numPlaces = console.nextInt(); 
		console.nextLine();
		while (numPlaces > remVis) {
			System.out.print("Only " + remVis + " places remaining. Would you like to try again"
					+ " with a lower number? (Y/N) ");
			String tryAgain = console.nextLine().trim();
			if (tryAgain.equalsIgnoreCase("N")) {
				System.out.print("Reservation cancelled");
				displayMenu();
			} else if (tryAgain.equalsIgnoreCase("Y")) {
				System.out.print("How many places should be reserved? ");
				numPlaces = console.nextInt();
				console.nextLine();
			} else {
				System.out.print("That's not a valid input. Enter Y if you would like to try "
						+ "again, or N if you would like to return to the menu. ");
				tryAgain = console.nextLine().trim();
			}
		}
		if (numPlaces <= remVis) {
			System.out.printf("Total cost:\n " + numPlaces + " x $%.2f = $%.2f", ticketPrice, 
					(numPlaces*ticketPrice));
			System.out.print("\nConfirm reservation? (Y/N) ");
			String confirmation = console.nextLine().trim();
			if (confirmation.equalsIgnoreCase("N")) {
				System.out.println("Reservation cancelled");
				displayMenu();
			} else if (confirmation.equalsIgnoreCase("Y")) {
				String experience = "";
				if (seshEx.charAt(1) == 'C') {
					availCollection[index] -= numPlaces;
					experience = "Collection";
				} else if (seshEx.charAt(1) == 'E') {
					availExhibition[index] -= numPlaces;
					experience = "Exhibition";
				} else if (seshEx.charAt(1) == 'T') {
					availTour[index] -= numPlaces;
					experience = "Tour";
				}
				System.out.print("\nPlease enter the customer's name: ");
				String name = console.nextLine();
				System.out.print("Please enter the customer's phone: ");
				String phone = console.nextLine();
				System.out.print("Please enter the customer's credit card number "
						+ "(XXXX XXXX XXXX XXXX): ");
				String creditCard = console.nextLine();
				
				String session = "";
				switch (index) {
					case 0:
						session = "Morning";
						break;
					case 1:
						session = "Lunch";
						break;
					case 2:
						session = "Afternoon";
						break;
				}
		
				System.out.printf("\nReservation details:\n"
						+ "Reservation no.: %03d\n"
						+ "Name:            %s\n"
						+ "Phone            %s\n"
						+ "Card number:     %s\n"
						+ "Session:         %s\n"
						+ "Experience:      %s\n"
						+ "No. places:      %d\n"
						+ "Cost:            $%.2f", 
						reservationNum, name, phone, creditCard, session, experience, 
						numPlaces, (numPlaces*ticketPrice));
				
				appendResList(name, phone, creditCard, session, experience, numPlaces);
				
				reservationNum++;
				System.out.print("\n\nPress Enter↵ when done.");
				console.nextLine();
				displayMenu();
			}
		}
	}
	
	//helper method for making reservation
	public String getSeshExInput() {
		System.out.print("\nSelect a session time (enter the corresponding key):\n"
				+ " - Morning   (M)\n"
				+ " - Lunch     (L)\n"
				+ " - Afternoon (A)\n");
		String seshEx = console.nextLine().trim().toUpperCase();
		System.out.print("\nSelect a reservation area (enter the corresponding key):\n"
				+ " - Collection (C)\n"
				+ " - Exhibition (E)\n"
				+ " - Tour       (T)\n");
		seshEx = seshEx + console.nextLine().trim().toUpperCase();
		return seshEx;
	}
	
	//helper method for indexing morning, lunch or afternoon
	public int getIndex(String seshEx) {
		int index = 0;
		if (seshEx.charAt(0) == 'L') {
			index = 1;
		} else if (seshEx.charAt(0) == 'A') {
			index = 2;
		}
		return index;
	}
	
	//helper method to append reservation to String of reservations
	public void appendResList(String name, String phone, String creditCard, String session,
			String experience, int numPlaces) {
		String appender = "\n";
		if (reservationNum < 10) {
			appender = appender + "00" + reservationNum + "   " + name;
		} else if (reservationNum < 100) {
			appender = appender + "0" + reservationNum + "   " + name;
		} else {
			appender = appender + reservationNum + "   " + name;
		}
		int nameCounter = 0;
		while (nameCounter < (23 - name.length())) {
			appender = appender + " ";
			nameCounter++;
		}
		appender = appender + phone; 
		int phoneCounter = 0;
		while (phoneCounter < (13 - phone.length())) {
			appender = appender + " ";
			phoneCounter++;
		}
		appender = appender + "   " + creditCard + "     " + session;
		int sessionCounter = 0;
		while (sessionCounter < (14 - session.length())) {
			appender = appender + " ";
			sessionCounter++;
		}
		appender = appender + experience;
		int experienceCounter = 0;
		while (experienceCounter < (15 - experience.length())) {
			appender = appender + " ";
			experienceCounter++;
		}
		appender = appender + numPlaces; 
		reservations = reservations + appender;
	}
	
	//exits the program when the user wishes
	public void quitMgr() {
		System.out.print("Program ended.");
		System.exit(0);
	}
	
	//prints the tabulated reservations to the console 
	public void viewRes() {
		System.out.print("\n\n     Reservations\n------------------------\n");
		if (reservations.length() == 0) {
			System.out.print("\nNo reservations have been made yet.");
		} else {
			System.out.printf("%-6s%-23s%-16s%-24s%-14s%-15s%s\n"
					+ "-------------------------------------------------------------"
					+ "-------------------------------------------",
					"No.", "Name", "Phone", "Credit Card", "Session", "Experience", "Places");
			System.out.print(reservations);
		}
		System.out.print("\n\nPress Enter↵ when done.");
		console.nextLine();
		displayMenu();
	}
	
	public static void main(String[] args) {
		StageB obj = new StageB();
	}
}