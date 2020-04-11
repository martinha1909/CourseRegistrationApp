package server.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class RegistrationApp {
	private Student student;
	private Course theCourse;
	private CourseCatalogue cat;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private Socket theSocket;
	
	RegistrationApp(Socket s)
	{
		theSocket = s;
		try {
			socketIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			socketOut = new PrintWriter(theSocket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
		student = new Student("Martin", 30068529);
		cat = new CourseCatalogue();
	}
	
	public void searchCatalogueCourses(String courseName, int courseNum)
	{
		theCourse = cat.searchCat(courseName, courseNum);
		if (theCourse != null) {
			sendString("Course found: ");
			sendString(theCourse.getCourseOfferingAt(0).toString());
			sendString(theCourse.getCourseOfferingAt(1).toString());
		}
		else
		{
			sendString("Sorry we can't find this course in our system. ");
		}
		sendString("\n");
	}
	
	public void viewAllCourses()
	{
		sendString(cat.toString());
		sendString("\n");
	}
	
	public void addStudentCourses(String courseName, int courseNum)
	{
		theCourse = cat.searchCat(courseName, courseNum);
		if(theCourse!=null)
		{
			ArrayList<CourseOffering> offList = theCourse.getOfferingList();
			for(int i=0; i<offList.size(); i++)
			{
				sendString(i+1 + ". " + offList.get(i).toString());
				sendString("\n");
			}
//			try {
//				int choice = Integer.parseInt(socketIn.readLine());
//				Registration rg;
//				switch(choice)
//				{
//				case 1:
//					rg = new Registration(student, theCourse.getCourseOfferingAt(0));
//					break;
//				case 2:
//					rg = new Registration(student, theCourse.getCourseOfferingAt(1));
//					break;
//				case 3:
//					rg = new Registration(student, theCourse.getCourseOfferingAt(2));
//				}
				student.printAllStudentCourses(theSocket);
				sendString("\n");
			sendString("\n");
//			} catch (NumberFormatException | IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	public void removeStudentCourses(int choice)
	{
		/**
		 * This line should be in the server right before this function is called:
		 * sendString("Which course would you like to remove? ");
		 */
		student.printAllStudentCourses(theSocket);
		if(student.getOfferingListSize()!=0)
		{
			Registration rg = new Registration(student, student.getOfferingList().get(choice-1), "remove");
			sendString("Operation succedded! ");
		}
		sendString("\n");
	}
	
	public void viewAllStudentCourses() 
	{
		student.printAllStudentCourses(theSocket);
	}
	
	private void sendString(String s) {
		socketOut.println(s);
		socketOut.flush();
	}

}
