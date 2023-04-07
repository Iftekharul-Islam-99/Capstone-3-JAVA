import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Reads and writes to files and creates lists of objects
 * using the data.
 * <p>
 * Reading from files:
 * <br>
 * reads from files "All projects.txt" and generates a list of objects
 * under the 'Project' class.
 * <p>
 * Writing to files:
 * <br>
 * Takes all the information stored in the list and writes all the information
 * to "All projects.txt", "Completed projects.txt", "customerContacts.txt", "contractorContacts.txt"
 * and the "architectContacts.txt" files.
 * 
 * @author Ifekharul Islam
 * @version 3.0, 30/08/2022
 */
public class ProjectDetails {
	private ArrayList<Project> projList = new ArrayList<>();
	private ArrayList<Person> customerList;
	private ArrayList<Person> contractorList;
	private ArrayList<Person> architechList;
	
	/**
	 * Constructs 3 list of 'Person' objects.
	 * <p>
	 * The 3 lists are separated into the customer,
	 * contractor and the architect.
	 * 
	 * @see Person
	 * @param customerList list of objects that hold the customer details.
	 * @param contractorList list of objects that hold the contractor details.
	 * @param architechList list of objects that hold the architect details.
	 */
	public ProjectDetails(ArrayList<Person> customerList, ArrayList<Person> contractorList, ArrayList<Person> architechList) {
		this.customerList = customerList;
		this.contractorList = contractorList;
		this.architechList = architechList;
	}
	
	/**
	 * Returns the list of generated Project object.
	 * 
	 * @return the list of Project objects.
	 */
	public ArrayList<Project> getProjList(){
		return projList;
	}
	
	/**
	 * Adds received objects to the projList.
	 * 
	 * @param newProj new object to be added to the list.
	 */
	public void addToList(Project newProj) {
		projList.add(newProj);
	}
	
