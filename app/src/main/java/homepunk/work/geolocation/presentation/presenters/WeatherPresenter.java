package homepunk.work.geolocation.presentation.presenters;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import homepunk.work.geolocation.data.repository.WeatherRepository;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.utils.BitmapLoader;
import homepunk.work.geolocation.presentation.view.interfaces.IWeatherView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static homepunk.work.geolocation.presentation.utils.LocationUtils.moveCameraToLatLng;
import static homepunk.work.geolocation.presentation.utils.LocationUtils.setMarkerOnMap;
import static homepunk.work.geolocation.presentation.utils.MapUtils.getCusomizedMap;

public class WeatherPresenter implements IWeatherViewPresenter {
    private final WeatherRepository repository;
    private IWeatherView view;
    private GoogleMap map;
    private BitmapLoader bitmapLoader;

    public WeatherPresenter(Context context) {
        this.repository = new WeatherRepository();
        this.bitmapLoader = new BitmapLoader();
        this.bitmapLoader.setPicassoInstance(Picasso.with(context));
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
        Timber.i("Recieved latLng: " + latLng.toString());

        repository.
                getCurrentWeatherByLatLng(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather -> {
                    if (view != null) {
                       view.onResult(weather);
                    }
                });
    }

    private void setUpMapListeners() {
        if (map != null) {
            map.setOnMapClickListener(latLng -> {
                moveCameraToLatLng(map, latLng);
                repository
                        .getCurrentWeatherByLatLng(latLng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(weather -> {
                            if (view != null) {
                                Timber.i("Marker recieved " + weather.getTitle());

                                bitmapLoader
                                        .getBitmap(weather.getFullWeatherIconPath())
                                        .subscribe(bitmap -> {
                                            setMarkerOnMap(map, new MarkerOptions()
                                                    .position(latLng)
                                                    .snippet(weather.getFullWeatherIconPath())
                                                    .title(weather.getTitle()));
                                        });
                            }
                        });
            });

            map.setOnMarkerClickListener(marker -> {
                LatLng latLng = marker.getPosition();
                Timber.i("Marker " + latLng.toString());
               return true;
            });
        }
    }


}
