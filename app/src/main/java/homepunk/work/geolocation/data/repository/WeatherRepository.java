package homepunk.work.geolocation.data.repository;


import com.google.android.gms.maps.model.LatLng;

import homepunk.work.geolocation.data.api.WeatherApi;
import homepunk.work.geolocation.data.interfaces.IMetaWeatherModel;
import homepunk.work.geolocation.presentation.model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Single;

import static homepunk.work.geolocation.data.Constants.BASE_URL;
import static homepunk.work.geolocation.presentation.utils.LocationUtils.convertToString;

public class WeatherRepository implements IMetaWeatherModel {
    private final WeatherApi weatherApi;

    public WeatherRepository() {
        this.weatherApi = createMetaWeatherApi();
    }

    @Override
    public Single<Weather> getCurrentWeatherByLatLng(LatLng latlng) {
        return weatherApi
                .fetchLocation(convertToString(latlng))
                .map(weatherLocations ->
                        weatherLocations.get(0))
                .flatMap(weatherLocation ->
                        weatherApi.fetchWeatherByWoeid((int) weatherLocation.getWoeid()));
    }


    private WeatherApi createMetaWeatherApi() {
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

        return retrofit.create(WeatherApi.class);
    }
}
