package homepunk.work.geolocation.data.interfaces;

import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.presentation.model.Weather;
import rx.Single;

public interface IMetaWeatherModel {
    Single<Weather> getCurrentWeatherByLatLng(LatLng latlng);
}
