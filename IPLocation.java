
import java.net.*;
import java.io.*;

public class IPLocation {

	static String getCity(String ipaddress) throws Exception {
		//pass empty string for localhost ip address
		String host = "https://ipinfo.io";
		if(ipaddress.length() != 0) host+="/"+ipaddress;
		host +="/city?token="+PrivateData.IPINFO_TOKEN;
		URL url = new URL(host);
		return getCity(url);
	}
	
	static String getCity(URL url) throws Exception {
		URLConnection con = url.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String line;
		//while((line = in.readLine()) != null) {
			//System.out.println("'"+line+"$");
		//}
		line = in.readLine();
		in.close();
		return line;
	}

	public static void main(String args[]) throws Exception {
		if(args.length < 1) {
			System.out.println("Enter an IP address as argument.");
			return;
		}


		System.out.println(IPLocation.getCity(args[0]));
	}
}
