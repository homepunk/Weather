package homepunk.work.geolocation.presentation;

import android.app.Application;
import android.content.Context;

import homepunk.work.geolocation.presentation.dagger.AppComponent;
import homepunk.work.geolocation.presentation.dagger.DaggerAppComponent;
import homepunk.work.geolocation.presentation.dagger.modules.AppModule;
import timber.log.Timber;

public class App extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        App app = (App) context.getApplicationContext();
        if (app.component == null)
            app.component = app.createComponent();

        return app.component;
    }
}
