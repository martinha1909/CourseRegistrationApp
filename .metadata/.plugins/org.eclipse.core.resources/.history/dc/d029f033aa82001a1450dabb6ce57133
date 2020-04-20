package client.controller;
import client.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class GUICommunicator {

	GUI theGUI;
	
	public GUICommunicator()
	{
		theGUI = new GUI("Course Registration App");
	}


	public void sendResponse(String response,String number) {
		
		
		int n = Integer.parseInt(number);
		
		switch(n)
		{
			case 1:
				
				theGUI.displayMessageWindowfor1(response);
				break;
				
			case 2:
				
				theGUI.displayMessageWindowfor2(response);
				break;
				
			case 3:
				
				theGUI.displayMessageWindowfor2(response);
				break;
				
			case 4:
				
				theGUI.displayCatalogue(response);
				break;
				
			case 5:
				
				theGUI.displayStudentCourses(response);	
				break;
		}
	}

	public String result() {
		
		return theGUI.sending.get(theGUI.sending.size()-1);
	}


	public boolean buttonPressed() {
		
		if(theGUI.pressed == true)
		{
			return true;
		}
		else
			return false;
	}



	
}
