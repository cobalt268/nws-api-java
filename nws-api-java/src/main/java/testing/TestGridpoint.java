package testing;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import weather.GridpointForecast;

public class TestGridpoint {
	public static void main(String[] args) {
		JsonObject forecast_json = null;
		forecast_json = Jsoner.deserialize(ExampleJson.GRIDPOINT_FORECAST,forecast_json);
		GridpointForecast forecast = new GridpointForecast(forecast_json);
	}
}
