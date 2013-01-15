package adder;

import context.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* 
 * singleton cache memory - maybe only -memory- (without cache)
 */
public class CacheMemory {

	private static CacheMemory instance = null;
	private Database db = Database.getInstance();

	public HashMap<String, ArrayList<String[]>> getWiredMap() {
		return wiredMap;
	}

	/* the below info is either in database either in cache memory */
	private HashMap<String, ArrayList<String[]>> wiredMap;
	private HashMap<String, ArrayList<String>> wiredMap2;
	private HashMap<String, ArrayList<String[]>> wirelessMap;
	private HashMap<String, ArrayList<String[]>> apMap;
	private HashMap<String, Long> timeMap;
	private ArrayList<String> devices;

	/* hash maps with interfaces that are going to be deleted */
	private ArrayList<String[]> delWIf;
	private ArrayList<String[]> delWirIf;
	private ArrayList<String[]> delAp;

	/* the below info exists only in cache memory */
	private HashMap<String, MonitorData> newData;
	private HashMap<String, MonitorData> cachedData;

	public HashMap<String, ArrayList<String[]>> getWirelessMap() {
		return wirelessMap;
	}

	public HashMap<String, ArrayList<String[]>> getApMap() {
		return apMap;
	}

	public CacheMemory() {
		wiredMap = new HashMap<String, ArrayList<String[]>>();
		wiredMap2 = new HashMap<String, ArrayList<String>>();
		wirelessMap = new HashMap<String, ArrayList<String[]>>();
		apMap = new HashMap<String, ArrayList<String[]>>();
		timeMap = new HashMap<String, Long>();
		devices = new ArrayList<String>();

		delAp = new ArrayList<String[]>();
		delWIf = new ArrayList<String[]>();
		delWirIf = new ArrayList<String[]>();

		newData = new HashMap<String, MonitorData>();

		System.out.println("Cache Memory has been just created!-----------------------------");
//
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				GUI gui = new GUI();
//				gui.addMenu();
//				/* Add tabs*/
//				gui.addTabs();
//				/* Make window visible*/
//				gui.setVisible(true);
//			}
//		});

	}

