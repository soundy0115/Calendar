package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SimpleCalendarTester {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		CalendarModel model = new CalendarModel();
		
		model.getCalendar().readFile("data/event.txt");
		
		// D-Day setup
		final LocalDate[] dDays = {model.getDDay()};
		
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				dDays[0] = model.getDDay();
			}
			
		});
		
		LocalDate firstDay = dDays[0].minusDays(dDays[0].getDayOfMonth()).plusDays(1); // 11-01
		DayOfWeek firstDayOfWeek = firstDay.getDayOfWeek(); // SUN.MON.TUE
		int monthDays = dDays[0].lengthOfMonth(); // 28, 29, 30, 31

		
		// Main Frame GridBagLayout
		JFrame frame = new JFrame();
		frame.setTitle("Simple Calendar");
		frame.setBackground(Color.WHITE);
		GridBagLayout gridBag = new GridBagLayout();
		frame.setLayout(gridBag);
		GridBagConstraints con = new GridBagConstraints();
		
		model.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				frame.repaint();
				System.out.println("Repainted");
			}
			
		});


		// Move Buttons
		JPanel moveBtnPanel = new JPanel();
		moveBtnPanel.setBackground(Color.WHITE);
		moveBtnPanel.setLayout(new FlowLayout());
		
		JButton minusBtn = new JButton("<");
		minusBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.moveDDay(-1);
			}
		});
		JButton plusBtn = new JButton(">");
		plusBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.moveDDay(1);
			}			
		});
		minusBtn.setBackground(Color.GRAY);
		plusBtn.setBackground(Color.GRAY);
		moveBtnPanel.add(minusBtn);
		moveBtnPanel.add(plusBtn);
		
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 0;
		frame.add(moveBtnPanel, con);

		// Empty Panel
		JPanel emptyPanel1 = new JPanel();
		emptyPanel1.setLayout(new FlowLayout());
		emptyPanel1.add(new JLabel(" "));

		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.ipady = 10;
		con.gridx = 1;
		con.gridy = 0;
		frame.add(emptyPanel1, con);
		
		// Empty Panel
		JPanel emptyPanel2 = new JPanel();
		emptyPanel2.setLayout(new FlowLayout());
		emptyPanel2.add(new JLabel(" "));
		
		emptyPanel2.setBackground(Color.WHITE);
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.ipady = 10;
		con.gridx = 2;
		con.gridy = 0;
		frame.add(emptyPanel2, con);
		
		// Full View Quit Button
		
		JPanel emptyPanel6 = new JPanel();
		emptyPanel6.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton quitBtn = new JButton("Quit");
		quitBtn.setBackground(Color.RED);
		quitBtn.setForeground(Color.WHITE);
		quitBtn.setBorderPainted(false);
		emptyPanel6.add(quitBtn);
		
		emptyPanel6.setBackground(Color.WHITE);
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.ipady = 0;
		con.gridx = 3;
		con.gridy = 0;
		frame.add(emptyPanel6, con);
		
		
		// empty line
		JPanel emptyLine = new JPanel();
		con.gridx = 0;
		con.gridy = 1;
		frame.add(emptyLine, con);
		
		
		// CREATE Frame
		JFrame createFrame = new JFrame();
		JPanel createMainPanel = new JPanel();
		createMainPanel.setLayout(new BoxLayout(createMainPanel, BoxLayout.Y_AXIS));
		
		JPanel createUpperPanel = new JPanel();
		createUpperPanel.setBackground(Color.WHITE);
		createUpperPanel.setLayout(new FlowLayout());
		JLabel eventText = new JLabel("Event Name: ");
		createUpperPanel.add(eventText);
		JTextField eventName = new JTextField("Event Name", 16);	
		createUpperPanel.add(eventName);
		createMainPanel.add(createUpperPanel);
		
		
		JPanel createLowerPanel = new JPanel();
		createLowerPanel.setLayout(new FlowLayout());
		createLowerPanel.add(new JLabel("From"));
		JTextField eventStartTime = new JTextField("00:00", 5);
		createLowerPanel.add(eventStartTime);
		createLowerPanel.add(new JLabel("To"));
		JTextField eventEndTime = new JTextField("23:59", 5);
		createLowerPanel.add(eventEndTime);
		JButton saveBtn = new JButton("SAVE");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// String split
				String name = eventName.getText();
				String startTime[] = eventStartTime.getText().split(":");
				int startTimeHour = Integer.parseInt(startTime[0]);
				int startTimeMin = Integer.parseInt(startTime[1]);
				
				String endTime[] = eventEndTime.getText().split(":");
				int endTimeHour = Integer.parseInt(endTime[0]);
				int endTimeMin = Integer.parseInt(endTime[1]);
				
				LocalDate date = model.getDDay();
				LocalTime stTime = LocalTime.of(startTimeHour, startTimeMin);
				LocalTime edTime = LocalTime.of(endTimeHour, endTimeMin);
				
				Event newEventInfo = new Event(name, date, stTime, edTime);
				model.getCalendar().add(newEventInfo);
				createFrame.setVisible(false);
			}
			
		});
		createLowerPanel.add(saveBtn);
		
		
		createMainPanel.add(createLowerPanel);
		createFrame.add(createMainPanel);
		createFrame.pack();
		createFrame.setVisible(false);
		
		// Full View Create Button
		JPanel createBtnPanel = new JPanel();
		createBtnPanel.setBackground(Color.WHITE);
		createBtnPanel.setLayout(new BoxLayout(createBtnPanel, BoxLayout.Y_AXIS));
		JButton createBtn = new JButton("Create");
		createBtn.setBackground(Color.RED);
		createBtn.setForeground(Color.WHITE);
		createBtn.setBorderPainted(false);
		createBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		createBtnPanel.add(createBtn);
		createBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createFrame.setVisible(true);
			}
			
		});
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 2;
		frame.add(createBtnPanel, con);
		
		
		// Empty Panel 3
		JPanel emptyPanel3 = new JPanel();
		emptyPanel3.add(new JLabel(" "));
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 2;
		frame.add(emptyPanel3, con);
		
		// Empty Panel
		JPanel emptyPanel4 = new JPanel();
		emptyPanel4.setBackground(Color.WHITE);
		emptyPanel4.add(new JLabel(" "));
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 2;
		con.gridy = 2;
		frame.add(emptyPanel4, con);
		
		// Day info
		JPanel dayInfoPanel = new JPanel();
		dayInfoPanel.setBackground(Color.WHITE);
		String dayInfo = dDays[0].getDayOfWeek().toString() + " " + dDays[0].getMonthValue() + "/" + dDays[0].getDayOfMonth();
		JLabel dayInfoLabel = new JLabel(dayInfo);
		dayInfoPanel.add(dayInfoLabel);
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 3;
		con.gridy = 2;		
		frame.add(dayInfoPanel, con);
		
		
		
		// Month View Main
		JPanel monthPanel = new JPanel();
		monthPanel.setBackground(Color.WHITE);
		monthPanel.setLayout(new BorderLayout());
		
		// Month View Name
		JPanel monthHead = new JPanel();
		monthHead.setBackground(Color.WHITE);
		JLabel monthLabel = new JLabel(dDays[0].getMonth().toString() + " " + dDays[0].getYear());
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				monthLabel.setText(dDays[0].getMonth().toString() + " " + dDays[0].getYear());
				
			}
		});
		monthHead.add(monthLabel);
		monthPanel.add(monthHead, BorderLayout.NORTH);

		
		// Month View Grid
		JPanel monthBody = new JPanel();
		monthBody.setBackground(Color.WHITE);
		monthBody.setLayout(new GridLayout(7, 7));
		
		ArrayList<JLabel> days = new ArrayList<JLabel>();
		days.add(new JLabel("S", JLabel.CENTER));
		days.add(new JLabel("M", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("W", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("F", JLabel.CENTER));
		days.add(new JLabel("S", JLabel.CENTER));


		for(JLabel label : days) {
			monthBody.add(label);
		}
		

		
		if(firstDayOfWeek.equals(DayOfWeek.SUNDAY)) {
			int full42 = 42;
			for(int i = 0; i < 0; i++) {

				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.MONDAY)) {
			int full42 = 42;
			for(int i = 0; i < 1; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.TUESDAY)) {
			int full42 = 42;
			for(int i = 0; i < 2; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
			int full42 = 42;
			for(int i = 0; i < 3; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.THURSDAY)) {
			int full42 = 42;
			for(int i = 0; i < 4; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else if(firstDayOfWeek.equals(DayOfWeek.FRIDAY)) {
			int full42 = 42;
			for(int i = 0; i < 5; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		} else { // Saturday
			int full42 = 42;
			for(int i = 0; i < 6; i++) {
				monthBody.add(new JLabel());
				full42--;
			}
			for(int i = 1; i <= monthDays; i++) {
				//JButton emptyButton = new JButton();				
				String dayNumber = Integer.toString(i);
				JButton newBtn = new JButton(dayNumber);
				final Integer innerI = i;
				if(innerI == dDays[0].getDayOfMonth()) {
					// TODO Auto-generated method stub
					newBtn.setBackground(Color.GRAY);
				} else {
					newBtn.setBackground(Color.WHITE);					
				}
				model.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						if(innerI == dDays[0].getDayOfMonth()) {
							// TODO Auto-generated method stub
							newBtn.setBackground(Color.GRAY);
						} else {
							newBtn.setBackground(Color.WHITE);					
						}
					}					
				});
				monthBody.add(newBtn);
				full42--;
			}
			while(full42 > 0) {
				monthBody.add(new JLabel());
				full42--;
			}
		}
		monthPanel.add(monthBody, BorderLayout.CENTER);
		
		con.fill = GridBagConstraints.HORIZONTAL;
		con.anchor = GridBagConstraints.NORTH;
		con.weightx = 0.5;
		con.gridx = 0;
		con.gridy = 3;		
		frame.add(monthPanel, con);

		// Empty Panel
		JPanel emptyPanel5 = new JPanel();
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 3;
		frame.add(emptyPanel5, con);
		
		
		// Day View Time
		JPanel dayTimePanel = new JPanel();
		dayTimePanel.setBackground(Color.WHITE);
		dayTimePanel.setLayout(new GridLayout(0, 1));
		for(int i = 5; i <= 12; i++) {
			
			if(i == 12) {
				JLabel timeLabel = new JLabel(Integer.toString(i) + "PM ", SwingConstants.RIGHT);
				timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				dayTimePanel.add(timeLabel);

			} else {
				JLabel timeLabel = new JLabel(Integer.toString(i) + "AM ", SwingConstants.RIGHT);
				timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				dayTimePanel.add(timeLabel);
			}
			JLabel emptyLabel = new JLabel("              ");
			emptyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayTimePanel.add(emptyLabel);
		}
		
		for(int i = 1; i <= 12; i++) {
			JLabel timeLabel = new JLabel(Integer.toString(i) + "PM ", SwingConstants.RIGHT);
			timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			dayTimePanel.add(timeLabel);
			JLabel emptyLabel = new JLabel("              ");
			emptyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayTimePanel.add(emptyLabel);
		}
		
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 2;
		con.gridy = 3;
		frame.add(dayTimePanel, con);
		
		
		// Day View Main
		
		JPanel dayView = new JPanel();
		dayView.setLayout(new GridLayout(0, 1));
		for(int i = 0; i < 40; i++) {
			JLabel timeInfo = new JLabel("                                                                 ");
			timeInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayView.add(timeInfo);
		}
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.ipadx = 500;
		con.gridx = 3;
		con.gridy = 3;
		frame.add(dayView, con);
		
		// Closing frame
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		

	}

}
