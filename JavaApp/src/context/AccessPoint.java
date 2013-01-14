package context;

public class AccessPoint {	
		
	/*
	 *  AccessPoint members
	 *  mutators
	 *  accessors
	 */

	private String aPMAC;
	private String aPESSID;
	private String aPChannel;
	private String aPMode;
	private String aPSignalLevel;
		
	public void set_APMAC(String str) {this.aPMAC = str;}
	public String get_APMAC() {return this.aPMAC;}

	public void set_APESSID(String str) {this.aPESSID = str;}
	public String get_APESSID() {return this.aPESSID;}

	public void set_APChannel(String str) {this.aPChannel = str;}
	public String get_APChannel() {return this.aPChannel;}
		
	public void set_APMode(String str) {this.aPMode = str;}
	public String get_APMode() {return this.aPMode;}
		
	public void set_APSignalLevel(String str) {this.aPSignalLevel = str;}
	public String get_APSignalLevel() {return this.aPSignalLevel;}

}