package homepunk.work.geolocation.presenters.interfaces;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.ui.interfaces.IWeatherView;

public interface IWeatherViewPresenter {
    void setView(IWeatherView view);
    void getWeatherByLocation(LatLng latLng);
    void setMap(GoogleMap map);
}
