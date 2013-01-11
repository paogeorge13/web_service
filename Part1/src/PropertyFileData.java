import java.io.IOException;
import java.util.Properties;

public class PropertyFileData {
	
	private String MaxTransfer;
	private String c;
	private String sleep_time;
	private String k;
	private String x;
	
	public String get_propertyMaxTransfer() {
		return this.MaxTransfer;
	}
	
	public String get_propertyc() {
		return this.c;
	}
	
	public String get_propertysleep_time() {
		return this.sleep_time;
	}
	
	public String get_propertyk() {
		return this.k;
	}
	
	public String get_propertyx() {
		return this.x;
	}

	
	PropertyFileData(String file) {
		Properties prop = new Properties(); 
		try {
			prop.load(getClass().getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.MaxTransfer = prop.getProperty("MaxTransfer");
		this.c = prop.getProperty("c");
		this.sleep_time = prop.getProperty("sleep_time");
		this.k = prop.getProperty("k");
		this.x = prop.getProperty("x");
		
	}
}