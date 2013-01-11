import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Operations {
	
	private String ResultString;
	private String line;
	private String[] returnString;
	private int index;
	private InputStream inputstream;
	private InputStreamReader inputstreamreader;
	private BufferedReader bufferedreader;

	
	/* 
	 * Scan with parameter String.
	 * It runs the command and captures and returns its output. 
	 * This function is used for ifconfig, iwconfig and iwlist.
	 */
	public String Scan(String command) {
		boolean flag = false;
		ResultString = "";
		line = "";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			inputstream = process.getInputStream();
			flag = true;
			inputstreamreader = new InputStreamReader(inputstream);
			bufferedreader = new BufferedReader(inputstreamreader);
			while ((line = bufferedreader.readLine()) != null) ResultString += (line+"/n");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
	//			if (flag) {
					inputstream.close();
			//	}
			} 
			catch (IOException ioex) {
			}
		}
		return ResultString;
	}
	
	
	
	/* 
	 * Scan with parameter String[].
	 * It runs the command and captures and returns its output. 
	 * This function is used for proc/net/dec and proc/net/wireless.
	 */
	public String Scan(String[] command) {
		ResultString = "";
		line = "";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			inputstream = process.getInputStream();
			inputstreamreader = new InputStreamReader(inputstream);
			bufferedreader = new BufferedReader(inputstreamreader);
			while ((line = bufferedreader.readLine()) != null) ResultString += (line);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
				inputstream.close();
			} 
			catch (IOException ioex) {
			}
		}
		return ResultString;
	}
	
	
	
	/* 
	 * This function takes a string and splits 
	 * it around matches of the given regular expression splitChar.
	 * It returns a string which in our case is an Interface
	 * or Access Point information
	 */
	public String Info(String text, String str, int charNum, String splitChar) { 
		returnString = null;
		index = text.indexOf(str);
		returnString = text.substring(index+charNum).split(splitChar);
		return returnString[0];
	}
	
	
}
