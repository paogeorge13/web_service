package context;

import java.util.ArrayList;

public class MonitorData {

	private ArrayList<WiredInterface> listA = null;
	private ArrayList<WirelessInterface> listB = null;
	private ArrayList<AccessPoint> listC = null;

	public MonitorData() {
		System.out.println("Monitor Data has been just created!");
		listA = new ArrayList<WiredInterface>();
		listB = new ArrayList<WirelessInterface>();
		listC = new ArrayList<AccessPoint>();
	}

	public void setListA(ArrayList<WiredInterface> listA) {
		this.listA = listA;
	}

	public void setListB(ArrayList<WirelessInterface> listB) {
		this.listB = listB;
	}

	public void setListC(ArrayList<AccessPoint> listC) {
		this.listC = listC;
	}

	public ArrayList<WiredInterface> getListA() {
		return listA;
	}

	public ArrayList<WirelessInterface> getListB() {
		return listB;
	}

	public ArrayList<AccessPoint> getListC() {
		return listC;
	}

	public ArrayList<String[]> getInterfNameList(ArrayList<String[]> previous) {
		ArrayList<String[]> nameList = new ArrayList<String[]>();

		for (int i = 0; i != listA.size(); ++i) {
			if (!listA.get(i).isWireless()) {
				String[] f = new String[3];
				f[0] = listA.get(i).get_InterfaceName();
				f[1] = Integer.toString(listA.get(i).hashCode());
				f[2] = Integer.toString(0);
				/* check if this interface is already in overview */
				if (previous != null) {
					f[2] = Integer.toString(0);
					/* if previous contains exactly the same interface and hashCode, 
					 * then keep the previous info about db insertion
					 * -> String[3] (0 or 1)
					 */
					if (previous.contains(f)) {
						nameList.add(f);
						continue;
					}
					f[2] = Integer.toString(1);
					if (previous.contains(f)) {
						nameList.add(f);
						continue;
					}
				}
				System.out.println("for " + f[0] + " I found this hashcode: " + listA.get(i).hashCode());
				/* if this interface is not in overview, then f[2] = 0 */
				nameList.add(f);
			}
		}
		return nameList;
	}

	public ArrayList<String> getInterfNameList2() {
		ArrayList<String> nameList = new ArrayList<String>();

		for (int i = 0; i != listA.size(); ++i) {
			if (!listA.get(i).isWireless()) {
				nameList.add(listA.get(i).get_InterfaceName());
			}
		}
		return nameList;
	}

	public ArrayList<String[]> getWirInterfNameList(ArrayList<String[]> previous) {
		ArrayList<String[]> nameList = new ArrayList<String[]>();

		for (int i = 0; i != listB.size(); ++i) {
			String[] f = new String[3];
			f[0] = listB.get(i).get_InterfaceName();
			f[1] = Integer.toString(listB.get(i).hashCode());
			f[2] = Integer.toString(0);
			/* check if this interface is already in overview */
			if (previous != null) {
				f[2] = Integer.toString(0);
				/* if previous contains exactly the same interface and hashCode, 
				 * then keep the previous info about db insertion
				 * -> String[3] (0 or 1)
				 */
				if (previous.contains(f)) {
					nameList.add(f);
					continue;
				}
				f[2] = Integer.toString(1);
				if (previous.contains(f)) {
					nameList.add(f);
					continue;
				}
			}
			nameList.add(f);
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
