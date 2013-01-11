package adder;

import context.*;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AdderWebService")
public class AdderWebService {

	private CacheMemory memory = CacheMemory.getInstance();
	private Database db = Database.getInstance();

	public AdderWebService() {
		//maybe call. DatabaseCreate() and then the below functions
		// TODO prwta drop
		/*
		 Database.getInstance().dropTable("");
		 Database.getInstance().dropTable("");
		 Database.getInstance().dropTable("");
		 Database.getInstance().dropTable("");
		 // TODO create tables in database
		 Database.getInstance().createTable("", "");
		 Database.getInstance().createTable("", "");
		 Database.getInstance().createTable("", "");
		 Database.getInstance().createTable("", "");
		 */
		//TODO thread for deletion
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "setMonitorData")
	@Oneway
//	public void setMonitorData(@WebParam(name = "device") String device, @WebParam(name = "md") Data md) {
	public void setMonitorData(@WebParam(name = "device") String device, @WebParam(name = "md") MonitorData md) {

		System.out.println("setMonitorData function begins running...");
		System.out.println("I got the below info");
		System.out.println("Device name: " + device);

		System.out.println("size of md list: " + md.getListA().size());
		for (int i = 0; i != md.getListA().size(); ++i) {
			System.out.println("i: " + i);
			System.out.println("interface name: " + md.getListA().get(i).get_InterfaceName());
			System.out.println("interface ip: " + md.getListA().get(i).get_InterfaceIP());
			System.out.println("mac:  " + md.getListA().get(i).get_InterfaceMAC());
		}
		System.out.println("");
		System.out.println("--------------------------------------");
		
		memory.update(device, md);
		memory.printMemory();
	}

	private void infoOf(String device, String interf) {

		if (memory.hasLatestInfoOf(device)) {
			memory.getInfoOf(device, interf);
		} else {
			db.getInfoOf(device, interf);
		}
	}
}
