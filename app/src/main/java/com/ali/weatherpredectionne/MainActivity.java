package com.ali.weatherpredectionne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;

import com.ali.weatherpredectionne.Adapters.DaysForecastAdapter;
import com.ali.weatherpredectionne.Adapters.HoursForecastsAdapter;
import com.ali.weatherpredectionne.entity.Forecast;
import com.ali.weatherpredectionne.entity.WeatherInfo;
import com.ali.weatherpredectionne.fragments.PrimaryWeatherInfoFragment;
import com.ali.weatherpredectionne.fragments.SecondaryWeatherInfoFragment;
import com.ali.weatherpredectionne.network.NetworkUtils;
import com.ali.weatherpredectionne.utils.OpenWeatherDateParser;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;
    private HeaderFragmentAdapter mHeaderFragmentAdapter;

    private DaysForecastAdapter mDaysForecastAdapter;
    private HoursForecastsAdapter mHoursForecastsAdapter;

    private RecyclerView mHoursForecastsRecyclerView;
    private RecyclerView mDaysForecastsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mViewPager = findViewById(R.id.pager);
        mHeaderFragmentAdapter = new HeaderFragmentAdapter(mFragmentManager);
        mViewPager.setAdapter(mHeaderFragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.indicator);
        tabLayout.setupWithViewPager(mViewPager, true);

        mHoursForecastsAdapter = new HoursForecastsAdapter(this);
        mHoursForecastsRecyclerView = findViewById(R.id.rv_hours_forecast);
        mHoursForecastsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mHoursForecastsRecyclerView.setAdapter(mHoursForecastsAdapter);

        mDaysForecastAdapter = new DaysForecastAdapter(this);
        mDaysForecastsRecyclerView = findViewById(R.id.rv_days_forecast);
        mDaysForecastsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDaysForecastsRecyclerView.setAdapter(mDaysForecastAdapter);

        List<Forecast> hourForecasts = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            hourForecasts.add(new Forecast());
        }

        List<List<Forecast>> daysForecasts = new ArrayList<>();
        for (int i=0; i<4; i++){
            List<Forecast> hoursForecasts = new ArrayList<>();
            for (int w=0; w<8; w++){
                hoursForecasts.add(new Forecast());
            }
            daysForecasts.add(hoursForecasts);
        }

        mHoursForecastsAdapter.updateData(hourForecasts);
        mDaysForecastAdapter.updateData(daysForecasts);

        requestWeatherInfo();

    }

    private void requestWeatherInfo(){
        new WeatherDateGetTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class WeatherDateGetTask extends AsyncTask<Void, Integer, WeatherInfo> {

        @Override
        protected WeatherInfo doInBackground(Void... voids) {
            URL weatherUrl = NetworkUtils.getWeatherUrl(MainActivity.this);
            String weatherJasonResponse = null;
            WeatherInfo weatherInfo = null;

            try {
                weatherJasonResponse = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
                weatherInfo = OpenWeatherDateParser.getWeatherInfoObjectFromJson(weatherJasonResponse);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return weatherInfo;
        }

        @Override
        protected void onPostExecute(WeatherInfo weatherInfo) {
            super.onPostExecute(weatherInfo);
            if (weatherInfo != null){
                mHeaderFragmentAdapter.updateData(weatherInfo);
            }
        }
    }

    //Class of Fragment Adapter
    static class HeaderFragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public HeaderFragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new PrimaryWeatherInfoFragment();
                case 1:
                    return new SecondaryWeatherInfoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fragments.add(fragment);
            return fragment;
        }

        public void updateData(WeatherInfo weatherInfo){
            ((PrimaryWeatherInfoFragment)fragments.get(0)).updateWeatherInfo(weatherInfo);
            ((SecondaryWeatherInfoFragment)fragments.get(1)).updateWeatherInfo(weatherInfo);

        }
    }
}