package context;

public class WiredInterface {

	/*
	 *  WiredInterface members
	 *  mutators
	 *  accessors
	 */
	private String interfaceName;
	private boolean isWireless;
	private String interfaceMAC;
	private String interfaceIP;
	private String interfaceMask;
	private String networkAddress;
	private String bcast;
	private String defaultGetway;
	private String maxTransfer;
	private String currentTransfer;
	private String consumedRate;
	private String packetError;

	public WiredInterface() {
	}

	public WiredInterface(String str) {
		this.interfaceName = str;
	}

	public void set_ConsumedRate(String str) {
		this.consumedRate = str;
	}

	public String get_ConsumedRate() {
		return this.consumedRate;
	}

	public void set_CurrentTransfer(String str) {
		this.currentTransfer = str;
	}

	public String get_CurrentTransfer() {
		return this.currentTransfer;
	}

	public void set_MaxTransfer(String str) {
		this.maxTransfer = str;
	}

	public String get_MaxTransfer() {
		return this.maxTransfer;
	}

	public void set_DefaultGetway(String str) {
		this.defaultGetway = str;
	}

	public String get_DefaultGetway() {
		return this.defaultGetway;
	}

	public void set_Bcast(String str) {
		this.bcast = str;
	}

	public String get_Bcast() {
		return this.bcast;
	}

	public void set_NetworkAddress(String str) {
		this.networkAddress = str;
	}

	public String get_NetworkAddress() {
		return this.networkAddress;
	}

	public void set_InterfaceMask(String str) {
		this.interfaceMask = str;
	}

	public String get_InterfaceMask() {
		return this.interfaceMask;
	}

	public void set_InterfaceIP(String str) {
		this.interfaceIP = str;
	}

	public String get_InterfaceIP() {
		return this.interfaceIP;
	}

	public void set_InterfaceMAC(String str) {
		this.interfaceMAC = str;
	}

	public String get_InterfaceMAC() {
		return this.interfaceMAC;
	}

	//TODO: change this name
	public void set_isWireless(boolean bln) {
		this.isWireless = bln;
	}

	public boolean isWireless() {
		return this.isWireless;
	}

	public void set_InterfaceName(String str) {
		this.interfaceName = str;
	}

	public String get_InterfaceName() {
		return this.interfaceName;
	}

	public void set_PacketError(String str) {
		this.packetError = str;
	}

	public String get_PacketError() {
		return this.packetError;
	}

	@Override
	public int hashCode() {
		final int seed = 31;
		int result = 1;
		result = seed * result + ((interfaceName == null) ? 0 : interfaceName.hashCode());
		result = seed * result + ((interfaceMAC == null) ? 0 : interfaceMAC.hashCode());
		result = seed * result + ((interfaceIP == null) ? 0 : interfaceIP.hashCode());
		return result;
		//return super.hashCode();
	}
}