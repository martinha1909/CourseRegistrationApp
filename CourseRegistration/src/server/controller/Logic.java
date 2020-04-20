package server.controller;
import server.model.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Logic implements Runnable{

	private Socket theSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private Database theDataBase;
	
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
			System.err.println("ERROR!");
		}	
		
	}
		
	
	}
	
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
				
				enrolled = theDataBase.isStudent(word[0]);
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
				enrolled = theDataBase.isStudent(word[0]);
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
				enrolled = theDataBase.isStudent(word[0]);
				if(enrolled)
					socketOut.println(theDataBase.getCourseOfferings(word[0]));
				else
					socketOut.println("Student is not enrolled in the system end");
				break;
		}
			socketOut.flush();
		
	}

}
