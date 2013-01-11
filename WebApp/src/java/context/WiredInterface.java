package context;

public class WiredInterface {
		
	/*
	 *  WiredInterface members
	 *  mutators
	 *  accessors
	 */
		
	private String InterfaceName;		
	private boolean IsWireless;	
	private String InterfaceMAC;		
	private String InterfaceIP;			
	private String InterfaceMask;		
	private String NetworkAddress;			
	private String Bcast;		
	private String DefaultGetway;		
	private String MaxTransfer;			
	private String CurrentTransfer;		
	private String ConsumedRate;		
	private String PacketError;	
		
	public WiredInterface() {}
		
	public WiredInterface(String str) {
		this.InterfaceName = str;	
	}
			
	public void set_ConsumedRate(String str) {this.ConsumedRate = str;}
	public String get_ConsumedRate() {return this.ConsumedRate;}

	public void set_CurrentTransfer(String str) {this.CurrentTransfer = str;}
	public String get_CurrentTransfer() {return this.CurrentTransfer;}

	public void set_MaxTransfer(String str) {this.MaxTransfer = str;}
	public String get_MaxTransfer() {return this.MaxTransfer;}

	public void set_DefaultGetway(String str) {this.DefaultGetway = str;}
	public String get_DefaultGetway() {return this.DefaultGetway;}
		
	public void set_Bcast(String str) {this.Bcast = str;}
	public String get_Bcast() {return this.Bcast;}

	public void set_NetworkAddress(String str) {this.NetworkAddress = str;}
	public String get_NetworkAddress() {return this.NetworkAddress;}

	public void set_InterfaceMask(String str) {this.InterfaceMask = str;}
	public String get_InterfaceMask() {return this.InterfaceMask;}

	public void set_InterfaceIP(String str) {this.InterfaceIP = str;}
	public String get_InterfaceIP() {return this.InterfaceIP;}

	public void set_InterfaceMAC(String str) {this.InterfaceMAC = str;}
	public String get_InterfaceMAC() {return this.InterfaceMAC;}

	//TODO: change this name
	public void isWireless(boolean bln) {this.IsWireless = bln;}
	public boolean isWireless() {return this.IsWireless;}

	public void set_InterfaceName(String str) {this.InterfaceName = str;}
	public String get_InterfaceName() {return this.InterfaceName;}

	public void set_PacketError(String str) {this.PacketError = str;}
	public String get_PacketError() {return this.PacketError;}

}