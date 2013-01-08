package adder;

import context.*;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AdderWebService")
public class AdderWebService {


	public AdderWebService() {
		//maybe call. DatabaseCreate() and then the below functions
		// TODO prwta drop
		Database.getInstance().dropTable("");
		Database.getInstance().dropTable("");
		Database.getInstance().dropTable("");
		Database.getInstance().dropTable("");
		// TODO create tables in database
		Database.getInstance().createTable("", "");
		Database.getInstance().createTable("", "");
		Database.getInstance().createTable("", "");
		Database.getInstance().createTable("", "");

		//TODO thread for deletion
	}


	

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "setMonitorData")
	@Oneway
	public void setMonitorData(@WebParam(name = "device") String device, @WebParam(name = "md") MonitorData md) {
		System.out.println("Device name: " + device);

		CacheMemory.getInstance().updateMem(device, md);
		//Database.getInstance().selectADevice(id);
	}
}
