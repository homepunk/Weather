package homepunk.work.geolocation.presentation.utils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.Weather;

public class MapUtils {
    public static final float DEFAULT_ZOOM = 6.0f;

    public static GoogleMap getCusomizedMap(GoogleMap map) {
        if (map != null) {
            map.getUiSettings().setZoomGesturesEnabled(true);

            return map;
        } else {
            return null;
        }
    }

    public static MarkerOptions getMarkerOptions(Weather weather, Coordinate coordinate) {
        return new MarkerOptions()
                .position(coordinate.getMapLatLng())
                .snippet(weather.getFullWeatherIconPath())
                .title(weather.getTitle());
    }


    public static void setMarkerOnMap(GoogleMap map, MarkerOptions markerOptions) {
        if (map != null && markerOptions != null) {
            map.clear();
            map.addMarker(markerOptions)
                    .showInfoWindow();
        }
    }

    public static void moveCameraToLatLng(GoogleMap map, Coordinate target) {
        if (map != null && target != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(target.getMapLatLng(), DEFAULT_ZOOM));
        }
    }
}
