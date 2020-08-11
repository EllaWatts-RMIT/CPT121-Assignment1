// 
// StageA.java is a program constructed for Programming 1.
// It is a reservation manager for the fictional art gallery, 'Artopia'.
// The program allows users to view availability, create reservations and 
// view reservations. 
// 

import java.util.*;

public class StageA {
	Scanner console = new Scanner(System.in);
	int remVisTotal = 80; //is this even needed?
	int remVisCollection = 45;
	int remVisExhib = 20;
	int remVisTour = 15;
	float priceCollection = 6.00F;
	float priceExhib = 25.00F;
	float priceTour = 40.00F;
	int reservationNum = 1;
	String reservations = "";
	
	//constructor loads menu
	public StageA() {
		System.out.print("Welcome to the Reservation Manager");
		displayMenu();
	}
	
	//displayMenu loads menu, allows user to access all methods
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
	
	//prints the remaining places in each experience to the console. 
	public void showAvail() {
		System.out.printf("\n\n   Available places\n------------------------\n"
				+ "   %-15s%d\n   %-15s%d\n   %-15s%d", 
				"Collection", remVisCollection, "Exhibition", remVisExhib, "Tour", remVisTour);
		System.out.print("\n\nPress Enter↵ when done.");
		console.nextLine();
		displayMenu();
	}
	
	//takes user inputs to create a new reservation and adds to String of reservations
	public void newRes() {
		System.out.print("\n\n    New reservation\n------------------------");
		String areaSelect = getAreaInput();
		float ticketPrice = 0;
		int remVis = 0;
		if (areaSelect.equalsIgnoreCase("C")) {
			ticketPrice = priceCollection;
			remVis = remVisCollection;
		} else if (areaSelect.equalsIgnoreCase("E")) {
			ticketPrice = priceExhib;
			remVis = remVisExhib;
		} else if (areaSelect.equalsIgnoreCase("T")) {
			ticketPrice = priceTour;
			remVis = remVisTour;
		} else {
			System.out.print("That's not a valid option.\n");
			getAreaInput();
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
				if (areaSelect.equalsIgnoreCase("C")) {
					remVisCollection -= numPlaces;
					experience = "Collection";
				} else if (areaSelect.equalsIgnoreCase("E")) {
					remVisExhib -= numPlaces;
					experience = "Exhibition";
				} else if (areaSelect.equalsIgnoreCase("T")) {
					remVisTour -= numPlaces;
					experience = "Tour";
				}
				System.out.print("\nPlease enter the customer's name: ");
				String name = console.nextLine();
				System.out.print("Please enter the customer's phone: ");
				String phone = console.nextLine();
				System.out.print("Please enter the customer's credit card number "
						+ "(XXXX XXXX XXXX XXXX): ");
				String creditCard = console.nextLine();
		
				System.out.printf("\nReservation details:\n"
						+ "Reservation no.: %03d\n"
						+ "Name:            %s\n"
						+ "Phone            %s\n"
						+ "Card number:     %s\n"
						+ "Experience:      %s\n"
						+ "No. places:      %d\n"
						+ "Cost:            $%.2f", 
						reservationNum, name, phone, creditCard, experience, numPlaces, 
						(numPlaces*ticketPrice));
				
				appendResList(name, phone, creditCard, experience, numPlaces);
				
				reservationNum++;
				System.out.print("\n\nPress Enter↵ when done.");
				console.nextLine();
				displayMenu();
			}
		}

	}
	
	//helper method for making reservation
	public String getAreaInput() {
		System.out.print("\nSelect a reservation area (enter the corresponding key):\n"
				+ " - Collection (C)\n"
				+ " - Exhibition (E)\n"
				+ " - Tour       (T)\n");
		String areaSelect = console.nextLine().trim();
		return areaSelect;
		
	}
	
	//helper method for adding details to String of reservations
	public void appendResList(String name, String phone, String creditCard, String experience, 
			int numPlaces) {
		String appender = "\n";
		if (reservationNum < 10) {
			appender = appender + "00" + reservationNum + "     " + name;
		} else if (reservationNum < 100) {
			appender = appender + "0" + reservationNum + "     " + name;
		} else {
			appender = appender + reservationNum + "     " + name;
		}
		int nameCounter = 0;
		while (nameCounter < (25 - name.length())) {
			appender = appender + " ";
			nameCounter++;
		}
		appender = appender + phone; 
		int phoneCounter = 0;
		while (phoneCounter < (16 - phone.length())) {
			appender = appender + " ";
			phoneCounter++;
		}
		appender = appender + "   " + creditCard + "     " + experience;
		int experienceCounter = 0;
		while (experienceCounter < (15 - experience.length())) {
			appender = appender + " ";
			experienceCounter++;
		}
		appender = appender + numPlaces; 
		reservations = reservations + appender;
	}
	
	//prints the string of all reservations to the console
	public void viewRes() {
		System.out.print("\n\n     Reservations\n------------------------\n");
		if (reservations.length() == 0) {
			System.out.print("\nNo reservations have been made yet.");
		} else {
			System.out.printf("Session:  12.00 - 02.30pm\n"
					+ "%-8s%-25s%-19s%-24s%-15s%s\n"
					+ "-------------------------------------------------------------"
					+ "------------------------------------",
					"No.", "Name", "Phone", "Credit Card", "Experience", "Places");
			System.out.print(reservations);
		}
		System.out.print("\n\nPress Enter↵ when done.");
		console.nextLine();
		displayMenu();
	}
	
	//Exits system when user selects using menu
	public void quitMgr() {
		System.out.print("Program ended.");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		StageA obj = new StageA();
	}
}

