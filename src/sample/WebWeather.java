package sample;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebWeather {

    public Weather getData(String city, String code2){
        String name = "";
        String country = "";
        double temp = 0;
        int humidity = 0;
        double lon = 0;
        double lat = 0;
        int visibility = 0;
        long sunrise = 0;
        long sunset = 0;

        try{
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + code2 + "&units=metric&appid=37d1b237e2d02d334784c7f5f8507e7d");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if(connection.getResponseCode() == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String output = br.readLine();

                JSONObject jsonObject = new JSONObject(output);
                name = jsonObject.getString("name");
                visibility = jsonObject.getInt("visibility");
                country = jsonObject.getJSONObject("sys").getString("country");
                temp = jsonObject.getJSONObject("main").getDouble("temp");
                humidity = jsonObject.getJSONObject("coord").getInt("lon");
                lon = jsonObject.getJSONObject("coord").getDouble("lon");
                lat = jsonObject.getJSONObject("coord").getDouble("lat");
                sunrise = jsonObject.getJSONObject("sys").getLong("sunrise");
                sunset = jsonObject.getJSONObject("sys").getLong("sunset");
            }else{
                throw new NoSuchCityException("City not found.");
            }
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Weather(name, country, temp, humidity, lon, lat, visibility, sunrise, sunset);
    }
}