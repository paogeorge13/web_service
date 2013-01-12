package adder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateDbThread implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			Logger.getLogger(UpdateDbThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
