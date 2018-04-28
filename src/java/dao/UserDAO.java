/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.University;
import model.User;

/**
 *
 * @author it353s836
 */
public class UserDAO {
     private static final String DB_URL = "jdbc:derby://localhost:1527/Project353";
    private static final String USERNAME = "itkstu";
    private static final String PASSWORD = "student";
    private static final String QUERY_BY_NAME = "SELECT firstname,profileURL,gradDetails,highSchoolName "
            + "FROM Project353.Users WHERE Upper(firstname || ' ' || lastname) LIKE Upper(%?%) OR UPPER(firstname) LIKE upper(%?%) OR UPPER(lastname) LIKE UPPER(%?%)";
    public ArrayList<User> checkDBForStudents(String entry){
        ArrayList<User> userList = new ArrayList();
        User user;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement query = connection.prepareStatement(QUERY_BY_NAME);
            query.setString(1, entry);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                String n = resultSet.getString("name");
                String url = resultSet.getString("profileURL");
                String gradDetails = resultSet.getString("gradDetails");
                String highSchoolName = resultSet.getString("highSchoolName");
                user = new User(n,url, gradDetails,highSchoolName);
                userList.add(user);
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }

        return userList;
    
    }
    
    
}
