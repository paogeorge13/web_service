
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
		this.set_InterfaceName(str);	
	}
	
	public void set_BaseStationMAC(String str) {this.BaseStationMAC = str;}
	public String get_BaseStationMAC() {return this.BaseStationMAC;}
	
	public void set_ESSID(String str) {this.ESSID = str;}
	public String get_ESSID() {return this.ESSID;}

	public void set_Channel(String str) {this.Channel = str;}
	public String get_Channel() {return this.Channel;}

	public void set_Mode(String str) {this.Mode = str;}
	public String get_Mode() {return this.Mode;}
	
	public void set_TransmitPower(String str) {this.TransmitPower = str;}
	public String get_TransmitPower() {return this.TransmitPower;}
	
	public void set_LinkQuality(String str) {this.LinkQuality = str;}
	public String get_LinkQuality() {return this.LinkQuality;}
	
	public void set_SignalLevel(String str) {this.SignalLevel = str;}
	public String get_SignalLevel() {return this.SignalLevel;}

	public void set_NoisePower(String str) {this.NoisePower = str;}
	public String get_NoisePower() {return this.NoisePower;}

	public void set_DiscardedPackets(String str) {this.DiscardedPackets = str;}
	public String get_DiscardedPackets() {return this.DiscardedPackets;}

}