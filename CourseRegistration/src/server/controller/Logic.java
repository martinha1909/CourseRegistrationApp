package server.controller;
import server.model.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is what runs everytime a new thread is created and tells the thread what is has to do
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 *
 */

public class Logic implements Runnable{

	/**
	 * The socket that communicates with the Client
	 */
	private Socket theSocket;
	
	/**
	 * Used to take the input from the Client
	 */
	private BufferedReader socketIn;
	
	/**
	 * Used to send information back to the Client
	 */
	private PrintWriter socketOut;
	
	/**
	 * Used to access the Database in order to access the information
	 */
	private Database theDataBase;
	
	
	/**
	 * When object of type Logic is created it sets the Socket to s and the database to d
	 * It also initializes the socketIn and socketOut
	 * @param s is the desired Socket
	 * @param d is the Database
	 */
	public Logic(Socket s, Database d)
	{
		try
		{
			theSocket = s;
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter(theSocket.getOutputStream(),true);
			theDataBase = d;
		}catch(IOException e)
		{
			System.err.println("Error is present");
		}
		
	}
	
	/**
	 * What every thread does when it is created
	 */
	@Override
	public void run() {
		
		String line = null;
	
	while(true)
	{
		try
		{
			line = socketIn.readLine();
			String[] word = line.split(",");
			switcher(word,Integer.parseInt(word[word.length-1]));
			
			
		} catch (IOException e)
		{
			System.exit(0);
		}	
		
	}
		
	
	}
	
	/**
	 * Helper class that chooses which option to pick depending on what it received from the client-end
	 * @param word
	 * @param num
	 */
	private void switcher(String[] word,int num) {
	boolean enrolled; 
	String send = "";
		switch(num)
		{
			case 1:
				
				send = theDataBase.getCourse(word[0],word[1]);
				socketOut.println(send);
				socketOut.println("end");
				send = "";
				break;
				
			case 2:
				
				enrolled = theDataBase.isStudent(word[0], theSocket, socketOut);
				if(enrolled)
				{
					if(theDataBase.isValidCourse(word[1], word[2], word[3]))
					{
						if(!theDataBase.isCourseOffering(word[0], word[1], word[2], word[3]))
						{
							if(theDataBase.isAvailableSeats(word[1], word[2], word[3]))
							{
								theDataBase.insertCourseOffering(word[0], word[1], word[2], word[3]);
								theDataBase.decrementAvailableSeats(word[1], word[2], word[3]);
								socketOut.println("Operation successful");
								socketOut.println("end");
							}
							else {
								socketOut.println("The course section is full");
								socketOut.println("end");
							}
						}
						else
						{
							socketOut.println("You have already enrolled in this course");
							socketOut.println("end");
						}
					}
					else {
						socketOut.println("This course is not in the system.");
						socketOut.println("end");
					}
				}
				else
				{
					send = "Student is not enrolled in the system";
					socketOut.println(send);
					socketOut.println("end");
					send = "";
				}
				break;
				
			case 3:
				enrolled = theDataBase.isStudent(word[0], theSocket, socketOut);
				if(enrolled)
				{
					if(theDataBase.isValidCourse(word[1], word[2], word[3]))
					{
						if(theDataBase.isCourseOffering(word[0], word[1], word[2], word[3]))
						{
							theDataBase.deleteCourseOffering(word[0], word[1], word[2], word[3]);
							theDataBase.incrementAvailableSeats(word[1], word[2], word[3]);
							socketOut.println("Operation successful");
							socketOut.println("end");
						}
						else
						{
							socketOut.println("You have already enrolled in this course");
							socketOut.println("end");
						}
					}
					else {
						socketOut.println("This course is not in the system.");
						socketOut.println("end");
					}
				}
				else
				{
					socketOut.println("Student is not enrolled in the system");
					socketOut.println("end");
				}
				break;
				
			case 4:
		
				send = theDataBase.getCourseCatalogue();
				socketOut.println(send);
				socketOut.println("end");
				send = "";
				break;
				
			case 5:
				enrolled = theDataBase.isStudent(word[0], theSocket, socketOut);
				if(enrolled)
				{
					socketOut.println(theDataBase.getCourseOfferings(word[0]));
					socketOut.println("end");
				}
				else
				{
					socketOut.println("Student is not enrolled in the system end");
					socketOut.println("end");
				}
				break;
		}
			socketOut.flush();
		
	}

}
