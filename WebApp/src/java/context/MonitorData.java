package context;

import java.util.ArrayList;
import java.util.Iterator;

public class MonitorData {

	private ArrayList<WiredInterface> listA;
	private ArrayList<WirelessInterface> listB;
	private ArrayList<AccessPoint> listC;

	public ArrayList<WiredInterface> getListA() {
		return listA;
	}

	public ArrayList<WirelessInterface> getListB() {
		return listB;
	}

	public ArrayList<AccessPoint> getListC() {
		return listC;
	}

	public ArrayList<String> getInterfNameList() {
		ArrayList<String> nameList = new ArrayList<String>();

		for (int i = 0; i != listA.size(); ++i) {
			if (listA.get(i).isWireless()) {
				nameList.add(listA.get(i).getInterfaceName());
			}
		}
		return nameList;
	}

	public ArrayList<String> getWirInterfNameList() {
		ArrayList<String> nameList = new ArrayList<String>();

		for (int i = 0; i != listB.size(); ++i) {
			nameList.add(listB.get(i).getInterfaceName());
		}
		return nameList;
	}
/*
	public ArrayList<String> getAPNameList(ArrayList<AccessPoint> list) {
		ArrayList<String> nameList = new ArrayList<String>();

		for (int i = 0; i != list.size(); ++i) {
			nameList.add(list.get(i).getInterfaceName());
		}
		return nameList;
	}
	*/
}
