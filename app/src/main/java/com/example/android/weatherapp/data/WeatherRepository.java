package com.example.android.weatherapp.data;

import android.os.StrictMode;

import com.example.android.weatherapp.data.entities.WeatherObj;
import com.example.android.weatherapp.data.remote.ServiceWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    RepositoryContract repositoryContract;

    public WeatherRepository() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
    public void setListener( RepositoryContract repositoryContract){
        this.repositoryContract = repositoryContract;
    }

    public void searchWeather(int zipText) {

        try{

            ServiceWeather serviceWeather = new ServiceWeather.Factory().create();
            serviceWeather.getForecast(zipText,"3532853227d31c8fb45b437003de6b0c","metric")

                    .enqueue(new Callback<WeatherObj>() {
                        @Override
                        public void onResponse(Call<WeatherObj> call, Response<WeatherObj> response) {

                            WeatherObj weatherObj = response.body();
                            repositoryContract.onSucces(weatherObj);

                        }

                        @Override
                        public void onFailure(Call<WeatherObj> call, Throwable t) {

                            repositoryContract.onError();


                        }
                    });
        }catch (Exception e){
            //Show Error

            repositoryContract.onError();
        }


    }

}
