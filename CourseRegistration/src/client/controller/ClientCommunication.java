package client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** 
 * The client side of the program that communicates with the server
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class ClientCommunication {
	
	/**
	 * Variable that is used to access the options in the GUI
	 */
	private GUICommunicator theInput;
	
	/**
	 * Used to send information to the Server
	 */
	private PrintWriter socketOut;
	
	/**
	 * The connection to the Server
	 */
	private Socket theSocket;
	
	/**
	 * Used to receive information from the Server
	 */
	private BufferedReader socketIn;
	
	/**
	 * Initializes all connections for this class
	 * @param serverName
	 * @param portNumber
	 */
	public ClientCommunication(String serverName, int portNumber)
	{
		try
		{
			theSocket = new Socket(serverName,portNumber);
			theInput = new GUICommunicator();
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter (theSocket.getOutputStream(),true);
			
			System.out.println("Connected to server");
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method that is used to communicate with the Server, will be used to send and receive information
	 */
	public void communicate()
	{
		while (true)
		{
			String line = null;
		
			String response = "";
		
			String send = "";
			String [] temp = null;
			while(true)
			{
				try
				{
					System.out.print("");
					while(theInput.buttonPressed())
					{
					
						if(line == null)
						{
							line = theInput.result();	
							temp = line.split(",");
							socketOut.println(line);
							socketOut.flush();
						}
						
						
						response = socketIn.readLine();
					
						if(response.contentEquals("end"))
						{
							theInput.theGUI.pressed = false;
							theInput.sendResponse(send,temp[temp.length-1]);
							temp = null;
							line = null;
							send = "";
							break;
						}

						send += response + "\n";
					
					
					}
					
				}catch(IOException e)
				{
					System.err.println("Error has occured");
				}
			}
		}
	}
	
	public static void main (String[] arg) throws IOException
	{
		ClientCommunication theClient = new ClientCommunication("localhost",8099);
		
		theClient.communicate();
		
		theClient.socketIn.close();
		theClient.socketOut.close();
	}
	
}
