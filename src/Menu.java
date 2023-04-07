import java.util.Date;
import java.util.Scanner;

/**
 * Saves information of the sent project object and
 * generated a menu. The menu gives options to
 * modify the sent project in different ways.
 * 
 * 
 * @author Ifekharul Islam
 * @version 3.0, 30/08/2022
 */
public class Menu {
	private Project proj;
	
	/**
	 * Constructor thats saves the project object.
	 * 
	 * @see Project
	 * @param proj object of class 'Project'.
	 */
	public Menu(Project proj) {
		this.proj = proj;
	}
	
	/**
	 * generated a menu that lest the user to modify aspects of
	 * the project.
	 * <p>
	 * User is given options to:
	 * <br>
	 * Update the deadline.
	 * <br>
	 * Update the contractors details.
	 * <br>
	 * Update the paid amount.
	 * <br>
	 * Finalise the project.
	 * <br>
	 * Exit the menu.
	 * 
	 * @see Project#getContractor()
	 * @see Person#setAddress(String)
	 * @see Person#setEmail(String)
	 * @see Person#setName(String)
	 * @see Person#setTeleNum(String)
	 * @see Project#setDeadline(Date)
	 * @see Project#getFee()
	 * @see Project#getPaidAmount()
	 * @see Project#setPaidAmount(int)
	 * @see Project#setFinalised(boolean)
	 * @see Project#setDateCompleted(Date)
	 * @see Checks#checkEmail(Scanner)
	 * @see Checks#checkName(String, Scanner)
	 * @see Checks#checkPayment(String, int, Scanner)
	 * @see Checks#checkEmpty(String, Scanner)
	 * @see Checks#checkTeleNo(Scanner)
	 * @see Checks#formatDate(String, Scanner)
	 * @see Checks#stringDate(Date)
	 * @param input2 scanner object to take user input.
	 */
	public void editProj(Scanner input2) {
		while (true){
			System.out.println("""
					
					Choose from the menu below.
					ud - Update due date.
					uc - Update contractors details.
					up - Update total amount paid to date.
					f - Finalise the project.
					e - Exit
					""");
			
			String menu = input2.nextLine().strip().toLowerCase();
			
			// This updates the date with new user inputed date using setters in the class.
			if (menu.equals("ud")) {
				System.out.println("Current deadline: " + Checks.stringDate(proj.getDeadline()));
				System.out.println();
				Date newDeadline = Checks.formatDate("Enter new due date (dd/mm/yyyy)", input2);
				proj.setDeadline(newDeadline);
				System.out.println("New deadline set to " + newDeadline);
			
			// This updates the details of the contractor with new user inputed data using setters in the class.
			// We first access the object under 'Person' but using a getter in the 'project' class.
			// Then we can access the setter to set the new value.
			}else if (menu.equals("uc")) {
				System.out.println("Enter new details");
				String newName = Checks.checkName("Firstname and surname", input2);
				proj.getContractor().setName(newName);
				
				String newTeleNum = Checks.checkTeleNo(input2);
				proj.getContractor().setTeleNum(newTeleNum);
				
				String newEmail = Checks.checkEmail(input2);
				proj.getContractor().setEmail(newEmail);
				
				String newAddress = Checks.checkEmpty("Address", input2);
				proj.getContractor().setAddress(newAddress);
				
				System.out.println("Details updated.");
				
			// This sets the new paid amount.
			}else if (menu.equals("up")) {
				String text = "Total fee of the project: " 
								+ proj.getFee()
								+ "\nCurrent amount paid to date: "
								+ proj.getPaidAmount()
								+ "\nEnter new amount";
				int newAmount = Checks.checkPayment(text, proj.getFee(), input2);
				proj.setPaidAmount(newAmount);
				
			// This finalises the project if the total fee is paid.
			// Also sets the current date if the project is finalised and displays all the info.
			}else if (menu.equals("f")) {
				if (proj.getFinalised()) {
					System.out.println("Project already finalised.");
				}else if (proj.getPaidAmount() == proj.getFee()) {
					proj.setFinalised(true);
					proj.setDateCompleted(new Date());
					
					System.out.println("Project finalised.");
					System.out.println("\nCompletion date: " + proj.getDateCompleted() );
				}else {
					proj.setFinalised(true);
					proj.setDateCompleted(new Date());
					System.out.println(proj + "\nCompletion date: " + proj.getDateCompleted() );
				}
			
			// Exits the menu.
			}else if (menu.equals("e")) {
				break;
			}else {
				System.err.println("Incorrect choice. Try again.");
			}
		}
	}
}
