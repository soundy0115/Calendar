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
	 * Mutator
	 * : Create Event and notify to the ChangeListeners
	 * 
	 * @param	event	the event that will be added into the calendar.
	 */
	public void newEvent(Event event) {
		calendar.add(event);
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	/**
	 * Mutator
	 * : Move D-Day and notify to the ChangeListeners
	 * 
	 * @param	day		if it is 1, it will plus 1 day to the dday,
	 * 					if it is -1, it will minus 1 day to the dday.
	 * 					only 1 or -1 can be entered.
	 */
	public void moveDDay(int day) {
		if(day == 1) {
			dDay = dDay.plusDays(1);
		} else if(day == -1) {
			dDay = dDay.minusDays(1);
		} else {
		}
		
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	/**
	 * Mutator
	 * : Set the D-Day and notify to the ChangeListeners
	 * 
	 * @param	date	D-Day will be set to the date(LocalDate type)
	 */
	public void setDDay(LocalDate date) {
		dDay = date;
		
		ChangeEvent e = new ChangeEvent(this);
		for(ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
	
	/**
	 * Accessor: get all Events
	 * 
	 * @return	return all events in the calendar in treeset.
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
	 * 
	 * @return	return all events that occurs in D-Day in treeset structure.
	 */
	public ArrayList<Event> getDDayEvents(LocalDate dDay){
		ArrayList<Event> dDayEvents = new ArrayList<Event>();
		for(Event e : calendar.getEvents()) {
			if(e.isOccurring(dDay)) {
				dDayEvents.add(e);
			}
		}
		return dDayEvents;
	}
	
	/**
	 * Attach: attach listeners to the model
	 * 
	 * @param	listener	ChangeListener from the view.
	 * 						it will be stored in the data structure(listeners),
	 * 						and be notified when there is any change in the model.
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	
	
	
}
