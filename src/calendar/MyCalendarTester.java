package calendar;

import java.io.IOException;
import java.time.*;
import java.util.*;

/**
* The MyCalendar program implements an application that
* can create, remove, and view events on certain day or
* month. The event data automatically saved in output.txt
* file in data folder. 
*
* @author  Juhan Lee
* @version 1.0
* @since   2022-09-12 
*/
public class MyCalendarTester {
	
	/**
	 * Main Method to execute MyCalendar
	 * 
	 * @param 	args			Unused
	 * @throws 	IOException		On File reading and writing error
	 * 
	 */
	public static void main(String[] args) throws IOException {

		MyCalendar calendar = new MyCalendar();
		
		// load event
		calendar.readFile("data/event.txt");
		System.out.println("Loading is Done!!!");
		// print initial screen
		calendar.monthViewOf(LocalDate.now());
		
		Scanner sc = new Scanner(System.in);
		String mainMenuInput = "a";

		while(!mainMenuInput.equals("Q")) {
			System.out.println();
			System.out.println("Select one of the following main menu options:");
			System.out.println("[V]iew by,  [C]reate,  [G]o to,  [E]vent list,  [D]elete,  [Q]uit");
			mainMenuInput = sc.nextLine();
			System.out.println();
			switch(mainMenuInput) {
			case "V":
				String viewMenuInput = "a";
				
				System.out.println("Which view do you want:");
				System.out.println("[D]ay View, [M]onth View");
				viewMenuInput = sc.nextLine();
				
				LocalDate dayDate = LocalDate.now();
				LocalDate monthDate = LocalDate.now();
				switch(viewMenuInput) {
				case "D":
					calendar.dayViewOf(dayDate);
					String dayMenuInput = "default";
					while(!dayMenuInput.equals("G")) {
						System.out.println("Which day do you want:");
						System.out.println("[P]revious, [N]ext, [G]o back to the main menu");
						dayMenuInput = sc.nextLine();
						switch(dayMenuInput) {
						case "P":
							dayDate = dayDate.minusDays(1);
							calendar.dayViewOf(dayDate);
							break;
						case "N":
							dayDate = dayDate.plusDays(1);
							calendar.dayViewOf(dayDate);
							break;
						case "G":
							// go back to main menu
							break;
						default:
							System.out.println("Wrong input. Note that the input should be capitalized.");
						}
					}
					break;				
				case "M":
					calendar.monthViewUpdate(monthDate);
					String monthMenuInput = "default";
					while(!monthMenuInput.equals("G")) {
						System.out.println("Which month do you want:");
						System.out.println("[P]revious, [N]ext, [G]o back to the main menu");
						monthMenuInput = sc.nextLine();
						switch(monthMenuInput) {
						case "P":
							monthDate = monthDate.minusMonths(1);
							calendar.monthViewUpdate(monthDate);
							break;
						case "N":
							monthDate = monthDate.plusMonths(1);
							calendar.monthViewUpdate(monthDate);
							break;
						case "G":
							// go back to main menu
							break;
						default:
							System.out.println("Wrong input. Note that the input should be capitalized.");
						}
					}
					// Monthly view function
					break;	
				default:
					System.out.println("Wrong input. Note that the input should be capitalized.");
				}
				System.out.println("----------View Menu is done----------");
				break;
			case "C":
				System.out.println("Create");
				
				System.out.println("Enter the information of the event!");
				System.out.print("Name: ");
				
				String newEventName = sc.nextLine();
				System.out.print("Date(MM/DD/YYYY): ");
				String newEventDateStr = sc.nextLine();
				String[] newEventDateArr = newEventDateStr.split("/");
				int newEventMonth = Integer.parseInt(newEventDateArr[0]);
				int newEventDate = Integer.parseInt(newEventDateArr[1]);
				int newEventYear = Integer.parseInt(newEventDateArr[2]);
				LocalDate newEventDateInfo = LocalDate.of(newEventYear, newEventMonth, newEventDate);
				
				System.out.print("Starting Time(ex. 13:00): ");
				String newStartTimeStr = sc.nextLine();
				String[] newStartTimeArr = newStartTimeStr.split(":");
				int newStartTimeHr = Integer.parseInt(newStartTimeArr[0]);
				int newStartTimeMin = Integer.parseInt(newStartTimeArr[1]);
				LocalTime newStartTimeInfo = LocalTime.of(newStartTimeHr, newStartTimeMin);
				System.out.print("Ending Time(ex. 23:30): ");
				String newEndTimeStr = sc.nextLine();
				String[] newEndTimeArr = newEndTimeStr.split(":");
				int newEndTimeHr = Integer.parseInt(newEndTimeArr[0]);
				int newEndTimeMin = Integer.parseInt(newEndTimeArr[1]);
				LocalTime newEndTimeInfo = LocalTime.of(newEndTimeHr, newEndTimeMin);
				
				calendar.add(new Event(newEventName, newEventDateInfo, newStartTimeInfo, newEndTimeInfo));
				System.out.println("----------Create Menu is done----------");
				break;
			case "G":
				System.out.println("Go to");
				System.out.print("Enter the Date you want to go(MM/DD/YYYY): ");
				String enteredDate = sc.nextLine();
				calendar.dayViewOf(calendar.stringToDate(enteredDate));
				System.out.println("----------Go To Menu is done----------");
				break;
			case "E":
				System.out.println("Event List");
				System.out.println();
				System.out.println("One Time Events");
				calendar.printAllEventList();
				System.out.println();
				System.out.println("Recurring Events");
				for(Event e: calendar.getEvents()) {
					if(e.isRecurring()) {
						e.printEventList();
					}
				}
				System.out.println("----------Event List Menu is done----------");
				break;
			case "D":
				System.out.println("Delete");
				System.out.println("Select the Menu: ");
				System.out.println("[S]elected, [A]ll, [DR](Delete Recurring)");
				String deleteInput = sc.nextLine();
				switch(deleteInput) {
				case "S":
					System.out.print("Enter the Date you want to go(MM/DD/YYYY): ");
					String deleteDateStr = sc.nextLine();
					calendar.dayViewOf(calendar.stringToDate(deleteDateStr));
					System.out.print("Enter the name of event to delete: ");
					String deleteNameStr = sc.nextLine();
					calendar.deleteSelected(calendar.stringToDate(deleteDateStr), deleteNameStr);
					break;
				case "A":
					System.out.print("Enter the Date you want to go(MM/DD/YYYY): ");
					String deleteDateAllStr = sc.nextLine();
					calendar.deleteAll(calendar.stringToDate(deleteDateAllStr));
					break;
				case "DR":
					calendar.printAllRecurring();
					System.out.println();
					System.out.print("Enter the name of recurring Event you want to delete: ");
					String deleteRecName = sc.nextLine();
					calendar.deleteRecurring(deleteRecName);
					break;
				default:
					
				}
				System.out.println("----------Delete Menu is done----------");
				break;
			case "Q":
				calendar.writeFile();
				System.out.println("Good Bye!");
				break;
			default:
				System.out.println("Input is wrong");
			}
		}
		sc.close();
	}
	
}
