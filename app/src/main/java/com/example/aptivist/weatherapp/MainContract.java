package com.example.android.watherapp;

import com.example.android.watherapp.data.entities.WeatherObj;

import java.util.ArrayList;

public interface MainContract {

    interface View {
        void showError(String error);

        void onSuccessfulData( WeatherObj weatherObj);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void getWeather(int zipText);

    }
}
