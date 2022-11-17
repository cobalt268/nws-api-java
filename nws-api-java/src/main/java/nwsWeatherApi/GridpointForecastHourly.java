package nwsWeatherApi;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Similar to GridpointForecast, but returns less detailed forecasts for hourly
 * periods.
 * 
 * @author cobalt
 *
 */
public class GridpointForecastHourly extends GridpointForecast {
	/**
	 * Creates a set of hourly gridpoint forecasts for the given WFO and grid
	 * coordinates.
	 * 
	 * @param wfo
	 * @param x
	 * @param y
	 */
	public GridpointForecastHourly(Office wfo, int x, int y) {
		super(requestJson(generateURL(wfo, x, y)));
	}

	@Override
	protected ForecastPeriod createPeriod(JsonObject obj) {
		return new GridpointForecastHourlyPeriod(obj);
	}

	/**
	 * All URLs used in the {@link GridpointForecastHourly(Office wfo, int x, int
	 * y)} constructor <b>should</b> be valid because they are build using enums and
	 * primitives. This allows us to not have to use a throws statement on the
	 * constructor.
	 * 
	 * @param wfo
	 * @param x
	 * @param y
	 * @return
	 */
	private static URL generateURL(Office wfo, int x, int y) {
		try {
			return new URL(
					String.format("https://api.weather.gov/gridpoints/%s/%d,%d/forecast/hourly", wfo.name(), x, y));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
