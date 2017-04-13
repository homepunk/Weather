package homepunk.work.geolocation.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homepunk.work.geolocation.R;
import homepunk.work.geolocation.model.Weather;
import homepunk.work.geolocation.model.WeatherConsolidated;
import homepunk.work.geolocation.presenters.WeatherViewPresenter;
import homepunk.work.geolocation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.ui.interfaces.IWeatherView;
import io.nlopez.smartlocation.location.providers.LocationManagerProvider;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static io.nlopez.smartlocation.SmartLocation.Builder;
import static io.nlopez.smartlocation.SmartLocation.LocationControl;

public class WeatherActivity extends AppCompatActivity implements IWeatherView, OnMapReadyCallback {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.lattlng_weather_textview) TextView latLngWeatherTV;
    @Bind(R.id.lattlng_weather_icon) ImageView latLngWeatherIconIV;

    private Location location;
    private RxPermissions rxPermissions;
    private LocationControl locationControl;
    private SupportMapFragment mapFragment;
    private IWeatherViewPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setUpUI();
    }

    @Override
    protected void onResume() {
        mapFragment.onResume();
        super.onResume();

        locate();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);

        weatherPresenter.setMap(map);
    }


    @Override
    public void onResult(Weather weather) {
        if (weather != null) {
            WeatherConsolidated weatherConsolidated = weather.getFirstConsolidatedWeather();
            if (weatherConsolidated != null) {
                StringBuilder sb = new StringBuilder()
                        .append(weatherConsolidated.getWeatherStateName())
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

    @OnClick(R.id.fab)
    public void onClick() {
        locate();
    }

    private boolean isPermissionsGranted() {
        return rxPermissions.isGranted(ACCESS_FINE_LOCATION) && rxPermissions.isGranted(ACCESS_COARSE_LOCATION);
    }

    private void setUpUI() {
        weatherPresenter = new WeatherViewPresenter();
        weatherPresenter.setView(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
        
        locationControl = new Builder(this)
                .logging(true)
                .build()
                .location(new LocationManagerProvider());
        
        rxPermissions = new RxPermissions(this);
    }

    private void locate() {
        if (!isPermissionsGranted()) {
            rxPermissions.request(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION);
        }

        locationControl.oneFix()
                       .start(location -> WeatherActivity.this.location = location);

        if (location == null) {
            location = locationControl.getLastLocation();
        } else {
            weatherPresenter.getWeatherByLocation(new LatLng(location.getLatitude(),
                                                             location.getLongitude()));
        }
    }

}
