package weather;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

public class GridpointForecast extends Forecast {

	private GridpointForecast(JsonObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a GridpointForecast using the WFO and the X and Y coordinates on the
	 * WFO's grid. Each grid coordinate corresponds to a 2.5 * 2.5 km square.
	 * 
	 * @param wfo
	 * @param x
	 * @param y
	 * @throws MalformedURLException
	 */
	public GridpointForecast(Office wfo, int x, int y) throws MalformedURLException {
		this(requestJson(
				new URL(String.format("https://api.weather.gov/gridpoints/%s/%d,%d/forecast", wfo.name(), x, y))));
	}

	@Override
	protected ForecastPeriod createPeriod(JsonObject obj) {
		return new GridpointForecastPeriod(obj);
	}

	/**
	 * why does the api do this to me
	 * 
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		GridpointForecast test = new GridpointForecast(Office.ILM, 94, 68);
		System.out.println(test);
	}
}
