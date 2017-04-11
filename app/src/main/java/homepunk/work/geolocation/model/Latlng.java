package homepunk.work.geolocation.model;


public class Latlng {
    private double lat;
    private double lng;

    public Latlng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return String.format("%.2f,%.2f", lat, lng);
    }
}
