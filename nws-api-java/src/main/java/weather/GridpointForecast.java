package weather;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

public class GridpointForecast extends Forecast {

	private GridpointForecast(JsonObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

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
