package javaapp;

import javax.xml.ws.Endpoint;

public class JavaApp {

	public static void main(String[] args) {
		 Endpoint.publish("http://"+  "0.0.0.0"+ ":" +  9999 +"/WebApp/AdderWebService/", new adder.AdderWebService()); 
	}
}
