package calendar;

import java.io.*;
import java.time.*;
import java.util.*;

public class MyCalendar {
	
	private TreeSet<Event> events;
	
	/**
	 * Constructor for MyCalendar class
	 * This will initialize the treeSet events.
	 */
	public MyCalendar() {
		events = new TreeSet<>();
	}

	/**
	 * Add new event in events list
	 * 
	 * @param	e		new event to be added in ArrayList events.
	 * @return	true	If an event successfully added without any conflict
	 * 			false	If an event conflicts with existing event and did not added.
	 */
	public boolean add(Event e) {
		if(!events.isEmpty() || !e.isRecurring()) { // if events is empty or recursive just put it.
			for(Event event : events) { // check conflict between existing events
				if(!event.isRecurring()) {
					if(event.isConflict(e)) {
						System.out.println("It conflicts with existing event.");
						return false;
					}	
				}
			}
			events.add(e);
			return true;
		}
		events.add(e);
		return true;

	}
	
	/**
	 * This method will delete event with given date and name.
	 * 
	 * @param 	date	Will find event with given date
	 * 			name	Will find event with given name
	 */
	public void deleteSelected(LocalDate date, String name) {
		ArrayList<Event> toRemove = new ArrayList<>();
		
		for(Event e : events) {

			if(!e.isRecurring()) {
				if(e.isOccurring(date)) {
					if(e.getName().equals(name)) {
						toRemove.add(e);
					}
				}
			}
		}
		events.removeAll(toRemove);
	}
	
	/**
	 * This method will delete all one-time event with given day.
	 * 
	 * @param	dDay	Will find one-time events with given name.
	 */
	public void deleteAll(LocalDate dDay) {
		ArrayList<Event> toRemove = new ArrayList<>();
		for(Event e : events) {
			if(!e.isRecurring()) {
				if(e.isOccurring(dDay)) {
					toRemove.add(e);
				}
			}
		}
		events.removeAll(toRemove);
	}

	/**
	 * This method will delete recurring event with given name.
	 * 
	 * @param	name	Will delete recurring event with given name.
	 */
	public void deleteRecurring(String name) {
		ArrayList<Event> toRemove = new ArrayList<>();
		for(Event e : events) {
			if(e.isRecurring()) {
				if(e.getName().equals(name)) {
					toRemove.add(e);
				}
			}
		}
		events.removeAll(toRemove);
	}
	
	/**
	 * This method will convert string form date to Local type date
	 * 
	 * @param 	s		Receive String s to convert to LocalDate type
	 * @return	dateLD	LocalDate of s's year, month, date
	 */
	public LocalDate stringToDate(String s) {
		String[] dateArr = s.split("/");
		int month = Integer.parseInt(dateArr[0]);
		int date = Integer.parseInt(dateArr[1]);
		int year = Integer.parseInt(dateArr[2]);
		
		LocalDate dateLD = LocalDate.of(year, month, date);
		return dateLD;
	}
	
	/**
	 * This method will print all recurring events
	 */
	public void printAllRecurring() {
		for(Event e : events) {
			if(e.isRecurring()) {
				e.printEventList();
			}
		}
	}
	
	/**
	 * Getter method to pass events to other classes
	 * 
	 * @return 	this.events		Returns itself
	 */
	public TreeSet<Event> getEvents() {
		return this.events;
	}
	
	/**
	 * This method will show the events those are occurring in dDay.
	 * 
	 * @param	Dday	This is the day used to find and print events.
	 */
	public void dayViewOf(LocalDate Dday) { // get day info
		String firstLine = Dday.getDayOfWeek().toString() + ", " + Dday.getMonth().toString() + " " + Dday.getDayOfMonth() + ", " + Dday.getYear();
		System.out.println(firstLine);
		
		ArrayList<Event> daySort = new ArrayList<>();
		for(Event e : events) {
			if(e.isOccurring(Dday)) {
				if(daySort.isEmpty()) { // in case 1
					daySort.add(e);
				} else {
					if(daySort.size() == 1) { // in case 2
						if(daySort.get(0).getStartTime().isBefore(e.getStartTime())) {
							daySort.add(e);
						} else {
							daySort.add(0, e);
						}
					} else if(daySort.size() == 2) { // in case larger than 3
						for(int i = 0; i < daySort.size(); i++) {
							if(daySort.get(i).getStartTime().isAfter(e.getStartTime())) {
								daySort.add(i, e);
							}
						}
						if(!daySort.contains(e)) {
							daySort.add(e);
						}
					}
				}
			}
		}
		
		for(Event e : daySort) {
			e.printDayEvent();
		}
	}
	
