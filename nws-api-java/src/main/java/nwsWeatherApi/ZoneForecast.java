package nwsWeatherApi;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Wraps the ZoneForecastGeoJson schema of the NWS API.
 * 
 * @author cobalt
 *
 */
public class ZoneForecast extends Forecast {

	/**
	 * Constructs a ZoneForecast from a <b>valid</b> JSON of the ZoneForecastGeoJson
	 * schema of the NWS API.
	 * 
	 * @param obj
	 */
	public ZoneForecast(JsonObject obj) {
		super(obj);
	}

	/**
	 * Constructs a ZoneForecast from the zone's code.
	 * 
	 * @param zone
	 */
	public ZoneForecast(String zone) {
		this(requestJson(generateURL(zone)));
	}

	protected ZoneForecastPeriod createPeriod(JsonObject obj) {
		return new ZoneForecastPeriod(obj);
	}

	/**
	 * All URLs used in the {@link ZoneForecast(String zone)} constructor
	 * <b>should</b> be valid because they are build using enums and primitives.
	 * This allows us to not have to use a throws statement on the constructor.
	 * 
	 * @param zone
	 * @return
	 */
	private static URL generateURL(String zone) {
		try {
			return new URL(String.format("https://api.weather.gov/zones/forecast/%s/forecast", zone));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
