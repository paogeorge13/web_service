import interfaces.WiredInterface;
import interfaces.WirelessInterface;
import java.util.ArrayList;
import java.util.Iterator;


public class ThreadInterface implements Runnable {	
	private Data data;
	private Operations operations = new Operations();
	private PropertyFileData prop;
	private String name;
	private String ifconfig;
	private String iwconfig;
	private String channel;
	private double d;
	private ArrayList<Integer> markovList;
	private WiredInterface interfaceData;
	private WirelessInterface wirelessInterfaceData;
	private int i , index;
	private int c , k , sleep_time , newSleep_time;
	private long T;
	private boolean update;
	private int loop;

	
	public ThreadInterface(Data data, String name, PropertyFileData prop) {
		this.data = data;	
		this.name = name;	
		this.prop = prop;
	}
	
	
	
	public void printMyName() {
		if ( data.sys1 == false ) { System.out.println("This is my name: " + Thread.currentThread()); }
	}


	
	/*calculate the network address
	 * 
	 * This function takes IP Address and Network Mask trying to calculate the Network Address
	 * The calculation is made by comparing the IP and Mask fields (the first IP's field with the first Mask's field, the
	 * second one IP's field with the second one Mask's field, ... )
	 * The compare has been made with the logical  &.
	 * 
	 * */
	public String Network_Address(String IP , String Mask)  {	
		String[] str1 = null;
		String[] str2 = null;
		int[] field1 = new int[4];
		int[] field2 = new int[4];
		int[] field3 = new int[4];
		String Address;
		
		if (IP == null){
			Address = null;
			return Address;
		}
		else {
			str1 = IP.split("\\.");
			str2 = Mask.split("\\.");
			
			for (i = 0; i < 4 ; i++) 
				field1[i] = Integer.parseInt(str1[i]);
				
			for (i = 0 ; i < 4 ; i++) 
				field2[i] = Integer.parseInt(str2[i]);
			
			for (i = 0 ; i < 4 ; i++)
				field3[i] = field1[i] & field2[i];
			
			StringBuffer sbfNumbers = new StringBuffer();
			String strSeparator = ".";
			if (field3.length > 0) {
				sbfNumbers.append(field3[0]);
				for(i = 1 ; i < field3.length ; i++)
                    sbfNumbers.append(strSeparator).append(field3[i]);
			}
			return sbfNumbers.toString();
		}
		
	}
	
	
	
	/*parameters parsing for a specific wired interface*/
	public WiredInterface WiredInterfaceInfo() {
		ifconfig = operations.Scan("ifconfig " + this.name);
		WiredInterface node = new WiredInterface();
		node.isWireless(false);
		node.set_InterfaceName(ifconfig.split(" ")[0]);
		if (ifconfig.contains("inet addr")) {
			node.set_InterfaceIP(operations.Info(ifconfig, "inet addr", 10, " "));
			node.set_InterfaceMask(operations.Info(ifconfig, "Mask", 5, "/n"));
			node.set_Bcast(operations.Info(ifconfig, "Bcast", 6, " "));
			node.set_NetworkAddress(Network_Address(node.get_InterfaceIP(),node.get_InterfaceMask())); 
			String[] ExecString = {"/bin/sh", "-c", "route -n | grep " + node.get_InterfaceName() + " | grep UG | awk '{print $2}'"};
			node.set_DefaultGetway(operations.Scan(ExecString));
			
		}
		else {
			node.set_InterfaceIP("null");
			node.set_InterfaceMask("null");
			node.set_Bcast("null");
			node.set_NetworkAddress("null");
			node.set_DefaultGetway("null");
		}
		node.set_InterfaceMAC(operations.Info(ifconfig, "HWaddr", 7, " "));
		node.set_MaxTransfer(prop.get_propertyMaxTransfer());
		
		long time1 = System.currentTimeMillis();
		String[] ExecString2 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $10}'"};
		String bytes1 = operations.Scan(ExecString2);
		
		String[] ExecString4 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $12}'"};
		String errors = operations.Scan(ExecString4);
			
		long time2 = System.currentTimeMillis();
		String[] ExecString3 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $10}'"};
		String bytes2 = operations.Scan(ExecString3);
