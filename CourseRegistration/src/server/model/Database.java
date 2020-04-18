package server.model;

import java.sql.*;

public class Database implements DBCredentials {
	private Connection conn;
	private ResultSet rs;

	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a schema, ONLY RUN ONCE
	 */
	public void createSchema() {
		String query = "CREATE SCHEMA mydb";

		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Schema could not be created.");
		}

		System.out.println("Schema created successfully.");
	}

	/**
	 * Create a student table and a course catalogue table, ONLY RUN ONCE
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
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public void insertStudent(int id, String firstName, String lastName) {
		try {
			// student is the table name
			// id, firstname, lastname are the columns
			// try changing mydb.student to just student if not working
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

	public String searchStudent(int studentId) {
		String toSend = "";
		try {
			String query = "SELECT * FROM mydb.student where id = studentId";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, studentId);
			rs = pStat.executeQuery();
			while (rs.next()) {
				toSend += rs.getInt("id") + rs.getString("firstName") + rs.getString("lastName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toSend;
	}

	public void deleteUser(int id) {
		String query = "DELETE FROM student WHERE id = 'id'";
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertCourse(int id, String courseName, String courseNumber) {
		try {
			// coursecatalogue is the table name
			// id, coursename, coursenumber are the columns
			// try changing mydb.coursecatalogue to just coursecatalogue if not working
			String query = "INSERT INTO mydb.coursecatalogue (id, coursename, coursenumber) VALUES(?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);

			pStat.setInt(1, id);
			pStat.setString(2, courseName);
			pStat.setString(3, courseNumber);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the course catalogue
	 * 
	 * @return the whole course catalogue from the database as a single string
	 */
	public String getCourseCatalogue() {
		String s = "";

		try {

			// try changing mydb.coursecatalogue to just coursecatalogue if not working
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM coursecatalogue";
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				s = s + rs.getString("courseName") + " " + rs.getString("courseNumber") + "\n";
			}

			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}

	public void deleteCourse(String name, String num) {
		String query = "DELETE FROM coursecatalogue WHERE courseName = 'name' and courseNum = 'num'";
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
