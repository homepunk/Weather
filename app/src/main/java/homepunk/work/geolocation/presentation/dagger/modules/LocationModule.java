package homepunk.work.geolocation.presentation.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationManagerProvider;

/**
 * Created by Homepunk on 19.04.2017.
 */

@Module
public class LocationModule {
    @Provides
    @Singleton
    SmartLocation.LocationControl provideLocationControl(Context context){
        return new SmartLocation.Builder(context)
                .logging(true)
                .build()
                .location(new LocationManagerProvider());
    }

}
