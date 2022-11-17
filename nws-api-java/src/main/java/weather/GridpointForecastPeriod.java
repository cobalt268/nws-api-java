package weather;

import java.math.BigDecimal;

import com.github.cliftonlabs.json_simple.JsonObject;

public class GridpointForecastPeriod extends ForecastPeriod {
	private String windSpeed, windDirection, temperatureTrend, startTime, endTime, iconUrl, shortForecast;
	private int temperature, periodNumber;
	private char temperatureUnit;
	private boolean isDaytime;

	public GridpointForecastPeriod(JsonObject obj) {
		super(obj);
		windSpeed = (String) obj.get("windSpeed");
		windDirection = (String) obj.get("windDirection");
		temperature = ((BigDecimal) obj.get("temperature")).intValue();
		temperatureUnit = ((String) obj.get("temperatureUnit")).charAt(0);
		String trend = (String) obj.get("temperatureTrend");
		if (trend == null)
			temperatureTrend = "no trend.";
		else
			temperatureTrend = trend;
		startTime = (String) obj.get("startTime");
		endTime = (String) obj.get("endTime");
		iconUrl = (String) obj.get("icon");
		isDaytime = (boolean) obj.get("isDaytime");
		shortForecast = (String) obj.get("shortForecast");
		periodNumber = ((BigDecimal) obj.get("periodNumber")).intValue();
	}

	/**
	 * Returns a short qualitative forecast.
	 * 
	 * @return
	 */
	public String shortForecast() {
		return shortForecast;
	}

	/**
	 * Returns the wind speed in miles per hour.
	 * 
	 * @return
	 */
	public String windSpeed() {
		// TODO make return type integer?
		return windSpeed;
	}

	/**
	 * Returns the wind direction.
	 * 
	 * @return
	 */
	public String windDirection() {
		// TODO make enum for compass directions?
		return windDirection;
	}

	/**
	 * Returns the current temperature trend or null if there is none.
	 * 
	 * @return
	 */
	public String temperatureTrend() {
		// TODO trend enum?
		return temperatureTrend;
	}

	/**
	 * Returns the start time for the forecast's validity.
	 * 
	 * @return
	 */
	public String startTime() {
		// TODO return as datetime?
		return startTime;
	}

	/**
	 * Returns the end time for the forecast's validity.
	 * 
	 * @return
	 */
	public String endTime() {
		// TODO return as datetime?
		return endTime;
	}

	/**
	 * Returns the URL for the icon used in the NWS forecast.
	 * 
	 * @return
	 */
	public String iconUrl() {
		return iconUrl;
	}

	/**
	 * Returns the temperature.
	 * 
	 * @return
	 */
	public int temperature() {
		return temperature;
	}

	/**
	 * Returns the number of the forecast period in the full forecast request.
	 * 
	 * @return
	 */
	public int periodNumber() {
		return periodNumber;
	}

	/**
	 * Returns the letter abbreviation of the temperature scale in use.
	 * 
	 * @return
	 */
	public char temperatureUnit() {
		// TODO temperature scale enum?
		return temperatureUnit;
	}

	/**
	 * Returns whether the period occurs during the day or the night.
	 * 
	 * @return
	 */
	public boolean isDaytime() {
		return isDaytime;
	}

}
