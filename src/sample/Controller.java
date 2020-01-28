package sample;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public ComboBox<String> combo1;
    public ComboBox<String> combo2;
    public Button okButton;
    public Label population;
    List countries;
    List cities;

    public Controller() {
        Database db = new Database();
        countries = db.getCountries();
    }
    public void initialize(){
        combo2.setDisable(true);
        okButton.setDisable(true);
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
        List<City> city;
        city = db.getCities(getCB1Value());
        combo2.getItems().clear();
        for(City s: city){
            combo2.getItems().add(s.getName());
        }
    }

    public void getInfo(){
        Database db = new Database();
        String pop = db.getPopulation(getCB1Value(), getCB2Value());
        population.setText(pop);
    }
}
