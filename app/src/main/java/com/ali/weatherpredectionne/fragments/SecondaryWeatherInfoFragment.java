package com.ali.weatherpredectionne.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ali.weatherpredectionne.R;
import com.ali.weatherpredectionne.entity.WeatherInfo;
import com.ali.weatherpredectionne.utils.WeatherUtils;

public class SecondaryWeatherInfoFragment extends Fragment {

    private TextView mWindTextView;
    private TextView mPressureTextView;
    private TextView mHumidityTextView;

    private WeatherInfo weatherInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secondary_weather_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view == null)
            return;

        mHumidityTextView = view.findViewById(R.id.humidity);
        mPressureTextView = view.findViewById(R.id.pressure);
        mWindTextView = view.findViewById(R.id.wind);
        showWeatherInfo();
    }

    public void updateWeatherInfo(WeatherInfo weatherInfo){
        this.weatherInfo = weatherInfo;
        showWeatherInfo();
    }
    private void showWeatherInfo(){

        if (weatherInfo == null)
            return;

        double windy_direction = weatherInfo.getWind().getDeg();
        double wind_speed = weatherInfo.getWind().getSpeed();
        String wind_string = WeatherUtils.getFormattedWind(getContext(), wind_speed, windy_direction);
        mWindTextView.setText(wind_string);

        double pressure_degree = weatherInfo.getMain().getPressure();
        String pressure_string = getString(R.string.format_pressure, pressure_degree);
        mPressureTextView.setText(pressure_string);

        double humidity_degree = weatherInfo.getMain().getHumidity();
        String humidity_string = getString(R.string.format_humidity, humidity_degree);
        mHumidityTextView.setText(humidity_string);
    }

}
