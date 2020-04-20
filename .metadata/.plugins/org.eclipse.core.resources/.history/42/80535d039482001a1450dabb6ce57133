package server.model;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 */
public class CourseOffering {
	
	private int secNum;
	private int secCap;
	private Course theCourse;
	private ArrayList <Registration> offeringRegList;
	private int offeringRegListSize;
	
	public CourseOffering (int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList <Registration>();
		offeringRegListSize = 0;
	}
	public int getSecNum() {
		return secNum;
	}
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	public int getSecCap() {
		return secCap;
	}
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	public Course getTheCourse() {
		return theCourse;
	}
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	@Override
	public String toString () {
		Iterator<Registration> iterator = offeringRegList.iterator();
		int index = 0;
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: "+ getSecCap() +"\n";
		st += "All students in this section are: \n";
		if(offeringRegListSize!=0)
		{
			while(iterator.hasNext()&& index < offeringRegListSize)
			{
				st+= offeringRegList.get(index).getTheStudent().toString() + "\n";
				index++;
			}
		}
		else
			st+="No students have registered in this section yet";
		
		return st;
	}
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);
		offeringRegListSize++;
	}
	
	public void removeRegistration(Registration registration)
	{
		offeringRegList.remove(registration);
		offeringRegListSize--;
	}
	

}
