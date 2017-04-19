package homepunk.work.geolocation.presentation.presenters;

import homepunk.work.geolocation.data.repository.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.data.repository.WeatherRepository;
import homepunk.work.geolocation.presentation.models.Coordinate;
import homepunk.work.geolocation.presentation.models.TotalWeather;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;
import homepunk.work.geolocation.presentation.views.interfaces.IWeatherView;
import rx.SingleSubscriber;

import static homepunk.work.geolocation.presentation.utils.RxUtils.applySchedulers;

public class WeatherPresenter implements IWeatherViewPresenter {
    private IWeatherView view;
    private IMetaWeatherModel repository;

    public WeatherPresenter() {
        this.repository = new WeatherRepository();
    }

    @Override
    public void setView(IWeatherView view) {
        this.view = view;
    }

    @Override
    public void getCurrentWeather(Coordinate coordinate) {
        if (coordinate.isEmpty() || view == null) {
            return;
        }

        repository.getCurrentWeather(coordinate)
                .compose(applySchedulers())
                .subscribe(getSingleSubscriber());
    }

    private SingleSubscriber<? super TotalWeather> getSingleSubscriber() {
        return new SingleSubscriber<TotalWeather>() {
            @Override
            public void onSuccess(TotalWeather weather) {
                view.onResult(weather);
            }

            @Override
            public void onError(Throwable error) {
                view.onError(error.getLocalizedMessage());
            }
        };
    }


}
