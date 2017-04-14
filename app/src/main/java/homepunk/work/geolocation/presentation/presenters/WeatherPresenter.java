package homepunk.work.geolocation.presentation.presenters;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import homepunk.work.geolocation.data.repository.WeatherRepository;
import homepunk.work.geolocation.presentation.model.Weather;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.utils.BitmapLoader;
import homepunk.work.geolocation.presentation.view.interfaces.IWeatherView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static homepunk.work.geolocation.presentation.utils.LocationUtils.moveCameraToLatLng;
import static homepunk.work.geolocation.presentation.utils.LocationUtils.setMarkerOnMap;
import static homepunk.work.geolocation.presentation.utils.MapUtils.getCusomizedMap;

public class WeatherPresenter implements IWeatherViewPresenter {
    private final WeatherRepository repository;
    private final Context context;
    private IWeatherView view;
    private GoogleMap map;
    private BitmapLoader bitmapLoader;

    public WeatherPresenter(Context context) {
        this.context = context;
        this.repository = new WeatherRepository();
    }

    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void setMap(GoogleMap map) {
        this.map = getCusomizedMap(map);

        setUpMapListeners();
    }

    @Override
    public void getWeatherByLatLng(LatLng latLng) {
        repository.
                getCurrentWeatherByLatLng(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.onError(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (view != null) {
                            view.onResult(weather);
                        }
                    }
                });
    }

    private void setUpMapListeners() {
        if (map != null) {
            map.setOnMapClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                setMarkerOnMap(map, markerOptions);
                moveCameraToLatLng(map, latLng);
            });

            map.setOnMarkerClickListener(marker -> {
                LatLng latLng = marker.getPosition();
                repository
                        .getCurrentWeatherByLatLng(latLng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Weather>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable error) {
                                if (view != null) {
                                    view.onError(error.getLocalizedMessage());
                                }
                            }

                            @Override
                            public void onNext(Weather weather) {
                                if (view != null) {
                                    view.onMarkerChangeResult(weather, marker);

                                    bitmapLoader.getBitmap(weather.getFullWeatherIconPath()).subscribe(bitmap -> {
                                        map.addMarker(new MarkerOptions()
                                                .title(weather.getTitle())
                                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                    },Throwable::printStackTrace);

                            }
                        }
            });

            return true;
        });
    }
}


}
