import java.util.*;

public class ReservationMgr {
	
	private int[] availCollection = new int[3];
	private int[] availExhibition = new int[3];
	private int[] availTour = new int[3];
	
	private double[] priceCollection = { 2.00,  6.00,  5.00};
	private double[] priceExhibition = {15.00, 25.00, 20.00};
	private double[] priceTour       = {30.00, 40.00, 35.00};
	
	private int[] reservationPlaces;
	private String[] reservationDetails;
	private int reservationNum = 1;
	private int numReservations = 0;
	private int[] cannedReservations;
	private int nextCannedRes = 0;
	
	static Scanner console = new Scanner(System.in);
	
	//constructor takes inputs to initialize avail arrays and reservation details arrays
	public ReservationMgr(int totalVisCollection, int totalVisExhibition, 
			int totalVisTour) {
		for (int i = 0; i < availCollection.length; i++) {
			availCollection[i] = totalVisCollection;
		}
		for (int i = 0; i < availExhibition.length; i++) {
			availExhibition[i] = totalVisExhibition;
		}
		for (int i = 0; i < availTour.length; i++) {
			availTour[i] = totalVisTour;
		}
		int arrayLength = totalVisCollection + totalVisExhibition + totalVisTour;
		reservationPlaces = new int[arrayLength];
		reservationDetails = new String[arrayLength];
		cannedReservations = new int[arrayLength];
	}
	
	//prints a table of available places (all sessions and areas) to console
	public void showAvail() {
		System.out.printf("\n\n   Available places\n------------------------\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n"
				+ "%-14s %-12s %-12s %-12s\n", 
				"Session", "Collection", "Exhibition", "Tour",
				"09.30-11.30am", 
				this.availCollection[0], this.availExhibition[0], this.availTour[0],
				"12.00-02.30pm", 
				this.availCollection[1], this.availExhibition[1], this.availTour[1],
				"03.00-05.00pm", 
				this.availCollection[2], this.availExhibition[2], this.availTour[2]);
		System.out.print("\nPress Enter↵ when done.");
		console.nextLine();
	}
	