//		int bytes3 = Integer.parseInt(bytes1);
//		int bytes4 = Integer.parseInt(bytes2);
		int bytes3 = 0;
		int bytes4 = 0;
		long currentTrans = (bytes4-bytes3)/(time2-time1);
		String currentTrans2 = Integer.toString((int) currentTrans);
		node.set_CurrentTransfer(currentTrans2);
		
		
		long time3 = System.currentTimeMillis();
		String[] ExecString5 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $12}'"};
		String errors2 = operations.Scan(ExecString5);
//		int errors3 = Integer.parseInt(errors);
//		int errors4 = Integer.parseInt(errors2);
		int errors3 = 0;
		int errors4 = 0;
		long packetErr = (errors4-errors3)/(time3-time1);
		String packetErr2 = Integer.toString((int) packetErr);
		node.set_PacketError(packetErr2);
		
		node.set_ConsumedRate(Double.toString(currentTrans/Double.parseDouble(node.get_MaxTransfer())));
		
		return node;
	}
	

	
	/*
	 * finding channel using the frequency
	 * http://www.moonblinkwifi.com/2point4freq.cfm
	 */
	public String find_Channel(String str) {
		channel = "";
		d = Double.parseDouble(str);
		if (d ==  2.412) channel = "1";
		if (d ==  2.417) channel = "2";
		if (d ==  2.422) channel = "3";
		if (d ==  2.427) channel = "4";
		if (d ==  2.432) channel = "5";
		if (d ==  2.437) channel = "6";
		if (d ==  2.442) channel = "7";
		if (d ==  2.447) channel = "8";
		if (d ==  2.452) channel = "9";
		if (d ==  2.457) channel = "10";
		if (d ==  2.462) channel = "11";
		return channel;	
	}
	
	
	
	/*parameters parsing for a specific wireless interface*/
	public WirelessInterface WirelessInterfaceInfo() {
		iwconfig = operations.Scan("iwconfig " + this.name);
		ifconfig = operations.Scan("ifconfig " + this.name);
		WirelessInterface node = new WirelessInterface();
		node.isWireless(true);
		node.set_InterfaceName(ifconfig.split(" ")[0]);
		if (ifconfig.contains("inet addr")) {
			node.set_InterfaceIP(operations.Info(ifconfig, "inet addr", 10, " "));
			node.set_InterfaceMask(operations.Info(ifconfig, "Mask", 5, "/n"));
			node.set_Bcast(operations.Info(ifconfig, "Bcast", 6, " "));
			node.set_NetworkAddress(Network_Address(node.get_InterfaceIP(),node.get_InterfaceMask()));
			String[] ExecString = {"/bin/sh", "-c", "route -n | grep " + node.get_InterfaceName() + " | grep UG | awk '{print $2}'"};
			node.set_DefaultGetway(operations.Scan(ExecString));
		}
		else {
			node.set_InterfaceIP("null");
			node.set_InterfaceMask("null");
			node.set_Bcast("null");
			node.set_NetworkAddress("null");
			node.set_DefaultGetway("null");
		}
		node.set_InterfaceMAC(operations.Info(ifconfig, "HWaddr", 7, " "));
		node.set_MaxTransfer(prop.get_propertyMaxTransfer());
		
		long time1 = System.currentTimeMillis();
		String[] ExecString2 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $10}'"};
		String bytes1 = operations.Scan(ExecString2);
		
		String[] ExecString4 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $12}'"};
		String errors = operations.Scan(ExecString4);
			
		long time2 = System.currentTimeMillis();
		String[] ExecString3 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $10}'"};
		String bytes2 = operations.Scan(ExecString3);
		int bytes3 = Integer.parseInt(bytes1);
		int bytes4 = Integer.parseInt(bytes2);
		long currentTrans = (bytes4-bytes3)/(time2-time1);
		String currentTrans2 = Integer.toString((int) currentTrans);
		node.set_CurrentTransfer(currentTrans2);
		
		
		long time3 = System.currentTimeMillis();
		String[] ExecString5 = {"/bin/sh", "-c", "cat /proc/net/dev | grep " + node.get_InterfaceName() + " |  awk '{print $12}'"};
		String errors2 = operations.Scan(ExecString5);
