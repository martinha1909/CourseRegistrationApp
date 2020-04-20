package client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
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
			socketOut = new PrintWriter (theSocket.getOutputStream(),true);
			
			System.out.println("Connected to server");
		}catch(IOException e)
		{
			System.err.println("Error!!!");
		}
	}
	
	
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
