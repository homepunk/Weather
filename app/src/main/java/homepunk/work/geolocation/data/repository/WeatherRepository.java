package homepunk.work.geolocation.data.repository;


import homepunk.work.geolocation.data.api.ApiManager;
import homepunk.work.geolocation.data.api.WeatherApi;
import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import homepunk.work.geolocation.presentation.models.LocationInformation;
import rx.Single;
import timber.log.Timber;

public class WeatherRepository implements IMetaWeatherModel {
    private WeatherApi weatherApi;

    public WeatherRepository() {
        this.weatherApi = ApiManager.getInstance();
    }

    @Override
    public Single<TotalWeather> getCurrentWeather(Coordinate coordinate) {
        Timber.i("Get weather by " + coordinate.toString() + " latLng");

        return weatherApi.fetchLocation(coordinate.toString())
                .map(locationsInfo -> {
                    for (LocationInformation locationInformation : locationsInfo) {
                        Timber.i("Founded: " + locationInformation.getLatlng() + " " + locationInformation.getTitle());
                    }

                    return locationsInfo.get(0);
                })
                .flatMap(locationInformation -> weatherApi.fetchWeatherByWoeid(locationInformation.getWoeid()));
    }


}
