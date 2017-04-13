package homepunk.work.geolocation.data.interfaces;

import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.model.Weather;
import rx.Single;

public interface IMetaWeatherModel {
    Single<Weather> getCurrentWeatherByLatlng(LatLng latlng);
}
