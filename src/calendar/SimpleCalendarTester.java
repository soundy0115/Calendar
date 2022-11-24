package calendar;

import java.awt.GridLayout;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleCalendarTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		MyCalendar calendar = new MyCalendar();
		calendar.readFile("data/event.txt");
		
		
		
		
		
		LocalDate now = LocalDate.now();
		
		LocalDate firstDay = now.minusDays(now.getDayOfMonth()).plusDays(1);
		DayOfWeek firstDayOfWeek = firstDay.getDayOfWeek();
		int monthDays = now.lengthOfMonth();
		System.out.println(firstDay.toString());
		System.out.println(firstDayOfWeek);
		
		JFrame frame = new JFrame();
		frame.setTitle("Simple Calendar");

		JPanel monthPanel = new JPanel();
		
		ArrayList<JLabel> days = new ArrayList<JLabel>();
		days.add(new JLabel("S", JLabel.CENTER));
		days.add(new JLabel("M", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("W", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("F", JLabel.CENTER));
		days.add(new JLabel("S", JLabel.CENTER));
		monthPanel.setLayout(new GridLayout(7, 7));
		for(JLabel label : days) {
			monthPanel.add(label);
		}
		
		if(firstDayOfWeek.equals(DayOfWeek.SUNDAY)) {
			int full42 = 42;
			for(int i = 0; i < 0; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.MONDAY)) {
			int full42 = 42;
			for(int i = 0; i < 1; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.TUESDAY)) {
			int full42 = 42;
			for(int i = 0; i < 2; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
			int full42 = 42;
			for(int i = 0; i < 3; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.THURSDAY)) {
			int full42 = 42;
			for(int i = 0; i < 4; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.FRIDAY)) {
			int full42 = 42;
			for(int i = 0; i < 5; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		} else { // Saturday
			int full42 = 42;
			for(int i = 0; i < 6; i++) {
				monthPanel.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				monthPanel.add(new JButton(dayNumber));
				full42--;
			}
			while(full42 > 0) {
				monthPanel.add(new JLabel());
				full42--;
			}
		}
		
		

		frame.add(monthPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
