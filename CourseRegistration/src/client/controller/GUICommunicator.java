package client.controller

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
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to enroll into:");
				String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to enroll into:");
				String section = JOptionPane.showInputDialog(p,"Enter the section you want to enroll into:");
				
				sending = studentId + "," courseName + "," + courseNumber + "," + section + ",2";	
			}
				
		});
		
		theGUI.b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to remove:");
				
				
				sending = studentId + "," + courseName + ",3";	
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
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				sending = studentId + ",5";	
			}
				
		});
		return sending;
	}

	public void sendResponse(String response,String number) {
		
		
		int n = Integer.parseInt(number);
		
		if(n == 1)
		{
			theGUI.displayMessageWindow("SearchResult",response);
		}
		else if(n == 2)
		{
			theGUI.displayMessageWindow("RemoveResult",response);
		}
		else if(n == 3)
		{
			theGUI.displayMessageWindow("RemoveResult",response);
		}
		else if(n == 4)
		{
			theGUI.displayCatalogue(response);
		}
		
		else if(n == 5)
		{
			theGUI.displayStudentCourses(response);
		}
		
			
	}



	
}
