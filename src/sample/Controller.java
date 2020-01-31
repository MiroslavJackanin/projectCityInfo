package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Controller {
    public ComboBox<String> combo1;
    public ComboBox<String> combo2;
    public Button okButton;
    public Label population;
    public Label temperature;
    public Label humidity;
    public Label sCity;
    public Label sCountry;
    public Label longitude;
    public Label latitude;
    public Label visibility;
    public CheckBox checkDetail;
    public Label sunrise;
    public Label sunset;
    public Pane detailPane;
    List countries;
    List<City> city;

    public Controller() {
        Database db = new Database();
        countries = db.getCountries();
    }
    public void initialize(){
        combo2.setDisable(true);
        okButton.setDisable(true);
        detailPane.setVisible(false);
    }

    public void populateCB1(){
        Database db = new Database();
        countries = db.getCountries();
        combo1.getItems().setAll(countries);
    }

    public String getCB1Value(){
        if (combo1.getValue()==null){
            combo2.setDisable(true);
        }else{
            combo2.setDisable(false);
        }
        return combo1.getValue();
    }

    public String getCB2Value(){
        if (combo2.getValue()==null){
            okButton.setDisable(true);
        }else{
            okButton.setDisable(false);
        }
        return combo2.getValue();
    }

    public void populateCB2(){
        Database db = new Database();
        city = db.getCities(getCB1Value());
        combo2.getItems().clear();
        for(City s: city){
            combo2.getItems().add(s.getName());
        }
    }

    public void getInfo(){
        Database db = new Database();
        String pop = db.getPopulation(getCB1Value(), getCB2Value());
        population.setText(populationDecimalFormat(pop));
        sCity.setText(getCB2Value());
        sCountry.setText(getCB1Value());

        String cityName = combo2.getValue();
        City city = null;
        for (City c: this.city){
            if (c.getName().equalsIgnoreCase(cityName)){
                city = c;
                break;
            }
        }
        if (city==null){
            return;
        }

        WebWeather webWeather = new WebWeather();
        temperature.setText(webWeather.getData(city.getName(), city.getCode2()).getTemp()+"");
        humidity.setText(webWeather.getData(city.getName(), city.getCode2()).getHumidity()+"");
        longitude.setText(webWeather.getData(city.getName(), city.getCode2()).getLon()+"");
        latitude.setText(webWeather.getData(city.getName(), city.getCode2()).getLat()+"");
        visibility.setText(webWeather.getData(city.getName(), city.getCode2()).getVisibility()+"");
        sunrise.setText(new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (webWeather.getData(city.getName(), city.getCode2()).getSunrise()*1000)));
        sunset.setText(new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (webWeather.getData(city.getName(), city.getCode2()).getSunset()*1000)));
    }


    public String populationDecimalFormat(String population){
        return String.format("%,d", Integer.parseInt(population));
    }

    public void showDetail(ActionEvent actionEvent) {
        if (checkDetail.isSelected()){
            detailPane.setVisible(true);
        }else{
            detailPane.setVisible(false);
        }
    }
}
