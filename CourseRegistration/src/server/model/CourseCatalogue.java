package server.model;
import java.util.ArrayList;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	
	public CourseCatalogue () {
		loadFromDataBase ();
	}
	
	private void loadFromDataBase() {
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());
		createCourseOffering(db.courseList.get(0), 1, 100);
		createCourseOffering(db.courseList.get(0), 2, 200);
		createCourseOffering(db.courseList.get(0), 3, 200);
		createCourseOffering(db.courseList.get(1), 1, 400);
		createCourseOffering(db.courseList.get(1), 2, 300);
		createCourseOffering(db.courseList.get(1), 3, 300);
		createCourseOffering(db.courseList.get(2), 1, 50);
		createCourseOffering(db.courseList.get(2), 2, 70);
		createCourseOffering(db.courseList.get(2), 3, 70);
		
	}
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			c.addOffering(theOffering);
		}
	}
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.toLowerCase().equals(c.getCourseName().toLowerCase()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		displayCourseNotFoundError();
		return null;
	}
	private void displayCourseNotFoundError() {
		System.err.println("Course was not found!");
		
	}
	public ArrayList <Course> getCourseList() {
		return courseList;
	}


	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	@Override
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c.toString(); 
		}
		return st;
	}

}
