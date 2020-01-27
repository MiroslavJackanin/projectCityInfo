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

    public List getCities(){
        List<String> list = new ArrayList<>();
        Controller ctr = new Controller();
        try{
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT Name FROM city WHERE countryCode LIKE ?");
            ps.setString(1, ctr.getCB1Value());
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
