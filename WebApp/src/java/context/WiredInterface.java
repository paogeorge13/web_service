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

	public String getInterfaceName() {
		return InterfaceName;
	}
			
	public String get_ConsumedRate() {return this.ConsumedRate;}

	public String get_CurrentTransfer() {return this.CurrentTransfer;}

	public String get_MaxTransfer() {return this.MaxTransfer;}

	public String get_DefaultGetway() {return this.DefaultGetway;}
		
	public String get_Bcast() {return this.Bcast;}

	public String get_NetworkAddress() {return this.NetworkAddress;}

	public String get_InterfaceMask() {return this.InterfaceMask;}

	public String get_InterfaceIP() {return this.InterfaceIP;}

	public String get_InterfaceMAC() {return this.InterfaceMAC;}

	public boolean isWireless() {return this.IsWireless;}

	public String get_InterfaceName() {return this.InterfaceName;}

	public String get_PacketError() {return this.PacketError;}

}