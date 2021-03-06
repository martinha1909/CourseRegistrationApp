package server.model;

/**
 * A first time setup of databases and tables creation.
 * The default schema name is mydb.
 * Only RUN this ONCE. 
 * 
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 */
public class DBSetup {
	
	public static void main(String[] args) {
		Database dbsetup = new Database();
		dbsetup.initializeConnection();
		dbsetup.createTables();
		
		//Add students to database
		dbsetup.insertStudent(1, "Duan", "Le");
		dbsetup.insertStudent(2, "Martin", "Ha");
		dbsetup.insertStudent(3, "Ayush", "Chaudhari");
		dbsetup.insertStudent(4, "John", "Smith");
		dbsetup.insertStudent(5, "Bob", "MacDonald");
		
		//Add courses to database
		dbsetup.insertCourse(1, "ENGG", "200", "01", 100);
		dbsetup.insertCourse(2, "ENGG", "200", "02", 100);
		dbsetup.insertCourse(3, "ENGG", "201", "01", 100);
		dbsetup.insertCourse(4, "ENGG", "201", "02", 100);
		dbsetup.insertCourse(5, "ENGG", "202", "01", 100);
		dbsetup.insertCourse(6, "ENGG", "202", "02", 100);
		dbsetup.insertCourse(7, "ENGG", "225", "01", 100);
		dbsetup.insertCourse(8, "ENGG", "225", "02", 100);
		dbsetup.insertCourse(9, "ENGG", "233", "01", 100);
		dbsetup.insertCourse(10, "ENGG", "233", "02", 100);
		dbsetup.insertCourse(11, "MATH", "211", "01", 100);
		dbsetup.insertCourse(12, "MATH", "211", "02", 100);
		dbsetup.insertCourse(13, "MATH", "275", "01", 100);
		dbsetup.insertCourse(14, "MATH", "275", "02", 100);
		dbsetup.insertCourse(15, "MATH", "277", "01", 100);
		dbsetup.insertCourse(16, "MATH", "277", "02", 100);
		dbsetup.insertCourse(17, "MATH", "200", "01", 100);
		dbsetup.insertCourse(18, "MATH", "200", "02", 100);
		dbsetup.insertCourse(19, "PHYS", "259", "01", 100);
		dbsetup.insertCourse(20, "PHYS", "259", "02", 100);
		dbsetup.insertCourse(21, "CHEM", "209", "01", 100);
		dbsetup.insertCourse(22, "CHEM", "209", "02", 100);
				
		dbsetup.closeConnection();
	}
}
