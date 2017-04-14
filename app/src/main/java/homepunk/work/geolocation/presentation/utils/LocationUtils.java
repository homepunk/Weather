package homepunk.work.geolocation.presentation.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

public class LocationUtils {
    public static final float DEFAULT_ZOOM = 6.0f;
    public static final int MAX_RESULTS = 1;

    private static Geocoder geocoder;

    public static void setMarkerOnMap(GoogleMap map, MarkerOptions markerOptions) {
        if (map != null && markerOptions != null) {
            map.clear();
            map.addMarker(markerOptions);
        }
    }

    public static void moveCameraToLatLng(GoogleMap map, LatLng targetLatLng) {
        if (map != null && targetLatLng != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, DEFAULT_ZOOM));
        }
    }

    public static Address getAdressByLatLng(Context context, LatLng latLng) {
        Address adress = null;

        if (latLng != null) {
            initGeocoder(context);

            try {
                List<Address> adresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, MAX_RESULTS);

                Timber.i("Addresses size: " + adresses.size());
                Timber.i("Addresses are: " + adresses.toString());
                if (adresses.size() > 0) {
                    adress = adresses.get(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Timber.i("Adress is: " + (adress != null ? adress.getLocality() : null));
        return adress;
    }

    public static String format(LatLng latLng){
        return String.format("%.2f,%.2f", latLng.latitude, latLng.longitude);
    }

    private static void initGeocoder(Context context) {
        if (geocoder == null) {
            geocoder = new Geocoder(context);
        }
    }
}
