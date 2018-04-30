package com.example.android.weatherapp.data;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.watherapp.R;
import com.example.android.weatherapp.data.entities.ListWeather;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{

    ArrayList<String> days;
    List<ListWeather> listObj;
    Map<String,Double> avgDays = new HashMap<>();

    public DayAdapter(ArrayList<String> days) {
        this.days = days;
    }

    @Override
    public DayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayAdapter.ViewHolder holder, int position) {
        holder.bind(days.get(position));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void updateData(ArrayList newData){
        days.clear();
        days.addAll(newData);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDay;
        TextView textTemp;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.r_row_day);
            textTemp = itemView.findViewById(R.id.r_row_temp);
             imageView =itemView.findViewById(R.id.a_r_icon);
        }

        public void bind(String string) {
            String formatData;
            try {

                formatData=getDate(string);
                formatData = formatData.substring(0,11);
            } catch (ParseException e) {
                formatData="";
                e.printStackTrace();
            }
            textDay.setText(formatData);
            textTemp.setText(String.valueOf(avgDays.get(string)));
//            Picasso.get().load("http://openweathermap.org/img/w/10d.png").into(imageView);


            Picasso.get().load(R.drawable.weather_256).into(imageView);
        }
    }

    public void analizeData(List<ListWeather> listAPI){
        for(String dateX:days){
            double avgTemp = 0;
            int nTemp = 0;
            for (ListWeather listWeather: listAPI){
                if(listWeather.getDtTxt().contains(dateX)){
                    avgTemp+=listWeather.getMain().getTemp();
                    nTemp++;
                }
            }


            avgDays.put(dateX, round(avgTemp/nTemp,1));
        }
        notifyDataSetChanged();
        Log.d("TAG", "analizeData: "+avgDays.toString());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public String getDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(dateString);
        Log.d("AGssss", "getDate: " + date.toString());
        return date.toString();
    }
}
