package javaapp;

import adder.CacheMemory;
import grafiko.GUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Endpoint;

public class JavaApp {

	public static void main(String[] args) {
		 Endpoint.publish("http://"+  "0.0.0.0"+ ":" +  9999 +"/WebApp/AdderWebService", new adder.AdderWebService()); 

		 GUI gui = new GUI(CacheMemory.getInstance());

		 Thread guiThread = new Thread(gui);
		 guiThread.start();

		try {
			guiThread.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			Logger.getLogger(JavaApp.class.getName()).log(Level.SEVERE, null, ex);
		}
		 
	}
}
