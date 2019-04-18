
public class Weather {

	//Document doc; //parsed XML stored here
	String rawdata;

	Weather(String city) {
		URL url = new URL("api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+PrivateData.OPENWEATHER_KEY);
		URLConnection con = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

		StringBuilder data = new StringBuilder("");
		String line;
		while((line = br.readLine()) != null) {
			data.append(line);
		}

		br.close();
		con.close();

		rawdata = data.toString();
	}



	public static void main(String args[]) {
		;
	}
}
