
import java.io.*;
import java.net.*;
import com.google.gson.Gson;

public class Weather {

	String name;
	Coord coord;
	WeatherC []weather;
	String base;
	TempInfo main;
	Wind wind;
	Cloud clouds;

	private Weather() {;
	}

	public static Weather fromCity(String city) throws Exception{

		URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+PrivateData.OPENWEATHER_KEY);
		return Weather.getWeather(url);
	}

	public static Weather getWeather(URL url) throws Exception{
		String rawdata;
		URLConnection con = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

		StringBuilder data = new StringBuilder("");
		String line;
		while((line = br.readLine()) != null) {
			data.append(line);
		}

		br.close();
//		con.close();

		rawdata = data.toString();

		Gson g = new Gson();

		return g.fromJson(rawdata, Weather.class);
	}

	String getIconURL() {
		return "http://openweathermap.org/img/w/"+weather[0].icon+".png";
	}

	public static void main(String args[]) throws Exception{
/*		if(args.length < 1) {
			System.out.println("Enter a city as an argument");
			return;
		}*/
		Weather w = Weather.fromCity("Pune");

		System.out.println(w.getIconURL());

	}
}

class Coord {
	float lon;
	float lat;
}

class WeatherC {
	int id;
	String main;
	String description;
	String icon;
}

class TempInfo {
	float temp;
	float pressure;
	float humidity;
	float temp_min;
	float temp_max;
}

class Wind {
	float speed;
	int deg;
}

class Cloud {
	int all;
}
