package client.controller;
import client.view.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** 
 * This class is used as a communication with the Client and the GUI
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 */
public class GUICommunicator {

	/**
	 * The display
	 */
	GUI theGUI;
	
	/**
	 * Initializes the display
	 */
	public GUICommunicator()
	{
		theGUI = new GUI("Course Registration App");
	}


	/**
	 * Receives 2 strings from the Client and depending on them, it 
	 * sends the information to the correct method in the GUI
	 * @param response is the string that will be displayed on the GUI
	 * @param number is used to determine which number was pressed
	 */
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


	/**
	 * Used to determine if a button has been pressed or not
	 * @return true if a button was pressed, otherwise return false
	 */
	public boolean buttonPressed() {
		
		if(theGUI.pressed == true)
		{
			return true;
		}
		else
			return false;
	}



	
}
