package context;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * DATABASE TABLES:
 * 
 * wiredInterfaces <device, interfaceName, mac, ip, mask ...>
 * wirelessInterfaces <device, mac, essid, mode, signal ...>
 * accessPoints <mac, essid, channel, mode>
 * pcAPs <device, mac(of AP), signal>
 * devices <device, timestamp>
 * 
 * --
 * mobiles <mbldevice>
 * 
 */
public class Database {

	private static Database instance = null;
	private Connection conn = null;
	//  Database credentials
	private String user;
	private String password;
	// JDBC driver name and database URL
	private String jdbc_driver;
	private String db_url;
	private String[][] tables = {
		{"wiredInterfaces", "device", "interfaceName", "interfaceMac", "interfaceIP", "interfaceMask", "networkAddress", "bcast", "defaultGetway", "maxTranfer", "currentTransfer", "consumedRate", "packetError"},
		{"wirelessInterfaces", "device", "interfaceName", "interfaceMac", "interfaceIP", "interfaceMask", "networkAddress", "bcast", "defaultGetway", "maxTranfer", "currentTransfer", "consumedRate", "packetError", "baseStationMAC", "essid", "channel", "mode", "transmitPower", "linkQuality", "signalLevel", "noisePower", "discardedPackets"},
		{"accessPoints", "aPMAC", "aPESSID", "aPChannel", "aPMode"},
		{"pcAPs", "device", "APMAC", "aPSignalLevel"},
		{"devices", "device", "timestamp"}
	};

