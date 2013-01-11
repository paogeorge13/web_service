import interfaces.AccessPoint;
import java.util.ArrayList;
import java.util.Iterator;

public class ThreadAccessPoint implements Runnable{
	
	private Data data;
	private Operations operations = new Operations();
	private String iwlist;
	private String name;
	private String[] AP;
	private AccessPoint[] node;
	private PropertyFileData prop;
	private int currentNumOfAccessPoints;
	private int compareNumOfAccessPoints;
	private int c , k , sleep_time , newSleep_time;
	private ArrayList<Integer> markovList;
	private long T;	
	private int loop;


	public ThreadAccessPoint(Data data, String name, PropertyFileData prop) {
		this.data = data;	
		this.name = name;	
		this.prop = prop;
	}
	
	public int AccessPointInfo() {
		iwlist = operations.Scan("iwlist " + this.name + " scan");			//TODO sudo -s
		
		/* situation of iwlist (wlan? scan) == null
		 * a thread is running but the wireless interface has been removed
		 * however monitor hasn't forestalled to interrupt this thread
		 */
		if (iwlist != null && !iwlist.isEmpty()) {
			AP = iwlist.split("Cell");	
			node = new AccessPoint[AP.length];
			for (int i = 0 ; i < AP.length - 1 ; i++) {				//TODO VRISKEI PADA ENA MONO ACCESS POINT.
				
				/* parameters parsing for each access point*/
				node[i] = new AccessPoint();
				node[i].set_APMAC(operations.Info(AP[i+1], "Address", 9, "/n"));
				node[i].set_APESSID(operations.Info(AP[i+1], "ESSID", 6, "/n"));
				node[i].set_APChannel(operations.Info(AP[i+1], "Channel", 8 , "/n"));
				node[i].set_APMode(operations.Info(AP[i+1], "Mode", 5, "/n"));
				node[i].set_APSignalLevel(operations.Info(AP[i+1], "Signal level", 13, " "));	
				
				/* New access point insertion in the list if list is empty 
				 * or that access point doesn't exist in the list
				 */
				if (data.NoAccessPoint() || !data.AccessPointExists(node[i])) 
					data.addAccessPoint(node[i]);
				
				/* else if that access point already exists in the list
				 * check if there is any parameter update 
				 */
				else data.check_and_updateAccessPoint(node[i].get_APESSID(), node[i]);
			}	
		}
		return data.NumOfAccessPoints();
	}
	
	

	public void run() {

		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO WirelessEnvironmentScanner - Starting scanning for APs - " + name);

		k = Integer.parseInt(prop.get_propertyk());
		c = Integer.parseInt(prop.get_propertyc());
		sleep_time = Integer.parseInt(prop.get_propertysleep_time());
		newSleep_time = sleep_time;
		compareNumOfAccessPoints = 0;
		markovList = new ArrayList<Integer>();

		for (int i = 1; i != k+1; ++i) {
			for (int j = 0; j != c; ++j) {
				markovList.add(new Integer(i * sleep_time));	
			}
		}
		
		Iterator it = markovList.iterator();

		loop = 0;
		for ( ;  ; ) {
			loop++;
			try {
				long time1 = System.currentTimeMillis();
				currentNumOfAccessPoints = AccessPointInfo();
				T = System.currentTimeMillis() - time1;
				/* in case of we had a successful resumption return to Sk state */
				/* check mailing list... */
				if (currentNumOfAccessPoints != compareNumOfAccessPoints) {
					compareNumOfAccessPoints = currentNumOfAccessPoints;
					newSleep_time = sleep_time;
					it = markovList.iterator();
				}
				/* otherwise go to S(i-1) state */
				else
				{
					if (c == 1) {
						/* if we haven't reached state S(1) */
						if ( newSleep_time < (k * sleep_time) ) {
							newSleep_time += sleep_time;						
						}
						/* otherwise we are already at state S(1) so do nothing */	
					}
					else {
						if (it.hasNext()) {
							newSleep_time = (int) it.next();
						}
					}
				}				
				if ( data.sys1 == false ) {
					System.out.println(this.name + " is scanning for Access Points..");
				}
				Thread.currentThread().sleep(newSleep_time - T);	
			} catch (InterruptedException e) {
				System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO WirelessEnvironmentScanner - " + name + " Stopping scanning");

				break;
			}	
			
			System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO WirelessEnvironmentScanner - " + name + " -> State is " + newSleep_time/sleep_time + ". Internal Loop is: " + loop + ". Timeout is: " + (newSleep_time-T) + "ms");

		}	
	}
	

}