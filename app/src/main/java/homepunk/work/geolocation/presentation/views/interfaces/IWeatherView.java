package homepunk.work.geolocation.presentation.views.interfaces;

import homepunk.work.geolocation.presentation.models.TotalWeather;

public interface IWeatherView {
    void onResult(TotalWeather weather);
    void onError(String e);
}
