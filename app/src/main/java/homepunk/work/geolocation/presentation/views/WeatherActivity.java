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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homepunk.work.geolocation.R;
import homepunk.work.geolocation.presentation.adapters.CustomInfoWindowAdapter;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import homepunk.work.geolocation.presentation.models.Weather;
import homepunk.work.geolocation.presentation.presenters.MapPresenter;
import homepunk.work.geolocation.presentation.presenters.WeatherPresenter;
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

    private Coordinate coordinate;
    private IMapPresenter mapPresenter;
    private SupportMapFragment mapFragment;
    private LocationControl locationControl;
    private IWeatherViewPresenter weatherPresenter;
    private CustomInfoWindowAdapter customInfoWindowAdapter;


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
        GoogleMap map = getCusomizedMap(googleMap);

        map.setInfoWindowAdapter(customInfoWindowAdapter);

        map.setOnMapClickListener(latLng ->
                mapPresenter.onMapClick(new Coordinate(latLng)));

        mapPresenter.setMap(map);


    }

    @Override
    public void onResult(TotalWeather totalWeather) {
        Weather weather = totalWeather.getFirstConsolidatedWeather();

        if (weather != null) {
                StringBuilder sb = new StringBuilder()
                        .append(weather.getWeatherStateName())
                        .append(" in ")
                        .append(totalWeather.getTitle());

                latLngWeatherTV.setText(sb.toString());

                Picasso.with(this)
                        .load(totalWeather.getFullWeatherIconPath())
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
        mapPresenter = new MapPresenter(this);
        mapPresenter.setView(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        locationControl = new Builder(this)
                .logging(true)
                .build()
                .location(new LocationManagerProvider());

        weatherPresenter = new WeatherPresenter(this);
        weatherPresenter.setView(this);

        customInfoWindowAdapter = new CustomInfoWindowAdapter(this);

    }

    private void locate() {
        verifyPermissionsGranted(this);

        locationControl.start(this::setCoordinate);

        if (coordinate == null) {
            setCoordinate(locationControl.getLastLocation());
        }

        if (coordinate != null) {
            weatherPresenter.getCurrentWeather(coordinate);
       }
    }

    private void setCoordinate(Location location) {
        if (location == null){
            return;
        }

        coordinate = new Coordinate(location);
    }
}