//		int errors3 = Integer.parseInt(errors);
//		int errors4 = Integer.parseInt(errors2);
		int errors3 = 0;
		int errors4 = 0;
		long packetErr = (errors4-errors3)/(time3-time1);
		String packetErr2 = Integer.toString((int) packetErr);
		node.set_PacketError(packetErr2);
		
		node.set_ConsumedRate(Double.toString(currentTrans/Double.parseDouble(node.get_MaxTransfer())));
		
		node.set_BaseStationMAC(operations.Info(iwconfig, "Access Point", 14, " "));
		if (!iwconfig.contains("Frequency")) {
			node.set_InterfaceMask(operations.Info(ifconfig, "Mask", 5, " "));
			node.set_BaseStationMAC("null");
			node.set_ESSID("null");
			node.set_Channel("null");	
			node.set_LinkQuality("null");
			node.set_SignalLevel("null");
			node.set_NoisePower("null");
			node.set_DiscardedPackets("null");
		}
		else {
			node.set_ESSID(operations.Info(iwconfig, "ESSID", 6, "/n"));
			node.set_Channel(find_Channel(operations.Info(iwconfig, "Frequency", 10, " ")));
			node.set_LinkQuality(operations.Info(iwconfig, "Link Quality", 13, " "));
			node.set_SignalLevel(operations.Info(iwconfig, "Signal level", 13, " "));
			String[] ExecString = {"/bin/sh", "-c"," awk 'NR == 3  {print $5}' /proc/net/wireless"};
			node.set_NoisePower(operations.Scan(ExecString));
			String[] ExecString1 = {"/bin/sh", "-c", "route -n | grep " + node.get_InterfaceName() + " | awk '{print $6+$7+$8+$9+$10}'"};
			node.set_DiscardedPackets(operations.Scan(ExecString1));
		}
		node.set_Mode(operations.Info(iwconfig, "Mode", 5, " "));
		node.set_TransmitPower(operations.Info(iwconfig, "Tx-Power", 9, " "));	
		return node;
	}
	
		
	
	public void run() {
		
		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO InterfaceMonitor - Starting monitor for interface: " + name);
		k = Integer.parseInt(prop.get_propertyk());
		c = Integer.parseInt(prop.get_propertyc());
		sleep_time = Integer.parseInt(prop.get_propertysleep_time());
		newSleep_time = sleep_time;
		markovList = new ArrayList<Integer>();

		for (int i = 1; i != k+1; ++i) {
			for (int j = 0; j != c; ++j) {
				markovList.add(new Integer(i * sleep_time));
			}
		}

		Iterator it = markovList.iterator();
		loop = 0;
		while ("the truth about forever is that it is happening right now" != null) {
			loop++;
			try {
				
				/* if that interface is wireless
				 * parse its parameters
				 * and check for updates
				 */				
				String ret1 = operations.Scan("iwconfig " + this.name);
				String ret2 = operations.Scan("ifconfig " + this.name);
				boolean isWireless = operations.Scan("iwconfig " + this.name).contains("Access Point");
						
				if (!((ret1 == "") && (ret2 == ""))) {
					if (isWireless) {
						long time1 = System.currentTimeMillis();
						wirelessInterfaceData = WirelessInterfaceInfo();
						update = data.check_and_update(this.name, wirelessInterfaceData, Double.parseDouble(prop.get_propertyx()));
						T = System.currentTimeMillis() - time1;
						if (update) {
							newSleep_time = sleep_time;
							it = markovList.iterator();
						}
						else { 
							if (c == 1) {
								if ( newSleep_time < (k * sleep_time) )
									newSleep_time += sleep_time;
							}
							else {
								if (it.hasNext()) {
									newSleep_time = (int) it.next();
								}
							}
						}
					}
					
					/* if that interface is wired
					 * parse its parameters
					 * and check for updates
					 */
					else {
						long time1 = System.currentTimeMillis();
						interfaceData = WiredInterfaceInfo();
						update = data.check_and_update(this.name, interfaceData, Double.parseDouble(prop.get_propertyx()));
						T = System.currentTimeMillis() - time1;
						if (update) {
							newSleep_time = sleep_time;
							it = markovList.iterator();
						}
						else { 
							if (c == 1) {
								if ( newSleep_time < (k * sleep_time) )
									newSleep_time += sleep_time;
							}
							else {
								if (it.hasNext()) {
									newSleep_time = (int) it.next();
								}
							}
						}
					}
				}
				Thread.currentThread().sleep(newSleep_time - T);						
			} catch (InterruptedException e) {
				System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO InterfaceMonitor - Stopping monitor for interface: " + name);
				break;		
			}
			System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO InterfaceMonitor - " + name + " -> State is " + newSleep_time/sleep_time + ". Internal Loop is: " + loop + ". Timeout is: " + (newSleep_time-T) + "ms");

		}
	}
	
	
	
	
}