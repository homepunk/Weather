package homepunk.work.geolocation.data.api;

import java.util.List;

import homepunk.work.geolocation.presentation.models.WeatherLocation;
import homepunk.work.geolocation.presentation.models.Weather;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

import static homepunk.work.geolocation.data.Constants.LATLONG_PARAM;
import static homepunk.work.geolocation.data.Constants.LOCATION_URL;
import static homepunk.work.geolocation.data.Constants.WEATHER_URL;
import static homepunk.work.geolocation.data.Constants.WOEID_PARAM;

public interface WeatherApi {
    @GET(LOCATION_URL)
    Single<List<WeatherLocation>> fetchLocation(@Query(LATLONG_PARAM) String latlng);

    @GET(WEATHER_URL)
    Single<Weather> fetchWeatherByWoeid(@Path(WOEID_PARAM) int woeid);
}
