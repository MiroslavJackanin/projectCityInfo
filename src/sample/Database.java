package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public List getCountries(){
        List<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT Code, Name FROM country ORDER BY name ASC");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("Code")+"  "+rs.getString("Name"));
                list.add(rs.getString("Name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List getCities(String country){
        List<City> list = new ArrayList<>();
        try{
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT city.name, json_extract(info, '$.Population') AS Info, Code, Code2 FROM country JOIN city ON country.code = city.countrycode WHERE country.name LIKE ? ");
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("city.name");
                String code2 = rs.getString("Code2");
                String code3 = rs.getString("Code");
                int population = rs.getInt("Info");
                City city = new City(name, population, code3, code2);
                list.add(city);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public String getPopulation(String country, String city){
        String population = null;
        try{
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT json_extract(info, '$.Population') FROM city JOIN country ON city.countryCode = country.code WHERE country.name LIKE ? AND city.name LIKE ?");
            ps.setString(1, country);
            ps.setString(2, city);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                population = rs.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return population;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://itsovy.sk:3306/world_x", "student", "kosice2019");
    }
}
