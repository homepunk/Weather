package homepunk.work.geolocation.data.repository;


import homepunk.work.geolocation.data.api.WeatherApi;
import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.Weather;
import homepunk.work.geolocation.presentation.models.WeatherLocation;
import rx.Single;
import timber.log.Timber;

public class WeatherRepository implements IMetaWeatherModel {
    private final WeatherApi weatherApi;

    public WeatherRepository(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public Single<Weather> getCurrentWeather(Coordinate latlng) {
        Timber.i("Get weather by " + latlng.toString() + " latLng");

        return weatherApi.fetchLocation(latlng.toString())
                .map(weatherLocations -> {
                    for (WeatherLocation weatherLocation : weatherLocations) {
                        Timber.i("Founded: " + weatherLocation.getLatlng() + " " + weatherLocation.getTitle());
                    }
                    Timber.i("Found location" + weatherLocations.get(0).getLatlng() + " " + weatherLocations.get(0).getTitle());
                    return weatherLocations.get(0);
                })
                .flatMap(weatherLocation -> weatherApi.fetchWeatherByWoeid(weatherLocation.getWoeid()));
    }


}
