package com.example.android.weatherapp.data;

import com.example.android.weatherapp.data.entities.WeatherObj;

public interface RepositoryContract {
    void onSucces(WeatherObj weatherObj);
    void onError();
}
