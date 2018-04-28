package com.example.aptivist.weatherapp.data.remote;

import com.example.android.watherapp.data.entities.WeatherObj;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceWeather {
    String BASE_URL = "http://api.openweathermap.org/";


    @GET("/data/2.5/forecast")
    Call<WeatherObj> getForecast(@Query("zip") int zipcode, @Query("appid") String apiKey);

    class Factory {
        private Retrofit createRetrofit(){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public ServiceWeather create(){
            return createRetrofit().create(ServiceWeather.class);
        }
    }

}
