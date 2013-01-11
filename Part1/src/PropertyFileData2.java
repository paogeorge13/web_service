
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileData2 {

	private String MaxTransfer;
	private String c;
	private String sleep_time;
	private String k;
	private String x;
	private String Driver;
	private String URL;
	private String username;
	private String password;

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

	public String get_propertyDriver() {
		return this.Driver;
	}

	public String get_propertyURL() {
		return this.URL;
	}

	public String get_propertyUsername() {
		return this.username;
	}

	public String get_propertyPassword() {
		return this.password;
	}

	public PropertyFileData2(String file, String oper) {
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream(file);
		try {
			prop.load(in);
			switch (oper) {
				case "database":
					this.Driver = prop.getProperty("Driver");
					this.URL = prop.getProperty("URL");
					this.username = prop.getProperty("username");
					this.password = prop.getProperty("password");

					break;
				case "input":
					this.MaxTransfer = prop.getProperty("MaxTransfer");
					this.c = prop.getProperty("c");
					this.sleep_time = prop.getProperty("sleep_time");
					this.k = prop.getProperty("k");
					this.x = prop.getProperty("x");

					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} /* Close propery */ finally {
			try {
				in.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}
	}
}
