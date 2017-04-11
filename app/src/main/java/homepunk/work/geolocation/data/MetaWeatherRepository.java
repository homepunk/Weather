package homepunk.work.geolocation.data;


import java.util.List;

import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.model.WeatherLocation;
import homepunk.work.geolocation.model.Latlng;
import homepunk.work.geolocation.model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Single;
import rx.functions.Func1;

import static homepunk.work.geolocation.data.Constants.BASE_URL;

public class MetaWeatherRepository implements IMetaWeatherModel {
    private final MetaWeatherApi weatherApi;

    public MetaWeatherRepository() {
        this.weatherApi = createMetaWeatherApi();
    }

    @Override
    public Single<Weather> getCurrentWeatherByLatlng(Latlng latlng) {
        return weatherApi.fetchLocation(latlng.toString())
                .flatMap(new Func1<List<WeatherLocation>, Single<? extends Weather>>() {
                    @Override
                    public Single<? extends Weather> call(List<WeatherLocation> metaLocations) {
                        return weatherApi.fetchWeatherByWoeid((int) metaLocations.get(0).getWoeid());
                    }
                });
    }


    private MetaWeatherApi createMetaWeatherApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(MetaWeatherApi.class);
    }
}
