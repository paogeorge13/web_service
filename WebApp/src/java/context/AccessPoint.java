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
		
	public String get_APMAC() {return this.APMAC;}

	public String get_APESSID() {return this.APESSID;}

	public String get_APChannel() {return this.APChannel;}
		
	public String get_APMode() {return this.APMode;}
		
	public String get_APSignalLevel() {return this.APSignalLevel;}
}