	/**
	 * This method will show the events in calendar form and highlight today's date.
	 * 
	 * @param	Dday	This is the day used to find and print events.
	 */
	public void monthViewOf(LocalDate Dday) {
		LocalDate dDay = Dday;
		LocalDate cal = LocalDate.of(dDay.getYear(), dDay.getMonthValue(), 1);
		YearMonth ym = YearMonth.of(dDay.getYear(), dDay.getMonthValue());
		System.out.println();
		System.out.print(cal.getMonth() + "\t");
		System.out.println(cal.getYear());
		System.out.println("Su\tMo\tTu\tWe\tTh\tFr\tSa");
		
		
		int[] maxDayNotLeap = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int[] maxDayLeap = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		// check leap year
		int maxDay = 0;
		if(cal.isLeapYear() == true) {
			maxDay = maxDayLeap[cal.getMonthValue() - 1];
		} else {
			maxDay = maxDayNotLeap[cal.getMonthValue() - 1];
		}
		
		
		
		for(int i = cal.getDayOfMonth(); i <= maxDay; i++) { // starts from the beginning
			
			// Only for the firstday
			if(i == 1) {
				switch(ym.atDay(i).getDayOfWeek().toString()) { // first day
				case "SUNDAY":
					System.out.print(i + "\t");
					break;
				case "MONDAY":
					System.out.print("\t" + i + "\t");
					break;
				case "TUESDAY":
					System.out.print("\t\t" + i + "\t");
					break;
				case "WEDNESDAY":
					System.out.print("\t\t\t" + i + "\t");
					break;
				case "THURSDAY":
					System.out.print("\t\t\t\t" + i + "\t");
					break;			
				case "FRIDAY":
					System.out.print("\t\t\t\t\t" + i + "\t");
					break;
				case "SATURDAY":
					System.out.print("\t\t\t\t\t\t" + i + "\t");
					System.out.println();
					break;
				default:
					System.out.println("Error!");
					break;
				}
			} else {				
				if(ym.atDay(i).getDayOfWeek().toString() == "SATURDAY") { // If Saturday
					if(i == dDay.getDayOfMonth()) { // if today
						System.out.print("{" + i + "}" + "\n");
					} else {
						System.out.print(i + "\n");
					}
				} else { // If not Saturday
					if(i == dDay.getDayOfMonth()) { // if today
						System.out.print("{" + i + "}" + "\t");
					} else {
						System.out.print(i + "\t");
					}
				}
				
			}
			if(i == maxDay) {
				System.out.println();
			}
			
		}
	}
	
	/**
	 * This method will show the events in calendar form and highlighted when the day has events.
	 * 
	 * @param 	Dday	This is the day used to be standard of calendar.
	 */
	public void monthViewUpdate(LocalDate Dday) {
		LocalDate dDay = Dday;
		LocalDate indexDate = LocalDate.of(dDay.getYear(), dDay.getMonthValue(), 1);
		YearMonth ym = YearMonth.of(dDay.getYear(), dDay.getMonthValue());
		System.out.println();
		System.out.print(indexDate.getMonth() + "\t");
		System.out.println(indexDate.getYear());
		System.out.println("Su\tMo\tTu\tWe\tTh\tFr\tSa");
		
		
		int[] maxDayNotLeap = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int[] maxDayLeap = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		// check leap year
		int maxDay = 0;
		if(indexDate.isLeapYear() == true) {
			maxDay = maxDayLeap[indexDate.getMonthValue() - 1];
		} else {
			maxDay = maxDayNotLeap[indexDate.getMonthValue() - 1];
		}
		
		for(int i = indexDate.getDayOfMonth(); i <= maxDay; i++) { // starts from the beginning	
			boolean eventYes = false;
			for(Event e : events) {
				if(e.isOccurring(LocalDate.of(dDay.getYear(), dDay.getMonthValue(), i))) { // 쓰여지는 날에 이벤트 있는지 확인
					eventYes = true;
				}
			}
			// Only for the firstday
			if(i == 1) {
				switch(ym.atDay(i).getDayOfWeek().toString()) { // first day
				case "SUNDAY":
					if(eventYes == true) {
						System.out.print("[" + i + "]" + "\t");
					} else {
						System.out.print(i + "\t");						
					}
					break;
				case "MONDAY":
					if(eventYes == true) {
						System.out.print("\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t" + i + "\t");
						
					}
					break;
				case "TUESDAY":
					if(eventYes == true) {
						System.out.print("\t\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t\t" + i + "\t");
						
					}
					break;
				case "WEDNESDAY":
					if(eventYes == true) {
						System.out.print("\t\t\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t\t\t" + i + "\t");
						
					}
					break;
				case "THURSDAY":
					if(eventYes == true) {
						System.out.print("\t\t\t\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t\t\t\t" + i + "\t");
						
					}
					break;			
				case "FRIDAY":
					if(eventYes == true) {
						System.out.print("\t\t\t\t\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t\t\t\t\t" + i + "\t");
						
					}
					break;
				case "SATURDAY":
					if(eventYes == true) {
						System.out.print("\t\t\t\t\t\t" + "[" + i + "]" + "\t");
					} else {
						System.out.print("\t\t\t\t\t\t" + i + "\t");
					}
					System.out.println();
					break;
				default:
					System.out.println("Error!");
					break;
				}
			} else {				
				if(ym.atDay(i).getDayOfWeek().toString() == "SATURDAY") { // If Saturday
					if(eventYes == true) { // if today
						System.out.print("[" + i + "]" + "\n");
					} else {
						System.out.print(i + "\n");
					}
				} else { // If not Saturday
					if(eventYes == true) { // if today
						System.out.print("[" + i + "]" + "\t");
					} else {
						System.out.print(i + "\t");
					}
				}
				
			}
			if(i == maxDay) {
				System.out.println();
			}
			
		}
	}
	
