package homepunk.work.geolocation.presentation.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import homepunk.work.geolocation.presentation.App;

/**
 * Created by Homepunk on 19.04.2017.
 */

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return app;
    }
}