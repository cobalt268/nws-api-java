package weather;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * A wrapper for the GridpointForecast schema of the NWS API
 * 
 * @author cobalt
 *
 */
public class GridpointForecast {
	JsonObject forecast;

	public GridpointForecast(JsonObject forecast) {
		this.forecast = forecast;
	}

	/**
	 * 
	 * @param wfo The forecast field office
	 * @param x   The x coordinate of the forecast grid
	 * @param y   The y coordinate of the forecast grid
	 * @return The gridpoint forecast for the given field office and grid
	 *         coordinates
	 */
	public static GridpointForecast fromGridpoint(Office wfo, double x, double y) {
		// TODO connect with nws api
		return null;
	}
	
	private JsonObject getPeriod(int period) {
		return (JsonObject) ((JsonObject) forecast.get("properties")).get(Integer.toString(period));
	}
	
	/**
	 * Returns the temperature in the units specified by the forecast.
	 * @return
	 */
	public double getTemperature() {
		//TODO
		return -1;
	}
}
