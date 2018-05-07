package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utility.LinkedUConstants;

public class LoginBeanDA implements Serializable {
    private static final String DB_URL = LinkedUConstants.DB_URL;
    private static final String DB_NAME = LinkedUConstants.DB_NAME;
    public static LoginBean validInfo(String username, String password) {
        LoginBean lb = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(DB_URL, "itkstu", "student");

            String queryString = "select * from " + DB_NAME  +".USERS where USERID = ? and PASSWORD = ?";

            PreparedStatement pstmt = DBConn.prepareStatement(queryString);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            boolean r = rs.next();
            if (r) {
                lb = new LoginBean();
                lb.setEmail(rs.getString("email"));
                lb.setFirstname(rs.getString("firstname"));
                lb.setLastname(rs.getString("lastname"));
                lb.setActScore(rs.getInt("act"));
                lb.setProfileURL(rs.getString("profileurl"));
                lb.setSatScore(rs.getInt("sat"));
                lb.setFollowing(rs.getString("following"));
                lb.setHighSchoolDetails(rs.getString("highSchoolDetails"));
                lb.setGradDetails(rs.getString("gradDetails"));
                lb.setAdministrator(rs.getBoolean("administrator"));
            }
            DBConn.close();
            return lb;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }

    public static boolean usernameTaken(String username) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(DB_URL, "itkstu", "student");
            String queryString = "select * from " + DB_NAME + ".Users where USERID = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(queryString);
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            boolean r = rs.next();
            DBConn.close();
            return r;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static int storeCustomerToDB(LoginBean cust) {
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
            String insertString2 = "INSERT INTO " + DB_NAME + ".Users VALUES (?,?,?,?,?,'img/egg.jpg','',?,?,false,?,?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString2);
            pstmt.setString(1, cust.getUsername());
            pstmt.setString(2, cust.getPassword());
            pstmt.setString(3, cust.getFirstname());
            pstmt.setString(4, cust.getLastname());
            pstmt.setString(5, cust.getEmail());
            pstmt.setString(6, cust.getGradDetails());
            pstmt.setString(7, cust.getHighSchoolDetails());
            pstmt.setInt(8, cust.getActScore());
            pstmt.setInt(9, cust.getSatScore());
            rowCount = pstmt.executeUpdate();
            System.out.println("insert string =" + insertString2);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;

    }

    public static int storeProfileImgUrlToDB(String profileURL, String username) {
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
            insertString = "UPDATE " + DB_NAME + ".Users set " + "profileURL='uploads\\" + profileURL + "' where userid='" + username + "'";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;

    }

    public static int updateCustomerToDB(LoginBean cust) {
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
            insertString += (cust.getPassword().equals("") ? "" : "password='" + cust.getPassword() + "',");
            insertString += (cust.getEmail().equals("") ? "" : "email='" + cust.getEmail() + "',");
            insertString += (cust.getFirstname().equals("") ? "" : "firstname='" + cust.getFirstname() + "',");
            insertString += (cust.getLastname().equals("") ? "" : "lastname='" + cust.getLastname() + "'");
            insertString += " where userid='" + cust.getUsername() + "'";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;

    }

}
