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

public class DaysForecastAdapter extends RecyclerView.Adapter<DaysForecastAdapter.ForecastAdapterViewHolder> {

    private final Context mContext;
    private List<List<Forecast>> mForecasts;

    public DaysForecastAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_day_forecast, parent, false);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {

        int weatherImageId  = R.drawable.ic_broken_clouds;
        holder.icon.setImageResource(weatherImageId);
        String dateString = "Wed, 24 April";
        holder.date.setText(dateString);
        String description = "Cloudy";
        holder.state.setText(description);
        String highTemperatureString = "19°";
        holder.highTemp.setText(highTemperatureString);
        String lowTemperatureString = "10°";
        holder.lowTemp.setText(lowTemperatureString);
    }

    @Override
    public int getItemCount() {
        if (mForecasts == null)
            return 0;
        else
            return mForecasts.size();
    }

    static class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView state;
        TextView highTemp;
        TextView lowTemp;
        ImageView icon;
        public ForecastAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_week);
            state = itemView.findViewById(R.id.state);
            highTemp = itemView.findViewById(R.id.higher_temp);
            lowTemp = itemView.findViewById(R.id.smallest_temp);
            icon = itemView.findViewById(R.id.weather_icon_day);
        }
    }

    public void updateData(List<List<Forecast>> forecasts) {
        this.mForecasts = forecasts;
        notifyDataSetChanged();
    }
}
