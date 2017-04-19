package homepunk.work.geolocation.presentation.presenters;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.presentation.App;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.presenters.interfaces.IMapPresenter;
import homepunk.work.geolocation.presentation.utils.loaders.BitmapLoader;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;
import timber.log.Timber;

import static homepunk.work.geolocation.presentation.utils.MapUtils.getMarkerOptions;
import static homepunk.work.geolocation.presentation.utils.MapUtils.moveCameraToLatLng;
import static homepunk.work.geolocation.presentation.utils.MapUtils.setMarkerOnMap;
import static homepunk.work.geolocation.presentation.utils.RxUtils.applySchedulers;

/**
 * Created by Homepunk on 19.04.2017.
 */

public class MapPresenter implements IMapPresenter {
    @Inject IMetaWeatherModel repository;

    private GoogleMap map;
    private IWeatherView view;
    private BitmapLoader bitmapLoader;

    public MapPresenter(Context context) {
        App.getAppComponent(context).plus(this);
        this.bitmapLoader = new BitmapLoader();
        this.bitmapLoader.setPicassoInstance(Picasso.with(context));
    }


    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void setMap(GoogleMap googleMap) {
        this.map = googleMap;
    }

    @Override
    public void onMapClick(Coordinate coordinate) {
        if (map != null) {
            moveCameraToLatLng(map, coordinate);

            repository.getCurrentWeather(coordinate)
                    .compose(applySchedulers())
                    .subscribe(weather -> {
                        Timber.i("Recived " + weather.getTitle() + " with " + weather.getLattLong());
                        if (view != null) {
                            bitmapLoader.getBitmap(weather.getFullWeatherIconPath())
                                    .subscribe(bitmap ->
                                            setMarkerOnMap(map, getMarkerOptions(weather, coordinate)));
                        }
                    });
        }
    }

}
