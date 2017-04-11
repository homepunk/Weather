package homepunk.work.geolocation.model;


import com.google.gson.annotations.SerializedName;

public class WeatherConsolidated {
    @SerializedName("weather_state_name")
    private String weatherStateName;

    @SerializedName("weather_state_abbr")
    private String weatherStateAbbr;

    @SerializedName("wind_direction_compass")
    private String windDirectionCompass;

    @SerializedName("created")
    private String created;

    @SerializedName("applicable_date")
    private String applicableDate;

    @SerializedName("min_temp")
    private double minTemp;

    @SerializedName("max_temp")
    private double maxTemp;

    @SerializedName("the_temp")
    private double theTemp;

    @SerializedName("wind_speed")
    private double windSpeed;

    @SerializedName("wind_direction")
    private double windDirection;

    @SerializedName("air_pressure")
    private double airPressure;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("predictability")
    private int predictability;


    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApplicableDate() {
        return applicableDate.substring(5, 10);
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getTheTemp() {
        return theTemp;
    }

    public void setTheTemp(double theTemp) {
        this.theTemp = theTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(double airPressure) {
        this.airPressure = airPressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }
}
