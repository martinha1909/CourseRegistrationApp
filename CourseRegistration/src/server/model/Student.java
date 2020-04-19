package server.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class Student {
	
	private String studentName;
	private int studentId;
	private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;
	private int offeringListSize;
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		offeringList = new ArrayList<CourseOffering>();
		offeringListSize = 0;
	}
	
	public void addCourse(CourseOffering theOffering)
	{
		offeringList.add(theOffering);
		offeringListSize++;
	}
	
	public void removeCourse(Registration theReg)
	{
		studentRegList.remove(theReg);
	}
	
	public String removeCourse(String courseName, int courseNum)
	{
		String sender = "";
		int counter = 0;
		for(int i=0; i<studentRegList.size(); i++)
		{
			if(studentRegList.get(i).getTheOffering().getTheCourse().getCourseName().toLowerCase().equals(courseName.toLowerCase()) && studentRegList.get(i).getTheOffering().getTheCourse().getCourseNum() == courseNum)
			{
				studentRegList.remove(i);
				sender += "Opperation successful";
				counter++;
			}
		}
		if(counter == 0)
			sender+="the student is not taking this class";
		return sender;
	}
	
	public String printAllStudentCourses(Socket s)
	{
		String sender = "";
		try {
			PrintWriter socketOut = new PrintWriter(s.getOutputStream(),true);
			Iterator<Registration> iterator = studentRegList.iterator();
			int i=0;
			if(studentRegList.size()!=0)
			{
				sender += "Here are your courses: \n";
				while(iterator.hasNext() && i<studentRegList.size())
				{
					sender +=  studentRegList.get(i) + "\n";
					i++;
				}	
					sender += "\n";
			}
			else
			{
				sender += "Student Name: " + studentName  + "    Student ID: "  + studentId + "\n\n\n              currently has no courses.";
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
		return sender;
	}
	public String getStudentName() {
		return studentName;
	}
	
	public ArrayList<CourseOffering> getOfferingList()
	{
		return offeringList;
	}
	public int getOfferingListSize()
	{
		return offeringListSize;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}
	
	public ArrayList<Registration> getRegList(){
		return studentRegList;
	}

}
