package homepunk.work.geolocation.ui;

import android.Manifest.permission;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homepunk.work.geolocation.R;
import homepunk.work.geolocation.model.Weather;
import homepunk.work.geolocation.model.WeatherConsolidated;
import homepunk.work.geolocation.presenters.WeatherViewPresenter;
import homepunk.work.geolocation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.ui.interfaces.IWeatherView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class WeatherActivity extends AppCompatActivity implements IWeatherView {
    public static final int MIN_TIME = 10000;
    public static final int MIN_DISTANCE = 0;
    public static final int SUCCESS_REQUEST_CODE = 4;

    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private IWeatherViewPresenter weatherViewPresenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.lattlng_weather_textview) TextView latLngWeatherTV;
    @Bind(R.id.lattlng_weather_icon) ImageView latLngWeatherIconIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        weatherViewPresenter = new WeatherViewPresenter();
        weatherViewPresenter.setView(this);

        setUpLocationManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SUCCESS_REQUEST_CODE && permissions.length == 2) {
            getCurrentLocation();
        }
    }


    @Override
    public void onResult(Weather weather) {
        if (weather != null) {
            WeatherConsolidated consolidatedWeather = weather.getFirstConsolidatedWeather();
            if (consolidatedWeather != null) {
                StringBuilder sb = new StringBuilder()
                        .append(consolidatedWeather.getApplicableDate())
                        .append(", ")
                        .append(consolidatedWeather.getWeatherStateName())
                        .append(" in ")
                        .append(weather.getTitle());

                latLngWeatherTV.setText(sb.toString());

                Picasso.with(this)
                        .load(weather.getFullWeatherIconPath())
                        .into(latLngWeatherIconIV);
            }
        }
    }


    @Override
    public void onError(String e) {
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.fab)
    public void onClick() {
        if (location != null) {
            weatherViewPresenter.getWeatherByLocation(location);
        }
    }

    private void setUpLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    WeatherActivity.this.location = location;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION}, SUCCESS_REQUEST_CODE);
        } else {
            location = locationManager.getLastKnownLocation(GPS_PROVIDER);

            if (location == null) {
                locationManager.requestLocationUpdates(GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
                locationManager.requestLocationUpdates(NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
            }
        }
    }

}
