package client.controller

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunication {
	
	private GUICommunicator theInput;
	private PrintWriter socketOut;
	private Socket theSocket;
	private BufferedReader socketIn;
	
	public ClientCommunication(String serverName, int portNumber)
	{
		try
		{
			theSocket = new Socket(serverName,portNumber);
			theInput = new GUICommunicator();
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter (theSocket.getOutputStream());
		}catch(IOException e)
		{
			System.err.println("Error!!!");
		}
	}
	
	
	public void communicate()
	{
		String line = "";
		
		String response = "";
		
		while(true)
		{
			try
			{
				line = theInput.buttonPressed();
				
				String [] temp = line.split(",");
						
				socketOut.println(line);
			
				response = socketIn.readLine();
			
				theInput.sendResponse(response,temp[temp.length-1]);
				
			}catch(IOException e)
			{
				System.err.println("Error has occured");
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
