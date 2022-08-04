package com.ali.weatherpredectionne.utils;

import com.ali.weatherpredectionne.entity.Main;
import com.ali.weatherpredectionne.entity.Weather;
import com.ali.weatherpredectionne.entity.WeatherInfo;
import com.ali.weatherpredectionne.entity.Wind;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherDateParser {

    private static final String OWM_MESSAGE_CODE = "cod";
    private static final String OWM_CITY = "city";
    private static final String OWM_CITY_NAME = "name";
    private static final String OWM_LIST = "list";
    private static final String OWM_DATE = "dt";
    private static final String OWM_DATE_TEXT = "dt_txt";
    private static final String OWM_WIND = "wind";
    private static final String OWM_WINDSPEED = "speed";
    private static final String OWM_WIND_DIRECTION = "deg";
    private static final String OWM_MAIN = "main";
    private static final String OWM_TEMPERATURE = "temp";
    private static final String OWM_MAX = "temp_max";
    private static final String OWM_MIN = "temp_min";
    private static final String OWM_PRESSURE = "pressure";
    private static final String OWM_HUMIDITY = "humidity";
    private static final String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_DESCRIPTION = "description";
    private static final String OWM_WEATHER_ICON = "icon";

    public static WeatherInfo getWeatherInfoObjectFromJson(String weatherInfoJsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(weatherInfoJsonString);
        JSONObject weatherJsonObject = jsonObject.getJSONArray(OWM_WEATHER).getJSONObject(0);
        JSONObject mainJsonObject = jsonObject.getJSONObject(OWM_MAIN);
        JSONObject windJsonObject = jsonObject.getJSONObject(OWM_WIND);

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setDt(jsonObject.getLong(OWM_DATE));
        weatherInfo.setName(jsonObject.has(OWM_CITY_NAME) ? jsonObject.getString(OWM_CITY_NAME) : "");

        Weather weather = new Weather();
        weather.setDescription(weatherJsonObject.getString(OWM_WEATHER_DESCRIPTION));
        weather.setIcon(weatherJsonObject.getString(OWM_WEATHER_ICON));
        List<Weather> list = new ArrayList<>();
        list.add(weather);
        weatherInfo.setWeatherList(list);

        Main main = new Main();
        main.setTemp(mainJsonObject.getDouble(OWM_TEMPERATURE));
        main.setMax_temp(mainJsonObject.getDouble(OWM_MAX));
        main.setMin_temp(mainJsonObject.getDouble(OWM_MIN));
        main.setHumidity(mainJsonObject.getDouble(OWM_HUMIDITY));
        main.setPressure(mainJsonObject.getDouble(OWM_PRESSURE));
        weatherInfo.setMain(main);

        Wind wind = new Wind();
        wind.setDeg(windJsonObject.getDouble(OWM_WIND_DIRECTION));
        wind.setSpeed(windJsonObject.getDouble(OWM_WINDSPEED));
        weatherInfo.setWind(wind);

        return weatherInfo;
    }
}
