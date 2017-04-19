package homepunk.work.geolocation.presentation.presenters;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.data.repository.WeatherRepository;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;
import rx.SingleSubscriber;

import static homepunk.work.geolocation.presentation.utils.RxUtils.applySchedulers;

public class WeatherPresenter implements IWeatherViewPresenter {
    private IMetaWeatherModel repository;

    private IWeatherView view;
    private LatLng zeroLatLng;

    public WeatherPresenter(Context context) {
        this.repository = new WeatherRepository();
        this.zeroLatLng = new LatLng(0, 0);
    }

    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void getCurrentWeather(Coordinate coordinate) {
        if (coordinate.equals(zeroLatLng)) {
            return;
        }

        repository.getCurrentWeather(coordinate)
                .compose(applySchedulers())
                .subscribe(new SingleSubscriber<TotalWeather>() {
                    @Override
                    public void onSuccess(TotalWeather weather) {
                        if (view != null) {
                            view.onResult(weather);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        if (view != null) {
                            view.onError(error.getLocalizedMessage());
                        }
                    }
                });
    }


}
