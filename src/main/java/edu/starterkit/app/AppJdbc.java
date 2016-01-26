package edu.starterkit.app;

/* STEP 1. Import required packages
 * Import the packages: Requires that you include the packages containing the JDBC classes needed
 * for database programming. Most often, using import java.sql.* will suffice.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * http://www.tutorialspoint.com/jdbc/index.htm
 */
public class AppJdbc {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/world";

	// Database credentials
	static final String USER = "test";
	static final String PASS = "test";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			/*
			 * STEP 2: Register JDBC driver
			 * Register the JDBC driver: Requires that you initialize a driver
			 * so you can open a communication channel with the database.
			 */
			System.out.println("Registering driver...");
			Class.forName("com.mysql.jdbc.Driver");

			/* STEP 3: Open a connection
			 * Open a connection: Requires using the DriverManager.getConnection() method 
			 * to create a Connection object, which represents a physical connection with the database.
			 */
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			/* STEP 4: Execute a query
			 * Execute a query: Requires using an object of type Statement for building and
			 * submitting an SQL statement to the database.
			 */
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT code, name, population FROM country";
			ResultSet rs = stmt.executeQuery(sql);

			/* STEP 5: Extract data from result set
			 * Extract data from result set: Requires that you use the appropriate 
			 * ResultSet.getXXX() method to retrieve the data from the result set.
			 */
			while (rs.next()) {
				// Retrieve by column name
				String code = rs.getString("code");
				String name = rs.getString("name");
				int population = rs.getInt("population");

				// Display values
				System.out.print("Code: " + code);
				System.out.print(", Name: " + name);
				System.out.println(", Population: " + population);
			}
			/* STEP 6: Clean-up environment
			 * Clean up the environment: Requires explicitly closing all database resources 
			 * versus relying on the JVM's garbage collection.
			 */
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end main
}// end AppJdbc