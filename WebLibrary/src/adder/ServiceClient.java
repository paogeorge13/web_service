package adder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceClient {

	private static ServiceClient instance = null;

	public static ServiceClient getInstance() {
		if (instance == null) {
			instance = new ServiceClient();
		}
		return instance;
	}

	public static void invokeSetMonitorData(java.lang.String device, adder.MonitorData md, String IP, int PORT) {
		adder.AdderWebService_Service service = null;

		try {
			service = new adder.AdderWebService_Service(new URL("http://" + IP + ":" + PORT + "/WebApp/AdderWebService"));
			adder.AdderWebService port = service.getAdderWebServicePort();
			port.setMonitorData(device, md);
		} catch (MalformedURLException ex) {
			System.err.println("Malformed URL Exception");
			Logger.getLogger(ServiceClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception e) {
			System.err.println("Unreachable service");
		}
	}
}
