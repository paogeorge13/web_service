package context;


public class WirelessInterface extends WiredInterface {

	/*
	 *  WirelessInterface members
	 *  mutators
	 *  accessors
	 */

	private String BaseStationMAC;
	private String ESSID;
	private String Channel;
	private String Mode;
	private String TransmitPower;
	private String LinkQuality;
	private String SignalLevel;
	private String NoisePower;
	private String DiscardedPackets;
	
	public WirelessInterface() {}
	
	public WirelessInterface(String str) {
		//WiredInterface(str);
		//this.set_InterfaceName(str);	
	}
	
	public String get_BaseStationMAC() {return this.BaseStationMAC;}
	
	public String get_ESSID() {return this.ESSID;}

	public String get_Channel() {return this.Channel;}

	public String get_Mode() {return this.Mode;}
	
	public String get_TransmitPower() {return this.TransmitPower;}
	
	public String get_LinkQuality() {return this.LinkQuality;}
	
	public String get_SignalLevel() {return this.SignalLevel;}

	public String get_NoisePower() {return this.NoisePower;}

	public String get_DiscardedPackets() {return this.DiscardedPackets;}

}