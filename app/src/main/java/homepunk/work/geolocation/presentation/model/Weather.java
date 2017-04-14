package homepunk.work.geolocation.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static homepunk.work.geolocation.data.Constants.BASE_URL;
import static homepunk.work.geolocation.data.Constants.WEATHER_ICON_PNG_TYPE;
import static homepunk.work.geolocation.data.Constants.WEATHER_ICON_URL;

public class Weather {
    @SerializedName("consolidated_weather")
    private List<WeatherConsolidated> consolidatedWeather = null;

    @SerializedName("time")
    private String time;

    @SerializedName("sun_rise")
    private String sunRise;

    @SerializedName("sun_set")
    private String sunSet;

    @SerializedName("timezone_name")
    private String timezoneName;

    @SerializedName("title")
    private String title;

    @SerializedName("location_type")
    private String locationType;

    @SerializedName("woeid")
    private int woeid;

    @SerializedName("latt_long")
    private String lattLong;

    @SerializedName("timezone")
    private String timezone;

    public List<WeatherConsolidated> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    public void setConsolidatedWeather(List<WeatherConsolidated> consolidatedWeather) {
        this.consolidatedWeather = consolidatedWeather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

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
        return woeid;
    }

    public void setWoeid(int woeid) {
        this.woeid = woeid;
    }

    public String getLattLong() {
        return lattLong;
    }

    public void setLattLong(String lattLong) {
        this.lattLong = lattLong;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getFullWeatherIconPath(){
        WeatherConsolidated consolidatedWeather = getConsolidatedWeather().get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BASE_URL)
                     .append(WEATHER_ICON_URL)
                     .append(consolidatedWeather.getWeatherStateAbbr())
                     .append(WEATHER_ICON_PNG_TYPE);

        return stringBuilder.toString();
    }

    public WeatherConsolidated getFirstConsolidatedWeather() {
        return consolidatedWeather == null || consolidatedWeather.size() == 0 ? null : consolidatedWeather.get(0);
    }

}
