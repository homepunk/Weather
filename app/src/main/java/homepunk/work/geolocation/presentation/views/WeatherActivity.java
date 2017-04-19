package homepunk.work.geolocation.presentation.views;

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
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homepunk.work.geolocation.R;
import homepunk.work.geolocation.presentation.App;
import homepunk.work.geolocation.presentation.adapters.CustomInfoWindowAdapter;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.Weather;
import homepunk.work.geolocation.presentation.models.WeatherConsolidated;
import homepunk.work.geolocation.presentation.presenters.interfaces.IMapPresenter;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;
import io.nlopez.smartlocation.location.providers.LocationManagerProvider;

import static homepunk.work.geolocation.presentation.utils.MapUtils.getCusomizedMap;
import static homepunk.work.geolocation.presentation.utils.RxUtils.verifyPermissionsGranted;
import static io.nlopez.smartlocation.SmartLocation.Builder;
import static io.nlopez.smartlocation.SmartLocation.LocationControl;

public class WeatherActivity extends AppCompatActivity implements IWeatherView, OnMapReadyCallback {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.lattlng_weather_textview) TextView latLngWeatherTV;
    @Bind(R.id.lattlng_weather_icon) ImageView latLngWeatherIconIV;

    @Inject IMapPresenter mapPresenter;
    @Inject IWeatherViewPresenter weatherPresenter;

    private Location location;
    private SupportMapFragment mapFragment;
    private LocationControl locationControl;
    private WeatherConsolidated weatherConsolidated;
    private CustomInfoWindowAdapter customInfoWindowAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        App.getAppComponent(this).plus(this);
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
        GoogleMap map = getCusomizedMap(googleMap);

        map.setInfoWindowAdapter(customInfoWindowAdapter);

        map.setOnMapClickListener(latLng ->
                mapPresenter.onMapClick(new Coordinate(latLng)));

        mapPresenter.setMap(map);


    }

    @Override
    public void onResult(Weather weather) {
        weatherConsolidated = weather.getFirstConsolidatedWeather();

        if (weather != null && weatherConsolidated!= null) {
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

    @Override
    public void onError(String e) {
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.fab)
    public void update() {
        locate();
    }

    private void setUpUI() {
        weatherPresenter.setView(this);

        mapPresenter.setView(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        locationControl = new Builder(this)
                .logging(true)
                .build()
                .location(new LocationManagerProvider());

        customInfoWindowAdapter = new CustomInfoWindowAdapter(this);

    }

    private void locate() {
        verifyPermissionsGranted(this);

        locationControl.start((Location location1) -> {
            WeatherActivity.this.location = location1;
        });

        if (location == null) {
            location = locationControl.getLastLocation();
        }

        if (location != null) {
            weatherPresenter.getCurrentWeather(new Coordinate(location));
       }
    }
}
