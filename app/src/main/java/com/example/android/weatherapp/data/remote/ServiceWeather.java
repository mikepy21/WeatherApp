package com.example.android.weatherapp.data.remote;

import com.example.android.weatherapp.data.entities.WeatherObj;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceWeather {
    String BASE_URL = "http://api.openweathermap.org/";


    @GET("/data/2.5/forecast")
    Call<WeatherObj> getForecast(@Query("zip") int zipcode,@Query("appid") String apiKey, @Query("units") String units);

    @GET("/data/2.5/forecast")
    Observable<WeatherObj> getForecastObserver(@Query("zip") int zipcode, @Query("appid") String apiKey, @Query("units") String units);

    class Factory {
        private Retrofit createRetrofit(){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        public ServiceWeather create(){
            return createRetrofit().create(ServiceWeather.class);
        }
    }

}
