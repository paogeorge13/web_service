package adder;

import context.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

	/* the below info exists only in cache memory */
	private HashMap<String, MonitorData> newData;
	private HashMap<String, MonitorData> cachedData;

	public CacheMemory() {
		System.out.println("Cache Memory has been just created!");
		wiredMap = new HashMap<String, ArrayList<String[]>>();
		wiredMap2 = new HashMap<String, ArrayList<String>>();
		wirelessMap = new HashMap<String, ArrayList<String[]>>();
		apMap = new HashMap<String, ArrayList<AccessPoint>>();
		timeMap = new HashMap<String, Long>();
		devices = new ArrayList<String>();
		newData = new HashMap<String, MonitorData>();
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
		/* check if this device's data exist in overiew (~ db or cache memory) */
		if (timeMap.containsKey(device)) {
			System.out.print(device + " is found!");
			if (dataIsUpdated(device, md)) {
				System.out.println(" and updated!");
				timeMap.put(device, System.currentTimeMillis());
				return;
			}
		}
		System.out.println(device + " is NOT found and NOT updated!");
		System.out.println("Memory: memory is going to be updated...");
		/* otherwise update the οωεoverview */
		wiredMap.put(device, md.getInterfNameList());
		wiredMap2.put(device, md.getInterfNameList2());
		wirelessMap.put(device, md.getWirInterfNameList());
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

	private boolean dataIsUpdated(String device, MonitorData md) {
		ArrayList<String[]> s = new ArrayList<String[]>();
		String[] str = new String[2];
		int mdSize = 0;
		int overviewSize = 0;
		int found = 0;

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
			return false;
		}
		found = 0;
		System.out.println("I am going to print everything...");
		for (int i = 0; i != mdSize; ++i) {
			for (int j = 0; j != overviewSize; ++j) {
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
		}
		/* even if overviewSize == mdSize, the unchanged interfaces may differ */
		if (found != overviewSize) {
			System.out.println("false2");
			return false;
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
			return false;
		}
		found = 0;
		for (int i = 0; i != mdSize; ++i) {
			for (int j = 0; j != overviewSize; ++j) {
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
		if (found != overviewSize) {
			System.out.println("false4");
			return false;
		}
		System.out.println("=");


		return true;
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
}
