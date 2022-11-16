package weather;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * A wrapper for the ZonalForecast schema of the NWS API
 * 
 * @author cobalt268
 *
 */
public class ZoneForecast extends GeoJson {
	/**
	 * Builds a zone forecast from a zone code.
	 * 
	 * @param zone
	 * @throws MalformedURLException
	 */
	public ZoneForecast(String zone) throws MalformedURLException {
		String urlString = String.format("https://api.weather.gov/zones/forecast/%s/forecast", zone);
		setJson(requestJson(new URL(urlString)));
	}

	/**
	 * Returns an array of all periods for the zone forecast.
	 * 
	 * @return
	 */
	public JsonArray getForecasts() {
		return (JsonArray) getProperties().get("periods");
	}

	/**
	 * Returns the name of the given forecast period.
	 * 
	 * @param period
	 * @return
	 */
	public String getForecastName(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("name");
	}

	/**
	 * Returns the forecast text for the given period
	 * 
	 * @param period
	 * @return
	 */
	public String getForecastText(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("detailedForecast");
	}

	/**
	 * Returns the full forecast text for the zone forecast.
	 * 
	 * @return
	 */
	public String fullText() {
		String result = "";
		for (int i = 0; i < getForecasts().size(); i++) {
			result += getForecastName(i) + "\n\n" + getForecastText(i)
					+ "\n---------------------------------------------\n";
		}
		return result;
	}
}
