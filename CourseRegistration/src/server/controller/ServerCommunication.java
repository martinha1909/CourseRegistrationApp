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
 * The server side of the program that communicates with the Client
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 */
public class ServerCommunication {

	/**
	 * Used to establish a connection with the Client
	 */
	private ServerSocket s;
	
	/**
	 * The database that will be used
	 */
	private Database db;
	
	/**
	 * A thread pool so the program can run for multiple clients
	 */
	private ExecutorService pool;
	
	/**
	 * Intializes all variables
	 * @param port
	 */
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to communicate with the Client side and creates threads every time a new Client appears
	 */
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
