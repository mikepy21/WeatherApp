package com.example.android.weatherapp;

import com.example.android.weatherapp.data.entities.WeatherObj;

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
