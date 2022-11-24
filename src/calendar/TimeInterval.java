package calendar;

import java.time.*;

public class TimeInterval { // Provide a method to check whether two intervals overlap.
	private LocalTime startTime;
	private LocalTime endTime;

	
	/**
	 * TimeInterval Constructor
	 * 
	 * @param startTime	Start time of Event
	 * @param endTime	End time of Event
	 */
	public TimeInterval(LocalTime startTime, LocalTime endTime) {
		// TODO Auto-generated constructor stub
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Return start time of event
	 * 
	 * @return	this.startTime
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Return end time of event
	 * 
	 * @return	this.endTime
	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Check conflict between new event and existing events
	 * 
	 * @param 	that	new created event
	 * @return	true	if created event time conflicts with existing event time
	 * 			false	if created event time does not conflict with existing event time
	 */
	public boolean conflictCheck(TimeInterval that) {
		if((this.startTime.isBefore(that.endTime) && this.endTime.isAfter(that.startTime)) || (that.startTime.isBefore(this.endTime) && that.endTime.isAfter(this.startTime))) {
			return true;
		} else {
			return false;
		}
	}

}