	public static synchronized CacheMemory getInstance() {
		if (instance == null) {
			instance = new CacheMemory();
		}
		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public synchronized void update(String device, MonitorData md) {
		ArrayList<String[]> param1 = new ArrayList<String[]>();
		ArrayList<String[]> param2 = new ArrayList<String[]>();
		ArrayList<String[]> param3 = new ArrayList<String[]>();
		ArrayList<String[]> param4 = new ArrayList<String[]>();

		param1 = param2 = param3 = param4 = null;
		/* check if this device's data exist in overiew (~ db or cache memory) */
		if (timeMap.containsKey(device)) {
			/* if device exists in overview then pass as a parameter
			 * the previous about the interfaces of it. 
			 * the previous info that we αρare interested in, is the database insertion byte - String[3]ε
			 */
			param1 = wiredMap.get(device);
			param2 = wirelessMap.get(device);
			param3 = apMap.get(device);
			System.out.print(device + " is found!");
			if (dataIsUpdated(device, md)) {
				System.out.println(" and updated!");
				timeMap.put(device, System.currentTimeMillis());
				return;
			}
//			checkForDeletedIf(md);
		}
		System.out.println(device + " is NOT found and NOT updated!");
		System.out.println("Memory: memory is going to be updated...");
		/* otherwise update the οωεoverview */
		wiredMap.put(device, md.getInterfNameList(param1));
		wiredMap2.put(device, md.getInterfNameList2());
		wirelessMap.put(device, md.getWirInterfNameList(param2));
		apMap.put(device, md.getAPNameList(param4));
		timeMap.put(device, System.currentTimeMillis());
		/* TODO: check list with devices */

		/* hold the latest info in memory */
		newData.put(device, md);

	}

	public boolean hasLatestInfoOf(String device) {
		return newData.containsKey("device");
	}

	public synchronized WiredInterface getInfoOfWired(String device, String interf) {
		return db.getInfoOfWired(device, interf);
	}

	public synchronized WirelessInterface getInfoOfWireless(String device, String interf) {
		return db.getInfoOfWireless(device, interf);
	}

	/* this is a fast check */
	private boolean dataIsUpdated(String device, MonitorData md) {
		ArrayList<String[]> s = new ArrayList<String[]>();
		String[] str = new String[2];
		int mdSize = 0;
		int overviewSize = 0;
		int found = 0;
		boolean updated = true;

//	/* --- checking for wired interfaces --- */
		/* get the wired interface names for cache memory overvies */
		s = wiredMap.get(device);

		/* get the number of wired interfaces in monitor data */
		mdSize = md.getListA().size();
		/* get the number of wired interfaces which are cached */
		overviewSize = s.size();

		System.out.println("mdSize: " + mdSize + " overviewSize: " + overviewSize);
		/* if these numbers are different, then for sure the data has been changed */
		if (overviewSize != mdSize) {
			System.out.println("false1");
			updated = false;
		}
		found = 0;
		System.out.println("I am going to print everything...");
		int i, j;
		/* for every interface in overview */
		for (j = 0; j != overviewSize; ++j) {
			/* check every interface in monitor data */
			for (i = 0; i != mdSize; ++i) {
				str[0] = md.getListA().get(i).get_InterfaceName();
				str[1] = Integer.toString(md.getListA().get(i).hashCode());

				System.out.println("s string: " + s.get(j)[0]);
				System.out.print(" md string: " + str[0]);
				System.out.println("s hashcode: " + s.get(j)[1]);
				System.out.print(" md hashcode: " + str[1]);
				/* if they have the same name and hash code */
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					found++;
					break;
				}
			}
			/* if didn't execute break statement
			 * if an interface in overview wasn't found in monitor data
			 * this interface has to be deleted
			 */
			if (i == mdSize) {
				String[] del = new String[2];
				del[0] = device;
				del[1] = s.get(j)[0];
				delWIf.add(del);
			}
		}
		/* even if overviewSize == mdSize, the unchanged interfaces may differ */
		if (found != mdSize) {
			System.out.println("false2");
			updated = false;
		}


//	/* --- checking for the wireless interfaces --- */

		/* get the wireless interface names for cache memory overvies */
		s = wirelessMap.get(device);

		/* get the number of wireless interfaces in monitor data */
		mdSize = md.getListB().size();
		/* get the number of wireless interfaces which are cached */
		overviewSize = s.size();

		/* if these numbers are different, then for sure the data has been changed */
		if (overviewSize != mdSize) {
			System.out.println("false3");
			updated = false;
		}
		found = 0;
		/* for every interface in overview */
		for (j = 0; j != overviewSize; ++j) {
			/* check every interface in monitor data */
			for (i = 0; i != mdSize; ++i) {
				str[0] = md.getListB().get(i).get_InterfaceName();
				str[1] = Integer.toString(md.getListB().get(i).hashCode());
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					found++;
					break;
				}
			}
			/* if didn't execute break statement
			 * if an interface in overview wasn't found in monitor data
			 * this interface has to be deleted
			 */
			if (i == mdSize) {
				String[] del = new String[2];
				del[0] = device;
				del[1] = s.get(j)[0];
				delWirIf.add(del);
			}
		}
		/* even if overviewSize == mdSize, the unchanged interfaces may differ */
		if (found != mdSize) {
			System.out.println("false4");
			updated = false;
		}

		
//	/* --- checking for access points --- */
		/* get the access point names for cache memory overvies */
		s = apMap.get(device);

		/* get the number of access points in monitor data */
		mdSize = md.getListC().size();
		/* get the number of access points which are cached */
		overviewSize = s.size();

