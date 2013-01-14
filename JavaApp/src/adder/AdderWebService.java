package adder;

import context.*;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AdderWebService")
public class AdderWebService {

	private AW aw = AW.getInstance();

	public AdderWebService() {
		System.out.println("AdderWebService: I am going to drop and create tables.");
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "setMonitorData")
	@Oneway
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
		aw.set(device, md);
		
		/*
		for (int i = 0; i != md.getListA().size(); ++i) {
			db.insertADevice(device, md.getListA().get(i));
		}
		System.out.println("---------------------------" + md.getListB().size());
		for (int i = 0; i != md.getListB().size(); ++i) {
			db.insertADevice(device, md.getListB().get(i));
		}
		for (int i = 0; i != md.getListC().size(); ++i) {
			db.insertADevice(device, md.getListC().get(i));
		}
		*/
//		Database.getInstance().deleteADevice("ubuntu");
//		db.close();
	}

}
