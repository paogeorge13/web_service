package context;


public class WirelessInterface extends WiredInterface {

	/*
	 *  WirelessInterface members
	 *  mutators
	 *  accessors
	 */

	private String baseStationMAC;
	private String essid;
	private String channel;
	private String mode;
	private String transmitPower;
	private String linkQuality;
	private String signalLevel;
	private String noisePower;
	private String discardedPackets;
	
	public WirelessInterface() {}
	
	public WirelessInterface(String str) {
		this.set_InterfaceName(str);	
	}
	
	public void set_BaseStationMAC(String str) {this.baseStationMAC = str;}
	public String get_BaseStationMAC() {return this.baseStationMAC;}
	
	public void set_ESSID(String str) {this.essid = str;}
	public String get_ESSID() {return this.essid;}

	public void set_Channel(String str) {this.channel = str;}
	public String get_Channel() {return this.channel;}

	public void set_Mode(String str) {this.mode = str;}
	public String get_Mode() {return this.mode;}
	
	public void set_TransmitPower(String str) {this.transmitPower = str;}
	public String get_TransmitPower() {return this.transmitPower;}
	
	public void set_LinkQuality(String str) {this.linkQuality = str;}
	public String get_LinkQuality() {return this.linkQuality;}
	
	public void set_SignalLevel(String str) {this.signalLevel = str;}
	public String get_SignalLevel() {return this.signalLevel;}

	public void set_NoisePower(String str) {this.noisePower = str;}
	public String get_NoisePower() {return this.noisePower;}

	public void set_DiscardedPackets(String str) {this.discardedPackets = str;}
	public String get_DiscardedPackets() {return this.discardedPackets;}

}