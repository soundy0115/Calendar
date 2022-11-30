package calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel {
	private ArrayList<ChangeListener> listeners;
	private MyCalendar calendar;
	private LocalDate dDay;
	
	
	/**
	 * Constructor of Model
	 */
	public CalendarModel() {
		dDay = LocalDate.now();
		calendar = new MyCalendar();
		listeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 * Mutator: Create Event
	 */
	public void newEvent(Event event) {
		calendar.add(event);
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	/**
	 * Mutator: Move D-Day
	 * @param day
	 */
	public void moveDDay(int day) {
		if(day == 1) {
			dDay = dDay.plusDays(1);
			System.out.println(dDay);
		} else if(day == -1) {
			dDay = dDay.minusDays(1);
			System.out.println(dDay);
		} else {
			System.out.println("Error: should be +1 or -1");
		}
		
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	
	/**
	 * Accessor: get all Events
	 */
	public TreeSet<Event> getAllEvents() {
		return calendar.getEvents();
	}
	
	public MyCalendar getCalendar() {
		return calendar;
	}
	
	public LocalDate getDDay() {
		return dDay;
	}
	
	/**
	 * Accessor: get D-Day's events
	 */
	public ArrayList<Event> getDDayEvents(LocalDate dDay){
		ArrayList<Event> dDayEvents = new ArrayList<Event>();
		for(Event e : calendar.getEvents()) {
			if(e.getDate().equals(dDay)) {
				dDayEvents.add(e);
			}
		}
		return dDayEvents;
	}
	

	
	
	/**
	 * Attach: attach listeners to the model
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	
	
	
}
