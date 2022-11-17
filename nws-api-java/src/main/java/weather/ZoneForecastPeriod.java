package weather;

import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Holds information about {@link ZoneForecast} periods.
 * 
 * @author cobalt
 *
 */
public class ZoneForecastPeriod extends ForecastPeriod {

	/**
	 * Constructs a ZoneForecastPeriod from a <b>valid</b> JSON of the ZoneForecast
	 * schema of the NWS API.
	 * 
	 * @param obj
	 */
	public ZoneForecastPeriod(JsonObject obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

}
