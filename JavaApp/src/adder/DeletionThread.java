/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeletionThread implements Runnable {
	private int time;
	private CacheMemory mem = CacheMemory.getInstance();

	public DeletionThread(int time) {
		this.time = time;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Logger.getLogger(UpdateDbThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
