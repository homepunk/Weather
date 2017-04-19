package homepunk.work.geolocation.presentation.models;

import android.location.Location;

import java.util.Locale;

/**
 * Created by Homepunk on 19.04.2017.
 */

public class Coordinate {
    public static final String REGEX = ",";

    private double longitude;
    private double latitude;

    public Coordinate() {
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinate(com.google.android.gms.maps.model.LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public Coordinate(Location location) {
        if (location == null) {
            this.latitude = 0;
            this.longitude = 0;
        } else {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        }
    }


    public Coordinate(String latLng) {
            String[] coordinates = latLng.split(REGEX);

            this.latitude = Double.parseDouble(coordinates[0]);
            this.longitude = Double.parseDouble(coordinates[1]);
        }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public com.google.android.gms.maps.model.LatLng getMapLatLng(){
        return new com.google.android.gms.maps.model.LatLng(latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%.2f,%.2f", latitude, longitude);
    }
}
