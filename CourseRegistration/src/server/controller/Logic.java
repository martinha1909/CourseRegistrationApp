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
		
	String send = "";
		switch(num)
		{
			case 1:
				
				send = theDataBase.searchCatalogueCourses(word[0],word[1]);
				socketOut.println(send);
				break;
				
			case 2:
				
				send = theDataBase.addStudentCourses(word[0],word[1],word[2],word[3]);
				socketOut.println(send);
				break;
				
			case 3:

				send = theDataBase.removeStudentCourses(word[0],word[1], word[2]);
				socketOut.println(send);
				break;
				
			case 4:
		
				send = theDataBase.getCourseCatalogue();
				socketOut.println(send);
				break;
				
			case 5:
			
				send = theDataBase.viewAllStudentCourses(word[0]);
				socketOut.println(send);
				break;
		}
		
	}

}
