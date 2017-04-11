package homepunk.work.geolocation.data;

public class Constants {
    //    Api urls
    public static final String BASE_URL = "https://www.metaweather.com";
    public static final String LOCATION_URL = "/api/location/search/";
    public static final String WEATHER_URL = "/api/location/{woeid}/";
    //    Api params
    public static final String LATLONG_PARAM = "lattlong";
    public static final String WOEID_PARAM = "woeid";

    public static final String WEATHER_ICON_URL = "/static/img/weather/png/";
    public static final String WEATHER_ICON_PNG_TYPE = ".png";

    //    WeatherLocation
    public static final String LOCATION_TITLE = "title";
    public static final String LOCATION_LATLNG = "latt_long";
    public static final String LOCATION_LOC_TYPE = "location_type";
    public static final String LOCATION_WOEID = "woeid";
    public static final String LOCATION_DISTANCE = "distance";

    //    Consolidated weather
    public static final String CONSOLIDATED_WEATHER_STATE_NAME = "weather_state_name";
    public static final String CONSOLIDATED_WEATHER_ID = "weather_state_name";
}