	private Database() {
		System.out.println("Database has been just created!");

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

	public void createTables() {
		PreparedStatement prepstmt = null;
		String sql = "";

		try {
			for (int i = 0; i != tables.length; ++i) {
				/* prepare sql query */
				sql = "CREATE TABLE ? ( ? VARCHAR(255)";
				/* lenght - 2 because the first two questionmarks are ready */
				for (int j = 0; j != tables[i].length-2; ++j) {
					sql += ", ? VARCHAR(255)";
				}
				sql += ")";

				prepstmt = conn.prepareStatement(sql);

				/* set values in the sql query */
				for (int j = 0; j != tables[i].length; ++j) {
					prepstmt.setString(j + 1, tables[i][j]);
				}

				System.out.println("Sql query for insertion: " + sql);

				prepstmt.execute();
				prepstmt.close();
			}
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

	public void dropTable() {
		PreparedStatement prepstmt = null;
		String sql = "";
		ResultSet rs;

		try {
			sql = "DROP TABLE ?";
			prepstmt = conn.prepareStatement(sql);

			for (int i = 0; i != tables.length; i++) {
				prepstmt.setString(1, tables[i][0]);
				prepstmt.execute();
				rs = prepstmt.executeQuery();
				System.out.println("INFO: " + tables[i][0] + ": this table has been dropped.");

				//STEP 6: Clean-up environment
				rs.close();
			}
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
			sql = "INSERT INTO wiredInterfaces VALUES (?";
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
				prepstmt.setString(i + 2, field[i]);
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
	public void deleteADevice(String device) {
		System.out.println("pirate proponiti");
	}

//	UPDATE table_name
//	SET column1 = value, column2 = value2,
//	WHERE some_column = some_value
	public void updateADevice(String device, WiredInterface wi) {
		PreparedStatement prepstmt = null;
		String sql = "";

		try {
			/* prepare sql query */
			sql = "UPDATE wiredInterfaces SET interfaceName = ?, interfaceMAC = ?, interfaceIP = ?, interfaceMask = ?, networkAddress = ?, bcast = ?, defaultGetway = ?, maxTransfer = ?, currentTransfer = ?, consumedRate = ? WHERE device = ?";

			prepstmt = conn.prepareStatement(sql);

			prepstmt.setString(1, wi.get_InterfaceName());
			prepstmt.setString(2, wi.get_InterfaceMAC());
			prepstmt.setString(3, wi.get_InterfaceIP());
			prepstmt.setString(4, wi.get_InterfaceMask());
			prepstmt.setString(5, wi.get_NetworkAddress());
			prepstmt.setString(6, wi.get_Bcast());
			prepstmt.setString(7, wi.get_DefaultGetway());
			prepstmt.setString(8, wi.get_MaxTransfer());
			prepstmt.setString(9, wi.get_CurrentTransfer());
			prepstmt.setString(10, wi.get_ConsumedRate());
			prepstmt.setString(11, device);

			System.out.println("Sql query for insertion: " + sql);

			prepstmt.execute();
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

	public void updateADevice(String device, WirelessInterface wf) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			/* prepare sql query */
			sql = "UPDATE wirelessInterfaces SET interfaceName = ?, interfaceMAC = ?, interfaceIP = ?, interfaceMask = ?, networkAddress = ?, bcast = ?, defaultGetway = ?, maxTransfer = ?, currentTransfer = ?, consumedRate = ?, baseStationMAC = ?, ESSID = ?, channel = ?, mode = ?, transmitPower = ?, linkQuality = ?, signalLevel = ?, NoisePower = ?, DiscardedPackets = ? WHERE device = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, wf.get_InterfaceName());
			prepstmt.setString(2, wf.get_InterfaceMAC());
			prepstmt.setString(3, wf.get_InterfaceIP());
			prepstmt.setString(4, wf.get_InterfaceMask());
			prepstmt.setString(5, wf.get_NetworkAddress());
			prepstmt.setString(6, wf.get_Bcast());
			prepstmt.setString(7, wf.get_DefaultGetway());
			prepstmt.setString(8, wf.get_MaxTransfer());
			prepstmt.setString(9, wf.get_CurrentTransfer());
			prepstmt.setString(10, wf.get_ConsumedRate());
			prepstmt.setString(11, wf.get_BaseStationMAC());
			prepstmt.setString(12, wf.get_ESSID());
			prepstmt.setString(13, wf.get_Channel());
			prepstmt.setString(14, wf.get_Mode());
			prepstmt.setString(15, wf.get_TransmitPower());
			prepstmt.setString(16, wf.get_LinkQuality());
			prepstmt.setString(17, wf.get_SignalLevel());
			prepstmt.setString(18, wf.get_NoisePower());
			prepstmt.setString(19, wf.get_DiscardedPackets());
			prepstmt.setString(20, device);
			System.out.println("Sql query for insertion: " + sql);
			prepstmt.execute();
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

	public void updateADevice(String device, AccessPoint ac) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			/* prepare sql query */
			sql = "UPDATE pcAPs SET mac = ?, signal = ? WHERE device = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, ac.get_APMAC());
			prepstmt.setString(3, ac.get_APSignalLevel());
			System.out.println("Sql query for insertion: " + sql);
			prepstmt.execute();
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
//	INSERT INTO table_name
//	VALUES (value1, value2, value3,...)

	public void insertADevice(String device, WiredInterface wi) {
		PreparedStatement prepstmt = null;
		String sql = "";

		try {
			/* prepare sql query */
			sql = "INSERT INTO wiredInterfaces VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			prepstmt = conn.prepareStatement(sql);

			prepstmt.setString(1, device);
			prepstmt.setString(2, wi.get_InterfaceName());
			prepstmt.setString(3, wi.get_InterfaceMAC());
			prepstmt.setString(4, wi.get_InterfaceIP());
			prepstmt.setString(5, wi.get_InterfaceMask());
			prepstmt.setString(6, wi.get_NetworkAddress());
			prepstmt.setString(7, wi.get_Bcast());
			prepstmt.setString(8, wi.get_DefaultGetway());
			prepstmt.setString(9, wi.get_MaxTransfer());
			prepstmt.setString(10, wi.get_CurrentTransfer());
			prepstmt.setString(11, wi.get_ConsumedRate());

			System.out.println("Sql query for insertion: " + sql);

			prepstmt.execute();
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

	public void insertADevice(String device, WirelessInterface wf) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			/* prepare sql query */
			sql = "INSERT INTO wirelessInterfaces VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, wf.get_InterfaceName());
			prepstmt.setString(3, wf.get_InterfaceMAC());
			prepstmt.setString(4, wf.get_InterfaceIP());
			prepstmt.setString(5, wf.get_InterfaceMask());
			prepstmt.setString(6, wf.get_NetworkAddress());
			prepstmt.setString(7, wf.get_Bcast());
			prepstmt.setString(8, wf.get_DefaultGetway());
			prepstmt.setString(9, wf.get_MaxTransfer());
			prepstmt.setString(10, wf.get_CurrentTransfer());
			prepstmt.setString(12, wf.get_ConsumedRate());
			prepstmt.setString(13, wf.get_BaseStationMAC());
			prepstmt.setString(14, wf.get_ESSID());
			prepstmt.setString(15, wf.get_Channel());
			prepstmt.setString(16, wf.get_Mode());
			prepstmt.setString(17, wf.get_TransmitPower());
			prepstmt.setString(18, wf.get_LinkQuality());
			prepstmt.setString(19, wf.get_SignalLevel());
			prepstmt.setString(20, wf.get_NoisePower());
			prepstmt.setString(21, wf.get_DiscardedPackets());
			System.out.println("Sql query for insertion: " + sql);
			prepstmt.execute();
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

	public void insertADevice(String device, AccessPoint ac) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			/* prepare sql query */
			sql = "INSERT INTO pcAPs VALUES (?, ?, ?)";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, ac.get_APMAC());
			prepstmt.setString(3, ac.get_APSignalLevel());
			System.out.println("Sql query for insertion: " + sql);
			prepstmt.execute();
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

	public WiredInterface getInfoOfWired(String device, String ifName) {
		PreparedStatement prepstmt = null;
		ResultSet rs;
		WiredInterface w = new WiredInterface();

		try {
			String sql = "SELECT interfaceMAC, interfaceIP, interfaceMask, networkAddress, bcast, defaultGetway, maxTransfer, currentTransfer, consumedRate, packetError"
				+ "FROM wiredInterfaces"
				+ "WHERE device = ? AND interfaceName = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, ifName);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				w.set_InterfaceMAC(rs.getString("interfaceMAC"));
				w.set_InterfaceIP(rs.getNString("interfaceIP"));
				w.set_InterfaceMask(rs.getNString("interfaceMask"));
				w.set_NetworkAddress(rs.getNString("networkAddress"));
				w.set_Bcast(rs.getNString("bcast"));
				w.set_DefaultGetway(rs.getString("defaultGetway"));
				w.set_MaxTransfer(rs.getString("maxTransfer"));
				w.set_CurrentTransfer(rs.getString("currentTransfer"));
				w.set_ConsumedRate(rs.getString("consumedRate"));
				w.set_PacketError(rs.getString("packetError"));


			}
		} catch (SQLException ex) {
			Logger.getLogger(Database.class
				.getName()).log(Level.SEVERE, null, ex);
		}
		return w;
	}

	public WirelessInterface getInfoWireless(String device, String ifName) {
		PreparedStatement prepstmt = null;
		ResultSet rs;
		WirelessInterface wl = new WirelessInterface();

		try {

			String sql = "SELECT interfaceMAC, interfaceIP, interfaceMask, networkAddress, bcast, defaultGetway, maxTransfer, currentTransfer, consumedRate, packetError, baseStationMAC, essid, channel, mode, transmitPower, linkQuality, signalLevel, noisePower, discardedPackets"
				+ "FROM wiredInterfaces"
				+ "WHERE device = ? AND interfaceName = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, ifName);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				wl.set_InterfaceMAC(rs.getString("interfaceMAC"));
				wl.set_InterfaceIP(rs.getNString("interfaceIP"));
				wl.set_InterfaceMask(rs.getNString("interfaceMask"));
				wl.set_NetworkAddress(rs.getNString("networkAddress"));
				wl.set_Bcast(rs.getNString("bcast"));
				wl.set_DefaultGetway(rs.getString("defaultGetway"));
				wl.set_MaxTransfer(rs.getString("maxTransfer"));
				wl.set_CurrentTransfer(rs.getString("currentTransfer"));
				wl.set_ConsumedRate(rs.getString("consumedRate"));
				wl.set_PacketError(rs.getString("packetError"));
				wl.set_BaseStationMAC(rs.getString("baseStationMAC"));
				wl.set_ESSID(rs.getString("essid"));
				wl.set_Channel(rs.getString("channel"));
				wl.set_Mode(rs.getString("mode"));
				wl.set_TransmitPower(rs.getString("transmitPower"));
				wl.set_LinkQuality(rs.getString("linkQuality"));
				wl.set_SignalLevel(rs.getString("linkQuality"));
				wl.set_NoisePower(rs.getString("noisePower"));
				wl.set_DiscardedPackets(rs.getString("discardedPackets"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(Database.class
				.getName()).log(Level.SEVERE, null, ex);
		}
		return wl;

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

	public void getInfoOf(String device, String interf) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
