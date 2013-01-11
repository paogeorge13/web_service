import interfaces.WiredInterface;
import interfaces.AccessPoint;
import interfaces.WirelessInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Math;

/* Data class contains 3 (three) lists
 * listA: all wired interfaces
 * listB: all wireless interfaces
 * listC: all available Access Points
 */

public class Data implements Serializable {

	private static Data instance = null;
	
	private ArrayList<WiredInterface> ListA;
	private ArrayList<WirelessInterface> ListB;
	private ArrayList<AccessPoint> ListC;
	private long startTime;
	public boolean sys1 = false;
	
	private Data() {
		ListA = new ArrayList<WiredInterface>();
		ListB = new ArrayList<WirelessInterface>();
		ListC = new ArrayList<AccessPoint>();
	}
	
	public static synchronized Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	

	public ArrayList<WiredInterface> getListA() {
		return ListA;
	}

	public ArrayList<WirelessInterface> getListB() {
		return ListB;
	}

	public ArrayList<AccessPoint> getListC() {
		return ListC;
	}
	
	

	public void set_StartTime(long time) {
		this.startTime = time;	
	}
	public long get_StartTime() {
		return startTime;
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~A~~~~~~~~~~~~~~~~~~~~~~~*/
	
	public synchronized int NumOfWired() {		
		return ListA.size();	
	}	
	
	
	public synchronized boolean NoWired() {		
		return ListA.isEmpty();	
	}
	
	
	public synchronized WiredInterface getWired(int i) {
		return ListA.get(i);
	}	
	
	
	public synchronized void addWired(WiredInterface nd) {
		ListA.add(nd);
	}	
	
	
	public synchronized void removeWired(int i) {
		ListA.remove(i);
	}	
	
	public synchronized void clearWiredInterfacesList() {
		ListA.clear();
	}
	/*~~~~~~~~~~~~~~~~~~~~~~~~B~~~~~~~~~~~~~~~~~~~~~~~*/
	
	public synchronized int NumOfWireless() {		
		return ListB.size();	
	}	
	
	public synchronized boolean NoWireless() {		
		return ListB.isEmpty();	
	}
	
	public synchronized WirelessInterface getWireless(int i) {
		return ListB.get(i);
	}	
	
	public synchronized void addWireless(WirelessInterface nd) {
		ListB.add(nd);
	}
	
	public synchronized void removeWireless(int i) {
		ListB.remove(i);
	}
	
	public synchronized void clearWirelessInterfacesList() {
		ListB.clear();
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~C~~~~~~~~~~~~~~~~~~~~~~~*/
	
	public synchronized int NumOfAccessPoints() {		
		return ListC.size();	
	}	
	
	public synchronized boolean NoAccessPoint() {		
		return ListC.isEmpty();	
	}
	
	public synchronized AccessPoint getAccessPoint(int i) {
		return ListC.get(i);
	}	
	
	public synchronized void addAccessPoint(AccessPoint nd) {
		ListC.add(nd);
	}
	
	public synchronized void removeAccessPoint(int i) {
		ListC.remove(i);
	}
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~A~~~~~~~~~~~~~~~~~~~~~~~*/
		
	/* We have two nodes. newNode and ListA.get(i) 
	 * After executing this function, ListA.get(i) node will have the 
	 * same data as newNode.
	 */
	public boolean update(WiredInterface newNode, int i, double x) {
		double doubleList , doubleNewNode;
		boolean update = false;
		// MAC Address
		if ( ListA.get(i).get_InterfaceMAC() == null ) {
			ListA.get(i).set_InterfaceMAC(newNode.get_InterfaceMAC());	
			update = true;
		}			
		else if (! ListA.get(i).get_InterfaceMAC().equals(newNode.get_InterfaceMAC())) {
			ListA.get(i).set_InterfaceMAC(newNode.get_InterfaceMAC());	
			update = true;
		}
		// IP address
		if ( ListA.get(i).get_InterfaceIP() == null ) {
			ListA.get(i).set_InterfaceIP(newNode.get_InterfaceIP());	
			update = true;
		}			
		else if (! ListA.get(i).get_InterfaceIP().equals(newNode.get_InterfaceIP())) {
			ListA.get(i).set_InterfaceIP(newNode.get_InterfaceIP());
			update = true;
		}
		// InterfaceMask
		if ( ListA.get(i).get_InterfaceMask() == null ) {
			ListA.get(i).set_InterfaceMask(newNode.get_InterfaceMask());	
			update = true;
		}			
		else if (! ListA.get(i).get_InterfaceMask().equals(newNode.get_InterfaceMask())) {
			ListA.get(i).set_InterfaceMask(newNode.get_InterfaceMask());	
			update = true;
		}
		// NetworkAddress
		if ( ListA.get(i).get_NetworkAddress() == null ) {
			ListA.get(i).set_NetworkAddress(newNode.get_NetworkAddress());					
		}			
		else if (! ListA.get(i).get_NetworkAddress().equals(newNode.get_NetworkAddress())) {
			ListA.get(i).set_NetworkAddress(newNode.get_NetworkAddress());					
		}
		// Bcast
		if ( ListA.get(i).get_Bcast() == null ) {
			ListA.get(i).set_Bcast(newNode.get_Bcast());					
		}			
		else if (! ListA.get(i).get_Bcast().equals(newNode.get_Bcast())) {
			ListA.get(i).set_Bcast(newNode.get_Bcast());					
		}
		// DefaultGetway
		if ( ListA.get(i).get_DefaultGetway() == null ) {
			ListA.get(i).set_DefaultGetway(newNode.get_DefaultGetway());	
			update = true;
		}			
		else if (! ListA.get(i).get_DefaultGetway().equals(newNode.get_DefaultGetway())) {
			ListA.get(i).set_DefaultGetway(newNode.get_DefaultGetway());
			update = true;
		}
		// MaxTransfer
		if ( ListA.get(i).get_MaxTransfer() == null ) {
			ListA.get(i).set_MaxTransfer(newNode.get_MaxTransfer());					
		}			
		else if (! ListA.get(i).get_MaxTransfer().equals(newNode.get_MaxTransfer())) {
			ListA.get(i).set_MaxTransfer(newNode.get_MaxTransfer());					
		}
		// CurrentTransfer
		if ( ListA.get(i).get_CurrentTransfer() == null ) {
			ListA.get(i).set_CurrentTransfer(newNode.get_CurrentTransfer());	
			if (Double.parseDouble(ListA.get(i).get_CurrentTransfer()) > x)
				update = true;
		}			
		else if (! ListA.get(i).get_CurrentTransfer().equals(newNode.get_CurrentTransfer())) {
			doubleList = Double.parseDouble(ListA.get(i).get_CurrentTransfer());
			doubleNewNode = Double.parseDouble(newNode.get_CurrentTransfer());
			if (Math.abs(doubleList-doubleNewNode) > x)
				update = true;
			ListA.get(i).set_CurrentTransfer(newNode.get_CurrentTransfer());	
			
		}
		// ConsumedRate
		if ( ListA.get(i).get_ConsumedRate() == null ) {
			ListA.get(i).set_ConsumedRate(newNode.get_ConsumedRate());					
		}			
		else if (! ListA.get(i).get_ConsumedRate().equals(newNode.get_ConsumedRate())) {
			ListA.get(i).set_ConsumedRate(newNode.get_ConsumedRate());					
		}
		// PacketError
		if ( ListA.get(i).get_PacketError() == null ) {
			ListA.get(i).set_PacketError(newNode.get_PacketError());	
			if (Double.parseDouble(ListA.get(i).get_PacketError()) > x)
				update = true;
		}			
		else if (! ListA.get(i).get_PacketError().equals(newNode.get_PacketError())) {
			doubleList = Double.parseDouble(ListA.get(i).get_PacketError());
			doubleNewNode = Double.parseDouble(newNode.get_PacketError());
			if (Math.abs(doubleList-doubleNewNode) > x)
				update = true;
			ListA.get(i).set_PacketError(newNode.get_PacketError());					
		}
		return update;
	}
	
	
	/* find the node and update it's data */
	public synchronized boolean check_and_update(String name, WiredInterface newNode, double x) {	
		boolean Varupdate = false;
		for ( int i = 0; i != ListA.size(); ++i ) {
			/* find the node we want to check */
			if (ListA.get(i).get_InterfaceName().equals(name)) {
				/* update any info */
				Varupdate = update(newNode, i, x);	
				break;
			}				
		}
		return Varupdate;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~B~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* We have two nodes. newNode and ListB.get(i) 
	 * After executing this function, ListB.get(i) node will have the 
	 * same data as newNode.
	 */
	public boolean update(WirelessInterface newNode, int i, double x) {
		double doubleList , doubleNewNode;
		boolean update = false;
		// MAC Address
		if ( ListB.get(i).get_InterfaceMAC() == null ) {
			ListB.get(i).set_InterfaceMAC(newNode.get_InterfaceMAC());		
			update = true;
		}			
		else if (! ListB.get(i).get_InterfaceMAC().equals(newNode.get_InterfaceMAC())) {
			ListB.get(i).set_InterfaceMAC(newNode.get_InterfaceMAC());
			update = true;
		}
		// IP address
		if ( ListB.get(i).get_InterfaceIP() == null ) {
			ListB.get(i).set_InterfaceIP(newNode.get_InterfaceIP());					
			update = true;
		}			
		else if (! ListB.get(i).get_InterfaceIP().equals(newNode.get_InterfaceIP())) {
			ListB.get(i).set_InterfaceIP(newNode.get_InterfaceIP());
			update = true;
		}
		// InterfaceMask
		if ( ListB.get(i).get_InterfaceMask() == null ) {
			ListB.get(i).set_InterfaceMask(newNode.get_InterfaceMask());
			update = true;
		}			
		else if (! ListB.get(i).get_InterfaceMask().equals(newNode.get_InterfaceMask())) {
			ListB.get(i).set_InterfaceMask(newNode.get_InterfaceMask());	
			update = true;
		}
		// NetworkAddress
		if ( ListB.get(i).get_NetworkAddress() == null ) {
			ListB.get(i).set_NetworkAddress(newNode.get_NetworkAddress());					
		}			
		else if (! ListB.get(i).get_NetworkAddress().equals(newNode.get_NetworkAddress())) {
			ListB.get(i).set_NetworkAddress(newNode.get_NetworkAddress());					
		}
		// Bcast
		if ( ListB.get(i).get_Bcast() == null ) {
			ListB.get(i).set_Bcast(newNode.get_Bcast());					
		}			
		else if (! ListB.get(i).get_Bcast().equals(newNode.get_Bcast())) {
			ListB.get(i).set_Bcast(newNode.get_Bcast());					
		}
		// DefaultGetway
		if ( ListB.get(i).get_DefaultGetway() == null ) {
			ListB.get(i).set_DefaultGetway(newNode.get_DefaultGetway());	
			update = true;
		}			
		else if (! ListB.get(i).get_DefaultGetway().equals(newNode.get_DefaultGetway())) {
			ListB.get(i).set_DefaultGetway(newNode.get_DefaultGetway());	
			update = true;
		}
		// MaxTransfer
		if ( ListB.get(i).get_MaxTransfer() == null ) {
			ListB.get(i).set_MaxTransfer(newNode.get_MaxTransfer());					
		}			
		else if (! ListB.get(i).get_MaxTransfer().equals(newNode.get_MaxTransfer())) {
			ListB.get(i).set_MaxTransfer(newNode.get_MaxTransfer());					
		}
		// CurrentTransfer
		if ( ListB.get(i).get_CurrentTransfer() == null ) {
			ListB.get(i).set_CurrentTransfer(newNode.get_CurrentTransfer());	
			if (Double.parseDouble(ListB.get(i).get_CurrentTransfer()) > x)
				update = true;
		}			
		else if (! ListB.get(i).get_CurrentTransfer().equals(newNode.get_CurrentTransfer())) {
			doubleList = Double.parseDouble(ListB.get(i).get_CurrentTransfer());
			doubleNewNode = Double.parseDouble(newNode.get_CurrentTransfer());
			if (Math.abs(doubleList-doubleNewNode) > x)
				update = true;
			ListB.get(i).set_CurrentTransfer(newNode.get_CurrentTransfer());	
			
		}
		// ConsumedRate
		if ( ListB.get(i).get_ConsumedRate() == null ) {
			ListB.get(i).set_ConsumedRate(newNode.get_ConsumedRate());					
		}			
		else if (! ListB.get(i).get_ConsumedRate().equals(newNode.get_ConsumedRate())) {
			ListB.get(i).set_ConsumedRate(newNode.get_ConsumedRate());					
		}
		// PacketError
		if ( ListB.get(i).get_PacketError() == null ) {
			ListB.get(i).set_PacketError(newNode.get_PacketError());	
			if (Double.parseDouble(ListB.get(i).get_PacketError()) > x)
				update = true;
		}			
		else if (! ListB.get(i).get_PacketError().equals(newNode.get_PacketError())) {
			doubleList = Double.parseDouble(ListB.get(i).get_PacketError());
			doubleNewNode = Double.parseDouble(newNode.get_PacketError());
			if (Math.abs(doubleList-doubleNewNode) > x)
				update = true;
			ListB.get(i).set_PacketError(newNode.get_PacketError());					
		}
		//basestationMac
		if ( ListB.get(i).get_BaseStationMAC() == null ) {
			ListB.get(i).set_BaseStationMAC(newNode.get_BaseStationMAC());					
		}			
		else if (! ListB.get(i).get_BaseStationMAC().equals(newNode.get_BaseStationMAC())) {
			ListB.get(i).set_BaseStationMAC(newNode.get_BaseStationMAC());					
		}
		//ESSID
		if ( ListB.get(i).get_ESSID() == null ) {
			ListB.get(i).set_ESSID(newNode.get_ESSID());					
		}			
		else if (! ListB.get(i).get_ESSID().equals(newNode.get_ESSID())) {
			ListB.get(i).set_ESSID(newNode.get_ESSID());					
		}
		//Channel
		if ( ListB.get(i).get_Channel() == null ) {
			ListB.get(i).set_Channel(newNode.get_Channel());					
		}			
		else if (! ListB.get(i).get_Channel().equals(newNode.get_Channel())) {
			ListB.get(i).set_Channel(newNode.get_Channel());					
		}
		//Mode
		if ( ListB.get(i).get_Mode() == null ) {
			ListB.get(i).set_Mode(newNode.get_Mode());					
		}			
		else if (! ListB.get(i).get_Mode().equals(newNode.get_Mode())) {
			ListB.get(i).set_Mode(newNode.get_Mode());					
		}
		//TransmitPower
		if ( ListB.get(i).get_TransmitPower() == null ) {
			ListB.get(i).set_TransmitPower(newNode.get_TransmitPower());
			update = true;
		}			
		else if (! ListB.get(i).get_TransmitPower().equals(newNode.get_TransmitPower())) {
			ListB.get(i).set_TransmitPower(newNode.get_TransmitPower());
			update = true;
		}
		//LinkQuality
		if ( ListB.get(i).get_LinkQuality() == null ) {
			ListB.get(i).set_LinkQuality(newNode.get_LinkQuality());		
			update = true;
		}			
		else if (! ListB.get(i).get_LinkQuality().equals(newNode.get_LinkQuality())) {
			ListB.get(i).set_LinkQuality(newNode.get_LinkQuality());	
			update = true;
		}
		//SignalLevel
		if ( ListB.get(i).get_SignalLevel() == null ) {
			ListB.get(i).set_SignalLevel(newNode.get_SignalLevel());
			update = true;
		}			
		else if (! ListB.get(i).get_SignalLevel().equals(newNode.get_SignalLevel())) {
			ListB.get(i).set_SignalLevel(newNode.get_SignalLevel());	
			update = true;
		}
		//NoisePower
		if ( ListB.get(i).get_NoisePower() == null ) {
			ListB.get(i).set_NoisePower(newNode.get_NoisePower());	
			update = true;
		}			
		else if (! ListB.get(i).get_NoisePower().equals(newNode.get_NoisePower())) {
			ListB.get(i).set_NoisePower(newNode.get_NoisePower());	
			update = true;
		}
		//DiscardedPackets
		if ( ListB.get(i).get_DiscardedPackets() == null ) {
			ListB.get(i).set_DiscardedPackets(newNode.get_DiscardedPackets());					
		}			
		else if (! ListB.get(i).get_DiscardedPackets().equals(newNode.get_DiscardedPackets())) {
			ListB.get(i).set_DiscardedPackets(newNode.get_DiscardedPackets());					
		}
		return update;
	}
	
	
	/* find the node and update it's data */
	public synchronized boolean check_and_update(String name, WirelessInterface newNode, double x) {	
		boolean Varupdate = false;
		for ( int i = 0; i != ListB.size(); ++i ) {
			/* find the node we want to check */
			if (ListB.get(i).get_InterfaceName().equals(name)) {
				/* update any info */
				update(newNode, i, x);	
				break;
			}				
		}	
		return Varupdate;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~C~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* We have two nodes. newNode and ListC.get(i) 
	 * After executing this function, ListC.get(i) node will have the 
	 * same data as newNode.
	 */
	public void updateAccessPoint(AccessPoint newNode, int i) {
		// MAC Address
		if ( ListC.get(i).get_APMAC() == null ) {
			ListC.get(i).set_APMAC(newNode.get_APMAC());					
		}			
		else if (! ListC.get(i).get_APMAC().equals(newNode.get_APMAC())) {
			ListC.get(i).set_APMAC(newNode.get_APMAC());					
		}
		// Channel
		if ( ListC.get(i).get_APChannel() == null ) {
			ListC.get(i).set_APChannel(newNode.get_APChannel());					
		}			
		else if (! ListC.get(i).get_APChannel().equals(newNode.get_APChannel())) {
			ListC.get(i).set_APChannel(newNode.get_APChannel());					
		}
		// Mode
		if ( ListC.get(i).get_APMode() == null ) {
			ListC.get(i).set_APMode(newNode.get_APMode());					
		}			
		else if (! ListC.get(i).get_APMode().equals(newNode.get_APMode())) {
			ListC.get(i).set_APMode(newNode.get_APMode());					
		}
		// SignalLevel
		if ( ListC.get(i).get_APSignalLevel() == null ) {
			ListC.get(i).set_APSignalLevel(newNode.get_APSignalLevel());					
		}			
		else if (! ListC.get(i).get_APSignalLevel().equals(newNode.get_APSignalLevel())) {
			ListC.get(i).set_APSignalLevel(newNode.get_APSignalLevel());					
		}
		
	}
	

	public synchronized void check_and_updateAccessPoint(String name, AccessPoint newNode) {	
		for ( int i = 0; i != ListC.size(); ++i ) {
			/* find the node we want to check */
			if (ListC.get(i).get_APESSID().equals(name)) {
				/* update any info */
				updateAccessPoint(newNode, i);	
				break;
			}				
		}	
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~A~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* 	
	 * Check if two nodes of ListA with 
	 * wired Interfaces are equal
	 */
	public boolean AreEqual(WiredInterface node, int i) {
		return (ListA.get(i).get_InterfaceName().equals(node.get_InterfaceName())/* &&
				(ListA.get(i).get_IsWireless() == node.get_IsWireless()) &&
				ListA.get(i).get_InterfaceMAC().equals(node.get_InterfaceMAC()) &&			
				ListA.get(i).get_InterfaceIP().equals(node.get_InterfaceIP()) &&
				ListA.get(i).get_InterfaceMask().equals(node.get_InterfaceMask()) &&
				ListA.get(i).get_NetworkAddress().equals(node.get_NetworkAddress()) &&
				ListA.get(i).get_Bcast().equals(node.get_Bcast()) &&
				ListA.get(i).get_DefaultGetway().equals(node.get_DefaultGetway()) &&
				ListA.get(i).get_MaxTransfer().equals(node.get_MaxTransfer()) &&
				ListA.get(i).get_CurrentTransfer().equals(node.get_CurrentTransfer()) &&
				ListA.get(i).get_ConsumedRate().equals(node.get_ConsumedRate()) &&
				ListA.get(i).get_PacketError().equals(node.get_PacketError())*/
				);
	}
	
	
	/* 
	 * check if a wired Interface exists in
	 * listA with wired Interfaces 
	 */
	public synchronized boolean WiredExists(WiredInterface node) {			
		boolean flag = false;
		for (int j = 0 ; j < ListA.size() ; j++) {
			if (AreEqual(node,j)) {
				flag = true;
				break;
			}
			else flag = false;
		}
		return flag;
	}	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~B~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* 	
	 * Check if two nodes of ListB with 
	 * wireless Interfaces are equal
	 */
	public boolean AreEqual(WirelessInterface node , int i) {
		return (ListB.get(i).get_InterfaceName().equals(node.get_InterfaceName()) /*&&
				ListB.get(i).get_IsWireless() == node.get_IsWireless()) &&
				ListB.get(i).get_InterfaceMAC().equals(node.get_InterfaceMAC()) &&			
				ListB.get(i).get_InterfaceIP().equals(node.get_InterfaceIP()) &&
				ListB.get(i).get_InterfaceMask().equals(node.get_InterfaceMask()) &&
				ListB.get(i).get_NetworkAddress().equals(node.get_NetworkAddress()) &&
				ListB.get(i).get_Bcast().equals(node.get_Bcast()) &&
				ListB.get(i).get_DefaultGetway().equals(node.get_DefaultGetway()) &&
				ListB.get(i).get_MaxTransfer().equals(node.get_MaxTransfer()) &&
				ListB.get(i).get_CurrentTransfer().equals(node.get_CurrentTransfer()) &&
				ListB.get(i).get_ConsumedRate().equals(node.get_ConsumedRate()) &&
				ListB.get(i).get_PacketError().equals(node.get_PacketError()) &&		
				ListB.get(i).get_BaseStationMAC().equals(node.get_BaseStationMAC()) &&
				ListB.get(i).get_ESSID().equals(node.get_ESSID()) &&
				ListB.get(i).get_Channel().equals(node.get_Channel()) &&			
				ListB.get(i).get_Mode().equals(node.get_Mode()) &&
				ListB.get(i).get_TransmitPower().equals(node.get_TransmitPower()) &&
				ListB.get(i).get_LinkQuality().equals(node.get_LinkQuality()) &&
				ListB.get(i).get_SignalLevel().equals(node.get_SignalLevel())*/ /*&&
				ListB.get(i).get_NoisePower().equals(node.get_NoisePower()) && 
				ListB.get(i).get_DiscardedPackets().equals(node.get_DiscardedPackets())*/
			);
	}
	
	
	/* 
	 * check if a wireless Interface exists in
	 * listB with wireless Interfaces 
	 */
	public boolean WirelessExists(WirelessInterface node)  {
		boolean flag = false;
		for (int j = 0 ; j < ListB.size() ; j++) {
			if (AreEqual(node,j)) {
				flag = true;
				break;
			}
			else flag = false;
		}
		return flag;
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~C~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* 	
	 * Check if two nodes of ListC with 
	 * access points are equal
	 */
	public boolean AreEqual(AccessPoint node , int i) {
		return (ListC.get(i).get_APESSID().equals(node.get_APESSID()) /*&&
			ListC.get(i).get_APMAC().equals(node.get_APMAC()) &&
			ListC.get(i).get_APChannel().equals(node.get_APChannel()) &&			
			ListC.get(i).get_APMode().equals(node.get_APMode()) &&
			ListC.get(i).get_APSignalLevel().equals(node.get_APSignalLevel()) 	*/
			);
	}
	
	
	/* 
	 * check if an access point exists in
	 * listC with access points 
	 */
	public boolean AccessPointExists(AccessPoint node) {
		boolean flag = false;
		for (int j = 0 ; j < ListC.size() ; j++) {
			if (AreEqual(node,j)) {
				flag = true;
				break;
			}
			else flag = false;
		}
		return flag;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~A~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* prints the data of a WiredInterface */
	public void printDataNode(WiredInterface node) {
			System.out.println("Follows info of interface: " + node.get_InterfaceName() + "\n" + 
			"MAC:" + node.get_InterfaceMAC() + " " + 		
			"IP:" + node.get_InterfaceIP() + " " + 		
			"Mask:" + node.get_InterfaceMask() + " " + 		
			"NetworkAddress:" + node.get_NetworkAddress() + " " + 		
			"BCast:" + node.get_Bcast() + " " + 		
			"Gtway:" + node.get_DefaultGetway() + " " + 		
			"MaxTransfer:" + node.get_MaxTransfer() + " " + 		
			"CurTransfer:" + node.get_CurrentTransfer() + " " + 		
			"ConsumedRate:" + node.get_ConsumedRate() + " " + 		
			"PacketError:" + node.get_PacketError() + " ");
			System.out.println("");
		
		/*	System.out.println("INFO monitor.exec.NetworkInterfaceMonitor - Starting monitor for interface:" 
			+ node.get_InterfaceName());*/
		
	}
	

	/* prints ListA with all the info of each node */
	public synchronized void printWiredInterfaces() {
		if ( sys1 == false ) { System.out.println("--- List A begins ---"); }
		for (int i = 0; i != ListA.size(); ++i) {
			if ( sys1 == false ) { System.out.println("node " + i + ": " + ListA.get(i).get_InterfaceName()); }
			printDataNode(ListA.get(i));
		}
		if ( sys1 == false ) { System.out.println("--- List A ends ---"); }
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~B~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* prints the data of a WirelessInterface */
	public void printDataNode(WirelessInterface node) {
		if ( sys1 == false ) {
			System.out.println("Follows info of interface: " + node.get_InterfaceName() + "\n" + 
			"MAC:" + node.get_InterfaceMAC() + " " + 		
			"IP:" + node.get_InterfaceIP() + " " + 		
			"Mask:" + node.get_InterfaceMask() + " " + 		
			"NetworkAddress:" + node.get_NetworkAddress() + " " + 		
			"BCast:" + node.get_Bcast() + " " + 		
			"Gtway:" + node.get_DefaultGetway() + " " + 		
			"MaxTransfer:" + node.get_MaxTransfer() + " " + 		
			"CurTransfer:" + node.get_CurrentTransfer() + " " + 		
			"ConsumedRate:" + node.get_ConsumedRate() + " " + 		
			"PacketError:" + node.get_PacketError() + " " +
			"BaseStationMAC:" + node.get_BaseStationMAC() + " " +
			"ESSID:" + node.get_ESSID() + " " +
			"Channel:" + node.get_Channel() + " " +
			"Mode:" + node.get_Mode() + " " +
			"TransmitPower:" + node.get_TransmitPower() + " " +
			"LinkQuality:" + node.get_LinkQuality() + " " +
			"SignalLevel:" + node.get_SignalLevel() + " " +
			"NoisePower:" + node.get_NoisePower() + " " +
			"DiscardedPackets:" + node.get_DiscardedPackets());
			System.out.println("");
		}
		/*else{
			System.out.println("INFO monitor.exec.WirelessInterfaceMonitor - Starting monitor for interface " 
			+ node.get_InterfaceName() + " with network address " + node.get_NetworkAddress()
			+ " and network mask " + node.get_InterfaceMask());
		}*/
	}
	

	/* prints ListB with all the info of each node */
	public synchronized void printWirelessInterfaces() {
		if ( sys1 == false ) { System.out.println("--- List B begins ---"); }
		for (int i = 0; i != ListB.size(); ++i) {
			if ( sys1 == false ) { System.out.println("node " + i + ": " + ListB.get(i).get_InterfaceName()); }
			printDataNode(ListB.get(i));
		}
		if ( sys1 == false ) { System.out.println("--- List B ends ---"); }
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~C~~~~~~~~~~~~~~~~~~~~~~~*/	

	/* prints the data of a WiredInterface */
	public void printDataNode(AccessPoint node) {
			System.out.println("Follows info of Access Point with ESSID: " + node.get_APESSID() + "\n" + 
			"MAC:" + node.get_APMAC() + " " + 		
			"Channel:" + node.get_APChannel() + " " + 		
			"Mode:" + node.get_APMode() + " " + 		
			"SignalLevel:" + node.get_APSignalLevel());
			System.out.println("");
	}
	

	/* prints ListC with all the info of each node */
	public synchronized void printAccessPoints() {
		System.out.println("--- List C begins ---"); 
		if (ListB.size() == 0) {
			if ( sys1 == false ) { System.out.println("No wireless interfaces to scan for Access Points"); }
		}
		else {
			for (int i = 0; i != ListC.size(); ++i) {
				if ( sys1 == false ) { System.out.println("node " + i + ": " + ListC.get(i).get_APESSID()); }
				printDataNode(ListC.get(i));
			}
		}
		System.out.println("--- List C ends ---"); 
	}


	
	
	public synchronized void clearAccessPointList() {
		ListC.clear();
	}
	
}
