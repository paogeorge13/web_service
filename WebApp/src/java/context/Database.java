package context;

import java.sql.*;

public class Database {

	private static Database instance = null;
	private Connection conn = null;
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://pantheon.di.uoa.gr/sdi0900141";
	//  Database credentials
	private static final String USER = "sdi0900141";
	private static final String PASS = "dw9UkgaA";

	private Database() {
		System.out.println("to elava - ok!");

		/*
		DataProp = new PropertyFileData("DB.properties", false);
		this.driver = DataProp.get_propertyDriver();
		this.URL = DataProp.get_propertyURL();
		this.username = DataProp.get_propertyUsername();
		this.password = DataProp.get_propertyPassword();
		*/

		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static synchronized Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	//TODO this ... 
	public void dropTable(String tableName) {

	}

	public void createTable(String tableName, String columns) {
		PreparedStatement prepstmt = null;
		String sql = "";
		ResultSet rs;

		try {
			prepstmt = conn.prepareStatement("SELECT id, name FROM devices WHERE id = ?");
			System.out.println("Database.java idF: " );
			
			prepstmt.setInt(1, 2);
			prepstmt.execute();
			rs = prepstmt.executeQuery();

			//STEP 5: Extract data from result set
			while (rs.next()) {
				//Retrieve by column name
				int id = rs.getInt("id");
				String first = rs.getString("name");

				//Display values
				System.out.print("ID: " + id);
				System.out.println(", Name: " + first);
			}
			//STEP 6: Clean-up environment
			rs.close();
			//	stmt.close();
			prepstmt.close();
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			System.out.println("finally here...");
		}
		System.out.println("Goodbye!");
	}

	// TODO this...
	public void deletaADevice(String device) {


	}

	public void selectADevice(int idF) {
		Statement stmt = null;
		PreparedStatement prepstmt = null;
		String sql;
		ResultSet rs;

		try {
			prepstmt = conn.prepareStatement("SELECT id, name FROM devices WHERE id = ?");
			System.out.println("Database.java idF: " + idF);
			prepstmt.setInt(1, idF);
			prepstmt.execute();
			rs = prepstmt.executeQuery();

			/*
			 //STEP 4: Execute a query
			 System.out.println("Creating statement with id 13...");
			 stmt = conn.createStatement();
			 sql = "SELECT id, name FROM devices WHERE id=13";
			 rs = stmt.executeQuery(sql);
			 */

			//STEP 5: Extract data from result set
			while (rs.next()) {
				//Retrieve by column name
				int id = rs.getInt("id");
				String first = rs.getString("name");

				//Display values
				System.out.print("ID: " + id);
				System.out.println(", Name: " + first);
			}
			//STEP 6: Clean-up environment
			rs.close();
			//	stmt.close();
			prepstmt.close();
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			System.out.println("finally here...");
		}
		System.out.println("Goodbye!");
	} //end main

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
