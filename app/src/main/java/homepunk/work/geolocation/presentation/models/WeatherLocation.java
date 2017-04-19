package homepunk.work.geolocation.presentation.models;


import com.google.gson.annotations.SerializedName;

import static homepunk.work.geolocation.data.Constants.LOCATION_DISTANCE;
import static homepunk.work.geolocation.data.Constants.LOCATION_LATLNG;
import static homepunk.work.geolocation.data.Constants.LOCATION_LOC_TYPE;
import static homepunk.work.geolocation.data.Constants.LOCATION_TITLE;
import static homepunk.work.geolocation.data.Constants.LOCATION_WOEID;

public class WeatherLocation {
    @SerializedName(LOCATION_TITLE)
    private String title;

    @SerializedName(LOCATION_LOC_TYPE)
    private String locationType;

    @SerializedName(LOCATION_WOEID)
    private long woeid;

    @SerializedName(LOCATION_DISTANCE)
    private long distance;

    @SerializedName(LOCATION_LATLNG)
    private String latlng;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public int getWoeid() {
        return (int) woeid;
    }

    public void setWoeid(long woeid) {
        this.woeid = woeid;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }
}
