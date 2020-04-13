package client.controller;
import client.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUICommunicator {

	GUI theGUI;
	public String sending;
	
	public GUICommunicator()
	{
		theGUI = new GUI("Course Registration App");
		sending = "";
	}

	public void buttonPressed() {
		
		
		
		theGUI.getB1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
				String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to search for:");
				
				System.out.println("I get here");
				sending = courseName + "," + courseNumber + ",1";
				System.out.println(sending);
			}
				
		});
		
		theGUI.getB2().addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to enroll in:");
				String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to enroll in:");
				String section = JOptionPane.showInputDialog(p,"Enter the section you want to enroll in:");
				
				sending = studentId + "," + courseName + ",3";	
			}
			
				
		});
		
		theGUI.getB3().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to remove:");
				
				
				sending = studentId + "," + courseName + ",3";	
			}
				
		});
		
		theGUI.getB4().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				sending = "4";	
			}
				
		});
		
		theGUI.getB5().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				
				String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
				sending = studentId + ",5";	
			}
				
		});
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
			String [] word = response.split(",");
			theGUI.displayCatalogue(word);
		}
		
		else if(n == 5)
		{
			String [] word = response.split(",");
			theGUI.displayStudentCourses(word[0],word[1],word[word.length-1]);
		}
		
			
	}

	public String result() {
		
		return sending;
	}



	
}