	//takes user inputs of reservation details and stores as a new reservation
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
		}
		
		System.out.print("How many places should be reserved? " );
		int numPlaces = console.nextInt(); 
		console.nextLine();
		while (numPlaces > remVis) {
			System.out.print("Only " + remVis + " places remaining. Would you like to try "
					+ "again with a lower number? (Y/N) ");
			String tryAgain = console.nextLine().trim();
			if (tryAgain.equalsIgnoreCase("N")) {
				System.out.print("Reservation cancelled");
				break;
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
			System.out.printf("Total cost:\n " + numPlaces + " x $%.2f = $%.2f", 
					ticketPrice, (numPlaces*ticketPrice));
			System.out.print("\nConfirm reservation? (Y/N) ");
			String confirmation = console.nextLine().trim();
			if (confirmation.equalsIgnoreCase("N")) {
				System.out.println("Reservation cancelled");
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
				
				addResArray(name, phone, creditCard, session, experience, numPlaces);
				
				reservationNum++;
				System.out.print("\n\nPress Enter↵ when done.");
				console.nextLine();
			}
		}
	}
	
	//helper method for making reservation
	private String getSeshExInput() {
		System.out.print("\nSelect a session time (enter the corresponding key):\n"
				+ " - Morning   (M)\n"
				+ " - Lunch     (L)\n"
				+ " - Afternoon (A)\n");
		String seshEx = console.nextLine().trim().toUpperCase();
		if (seshEx.charAt(0) != 'M' && seshEx.charAt(0) !='L' && seshEx.charAt(0) != 'A') {
			System.out.print("That's not a valid input. Please try again.");
			seshEx = getSeshExInput();
			return seshEx;
		}
		System.out.print("\nSelect a reservation area (enter the corresponding key):\n"
				+ " - Collection (C)\n"
				+ " - Exhibition (E)\n"
				+ " - Tour       (T)\n");
		seshEx = seshEx + console.nextLine().trim().toUpperCase();
		if (seshEx.charAt(1) != 'C' && seshEx.charAt(1) !='E' && seshEx.charAt(1) != 'T') {
			System.out.print("That's not a valid input. Please try again.");
			seshEx = getSeshExInput();
			return seshEx;
		}
		return seshEx;
	}
	
	//helper method for indexing morning, lunch or afternoon
	private int getIndex(String seshEx) {
		int index = 0;
		if (seshEx.charAt(0) == 'L') {
			index = 1;
		} else if (seshEx.charAt(0) == 'A') {
			index = 2;
		}
		return index;
	}
	
	//helper method to add reservation to array of reservation strings
	private void addResArray(String name, String phone, String creditCard, String session,
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
		reservationDetails[numReservations] =  appender;
		reservationPlaces[numReservations] = numPlaces;
		
		numReservations++;
		
		if (numReservations >= reservationDetails.length) {
			reservationDetails = expandArray(reservationDetails);
			reservationPlaces = expandArray(reservationPlaces);
			cannedReservations = expandArray(cannedReservations);
		}
	}
	
	//helper method for expanding a String array - necessary if a large 
	//number of reservations are made and cancelled
	private String[] expandArray(String[] array) {
		String[] newArray = new String[array.length + 1];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	//helper method, performs same function as above method but for int arrays
	private int[] expandArray(int[] array) {
		int[] newArray = new int[array.length + 1];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	//prints the tabulated reservations to the console 
	public void viewRes() {
		System.out.print("\n\n     Reservations\n------------------------\n");
		resList();
		System.out.print("\n\nPress Enter↵ when done.");
		console.nextLine();
	}
	
	//when called, adds the reservation's index to an array of cancelled reservation 
	//indices, and restores the number of available places
	public void canRes() {
		System.out.print("\nSelect a reservation to cancel (enter reservation number): \n");
		resList();
		System.out.print("\n");
		int cannedResNumber = console.nextInt();
		
		if (reservationDetails[cannedResNumber-1].charAt(70) == 'M') {
			recoverPlaces(cannedResNumber, 0);
		} else if (reservationDetails[cannedResNumber-1].charAt(70) == 'L') {
			recoverPlaces(cannedResNumber, 1);
		} else if (reservationDetails[cannedResNumber-1].charAt(70) == 'A') {
			recoverPlaces(cannedResNumber, 2);
		}
		
		
		cannedReservations[nextCannedRes] = cannedResNumber;
		
		System.out.print("Reservation " + cannedResNumber + " has been cancelled.");
		nextCannedRes++;
	}
	
	//helper function to avoid repetition, for changing availability when a reservation
	//is cancelled.
	private void recoverPlaces(int cannedResNumber, int index) {
		if (reservationDetails[cannedResNumber-1].charAt(84) == 'C') {
			availCollection[index] = availCollection[index] 
					+ reservationPlaces[cannedResNumber-1];
		} else if (reservationDetails[cannedResNumber-1].charAt(84) == 'E') {
			availExhibition[index] = availExhibition[index] 
					+ reservationPlaces[cannedResNumber-1];
		} else if (reservationDetails[cannedResNumber-1].charAt(84) == 'T') {
			availTour[index] = availTour[index] 
					+ reservationPlaces[cannedResNumber-1];
		}
	}
	
	//helper function to avoid repetition, for printing array of reservations to console
	private void resList() {
		if (reservationDetails.length == 0) {
			System.out.print("\nNo reservations have been made yet.");
		} else {
			System.out.printf("%-6s%-23s%-16s%-24s%-14s%-15s%s\n"
					+ "-------------------------------------------------------------"
					+ "-------------------------------------------",
					"No.", "Name", "Phone", "Credit Card", "Session", "Experience", "Places");
			for (int i = 0; i < numReservations; i++) {
				if (!resIsCanned(i)) {
					System.out.print(reservationDetails[i]);
				}
			}
		}
	}
	
	//helper function, returns boolean to resList if a given reservation has been 
	//cancelled
	private boolean resIsCanned(int index) {
		boolean resIsCanned = false;
		for (int i = 0; i < nextCannedRes; i++) {
			if ((cannedReservations[i] - 1) == index) {
				resIsCanned = true;
			}
		}
		return resIsCanned;
	}
		
}
