package weather;

import com.github.cliftonlabs.json_simple.JsonObject;

public abstract class ForecastPeriod {

	String detailedText;
	String name;

	public ForecastPeriod(JsonObject obj) {
		detailedText = (String) obj.get("detailedForecast");
		name = (String) obj.get("name");
	}

	public String text() {
		return detailedText;
	}

	public String name() {
		return name;
	}

	public String toString() {
		return name + "\n" + detailedText;
	}
}
