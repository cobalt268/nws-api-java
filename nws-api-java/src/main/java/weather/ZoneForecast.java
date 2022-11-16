package weather;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

public class ZoneForecast extends Forecast {

	private ZoneForecast(JsonObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}
	
	public ZoneForecast(String zone) throws MalformedURLException {
		this(requestJson(new URL(String.format("https://api.weather.gov/zones/forecast/%s/forecast", zone))));
	}
	
	protected ZoneForecastPeriod createPeriod(JsonObject obj) {
		return new ZoneForecastPeriod(obj);
	}
}
