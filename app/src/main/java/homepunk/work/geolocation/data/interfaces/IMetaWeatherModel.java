package homepunk.work.geolocation.data.interfaces;

import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.Weather;
import rx.Single;

public interface IMetaWeatherModel {
    Single<Weather> getCurrentWeather(Coordinate latlng);
}
