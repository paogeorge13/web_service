
import interfaces.WirelessInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor implements Runnable {

	private int numberOfInterfaces, numberOfWireless;
	private int i, j, index;
	private String name;
	private String[] IfconfigSplit;
	private WirelessInterface[] node;
	private Data data;
	private Operations operations;
	private PropertyFileData prop;
	private HashMap<String, Thread> threadMap;
	private Runnable r1;
	private Thread t1;
	private boolean apScanner;		// if there is a wlan scanning for AP (access points) 
	private boolean scanAndUpd;		// the above wlan is also connected to an AP
	private Runnable rScanner;
	private Runnable rAdder;
	private Thread tScanner;
	private Thread adder;
	private String apScannerInt = "";
	private ArrayList<Integer> markovList;
	private int compareNumOfInterfaces;
	private int currentNumOfInterfaces;
	private int c, k, sleep_time, newSleep_time;
	private long T;
	private int loop;

	/*
	 Monitor(PropertyFileData prop) {
	 this.prop = prop;
	 this.data = new Data(prop);
	 }*/
	public Monitor(PropertyFileData prop) {
		this.prop = prop;
		data = Data.getInstance();
		data.set_StartTime(System.currentTimeMillis());
		operations = new Operations();
		threadMap = new HashMap<String, Thread>();
		apScanner = false;
		scanAndUpd = false;


	}

	public void CheckForInsertion(WirelessInterface[] interf, int numberOfInterfaces) {
		for (i = 0; i < numberOfInterfaces; i++) {

			/*
			 * New interface insertion in list with wired 
			 * interfaces if it is wired and doesn't exist in the list
			 */
			if (!data.WiredExists(interf[i]) && !interf[i].isWireless()) {
				insert_Interface();
				data.addWired(interf[i]);
				r1 = new ThreadInterface(data, interf[i].get_InterfaceName(), prop);
				t1 = new Thread(r1);
				threadMap.put(interf[i].get_InterfaceName(), t1);
				t1.start();
			} /*
			 * New interface insertion in list with wireless interfaces
			 * if it is wireless and doesn't exist in the list
			 * (3 cases) 
			 */ else if (!data.WirelessExists(interf[i]) && interf[i].isWireless()) {
				insert_Interface();
				data.addWireless(interf[i]);


				/* 1st case
				 * There is no wireless interface scanning for access points.
				 * That means that the list with wireless interfaces is empty
				 * and the new interface has to scan for access points
				 * and check for its information updates.
				 */
				if (!apScanner) {
					r1 = new ThreadInterface(data, interf[i].get_InterfaceName(), prop);
					t1 = new Thread(r1);
					threadMap.put(interf[i].get_InterfaceName(), t1);
					t1.start();

					rScanner = new ThreadAccessPoint(data, interf[i].get_InterfaceName(), prop);
					tScanner = new Thread(rScanner);
					tScanner.start();

					apScanner = true;
					apScannerInt = interf[i].get_InterfaceName();
					if (data.NumOfWireless() == 1) {
						scanAndUpd = true;
					}
					System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " ERROR Monitor - Found 1 wireless interfaces. Successful execution requires at least 2. Execution will continue yet results are not guaranteed.");
				} /* 2nd case
				 * There is only one wireless interface which is scanning and is connected
				 * to an access point and the new wlan is not associated with any AP
				 * The new wlan will be scanning for access points
				 */ /*
				 * Answer from list
				 * Nai. Genika pantws (kai afto paei gia oles tis erwthseis sou) emeis
				 * ypothetoume oti xekinaei to programma kai den eisai syndedemenos pouthena
				 * (exou kai to tyxaia sthn ekfwnhsh).
				 */ else if (scanAndUpd) {
					/* first stop the Scanning_for_AccessPoint Thread of previous interface */
					try {
						tScanner.join(1000);
						tScanner.interrupt();
						tScanner.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO Monitor - Found a 2nd wireless interface. Successful execution required at least 2. Execution will continue without any problem.");

					/* create a new Scanning_for_AccessPoint Thread for the new wireless interface */
					rScanner = new ThreadAccessPoint(data, interf[i].get_InterfaceName(), prop);
					tScanner = new Thread(rScanner);
					tScanner.start();

					apScannerInt = interf[i].get_InterfaceName();
					scanAndUpd = false; 	// there are is no associated wireless interface for scanning APs

				} /* in any other case, just create and start a thread, only
				 * for checking interface's information
				 */ else {
					r1 = new ThreadInterface(data, interf[i].get_InterfaceName(), prop);
					t1 = new Thread(r1);
					threadMap.put(interf[i].get_InterfaceName(), t1);
					t1.start();
				}
			}
		}
	}

	public void CheckForDeletion(WirelessInterface[] interf, int numberOfInterfaces) {
		/*
		 * Check if a wired interface hasn't be found during the last scan of interfaces.
		 * In that case stop the appropriate thread and remove that interface from
		 * the list with wired interfaces.
		 */
		for (i = 0; i < data.NumOfWired(); i++) {
			for (j = 0; j < numberOfInterfaces; j++) {
				if (data.getWired(i).get_InterfaceName().equals(interf[j].get_InterfaceName())) {
					break;
				}
			}
			if (j == numberOfInterfaces) {
				remove_Interface();
				String toBeRemoved = data.getWired(i).get_InterfaceName();
				data.removeWired(i);
				try {
					threadMap.get(toBeRemoved).interrupt();
					threadMap.get(toBeRemoved).join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				threadMap.remove(toBeRemoved);
			}
		}
		/*
		 * Check if a wireless interface hasn't be found during the last scan of interfaces.
		 * In that case stop the appropriate thread and remove that interface from
		 * the list with wired interfaces.
		 */
		for (i = 0; i < data.NumOfWireless(); i++) {
			for (j = 0; j < numberOfInterfaces; j++) {
				if (data.getWireless(i).get_InterfaceName().equals(interf[j].get_InterfaceName())) {
					break;
				}
			}
			if (j == numberOfInterfaces) {
				remove_Interface();
				String toBeRemoved = data.getWireless(i).get_InterfaceName();

				/* In any case delete this wireless interface from data list */
				data.removeWireless(i);

				/* 1st case
				 * We are going to delete an AccessPoint Scanner, but
				 * this is the only wireless interface
				 */
				if (data.NumOfWireless() == 0) {
					if (data.sys1 == false) {
						System.out.println("Attention, there are no any wireless interfaces so on...");
					}

					/* interrupt the scanner thread... */
					try {
						tScanner.interrupt();
						tScanner.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					apScanner = false;		// from now on, there is no AP Scanner...
					apScannerInt = "";
					System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " ERROR Monitor - There is not any wireless interface. Successful execution requires at least 2. Execution will continue yet results are not guaranteed.");

					/* in this case empty the list with all the access points */
					data.clearAccessPointList();

				} /* 2nd case
				 * We are going to delete an AccessPoint Scanner, but there are
				 * more wireless interfaces
				 */ else if (toBeRemoved.equals(apScannerInt)) {
					System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " ERROR Monitor - A wireless environment scanner has been stopped.");


					/* interrupt the scanner thread... */
					try {
						tScanner.interrupt();
						tScanner.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					apScanner = false;		// from now on, there is not any AP Scanner...
					apScannerInt = "";
					forceFindNewScanner();	// find new AP Scanner
				}

				/* 
				 * We are going to delete the thread that collects wireless 
				 * interface's information 
				 */
				if (threadMap.get(toBeRemoved) != null) {
					/* Get here only when we remove a wireless interface which doesn't scan
					 * or a wireless interface which was scanning and was associated
					 */
					try {
						threadMap.get(toBeRemoved).interrupt();
						threadMap.get(toBeRemoved).join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					threadMap.remove(toBeRemoved);
				}
			}
		}

	}

	private void insert_Interface() {
		if (data.sys1 == false) {
			System.out.println("inserted");
		}
	}

	private void remove_Interface() {
		if (data.sys1 == false) {
			System.out.println("removed");
		}
		if (data.sys1 == false) {
			System.out.println("Stoping monitor for interface : " + data.getWired(i).get_InterfaceName());
		}
	}

	private void forceFindNewScanner() {
		int i;
		/*
		 * choose the first wireless interface, 
		 * which is not associated, for scanning
		 */
		for (i = 0; i != data.NumOfWireless(); ++i) {
			if (!(operations.Scan("iwconfig " + data.getWireless(i).get_InterfaceName()).contains("Frequency"))) {
				rScanner = new ThreadAccessPoint(data, data.getWireless(i).get_InterfaceName(), prop);
				tScanner = new Thread(rScanner);
				tScanner.start();

				apScanner = true;
				apScannerInt = data.getWireless(i).get_InterfaceName();
				scanAndUpd = false;
				break;
			}
		}
		/* 
		 * otherwise force a wireless interface for scanning
		 * and print an attention and a no guarantee message
		 */
		if (i == data.NumOfWireless()) {
			rScanner = new ThreadAccessPoint(data, data.getWireless(0).get_InterfaceName(), prop);
			tScanner = new Thread(rScanner);
			tScanner.start();

			apScanner = true;
			apScannerInt = data.getWireless(0).get_InterfaceName();
			scanAndUpd = true;

			printAttention();
		}
		if (data.sys1 == false) {
			System.out.println("found");
		}
	}

	public int InterfaceScan(String command) {
		/* scan for new interfaces*/
		IfconfigSplit = operations.Scan("ifconfig").split("/n/n");
		numberOfInterfaces = IfconfigSplit.length;
		node = new WirelessInterface[numberOfInterfaces];
		for (i = 0; i < numberOfInterfaces; i++) {
			index = IfconfigSplit[i].indexOf(" ");
			name = IfconfigSplit[i].substring(0, index);
			node[i] = new WirelessInterface(name);

			/* check if a specific interface is wired or wireless*/
			if (operations.Scan("iwconfig " + node[i].get_InterfaceName()).contains("Access Point")) {
				node[i].isWireless(true);
			} else {
				node[i].isWireless(false);
			}
		}
		/* insert and delete interfaces if it is necessary*/
		CheckForInsertion(node, numberOfInterfaces);
		CheckForDeletion(node, numberOfInterfaces);
		return (data.NumOfWired() + data.NumOfWireless());
	}

	/* prints the map with the threads */
	public void printThreadMap() {
		Iterator it = threadMap.entrySet().iterator();
		System.out.println("--- Map begin ---");
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("--- Map end ---");
		System.out.println("");
	}

	private void printAttention() {
		//System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO Monitor" );

		if (data.sys1 == false) {
			System.out.println("attention");
		}
	}

	public void run() {
		rAdder = new Adder(data);
		adder = new Thread(rAdder);
		adder.start();

		k = Integer.parseInt(prop.get_propertyk());
		c = Integer.parseInt(prop.get_propertyc());
		sleep_time = Integer.parseInt(prop.get_propertysleep_time());
		newSleep_time = sleep_time;
		compareNumOfInterfaces = 0;

		markovList = new ArrayList<Integer>();
		for (int i = 1; i != k + 1; ++i) {
			for (int j = 0; j != c; ++j) {
				markovList.add(new Integer(i * sleep_time));
				//System.out.println(Thread.currentThread().getName() + i*sleep_time);
			}
		}

		Iterator it = markovList.iterator();

		loop = 0;
		while ("the truth about forever is that it is happening right now" != null) {
			loop++;
			try {
				long time1 = System.currentTimeMillis();
				currentNumOfInterfaces = InterfaceScan(operations.Scan("ifconfig"));
				T = System.currentTimeMillis() - time1;
				/* in case of we had a successful resumption return to Sk state */
				if (currentNumOfInterfaces != compareNumOfInterfaces) {
					compareNumOfInterfaces = currentNumOfInterfaces;
					newSleep_time = sleep_time;
					it = markovList.iterator();
				} /* otherwise go to S(i-1) state */ else {
					if (c == 1) {
						/* if we haven't reached state S(1) */
						if (newSleep_time < (k * sleep_time)) {
							newSleep_time += sleep_time;
						}
					} else {
						if (it.hasNext()) {
							newSleep_time = (int) it.next();
						}
					}
					/* otherwise we are already at state S(1) so do nothing */
				}
				Thread.currentThread().sleep(newSleep_time);
			} catch (InterruptedException e1) {

				/* stop every thread of thread map */
				for (Thread t : threadMap.values()) {
					t.interrupt();
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				/* stop the adder */
				try {
					adder.interrupt();
					adder.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
				}

				/* also stop the wireless environment scanner thread */
				tScanner.interrupt();
				try {
					tScanner.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				/* empty hash map */
				it = threadMap.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					it.remove();
				}

				/* empty any lists */
				data.clearWiredInterfacesList();
				data.clearWirelessInterfacesList();
				data.clearAccessPointList();
				System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO AvailableInterfacesMonitor - Stopping for scanning available interfaces");

				break;
			}

			if (data.sys1 == false) {
				printThreadMap();
				data.printWiredInterfaces();
				data.printWirelessInterfaces();
				data.printAccessPoints();
			}
			if (data.sys1 == false) {
				System.out.println("-------------------------------------");
			}
			System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO AvailableInterfacesMonitor - State is " + newSleep_time / sleep_time + ". Internal Loop is: " + loop + ". Timeout is: " + (newSleep_time - T) + "ms");

		}
	}
}