package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Simple Calendar Application
 * 
 * The Calendar application that displays month view and day view.
 * User can create the event, move the day. Current will be highlighted by the gray color.
 * This program also detects error when the user try to create an event that conflicts with the existing events.
 * 
 * @version 22.11.30
 * @author Juhan Lee
 * 
 *
 */


public class SimpleCalendarTester {
	
	/**
	 * The Main method of the calendar which contains container and view in it.
	 * Most of the part is view, except the JButtons and JTextField.
	 * The Controller of the application (JButtons and JTextField),
	 * user can move current day and create events.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		CalendarModel model = new CalendarModel();
		
		model.getCalendar().readFile("data/event.txt");
		
		// D-Day setup
		final LocalDate[] dDays = {model.getDDay()};
		LocalDate firstDay = dDays[0].minusDays(dDays[0].getDayOfMonth()).plusDays(1); // 11-01
		DayOfWeek firstDayOfWeek = firstDay.getDayOfWeek(); // SUN.MON.TUE
		int monthDays = dDays[0].lengthOfMonth(); // 28, 29, 30, 31
		
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				dDays[0] = model.getDDay();
			}
			
		});
		
		// Main Frame GridBagLayout
		JFrame frame = new JFrame();
		frame.setTitle("Simple Calendar");
		frame.setBackground(Color.WHITE);
		GridBagLayout gridBag = new GridBagLayout();
		frame.setLayout(gridBag);
		GridBagConstraints con = new GridBagConstraints();
		



		// Move Buttons
		JPanel moveBtnPanel = new JPanel();
		moveBtnPanel.setBackground(Color.WHITE);
		moveBtnPanel.setLayout(new FlowLayout());
		
		JButton minusBtn = new JButton("<");
		minusBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.moveDDay(-1);
			}
		});
		JButton plusBtn = new JButton(">");
		plusBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		quitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					model.getCalendar().writeFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
			
		});
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
				
				
				boolean successAdd = model.getCalendar().add(newEventInfo); 
				if(successAdd) {
					createFrame.setVisible(false);
				} else {
					// Create Error
					JFrame errorFrame = new JFrame();
					
					
					JPanel errorPanel = new JPanel();
					errorPanel.setLayout(new BorderLayout());
					errorPanel.setBackground(Color.red);
					JLabel errorLabel = new JLabel("Time Conflict Error", SwingConstants.CENTER);
					
					errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					errorLabel.setBackground(Color.red);
					errorLabel.setForeground(Color.black);
					errorLabel.setPreferredSize(new Dimension(250, 100));
					errorLabel.setFont(new Font("Verdana", Font.BOLD, 20));
					
					
					errorPanel.add(errorLabel, BorderLayout.CENTER);
					errorFrame.add(errorPanel);
					errorFrame.pack();
					errorFrame.setTitle("Error");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					errorFrame.setVisible(true);
					
					
				}

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

		
		// Day info
		JPanel dayInfoPanel = new JPanel();
		dayInfoPanel.setBackground(Color.WHITE);
		String dayInfo = dDays[0].getDayOfWeek().toString() + " " + dDays[0].getMonthValue() + "/" + dDays[0].getDayOfMonth();
		JLabel dayInfoLabel = new JLabel(dayInfo);
		model.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				String dayInfo = dDays[0].getDayOfWeek().toString() + " " + dDays[0].getMonthValue() + "/" + dDays[0].getDayOfMonth();
				dayInfoLabel.setText(dayInfo);
			}
			
		});
		dayInfoPanel.add(dayInfoLabel);
		con.fill = GridBagConstraints.HORIZONTAL;
		con.weightx = 0.5;
		con.gridx = 2;
		con.gridwidth = 2;
		con.gridy = 2;		
		frame.add(dayInfoPanel, con);
		
		con.gridwidth = 1;
		
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


		
		int[] days42 = new int[42];
		JButton[] btn42 = new JButton[42];
		for(int i = 0; i < days42.length; i++) {
			final int j = i;
			btn42[i] = new JButton();
			btn42[i].setBackground(Color.WHITE);
			if(firstDayOfWeek.equals(DayOfWeek.SUNDAY)) {
				if((j + 1) > 0 && (j + 1) <= monthDays) {
					if((j + 1) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j + 1));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}
			} else if(firstDayOfWeek.equals(DayOfWeek.MONDAY)) {
				if(j > 0 && j <= monthDays) {
					if(j == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}
			} else if(firstDayOfWeek.equals(DayOfWeek.TUESDAY)) {
				if((j - 1) > 0 && (j - 1) <= monthDays) {
					if((j - 1) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j - 1));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}

			} else if(firstDayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
				if((j - 2) > 0 && (j - 2) <= monthDays) {
					if((j - 2) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j - 2));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}

			} else if(firstDayOfWeek.equals(DayOfWeek.THURSDAY)) {
				if((j - 3) > 0 && (j - 3) <= monthDays) {
					if((j - 3) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j - 3));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}

			} else if(firstDayOfWeek.equals(DayOfWeek.FRIDAY)) {
				if((j - 4) > 0 && (j - 4) <= monthDays) {
					if((j - 4) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j - 4));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}

			} else {
				if((j - 5) > 0 && (j - 5) <= monthDays) {
					if((j - 5) == dDays[0].getDayOfMonth()) {
						btn42[j].setBackground(Color.GRAY);
					} else {
						btn42[j].setBackground(Color.WHITE);
					}
					btn42[j].setText(Integer.toString(j - 5));
				} else {
					btn42[j].setBackground(Color.WHITE);
					btn42[j].setText("");
				}
			}
			model.addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					
					final LocalDate[] dDays = {model.getDDay()};
					LocalDate firstDay = dDays[0].minusDays(dDays[0].getDayOfMonth()).plusDays(1); // 11-01
					DayOfWeek firstDayOfWeek = firstDay.getDayOfWeek(); // SUN.MON.TUE
					int monthDays = dDays[0].lengthOfMonth(); // 28, 29, 30, 31
					if(firstDayOfWeek.equals(DayOfWeek.SUNDAY)) {
						if((j + 1) > 0 && (j + 1) <= monthDays) {
							if((j + 1) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j + 1));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}
					} else if(firstDayOfWeek.equals(DayOfWeek.MONDAY)) {
						if(j > 0 && j <= monthDays) {
							if(j == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}
					} else if(firstDayOfWeek.equals(DayOfWeek.TUESDAY)) {
						if((j - 1) > 0 && (j - 1) <= monthDays) {
							if((j - 1) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j - 1));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}

					} else if(firstDayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
						if((j - 2) > 0 && (j - 2) <= monthDays) {
							if((j - 2) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j - 2));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}

					} else if(firstDayOfWeek.equals(DayOfWeek.THURSDAY)) {
						if((j - 3) > 0 && (j - 3) <= monthDays) {
							if((j - 3) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j - 3));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}

					} else if(firstDayOfWeek.equals(DayOfWeek.FRIDAY)) {
						if((j - 4) > 0 && (j - 4) <= monthDays) {
							if((j - 4) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j - 4));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}

					} else {
						if((j - 5) > 0 && (j - 5) <= monthDays) {
							if((j - 5) == dDays[0].getDayOfMonth()) {
								btn42[j].setBackground(Color.GRAY);
							} else {
								btn42[j].setBackground(Color.WHITE);
							}
							btn42[j].setText(Integer.toString(j - 5));
						} else {
							btn42[j].setBackground(Color.WHITE);
							btn42[j].setText("");
						}
					}
				}			
			});
			
			btn42[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final LocalDate[] dDays = {model.getDDay()};
					if(btn42[j].getText() != "") {
						LocalDate setDay = LocalDate.of(dDays[0].getYear(), dDays[0].getMonth(), Integer.parseInt(btn42[j].getText()));
						model.setDDay(setDay);

					}
				}
				
			});
			monthBody.add(btn42[i]);
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
		
		// Day View Panel
		JPanel dayViewPanel = new JPanel();
		dayViewPanel.setBackground(Color.WHITE);
		dayViewPanel.setLayout(new FlowLayout());
		JTextArea dayViewText = new JTextArea(20, 40);
		dayViewText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dayViewText.setBackground(Color.WHITE);
		dayViewText.setFont(new Font("Verdana", Font.BOLD, 18));
		
		String dayEventStr = "";		
		for(Event event : model.getDDayEvents(dDays[0])) {
			dayEventStr += event.toString();
			dayEventStr += "\n";
		}		
		dayViewText.setText(dayEventStr);

		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				String dayEventStr = "";
				final LocalDate[] dDays = {model.getDDay()};
				
				for(Event event : model.getDDayEvents(dDays[0])) {
					dayEventStr += event.toString();
					dayEventStr += "\n";
				}
				
				dayViewText.setText(dayEventStr);
			}			
		});
		
		con.gridx = 2;
		con.gridwidth = 2;
		con.gridy = 3;
		dayViewPanel.add(dayViewText);
		frame.add(dayViewPanel, con);

		
		
		// Closing frame
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
