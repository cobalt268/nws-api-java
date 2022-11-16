package weather;

import java.math.BigDecimal;

import com.github.cliftonlabs.json_simple.JsonObject;

public class GridpointForecastPeriod extends ForecastPeriod {
	private String windSpeed, windDirection, temperatureTrend, startTime, endTime, iconUrl;
	private int temperature;
	private char temperatureUnit;
	private boolean isDaytime;

	public GridpointForecastPeriod(JsonObject obj) {
		super(obj);
		windSpeed = (String) obj.get("windSpeed");
		windDirection = (String) obj.get("windDirection");
		temperature = ((BigDecimal) obj.get("temperature")).intValue();
		temperatureUnit = ((String) obj.get("temperatureUnit")).charAt(0);
		String trend = (String) obj.get("temperatureTrend");
		if (trend == null)
			temperatureTrend = "no trend.";
		else
			temperatureTrend = trend;
		startTime = (String) obj.get("startTime");
		endTime = (String) obj.get("endTime");
	}

	public String windSpeed() {
		return windSpeed;
	}

	public String windDirection() {
		return windDirection;
	}

	public String temperatureTrend() {
		return temperatureTrend;
	}

	public String startTime() {
		return startTime;
	}

	public String endTime() {
		return endTime;
	}

	public String iconUrl() {
		return iconUrl;
	}

	public int temperature() {
		return temperature;
	}

	public char temperatureUnit() {
		return temperatureUnit;
	}

	public boolean isDaytime() {
		return isDaytime;
	}

}
