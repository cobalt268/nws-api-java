package weather;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Wraps the GridpointForecastGeoJson schema of the NWS API.
 * 
 * @author cobalt
 *
 */
public class GridpointForecast extends Forecast {

	/**
	 * Creates a GridpointForecast using a <b>valid</b> JSON of the NWS API
	 * GridpointForecastGeoJson schema.
	 * 
	 * @param obj
	 */
	public GridpointForecast(JsonObject obj) {
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
	 */
	public GridpointForecast(Office wfo, int x, int y) {
		this(requestJson(generateURL(wfo, x, y)));
	}

	public GridpointForecastPeriod getPeriod(int period) {
		return (GridpointForecastPeriod) getForecast().get(period);
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
		// TODO remove test main
		GridpointForecast test = new GridpointForecast(Office.ILM, 94, 68);
		System.out.println(test);
	}

	/**
	 * All URLs used in the {@link GridpointForecast(Office wfo, int x, int y)}
	 * constructor <b>should</b> be valid because they are build using enums and
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
			return new URL(String.format("https://api.weather.gov/gridpoints/%s/%d,%d/forecast", wfo.name(), x, y));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
