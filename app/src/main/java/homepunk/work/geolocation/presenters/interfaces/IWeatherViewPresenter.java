package homepunk.work.geolocation.presenters.interfaces;

import android.location.Location;

import homepunk.work.geolocation.ui.interfaces.IWeatherView;

public interface IWeatherViewPresenter {
    void setView(IWeatherView view);
    void getWeatherByLocation(Location location);
}
