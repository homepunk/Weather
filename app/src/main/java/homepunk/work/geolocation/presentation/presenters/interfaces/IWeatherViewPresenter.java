package homepunk.work.geolocation.presentation.presenters.interfaces;

import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.presentation.view.interfaces.IWeatherView;

public interface IWeatherViewPresenter {
    void setView(IWeatherView view);
    void getWeatherByLatLng(LatLng latLng);
    void setMap(GoogleMap map);
}
