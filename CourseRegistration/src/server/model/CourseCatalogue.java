package server.model;
import java.util.ArrayList;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	private Database db;
	public CourseCatalogue (Database theDataBase) {
		courseList = new ArrayList<Course>();
		db = theDataBase;
		loadFromDataBase ();
	}
	
	private void loadFromDataBase() {
		setCourseList();
		for(int i=1; i<=db.getNumberOfRows("mydb.coursecatalogue"); i++)
		{
			createCourseOffering(courseList.get(i-1), db.getSecNum(i), db.getSecCap(i));
		}
		
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


	public void setCourseList() {
		for(int i=1; i<=db.getNumberOfRows("mydb.coursecatalogue"); i++)
		{
			courseList.add(db.getRow("mydb.coursecatalogue", i));
		}
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
