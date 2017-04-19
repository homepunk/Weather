package homepunk.work.geolocation.presentation.views.interfaces;

import com.google.android.gms.maps.model.Marker;

import homepunk.work.geolocation.presentation.models.Weather;

public interface IWeatherView {
    void onResult(Weather weather);
    void onError(String e);
}
