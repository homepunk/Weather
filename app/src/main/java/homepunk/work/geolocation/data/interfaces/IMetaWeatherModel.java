package homepunk.work.geolocation.data.interfaces;

import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import rx.Single;

public interface IMetaWeatherModel {
    Single<TotalWeather> getCurrentWeather(Coordinate latlng);
}
