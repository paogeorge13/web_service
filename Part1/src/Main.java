
public class Main {
	
	public static void main(String[] args) {	
		PropertyFileData prop = new PropertyFileData("input.properties");

		final Thread monitor = new Thread(new Monitor(prop));	
		monitor.start();
		
		Data data = Data.getInstance();
		
		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO interfaces.Starter - Monitor is going to be initialized with the following properties:");
		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO interfaces.Starter - States: " + prop.get_propertyk());
		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO interfaces.Starter - TIMEOUT: " + prop.get_propertysleep_time());
		System.out.println(System.currentTimeMillis() - data.get_StartTime() + " [" + Thread.currentThread().getName() + "]" + " INFO interfaces.Starter - LOG_FILE: " + "input.properties");
	
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	monitor.interrupt();
				try {
					monitor.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		});
	}
}
	