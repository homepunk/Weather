package homepunk.work.geolocation.presentation.dagger;

import javax.inject.Singleton;

import dagger.Component;
import homepunk.work.geolocation.presentation.dagger.modules.AppModule;
import homepunk.work.geolocation.presentation.dagger.modules.DataModule;
import homepunk.work.geolocation.presentation.dagger.modules.LocationModule;
import homepunk.work.geolocation.presentation.dagger.modules.PresentersModule;
import homepunk.work.geolocation.presentation.presenters.MapPresenter;
import homepunk.work.geolocation.presentation.presenters.WeatherPresenter;
import homepunk.work.geolocation.presentation.views.WeatherActivity;

@Singleton
@Component(modules = {DataModule.class, PresentersModule.class, AppModule.class, LocationModule.class})
public interface AppComponent {
    WeatherActivity plus(WeatherActivity activity);
    WeatherPresenter plus(WeatherPresenter presenter);
    MapPresenter plus(MapPresenter presenter);
}
