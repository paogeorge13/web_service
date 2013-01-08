package context;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/*
 * DATABASE TABLES:
 * 
 * wiredInterfaces <device, interfaceName, mac, ip, mask ...>
 * wirelessInterfaces <device, mac, essid, mode, signal ...>
 * accessPoints <mac, essid, channel, mode, signal>
 * pcAPs <device, mac(of AP)>
 * devices <device>
 * 
 * --
 * mobiles <mbldevice>
 * 
 */

public class Database {

	private static Database instance = null;
	private Connection conn = null;

	// JDBC driver name and database URL
	/*private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://pantheon.di.uoa.gr/sdi0900141";
	//  Database credentials
	private static final String USER = "sdi0900141";
	private static final String PASS = "dw9UkgaA"; */

	private String user;
	private String password;
	private String jdbc_driver;
	private String db_url;

	private Database() {
		System.out.println(" ok! ");

		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.user = prop.getProperty("username");
		this.password = prop.getProperty("password");
		this.jdbc_driver = prop.getProperty("jdbc_driver");
		this.db_url = prop.getProperty("URL");

		try {
			//STEP 2: Register JDBC driver
			Class.forName(jdbc_driver);

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(db_url, user, password);
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

	public void dropTable(String tableName) {
		PreparedStatement prepstmt = null;
		String sql = "";
		ResultSet rs;

		try {
			sql = "DROP TABLE ?";
			prepstmt = conn.prepareStatement(sql);

			prepstmt.setString(1, tableName);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			System.out.println("INFO: " + tableName + ": this table has been dropped.");

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

	public void createTable(String tableName, String columns) {
		PreparedStatement prepstmt = null;
		String sql = "";
		ResultSet rs;
		String[] field;

		try {
			/* prepare sql query */
			sql = "INSERT INTO ? VALUES (?";
			field = columns.split(" ");
			/* lenght - 1: because the first questionmark is already printed */
			for (int i = 0; i != (field.length - 1); ++i) {
				sql += ", ?";
			}
			sql += ")";

			prepstmt = conn.prepareStatement(sql);

			System.out.println("Sql query for insertion: " + sql);

			prepstmt.setString(1, tableName);
			for (int i = 0; i != field.length; ++i) {
				prepstmt.setString(i + 1, field[i]);
			}
			prepstmt.execute();
			//stmt.close();
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
			//stmt.close();
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
