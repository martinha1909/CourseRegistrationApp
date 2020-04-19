package server.model;

import java.sql.*;

/**
 * A simple database class to work with SQL servers. 
 * 
 * @author Duan Le
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

		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query1);
			stat.executeUpdate(query2);
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
	
	public Student searchStudent(int studentId)
	{
		Student t = null;;
		try {
			String query = "SELECT * FROM mydb.student where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, studentId);
			rs = pStat.executeQuery();
			if(rs.next())
				t = new Student(rs.getString("firstname"), Integer.parseInt(rs.getString("id")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
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
	 * @return true if the student is in the database, false otherwise
	 */
	public boolean isStudent(String studentId) {
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
			rs.next();
			int id = rs.getInt("id");
			int newSeats = rs.getInt("seats") + 1;
			
			String query2 = "UPDATE mydb.coursecatalogue SET seats = ? WHERE (id = ?)";
			PreparedStatement pStat2 = conn.prepareStatement(query2);
			pStat2.setInt(1, newSeats);
			pStat2.setInt(2, id);
			pStat2.executeUpdate();
			
			pStat1.close();
			pStat2.close();
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
			rs.next();
			int id = rs.getInt("id");
			int newSeats = rs.getInt("seats") - 1;
			
			String query2 = "UPDATE mydb.coursecatalogue SET seats = ? WHERE (id = ?)";
			PreparedStatement pStat2 = conn.prepareStatement(query2);
			pStat2.setInt(1, newSeats);
			pStat2.setInt(2, id);
			pStat2.executeUpdate();
			
			pStat1.close();
			pStat2.close();
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
    
    public int getNumberOfRows(String tableName)
    {
    	int count = 0;
    	try {
        	Statement s = conn.createStatement();
			rs = s.executeQuery("SELECT COUNT(*) AS rowcount FROM " + tableName);
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return count;
    }
    
    public Course getRow(String tableName, int rowId)
    {
    	Course c = null;
    	try {
    		String query = "SELECT * FROM " + tableName + " where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, rowId);
			rs = pStat.executeQuery();
			if(rs.next())
				c = new Course(rs.getString("coursename"), Integer.parseInt(rs.getString("coursenumber")));
			pStat.close();
			rs.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return c;
    }
    
    public int getSecNum(int rowId)
    {
    	int section = 0;
    	try {
    		String query = "SELECT * FROM mydb.coursecatalogue where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, rowId);
			rs = pStat.executeQuery();
			if(rs.next())
				section = Integer.parseInt(rs.getString("section"));
			pStat.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return section;
    }
    
    public int getSecCap(int rowId)
    {
    	int cap = 0;
    	try {
    		String query = "SELECT * FROM mydb.coursecatalogue where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, rowId);
			rs = pStat.executeQuery();
			if(rs.next())
				cap = Integer.parseInt(rs.getString("seats"));
			rs.close();
			pStat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return cap;
    }

	public Student getStudent(String studentId) {
		Student s = null;
    	try {
    		String query = "SELECT * FROM 	mydb.student where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, studentId);
			rs = pStat.executeQuery();
			if(rs.next())
				s = new Student(rs.getString("firstname"), Integer.parseInt(rs.getString("id")));
			pStat.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return s;
	}
}