	/**
	 * Reads data form the file "All projects.txt" and its elements
	 * to the elements in customer, contractor and architect lists
	 * and send the appropriate elements to the setObject() method.
	 * 
	 * @see ProjectDetails#getObject(ArrayList, String)
	 * @see ProjectDetails#setObject(Person, Person, Person, ArrayList)
	 */
	public void setProjList() {
		
		try {
			File detailsFile = new File("All projects.txt");
			Scanner projDetails = new Scanner(detailsFile);
			ArrayList<String> fullDetails = new ArrayList<>();
			
			while (projDetails.hasNextLine()) {
				fullDetails.add(projDetails.nextLine());
			}
			
			// Formatting to each elements in the line.
			fullDetails.remove(0);
			for (String elements : fullDetails) {
				ArrayList<String> allData = new ArrayList<>();
				String[] temp = elements.split(", ");
				
				for (String item : temp) {
					if (item.endsWith(".")) {
						item = item.replace(".", "");
						allData.add(item);
					}else {
						allData.add(item);
					}
				}
				Person customer = getObject(customerList, allData.get(allData.size() -3));
				Person contractor = getObject(contractorList, allData.get(allData.size() -2));
				Person architect = getObject(architechList, allData.get(allData.size() -1));
				
				setObject(customer, contractor, architect, allData);
				
				projDetails.close();
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Something went wrong there. Failed to read projects.txt" );
		}
	
	}
	
	/**
	 * Receives data from the file; creates a 'Project' object and
	 * add the object to a list.
	 * <p>
	 * Only creates a new object if the data in "All Projects.txt"
	 * matches with data in all the "Contacts.txt" files.
	 * <br>
	 * If the project in the file is finalised then we set the completion state
	 * to true and set the completion date.
	 * 
	 * @see Person
	 * @see Project
	 * @see Project#setDateCompleted(Date)
	 * @see Project#setFinalised(boolean)
	 * @see ProjectDetails#formatDate(String)
	 * @see ProjectDetails#castInt(String)
	 * @param customer an object under 'Person'
	 * @param contractor an object under 'Person'
	 * @param architech an object under 'Person'
	 * @param data List containing string data from a file.
	 */
	public void setObject(Person customer, Person contractor
			, Person architech, ArrayList<String> data) {
		if (customer == null || contractor == null || architech == null) {
			System.err.println("Names mismatch. Element could not be added to database.");
			
		}else if ( castInt(data.get(5)) == -1 || castInt(data.get(6)) == -1 
					|| formatDate(data.get(7)) == null){
			System.err.println("Data mismatch. Element could not be added to database");
			
		}else {
			Project newObj = new Project(data.get(0), data.get(1), data.get(2)
					, data.get(3), data.get(4), castInt(data.get(5)), castInt(data.get(6))
					, formatDate(data.get(7)), customer, contractor, architech);
			
			projList.add(newObj);
			
			if (data.get(8).equalsIgnoreCase("Yes")) {
				newObj.setDateCompleted(formatDate(data.get(9)));
				newObj.setFinalised(true);
			}
		}
	}
	
	/**
	 * Takes a list of Person objects and compares each elements
	 * name to a sent string. Returns the object that matches the string.
	 * 
	 * @see Person
	 * @see Person#getName()
	 * @param objList List of 'Person' objects.
	 * @param name string to compare.
	 * @return object that matches the string value of 'name' or null in not matched.
	 */
	public Person getObject(ArrayList<Person> objList, String name) {
		
		for (Person element : objList) {
			if (element.getName().equalsIgnoreCase(name)) {
				return element;
			}
		}
		return null;
	}
	
	/**
	 * Takes a string and attempts to cast it to integer.
	 * Returns the integer value if succeeded or returns -1 if failed.
	 * 
	 * @param num string that will be attempted to cast to integer.
	 * @return casted value or -1.
	 */
	public int castInt(String num) {
		try {
			return Integer.parseInt(num);
		}
		catch (NumberFormatException e){
			return -1;
		}
	}
	
	/**
	 * Takes a string date and tries to format it an actual date object.
	 * 
	 * @param date string value of the date
	 * @return formatted date object or null.
	 */
	public Date formatDate(String date) {
			try {
				return new SimpleDateFormat("dd/MM/yyyy").parse(date);
			}
			catch (ParseException e) {
				System.err.println("Incorrect format. try in the format: 12/12/2022");
				
				return null;
			}
	}
	
	/**
	 * Each objects data is written to the file "All projects.txt".
	 * 
	 * @see Project
	 * @see Project#fileFormat()
	 */
	public void writeProjFile() {
		try (Formatter file = new Formatter("All projects.txt");){
			file.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s.%n",
					"Project name", "Project No.", "Address", "ERF No."
					, "Building type", "Fee", "Paid to date", "Deadline"
					, "Completion state", "Completion date"
					, "Customer", "Contractor", "Architect");
			
			for (Project element : projList) {
				file.format("%s.%n", element.fileFormat());
			}
		}
		catch (Exception e) {
			System.err.println("Error");
		}
	}
	
	/**
	 * Each finalised objects data is written to the file "Completed projects.txt".
	 * <p>
	 * We check each object in the projects list and only write the ones
	 * that have their finalised value set to 'true'.
	 * <br>
	 * If a projects completion date is set to 'null' we just set it to todays date.
	 * 
	 * @see Project
	 * @see Project#fileFormat()
	 * @see Project#getFinalised()
	 * @see Project#setDateCompleted(Date)
	 */
	public void writeCompProjFile() {
		try (Formatter file = new Formatter("Completed projects.txt");){
			file.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s.%n",
					"Project name", "Project No.", "Address", "ERF No."
					, "Building type", "Fee", "Paid to date", "Deadline"
					, "Completion state", "Completion date", "Customer", "Contractor"
					, "Architect");
			
			for (Project element : projList) {
				if (element.getFinalised()) {
					if (element.getDateCompleted() == null) {
						element.setDateCompleted(new Date());
						file.format("%s.%n", element.fileFormat());
					}else {
						file.format("%s.%n", element.fileFormat());
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println("Error1");
		}
	}
	
	/**
	 * Here we take a file name and a list;
	 * we write all the data in the list to the appropriate file.
	 * 
	 * @see Person
	 * @see Person#fileFormat()
	 * @param fileName name of the file to write to.
	 * @param contactList list of 'Person' objects
	 */
	public void writeContactFiles(String fileName, ArrayList<Person> contactList) {
		try (Formatter file = new Formatter(fileName);){
			file.format("%s, %s, %s, %s.%n", "Name", "Telephone No.", "Email", "Address");
			
			for (Person element : contactList) {
				file.format("%s.%n", element.fileFormat());
			}
		}
		catch (Exception e) {
			System.err.println("Error");
		}
	}
	
}
