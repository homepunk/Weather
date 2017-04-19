package homepunk.work.geolocation.presentation.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import homepunk.work.geolocation.data.api.WeatherApi;
import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.data.repository.WeatherRepository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static homepunk.work.geolocation.data.Constants.BASE_URL;

/**
 * Created by Homepunk on 19.04.2017.
 */

@Module
public class DataModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptror() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    IMetaWeatherModel provideWeatherRepository(WeatherApi weatherApi){
        return new WeatherRepository(weatherApi);
    }
}
