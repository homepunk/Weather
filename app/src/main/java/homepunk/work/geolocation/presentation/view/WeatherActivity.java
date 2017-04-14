package homepunk.work.geolocation.presentation.view;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homepunk.work.geolocation.R;
import homepunk.work.geolocation.presentation.model.Weather;
import homepunk.work.geolocation.presentation.model.WeatherConsolidated;
import homepunk.work.geolocation.presentation.presenters.WeatherPresenter;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.utils.BitmapLoader;
import homepunk.work.geolocation.presentation.view.interfaces.IWeatherView;
import io.nlopez.smartlocation.location.providers.LocationManagerProvider;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static io.nlopez.smartlocation.SmartLocation.Builder;
import static io.nlopez.smartlocation.SmartLocation.LocationControl;

public class WeatherActivity extends AppCompatActivity implements IWeatherView, OnMapReadyCallback {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.lattlng_weather_textview)
    TextView latLngWeatherTV;
    @Bind(R.id.lattlng_weather_icon)
    ImageView latLngWeatherIconIV;

    private Location location;
    private RxPermissions rxPermissions;
    private LocationControl locationControl;
    private SupportMapFragment mapFragment;
    private IWeatherViewPresenter presenter;
    private PopupWindow popupWindow;
    private ImageView ivPopupIcon;
    private TextView tvPopupInfo;
    private Marker marker;
    private View popupLayout;
    private BitmapLoader bitmapLoader;


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
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        popupLayout = inflater.inflate(R.layout.item_popup, (ViewGroup) findViewById(R.id.popup));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                WeatherActivity.this.marker = marker;

                ivPopupIcon = (ImageView) popupLayout.findViewById(R.id.popup_weather_icon);
                tvPopupInfo = (TextView) popupLayout.findViewById(R.id.popup_weather_textview);

                return popupLayout;
            }

            @Override
            public View getInfoContents(Marker marker) {

                if (WeatherActivity.this.marker != null && WeatherActivity.this.marker.isInfoWindowShown()) {
                    WeatherActivity.this.marker.hideInfoWindow();
                    WeatherActivity.this.marker.showInfoWindow();
                }

                return null;
            }
        });

        presenter.setMap(map);
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
    public void onMarkerChangeResult(Weather weather, Marker marker) {
        if (weather != null) {
            WeatherConsolidated weatherConsolidated = weather.getFirstConsolidatedWeather();

            if (weatherConsolidated != null) {
                StringBuilder sb = new StringBuilder()
                        .append(weatherConsolidated.getWeatherStateName())
                        .append(" in ")
                        .append(weather.getTitle());
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


    private void checkPermissions() {
        if (locationControl.state().isAnyProviderAvailable()) {
            rxPermissions.request(ACCESS_FINE_LOCATION)
                    .subscribe(aBoolean -> {
                        Timber.i("permission granted:  " + String.valueOf(aBoolean));
                    });
        }
    }

    private void setUpUI() {
        presenter = new WeatherPresenter(this);

        presenter.setView(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        locationControl = new Builder(this)
                .logging(true)
                .build()
                .location(new LocationManagerProvider());

        rxPermissions = new RxPermissions(this);

        bitmapLoader = new BitmapLoader(this);
        bitmapLoader.setPicassoInstance(Picasso.with(this));
    }

    private void locate() {
        checkPermissions();

        locationControl.start(location1 -> WeatherActivity.this.location = location1);

        if (location == null) {
            location = locationControl.getLastLocation();
        }

        if (location != null) {
            presenter.getWeatherByLatLng(getCurrentLatLng());
        }

    }

    private LatLng getCurrentLatLng() {
        Timber.i("Current location: " + (location != null ? location.getLatitude() + ", " + location.getLongitude() : null));
        return location != null ? new LatLng(location.getLatitude(), location.getLongitude()) : new LatLng(0, 0);
    }

}
