package adder;

import context.Database;
import java.io.IOException;
import java.util.Properties;

public class AW {

	private static AW instance = null;
	private CacheMemory memory = CacheMemory.getInstance();
	private Database db = Database.getInstance();
	private Thread deletionThread;
	private Runnable deletionRun;
	private Thread updateDbThread;
	private Runnable updateDbRun;

	public AW() {

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
		db.dropTables();
		db.createTables();
	}

	public static synchronized AW getInstance() {
		if (instance == null) {
			instance = new AW();
		}
		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void set(String device, context.MonitorData md) {
		memory.update(device, md);
		memory.printMemory();
		System.out.println("8****************begin*****************************************999999999999999");
		memory.updateDb();
		System.out.println("8*****************end****************************************999999999999999");
	}
}
