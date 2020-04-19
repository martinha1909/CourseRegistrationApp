package server.model;

import java.io.BufferedReader;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class RegistrationApp {
	private Course theCourse;
	private CourseCatalogue cat;
	private PrintWriter socketOut;
	private Socket theSocket;
	private Database db;
	
	public RegistrationApp(Socket s, PrintWriter p, Database theDataBase)
	{
		theSocket = s;
		socketOut = p;
		db = theDataBase;
		cat = new CourseCatalogue(db);
	}
	
	public void addStudentCourses(String StudentId, String courseName, String courseN,String sec)
	{
		int id = Integer.parseInt(StudentId);
		int courseNum = Integer.parseInt(courseN);
		int section = Integer.parseInt(sec);
		
		Registration t = new Registration(db.getStudent(id));
		int i = 0;
		Student temp = db.getStudent(id);

		
		theCourse = cat.searchCat(courseName, courseNum);
		if(theCourse!=null)
		{
			
			t.completeRegistration(temp,theCourse.getCourseOfferingAt(section-1));
			db.decrementAvailableSeats(courseName, courseN, sec);
			sendString("Registration completed");
			sendString("end");
		}
		else
		{
			sendString("Could not register to specified course, try again");
			sendString("end");
		}

	}
	
	public void removeStudentCourses(String StudentId, String courseName, String courseNum, String courseSec)
	{

		int num = Integer.parseInt(courseNum);
		int id = Integer.parseInt(StudentId);

		int i = 0;
		Student temp = db.getStudent(id);
		if(temp!=null) {
			String toSend = temp.removeCourse(courseName, num);
			db.decrementAvailableSeats(courseName, courseNum, courseSec);
			sendString(toSend);
			sendString("end");
		}
		else {
			sendString("Student not found");
			sendString("end");
		}
	}
	
	public void viewAllStudentCourses(String StudentId) 
	{
		int id = Integer.parseInt(StudentId);


		int i = 0;
		Student temp = db.getStudent(id);
		
		String toSend = temp.printAllStudentCourses(theSocket);
		sendString(toSend);
		sendString("end");
	}
	
	private void sendString(String s) {
		
		socketOut.println(s);
		socketOut.flush();
	}

}
