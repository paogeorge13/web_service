package adder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateDbThread implements Runnable {
	CacheMemory mem = CacheMemory.getInstance();

	public UpdateDbThread() {
	}


	@Override
	public void run() {
		System.out.println("Update database thread begun running...");
		try {
			Thread.sleep(6000);
			mem.updateDb();
		} catch (InterruptedException ex) {
			Logger.getLogger(UpdateDbThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
