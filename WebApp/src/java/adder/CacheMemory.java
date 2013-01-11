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
	private HashMap<String, ArrayList<String>> wirelessMap;
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
		wirelessMap = new HashMap<String, ArrayList<String>>();
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

	public void update(String device, MonitorData md) {
//		/* check if this device exists in db */
//		if ( timeMap.containsKey(device) ) {
//			// for listA
//			for (int i = 0; i != md.getListA().size(); ++i) {
//				wiredMap.containsValue(md.getListA().get(i).get_InterfaceName());
//				// difereent from wireless
//				md.getListA().get(i).get_InterfaceName().equals("adsf");
//			}
//		}
//		
		/* then update the οωεoverview */
		wiredMap.put(device, md.getInterfNameList());
		wiredMap2.put(device, md.getInterfNameList2());
		wirelessMap.put(device, md.getWirInterfNameList());
		apMap.put(device, md.getListC());
		timeMap.put(device, System.currentTimeMillis());
		/* TODO: check list with devices */

		/* hold the latest info in memory */
		newData.put(device, md);
		ArrayList<String[]> s = new ArrayList<String[]>();
		if (timeMap.containsKey(device)) {
			System.out.println("device: " + device + " is contained!");
			s = wiredMap.get("green-tower");
		} else {
			System.out.println("malaka den tha koimitheis apopse...");
		}

		String[] str = new String[2];
		for (int i = 0; i != md.getListA().size(); ++i) {
			for (int j = 0; j != s.size(); ++j) {
				str[0] = md.getListA().get(i).get_InterfaceName();
				str[1] = Integer.toString(md.getListA().get(i).hashCode());
				if (s.get(j)[0].equals(str[0]) && s.get(j)[1].equals(str[1])) {
					System.out.println("found: " + s.get(j)[0]);
					break;
				}
			}
		}
		System.out.println("=");

	}

	public boolean hasLatestInfoOf(String device) {
		return newData.containsKey("device");
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

	public MonitorData getInfoOf(String device, String interf) {



		return null;
	}
}
