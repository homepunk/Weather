package homepunk.work.geolocation.presenters;

import android.location.Location;
import android.util.Log;

import homepunk.work.geolocation.data.MetaWeatherRepository;
import homepunk.work.geolocation.model.Latlng;
import homepunk.work.geolocation.model.Weather;
import homepunk.work.geolocation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.ui.interfaces.IWeatherView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherViewPresenter implements IWeatherViewPresenter {
    private final MetaWeatherRepository weatherRepository;
    private IWeatherView view;

    public WeatherViewPresenter() {
        this.weatherRepository = new MetaWeatherRepository();
    }

    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void getWeatherByLocation(Location location) {
        Latlng latlng = new Latlng(location.getLatitude(), location.getLongitude());

        Log.d("PRESENTER", String.valueOf(latlng));
        weatherRepository.getCurrentWeatherByLatlng(latlng)
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

}
