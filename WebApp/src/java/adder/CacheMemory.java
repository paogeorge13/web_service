package adder;

import context.*;
import java.util.ArrayList;
import java.util.HashMap;

/* 
 * singleton cache memory - maybe only -memory- (without cache)
 */
public class CacheMemory {

	private static CacheMemory instance = null;

	/* the below info is in database with the latest update  */
	private HashMap<String, ArrayList<String>> wiredMap;
	private HashMap<String, ArrayList<String>> wirelessMap;
	private HashMap<String, ArrayList<AccessPoint>> apMap;
	private HashMap<String, Integer> timeMap;
	private ArrayList<String> devices;

	/* the below info exists only in cache memory */
	private HashMap<String, MonitorData> newData;


	public CacheMemory() {
		wiredMap = new HashMap<String, ArrayList<String>>();
		wirelessMap = new HashMap<String, ArrayList<String>>();
		apMap = new HashMap<String, ArrayList<AccessPoint>>();
		timeMap = new HashMap<String, Integer>();
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
	
	public void updateMem(String device, MonitorData md) {
		/* update first the οωεoverview */
		wiredMap.put(device, md.getInterfNameList());
		wirelessMap.put(device, md.getWirInterfNameList());
		apMap.put(device, md.getListC());
		timeMap.put(device, Integer.SIZE);
		/* TODO: check list with devices */

		/* hold the latest info in memory */
		newData.put(device, md);
	}


	public boolean hasLatestInfoOf(String device) {
		return newData.containsKey("device");
	}

//	public MonitorData getMonitorDataOf(String device, String interf) { }

	
}
