package calendar;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleCalendarTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setTitle("Simple Calendar");
	
		
		JPanel panel = new JPanel();
		
		
		
		BoxLayout boxLO = new BoxLayout(panel, BoxLayout.X_AXIS);
		panel.setLayout(boxLO);
		
		ArrayList<JLabel> days = new ArrayList<JLabel>();
		days.add(new JLabel("S", JLabel.CENTER));
		days.add(new JLabel("M", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("W", JLabel.CENTER));
		days.add(new JLabel("T", JLabel.CENTER));
		days.add(new JLabel("F", JLabel.CENTER));
		days.add(new JLabel("S", JLabel.CENTER));
		
		
		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new GridLayout(6, 7));
		for(JLabel label : days) {
			monthPanel.add(label);
		}
		for(int i = 1; i <= 35; i++) {
			//JButton emptyButton = new JButton();
			
			String dayNumber = Integer.toString(i);
			monthPanel.add(new JButton(dayNumber));
		}
		panel.add(monthPanel);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
