package com.example.android.watherapp.data;

import com.example.android.watherapp.data.entities.WeatherObj;

public interface RepositoryContract {
    void onSucces(WeatherObj weatherObj);
    void onError();
}
