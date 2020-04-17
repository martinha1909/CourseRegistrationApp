package server.controller;
import server.model.RegistrationApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Logic implements Runnable{

	private Socket theSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private RegistrationApp theLogic;
	
	public Logic(Socket s)
	{
		try
		{
			theSocket = s;
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter(theSocket.getOutputStream(),true);
			theLogic = new RegistrationApp(theSocket,socketOut);
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
		
		switch(num)
		{
			case 1:
				
				theLogic.searchCatalogueCourses(word[0],word[1]);
				
				break;
				
			case 2:
				
				theLogic.addStudentCourses(word[0],word[1],word[2],word[3]);
				
				break;
				
			case 3:

				theLogic.removeStudentCourses(word[0],word[1], word[2]);
				
				break;
				
			case 4:
		
				theLogic.viewAllCourses();
				
				break;
				
			case 5:
			
				theLogic.viewAllStudentCourses(word[0]);
				
				break;
		}
		
	}

}
