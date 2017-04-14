package homepunk.work.geolocation.presentation.view.interfaces;

import com.google.android.gms.maps.model.Marker;

import homepunk.work.geolocation.presentation.model.Weather;

public interface IWeatherView {
    void onResult(Weather weather);
    void onMarkerChangeResult(Weather weather, Marker marker);
    void onError(String e);
}
