package homepunk.work.geolocation.data.interfaces;

import homepunk.work.geolocation.model.Latlng;
import homepunk.work.geolocation.model.Weather;
import rx.Observable;
import rx.Single;

public interface IMetaWeatherModel {
    Single<Weather> getCurrentWeatherByLatlng(Latlng latlng);
}
