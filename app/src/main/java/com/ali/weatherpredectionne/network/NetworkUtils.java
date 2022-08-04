package com.ali.weatherpredectionne.network;

import android.content.Context;
import android.net.Uri;

import com.ali.weatherpredectionne.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static final String WEATHER_ENDPOINT = "weather";
    private static final String FORECAST_ENDPOINT = "forecast";

    private static final String QUERY_PARAM = "q";
    private static final String FORMAT_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String LANG_PARAM = "lang";
    private static final String APP_ID_PARAM = "appid";

    private static final String FORMAT = "json";

    private static final String METRIC = "metric";
    private static final String IMPERIAL = "imperial";

    public static URL getWeatherUrl(Context context){
        return buildUrl(context, WEATHER_ENDPOINT);
    }

    public static URL getForecastUrl(Context context){
        return buildUrl(context, FORECAST_ENDPOINT);
    }

    public static URL buildUrl(Context context, String endpoint){
        Uri.Builder builder = Uri.parse(BASE_URL + endpoint).buildUpon();
        Uri uri = builder.appendQueryParameter(QUERY_PARAM, context.getString(R.string.pref_location_default))
                .appendQueryParameter(FORMAT_PARAM, FORMAT)
                .appendQueryParameter(UNITS_PARAM, METRIC)
                .appendQueryParameter(LANG_PARAM, Locale.getDefault().getLanguage())
                .appendQueryParameter(APP_ID_PARAM, context.getString(R.string.api_key))
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;

        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            String response = null;
            if (scanner.hasNext()) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
