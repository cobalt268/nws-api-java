package weather;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public abstract class Forecast {
	ArrayList<ForecastPeriod> periods = new ArrayList<ForecastPeriod>();

	public Forecast(JsonObject obj) {
		// populate array of forecast periods
		JsonArray periodArray = ((JsonArray) getProperties(obj).get("periods"));
		for (Object x : periodArray) {
			periods.add(createPeriod((JsonObject) x));
		}
	}

	protected abstract ForecastPeriod createPeriod(JsonObject obj);

	/**
	 * Returns an array list of forecast periods.
	 * 
	 * @return
	 */
	public ArrayList<ForecastPeriod> getForecast() {
		return periods;
	}

	/**
	 * Returns the "properties" object of the JSON request.
	 * 
	 * @return
	 */
	protected JsonObject getProperties(JsonObject obj) {
		return (JsonObject) obj.get("properties");
	}

	/**
	 * Returns the GeoJson associated with the API URL.
	 * 
	 * @param url
	 * @return
	 */
	protected static JsonObject requestJson(URL url) {
		// catch invalid position or
		try {
			Scanner urlScan = new Scanner(url.openStream());
			String jsonString = "";
			while (urlScan.hasNextLine()) {
				jsonString += urlScan.nextLine();
			}
			urlScan.close();

			return Jsoner.deserialize(jsonString, (JsonObject) null);
		} catch (IOException e) {
			return null;
		}
	}

	public String toString() {
		String result = "";
		for (ForecastPeriod x : periods) {
			result += x + "\n---------------------------------------------\n";
		}
		return result;
	}
}
