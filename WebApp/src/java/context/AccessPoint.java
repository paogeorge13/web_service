package context;

public class AccessPoint {	
		
	/*
	 *  AccessPoint members
	 *  mutators
	 *  accessors
	 */

	private String APMAC;
	private String APESSID;
	private String APChannel;
	private String APMode;
	private String APSignalLevel;
		
	public void set_APMAC(String str) {this.APMAC = str;}
	public String get_APMAC() {return this.APMAC;}

	public void set_APESSID(String str) {this.APESSID = str;}
	public String get_APESSID() {return this.APESSID;}

	public void set_APChannel(String str) {this.APChannel = str;}
	public String get_APChannel() {return this.APChannel;}
		
	public void set_APMode(String str) {this.APMode = str;}
	public String get_APMode() {return this.APMode;}
		
	public void set_APSignalLevel(String str) {this.APSignalLevel = str;}
	public String get_APSignalLevel() {return this.APSignalLevel;}

}