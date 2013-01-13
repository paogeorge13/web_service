package adder;

import context.*;
import java.io.IOException;
import java.util.Properties;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AdderWebService")
public class AdderWebService {

	private CacheMemory memory = CacheMemory.getInstance();
	private Database db = Database.getInstance();
	private Thread deletionThread;
	private Runnable deletionRun;
	private Thread updateDbThread;
	private Runnable updateDbRun;

	public AdderWebService() {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("web_service.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		deletionRun = new DeletionThread(Integer.parseInt(prop.getProperty("T")));
		deletionThread = new Thread(deletionRun);
		deletionThread.start();

		updateDbRun = new UpdateDbThread();
		updateDbThread = new Thread(updateDbRun);
		updateDbThread.start();

		//TODO: Close property file
		//maybe call. DatabaseCreate() and then the below functions
		// TODO prwta drop
		db.dropTables();
		db.createTables();
//		db.dropTables();
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
		 memory.updateDb();

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
//		db.deleteADevice("green-tower");
		db.close();
	}

	private void infoOf(String device, String interf) {

		if (memory.hasLatestInfoOf(device)) {
			memory.getInfoOf(device, interf);
		} else {
//			db.getInfoOf(device, interf);
		}
	}
}
