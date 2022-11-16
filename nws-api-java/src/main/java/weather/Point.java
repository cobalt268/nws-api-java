package weather;

import java.net.MalformedURLException;
import java.net.URL;
import java.math.BigDecimal;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonArray;

/**
 * Wraps the NWS API Point object.
 * 
 * @author cobalt
 *
 */
public class Point extends GeoJson {
	/**
	 * Returns a point object from the NWS API with the given latitude and
	 * longitude.
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws IOException
	 */
	public Point(double latitude, double longitude) throws MalformedURLException {
		URL url = new URL(String.format("https://api.weather.gov/points/%f%%2C%f", latitude, longitude));
		setJson(requestJson(url));
	}

	/**
	 * Wraps a NWS Point JSON in the Point object. If it isn't a valid point object EVERYTHING WILL BREAK. //TODO some kind of checking utility
	 * 
	 * @param jsonObj
	 */
	public Point(JsonObject jsonObj) {
		super(jsonObj);
	}

	/**
	 * Returns the WFO associated with the point.
	 * 
	 * @return
	 */
	public Office office() {
		if (!isValid())
			return Office.INVALID_OFFICE;
		String officeName = (String) getProperties().get("cwa");
		for (Office office : Office.values()) {
			if (office.name().equals(officeName))
				return office;
		}
		return Office.INVALID_OFFICE;
	}

	/**
	 * Returns the point's X position on the WFO's forecast grid.
	 * 
	 * @return
	 */
	public int gridX() {
		return (isValid()) ? ((BigDecimal) getProperties().get("gridX")).intValue() : null;
	}

	/**
	 * Returns the point's Y position on the WFO's forecast grid.
	 * 
	 * @return
	 */
	public int gridY() {
		return (isValid()) ? ((BigDecimal) getProperties().get("gridY")).intValue() : null;
	}

	/**
	 * Returns the forecast zone of the point.
	 * 
	 * @return
	 */
	public String zone() {
		if (!isValid())
			return null;
		String zoneUrl = (String) getProperties().get("forecastZone");
		return zoneUrl.substring(zoneUrl.length() - 6, zoneUrl.length());
	}

	/**
	 * Returns the fire weather forecast zone of the point.
	 * 
	 * @return
	 */
	public String fireZone() {
		if (!isValid())
			return null;
		String zoneUrl = (String) getProperties().get("fireWeatherZone");
		return zoneUrl.substring(zoneUrl.length() - 6, zoneUrl.length());
	}

	/**
	 * Returns the NWS county code of the point.
	 * 
	 * @return
	 */
	public String county() {
		if (!isValid())
			return null;
		String zoneUrl = (String) getProperties().get("county");
		return zoneUrl.substring(zoneUrl.length() - 6, zoneUrl.length());
	}

	/**
	 * Returns the radar station responsible for this point
	 * 
	 * @return
	 */
	public String radar() {
		if (!isValid())
			return null;
		return (String) getProperties().get("radarStation");
	}

	/**
	 * Returns the relative location structure of the response.
	 * 
	 * @return
	 */
	private JsonObject getRelativeLocation() {
		if (!isValid())
			return null;
		return (JsonObject) getProperties().get("relativeLocation");
	}

	/**
	 * Returns the city and state nearest to the point.
	 * 
	 * @return
	 */
	public String city() {
		if (!isValid())
			return null;
		JsonObject location = (JsonObject) getRelativeLocation().get("properties");
		return (String) location.get("city") + ", " + (String) location.get("state");
	}

	/**
	 * Returns the distance in meters from the city given by {@link city()}.
	 * 
	 * @return
	 */
	public double distanceFromCity() {
		if (!isValid())
			return -1;
		JsonObject location = (JsonObject) getRelativeLocation().get("properties");
		return ((BigDecimal) ((JsonObject) location.get("distance")).get("value")).doubleValue();
	}

	/**
	 * Returns the directional bearing toward the city given by {@link city()} in
	 * compass degrees.
	 * 
	 * @return
	 */
	public int bearingFromCity() {
		if (!isValid())
			return -1;
		JsonObject location = (JsonObject) getRelativeLocation().get("properties");
		return ((BigDecimal) ((JsonObject) location.get("bearing")).get("value")).intValue();
	}

	/**
	 * Returns the latitude of the point
	 * 
	 * @return
	 */
	public double latitude() {
		if (!isValid())
			return 0;
		JsonObject location = (JsonObject) getRelativeLocation().get("geometry");
		return ((BigDecimal) ((JsonArray) location.get("coordinates")).get(1)).doubleValue();
	}

	/**
	 * Returns the longitude of the point
	 * 
	 * @return
	 */
	public double longitude() {
		if (!isValid())
			return 0;
		JsonObject location = (JsonObject) getRelativeLocation().get("geometry");
		return ((BigDecimal) ((JsonArray) location.get("coordinates")).get(0)).doubleValue();
	}

	/**
	 * Returns the timezone of the point.
	 * @return
	 */
	public String timeZone() {
		if (!isValid())
			return null;
		return (String) getProperties().get("timeZone");
	}
}
