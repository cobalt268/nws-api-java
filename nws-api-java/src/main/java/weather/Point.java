package weather;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.math.BigDecimal;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonArray;


public class Point {
	/**
	 * The API response wrapped by this class.
	 */
	private JsonObject pointJson = null;
	/**
	 * Holds potential errors returned by invalid API calls.
	 */
	private IOException error = null;
	/**
	 * Holds whether the wrapped response is valid.
	 */
	private boolean valid = true;

	/**
	 * Returns a point object from the NWS API with the given latitude and
	 * longitude.
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws IOException
	 */
	public Point(double latitude, double longitude) throws IOException {
		URL url = new URL(String.format("https://api.weather.gov/points/%.2f%%2C%.2f", latitude, longitude));
		// catch invalid position or
		try {
			Scanner urlScan = new Scanner(url.openStream());
			String jsonString = "";
			while (urlScan.hasNext()) {
				jsonString += urlScan.next();
			}

			this.pointJson = Jsoner.deserialize(jsonString, pointJson);
		} catch (IOException e) {
			error = e;
			this.valid = false;
		}
	}

	/**
	 * Wraps a preexisting NWS Point JSON in the Point object.
	 * 
	 * @param jsonObj
	 */
	public Point(JsonObject jsonObj) {
		this.pointJson = jsonObj;
	}

	/**
	 * Returns true if the API request produced an error
	 * 
	 * @return
	 */
	public boolean isValid() {
		// json only has status key if error
		return valid;
	}

	/**
	 * Returns the error that caused the request to be invalid. If request is valid
	 * returns null.
	 * 
	 * @return
	 */
	public IOException getError() {
		return error;
	}

	private JsonObject getProperties() {
		return (JsonObject) pointJson.get("properties");
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
		return (String) getProperties().get("radarStation");
	}

	/**
	 * Returns the relative location structure of the response.
	 * 
	 * @return
	 */
	private JsonObject getRelativeLocation() {
		return (JsonObject) getProperties().get("relativeLocation");
	}

	/**
	 * Returns the city and state nearest to the point.
	 * 
	 * @return
	 */
	public String city() {
		JsonObject location = (JsonObject) getRelativeLocation().get("properties");
		return (String) location.get("city") + ", " + (String) location.get("state");
	}
	
	/**
	 * Returns the distance in meters from the city given by {@link city()}.
	 * @return
	 */
	public double distanceFromCity() {
		JsonObject location = (JsonObject) getRelativeLocation().get("properties");
		return ((BigDecimal) ((JsonObject) location.get("distance")).get("value")).doubleValue();
	}

	/**
	 * Returns the latitude of the point
	 * 
	 * @return
	 */
	public double latitude() {
		JsonObject location = (JsonObject) getRelativeLocation().get("geometry");
		return ((BigDecimal) ((JsonArray) location.get("coordinates")).get(1)).doubleValue();
	}

	/**
	 * Returns the longitude of the point
	 * 
	 * @return
	 */
	public double longitude() {
		JsonObject location = (JsonObject) getRelativeLocation().get("geometry");
		return ((BigDecimal) ((JsonArray) location.get("coordinates")).get(0)).doubleValue();
	}

	public static void main(String[] args) throws IOException {
		Point ames = new Point(42.034, -93.620);
		System.out.println(ames.isValid());
		Point nullIsland = new Point(0, 0);
		System.out.println(nullIsland.isValid());
		System.out.println(ames.office());
		System.out.println(ames.gridX());
		System.out.println(ames.gridY());
		System.out.println(nullIsland.office());
		System.out.println(ames.zone());
		System.out.println(ames.fireZone());
		System.out.println(ames.county());
		System.out.println(ames.city());
		System.out.println(ames.latitude());
		System.out.println(ames.longitude());
		System.out.println(ames.distanceFromCity());
	}
}
