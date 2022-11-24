package calendar;

import java.time.*;
import java.util.ArrayList;

public class Event implements Comparable<Event>{
	
	private String name;
	private LocalDate date; // For one-time
	private LocalDate startDate; // For Recurring
	private LocalDate endDate; // For Recurring
	private ArrayList<DayOfWeek> days; // For Recurring
	private TimeInterval ti;
	private boolean isRecurring;
	MyCalendar calendar = new MyCalendar();

	
	/**
	 * One-Time Event Constructor
	 * 
	 * @param	name		name of Event
	 * @param	date		date of Event
	 * @param	startTime	Start time of Event
	 * @param	endTime		End time of Event
	 */
	public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.name = name;
		this.date = date;
		this.ti = new TimeInterval(startTime, endTime);
		this.isRecurring = false;
	}
	
	/**
	 * Recurring Event Constructor
	 * 
	 * @param	name		Name of Event
	 * @param	startDate	Start date of Event
	 * @param	endDate		End date of Event
	 * @param	startTime	Start time of Event
	 * @param	endTime		End time of Event
	 * @param	days		Days of Event, Case Sensitive, no spaces
	 */
	public Event(String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String days) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ti = new TimeInterval(startTime, endTime);
		ArrayList<DayOfWeek> alDays = new ArrayList<DayOfWeek>();
		
		for(int i = 0; i < days.length(); i++) { // days input -> case sensitive
			switch(days.charAt(i)) {
			case 'S':
				alDays.add(DayOfWeek.SUNDAY);
				break;
			case 'M':
				alDays.add(DayOfWeek.MONDAY);
				break;
			case 'T':
				alDays.add(DayOfWeek.TUESDAY);
				break;
			case 'W':
				alDays.add(DayOfWeek.WEDNESDAY);
				break;
			case 'R':
				alDays.add(DayOfWeek.THURSDAY);
				break;
			case 'F':
				alDays.add(DayOfWeek.FRIDAY);
				break;
			case 'A':
				alDays.add(DayOfWeek.SATURDAY);
				break;
			default:
				System.out.println("Wrong Input!");
			}
		}
		this.days = alDays;
		this.isRecurring = true;
	}

	/**
	 * This method will return the startTime of the event
	 *
	 * @return	this.ti.getStartTime()	it passes the startTime of given object
	 */
	public LocalTime getStartTime() {
		return this.ti.getStartTime();
	}
	
	/**
	 * This method will return the endTime of the event
	 *
	 * @return	this.ti.getEndTime()	it passes the endTime of given object
	 */
	public LocalTime getEndtime() {
		return this.ti.getEndTime();
	}
	
	/**
	 * Return name of Event
	 * 
	 * @return	this.name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return date of one-time event
	 * 
	 * @return	this.date
	 */
	public LocalDate getDate() {
		return this.date;
	}
	
	/**
	 * Return start date of recurring event
	 * 
	 * @return	this.startDate
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}

	/**
	 * Return end date of recurring event
	 * 
	 * @return	this.endDate
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Return Day of week of recurring event ex)MTW
	 * 
	 * @return	this.days
	 */
	public ArrayList<DayOfWeek> getDays(){
		return this.days;
	}
	
	/**
	 * Return whether the event is recurring or not
	 * 
	 * @return	this.recurring	True if event is recursive
	 */
	public boolean isRecurring() {
		return this.isRecurring;
	}
	
	/**
	 * compareTo Method for the comparable<Event>
	 * 
	 * @param	o				Event Object which will be compared with this object
	 * @return	returnValue		return 0 if same, 1 if o is earlier, -1 if this is earlier event.
	 */
	@Override
	public int compareTo(Event o) {
		
		LocalDateTime event1, event2;

		if(this.isRecurring) {
			event1 = LocalDateTime.of(this.startDate, this.ti.getStartTime());
		} else { // for one-time
			event1 = LocalDateTime.of(this.date, this.ti.getStartTime());
		}

		if(o.isRecurring) {
			event2 = LocalDateTime.of(o.startDate, o.ti.getStartTime());
		} else {
			event2 = LocalDateTime.of(o.date, o.ti.getStartTime());
		}
		
		int returnValue = event1.compareTo(event2);
		if(returnValue == 0) { // if occurs in same date and at same time
			return this.getName().compareTo(o.getName());
		}
		return returnValue;

	}

	/**
	 * Return conflict to MyCalendar class when they add new event
	 * 
	 * @return	true	if it conflicts
	 * 			false	if it does not conflict
	 */
	public boolean isConflict(Event o) {
		// date check
		if(this.isOccurring(o.getDate())) {
			if(this.ti.conflictCheck(o.ti)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Print the event information for "View by" and "Day View"
	 */
	public void printDayEvent() {
		String eventInfo;
		eventInfo = this.getName() + " : " + this.ti.getStartTime() + " - " + this.ti.getEndTime();
		System.out.println(eventInfo);
		
	}

	/**
	 * Print the event information for "Event List" menu
	 */
	public void printEventList() {
		if(this.isRecurring) {
			System.out.println(name);
			String daysStr = "";
			for(int i = 0; i < days.size(); i++) {
				if(days.get(i).toString().equals("MONDAY") || days.get(i).toString().equals("TUESDAY") || days.get(i).toString().equals("WEDNESDAY") || days.get(i).toString().equals("FRIDAY") || days.get(i).toString().equals("SUNDAY")) {
					daysStr = daysStr + days.get(i).toString().charAt(0);
				} else {
					if(days.get(i).toString().equals("THURSDAY")) {
						daysStr = daysStr + "R";
					} else {
						daysStr = daysStr + "A";
					}
				}
				
			}
			System.out.println(daysStr + " " + ti.getStartTime() + " " + ti.getEndTime() + " " + startDate + " " + endDate);
		} else {
			System.out.println("\t" + this.date.getDayOfWeek().toString() + " " + this.date.getMonth().toString() + " " + this.date.getDayOfMonth() + " " + this.ti.getStartTime() + " - " + this.ti.getEndTime() + " " + this.name);
		}
	}
	
	/**
	 * Pass all event lists in String type.
	 * 
	 * @return	the event lists in appropriate form.
	 */
	public String passEventList() {
		if(this.isRecurring) {
			String daysStr = "";
			for(int i = 0; i < days.size(); i++) {
				if(days.get(i).toString().equals("MONDAY") || days.get(i).toString().equals("TUESDAY") || days.get(i).toString().equals("WEDNESDAY") || days.get(i).toString().equals("FRIDAY") || days.get(i).toString().equals("SUNDAY")) {
					daysStr = daysStr + days.get(i).toString().charAt(0);
				} else {
					if(days.get(i).toString().equals("THURSDAY")) {
						daysStr = daysStr + "R";
					} else {
						daysStr = daysStr + "A";
					}
				}
				
			}
			return name + "\n" + daysStr + " " + ti.getStartTime() + " " + ti.getEndTime() + " "
			+ startDate.getMonthValue() + "/" + startDate.getDayOfMonth() + "/" + startDate.getYear() + " "
			+ endDate.getMonthValue() + "/" + endDate.getDayOfMonth() + "/" + endDate.getYear() + "\n";
		} else {
			return name + "\n" + this.date.getMonthValue() + "/" + this.date.getDayOfMonth() + "/" + (this.date.getYear())
					+ " " + this.ti.getStartTime() + " " + this.ti.getEndTime() + " " + "\n";
		}
	}

	/**
	 * This method checks whether the event is occuring in given date.
	 * @param 	dDay	Given date to check the event's occurrence
	 * @return	true	if event occurs in dDay
	 * 			false	if event does not occurs in dDay
	 */
	public boolean isOccurring(LocalDate dDay) {
		if(this.isRecurring) {
			if(this.startDate.isBefore(dDay) && this.endDate.isAfter(dDay)) { // occurs in recursive days
				if(this.days.contains(dDay.getDayOfWeek())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if(this.date.equals(dDay)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
