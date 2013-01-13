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

	/* the below info is either in database either in cache memory */
	private HashMap<String, ArrayList<String[]>> wiredMap;
	private HashMap<String, ArrayList<String>> wiredMap2;
	private HashMap<String, ArrayList<String[]>> wirelessMap;
	private HashMap<String, ArrayList<AccessPoint>> apMap;
	private HashMap<String, Long> timeMap;
	private ArrayList<String> devices;

	/* hash maps with interfaces that are going to be deleted */
	private ArrayList<String[]> delWIf;
	private ArrayList<String[]> delWirIf;
	private ArrayList<String[]> delAp;

	/* the below info exists only in cache memory */
	private HashMap<String, MonitorData> newData;
	private HashMap<String, MonitorData> cachedData;

	public CacheMemory() {
		wiredMap = new HashMap<String, ArrayList<String[]>>();
		wiredMap2 = new HashMap<String, ArrayList<String>>();
		wirelessMap = new HashMap<String, ArrayList<String[]>>();
		apMap = new HashMap<String, ArrayList<AccessPoint>>();
		timeMap = new HashMap<String, Long>();
		devices = new ArrayList<String>();

		delAp = new ArrayList<String[]>();
		delWIf = new ArrayList<String[]>();
		delWirIf = new ArrayList<String[]>();

		newData = new HashMap<String, MonitorData>();

		System.out.println("Cache Memory has been just created!");
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
		apMap.put(device, md.getListC());
		timeMap.put(device, System.currentTimeMillis());
		/* TODO: check list with devices */

		/* hold the latest info in memory */
		newData.put(device, md);

//		System.out.println("apo eksw twra... " + wiredMap.get(device).size());
//		if (timeMap.containsKey(device) && dataIsUpdated(device, md)) {
//			System.out.println("Memory: memory contains all the info of: " + device);
//			timeMap.put(device, System.currentTimeMillis());


	}

	public synchronized boolean hasLatestInfoOf(String device) {
		return newData.containsKey("device");
	}

	public MonitorData getInfoOf(String device, String interf) {
		return null;
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
		for (j = 0; j != overviewSize; ++j) {
			for (i = 0; i != mdSize; ++i) {
				str[0] = md.getListA().get(i).get_InterfaceName();
				str[1] = Integer.toString(md.getListA().get(i).hashCode());

				System.out.println("s string: " + s.get(j)[0]);
				System.out.print(" md string: " + str[0]);
				System.out.println("s hashcode: " + s.get(j)[1]);
				System.out.print(" md hashcode: " + str[1]);
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					found++;
					break;
				}
			}
			/* if didn't execute break statement
			 * if an interface in overview, didn't found in monitor data
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
		System.out.println("=");


//	/* --- checking for the wireless interfaces --- */

		/* get the wired interface names for cache memory overvies */
		s = wirelessMap.get(device);

		/* get the number of wired interfaces in monitor data */
		mdSize = md.getListB().size();
		/* get the number of wired interfaces which are cached */
		overviewSize = s.size();

		/* if these numbers are different, then for sure the data has been changed */
		if (overviewSize != mdSize) {
			System.out.println("false3");
			updated = false;
		}
		found = 0;
		for (i = 0; i != mdSize; ++i) {
			for (j = 0; j != overviewSize; ++j) {
				str[0] = md.getListB().get(i).get_InterfaceName();
				str[1] = Integer.toString(md.getListB().get(i).hashCode());
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					found++;
					break;
				}
			}
		}
		/* even if overviewSize == mdSize, the unchanged interfaces may differ */
		if (found != mdSize) {
			System.out.println("false4");
			updated = false;
		}
		System.out.println("=");

		return updated;
	}
	//TODO: TOSEE!

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
		}

		setUpdatedDb();
	}

	private void setUpdatedDb() {
//		throw new UnsupportedOperationException("Not yet implemented");
	}

	private void deleteInterfaces() {

		/* delete wired interfaces */
		for (int i = 0; i != delWIf.size(); ++i ) {
//			db.deleteWiredIf(delWIf.get(i)[0], delWIf.get(i)[1]);
		}

		/* delete wireless interfaces */
		for (int i = 0; i != delWirIf.size(); ++i ) {
//			db.deleteWirelessIf(delWirIf.get(i)[0], delWirIf.get(i)[1]);
		}

	}
}
