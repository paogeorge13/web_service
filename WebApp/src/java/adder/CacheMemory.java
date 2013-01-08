package adder;

import context.*;
import java.util.ArrayList;
import java.util.HashMap;

/* 
 * singleton cache memory
 */
public class CacheMemory {

	private static CacheMemory instance = null;

	private HashMap<String, ArrayList<WiredInterface>> wiredMap;
	private HashMap<String, ArrayList<WirelessInterface>> wirelessMap;
	private HashMap<String, ArrayList<AccessPoint>> apMap;
	private HashMap<String, Integer> timeMap;


	public CacheMemory() {
		wiredMap = new HashMap<String, ArrayList<WiredInterface>>();
		wirelessMap = new HashMap<String, ArrayList<WirelessInterface>>();
		apMap = new HashMap<String, ArrayList<AccessPoint>>();
		timeMap = new HashMap<String, Integer>();
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
		wiredMap.put(device, md.getListA());
		wirelessMap.put(device, md.getListB());
		apMap.put(device, md.getListC());
		timeMap.put(device, Integer.SIZE);
	}

	
}
