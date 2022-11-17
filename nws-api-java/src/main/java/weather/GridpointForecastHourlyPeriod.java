package weather;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Slightly modified version of {@link GridpointForecastPeriod} for the less
 * detailed {@link GridpointForecastHourly}
 * 
 * @author cobalt
 *
 */
public class GridpointForecastHourlyPeriod extends GridpointForecastPeriod {

	public GridpointForecastHourlyPeriod(JsonObject obj) {
		super(obj);
	}

	@Override
	/**
	 * Hourly forecasts don't have name fields.
	 */
	public String name() {
		int day = periodNumber() / 12;
		int hour = periodNumber() % 12;
		return "Day " + day + ", Hour " + hour;
	}

	@Override
	/**
	 * Hourly forecasts don't have detailed forecast texts.
	 */
	public String text() {
		return shortForecast() + " and " + temperature() + temperatureUnit();
	}

}
