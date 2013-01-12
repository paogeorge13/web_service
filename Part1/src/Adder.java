

public class Adder implements Runnable {

	private Data data;
	private adder.MonitorData md;
	private adder.WiredInterface[] w;

	public Adder(Data data) {
		this.data = data;
		md = new adder.MonitorData();
	}

	public void run() {
//		while ("the truth about forever is that it is hapenning right now" != null) {

			try {
				System.out.println("I am sleeping");
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			/* clear the list - includes previous info */
			md.getListA().clear();
			md.getListB().clear();
			md.getListC().clear();

			// TODO: continue this implementation

			w = new adder.WiredInterface[data.getListA().size()];

			/* copy every item of the list */
			for (int i = 0; i != data.getListA().size(); ++i) {
				/* new adder.node */
				w[i] = new adder.WiredInterface();

				/* copy the node to the webService class */
				copyWiredInterface(w[i], data.getListA().get(i));
				md.getListA().add(w[i]);
			}

			/* invoke web service */
			setMonitorData("green-tower", md);
			System.out.println("I sent the data");
//		}
	}

	private void copyWiredInterface(adder.WiredInterface newW, interfaces.WiredInterface w) {
		newW.setInterfaceName(w.get_InterfaceName());
		newW.setInterfaceIP(w.get_InterfaceIP());
		System.out.println("ip given: " + w.get_InterfaceIP());
		newW.setInterfaceMAC(w.get_InterfaceMAC());
		newW.setBcast(w.get_Bcast());
		newW.setConsumedRate(w.get_ConsumedRate());
		newW.setCurrentTransfer(w.get_CurrentTransfer());
		newW.setDefaultGetway(w.get_DefaultGetway());
		newW.setInterfaceMask(w.get_InterfaceMask());
		newW.setMaxTransfer(w.get_MaxTransfer());
		newW.setNetworkAddress(w.get_NetworkAddress());
		newW.setPacketError(w.get_PacketError());
	}

	private static void setMonitorData(java.lang.String device, adder.MonitorData md) {
		adder.AdderWebService_Service service = new adder.AdderWebService_Service();
		adder.AdderWebService port = service.getAdderWebServicePort();
		port.setMonitorData(device, md);
	}
}
