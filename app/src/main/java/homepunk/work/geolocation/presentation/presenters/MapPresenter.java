package homepunk.work.geolocation.presentation.presenters;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import homepunk.work.geolocation.data.repository.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.data.repository.WeatherRepository;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import homepunk.work.geolocation.presentation.presenters.interfaces.IMapPresenter;
import homepunk.work.geolocation.presentation.utils.loaders.BitmapLoader;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;

import static homepunk.work.geolocation.presentation.utils.MapUtils.getUpdatedMarkerOptions;
import static homepunk.work.geolocation.presentation.utils.MapUtils.moveCameraToLatLng;
import static homepunk.work.geolocation.presentation.utils.MapUtils.setMarkerOnMap;
import static homepunk.work.geolocation.presentation.utils.RxUtils.applySchedulers;

/**
 * Created by Homepunk on 19.04.2017.
 */

public class MapPresenter implements IMapPresenter {
    private GoogleMap map;
    private IWeatherView view;
    private BitmapLoader bitmapLoader;
    private IMetaWeatherModel repository;

    public MapPresenter(Context context) {
        this.bitmapLoader = new BitmapLoader();
        this.bitmapLoader.setPicassoInstance(Picasso.with(context));
        this.repository = new WeatherRepository();
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
        if (map == null || view == null) {
            return;
        }

        moveCameraToLatLng(map, coordinate);

        repository.getCurrentWeather(coordinate)
                  .compose(applySchedulers())
                  .subscribe(MapPresenter.this::loadBitmap);
    }

    private void loadBitmap(TotalWeather weather) {
        bitmapLoader.getBitmap(weather.getFullWeatherIconPath())
                    .subscribe(bitmap -> setMarkerOnMap(map, getUpdatedMarkerOptions(weather)));
    }

}
