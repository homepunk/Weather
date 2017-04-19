package homepunk.work.geolocation.presentation.presenters.interfaces;

import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;

public interface IWeatherViewPresenter {
    void setView(IWeatherView view);

    void getCurrentWeather(Coordinate coordinate);
}