	/**
	 * This method will read the file with given param and convert it into Event object
	 * 
	 * @param	eventFile	This specify the path where the file exist, and name of file.
	 * @throws 	IOException	On reading files
	 */
	public void readFile(String eventFile) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(eventFile));
		
		boolean eof = false;
		int counter = 0;

		String name = null;
		while(eof != true) {
			if(counter == 0) {
				name = br.readLine(); // First Line
			}
						
			String secondLine = br.readLine(); // Second Line
			String[] secondLineArr = secondLine.split(" ");
			
			if(secondLineArr[0].contains("/")) { // One-Time Event
				String[] dateArr = secondLineArr[0].split("/");
				int month = Integer.parseInt(dateArr[0]);
				int date = Integer.parseInt(dateArr[1]);
				int year = Integer.parseInt(dateArr[2]);
				
				String[] startTimeArr = secondLineArr[1].split(":");
				int startHr = Integer.parseInt(startTimeArr[0]);
				int startMin = Integer.parseInt(startTimeArr[1]);
				
				String[] endTimeArr = secondLineArr[2].split(":");
				int endHr = Integer.parseInt(endTimeArr[0]);
				int endMin = Integer.parseInt(endTimeArr[1]);
				
				Event newEvent = new Event(name, LocalDate.of(year, month, date), LocalTime.of(startHr, startMin), LocalTime.of(endHr, endMin));
				add(newEvent);
				counter++;
				
			} else { // Recurring Event
				String days = secondLineArr[0];
				
				String[] startTimeArr = secondLineArr[1].split(":");
				int startHr = Integer.parseInt(startTimeArr[0]);
				int startMin = Integer.parseInt(startTimeArr[1]);
				
				String[] endTimeArr = secondLineArr[2].split(":");
				int endHr = Integer.parseInt(endTimeArr[0]);
				int endMin = Integer.parseInt(endTimeArr[1]);
				
				String[] startDateArr = secondLineArr[3].split("/");
				int startMonth = Integer.parseInt(startDateArr[0]);
				int startDate = Integer.parseInt(startDateArr[1]);
				int startYear = Integer.parseInt(startDateArr[2]);
				
				String[] endDateArr = secondLineArr[4].split("/");
				int endMonth = Integer.parseInt(endDateArr[0]);
				int endDate = Integer.parseInt(endDateArr[1]);
				int endYear = Integer.parseInt(endDateArr[2]);
				
				LocalTime startTime = LocalTime.of(startHr, startMin);
				LocalTime endTime = LocalTime.of(endHr, endMin);
				LocalDate startDateInfo = LocalDate.of(startYear, startMonth, startDate);
				LocalDate endDateInfo = LocalDate.of(endYear, endMonth, endDate);
				
				Event newEvent = new Event(name, startDateInfo, endDateInfo, startTime, endTime, days);
				add(newEvent);
				counter++;
			}
			String firstLine = br.readLine();
			
			if(firstLine == null) {
				eof = true;
			} else {
				name = firstLine;
			}

		}
		
		br.close();

	}

	/**
	 * This method will write the events on file "output.txt"
	 * 
	 * @throws	IOException On writing files
	 */
	public void writeFile() throws IOException {
		PrintWriter pw = new PrintWriter("data/event.txt", "UTF-8");
		for(Event e : events) {
			pw.print(e.passEventList());
		}
		pw.close();
		
	}
	
	/**
	 * This method will print all one-time events with heading(its year)
	 */
	public void printAllEventList() {
		// for one time
		ArrayList<Integer> years = new ArrayList<>();
		for(Event e: events) {
			if(!e.isRecurring()) {
				if(!years.contains(e.getDate().getYear())) {
					years.add(e.getDate().getYear());
				}				
			}
		}
		for(int year : years) {
			System.out.println(year);
			for(Event e: events) {
				if(!e.isRecurring()) {
					if(e.getDate().getYear() == year) {
						e.printEventList();
					}
				}
			}
		}
		// for recurring
	}
}
