package com.ali.weatherpredectionne.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.weatherpredectionne.R;
import com.ali.weatherpredectionne.entity.Forecast;

import java.util.List;

public class HoursForecastsAdapter extends RecyclerView.Adapter<HoursForecastsAdapter.ForecastsAdapterViewHolder> {

    private Context mContext;
    private List<Forecast> mForecasts;

    public HoursForecastsAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public ForecastsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hour_forecast, parent, false);
        return new ForecastsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastsAdapterViewHolder holder, int position) {
        int weatherImageId = R.drawable.ic_broken_clouds;
        holder.weatherIcon.setImageResource(weatherImageId);
        String time = "9:00 AM";
        holder.time.setText(time);
        String temp = "19°";
        holder.temp.setText(temp);
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    static class ForecastsAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView  weatherIcon;
        TextView time;
        TextView temp;
        public ForecastsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherIcon = itemView.findViewById(R.id.weather_icon_hours);
            time = itemView.findViewById(R.id.time_hours);
            temp = itemView.findViewById(R.id.temperature_hours);
        }
    }

    public void updateData(List<Forecast> forecasts) {
        this.mForecasts = forecasts;
        notifyDataSetChanged();
    }
}
