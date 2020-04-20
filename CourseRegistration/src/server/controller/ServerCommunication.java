package server.controller;
import server.*;
import server.model.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class ServerCommunication {

	private ServerSocket s;
	private Database db;
	private ExecutorService pool;
	
	
	public ServerCommunication(int port)
	{
		try
		{
			s = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
			db = new Database();
			db.initializeConnection();
		}catch(IOException e)
		{
			System.err.println("Error");
		}
	}
	
	private void communication() {
		
		while(true)
		{
			try
			{
				pool.execute(new Logic(s.accept(), db));
				
			}catch(IOException e)
			{
				System.err.println("There is an Error");
			}
		}
	}
	
		

	
	
	public static void main(String[] arg) throws IOException
	{
		ServerCommunication theServer = new ServerCommunication(8099);
		theServer.communication();
		
	}

	

} 
