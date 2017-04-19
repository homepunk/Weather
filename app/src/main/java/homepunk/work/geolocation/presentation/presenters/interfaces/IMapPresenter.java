package homepunk.work.geolocation.presentation.presenters.interfaces;

import com.google.android.gms.maps.GoogleMap;

import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;

/**
 * Created by Homepunk on 19.04.2017.
 */

public interface IMapPresenter {
    void setView(IWeatherView view);
    void setMap(GoogleMap googleMap);
    void onMapClick(Coordinate coordinate);
}
