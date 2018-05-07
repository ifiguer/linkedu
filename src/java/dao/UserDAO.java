/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.inject.Inject;
import model.LoginBean;
import model.User;
import utility.LinkedUConstants;
/**
 *
 * @author it353s836
 */
public class UserDAO implements Serializable {

    private static final String DB_URL = LinkedUConstants.DB_URL;
    private static final String DB_NAME = LinkedUConstants.DB_NAME;
    
    private static final String USERNAME = "itkstu";
    private static final String PASSWORD = "student";
    private static final String SEARCH_QUERY_BY_NAME = "SELECT firstname,lastname,userID,profileURL,gradDetails,highSchooldetails "
            + "FROM " + DB_NAME + ".Users WHERE Upper(firstname || ' ' || lastname) LIKE Upper('%'||?||'%') OR UPPER(firstname) LIKE upper('%'||?||'%') OR UPPER(lastname) LIKE UPPER('%'||?||'%')";
    private static final String DELIMITER = ":";
    @Inject
    FeedDAO feedDAO;

    public ArrayList<User> checkDBForStudents(String entry) {
        ArrayList<User> userList = new ArrayList();
        User user;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement query = connection.prepareStatement(SEARCH_QUERY_BY_NAME);
            query.setString(1, entry);
            query.setString(2, entry);
            query.setString(3, entry);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                String n = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                String url = resultSet.getString("profileURL");
                String gradDetails = resultSet.getString("gradDetails");
                String highSchoolName = resultSet.getString("highschooldetails");
                String userID = resultSet.getString("userID");
                user = new User(n, url, userID, gradDetails, highSchoolName);
                userList.add(user);
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }

        return userList;

    }

    public int addUserToFollowingList(LoginBean cust, String followID) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            //String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(DB_URL, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "UPDATE " + DB_NAME +".Users set ";
            insertString += "following='";
            insertString += (cust.getFollowing().equals("") ? followID + "'" : cust.getFollowing() + DELIMITER + followID + "'");
            cust.setFollowing(cust.getFollowing().equals("") ? followID : cust.getFollowing() + DELIMITER + followID);
            insertString += " where userid='" + cust.getUsername() + "'";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            cust.setPosts(feedDAO.getFeedsByUsername(cust.getUsername()));
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }

    public int promoteUser(String u) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(DB_URL, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "UPDATE " + DB_NAME + ".Users set ";
            insertString += "administrator=true";

            insertString += " where userid='" + u + "'";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);

            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
}
