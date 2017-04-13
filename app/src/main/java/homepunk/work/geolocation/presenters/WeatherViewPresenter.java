package homepunk.work.geolocation.presenters;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import homepunk.work.geolocation.data.WeatherRepository;
import homepunk.work.geolocation.model.Weather;
import homepunk.work.geolocation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.ui.interfaces.IWeatherView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherViewPresenter implements IWeatherViewPresenter {
    public static final float DEFAULT_ZOOM = 14.0f;
    private final WeatherRepository weatherRepository;
    private IWeatherView view;
    private LatLng initLatLng;
    private LatLng targetLatLng;
    private GoogleMap map;

    public WeatherViewPresenter() {
        this.weatherRepository = new WeatherRepository();
    }

    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void getWeatherByLocation(LatLng latLng) {
        this.initLatLng = latLng;

        weatherRepository.getCurrentWeatherByLatlng(latLng)
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

    @Override
    public void setMap(GoogleMap map) {
        this.map = map;

        setUpMap();
        moveOnMap(initLatLng);
    }

    private void setUpMap(){
        if (map != null) {
            map.setOnCameraIdleListener(() -> targetLatLng = map.getCameraPosition().target);
        }
    }

    private void moveOnMap(LatLng destination) {
        if (initLatLng != null) {
            map.addMarker(new MarkerOptions().position(destination).title("Marker in Kharkiv"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, DEFAULT_ZOOM));
        }
    }
}