		System.out.println("mdSize: " + mdSize + " overviewSize: " + overviewSize);
		/* if these numbers are different, then for sure the data has been changed */
		if (overviewSize != mdSize) {
			System.out.println("false1");
			updated = false;
		}
		found = 0;
		System.out.println("I am going to print everything...");
		/* for every interface in overview */
		for (j = 0; j != overviewSize; ++j) {
			/* check every access point in monitor data */
			for (i = 0; i != mdSize; ++i) {
				str[0] = md.getListC().get(i).get_APMAC();
				str[1] = Integer.toString(md.getListC().get(i).hashCode());

				/* if they have the same MAC and hash code */
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					found++;
					break;
				}
			}
			/* if didn't execute break statement
			 * if an ap in overview wasn't found in monitor data
			 * this ap has to be deleted
			 */
			if (i == mdSize) {
				String[] del = new String[2];
				del[0] = device;
				del[1] = s.get(j)[0];
				delAp.add(del);
			}
		}
		/* even if overviewSize == mdSize, the unchanged access points may differ */
		if (found != mdSize) {
			System.out.println("false2");
			updated = false;
		}

		return updated;
	}

	/* check the hashCode for EVERY interface */
	public synchronized void updateDb() {
		ArrayList<String[]> overview;

		/* firstly, delete from database every interface that does not exist anymore */
		deleteInterfaces();

		/* for every monitor data that has reached the memory */
		for (Map.Entry<String, MonitorData> entry : newData.entrySet()) {
			MonitorData md = entry.getValue();
			String device = entry.getKey();
			int sizeA, sizeB, sizeC;
			int sizeMapA, sizeMapB, sizeMapC;

			System.out.println("I am going to update the database for device: " + device);

//		--- wired interfaces
			sizeA = md.getListA().size();
			/* get a list of the interfaces of the device - overview */
			overview = wiredMap.get(device);
			sizeMapA = overview.size();

			System.out.println("s.size: " + overview.size() + " md.size: " + md.getListA().size());
			/* for every wired interface in monitor data */
			int i, j;
			for (i = 0; i != sizeA; ++i) {
				/* check it with the overview */
				for (j = 0; j != sizeMapA; ++j) {
					/* for the same interface */
					if (md.getListA().get(i).get_InterfaceName().equals(overview.get(j)[0])) {
						/* check if the interface of the overview, is already in databe */
						if (overview.get(j)[2].equals("1")) {
							System.out.println(overview.get(j)[0] + " is already in database.");
							/* if they have the same hash code */
							if (overview.get(j)[1].equals(Integer.toString(md.getListA().get(i).hashCode()))) {
								// do nothing
							} else {
								/* else update */
								db.updateADevice(device, md.getListA().get(i));
							}
						} /* otherwise */ else {
							/* insert in database */
							db.insertADevice(device, md.getListA().get(i));
						}
						break;
					}
				}
				/* if "break;" was executed
				 * if there is an interface that there is not in overview
				 * insert it!
				 */
				if (j == sizeMapA) {
					System.out.println("kanonika den prepei na mpei edw pote...");
				}
			}


//		--- wireless interfaces
			sizeB = md.getListB().size();
			/* get a list of the interfaces of the device - overview */
			overview = wirelessMap.get(device);
			sizeMapB = overview.size();

			System.out.println("s.size: " + overview.size() + " md.size: " + md.getListB().size());
			/* for every wired interface in monitor data */
			for (i = 0; i != sizeB; ++i) {
				/* check it with the overview */
				for (j = 0; j != sizeMapB; ++j) {
					/* for the same interface */
					if (md.getListB().get(i).get_InterfaceName().equals(overview.get(j)[0])) {
						/* check if the interface of the overview, is already in databe */
						if (overview.get(j)[2].equals("1")) {
							/* if they have the same hash code */
							if (overview.get(j)[1].equals(Integer.toString(md.getListB().get(i).hashCode()))) {
								// do nothing
							} else {
								/* else update */
								db.updateADevice(device, md.getListB().get(i));
							}
						} /* otherwise */ else {
							/* insert in database */
							db.insertADevice(device, md.getListB().get(i));
						}
						break;
					}
				}
				/* if "break;" was executed
				 * if there is an interface that there is not in overview
				 * insert it!
				 */
				if (j == sizeMapB) {
					System.out.println("kanonika den prepei na mpei edw pote...");
				}
			}


//		--- access points 
			sizeC = md.getListC().size();
			/* get a list of the interfaces of the device - overview */
			overview = apMap.get(device);
			sizeMapC = overview.size();

			System.out.println("s.size: " + overview.size() + " md.size: " + md.getListC().size());
			/* for every ap in monitor data */
			for (i = 0; i != sizeC; ++i) {
				/* check it with the overview */
				for (j = 0; j != sizeMapC; ++j) {
					/* for the same interface */
					if (md.getListC().get(i).get_APMAC().equals(overview.get(j)[0])) {
						/* check if the ap of the overview, is already in databe */
						if (overview.get(j)[2].equals("1")) {
							/* if they have the same hash code */
							if (overview.get(j)[1].equals(Integer.toString(md.getListC().get(i).hashCode()))) {
								// do nothing
							} else {
								/* else update */
								db.updateADevice(device, md.getListC().get(i));
							}
						} /* otherwise */ else {
							/* insert in database */
							db.insertADevice(device, md.getListC().get(i));
						}
						break;
					}
				}
				/* if "break;" was executed
				 * if there is an interface that there is not in overview
				 * insert it!
				 */
				if (j == sizeMapC) {
					System.out.println("kanonika den prepei na mpei edw pote...");
				}
			}

			
		}
		setUpdatedDb();
	}

	/* set the str[2] = 1 in the overview
	 * this mean that the interface str[0] with hash code str[1] is
	 * in database
	 */
	private void setUpdatedDb() {
		for (Map.Entry<String, ArrayList<String[]>> entry : wiredMap.entrySet()) {
			String device = entry.getKey();
			ArrayList<String[]> list = entry.getValue();
			ArrayList<String[]> listNew = new ArrayList<String[]>();

			for (int i = 0; i != list.size(); ++i) {
				String[] newStr = new String[3];
				newStr[0] = list.get(i)[0];
				newStr[1] = list.get(i)[1];
				newStr[2] = Integer.toString(1);
				listNew.add(newStr);
			}
			wiredMap.put(device, listNew);
		}

		for (Map.Entry<String, ArrayList<String[]>> entry : wirelessMap.entrySet()) {
			String device = entry.getKey();
			ArrayList<String[]> list = entry.getValue();
			ArrayList<String[]> listNew = new ArrayList<String[]>();

			for (int i = 0; i != list.size(); ++i) {
				String[] newStr = new String[3];
				newStr[0] = list.get(i)[0];
				newStr[1] = list.get(i)[1];
				newStr[2] = Integer.toString(1);
				listNew.add(newStr);
			}
			wirelessMap.put(device, listNew);
		}

		
		for (Map.Entry<String, ArrayList<String[]>> entry : apMap.entrySet()) {
			String device = entry.getKey();
			ArrayList<String[]> list = entry.getValue();
			ArrayList<String[]> listNew = new ArrayList<String[]>();

			for (int i = 0; i != list.size(); ++i) {
				String[] newStr = new String[4];
				newStr[0] = list.get(i)[0];
				newStr[1] = list.get(i)[1];
				newStr[2] = Integer.toString(1);
				newStr[3] = list.get(i)[3];
				listNew.add(newStr);
			}
			apMap.put(device, listNew);
		}
	}

	private void deleteInterfaces() {

		/* delete wired interfaces */
		for (int i = 0; i != delWIf.size(); ++i) {
			db.deleteDeviceWired(delWIf.get(i)[0], delWIf.get(i)[1]);
		}

		/* delete wireless interfaces */
		for (int i = 0; i != delWirIf.size(); ++i) {
			db.deleteDeviceWireless(delWirIf.get(i)[0], delWirIf.get(i)[1]);
		}

		/* delete access points */
		for (int i = 0; i != delAp.size(); ++i) {
			db.deleteDeviceWireless(delAp.get(i)[0], delAp.get(i)[1]);
		}

	}

	public synchronized void printMemory() {
		Iterator it;

//		System.out.println("--- devices begin ---");
//		it = devices.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next());
//		}
//		System.out.println("--- devices end ---");

		System.out.println("--- wired map begin ---");
		it = wiredMap.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- wired map end ---");

		System.out.println("");

		System.out.println("--- wired map2 begin ---");
		it = wiredMap2.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- wired map2 end ---");

		System.out.println("");

		System.out.println("--- wireless map begin ---");
		it = wirelessMap.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- wireless map end ---");

		System.out.println("");

		System.out.println("--- ap map begin ---");
		it = apMap.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- ap map end ---");

		System.out.println("");

		System.out.println("--- time map begin ---");
		it = timeMap.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- time map end ---");

		System.out.println("");

		System.out.println("--- data begin---");
		it = newData.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- data end ---");
	}

}
