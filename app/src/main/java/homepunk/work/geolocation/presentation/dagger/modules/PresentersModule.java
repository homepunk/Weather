package homepunk.work.geolocation.presentation.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import homepunk.work.geolocation.presentation.presenters.MapPresenter;
import homepunk.work.geolocation.presentation.presenters.WeatherPresenter;
import homepunk.work.geolocation.presentation.presenters.interfaces.IMapPresenter;
import homepunk.work.geolocation.presentation.presenters.interfaces.IWeatherViewPresenter;

/**
 * Created by Homepunk on 19.04.2017.
 */

@Module
public class PresentersModule {
    @Provides
    @Singleton
    IWeatherViewPresenter provideIWeatherViewPresenter(Context context){
        return new WeatherPresenter(context);
    }

    @Provides
    @Singleton
    IMapPresenter provideIMapPresenter(Context context) {
        return new MapPresenter(context);
    }
}
