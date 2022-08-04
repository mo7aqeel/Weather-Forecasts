package com.ali.weatherpredectionne.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ali.weatherpredectionne.R;
import com.ali.weatherpredectionne.entity.WeatherInfo;
import com.ali.weatherpredectionne.utils.CustomDateUtils;
import com.ali.weatherpredectionne.utils.WeatherUtils;

public class PrimaryWeatherInfoFragment extends Fragment {

    private ImageView mIconView;
    private TextView mCityNameTextView;
    private TextView mDateView;
    private TextView mDescriptionWeatherView;
    private TextView mTempTextView;
    private TextView mHighLowTempView;

    private WeatherInfo weatherInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_primary_weather_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view == null)
            return;

        mIconView = view.findViewById(R.id.weather_icon);
        mCityNameTextView = view.findViewById(R.id.city);
        mDateView = view.findViewById(R.id.date);
        mDescriptionWeatherView = view.findViewById(R.id.weather_description);
        mTempTextView = view.findViewById(R.id.temperature);
        mHighLowTempView = view.findViewById(R.id.high_low_temperature);

        showWeatherInfo();
    }

    public void updateWeatherInfo(WeatherInfo weatherInfo){
        this.weatherInfo = weatherInfo;
        showWeatherInfo();
    }

    private void showWeatherInfo(){
        if (weatherInfo == null)
            return;

        int weatherImageId = WeatherUtils.getWeatherIcon(weatherInfo.getWeatherList().get(0).getIcon());
        mIconView.setImageResource(weatherImageId);

        String city_name = weatherInfo.getName();
        mCityNameTextView.setText(city_name);

        String dateString = CustomDateUtils.getFriendlyDateString(getContext(), weatherInfo.getDt(), false);
        mDateView.setText(dateString);

        String description = weatherInfo.getWeatherList().get(0).getDescription();
        mDescriptionWeatherView.setText(description);

        String temperatureString = getString(R.string.format_temperature, weatherInfo.getMain().getTemp());
        mTempTextView.setText(temperatureString);

        mHighLowTempView.setText(getString(R.string.high_low_temperature, weatherInfo.getMain().getMax_temp(), weatherInfo.getMain().getMin_temp()));
    }
}
