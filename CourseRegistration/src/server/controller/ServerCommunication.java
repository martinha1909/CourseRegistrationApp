package server.controller

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {

	private ServerSocket s;
	private Socket theSocket;
	private BufferedReader socketIn;
	private RegistrationApp theLogic;
	
	
	public ServerCommunication(int port)
	{
		try
		{
			s = new ServerSocket(port);
			theSocket = s.accept();
			socketIn = new BufferedReader (new InputStreamReader(theSocket.getInputStream()));
			theLogic = new RegistrationApp(theSocket);
		}catch(IOException e)
		{
			System.err.println("Error");
		}
	}
	
	private void communication() {
		
		String line = null;
		
		while(true)
		{
			try
			{
				line = socketIn.readLine();
				
				String[] word = line.split(",");
				
				switch(Integer.parseInt(word[word.length-1]))
				{
					case 1:
						
						theLogic.searchCatalogueCourses(word[0],word[1]);
						break;
						
					case 2:
						
						theLogic.addStudentCourses(word[0],word[1],word[2],word[3]);
						break;
						
					case 3:
						
						theLogic.removeStudentCourses(word[0]);
						break;
						
					case 4:
						
						theLogic.viewAllCourses();
						break;
						
					case 5:
						
						theLogic.viewAllStudentCourses(word[0]);
						break;
					
				}
			} catch (IOException e)
			{
				System.err.println("ERROR!");
			}
		}
		
	}
	
	public static void main(String[] arg) throws IOException
	{
		ServerCommunication theServer = new ServerCommunication(8099);
		theServer.communication();
		
		theServer.socketIn.close();
	}


} 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {

	private ServerSocket s;
	private Socket theSocket;
	private BufferedReader socketIn;
	private RegistrationApp theLogic;
	
	
	public ServerCommunication(int port)
	{
		try
		{
			s = new ServerSocket(port);
			theSocket = s.accept();
			socketIn = new BufferedReader (new InputStreamReader(theSocket.getInputStream()));
			theLogic = new RegistrationApp(theSocket);
		}catch(IOException e)
		{
			System.err.println("Error");
		}
	}
	
	private void communication() {
		
		String line = null;
		
		while(true)
		{
			try
			{
				line = socketIn.readLine();
				
				String[] word = line.split(",");
				
				switch(Integer.parseInt(word[word.length-1]))
				{
					case 1:
						
						theLogic.searchCatalogueCourses(word[0],word[1]);
						break;
						
					case 2:
						
						theLogic.addStudentCourses(word[0],word[1],word[2],word[3]);
						break;
						
					case 3:
						
						theLogic.removeStudentCourses(word[0],word[1]);
						break;
						
					case 4:
						
						theLogic.viewAllCourses();
						break;
						
					case 5:
						
						theLogic.viewAllStudentCourses(word[0]);
						break;
					
				}
			} catch (IOException e)
			{
				System.err.println("ERROR!");
			}
		}
		
	}
	
	public static void main(String[] arg) throws IOException
	{
		ServerCommunication theServer = new ServerCommunication(8099);
		theServer.communication();
		
		theServer.socketIn.close();
	}


} 
