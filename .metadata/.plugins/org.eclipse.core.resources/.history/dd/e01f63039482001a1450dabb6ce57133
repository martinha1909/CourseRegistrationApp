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
	private ArrayList <Student> students;
	private Course theCourse;
	private CourseCatalogue cat;
	private PrintWriter socketOut;
	private Socket theSocket;
	
	public RegistrationApp(Socket s, PrintWriter p)
	{
		students = new ArrayList<Student>();
		theSocket = s;
		socketOut = p;
		
		students.add(new Student("Martin", 30068529));
		students.add(new Student("Ayush", 12345678));
		students.add(new Student("Duan", 87654321));
		cat = new CourseCatalogue();
	}
	
	public void searchCatalogueCourses(String courseName, String courseN)
	{
		int courseNum = Integer.parseInt(courseN);
		theCourse = cat.searchCat(courseName, courseNum);
		if (theCourse != null) {
			sendString("Course found: ");
			sendString(theCourse.getCourseOfferingAt(0).toString());
			sendString(theCourse.getCourseOfferingAt(1).toString());
			sendString(theCourse.getCourseOfferingAt(2).toString());
			sendString("end");
		}
		else
		{
			sendString("Sorry we can't find this course in our system. \n");
			sendString("end");
		}
	}
	
	public void viewAllCourses()
	{
		sendString(cat.toString());
		sendString("end");
	}
	
	public void addStudentCourses(String StudentId, String courseName, String courseN,String sec)
	{
		int id = Integer.parseInt(StudentId);
		int courseNum = Integer.parseInt(courseN);
		int section = Integer.parseInt(sec);
		
		Registration t = null;
		Iterator k = students.iterator();
		for(int j=0; j<students.size(); j++)
		{
			if(students.get(j).getStudentId() == id)
				t = new Registration(students.get(j));
		}
		int i = 0;
		Student temp = null;
		while(k.hasNext() && i < students.size())
		{
			if(students.get(i).getStudentId() == id)
			{
				temp = students.get(i);
			}
			i++;
		}

		
		theCourse = cat.searchCat(courseName, courseNum);
		if(theCourse!=null)
		{
			
			t.completeRegistration(temp,theCourse.getCourseOfferingAt(section-1));
			sendString("Registration completed");
			sendString("end");
		}
		else
		{
			sendString("Could not register to specified course, try again");
			sendString("end");
		}

	}
	
	public void removeStudentCourses(String StudentId, String courseName, String courseNum)
	{

		int num = Integer.parseInt(courseNum);
		int id = Integer.parseInt(StudentId);

		Iterator k = students.iterator();

		int i = 0;
		Student temp = null;
		while(k.hasNext() && i < students.size())
		{
			if(students.get(i).getStudentId() == id)
			{
				temp = students.get(i);
			}
			i++;
		}
		if(temp!=null) {
			String toSend = temp.removeCourse(courseName, num);
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

		Iterator k = students.iterator();

		int i = 0;
		Student temp = null;
		while(k.hasNext() && i < students.size())
		{
			if(students.get(i).getStudentId() == id)
			{
				temp = students.get(i);
			}
			i++;
		}
		
		String toSend = temp.printAllStudentCourses(theSocket);
		sendString(toSend);
		sendString("end");
	}
	
	private void sendString(String s) {
		
		socketOut.println(s);
		socketOut.flush();
	}

}
