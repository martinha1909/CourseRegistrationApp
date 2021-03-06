package server.model;

import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/**
 * A simple database class to work with SQL servers. 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 */
public class Database implements DBCredentials {
	
	/**
	 * The connection
	 */
	private Connection conn;
	
	/**
	 * The ResultSet to hold information retrieved from tables
	 */
	private ResultSet rs;

	/**
	 * Connects to the SQL server
	 */
	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be established.");
		}

		System.out.println("Connection successfully established.");
	}

	/**
	 * Closes the connection to the SQL server
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a student table and a course catalogue table
	 * For DBSetup class to use, could also be implemented so admin can use
	 */
	public void createTables() {
		String query1 = "CREATE TABLE student " + 
					   "(id INTEGER not NULL, " + 
					   " firstname VARCHAR(255), " + 
					   " lastname VARCHAR(255), " + 
					   " PRIMARY KEY ( id ))";

		String query2 = "CREATE TABLE coursecatalogue " + 
					   "(id INTEGER not NULL, " + 
					   " coursename VARCHAR(255), " + 
					   " coursenumber VARCHAR(255), " +
					   " section VARCHAR(255), " +
					   " seats INTEGER not NULL, " +
					   " PRIMARY KEY ( id ))";
		
		String query3 = "CREATE TABLE courseoffering " + 
					   "(keyid INTEGER not NULL AUTO_INCREMENT, " + 
					   " studentid INTEGER not NULL, " + 
					   " courseid INTEGER not NULL, " +
					   " grade VARCHAR(255), " +
					   " PRIMARY KEY ( keyid ))";

		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query1);
			stat.executeUpdate(query2);
			stat.executeUpdate(query3);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Table(s) could not be created.");
		}
		
		System.out.println("Tables created successfully in given schema.");
	}

	/**
	 * Inserts a student into the database
	 * For DBSetup class to use, could also be implemented so admin can use
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public void insertStudent(int id, String firstName, String lastName) {
		try {
			String query = "INSERT INTO mydb.student (id, firstname, lastname) VALUES (?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);

			pStat.setInt(1, id);
			pStat.setString(2, firstName);
			pStat.setString(3, lastName);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Inserts a course into the database
	 * For DBSetup class to use, could also be implemented so admin can use
	 * 
	 * @param id
	 * @param courseName
	 * @param courseNumber
	 */
	public void insertCourse(int id, String courseName, String courseNumber, String section, int seats) {
		try {
			String query = "INSERT INTO mydb.coursecatalogue (id, coursename, coursenumber, section, seats) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);

			pStat.setInt(1, id);
			pStat.setString(2, courseName);
			pStat.setString(3, courseNumber);
			pStat.setString(4, section);
			pStat.setInt(5, seats);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if student is registered in the database
	 * 
	 * @param studentId the id of the student
	 * @param socketOut 
	 * @param theSocket 
	 * @return true if the student is in the database, false otherwise
	 */
	public boolean isStudent(String studentId, Socket theSocket, PrintWriter socketOut) {
		try {
			int ID = Integer.parseInt(studentId);
			String query = "SELECT * FROM mydb.student where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, ID);
			rs = pStat.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			rs.close();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			socketOut.println("Student Id is not a number, please try again.");
			socketOut.println("end");
		}
		return false;
	}
	
	/**
	 * Checks if there are still free seats in a course section in database
	 * 
	 * @return true if there are still available seats, false otherwise
	 */
	public boolean isAvailableSeats(String courseName, String courseNumber, String section) {
		try {
			String query = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, courseName);
			pStat.setString(2, courseNumber);
			pStat.setString(3, section);
			rs = pStat.executeQuery();
			rs.next();
			
			if (rs.getInt("seats") <= 0)
			{
				return false;
			}
			
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Increases available seats in a section by one
	 * Call this method when a student successfully dropped an enrolled course
	 */
	public void incrementAvailableSeats(String courseName, String courseNumber, String section) {
		try {
			String query1 = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat1 = conn.prepareStatement(query1);
			pStat1.setString(1, courseName);
			pStat1.setString(2, courseNumber);
			pStat1.setString(3, section);
			rs = pStat1.executeQuery();
			int id, newSeats;
			
			if (rs.next()) {
				id = rs.getInt("id");
				newSeats = rs.getInt("seats") + 1;

				String query2 = "UPDATE mydb.coursecatalogue SET seats = ? WHERE (id = ?)";
				PreparedStatement pStat2 = conn.prepareStatement(query2);
				pStat2.setInt(1, newSeats);
				pStat2.setInt(2, id);
				pStat2.executeUpdate();
				pStat2.close();
			}
					
			pStat1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Decreases available seats in a section by one
	 * Call this method when a student successfully enrolled in a course
	 */
	public void decrementAvailableSeats(String courseName, String courseNumber, String section) {
		try {
			String query1 = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat1 = conn.prepareStatement(query1);
			pStat1.setString(1, courseName);
			pStat1.setString(2, courseNumber);
			pStat1.setString(3, section);
			rs = pStat1.executeQuery();
			
			int id, newSeats;
			
			if (rs.next()) {
				id = rs.getInt("id");
				newSeats = rs.getInt("seats") - 1;

				String query2 = "UPDATE mydb.coursecatalogue SET seats = ? WHERE (id = ?)";
				PreparedStatement pStat2 = conn.prepareStatement(query2);
				pStat2.setInt(1, newSeats);
				pStat2.setInt(2, id);
				pStat2.executeUpdate();
				pStat2.close();
			}
			
			pStat1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a course and all of its sections from the catalogue from database
	 * 
	 * @return the course and its sections from  catalogue from the database as a single string if found else return course not found
	 */
	public String getCourse(String courseName, String courseNumber) {
		int num = Integer.parseInt(courseNumber);
		String s = "";

		try {
			String query = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, courseName);
			stmt.setInt(2, num);
			rs = stmt.executeQuery();
			
			if (rs.next())
			{
				s += rs.getString("coursename") + " " + rs.getString("coursenumber") + " | Section: " +  rs.getString("section") + ", Available Seats: " + rs.getInt("seats") + "\n";
				while (rs.next()) {
					s += rs.getString("coursename") + " " + rs.getString("coursenumber") + " | Section: " +  rs.getString("section") + ", Available Seats: " + rs.getInt("seats") + "\n";
				}
			} else {
				s += "Course not found.";
			}
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
     * Gets the course catalogue
     * 
     * @return the whole course catalogue from the database as a single string
     */
    public String getCourseCatalogue() {
        String s = "";

        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mydb.coursecatalogue";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
            	s += rs.getString("coursename") + " " + rs.getString("coursenumber") + " | Section: " +  rs.getString("section") + ", Available Seats: " + rs.getInt("seats") + "\n";
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }
    
    /**
     * Inserts an enrolled into the courseoffering table in the database
     * 
     * @param studentId
     * @param courseName
     * @param courseNumber
     * @param section
     */
    public void insertCourseOffering(String studentId, String courseName, String courseNumber, String section) {
		try {	
			String query1 = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat1 = conn.prepareStatement(query1);
			pStat1.setString(1, courseName);
			pStat1.setString(2, courseNumber);
			pStat1.setString(3, section);
			rs = pStat1.executeQuery();
			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt("id");
			}
			
			int studentid = Integer.parseInt(studentId);
				
			String query3 = "INSERT INTO mydb.courseoffering (studentid, courseid, grade) VALUES(?, ?, ?)";
			PreparedStatement pStat3 = conn.prepareStatement(query3);
			pStat3.setInt(1, studentid);
			pStat3.setInt(2, courseid);
			pStat3.setString(3, "N/A");
			pStat3.executeUpdate();
			
			pStat1.close();
			pStat3.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
       
    /**
     * Checks if the student is enrolled in said course
     * 
     * @param studentId
     * @param courseName
     * @param courseNumber
     * @param section
     * @return true or false
     */
    public boolean isCourseOffering(String studentId, String courseName, String courseNumber, String section) {
    	try {
    		String query1 = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat1 = conn.prepareStatement(query1);
			pStat1.setString(1, courseName);
			pStat1.setString(2, courseNumber);
			pStat1.setString(3, section);
			rs = pStat1.executeQuery();
			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt("id");
			}
			
			int studentid = Integer.parseInt(studentId);
			
			String query3 = "SELECT * FROM mydb.courseoffering where studentid = ? and courseid = ?";
			PreparedStatement pStat3 = conn.prepareStatement(query3);
			pStat3.setInt(1, studentid);
			pStat3.setInt(2, courseid);	
			rs = pStat3.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    /**
     * Deletes an enrolled into the courseoffering table in the database
     * 
     * @param studentId
     * @param courseName
     * @param courseNumber
     * @param section
     */
    public void deleteCourseOffering(String studentId, String courseName, String courseNumber, String section) {
    	try {	
			String query1 = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat1 = conn.prepareStatement(query1);
			pStat1.setString(1, courseName);
			pStat1.setString(2, courseNumber);
			pStat1.setString(3, section);
			rs = pStat1.executeQuery();
			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt("id");
			}
			
			int studentid = Integer.parseInt(studentId);
				
			String query3 = "DELETE FROM mydb.courseoffering WHERE studentid = ? and courseid = ?";
			PreparedStatement pStat3 = conn.prepareStatement(query3);
			pStat3.setInt(1, studentid);
			pStat3.setInt(2, courseid);
			pStat3.executeUpdate();
			
			pStat1.close();
			pStat3.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @return all the enrolled courses of a student as a single string
     */
    public String getCourseOfferings(String studentId) {
		int studentid = Integer.parseInt(studentId);
		String s = "";
		
    	try {
    		String query = "SELECT * FROM mydb.courseoffering where studentid = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, studentid);
			rs = stmt.executeQuery();
			
			ArrayList<Integer> arr = new ArrayList<Integer>();
			
			if (rs.next()) {
				arr.add(rs.getInt("courseid"));
				while (rs.next()) {
					arr.add(rs.getInt("courseid"));
				}
				
				s += "You are enrolled in:\n\n";
				for (int i = 0; i < arr.size(); i++) {
					String query2 = "SELECT * FROM mydb.coursecatalogue where id = ?";
					PreparedStatement stmt2 = conn.prepareStatement(query2);
					stmt2.setInt(1, arr.get(i));
					rs = stmt2.executeQuery();
					if (rs.next()) {
						s += rs.getString("coursename") + " " + rs.getString("coursenumber") + " | Section: " +  rs.getString("section") + "\n";
					}
				}
			} else {
				s += "You are not enrolled in any courses.";
			}
			
			stmt.close();
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return s;
    }
    

    /**
     * Checks to see if the inputed course is a valid course in the Database
     * @param courseName
     * @param courseNumber
     * @param courseSec
     * @return
     */
	public boolean isValidCourse(String courseName, String courseNumber, String courseSec) {
		try {
			String query = "SELECT * FROM mydb.coursecatalogue where coursename = ? and coursenumber = ? and section = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, courseName);
			pStat.setString(2, courseNumber);
			pStat.setString(3, courseSec);
			rs = pStat.executeQuery();
			if(rs.next())
				return true;
			pStat.close();
			rs.close();
		}catch (SQLException e) {
    		e.printStackTrace();
    	}
		return false;
	}
}
