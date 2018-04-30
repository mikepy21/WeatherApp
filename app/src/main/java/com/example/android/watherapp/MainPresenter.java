package com.example.android.watherapp;

import com.example.android.watherapp.data.RepositoryContract;
import com.example.android.watherapp.data.WeatherRepository;
import com.example.android.watherapp.data.entities.WeatherObj;

public class MainPresenter implements MainContract.Presenter ,RepositoryContract{

    private MainContract.View view;
    private WeatherRepository userRepository;

    public MainPresenter(WeatherRepository userRepository) {
        this.userRepository = userRepository;
        this.userRepository.setListener(this);
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getWeather(int zipText) {
        userRepository.searchWeather(zipText);
    }


    @Override
    public void onSucces(WeatherObj weatherObj) {

        view.onSuccessfulData(weatherObj);
    }

    @Override
    public void onError() {
        view.showError("");
    }
}
