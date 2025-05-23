import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Stores a list of Person objects and a string variable 'fileName'.
 * <p>
 * list is generated by opening the file under 'fileName' 
 * and storing that data.
 * 
 * @author Ifekharul Islam
 * @version 3.0, 30/08/2022
 */
public class Contacts{
	private String fileName;
	private ArrayList<Person> contactList = new ArrayList<>();
	
	/**
	 * Initialises the 'fileName' with provided string value.
	 * 
	 * @param fileName string containing a file name.
	 */
	public Contacts(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Returns a list of 'Person' objects.
	 * 
	 * @return list containing 'Person' objects.
	 */
	public ArrayList<Person> getContactList(){
		return contactList;
	}
	/**
	 * Adds to the list of objects with sent object.
	 * @param newItem new object to append to the list.
	 */
	public void addToContactList(Person newItem){
		contactList.add(newItem);
	}
	
	/**
	 * Reads from the file that matches 'fileName' and send
	 * the data to a method to build a list of 'Person'objects.
	 * 
	 * @see Contacts#buildContacts(Scanner)
	 */
	public void setContactList() {
			
			try {
				File detailsFile = new File(fileName);
				Scanner contactDetails = new Scanner(detailsFile);
				
				buildContacts(contactDetails);
				
				contactDetails.close();
			}
			catch (FileNotFoundException e) {
				System.err.println("Something went wrong there. Failed to read " + fileName);
			}
	}
	
	/**
	 * Builds a list of 'Person' objects with read file data.
	 * <p>
	 * We first format the data by reading each line in the file.
	 * <br>
	 * Each line is then separated into elements. Each element
	 * is then formatted appropriately.
	 * <br>
	 * We Then use these element to build a 'Person' object and add this
	 * object to the list. This is repeated for each line in the file.
	 * 
	 * @see Person
	 * @param contactDetails scanner object containing all the data in the file.
	 */
	public void buildContacts(Scanner contactDetails){
		ArrayList<String> fullDetails = new ArrayList<>();
		
		while (contactDetails.hasNextLine()) {
			fullDetails.add(contactDetails.nextLine());
		}
		
		fullDetails.remove(0);
		for (String elements : fullDetails) {
			ArrayList<String> iscolatedDetails = new ArrayList<>();
			String[] temp = elements.split(", ");
			
			for (String item : temp) {
				if (item.endsWith(".")) {
					item = item.replace(".", "");
					iscolatedDetails.add(item);
				}else {
					iscolatedDetails.add(item);
				}
			}
			
			contactList.add(new Person(iscolatedDetails.get(0), iscolatedDetails.get(1)
					,iscolatedDetails.get(2), iscolatedDetails.get(3)));
		}
		
	}
}
