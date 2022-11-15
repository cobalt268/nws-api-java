package weather;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public abstract class NWSJson {
	private JsonObject json;
	private IOException error;
	private boolean valid;
	
	public NWSJson() {
		json = null;
		error = null;
		valid = true;
	}

	public NWSJson(JsonObject json) {
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
			while (urlScan.hasNext()) {
				jsonString += urlScan.next();
			}
			urlScan.close();

			return Jsoner.deserialize(jsonString, getJson());
		} catch (IOException e) {
			error = e;
			this.valid = false;
			return null;
		}
	}
}
