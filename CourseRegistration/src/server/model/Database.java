package server.model;

import java.sql.*;

public class Database implements DBCredentials
{
	private Connection conn;
	private ResultSet rs;
	public void initializeConnection()
	{
		try
		{
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void closeConnection()
	{
		try
		{
			rs.close();
			conn.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	//You can just create a table on MySQL Workbench. If we implement this
	//function, it would only be allowed to run once anyways
	public void createTables()
	{

	}

	/**
	 * Inserts a student into the database
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public void insertStudent(int id, String firstName, String lastName)
	{
		try 
		{
			//student is the table name
			//id, firstname, lastname are the columns
			//try changing mydb.student to just student if not working
			String query = "INSERT INTO mydb.student (id, firstname, lastname) VALUES(?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			
			pStat.setInt(1, id);
			pStat.setString(2, firstName);
			pStat.setString(3, lastName);
			pStat.executeUpdate();
			pStat.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}


	public String searchStudent(int id, String firstName, String lastName)
	{
		String toSend = "";
		try {
			String query = "SELECT * FROM mydb.student where (id, firstName, lastName) VALUES (?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setString(2, firstName);
			pStat.setString(3,  lastName);
			rs = pStat.executeQuery();
			while(rs.next()) {
				toSend += rs.getInt("id") + rs.getString("firstName") + rs.getString("lastName");
			}
		}catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return toSend;
	}
	
	public void deleteUser(int id)
	{
		String query = "DELETE FROM student WHERE id = 'id'";
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertCourse(int id, String courseName, String courseNumber)
	{
		try 
		{
			//coursecatalogue is the table name
			//id, coursename, coursenumber are the columns
			//try changing mydb.coursecatalogue to just coursecatalogue if not working
			String query = "INSERT INTO mydb.coursecatalogue (id, coursename, coursenumber) VALUES(?, ?, ?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			
			pStat.setInt(1, id);
			pStat.setString(2, courseName);
			pStat.setString(3, courseNumber);
			pStat.executeUpdate();
			pStat.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets the course catalogue
	 * @return the whole course catalogue from the database as a single string
	 */
	public String getCourseCatalogue()
	{
		String s = "";

		try
		{

			//try changing mydb.coursecatalogue to just coursecatalogue if not working
			String query = "SELECT * FROM coursecatalogue";
			Statement stat = conn.createStatement();
			
			rs = stat.executeUpdate(query);
			
			while (rs.next())
			{
				s  = s + rs.getString("courseName") + " " + rs.getString("courseNumber") + "\n";
			}

			stat.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return s;
	}
	
	public void deleteCourse(String name, String num)
	{
		String query = "DELETE FROM coursecatalogue WHERE courseName = 'name' and courseNum = 'num'";
		try {
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
