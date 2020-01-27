package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public List getCountries(){
        List<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT Code, Name FROM country");
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
        List<String> list = new ArrayList<>();
        try{
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT city.name FROM country JOIN city ON country.code = city.countrycode WHERE country.name LIKE ? ");
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("Name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://itsovy.sk:3306/world_x", "student", "kosice2019");
    }
}
