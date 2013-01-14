
import java.util.logging.Level;
import java.util.logging.Logger;


public class Adder implements Runnable {

	private Operations oper;
	private Data data;
	private String device;
	private String resultstring;
	private String[] split;
	private adder.MonitorData md;
	private adder.WiredInterface[] w;
	private adder.WirelessInterface[] wl;
	private adder.AccessPoint[] ap;

	public Adder(Data data) {
		this.data = data;
		oper = new Operations();
		md = new adder.MonitorData();
	}

	public void run() {
		while ("the truth about forever is that it is hapenning right now" != null) {

			try {
				System.out.println("I am sleeping");
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			/* clear the list - includes previous info */
			md.getListA().clear();
			md.getListB().clear();
			md.getListC().clear();

			// TODO: to allaxe o vasilis afto

			w = new adder.WiredInterface[data.getListA().size()];
			wl = new adder.WirelessInterface[data.getListB().size()];
			ap = new adder.AccessPoint[data.getListC().size()];

			/* copy every item of wired list */
			for (int i = 0; i != data.getListA().size(); ++i) {
				/* new adder.node */
				w[i] = new adder.WiredInterface();

				/* copy the node to the webService class */
				copyWiredInterface(w[i], data.getListA().get(i));
				md.getListA().add(w[i]);
			}
			for (int i = 0; i != data.getListB().size(); ++i) {
				/* new adder.node */
				wl[i] = new adder.WirelessInterface();

				/* copy the node to the webService class */
				copyWirelessInterface(wl[i], data.getListB().get(i));
				md.getListB().add(wl[i]);
			}
			for (int i = 0; i != data.getListC().size(); ++i) {
				/* new adder.node */
				ap[i] = new adder.AccessPoint();

				/* copy the node to the webService class */
				copyAccessPoint(ap[i], data.getListC().get(i));
				md.getListC().add(ap[i]);
			}

			/* invoke web service */
			resultstring = oper.Scan("hostname");
			split = resultstring.split("/n");
			device = split[0];

			// these have to be read from property file!
//			adder.ServiceClient.getInstance().invokeSetMonitorData(device, md, "127.0.0.1", 8080);
			adder.ServiceClient.getInstance().invokeSetMonitorData(device, md, "0.0.0.0", 9999);
			System.out.println("I sent the data");
			try {
				System.out.println("I am goint to send a new data in 10 sec...");
				Thread.sleep(9000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Adder.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
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

	private void copyWirelessInterface(adder.WirelessInterface newW, interfaces.WirelessInterface w) {
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
		newW.setBaseStationMAC(w.get_BaseStationMAC());
		newW.setESSID(w.get_ESSID());
		newW.setChannel(w.get_Channel());
		newW.setMode(w.get_Mode());
		newW.setTransmitPower(w.get_TransmitPower());
		newW.setLinkQuality(w.get_LinkQuality());
		newW.setSignalLevel(w.get_SignalLevel());
		newW.setNoisePower(w.get_NoisePower());
		newW.setDiscardedPackets(w.get_DiscardedPackets());
	}

	private void copyAccessPoint(adder.AccessPoint newAP, interfaces.AccessPoint ap) {
		newAP.setAPChannel(ap.get_APChannel());
		newAP.setAPESSID(ap.get_APESSID());
		newAP.setAPMAC(ap.get_APMAC());
		newAP.setAPMode(ap.get_APMode());
		newAP.setAPSignalLevel(ap.get_APSignalLevel());
	}

}
