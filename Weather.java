
import java.io.*;
import java.net.*;
import com.google.gson.Gson;

public class Weather {

	String name; //city name
	Coord coord; //city coords
	WeatherC []weather; //weather data
//	String base;
	TempInfo main;
	Wind wind;
	Cloud clouds;
	String unit="metric"; //metric/imperial

	private Weather() {;
	}

	public static Weather fromCity(String city, String unit) throws Exception{

		URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + unit + "&APPID=" + PrivateData.OPENWEATHER_KEY);
		Weather w = Weather.getWeather(url);
		w.unit = unit;
		return w;
	}
	
	public static Weather fromCity(String city) throws Exception {
		URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city+ "&APPID=" + PrivateData.OPENWEATHER_KEY);
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
	
	String getWeatherInfo() {
	    return "<html>"+"Description: "+weather[0].description+"<br>"+
	        "Temperature: "+main.temp+getUnit("temp")+"<br/>"+
	        "Pressure: "+main.pressure+getUnit("pressure")+"<br/>"+
	        "Humidity: "+main.humidity+getUnit("humidity")+"<br/>"+
	        "Max temperature: "+main.temp_max+getUnit("temp")+"<br/>"+
	        "Min temperature: "+main.temp_min+getUnit("temp")+"<br/>"+
	        "Wind speed: "+wind.speed+getUnit("speed")
	        +"</html>";
	}
	
	String getUnit(String m) {
    	if(m=="pressure") return "hPa";
    	if(m=="humidity") return "%";
	    if(unit == "metric") {
    	    if(m == "temp") return "<sup>o</sup>C";
    	    if(m == "speed") return "meter/sec";
    	}
    	else if(unit == "imperial") {
    	    if(m == "temp") return "<sup>o</sup>F";
    	    if(m == "speed") return "miles/hour";
    	}
    	return "unit(s)";
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
	float deg;
}

class Cloud {
	int all;
}
