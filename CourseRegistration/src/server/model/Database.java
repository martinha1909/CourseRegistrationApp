package server.model;

import java.sql.*;

public class Database implements DBCredentials
{
	private Connection conn;

	public initializeConnection()
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
			ResultSet rs;

			//try changing mydb.coursecatalogue to just coursecatalogue if not working
			String query = "SELECT * FROM mydb.coursecatalogue";
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
}
