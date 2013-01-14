package context;

import grafiko.GUI;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

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
		{"wiredInterfaces", "device", "interfaceName", "interfaceMac", "interfaceIP", "interfaceMask", "networkAddress", "bcast", "defaultGetway", "maxTransfer", "currentTransfer", "consumedRate", "packetError"},
		{"wirelessInterfaces", "device", "interfaceName", "interfaceMac", "interfaceIP", "interfaceMask", "networkAddress", "bcast", "defaultGetway", "maxTransfer", "currentTransfer", "consumedRate", "packetError", "baseStationMAC", "essid", "channel", "mode", "transmitPower", "linkQuality", "signalLevel", "noisePower", "discardedPackets"},
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
		String type1 = "VARCHAR(255)";
		String space = " ";

		try {
			for (int i = 0; i != tables.length; ++i) {
				/* prepare sql query */
				sql = "CREATE TABLE";  // ? ( ? VARCHAR(255)";
				sql += space + tables[i][0] + space;
				sql += "(" + space + tables[i][1] + space + type1;

				for (int j = 2; j != tables[i].length; ++j) {
					sql += "," + space + tables[i][j] + space + type1 + space;
				}

				sql += ")";

				prepstmt = conn.prepareStatement(sql);

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

	public void dropTables() {
		PreparedStatement prepstmt = null;
		String sql = "";
		String space = " ";

		try {
			for (int i = 0; i != tables.length; i++) {
				sql = "DROP TABLE";
				sql += space + tables[i][0];
				prepstmt = conn.prepareStatement(sql);
				prepstmt.execute();
				System.out.println("INFO: " + tables[i][0] + ": this table has been dropped.");
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

	public void deleteADevice(String dev) {
		Statement stmt = null;
		PreparedStatement prepstmt = null;
		PreparedStatement prepstmt1 = null;
		String sql = "";
		String query = "";
		ResultSet rs, rs1;
		String space = " ";

		try {
			sql = "SELECT APMAC FROM pcAPs WHERE device = " + '"' + dev + '"';
			prepstmt1 = conn.prepareStatement(sql);
			prepstmt1.execute();
			rs = prepstmt1.executeQuery();
			while (rs.next()) {
				sql = "SELECT COUNT(APMAC) FROM pcAPs WHERE APMAC = ?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setString(1, rs.getString("APMAC"));
				prepstmt.execute();
				rs1 = prepstmt.executeQuery();
				prepstmt.close();
				if (rs1.next()) {
					if (rs1.getInt("COUNT(APMAC)") == 1) {
						prepstmt1 = null;
						sql = "";
						try {
							sql = "DELETE FROM accessPoints WHERE aPMAC = ?";
							prepstmt1 = conn.prepareStatement(sql);
							prepstmt1.setString(1, rs.getString("APMAC"));
							prepstmt1.execute();
							prepstmt1.close();
						} catch (SQLException ex) {
							Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			}
			for (int i = 0; i != tables.length; i++) {
				if (tables[i][0].equals("wiredInterfaces") || tables[i][0].equals("wirelessInterfaces") || tables[i][0].equals("pcAPs") || tables[i][0].equals("devices")) {
					query = "";
					prepstmt = null;
					query = "DELETE FROM";
					query += space + tables[i][0] + space;
					query += "WHERE device = ";
					query += space + '"' + dev + '"';
					System.out.println("auto paw na ektelesw...: " + query);
					prepstmt = conn.prepareStatement(query);
					prepstmt.execute();
					prepstmt.close();
				}
			}
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public void deleteDeviceWired(String device, String ifname) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			sql = "DELETE FROM wiredInterfaces WHERE device = ? AND interfaceName = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, ifname);
			prepstmt.execute();
			prepstmt.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void deleteDeviceWireless(String device, String ifname) {
		PreparedStatement prepstmt = null;
		String sql = "";
		try {
			sql = "DELETE FROM wirelessInterfaces WHERE device = ? AND interfaceName = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, ifname);
			prepstmt.execute();
			prepstmt.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void deleteDeviceAP(String device, String mac) {
		PreparedStatement prepstmt = null;
		String sql = "";
		ResultSet rs;
		int count;
		try {
			sql = "SELECT COUNT(*) FROM pcAPs WHERE APMAC = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, mac);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("COUNT(*)") == 1) {
					PreparedStatement prepstmt1 = null;
					String sql1 = "";
					try {
						sql1 = "DELETE FROM accessPoints WHERE aPMAC = ?";
						prepstmt1 = conn.prepareStatement(sql1);
						prepstmt1.setString(1, mac);
						prepstmt1.execute();
						prepstmt1.close();
					} catch (SQLException ex) {
						Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			prepstmt.close();
			rs.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}

		prepstmt = null;
		sql = "";
		try {
			sql = "DELETE FROM pcAPs WHERE device = ? AND APMAC = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, device);
			prepstmt.setString(2, mac);
			prepstmt.execute();
			prepstmt.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

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

	public void insertADevice(String device, WiredInterface wi) {
		PreparedStatement prepstmt = null;
		String sql = "";

		try {
			/* prepare sql query */
			sql = "INSERT INTO wiredInterfaces VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
			prepstmt.setString(12, wi.get_PacketError());

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
			prepstmt.setString(11, wf.get_ConsumedRate());
			prepstmt.setString(12, wf.get_PacketError());
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
		String sql = "", sql1 = "";
		ResultSet rs;
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

			prepstmt = null;
			sql = "";
			sql = "SELECT aPMAC "
				+ "FROM accessPoints "
				+ "WHERE aPMAC = ?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, ac.get_APMAC());
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			if (!rs.next()) {
				prepstmt = null;
				sql1 = "INSERT INTO accessPoints VALUES (?, ?, ?, ?)";
				prepstmt = conn.prepareStatement(sql1);
				prepstmt.setString(1, ac.get_APMAC());
				prepstmt.setString(2, ac.get_APESSID());
				prepstmt.setString(3, ac.get_APChannel());
				prepstmt.setString(4, ac.get_APMode());
				prepstmt.execute();
				prepstmt.close();
			} else {
				prepstmt = null;
				sql1 = "UPDATE accessPoints SET aESSID = ?, aPChannel = ?, aPMode = ? WHERE device = ?";
				prepstmt = conn.prepareStatement(sql1);
				prepstmt.setString(1, ac.get_APESSID());
				prepstmt.setString(2, ac.get_APChannel());
				prepstmt.setString(3, ac.get_APMode());
				prepstmt.setString(4, ac.get_APMAC());
				prepstmt.execute();
				prepstmt.close();
			}
			rs.close();
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
				+ " FROM wiredInterfaces "
				+ " WHERE device = " + '"' + device + '"' + " AND interfaceName = " + '"' + ifName + '"';
			prepstmt = conn.prepareStatement(sql);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			if (rs.next()) {
				w.set_InterfaceMAC(rs.getString("interfaceMAC"));
				w.set_InterfaceIP(rs.getString("interfaceIP"));
				w.set_InterfaceMask(rs.getString("interfaceMask"));
				w.set_NetworkAddress(rs.getString("networkAddress"));
				w.set_Bcast(rs.getString("bcast"));
				w.set_DefaultGetway(rs.getString("defaultGetway"));
				w.set_MaxTransfer(rs.getString("maxTransfer"));
				w.set_CurrentTransfer(rs.getString("currentTransfer"));
				w.set_ConsumedRate(rs.getString("consumedRate"));
				w.set_PacketError(rs.getString("packetError"));
			}
			rs.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class
				.getName()).log(Level.SEVERE, null, ex);
		}
		return w;
	}

	public WirelessInterface getInfoOfWireless(String device, String ifName) {
		PreparedStatement prepstmt = null;
		ResultSet rs;
		WirelessInterface wl = new WirelessInterface();

		try {

			String sql = "SELECT interfaceMAC, interfaceIP, interfaceMask, networkAddress, bcast, defaultGetway, maxTransfer, currentTransfer, consumedRate, packetError, baseStationMAC, essid, channel, mode, transmitPower, linkQuality, signalLevel, noisePower, discardedPackets "
				+ " FROM wirelessInterfaces "
				+ " WHERE device = " + '"' + device + '"' + " AND interfaceName = " + '"' + ifName + '"';
			prepstmt = conn.prepareStatement(sql);
			prepstmt.execute();
			rs = prepstmt.executeQuery();
			if (rs.next()) {
				wl.set_InterfaceMAC(rs.getString("interfaceMAC"));
				wl.set_InterfaceIP(rs.getString("interfaceIP"));
				wl.set_InterfaceMask(rs.getString("interfaceMask"));
				wl.set_NetworkAddress(rs.getString("networkAddress"));
				wl.set_Bcast(rs.getString("bcast"));
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
			rs.close();
		} catch (SQLException ex) {
			Logger.getLogger(Database.class
				.getName()).log(Level.SEVERE, null, ex);
		}
		return wl;
	}

	public AccessPoint getInfoOfAPs(String device, String mac) {
		PreparedStatement prepstmt = null;
		PreparedStatement prepstmt2 = null;
		ResultSet rs, rs2;
		AccessPoint ap = new AccessPoint();

		try {
			String sql = "SELECT aPMAC, aPESSID, aPChannel, aPMode "
				+ "FROM accessPoints "
				+ "WHERE aPMAC IN (SELECT APMAC FROM pcAPs WHERE device = " + '"' + device + '"' + " AND APMAC = " + '"' + mac + '"' + " )";

			String query = "SELECT aPSignalLevel "
				+ "FROM pcAPs "
				+ "WHERE device = " + '"' + device + '"' + " AND APMAC = " + '"' + mac + '"';

			System.out.println(sql + " " + query);

			prepstmt = conn.prepareStatement(sql);
			prepstmt.execute();
			rs = prepstmt.executeQuery();

			if (rs.next()) {
				ap.set_APMAC(rs.getString("aPMAC"));
				ap.set_APESSID(rs.getString("aPESSID"));
				ap.set_APChannel(rs.getString("aPChannel"));
				ap.set_APMode(rs.getString("aPMode"));
			}
			rs.close();

			prepstmt2 = conn.prepareStatement(query);
			prepstmt2.execute();
			rs2 = prepstmt2.executeQuery();


			if (rs2.next()) {
				ap.set_APSignalLevel(rs2.getString("aPSignalLevel"));
			}
			rs2.close();

		} catch (SQLException ex) {
			Logger.getLogger(Database.class
				.getName()).log(Level.SEVERE, null, ex);
		}
		return ap;
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
