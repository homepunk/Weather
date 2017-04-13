package homepunk.work.geolocation.ui.interfaces;

import homepunk.work.geolocation.model.Weather;

public interface IWeatherView {
    void onResult(Weather weather);
    void onError(String e);
}
