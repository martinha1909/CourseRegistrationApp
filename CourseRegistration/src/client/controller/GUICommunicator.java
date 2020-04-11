import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUICommunicator {

	GUI theGUI;
	
	public GUICommunicator()
	{
		theGUI = new GUI("FRAME");
	}

	public String buttonPressed() {
		
		String sending = "";
		
		theGUI.b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
				String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to search for:");
				
				
				sending = courseName + "," + courseNumber + ",1";	
			}
				
		});
		
		theGUI.b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
				String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to search for:");
				
				
				sending = courseName + "," + courseNumber + ",2";	
			}
				
		});
		
		theGUI.b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
				
				
				sending = courseName + ",1";	
			}
				
		});
		
		theGUI.b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				sending = "4";	
			}
				
		});
		
		theGUI.b5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				sending = "5";	
			}
				
		});
		return sending;
	}

	public void sendResponse(String response) {
		
		
		
	}

	
}
