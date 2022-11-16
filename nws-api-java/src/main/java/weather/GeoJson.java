package weather;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/**
 * Covers multiple NWS API JSON objects.
 * 
 * @author cobalt
 *
 */
public abstract class GeoJson {
	private JsonObject json;
	private IOException error;
	private boolean valid;

	public GeoJson() {
		json = null;
		error = null;
		valid = false;
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

	public GeoJson(JsonObject json) {
		setJson(json);
	}

	protected JsonObject getJson() {
		return json;
	}

	protected void setJson(JsonObject json) {
		this.json = json;
	}

	protected JsonObject requestJson(URL url) {
		// catch invalid position or
		try {
			Scanner urlScan = new Scanner(url.openStream());
			String jsonString = "";
			while (urlScan.hasNextLine()) {
				jsonString += urlScan.nextLine();
			}
			urlScan.close();

			this.valid = true;
			this.error = null;
			
			return Jsoner.deserialize(jsonString, getJson());
		} catch (IOException e) {
			error = e;
			this.valid = false;
			return null;
		}
	}

	/**
	 * Returns false if the API request produced an error
	 * 
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}
	
	/**
	 * Returns the "properties" object of the JSON request.
	 * @return
	 */
	protected JsonObject getProperties() {
		return (JsonObject) getJson().get("properties");
	}
}
