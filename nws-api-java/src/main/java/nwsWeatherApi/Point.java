package nwsWeatherApi;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Wraps the PointGeoJson schema of the NWS API.
 * 
 * @author cobalt
 *
 */
public class Point {
	private Office office;
	private int gridX, gridY, bearingFromCity;
	private String zone, fireZone, county, radar, city, timezone;
	private double distanceFromCity, latitude, longitude;

	// constructors

	/**
	 * Creates a Point object with the given latitude and longitude. Coordinates
	 * must be in the NWS's jurisdiction.
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public Point(double latitude, double longitude) {
		this(Forecast.getProperties(Forecast.requestJson(generateURL(latitude, longitude))));
	}

	/**
	 * Creates a point object using a <b>valid</b> NWS API JSON of the PointGeoJson
	 * schema.
	 * 
	 * @param obj
	 */
	public Point(JsonObject obj) {
		System.out.println(obj);
		// set office instance variable
		String officeName = (String) obj.get("cwa");
		for (Office officeX : Office.values()) {
			if (officeX.name().equals(officeName))
				office = officeX;
		}

		// set grid coordinates
		gridX = ((BigDecimal) obj.get("gridX")).intValue();
		gridY = ((BigDecimal) obj.get("gridY")).intValue();

		// set location codes
		String zoneUrl = (String) obj.get("forecastZone");
		zone = zoneUrl.substring(zoneUrl.length() - 6, zoneUrl.length());

		String fireZoneUrl = (String) obj.get("fireWeatherZone");
		fireZone = fireZoneUrl.substring(fireZoneUrl.length() - 6, fireZoneUrl.length());

		String countyUrl = (String) obj.get("county");
		county = countyUrl.substring(countyUrl.length() - 6, countyUrl.length());

		// set radar station callsign
		radar = (String) obj.get("radarStation");

		// set city
		JsonObject location = (JsonObject) getRelativeLocation(obj).get("properties");
		city = (String) location.get("city") + ", " + (String) location.get("state");
		System.out.println(city);

		distanceFromCity = ((BigDecimal) ((JsonObject) location.get("distance")).get("value")).doubleValue();
		bearingFromCity = ((BigDecimal) ((JsonObject) location.get("bearing")).get("value")).intValue();

		// set coordinates
		JsonObject geometry = (JsonObject) getRelativeLocation(obj).get("geometry");

		latitude = ((BigDecimal) ((JsonArray) geometry.get("coordinates")).get(1)).doubleValue();
		longitude = ((BigDecimal) ((JsonArray) geometry.get("coordinates")).get(0)).doubleValue();

		// set timezone
		timezone = (String) obj.get("timeZone");
	}

	// accessor methods

	/**
	 * Returns the WFO responsible for the point.
	 * 
	 * @return
	 */
	public Office office() {
		return office;
	}

	/**
	 * Returns the X coordinate of the Point's grid within the WFO.
	 * 
	 * @return
	 */
	public int gridX() {
		return gridX;
	}

	/**
	 * Returns the Y coordinate of the Point's grid within the WFO.
	 * 
	 * @return
	 */
	public int gridY() {
		return gridY;
	}

	/**
	 * Returns the compass angle from the point towards the city returned by
	 * {@link city()}.
	 * 
	 * @return
	 */
	public int bearingFromCity() {
		return bearingFromCity;
	}

	/**
	 * Returns the zone code for the point.
	 * 
	 * @return
	 */
	public String zone() {
		return zone;
	}

	/**
	 * Returns the fire weather zone code for the point.
	 * 
	 * @return
	 */
	public String fireZone() {
		return fireZone;
	}

	/**
	 * Returns the county code for the point.
	 * 
	 * @return
	 */
	public String county() {
		return county;
	}

	/**
	 * Returns the callsign of the radar station responsible for the point.
	 * 
	 * @return
	 */
	public String radar() {
		return radar;
	}

	/**
	 * Returns the nearest city. Direction and distance from the point can be found
	 * using {@link bearingFromCity()} and {@link distanceFromCity()}.
	 * 
	 * @return
	 */
	public String city() {
		return city;
	}

	/**
	 * Returns a String representation of the Point's timezone.
	 * 
	 * @return
	 */
	public String timezone() {
		return timezone;
	}

	/**
	 * Returns the Point's distance from the city returned by {@link city()} in
	 * meters.
	 * 
	 * @return
	 */
	public double distanceFromCity() {
		return distanceFromCity;
	}

	/**
	 * Returns the Point's latitude.
	 * 
	 * @return
	 */
	public double latitude() {
		return latitude;
	}

	/**
	 * Returns the Point's longitude.
	 * 
	 * @return
	 */
	public double longitude() {
		return longitude;
	}

	// static methods

	/**
	 * Returns the relative location structure of the response.
	 * 
	 * @return
	 */
	protected static JsonObject getRelativeLocation(JsonObject obj) {
		return (JsonObject) ((JsonObject) obj.get("relativeLocation"));
	}

	// forecast generation methods

	/**
	 * Returns the {@link ZoneForecast} for the point.
	 * 
	 * @return
	 */
	public ZoneForecast zoneForecast() {
		return new ZoneForecast(zone);
	}

	/**
	 * Returns the {@link GridpointForecast} for the point.
	 * 
	 * @return
	 */
	public GridpointForecast gridpointForecast() {
		return new GridpointForecast(office, gridX, gridY);
	}

	// private methods
	/**
	 * All URLs used in the {@link PointNew(double latitude, double longitude)}
	 * constructor <b>should</b> be valid because they are build using enums and
	 * primitives. This allows us to not have to use a throws statement on the
	 * constructor.
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	private static URL generateURL(double latitude, double longitude) {
		try {
			return new URL(String.format("https://api.weather.gov/points/%f%%2C%f", latitude, longitude));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
