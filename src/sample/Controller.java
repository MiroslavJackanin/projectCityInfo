package sample;

import javafx.scene.control.ComboBox;
import java.util.List;

public class Controller {
    public ComboBox<String> combo1;
    public ComboBox<String> combo2;
    List countries;
    List cities;

    public Controller() {
        Database db = new Database();
        countries = db.getCountries();
    }

    public void populateCB1(){
        Database db = new Database();
        countries = db.getCountries();
        combo1.getItems().setAll(countries);
    }

    public String getCB1Value(){
        return combo1.getValue();
    }

    public void populateCB2(){
        Database db = new Database();
        cities = db.getCities();
        combo2.getItems().setAll(cities);
    }
}
