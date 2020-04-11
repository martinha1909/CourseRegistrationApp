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
		menu();
	}
	public void menu()
	{
		sendString("Please choose one of the following opperations: ");
		sendString("1. Search catalogue courses ");
		sendString("2. Add course to student courses ");
		sendString("3. Remove course from student courses ");
		sendString("4. View all courses in catalogue ");
		sendString("5. View all courses taken by student ");
	}
	
	public void searchCatalogueCourses()
	{
		sendString("Please enter the name of the course you want to search ");
		try {
			String courseName = socketIn.readLine();
			sendString("Please enter the number of " + socketIn.readLine() + " course");
			int courseNum = Integer.parseInt(socketIn.readLine());
			theCourse = cat.searchCat(courseName, courseNum);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
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
	
	public void addStudentCourses()
	{
		try {
			sendString("Please enter the name of the course you want to add: ");
			String courseName = socketIn.readLine();
			sendString("Please enter the number of " + socketIn.readLine() + " course");
			int courseNum = Integer.parseInt(socketIn.readLine());
			theCourse = cat.searchCat(courseName, courseNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(theCourse!=null)
		{
			ArrayList<CourseOffering> offList = theCourse.getOfferingList();
			sendString("Which offering would you like to choose? ");
			for(int i=0; i<offList.size(); i++)
			{
				sendString(i+1 + ". " + offList.get(i).toString());
				sendString("\n");
			}
			try {
				int choice = Integer.parseInt(socketIn.readLine());
				Registration rg;
				switch(choice)
				{
				case 1:
					rg = new Registration(student, theCourse.getCourseOfferingAt(0));
					break;
				case 2:
					rg = new Registration(student, theCourse.getCourseOfferingAt(1));
					break;
				case 3:
					rg = new Registration(student, theCourse.getCourseOfferingAt(2));
				}
				student.printAllStudentCourses(theSocket);
				sendString("\n");
			sendString("\n");
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeStudentCourses()
	{
		sendString("Which course would you like to remove? ");
		student.printAllStudentCourses(theSocket);
		if(student.getOfferingListSize()!=0)
		{
			int choice;
			try {
				choice = Integer.parseInt(socketIn.readLine());
				Registration rg = new Registration(student, student.getOfferingList().get(choice-1), "remove");
				sendString("Operation succedded! ");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
