package weather;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * A wrapper for the GridpointForecast schema of the NWS API
 * 
 * @author cobalt
 *
 */
public class GridpointForecast extends GeoJson {
	/**
	 * Creates a gridpoint forecast from the WFO the point's X and Y positions on
	 * the WFO's grid.
	 * 
	 * @param wfo
	 * @param x
	 * @param y
	 * @throws IOException
	 */
	public GridpointForecast(Office wfo, int x, int y) throws MalformedURLException {
		String urlString = String.format("https://api.weather.gov/gridpoints/%s/%d,%d/forecast", wfo.name(), x, y);
		setJson(requestJson(new URL(urlString)));
	}

	/**
	 * Returns an array of forecast periods.
	 * 
	 * @return
	 */
	public JsonArray getForecasts() {
		return (JsonArray) getProperties().get("periods");
	}

	/**
	 * Prints a text forecast for the chosen period
	 * 
	 * @param period
	 * @return
	 */
	public String text(int period) {
		return forecastName(period) + "\nForecast period: " + forecastStartTime(period) + " through "
				+ forecastEndTime(period) + "\nTime of Day: " + ((isDaytime(period) ? "Day" : "Night"))
				+ "\nTemperature: " + forecastTemperature(period) + forecastTemperatureUnit(period) + " and "
				+ forecastTemperatureTrend(period) + "\nWind: " + forecastWindSpeed(period) + " from the "
				+ forecastWindDirection(period) + "\nDetails: " + detailedForecast(period);
	}
	
	/**
	 * Wind speed for the forecast period, should be in MPH.
	 * @param period
	 * @return
	 */
	public String forecastWindSpeed(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("windSpeed");
	}

	public String forecastWindDirection(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("windDirection");
	}
	/**
	 * Returns temperature for forecast period
	 * 
	 * @param period
	 * @return
	 */
	public int forecastTemperature(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return ((BigDecimal) forecast.get("temperature")).intValue();
	}

	/**
	 * Returns temperature unit abbreviation for forecast period, should be C or F.
	 * 
	 * @param period
	 * @return
	 */
	public char forecastTemperatureUnit(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return ((String) forecast.get("temperatureUnit")).charAt(0);
	}

	/**
	 * Return temperature trend for the forecast period.
	 * 
	 * @param period
	 * @return
	 */
	public String forecastTemperatureTrend(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		String trend = (String) forecast.get("temperatureTrend");
		if (trend == null)
			return "no trend.";
		else
			return trend;
	}

	/**
	 * Returns the start time for the period
	 * 
	 * @param period
	 * @return
	 */
	public String forecastStartTime(int period) {
		// TODO make return some kind of datetime object
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("startTime");
	}

	/**
	 * Returns the end time for the period
	 * 
	 * @param period
	 * @return
	 */
	public String forecastEndTime(int period) {
		// TODO make return some kind of datetime object
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("endTime");
	}

	/**
	 * Name of the forecasting period
	 * 
	 * @param period
	 * @return
	 */
	public String forecastName(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("name");
	}

	/**
	 * Returns a detailed forecast for the period
	 * 
	 * @param period
	 * @return
	 */
	public String detailedForecast(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("detailedForecast");
	}

	/**
	 * Whether the forecast is during the day
	 * 
	 * @param period
	 * @return
	 */
	public boolean isDaytime(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (boolean) forecast.get("isDaytime");
	}

	/**
	 * The URL for the forecast icon
	 * 
	 * @param period
	 * @return
	 */
	public String iconUrl(int period) {
		JsonObject forecast = (JsonObject) getForecasts().get(period);
		return (String) forecast.get("icon");
	}
}
