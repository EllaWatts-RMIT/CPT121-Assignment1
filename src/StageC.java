// 
// StageC.java is a program constructed for Programming 1.
// It is a reservation manager for the fictional art gallery, 'Artopia'.
// The program allows users to view availability, create reservations and 
// view reservations. In addition, it also allows users to choose from 
// 3 different session times across a day, and cancel reservations. 
// 
// The program also uses the class ReservationMgr (ReservationMgr.java) which 
// contains all methods for taking, making and removing reservations. 
//

import java.util.*;

public class StageC {
	static Scanner console = new Scanner(System.in);
	ReservationMgr manager;
	
	//constructor takes visitor limit inputs, shows menu, creates ReservationMgr object.
	public StageC() {
		System.out.print("Welcome to the Reservation Manager\n\n");
		System.out.print("How many visitors are allowed into general collection? ");
		int totalVisCollection = console.nextInt();
		console.nextLine();
		System.out.print("How many visitors are allowed into the exhibition area? ");
		int totalVisExhibition = console.nextInt();
		console.nextLine();
		System.out.print("How many visitors are allowed on the tour? ");
		int totalVisTour = console.nextInt();
		console.nextLine();
		
		manager = new ReservationMgr(totalVisCollection, totalVisExhibition, totalVisTour);
		
		displayMenu();
	}
	
	//shows menu when called. users can select actions from here.
	public void displayMenu() {
		System.out.print("\n\n          Menu\n-------------------------\n"
				+ " - Show Availability  (A)\n"
				+ " - New reservation    (N)\n"
				+ " - View reservations  (V)\n"
				+ " - Cancel reservation (C)\n"
				+ " - Quit               (Q)\n"
				+ "Select a menu item (enter the corresponding key): ");
		String menuSelect = console.nextLine().trim();
		if (menuSelect.equalsIgnoreCase("A")) {
			manager.showAvail();
			displayMenu();
		} else if (menuSelect.equalsIgnoreCase("N")) {
			manager.newRes();
			displayMenu();
		} else if (menuSelect.equalsIgnoreCase("V")) {
			manager.viewRes();
			displayMenu();
		} else if (menuSelect.equalsIgnoreCase("C")) {
			manager.canRes();
			displayMenu();
		} else if (menuSelect.equalsIgnoreCase("Q")) {
			quitMgr();
		} else {
			System.out.print("\n" + menuSelect + " isn't a valid choice. Please try again.");
			displayMenu();
		}
	}
	
	//terminates the program
	public void quitMgr() {
		System.out.println("\nProgram ended.");
		System.exit(0);
	}
	
	public static void main(String args[]) {
		StageC obj = new StageC();
	}
}