package com.example.android.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.watherapp.R;
import com.example.android.weatherapp.data.DayAdapter;
import com.example.android.weatherapp.data.WeatherRepository;
import com.example.android.weatherapp.data.entities.ListWeather;
import com.example.android.weatherapp.data.entities.WeatherObj;

import java.util.ArrayList;
import java.util.LinkedHashSet;



public class MainActivity extends AppCompatActivity  implements MainContract.View{
    final String TAG = "TAG_";


    private MainContract.Presenter presenter;
    private WeatherRepository weatherRepository;


    private TextView cityText;
    private Button buttonSearch;
    private EditText editTextZip;

    private RecyclerView recyclerView;
    private ArrayList<String> days= new ArrayList<>();
    private DayAdapter dayAdapter = new DayAdapter(days);
    WeatherObj weatherGlob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = findViewById(R.id.a_main_city);
        buttonSearch = findViewById(R.id.a_main_btn);
        editTextZip = findViewById(R.id.a_main_zipCode);

        weatherRepository = new WeatherRepository();

        presenter = new MainPresenter(weatherRepository);




        buttonSearch.setOnClickListener(v->searchWeather());

        recyclerView = findViewById(R.id.a_main_recyclerWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dayAdapter);

        presenter.attachView(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void searchWeather() {
        int zipToSearch;
        try {
            zipToSearch =   Integer.parseInt(String.valueOf(editTextZip.getText()));
            presenter.getWeather(zipToSearch);
        }catch (Exception e){
            showError("The code post field need to be an integer");
        }

    }


    private void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessfulData( WeatherObj weatherObj) {
        if (weatherObj!=null){
            weatherGlob = weatherObj;
            cityText.setText(weatherObj.getCity().getName()+","+weatherObj.getCity().getCountry());
            days.clear();
            for (ListWeather aux:weatherObj.getListWeather()){
                days.add(aux.getDtTxt()!=null?aux.getDtTxt().substring(0,aux.getDtTxt().length()-8).trim():"");
            }
            days = new ArrayList<String>(new LinkedHashSet<String>(days));
            //Log.d(TAG, "onResponse: "+days.toString());

            dayAdapter.analizeData(weatherObj.getListWeather());
            dayAdapter.updateData(days);

        }else{
            weatherGlob = null;
            days = new ArrayList<String>();
            Toast.makeText(MainActivity.this, "This zip code not exist in US", Toast.LENGTH_SHORT).show();
        }
        hideKeyBoard();
    }




}
